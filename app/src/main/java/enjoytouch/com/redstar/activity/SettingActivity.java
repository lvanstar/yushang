package enjoytouch.com.redstar.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 设置
 * duan
 */
public class SettingActivity extends SlideFinishActivity {

    private RelativeLayout feedback_rl;
    private RelativeLayout score_rl;
    private RelativeLayout adout_rl;
    private TextView setting_out;
    private View back;
    private MiddleDialog dialog;

    private SharedPreferences sf;
    private SettingActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        INSTANCE = this;
        sf = MyApplication.sf;
        setViews();
        setListeners();
    }


    private void setViews() {

        back = findViewById(R.id.back);
        feedback_rl = (RelativeLayout) findViewById(R.id.feedback_rl);
        score_rl = (RelativeLayout) findViewById(R.id.score_rl);
        adout_rl = (RelativeLayout) findViewById(R.id.adout_rl);
        //setting_out = (RelativeLayout) findViewById(R.id.setting_out);
        setting_out = (TextView) findViewById(R.id.feedback_send);
        dialog = new MiddleDialog(SettingActivity.this, "确定退出登录?\n", "","取消","确定", 0, new MiddleDialog.onButtonCLickListener() {
            @Override
            public void onActivieButtonClick(Object bean, int position) {
                final Dialog dialog = DialogUtil.createLoadingDialog(SettingActivity.this, getResources().getString(R.string.loading));
                dialog.show();
                Call<StatusBean> call = HttpServiceClient.getInstance().loginout(MyApplication.token);
                call.enqueue(new Callback<StatusBean>() {
                    @Override
                    public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().toString().equals("ok")) {
                                ExclusiveYeUtils.onMyEvent(INSTANCE, "exitLoginAction");
                                MyApplication.isLogin = false;
                                sf.edit().putBoolean(GlobalConsts.ISLOGIN, false).commit();
                                MainActivity.logout();
                                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Dialog dialog = DialogUtil.show(SettingActivity.this, response.body().getError().getMessage().toString(), false);
                                dialog.show();ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                            }
                        } else {
                            ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusBean> call, Throwable t) {

                        dialog.dismiss();
                    }
                });

            }
        }, R.style.registDialog);
    }

    private void setListeners() {

        //退出APP

        setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });

        //返回上一页

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        //意见反馈
        feedback_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingActivity.this, FeedBackActivity.class);
                startActivity(intent);
            }
        });

        //联系我们
        score_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "contactUs");
                Intent intent = new Intent(SettingActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        //关于
        adout_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "lookAbout");
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }


//    public void show(Context context){
//        WindowManager.LayoutParams lp = SettingActivity.this.getWindow()
//                .getAttributes();
//        lp.alpha = 0.5f;
//        SettingActivity.this.getWindow().setAttributes(lp);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.customdialog, null);// 得到加载view
//        LinearLayout layout = (LinearLayout) v.findViewById(R.id.custom_ll);
//        TextView ok=(TextView)v.findViewById(R.id.confirm_btn);
//        TextView canel=(TextView)v.findViewById(R.id.cancel_btn);
//        final Dialog loadingDialog = new Dialog(context, R.style.loading_dialogs);// 创建自定义样式dialog
//        loadingDialog.setCancelable(true);
//        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
//        loadingDialog.show();
//
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingDialog.dismiss();
//                sf.edit().putBoolean(GlobalConsts.ISLOGIN, false).commit();
//                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
//                startActivity(intent);
//                MainActivity.logout();
//                finish();
//            }
//        });
//
//        canel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingDialog.dismiss();
//
//            }
//        });
//
//        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                WindowManager.LayoutParams lp = SettingActivity.this
//                        .getWindow().getAttributes();
//                lp.alpha = 1.0f;
//                SettingActivity.this.getWindow().setAttributes(lp);
//            }
//        });
//    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
