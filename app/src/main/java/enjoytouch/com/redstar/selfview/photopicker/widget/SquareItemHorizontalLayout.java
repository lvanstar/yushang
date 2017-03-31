package enjoytouch.com.redstar.selfview.photopicker.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by donglua on 15/6/21.
 */
public class SquareItemHorizontalLayout extends RelativeLayout {
  public SquareItemHorizontalLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public SquareItemHorizontalLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SquareItemHorizontalLayout(Context context) {
    super(context);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
    int childHeightSize = getMeasuredHeight();
    widthMeasureSpec =
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
