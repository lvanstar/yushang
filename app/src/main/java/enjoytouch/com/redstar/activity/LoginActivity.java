package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.SinaBean;
import enjoytouch.com.redstar.bean.UserBean;
import enjoytouch.com.redstar.control.LoginControl;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.HttpUtils;
import enjoytouch.com.redstar.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录界面
 * duan
 */
public class LoginActivity extends Activity {


    private EditText username_et;//输入手机号码
    private EditText password_et;//输入验证码
    private String username;
    private String password;
    private TextView code;    //获取验证码
    private RelativeLayout login_02; //灰色登录
    private Handler handler;
    private timeThread thread;
    private boolean Status;
    private SharedPreferences sf;
    public static boolean fromOther = false;
    private TextView back;
    private RelativeLayout login;
    private LoginActivity INSTANCE;
    private Dialog dialog;
    private UMShareAPI umShareAPI;

    @InjectView(R.id.register)//注册
    TextView register;//注册
    @InjectView(R.id.code_Rl)
    RelativeLayout code_rl;//收不到短信，用语音验证
    @InjectView(R.id.code_Vis)
    RelativeLayout code_Vis;   //正在拨打电话，请留意电话一栏
    @InjectView(R.id.login_title)
    TextView title;
    @InjectView(R.id.login_other_ll)
    LinearLayout login_ll;
    @InjectView(R.id.login_qq)
    RelativeLayout qq;
    @InjectView(R.id.login_weixin)
    RelativeLayout weixin;
    @InjectView(R.id.login_weibo)
    RelativeLayout weibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
        INSTANCE = this;

        sf = MyApplication.sf;

