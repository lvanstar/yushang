package enjoytouch.com.redstar.selfview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class HorizontalSlideListView extends ListView {
	private boolean ban = false;
	private boolean isSliding;
	private int slidePosition;
	/**
	 * 用于计算手指滑动的速度。
	 */
	private VelocityTracker mVelocityTracker;
	/**
	 * 手指按下X的坐标
	 */
	private int downY;
	/**
	 * 手指按下Y的坐标
	 */
	private int downX;
	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * ListView的item
	 */
	private View itemView;
	private static final int SNAP_VELOCITY = 600;
	/**
	 * 认为是用户滑动的最小距离
	 */
	private int touchSlop;
	public static final int SHOW_DEL = 1;
	public static final int HIDE_DEL = 2;

	/**
	 * 记录当前的滑动状态
	 */
	private int slideState;
	private int extraWidth = -1;
	private float xMove;
	private float yMove;
	private float xUp;
	private LinearLayout.LayoutParams mLayoutParams;
	private Context context;

	private ArrayList<Integer> cantSlide = new ArrayList<>();

	public HorizontalSlideListView(Context context) {
		this(context, null);
	}

	public HorizontalSlideListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HorizontalSlideListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public void setBan(int position, boolean ban){
		if (ban){
			if (!cantSlide.contains(position)) cantSlide.add(position);
		}else {
			if (cantSlide.contains(position)) cantSlide.remove(new Integer(position));
		}
	}

	public boolean isBan() {
		return ban;
	}

	public void setBan(boolean ban) {
		this.ban = ban;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(!ban){
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			createVelocityTracker(ev);
			Log.i("issliding", isSliding + "");
			if(!isSliding){
			downX = (int) ev.getX();
			downY = (int) ev.getY();
			//点击的位置
			slidePosition = pointToPosition(downX, downY);
			Log.i("当前指向的position", slidePosition+"");
			if (slidePosition == AdapterView.INVALID_POSITION || cantSlide.contains(slidePosition)) {
				return super.onTouchEvent(ev);
			}
			// 获取当前点的item
			itemView = getChildAt(slidePosition - getFirstVisiblePosition());
			// 获取删除按钮的宽度
			if (extraWidth == -1) {
				extraWidth = ((ViewGroup) itemView).getChildAt(1).getLayoutParams().width;
			}
			// 重新设置layout_width 等于屏幕宽度
			mLayoutParams = (LinearLayout.LayoutParams) ((ViewGroup) itemView).getChildAt(0).getLayoutParams();
			mLayoutParams.width = screenWidth;
			((ViewGroup) itemView).getChildAt(0).setLayoutParams(mLayoutParams);
			Log.i("leftmargin", mLayoutParams.leftMargin+"");
			if(mLayoutParams.leftMargin<=-extraWidth){
				slideState = HIDE_DEL;
				isSliding = true;
				scrollToStartLayout();
				MotionEvent cancelEvent = MotionEvent.obtain(ev);
                cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                        (ev.getActionIndex()
                                << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                onTouchEvent(cancelEvent);
                cancelEvent.recycle();
				return true;
			}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (cantSlide.contains(slidePosition)) return super.onTouchEvent(ev);
			xMove = ev.getX();
			yMove = ev.getY();
			int moveDistanceX = (int) (xMove - downX);
			int moveDistanceY = (int) (yMove - downY);
			if (!isSliding && (Math.abs(moveDistanceX) >= touchSlop)
					&& (moveDistanceX < 0) && (Math.abs(moveDistanceY) < Math.abs(moveDistanceX))) {
				isSliding = true;
				slideState = SHOW_DEL;
			}
			if(isSliding&&(slideState == SHOW_DEL)){
				if(-moveDistanceX>extraWidth){
					moveDistanceX =-extraWidth;
				}
				if(-moveDistanceX<0){
					moveDistanceX =0;
				}
				mLayoutParams.leftMargin = moveDistanceX;
				((ViewGroup) itemView).getChildAt(0).setLayoutParams(mLayoutParams);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			xUp =  ev.getX();
			if (isSliding) {
				switch(slideState){
				case SHOW_DEL:
					if (downX-xUp > extraWidth/1.1||getScrollVelocity() > SNAP_VELOCITY) {
						scrollToDelLayout();
					} else {
						scrollToStartLayout();
					}
					break;
				}
				recycleVelocityTracker();
				MotionEvent cancelEvent = MotionEvent.obtain(ev);
                cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                        (ev.getActionIndex()
                                << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                onTouchEvent(cancelEvent);
                cancelEvent.recycle();
				return true;
			}else{
				recycleVelocityTracker();
			}
			break;
		}
		}
		return super.onTouchEvent(ev);
	}
	
	public void scrollToDelLayout() {
		new ScrollTask(itemView).execute(-9);
	}
	public void scrollToStartLayout() {
		new ScrollTask(itemView).execute(9);
	}
	public void scrollToStartLayout(View v) {
		new ScrollTask(v).execute(9);
	}
	
	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
		private View v;
		private LinearLayout.LayoutParams params;
		public ScrollTask(View v){
			this.v = v;
			params =(LinearLayout.LayoutParams) ((ViewGroup)v).getChildAt(0).getLayoutParams();
		}
		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = params.leftMargin;
			// 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin <= -extraWidth) {
					leftMargin =  -extraWidth;
					break;
				}
				if (leftMargin > 0) {
					leftMargin = 0;
					break;
				}
				publishProgress(leftMargin);
				sleep(5);
			}
			/*if (speed[0] > 0) {
				isLeftLayoutVisible = false;
			} else {
				isLeftLayoutVisible = true;
			}*/
			isSliding = false;
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			params.leftMargin = leftMargin[0];
			((ViewGroup) v).getChildAt(0).setLayoutParams(params);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			params.leftMargin = leftMargin;
			((ViewGroup) v).getChildAt(0).setLayoutParams(params);
		}
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}
	
	private void recycleVelocityTracker() {
		if(mVelocityTracker!=null){
		mVelocityTracker.recycle();
		mVelocityTracker = null;
		}
	}
	
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}
}
