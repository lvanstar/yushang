package enjoytouch.com.redstar.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 意见反馈
 * duan
 */
public class FeedBackActivity  extends SlideFinishActivity{

    private View back;
    private TextView send;
    private EditText input_et;
    private String input;
    private InputMethodManager imm;
    private static FeedBackActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;

        setContentView(R.layout.feedback);
        setOnSlideFinishListener(new OnSlideFinishListener() {
            @Override
            public void onSlideFinish() {
                if (imm != null) imm.hideSoftInputFromWindow(input_et.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        setViews();
        setListeners();
    }

    private void setViews(){

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        back=findViewById(R.id.back);
        send=(TextView)findViewById(R.id.feedback_send);
        input_et=(EditText)findViewById(R.id.feedback_input);
        send.setEnabled(false);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(500);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void setListeners(){

        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackActivity.this.finish();
                if(imm!=null){
                    imm.hideSoftInputFromWindow(input_et.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
            }
        });

        //输入监听事件
        input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    send.setEnabled(true);
                    send.setBackground(getResources().getDrawable(R.drawable.black_roundcorner));
                }else{
                    send.setEnabled(false);
                    send.setBackground(getResources().getDrawable(R.drawable.text_pinglun2));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //发送
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=input_et.getText().toString();
                if(input.length()!=0){
                    Call<StatusBean>call= HttpServiceClient.getInstance().user_feedback(MyApplication.token,input,"","");
                    call.enqueue(new Callback<StatusBean>() {
                        @Override
                        public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                            if ("ok".equals(response.body().getStatus())) {
                                ExclusiveYeUtils.onMyEvent(INSTANCE, "sendAdiviceAction");
                                ContentUtil.makeToast(INSTANCE, "发送成功，谢谢您的支持！");
                                MobclickAgent.onEvent(INSTANCE, "sendYiJian");
                                input_et.setText("");
                                MobclickAgent.onEvent(getApplicationContext(), "v2_sendIssue");
                                FeedBackActivity.this.finish();
                            } else {
                                ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusBean> call, Throwable t) {

                        }
                    });

                }else{
                    Dialog d=DialogUtil.show(FeedBackActivity.this,"请填写内容!",false);
                    d.show();
                }
            }
        });
    }

    private FeedBackHandler handler = new FeedBackHandler();
    private static class FeedBackHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                INSTANCE.input_et.setFocusable(true);
                INSTANCE.input_et.setFocusableInTouchMode(true);
                INSTANCE.input_et.requestFocus();
                INSTANCE.imm.showSoftInput(INSTANCE.input_et, InputMethodManager.SHOW_FORCED);
            }
        }
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
