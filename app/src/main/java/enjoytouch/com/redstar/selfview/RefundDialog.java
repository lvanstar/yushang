package enjoytouch.com.redstar.selfview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import enjoytouch.com.redstar.R;

/**
 * Created by lizhaozhao on 16/8/3.
 */
public class RefundDialog extends Dialog {
    private onButtonCLickListener listener;

    public RefundDialog(Context context,final onButtonCLickListener listener, int theme) {
        super(context, theme);
        View view = View.inflate(context, R.layout.dialog_refund, null);
        setContentView(view);
        setCancelable(true);        //设置点击对话框以外的区域时，是否结束对话框

        this.listener = listener;
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {      //确定
            @Override
            public void onClick(View v) {
                //if(bean!=null){
                dismiss();
                listener.onActivieButtonClick();
                // }
            }
        });

    }


    public interface onButtonCLickListener{
        public void onActivieButtonClick();
    }
}
