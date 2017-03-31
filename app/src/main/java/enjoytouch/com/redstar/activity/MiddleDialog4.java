package enjoytouch.com.redstar.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import enjoytouch.com.redstar.R;

/**
 * Created by Administrator on 2015/12/16.
 */
public class MiddleDialog4<E> extends Dialog {
    private E bean;
    private int position;
    private onButtonCLickListener listener;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    public MiddleDialog4(Context context, String str,String str2,String str3,String str4,String str5,String str6, final onButtonCLickListener<E> listener_butt, int theme) {
        super(context, theme);
        View view = View.inflate(context, R.layout.dialog_middle4, null);
        setContentView(view);
        setCancelable(true);        //设置点击对话框以外的区域时，是否结束对话框
        this.listener = listener_butt;
        init(view);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onActivieButtonClick(tv1.getText().toString(),1);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onActivieButtonClick(tv2.getText().toString(), 2);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onActivieButtonClick(tv3.getText().toString(), 3);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onActivieButtonClick(tv4.getText().toString(), 4);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onActivieButtonClick(tv5.getText().toString(), 5);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                dismiss();
               // listener.onActivieButtonClick("",6);
            }
        });

    }

    private void init(View view) {
        tv1= (TextView) view.findViewById(R.id.tv1);
        tv2= (TextView) view.findViewById(R.id.tv2);
        tv3= (TextView) view.findViewById(R.id.tv3);
        tv4= (TextView) view.findViewById(R.id.tv4);
        tv5= (TextView) view.findViewById(R.id.tv5);
        tv6= (TextView) view.findViewById(R.id.tv6);
    }


    public void resetData(E bean,int position) {
        this.bean = bean;
        this.position = position;
    }

    public interface onButtonCLickListener<E>{
        public void onActivieButtonClick(E bean, int position);
    }
}
