package enjoytouch.com.redstar.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import enjoytouch.com.redstar.R;

/**
 * Created by Administrator on 2015/12/16.
 */
public class MiddleDialog2<E> extends Dialog{
    private E bean;
    private int position;
    private onButtonCLickListener listener;
    public MiddleDialog2(final Context context, String str, final onButtonCLickListener<E> listener, int theme) {
        super(context, theme);
        View view = View.inflate(context, R.layout.dialog_middle2, null);
        setContentView(view);
        setCancelable(true);        //设置点击对话框以外的区域时，是否结束对话框
        ((TextView) view.findViewById(R.id.middle_tv)).setText(str);    //设置对话框内容
       // ((TextView) view.findViewById(R.id.phone)).setText(str2);
        this.listener = listener;
        view.findViewById(R.id.execute2).setOnClickListener(new View.OnClickListener() {//知道了
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
      /*  view.findViewById(R.id.phone).setOnClickListener(new View.OnClickListener() {//知道了
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(
                        "android.intent.action.CALL", Uri.parse("tel:"
                        + str2));
                context.startActivity(phoneIntent);
                dismiss();
            }
        });*/

    }



    public void resetData(E bean,int position) {
        this.bean = bean;
        this.position = position;
    }

    public interface onButtonCLickListener<E>{
        public void onActivieButtonClick(E bean, int position);
    }
}
