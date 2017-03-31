package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.HttpUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplacePhoneActivity extends Activity {
    private String username;
    private String password;
    private View back;
    private EditText et_phone;
    private TextView code,tv_huan;
    private EditText et_getcode;
    private RelativeLayout login_rl_02;
    private RelativeLayout replace_yuyin_rl;
    boolean f;
    int time;
    private ReplacePhoneActivity INSTANCE;

    private Handler handler;
    private timeThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_phone);
        INSTANCE=this;
        setViews();
        setListener();
    }
    private void setViews(){
        back=findViewById(R.id.back);
        replace_yuyin_rl= (RelativeLayout) findViewById(R.id.replace_yuyin);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ContentUtil.isMobileNO(et_phone.getText().toString())) {
                    if ((et_phone.getText().toString()).equals(MyApplication.mobile)){
                       DialogUtil.show(ReplacePhoneActivity.this,"不能更换原号码",false).show();
                    }else {
                        code.setEnabled(true);
                        code.setBackgroundColor(getResources().getColor(R.color.hong));
                        code.setText("获取验证码");
                        et_getcode.requestFocus();
                        code.setTextColor(getResources().getColor(R.color.white));
                    }
                    // tv_getcode.setTextColor(getResources().getColor(R.color.text02));
                }else {
                    code.setEnabled(false);
                    code.setBackgroundColor(getResources().getColor(R.color.bg_grak));
                    code.setText("获取验证码");
                    code.setTextColor(getResources().getColor(R.color.yiduiwan_text));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    int num = msg.arg1;
                    if (num > 0) {
                        code.setEnabled(false);
                        code.setBackgroundColor(0xffd4d4d4);
                        code.setTextColor(0xffb5b5b5);
                        code.setText(num + "秒后重发");
                    } else {
                        code.setEnabled(true);
                        code.setBackgroundColor(0xffE52E5E);
                        code.setTextColor(0xffffffff);
                        code.setText("获取验证码");
                    }
                } else if (msg.what == 3) {
                    try {
                        JSONObject jo = (JSONObject) msg.obj;
                        if (jo.getString("status").toLowerCase().equals("ok")) {
                            JSONObject jresult = jo.getJSONObject("data");
//                            password_et.setText(Integer.toString(jresult.getInt("code")));
                            Log.i("code", Integer.toString(jresult.getInt("code")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        code= (TextView) findViewById(R.id.tv_getcode);
        code.setFocusable(false);
        et_getcode= (EditText) findViewById(R.id.et_getcode);
        login_rl_02= (RelativeLayout) findViewById(R.id.login_rl_r);
        tv_huan=(TextView) findViewById(R.id.tv_huan);
//        login_rl_02.setFocusable(false);
//        et_getcode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (ContentUtil.isMobileNO(et_phone.getText().toString()) && et_getcode.getText().length() == 6) {
//                    login_rl_02.setBackgroundResource(R.drawable.text_pinglun);
//                    login_rl_02.setEnabled(true);
//                    tv_huan.setTextColor(getResources().getColor(R.color.white));
//                } else {
//                    login_rl_02.setEnabled(false);
//                    tv_huan.setTextColor(getResources().getColor(R.color.yiduiwan_text));
//                    login_rl_02.setBackgroundResource(R.drawable.black_roundcorner);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) et_phone.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_phone, 0);
            }

        }, 500);
    }
    private void setListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplacePhoneActivity.this.finish();
            }
        });

        /**
         * 语音电话
         */
        findViewById(R.id.tv_yuyin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "voiceVerificate");
                if (ContentUtil.isMobileNO(et_phone.getText().toString())) {
                    HttpUtils.getResultToHandler(INSTANCE, "sms", "code", params1(), handler, 3);
                    replace_yuyin_rl.setVisibility(View.VISIBLE);
                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入有效的手机号码", false);
                    dialog.show();
                }
            }
        });

        //发送验证码监听事件
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "getVerificationCode");
                if (ContentUtil.isMobileNO(et_phone.getText().toString())) { //满足11位并格式对
                    //  HttpUtils.getResultToHandler(LoginActivity.this, "sms", "code", params1(), handler, 3);
                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入有效的手机号码", false);
                    dialog.show();
                }
                code.setBackgroundColor(0xffE52E5E);
                code.setTextColor(0xffffffff);
                code.setEnabled(false);
                thread = null;
                thread = new timeThread();
                thread.start();
                MobclickAgent.onEvent(getApplicationContext(), "2_getConfirmCode");
                MobclickAgent.onEvent(INSTANCE, "getConfirmCode");
                HttpUtils.getResultToHandler(INSTANCE, "sms", "code", params(), handler, 3);
            }
        });

        /**
         * 更换手机号
         */
            login_rl_02.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = et_phone.getText().toString();//得到用户输入的手机号
                    password = et_getcode.getText().toString();//得到用户输入的验证码
                    if (!ContentUtil.isMobileNO(username)) {
                        Dialog dialog = DialogUtil.show(ReplacePhoneActivity.this, "请输入有效的手机号码", false);
                        dialog.show();
                    }else if (password.length() == 4) {
                        final Dialog dialog=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                        dialog.show();
                        MobclickAgent.onEvent(ReplacePhoneActivity.this, "replace");
                        Call<StatusBean> call = HttpServiceClient.getInstance().user_updatemobile(MyApplication.token, password,username);
                        call.enqueue(new Callback<StatusBean>() {
                            @Override
                            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                dialog.dismiss();
                                if (response.isSuccessful()) {

                                    if ("ok".equals(response.body().getStatus())) {
                                        ExclusiveYeUtils.onMyEvent(INSTANCE, "changeMobileAction");
                                        ContentUtil.makeToast(ReplacePhoneActivity.this, "更换成功！");
                                        finish();
                                    } else {
                                        ContentUtil.makeToast(ReplacePhoneActivity.this, response.body().getError().getMessage());
                                        ExclusiveYeUtils.isExtrude(INSTANCE, response.body().getError().getCode());
                                    }
                                } else {
                                    ContentUtil.makeToast(ReplacePhoneActivity.this, getString(R.string.loadding_error));
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusBean> call, Throwable t) {

                                dialog.dismiss();
                            }
                        });
                    }else {
                        Dialog dialog = DialogUtil.show(ReplacePhoneActivity.this, "请输入有效的验证码", false);
                        dialog.show();
                    }

                }
            });

    }

    private List<NameValuePair> params() {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobile", et_phone.getText().toString()));
        pairs.add(new BasicNameValuePair("login_type", "1"));
        return pairs;
    }

    private List<NameValuePair> params1() {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobile", et_phone.getText().toString()));
        pairs.add(new BasicNameValuePair("login_type", "1"));
        pairs.add(new BasicNameValuePair("type", "1"));
        pairs.add(new BasicNameValuePair("tip_type", "3"));
        return pairs;
    }

    private class timeThread extends Thread {
        @Override
        public void run() {
            for (int i = 30; i >= 0; i--) {
                try {
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
