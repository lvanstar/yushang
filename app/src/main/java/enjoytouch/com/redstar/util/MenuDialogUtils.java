package enjoytouch.com.redstar.util;

import android.app.Dialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CallLog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import enjoytouch.com.redstar.R;


/**
 * Created by Administrator on 2015/12/10.
 */
public class MenuDialogUtils extends Dialog {
   public ButtonClickListener listener_b;

    public MenuDialogUtils (final Context context, int theme,int resource, final String phone,ButtonClickListener listener) {
        super(context, theme);
        View view = View.inflate(context, resource, null);
        this.listener_b=listener;
        setContentView(view);
        setCancelable(true);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
       // window.setWindowAnimations(R.anim.menu_dialog_in); //添加动画
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        if (!"".equals(phone)){
         TextView view_text = (TextView) view.findViewById(R.id.number_Tv);
         view_text.setText(phone);
        }
        view.findViewById(R.id.rl_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener_b.onButtonClick(0);
                MenuDialogUtils.this.dismiss();
            }
        });
        findViewById(R.id.tv_cancle_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuDialogUtils.this.dismiss();
            }
        });
    }
//    public void setButtonClickListener(ButtonClickListener listener){
//        this.listener = listener;
//
//    }
    public interface ButtonClickListener {
        public void onButtonClick(int i);
    }
}