        setViews();
        setListeners();
    }


    private void setViews() {

        umShareAPI=UMShareAPI.get(INSTANCE);
        back = (TextView) findViewById(R.id.cancel);
        username_et = (EditText) findViewById(R.id.login_username);//输入手机号码
        password_et = (EditText) findViewById(R.id.login_password);//输入验证码
        code = (TextView) findViewById(R.id.login_code_02);  //获取验证码
        login = (RelativeLayout) findViewById(R.id.login_rl_ok);  //蓝色登录
        login_02 = (RelativeLayout) findViewById(R.id.login_rl_off);  //灰色登录
        String s = username_et.getText().toString();
        if (s != null && s.length() == 11) {
            code.setBackgroundColor(0xffE52E5E);//红色
            code.setTextColor(0xffffffff);//白色
            code.setEnabled(true);
        } else {
            code.setBackgroundColor(0xffd4d4d4);//灰色
            code.setTextColor(0xffb5b5b5);//灰亮色
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

    }


    private void setListeners() {
        back.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {    //注册
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
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
                if (username_et.length() == 11) {
                    password_et.requestFocus();
                } else {
                    username_et.clearFocus();
                }
                if (password_et.length() == 4 && username_et.length() == 11) {
                    login.setVisibility(View.VISIBLE);
                    login_02.setVisibility(View.GONE);
                } else {
                    login.setVisibility(View.GONE);
                    login_02.setVisibility(View.VISIBLE);
                }
            }
        });

        //输入密码时的监听事件
        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    login.setVisibility(View.VISIBLE);
                    login_02.setVisibility(View.GONE);
                } else {
                    login.setVisibility(View.GONE);
                    login_02.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password_et != null && password_et.length() < 4) {
//                    password_et.requestFocus();
                } else {
//                    password_et.clearFocus();
                }


            }
        });

        //发送验证码监听事件
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "getVerificationCode");
                if (ContentUtil.isMobileNO(username_et.getText().toString())) { //满足11位并格式对
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
                MobclickAgent.onEvent(LoginActivity.this, "getConfirmCode");
                HttpUtils.getResultToHandler(LoginActivity.this, "sms", "code", params(), handler, 3);
            }
        });

        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "login");
                username = username_et.getText().toString();//得到用户输入的手机号
                password = password_et.getText().toString();//得到用户输入的验证码
                login_02.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                if (!ContentUtil.isMobileNO(username)) {
                    Dialog dialog = DialogUtil.show(LoginActivity.this, "请输入有效的手机号码", false);
                    dialog.show();
                    login_02.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                } else {
                    MobclickAgent.onEvent(LoginActivity.this, "login");

                    final Dialog dialog=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                    dialog.show();
                    Call<UserBean>call= HttpServiceClient.getInstance().login(username,password);
                    call.enqueue(new Callback<UserBean>() {
                        @Override
                        public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                            if (response.isSuccessful()) {

                                dialog.dismiss();
                                login_02.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                if ("ok".equals(response.body().getStatus())) {
                                    UserBean.DataBean bean=response.body().getData();
                                    new LoginControl(INSTANCE).setUserInfo(bean);
                                } else {
                                    ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                }
                            } else {
                                ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<UserBean> call, Throwable t) {
                            login_02.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    });
                }


            }
        });

        code_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "voiceVerificate");
                if (ContentUtil.isMobileNO(username_et.getText().toString())) {
                    HttpUtils.getResultToHandler(LoginActivity.this, "sms", "code", params1(), handler, 3);
                    code_Vis.setVisibility(View.VISIBLE);
                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入有效的手机号码", false);
                    dialog.show();
                }

            }
        });

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "threeLogin");
                Context context = v.getContext();
                Class<?> cls = null;
                qqlogin();
                if (cls != null) {
                    Intent intent = new Intent(context, cls);
                    context.startActivity(intent);
                }
            }
        });

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "threeLogin");
                xinlang();
            }
        });

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "threeLogin");
                wxlogin();
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
        pairs.add(new BasicNameValuePair("tip_type", "2"));
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

    public void onResume() {
        super.onResume();
        code_Vis.setVisibility(View.INVISIBLE);
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void wxlogin(){     //微信的三方登录

        umShareAPI.doOauthVerify(INSTANCE, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                umShareAPI.getPlatformInfo(INSTANCE, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> info) {
                        new LoginControl(INSTANCE).third_login((String) info.get("openid"), "2", (String) info.get("headimgurl"), (String) info.get("nickname"), (String) info.get("sex"));
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Toast.makeText(INSTANCE, "获取平台数据错误...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Toast.makeText(INSTANCE, "获取平台数据取消...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(INSTANCE, "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(INSTANCE, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void qqlogin(){     //QQ第三方登录

        umShareAPI.doOauthVerify(INSTANCE, SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                umShareAPI.getPlatformInfo(INSTANCE, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> info) {
                        new LoginControl(INSTANCE).third_login((String) info.get("openid"), "3", (String) info.get("profile_image_url"), (String) info.get("screen_name"), "男".equals((String) info.get("gender")) ? "1" : "2");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Toast.makeText(INSTANCE, "获取平台数据错误...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Toast.makeText(INSTANCE, "获取平台数据取消...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(INSTANCE, "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(INSTANCE, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void xinlang(){     //新浪微博第三方登录

        umShareAPI.doOauthVerify(INSTANCE, SHARE_MEDIA.SINA, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                ContentUtil.makeLog("lzz","map:"+map);
                umShareAPI.getPlatformInfo(INSTANCE, SHARE_MEDIA.SINA, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Gson gson=new Gson();
                        SinaBean bean= gson.fromJson(map.get("result"), SinaBean.class);
                        ContentUtil.makeLog("lzz","info:"+bean);
                        new LoginControl(INSTANCE).third_login(bean.id,"1",bean.profile_image_url,bean.name,"m".equals(bean.gender)?"1":"2");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Toast.makeText(INSTANCE, "获取平台数据错误...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Toast.makeText(INSTANCE, "获取平台数据取消...", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(INSTANCE, "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(INSTANCE, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode,resultCode,data);
    }





}
