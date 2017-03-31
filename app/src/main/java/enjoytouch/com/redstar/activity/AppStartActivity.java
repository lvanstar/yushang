package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;

/**
 * Created by Administrator on 2015/6/29.
 */
public class AppStartActivity extends Activity {

    private SharedPreferences sf;
    private static Double latitude;
    private static Double longitude;
//    private boolean status = false;
    public static AppStartActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);

        INSTANCE=this;

        /**
         * 1.首先判断APP是否联网
         * 2.判断是否开启GPS定位
         */
        if (isNetworkAvailable(this)) {
            MyApplication.getLatestVersion(this);
            if (isOpen()==false) {
                show(this, "GPS未开启", "2");
                return;
            }
        } else {
            show(this, "网络连接失败", "1");

        }
        //gotoMain();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void gotoMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Log.i("lzz", "是否登录：" + MyApplication.isLogin + "是否为第一次：" + MyApplication.first);
                if (MyApplication.first) {
                    if (MyApplication.isLogin) {
                        Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
//                    MyApplication.bean = new Gson().fromJson(MyApplication.sf.getString("User", null), UserBean.class);
//                    Log.i("user", MyApplication.sf.getString("User", null));
//                    intent.putExtra("status", status);
                        startActivity(intent);
                        AppStartActivity.this.finish();
                    } else {
/*                        Intent intent = new Intent(AppStartActivity.this, LoginActivity.class);
//                    intent.putExtra("status", status);
                        intent.putExtra("from", false);*/
                        Intent intent = new Intent(AppStartActivity.this, MainActivity.class);
                        startActivity(intent);
                        AppStartActivity.this.finish();
                    }
                } else {
                    Intent intent = new Intent(AppStartActivity.this, WelcomeActivity.class);
//                    intent.putExtra("status", status);
                    startActivity(intent);
                    AppStartActivity.this.finish();
                }
            }
        }, 500);
    }

    /**
     * 判断是否连接网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //如果仅仅是用来判断网络连接
        if (cm == null) {

        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }


    /**
     * Dialog显示
     *
     * @param context
     */
    private void show(Context context, String content, final String type) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.money_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.money_dialog_ll);
        TextView title = (TextView) v.findViewById(R.id.money_dialog_text1);
        title.setText("提示");
        TextView number = (TextView) v.findViewById(R.id.moeny_number_tv);
        number.setText(content);
        TextView username = (TextView) v.findViewById(R.id.moeny_username_tv);
        username.setText("");
        TextView ok = (TextView) v.findViewById(R.id.confirm_tv);
        TextView canel = (TextView) v.findViewById(R.id.cancel_tv);
        ok.setText("确认");
        final Dialog loadingDialog = new Dialog(context, R.style.registDialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.dismiss();
                if ("1".equals(type)) {
                    startActivity(new Intent(Settings.
                            ACTION_DATA_ROAMING_SETTINGS));
                } else if ("2".equals(type)) {
                    startActivity(new Intent(Settings.
                            ACTION_LOCATION_SOURCE_SETTINGS));
                }

            }
        });

        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.dismiss();
                AppStartActivity.this.finish();
            }
        });
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public static boolean isOpen() {

        WifiManager wifiManager = (WifiManager)INSTANCE.getSystemService(Context.WIFI_SERVICE);
        LocationManager locationManager =
                ((LocationManager)INSTANCE.getSystemService(Context.LOCATION_SERVICE));
        return wifiManager.isWifiEnabled()||locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }



}