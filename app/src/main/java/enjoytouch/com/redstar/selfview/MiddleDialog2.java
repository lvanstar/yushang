package enjoytouch.com.redstar.selfview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import enjoytouch.com.redstar.R;

/**
 * Created by Administrator on 2015/12/12.
 * 优惠券已被使用的弹窗
 */
public class MiddleDialog2 extends Dialog {

    private onBottonListener listener;

    public MiddleDialog2(final Context context, String title, String str, final onBottonListener listener, int theme) {
        super(context, theme);
        View view = View.inflate(context, R.layout.dialog_middle2, null);
        setContentView(view);
        setCancelable(true);        //设置点击对话框以外的区域时，是否结束对话框
        this.listener=listener;
        ((TextView) view.findViewById(R.id.middle_tv)).setText(str);    //设置对话框内容
        ((TextView) view.findViewById(R.id.x_tv)).setText(title);
        view.findViewById(R.id.execute2).setOnClickListener(new View.OnClickListener() {//知道了
            @Override
            public void onClick(View v) {
                listener.onOk();
                dismiss();
            }
        });

    }

    public interface onBottonListener{
        void onOk();
    }
}
