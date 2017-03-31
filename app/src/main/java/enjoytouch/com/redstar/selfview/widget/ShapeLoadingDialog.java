package enjoytouch.com.redstar.selfview.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import enjoytouch.com.redstar.R;


/**
 * Created by zzz40500 on 15/6/15.
 */
public class ShapeLoadingDialog {



    private Context mContext;
    private Dialog mDialog;
    private LoadingView mLoadingView;
    private View mDialogContentView;


    public ShapeLoadingDialog(Context context) {
        this.mContext=context;
        init();
    }

    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog);
        mDialogContentView= LayoutInflater.from(mContext).inflate(R.layout.layout_dialog,null);


        mLoadingView= (LoadingView) mDialogContentView.findViewById(R.id.loadView);
        mDialog.setContentView(mDialogContentView);
    }

    public void setBackground(int color){
        GradientDrawable gradientDrawable= (GradientDrawable) mDialogContentView.getBackground();
        gradientDrawable.setColor(color);
    }

    public void setLoadingText(CharSequence charSequence){
        mLoadingView.setLoadingText(charSequence);
    }

    public void show(){
        mDialog.show();

        WindowManager m = ((Activity)mContext).getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight());   //高度设置为屏幕的
        p.width = (int) (d.getWidth());    //宽度设置为全屏
        mDialog.getWindow().setAttributes(p);     //设置生效

    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public Dialog getDialog(){
        return  mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCanceledOnTouchOutside(cancel);
    }
}
