package enjoytouch.com.redstar.selfview.observableScrollViewo;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import enjoytouch.com.redstar.selfview.Pullable;

//颜色渐变
public class ObservableScrollView extends ScrollView implements
		ObservableScrollable ,Pullable{
	private boolean mDisableEdgeEffects = true;

	private OnScrollChangedCallback mOnScrollChangedListener;

	public ObservableScrollView(Context context) {
		super(context);
	}

	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ObservableScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScroll(l, t);
		}
	}

	@Override
	protected float getTopFadingEdgeStrength() {
		if (mDisableEdgeEffects
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			return 0.0f;
		}
		return super.getTopFadingEdgeStrength();
	}

	@Override
	protected float getBottomFadingEdgeStrength() {
		if (mDisableEdgeEffects
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			return 0.0f;
		}
		return super.getBottomFadingEdgeStrength();
	}

	@Override
	public void setOnScrollChangedCallback(OnScrollChangedCallback callback) {
		mOnScrollChangedListener = callback;
	}

	@Override
	public boolean canPullDown() {
		if (getScrollY() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean canPullUp() {
		{
			if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
				return true;
			else
				return false;
		}
	}
}