package enjoytouch.com.redstar.selfview;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class DynamicHeightSimpleDraweeView extends SimpleDraweeView {

    private double mHeightRatio;//用于调整控件大小，传入的值为 Height除Width
    private boolean depandOnWidth;//标记以哪一边为标准

    public DynamicHeightSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public DynamicHeightSimpleDraweeView(Context context) {
        super(context);
    }

    public DynamicHeightSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHeightDevideByWidthRatio(double ratio,boolean depandOnWidth) {
        this.depandOnWidth = depandOnWidth;
        this.mHeightRatio = ratio;
        requestLayout();
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            if(depandOnWidth){
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) (width * mHeightRatio);
                setMeasuredDimension(width, height);
            }else {
                int height = MeasureSpec.getSize(heightMeasureSpec);
                int width = (int) (height / mHeightRatio);
                setMeasuredDimension(width, height);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
