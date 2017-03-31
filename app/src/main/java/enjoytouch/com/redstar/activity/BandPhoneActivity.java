package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.UserBean;
import enjoytouch.com.redstar.control.LoginControl;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.HttpUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/1/11.
 * <p/>
 * 绑定手机号
 */
public class BandPhoneActivity extends Activity {

    private BandPhoneActivity INSTANCE;

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
    public static boolean fromOther = false;
    private RelativeLayout login;
    private Dialog dialog;

    private String[]datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_phone);
        ButterKnife.inject(this);
        INSTANCE = this;

        datas=getIntent().getStringArrayExtra(GlobalConsts.INTENT_DATA);
        setViews();
        setListeners();
    }


    private void setViews() {
        login01.setText(getResources().getString(R.string.band));
        login02.setText(getResources().getString(R.string.band));

       // login = (RelativeLayout) findViewById(R.id.login_rl_02);//登录按钮
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
//        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        //登录
        login_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_et.getText().toString();        //得到用户输入的手机号
                password = password_et.getText().toString();        //得到用户输入的验证码
                if (!ContentUtil.isMobileNO(username)) {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入有效的手机号", false);
                    dialog.show();
                } else {
                    if(password.length()==6){

                        dialog=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                        dialog.show();
                        Call<UserBean>call= HttpServiceClient.getInstance().login_bind(datas[0],datas[1],username,password,datas[2],datas[3],datas[4]);
                        call.enqueue(new Callback<UserBean>() {
                            @Override
                            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                                if (response.isSuccessful()) {

                                    dialog.dismiss();
                                    if ("ok".equals(response.body().getStatus())) {
                                        UserBean.DataBean bean = response.body().getData();
                                        new LoginControl(INSTANCE).setUserInfo(bean);
                                    } else {
                                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                                    }
                                } else {
                                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                                }
                            }

                            @Override
                            public void onFailure(Call<UserBean> call, Throwable t) {

                                dialog.dismiss();
                            }
                        });
                    }else{
                        Dialog dialog = DialogUtil.show(INSTANCE, "请输入有效的验证码", false);
                        dialog.show();
                    }
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
                    if (t != null && t.length() > 0) {
                        login.setVisibility(View.VISIBLE);
                        login_02.setVisibility(View.GONE);
                    } else {
                        login.setVisibility(View.GONE);
                        login_02.setVisibility(View.VISIBLE);
                    }
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
//                if (username_et.length() == 11) {
//                    password_et.requestFocus();
//                } else {
//                    username_et.clearFocus();
//                }
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
//                if (password_et.length() == 6) {
//                } else {
//                    password_et.clearFocus();
//                }
//            }
//        });

        //发送验证码监听事件
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code.setBackgroundColor(0xffE52E5E);
                code.setTextColor(0xffffffff);
                code.setEnabled(false);
                thread = null;
                thread = new timeThread();
                thread.start();
                MobclickAgent.onEvent(INSTANCE, "getConfirmCode");
                HttpUtils.getResultToHandler(INSTANCE, "sms", "code", params(), handler, 3);
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
        pairs.add(new BasicNameValuePair("tip_type", "4"));
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
