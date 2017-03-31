package enjoytouch.com.redstar.control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BandPhoneActivity;
import enjoytouch.com.redstar.activity.MainActivity;
import enjoytouch.com.redstar.activity.PersonMessageActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.UserBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2015/7/30.
 */
public class LoginControl {

    private Activity context;
    private Dialog dialog;
    private SharedPreferences sf;

    public LoginControl(Activity context) {
        this.context = context;
        sf = MyApplication.sf;
    }


    public void third_login(final String open_id, final String type, final String head_img, final String nickname, final String sex) {
        dialog = DialogUtil.createLoadingDialog(context, context.getString(R.string.loading));
        dialog.show();
        Call<UserBean> call = HttpServiceClient.getInstance().third_login(open_id, type);
        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {

                    if ("ok".equals(response.body().getStatus())) {
                        UserBean.DataBean bean = response.body().getData();
                        setUserInfo(bean);
                    } else {
                        if ("1039".equals(response.body().getError().getCode())) {
                            // 需要绑定手机
                            Intent intent = new Intent(context, BandPhoneActivity.class);
                            intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{open_id,type,nickname, sex, head_img});
                            context.startActivity(intent);
                        } else {
                            ContentUtil.makeToast(context, response.body().getError().getMessage());
                            ExclusiveYeUtils.isExtrude(context,response.body().getError().getCode());
                        }
                    }
                } else {
                    ContentUtil.makeToast(context, context.getString(R.string.loadding_error));
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {

                dialog.dismiss();
            }
        });
    }

    public void uPData(final String type, final String value) {
        Call<UserBean> call1 = HttpServiceClient.getInstance().user_updateinfo(MyApplication.token, type, value);
        call1.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                setUserInfos(response.body().getData());
                if ("2".equals(type)) {
                    Intent intent = new Intent(context, PersonMessageActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA, value);
                    ((Activity)context).setResult(2, intent);
                    ((Activity)context).finish();
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {

            }
        });
    }

    public void setUserInfo(UserBean.DataBean bean) {

        sf.edit().putBoolean(GlobalConsts.ISLOGIN, true).commit();
        MyApplication.isLogin = true;
        MyApplication.user_id = bean.id;
        MyApplication.token = bean.token;
        MyApplication.mobile = bean.mobile;
        MyApplication.nickname = bean.nickname;
        MyApplication.head_img = bean.head_img;
        MyApplication.sex = bean.sex;
        sf.edit().putString(GlobalConsts.CONSTANT_USER_ID, bean.id).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_TOKEN, bean.token).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_MOBLIE, bean.mobile).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_NICKNAME, bean.nickname).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_HEAD, bean.head_img).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_SEX, bean.sex).commit();
        Dialog dialog = DialogUtil.show(context, "登录成功", false);
        dialog.show();
//        Intent intent = new Intent(context, MainActivity.class);
//        context.startActivity(intent);
        ((Activity) context).finish();
    }


    public void setUserInfos(UserBean.DataBean bean) {

        MyApplication.user_id = bean.id;
        MyApplication.token = bean.token;
        MyApplication.mobile = bean.mobile;
        MyApplication.nickname = bean.nickname;
        MyApplication.head_img = bean.head_img;
        MyApplication.sex = bean.sex;
        sf.edit().putString(GlobalConsts.CONSTANT_USER_ID, bean.id).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_TOKEN, bean.token).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_MOBLIE, bean.mobile).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_NICKNAME, bean.nickname).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_HEAD, bean.head_img).commit();
        sf.edit().putString(GlobalConsts.CONSTANT_SEX, bean.sex).commit();
    }

}
