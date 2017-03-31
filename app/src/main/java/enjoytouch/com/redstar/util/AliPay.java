package enjoytouch.com.redstar.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;


import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.PayResultsActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.StatusBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bullytou on 2015/9/28.
 */
public class AliPay {

    private static String partner = "2088021812841236";
    private static String seller_id = "natasha@yushang001.com";
    private static String app_id = "2015092500326252";
    private static String secret = "3be40a0d729c47a181db70412f415796";
    private static String privateKey = "MIICXQIBAAKBgQDmUVToRHGkKqS9IM1ZER4Bg+WYL1Z2k2rZCGCA31l5JF3TZl7OSOZPmxoGYXgqldlJlrzEJ8QRTwMy3hT7z+ckiGDT7gcd5Fpe9+9V88/biRTJ883b1DpUSGxpNjou977Zk5EGcUsv7cXkp1al3Gth7fYr3xaIbcV1YVs7bvJaywIDAQABAoGBAKqqa+NtDFSc8NPgoam1QGnZBeHeehCFrnBCf9gfKRrWctw6iFgNtvQYN5Gw2i2cOpYIJ7qmyOgWh4VDAYOTDHAi6BmJI3GIdw21z7RMbJYm5IxoUFK+sv62P3dvRY8hcivSr4r3U/WJy1IONRRgAGoQwh3JlNr0C/iONYvmSp+pAkEA9W0ggnrB4Rbo6urPL658fDOvV1Rca0yeGPCSqj5OU+Kolmr9xj6lEx1R+QNlHtifTLq9yCIwtp+DSK6uAAElxQJBAPA9kW74N1LVcaZdD4zoHU01g9NtGUukNtXR3uSaaRp7sJd0PmQGw/o5mRwCN14LUbabbS8/V79gn/h/CamXF08CQQDRSdkSS7qvx0iio9BAugwgIjchQqh5O+IKJIT9tpo3AK+BbgWxG9TLYxJ3RkTiNBpMZWbXlwhxg7+BqKxrBo99AkBcKAIGA4mVaPNz0fcJInE7EPBExnERpyix1RQftWvkENapApp8XGwJLNci+2ap2MW7utujaDUM4M/8zx6xw5fZAkBMbl0UCQ77MhnbczTdq+c9D+b2gR+aGLo2AVpDg8r6jrbG3rOT6g7eI019RtOU6+h6KeTZ+jolMxFY1tenbyUB";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static String notify_url = GlobalConsts.URI + "product/aplipay_notify";
    private static String service = "mobile.securitypay.pay";
    private static String payment_type = "1";
    private static String charset = "utf-8";
    private static String pay_time = "30m";
    private static String sign_type = "RSA";
    public static String nos;
    public static String id;
    public static String price;
    private static Context context;

    public AliPay(Context context) {
        this.context = context;

    }

