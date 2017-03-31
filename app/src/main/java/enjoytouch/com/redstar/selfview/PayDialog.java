package enjoytouch.com.redstar.selfview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import enjoytouch.com.redstar.R;


/**
 * Created by enjoytouch-ad02 on 2015/8/5.
 */
public class PayDialog<E> extends Dialog {
    private E bean;
    private int position;
    private onButtonCLickListener listener;

    public PayDialog(Context context,final onButtonCLickListener listener, int theme) {
        super(context, theme);
        View view = View.inflate(context, R.layout.dialog_pay, null);
        setContentView(view);
        setCancelable(true);        //设置点击对话框以外的区域时，是否结束对话框
        this.listener = listener;
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                listener.onCancel();
            }
        });
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {      //确定
            @Override
            public void onClick(View v) {
                //if(bean!=null){
                    listener.onOk();
               // }
            }
        });

    }


    public interface onButtonCLickListener{
       public void onOk();
        public void onCancel();
    }
}
