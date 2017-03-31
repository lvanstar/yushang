package enjoytouch.com.redstar.selfview.ImagePage;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import enjoytouch.com.redstar.fragment.HomepageFragment;
import enjoytouch.com.redstar.util.ContentUtil;

public class ChildViewPager extends ViewPager {
	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	private boolean left = false;
	private boolean right = false;
	private boolean isScrolling = false;
	private int lastValue = -1;
	OnSingleTouchListener onSingleTouchListener;

	public ChildViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChildViewPager(Context context) {
		super(context);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		getParent().requestDisallowInterceptTouchEvent(true); // 只需这句话，让父类不拦截触摸事件就可以了。
//		return super.dispatchTouchEvent(ev);
//	}

//	 @Override
//	 public boolean onInterceptTouchEvent(MotionEvent arg0) {
//	 // 当拦截触摸事件到达此位置的时候，返回true，
//	 // 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
//		 return true;
//	 }

	 @Override
	 public boolean onTouchEvent(MotionEvent arg0) {
		 // 每次进行onTouch事件都记录当前的按下的坐标

		 if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
		 // 记录按下时候的坐标
		 // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
		 downP.x = arg0.getX();
		 downP.y = arg0.getY();

		 // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
//				 getParent().requestDisallowInterceptTouchEvent(true);
		 onTouchDown();
		 }

		 if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
			 HomepageFragment.a(false);
				 // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
//				 getParent().requestDisallowInterceptTouchEvent(true);


		 }

		 if (arg0.getAction() == MotionEvent.ACTION_UP) {
			 HomepageFragment.a(true);
			 curP.x = arg0.getX();
			 curP.y = arg0.getY();
		 // 在up时判断是否按下和松手的坐标为一个点
		 // 如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
		 // if (downP.x == curP.x && downP.y == curP.y) {
			 ContentUtil.makeLog("lzz","curP.x:"+curP.x+","+"curP.y:"+curP.y);
			 onTouchUp();
			 if ((Math.abs(downP.x - curP.x) < 3 && Math.abs(downP.y - curP.y) < 3)) {
				 onSingleTouch();
				 //getParent().requestDisallowInterceptTouchEvent(false);
				 //return true;
			 }
		 }

		 return super.onTouchEvent(arg0);
	 }

	/**
	 * 单击
	 */
	public void onSingleTouch() {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch();
		}
	}

	public void onTouchDown() {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onTouchDown();
		}
	}

	public void onTouchUp() {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onTouchUp();
		}
	}

	/**
	 * 创建点击事件接口
	 */
	public interface OnSingleTouchListener {
		public void onSingleTouch();

		public void onTouchDown();

		public void onTouchUp();
	}

	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}


}
