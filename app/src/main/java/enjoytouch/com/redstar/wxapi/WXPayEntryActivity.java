package enjoytouch.com.redstar.wxapi;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import enjoytouch.com.redstar.R;

import enjoytouch.com.redstar.activity.PayResultsActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.control.LoginControl;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.WXpayUtils;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2015/8/11.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static String type;
    private WXPayEntryActivity INSTANCE;
    private IWXAPI wxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wxpay);
        INSTANCE = this;
        wxapi = WXAPIFactory.createWXAPI(getApplicationContext(), GlobalConsts.WX_APP_ID,
                true);
        wxapi.registerApp(GlobalConsts.WX_APP_ID);
        ContentUtil.makeLog("lzz", "1111111111111111111111111111");
        wxapi.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.wxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {


    }

    @Override
    public void onResp(BaseResp baseResp) {


        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                ContentUtil.makeLog("lzz", "code:" + baseResp.getType() + "====message:" + baseResp.errStr);
                if (1 == baseResp.getType()) {
                    wxCode = ((SendAuth.Resp) baseResp).code;
                    what = 0;
                    new Thread(twx).start();
                } else if (5 == baseResp.getType()) {
                    if ("1".equals(type)) {

                        final Dialog dialog=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                        dialog.show();
                        final Call<StatusBean> call= HttpServiceClient.getInstance().pay_finish(MyApplication.token,WXpayUtils.id);
                        call.enqueue(new retrofit2.Callback<StatusBean>() {
                            @Override
                            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                dialog.dismiss();
                                if(response.isSuccessful()){

                                    if("ok".equals(response.body().getStatus())){
                                        Intent intent = new Intent(INSTANCE, PayResultsActivity.class);
                                        intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{"1", WXpayUtils.oerder});
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                    }
                                }else{
                                    ContentUtil.makeToast(INSTANCE,getString(R.string.loadding_error));
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusBean> call, Throwable t) {

                                dialog.dismiss();
                            }
                        });
                        //微信支付
//                        new HotControl(INSTANCE).hot_finish(MyApplication.token, "1", WXpayUtils.id, new HotControl.PayWayCallBack() {
//                            @Override
//                            public void pay(String status) {
//
//                                if ("ok".equals(status)) {
//                                    Intent intent = new Intent(INSTANCE, PayResultsActivity.class);
//                                    intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{"1", WXpayUtils.id, ""});
//                                    INSTANCE.startActivity(intent);
//                                } else {
//                                    Dialog dialog = DialogUtil.show(INSTANCE, "支付失败", true);
//                                    dialog.show();
//                                }
//                            }
//                        });
                    }
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (5 == baseResp.getType()) {
                    if ("1".equals(type)) {
                        //微信支付取消
                        Intent intent = new Intent(INSTANCE, PayResultsActivity.class);
                        intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{"2", WXpayUtils.id, "操作已取消"});
                        INSTANCE.startActivity(intent);
                        finish();
                    } else if ("2".equals(type)) {
                    }else if("4".equals(type)){
                        //定金微信支付取消
                        Intent intent = new Intent(INSTANCE, PayResultsActivity.class);
                        intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{"2", WXpayUtils.id, "操作已取消"});
                        INSTANCE.startActivity(intent);
                        finish();
                    }else if("3".equals(type)){
                        //优惠券支付取消
                        Dialog dialog = DialogUtil.show(INSTANCE, "支付失败", true);
                        dialog.show();
                    }

                }
                ContentUtil.makeLog("lzz", "code:" + baseResp.getType() + "====cancel message:" + baseResp.errStr);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ContentUtil.makeLog("lzz", "" + baseResp.errCode);
                finish();
                break;
            default:
                break;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            ContentUtil.makeLog("lzz","回调type:"+type);
//            finish();
//            if("1".equals(type)){
//                new HotControl(INSTANCE).hot_finish(MyApplication.bean.token, "1", WXpayUtils.id, new HotControl.PayWayCallBack() {
//                    @Override
//                    public void pay(String status) {
//
//                        if ("ok".equals(status)) {
//                            Intent intent=new Intent(INSTANCE, PayResultsActivity.class);
//                            intent.putExtra(GlobalConsts.INTENT_DATA,new String[]{"1",WXpayUtils.id});
//                            INSTANCE.startActivity(intent);
//                        } else {
//                            Intent intent=new Intent(INSTANCE, PayResultsActivity.class);
//                            intent.putExtra(GlobalConsts.INTENT_DATA,new String[]{"2",WXpayUtils.id});
//                            INSTANCE.startActivity(intent);
//                        }
//                    }
//                });
//            }
////            new PayControl(WXPayEntryActivity.this).pay_finish(MyApplication.cityId, WXpayUtils.id, MyApplication.bean.token, false);
        }
    }


    private String wxCode;
    private String access_token;
    private String openid;
    private String nickname;
    private String pic;
    private int what;
    private String sex="0";

    private Runnable twx = new Runnable() {
        public void run() {
            StringBuffer res = new StringBuffer();
            InputStream in;
            try {
                URL url = new URL(getUrl());
                HttpsURLConnection c = (HttpsURLConnection) url.openConnection();

                in = c.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                char[] buffer = new char[8192];
                int n = 0;
                while ((n = reader.read(buffer)) > 0) {
                    res.append(buffer, 0, n);
                }
                Message msg = new Message();
                msg.what = what;
                msg.obj = res;
                Log.i("weixin", res.toString());
                resultHandler.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }

        }

        ;
    };

    private Handler resultHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                try {
                    JSONObject jo = new JSONObject(msg.obj.toString());
                    Log.i("lzz", jo.toString());
                    access_token = jo.getString("access_token");
                    openid = jo.getString("openid");
//                    MyApplication.sf.edit().putString("openid", openid).commit();
                    what = 1;
                    new Thread(twx).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
            } else if (msg.what == 1) {
                try {
                    JSONObject jo = new JSONObject(msg.obj.toString());
                    nickname = jo.getString("nickname");
                    pic = jo.getString("headimgurl");
                    sex=String.valueOf(jo.getInt("sex"));
                    new LoginControl(INSTANCE).third_login(openid, "2", pic, nickname, sex);
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
            }
        }

        ;
    };

    private String getUrl() {
        if (what == 0)
            return "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + GlobalConsts.WX_APP_ID + "&secret=" + GlobalConsts.WX_APP_SECRET + "&code=" + wxCode + "&grant_type=authorization_code";
        else if (what == 1)
            return "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
        else return null;
    }

    public static class BroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            type = intent.getStringExtra(GlobalConsts.INTENT_DATA);
            ContentUtil.makeLog("lzz", "type:" + type);
        }
    }

}
