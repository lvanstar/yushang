package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.RegisterBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.HttpUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    @InjectView(R.id.code_Rl)//语音拨打
            RelativeLayout code_rl;
    @InjectView(R.id.code_Vis)//弹出语音框
            RelativeLayout code_Vis;
    @InjectView(R.id.login_Tv01)
    TextView login01;
    @InjectView(R.id.login_Tv02)
    TextView login02;

    private EditText username_et;
    private EditText password_et;
    private String username;
    private String password;
    private TextView code;
    private TextView login_tv;
    private RelativeLayout login_02;
    private Handler handler;
    private timeThread thread;
    private boolean Status;
    private SharedPreferences sf;
    private RelativeLayout login;
    private RegisterActivity INSTANCE;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        INSTANCE = this;
        setViews();
        setListeners();
    }


    private void setViews() {
        login01.setText(getResources().getString(R.string.register));
        login02.setText(getResources().getString(R.string.register));

        username_et = (EditText) findViewById(R.id.login_username);
        password_et = (EditText) findViewById(R.id.login_password);
        code = (TextView) findViewById(R.id.login_code_02);
        login_tv = (TextView) findViewById(R.id.login_tv);
        login = (RelativeLayout) findViewById(R.id.login_rl_ok);
        login_02 = (RelativeLayout) findViewById(R.id.login_rl_off);

        String s = username_et.getText().toString();
        if (s != null && s.length() == 11) {
            code.setBackgroundColor(0xffE52E5E);
            code.setTextColor(0xffffffff);
            code.setEnabled(true);
        } else {
            code.setBackgroundColor(0xffd4d4d4);
            code.setTextColor(0xffb5b5b5);
            code.setEnabled(false);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) username_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(username_et, 0);
            }

        }, 500);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    int num = msg.arg1;
                    if (num > 0) {
                        code.setEnabled(false);
                        Status = true;
                        code.setBackgroundColor(0xffd4d4d4);
                        code.setTextColor(0xffb5b5b5);
                        code.setText(num + "秒后重发");
                    } else {
                        Status = false;
                        code.setEnabled(true);
                        code.setBackgroundColor(0xffE52E5E);
                        code.setTextColor(0xffffffff);
                        code.setText("重新获取");
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
    }

    private void setListeners() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 注册
         */
        login_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "registerAction");
                username=username_et.getText().toString();
                password=password_et.getText().toString();
                if(username.length()>0){
                    if(username.length()==11){
                        if(password.length()>0){
                            dialog=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                            dialog.show();
                            Call<RegisterBean>call= HttpServiceClient.getInstance().register(username,password);
                            call.enqueue(new Callback<RegisterBean>() {
                                @Override
                                public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {
                                    dialog.dismiss();
                                    if(response.isSuccessful()){

                                        if("ok".equals(response.body().getStatus())){

                                            Intent intent=new Intent(INSTANCE,PersonDataActivity.class);
                                            intent.putExtra(GlobalConsts.INTENT_DATA,response.body().getData().getId());
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            ContentUtil.makeToast(INSTANCE,response.body().getError().getMessage());
                                        }
                                    }else{
                                        ContentUtil.makeToast(INSTANCE,getString(R.string.loadding_error));
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterBean> call, Throwable t) {
                                    dialog.dismiss();
                                }
                            });
                        }else{
                            ContentUtil.makeToast(INSTANCE,getString(R.string.code));
                        }
                    }else{
                        ContentUtil.makeToast(INSTANCE,getString(R.string.phone2));
                    }
                }else{
                    ContentUtil.makeToast(INSTANCE,getString(R.string.phone01));
                }
            }
        });



        //输入账号时，监听事件
        username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Status && s != null && s.length() == 11 && "1".equals(username_et.getText().toString().substring(0, 1))) {
                    code.setBackgroundColor(0xffE52E5E);
                    code.setTextColor(0xffffffff);
                    code.setEnabled(true);
                    String t = password_et.getText().toString();
//                    if (t != null && t.length() > 0) {
//                        login.setVisibility(View.VISIBLE);
//                        login_02.setVisibility(View.GONE);
//                    } else {
//                        login.setVisibility(View.GONE);
//                        login_02.setVisibility(View.VISIBLE);
//                    }
                } else {
                    code.setBackgroundColor(0xffcccccc);
                    code.setTextColor(0xff999999);
                    code.setEnabled(false);
                    login.setVisibility(View.GONE);
                    login_02.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (username_et.length() == 11) {
                    password_et.requestFocus();
                } else {
                    username_et.clearFocus();
                }
//                if (password_et.length() < 6 && username_et.length() == 11) {
//                    login.setVisibility(View.VISIBLE);
//                    login_02.setVisibility(View.GONE);
//                } else {
//                    login.setVisibility(View.GONE);
//                    login_02.setVisibility(View.VISIBLE);
//                }
            }
        });

        //输入密码时的监听事件
//        password_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s != null && s.length() ==6) {
//                    login.setVisibility(View.VISIBLE);
//                    login_02.setVisibility(View.GONE);
//                } else {
//                    login.setVisibility(View.GONE);
//                    login_02.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        //发送验证码监听事件
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContentUtil.isMobileNO(username_et.getText().toString())) { //满足11位并格式对
                    //  HttpUtils.getResultToHandler(LoginActivity.this, "sms", "code", params1(), handler, 3);
                    Call<StatusBean> call=HttpServiceClient.getInstance().register_checkmobileisexists(username_et.getText().toString());
                    call.enqueue(new Callback<StatusBean>() {
                        @Override
                        public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                            if (response.isSuccessful()) {
                                if ("ok".equals(response.body().getStatus())) {
                                    code.setBackgroundColor(0xffE52E5E);
                                    code.setTextColor(0xffffffff);
                                    code.setEnabled(false);
                                    thread = null;
                                    thread = new timeThread();
                                    thread.start();
                                    MobclickAgent.onEvent(INSTANCE, "getConfirmCode");
                                    HttpUtils.getResultToHandler(INSTANCE, "sms", "code",

                                            params(), handler, 3);
                                }else {
                                    ContentUtil.makeToast(INSTANCE,response.body().getError().getMessage());
                                }
                            }else {

                            }
                        }

                        @Override
                        public void onFailure(Call<StatusBean> call, Throwable t) {

                        }
                    });

                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入有效的手机号码", false);
                    dialog.show();
                }



            }
        });


        code_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContentUtil.isMobileNO(username_et.getText().toString())) {
                    code.setEnabled(false);
                    thread = null;
                    thread = new timeThread();
                    thread.start();
                    HttpUtils.getResultToHandler(INSTANCE, "sms", "code", params1(), handler, 3);
                    code_Vis.setVisibility(View.VISIBLE);
                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入正确的手机号", true);
                    dialog.show();
                }

            }
        });
    }

    private List<NameValuePair> params() {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobile", username_et.getText().toString()));
        pairs.add(new BasicNameValuePair("login_type", "1"));
        return pairs;
    }

    private List<NameValuePair> params1() {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobile", username_et.getText().toString()));
        pairs.add(new BasicNameValuePair("login_type", "1"));
        pairs.add(new BasicNameValuePair("type", "1"));
        pairs.add(new BasicNameValuePair("tip_type", "1"));
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
