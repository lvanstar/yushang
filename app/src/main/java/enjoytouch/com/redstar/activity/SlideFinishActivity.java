package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import enjoytouch.com.redstar.R;

/**
 * Created by bullytou on 2015/8/21.
 */
public class SlideFinishActivity extends Activity {//滑动结束activity

    protected int layout_id = R.id.home;
    private ViewGroup viewGroup;
    private OnSlideFinishListener listener;
    private float startX = -1024f;
    private float startY = -1024f;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /*
        if (viewGroup == null) {
            View view = findViewById(layout_id);
            if (view == null) return false;
            else viewGroup = (ViewGroup) view.getParent();
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = event.getX();
            startY = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (startX - event.getX() < 0 && Math.abs(startX - event.getX())>Math.abs(startY - event.getY())) {
                viewGroup.scrollTo((int) (startX - event.getX()), 0);
                return true;
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            if (Math.abs(startX - event.getX())>Math.abs(startY - event.getY())){
                if (event.getX()-startX < getResources().getDisplayMetrics().widthPixels / 2f){
                    viewGroup.scrollTo(0, 0);
                }else {
                    viewGroup.scrollTo(0 - getResources().getDisplayMetrics().widthPixels, 0);
                    if (listener != null) listener.onSlideFinish();
                    finish();
                }
                return true;
            }else {
                viewGroup.scrollTo(0, 0);
            }
        } */
        return super.dispatchTouchEvent(event);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//    }

    protected void setOnSlideFinishListener(OnSlideFinishListener listener){//滑动结束监听
        this.listener = listener;
    }

    public interface OnSlideFinishListener{
        void onSlideFinish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
