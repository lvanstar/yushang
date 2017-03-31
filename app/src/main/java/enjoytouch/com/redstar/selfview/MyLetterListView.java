package enjoytouch.com.redstar.selfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.util.ContentUtil;

public class MyLetterListView extends View
{

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	/*String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };*/
	//public static String[] b = { "#","A","C","S","T"};

	public static List<String> b= MyApplication.b;


	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;

	public MyLetterListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public MyLetterListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MyLetterListView(Context context)
	{
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (showBkg)
		{
			canvas.drawColor(Color.parseColor("#00000000"));//点击右边首字母产生的透明背景
		}
		ContentUtil.makeLog("zZ正在执行：","MyLetterListView");
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.size();//获取每一个字母的高度
		for (int i = 0; i < b.size(); i++)
		{
			paint.setColor(0xff9e9e9e);
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			//选中的状态
			if (i == choose)
			{
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b.get(i)) / 2;	//首字母所在的x坐标
			float yPos = singleHeight * i + singleHeight;			//首字母所在的y坐标
			canvas.drawText(b.get(i), xPos, yPos, paint);
			paint.reset();											//重置画笔
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.size());// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null)
			{
				if (c > 0 && c < b.size())
				{
					listener.onTouchingLetterChanged(b.get(c));
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null)
			{
				if (c > 0 && c < b.size())
				{
					listener.onTouchingLetterChanged(b.get(c));
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener)
	{
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener
	{
		public void onTouchingLetterChanged(String s);
	}

}
