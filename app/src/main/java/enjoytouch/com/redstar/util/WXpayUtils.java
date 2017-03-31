package enjoytouch.com.redstar.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.WXPayBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2015/10/12.
 */
public class WXpayUtils {

    PayReq req;
    StringBuffer sb;
    Context context;
    public static WXPayBean.DataEntity bean;
    public static  String oerder;
    public static  String id;
    public static  String type;
    private Dialog dialog;

public WXpayUtils(Context context){
    this.context=context;
    dialog=DialogUtil.createLoadingDialog(context,context.getString(R.string.loading));
}


    public void pay(String id ,String order, final String type){

        req = new PayReq();
        sb=new StringBuffer();
        WXpayUtils.oerder=order;
        WXpayUtils.id=id;
        WXpayUtils.type=type;

        dialog.show();
        Call<WXPayBean>call=HttpServiceClient.getInstance().prepay(id,MyApplication.token,type);
        call.enqueue(new Callback<WXPayBean>() {
            @Override
            public void onResponse(Call<WXPayBean> call, Response<WXPayBean> response) {
                dialog.dismiss();
                if(response.isSuccessful()){

                    if("ok".equals(response.body().getStatus())){
                        bean=response.body().getData();
                        Intent intent=new Intent();
                        intent.putExtra(GlobalConsts.INTENT_DATA,"1");
                        intent.setAction(GlobalConsts.INTENT);
                        context.sendBroadcast(intent);
                        genPayReq();
                        sendPayReq();
                        ((Activity) context).finish();
                    }else{
                        ContentUtil.makeToast(context, response.body().getError().getMessage());
                    }
                }else{
                    ContentUtil.makeToast(context,context.getString(R.string.loadding_error));
                }
            }

            @Override
            public void onFailure(Call<WXPayBean> call, Throwable t) {

                dialog.dismiss();
            }
        });


    }


    private void sendPayReq() {
        MyApplication.wxapi.sendReq(req);
    }


    private void genPayReq() {

        req.appId = bean.appid;
        req.partnerId = bean.partnerid;
        req.prepayId = bean.prepayid;
        req.packageValue = "prepay_id="+bean.partnerid;
        req.nonceStr = bean.noncestr;
        req.timeStamp = bean.timestamp;


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n"+req.sign+"\n\n");


        Log.e("orion", signParams.toString());

    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        this.sb.append("sign str\n" + sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("orion",appSign);
        return appSign;
    }
}
