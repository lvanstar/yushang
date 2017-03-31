package enjoytouch.com.redstar.selfview;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import enjoytouch.com.redstar.R;


/**
 * Created by enjoytouch-ad02 on 2015/8/5.
 */
public class MiddleDialog<E> extends Dialog {
    private E bean;
    private int position;
    private onButtonCLickListener listener;

    public MiddleDialog(Context context, String str,String title,String cancel,String ok,int img,final onButtonCLickListener<E> listener, int theme) {
        super(context, theme);
        View view = View.inflate(context, R.layout.dialog_middle, null);
        setContentView(view);
        setCancelable(true);        //设置点击对话框以外的区域时，是否结束对话框
        ((TextView) view.findViewById(R.id.middle_tv)).setText(str);    //设置对话框内容
        ImageView imageView= (ImageView) view.findViewById(R.id.x);           //设置对话框的警告图片
        imageView.setImageResource(img);
        ((TextView) view.findViewById(R.id.x_tv)).setText(title);       //设置对话框的标题内容
        ((TextView) view.findViewById(R.id.cancel)).setText(cancel);
        ((TextView) view.findViewById(R.id.ok)).setText(ok);

        this.listener = listener;
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {      //确定
            @Override
            public void onClick(View v) {
                //if(bean!=null){
                    dismiss();
                    listener.onActivieButtonClick(bean,position);
               // }
            }
        });

    }

    public void resetData(E bean,int position) {
        this.bean = bean;
        this.position = position;
    }

    public interface onButtonCLickListener<E>{
       public void onActivieButtonClick(E bean, int position);
    }
}