    public void pay(final Activity activity, final String subject, final String body, final String price, final String nos, final String id) {
        AliPay.nos = nos;
        AliPay.id = id;
        AliPay.price = price;
//        new Thread(){
//            @Override
//            public void run() {
//                PayTask payTask = new PayTask(activity);
//                String result = payTask.pay(getOrderInfo(subject, body, "0.01"));
//                Log.i("Alipay", result);
//                Result result1=new Result(result);
//                if(TextUtils.equals(result1.resultStatus,"9000")){
//                    String results = result1.result;
//                    int index1=results.indexOf("out_trade_no")+13;
//                    int index2=results.indexOf("&", index1);
//                    String values = "";
//                    if (index2 != -1)
//                        values = results.substring(index1, index2);
//                    else
//                        values = results.substring(index1);
//                    ContentUtil.makeLog("���׺�", values);
//                    new PayControl(activity.getApplicationContext()).pay_finish(MyApplication.cityId,values,MyApplication.bean.token,true);
//                }
//            }
//        }.start();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(activity);
                String result = payTask.pay(getOrderInfo(subject, body, price));
                Message msg = new Message();        //新建消息对象
                msg.what = 1;                       //给对象中的what赋值1
                msg.obj = result;                   //给对象中的obj赋值为result
                mHandler.sendMessage(msg);          //发送消息
            }
        };
        Thread payThread = new Thread(runnable);    //新建工作线程，将上面的任务传入到此工作线程执行
        payThread.start();                          //线程开始执行
    }


    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    final Result resultObj = new Result((String) msg.obj);
                    ContentUtil.makeLog("lzz", "pay:" + (String) msg.obj + "======result:" + resultObj);
                    if (TextUtils.equals(resultObj.resultStatus, "9000")) {

                        final Dialog dialog=DialogUtil.createLoadingDialog(context,context.getString(R.string.loading));
                        dialog.show();
                        Call<StatusBean>call=HttpServiceClient.getInstance().pay_finish(MyApplication.token,id);
                        call.enqueue(new retrofit2.Callback<StatusBean>() {
                            @Override
                            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                dialog.dismiss();
                                if(response.isSuccessful()){

                                    if("ok".equals(response.body().getStatus())){
                                        Intent intent = new Intent(context, PayResultsActivity.class);
                                        intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{"1", nos, resultObj.memo});
                                        context.startActivity(intent);
                                        ((Activity) context).finish();
                                    }else{
                                        ContentUtil.makeToast(context, response.body().getError().getMessage());
                                    }
                                }else{
                                    ContentUtil.makeToast(context,context.getString(R.string.loadding_error));
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusBean> call, Throwable t) {

                                dialog.dismiss();
                            }
                        });
                    } else if (TextUtils.equals(resultObj.resultStatus, "8000")) {
                        Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();
                    } else {
                            Intent intent = new Intent(context, PayResultsActivity.class);
                            intent.putExtra(GlobalConsts.INTENT_DATA, new String[]{"2", nos, resultObj.memo});
                            context.startActivity(intent);
                            ((Activity) context).finish();
                    }
                    break;
            }

        }

    };

    private static String getOrderInfo(String subject, String body, String price) {//得到订单信息
        StringBuilder builder = new StringBuilder();
        builder.append("partner").append("=\"").append(partner).append("\"");
        builder.append("&");
        builder.append("seller_id").append("=\"").append(seller_id).append("\"");
        builder.append("&");
        builder.append("out_trade_no").append("=\"").append(nos).append("\"");
        builder.append("&");
        builder.append("subject").append("=\"").append(subject).append("\"");
        builder.append("&");
        builder.append("body").append("=\"").append(body).append("\"");
        builder.append("&");
        if (!price.contains(".")) price += ".00";
        builder.append("total_fee").append("=\"").append(price).append("\"");
        builder.append("&");
        builder.append("notify_url").append("=\"").append(notify_url).append("\"");
        builder.append("&");
        builder.append("service").append("=\"").append(service).append("\"");
        builder.append("&");
        builder.append("payment_type").append("=\"").append(payment_type).append("\"");
        builder.append("&");
        builder.append("_input_charset").append("=\"").append(charset).append("\"");
        builder.append("&");
        builder.append("it_b_pay").append("=\"").append(pay_time).append("\"");
        String info = builder.toString();
        builder.append("&");
        String sign = "";
        try {
            sign = URLEncoder.encode(getSign(info), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        builder.append("sign").append("=\"").append(sign).append("\"");
        builder.append("&");
        builder.append("sign_type").append("=\"").append(sign_type).append("\"");
//        builder.append("&");
        Log.i("PayInfo", builder.toString());
        return builder.toString();
    }

    public String getOutTradeNo() {
        return nos;
    }

    public static String getSign(String info) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    AlipayBase64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(sign_type, "BC");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA1WithRSA");

            signature.initSign(priKey);
            signature.update(info.getBytes(charset));

            byte[] signed = signature.sign();

            return AlipayBase64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
