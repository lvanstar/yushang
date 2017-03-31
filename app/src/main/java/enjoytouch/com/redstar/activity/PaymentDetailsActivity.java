package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.OrderBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.selfview.PayDialog;
import enjoytouch.com.redstar.util.AliPay;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.WXpayUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 支付详情（）
 */
public class PaymentDetailsActivity extends Activity {
    private ImageView zhifubao_iv, weixin_iv;
    private PaymentDetailsActivity INSTANCE;
    private OrderBean bean;
    private boolean status;
    private int type;
    private Dialog dialog;
    private PayDialog payDialog;

    @InjectView(R.id.order_confirm_code)
    TextView code_Tv;  //订单编号
    @InjectView(R.id.order_confirm_price)
    TextView price_Tv;  //应付款
    @InjectView(R.id.order_confirm_pay)
    TextView pay_Tv;   //立即支付
    @InjectView(R.id.pay_zhifubao)
    RelativeLayout zhifubao;
    @InjectView(R.id.pay_weixin)
    RelativeLayout weixin;
    @InjectView(R.id.details_shop_Rl)
    RelativeLayout shop_Rl;
    @InjectView(R.id.order_confirm_name)
    TextView bussines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        ButterKnife.inject(this);
        INSTANCE = this;
        bean= (OrderBean) getIntent().getSerializableExtra(GlobalConsts.INTENT_DATA);
        init();
        setListeners();
    }

    //切换选中图片
    private void setListeners() {
        //支付宝
        zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubao_iv.setImageResource(R.drawable.pay_select2);
                weixin_iv.setImageResource(R.drawable.pay_unselected);
                status=true;
                type=1;
            }
        });
        //微信
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubao_iv.setImageResource(R.drawable.pay_unselected);
                weixin_iv.setImageResource(R.drawable.pay_select2);
                status=true;
                type=2;
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog.show();
            }
        });


        pay_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"atOncePayClicked");
                //MobclickAgent.onEvent(INSTANCE, "v2_atOncePayClicked");
                if (status) {
                    if (1 == type) {
                        //支付宝支付
                        dialog.show();
                        Call<StatusBean>call=HttpServiceClient.getInstance().pay_way(MyApplication.token,bean.getData().getOrder_id());
                        call.enqueue(new Callback<StatusBean>() {
                            @Override
                            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                dialog.dismiss();
                                if(response.isSuccessful()){

                                    if("ok".equals(response.body().getStatus())){
                                        AliPay aliPay = new AliPay(INSTANCE);
                                        aliPay.pay(INSTANCE,bean.getData().getTitle(),bean.getData().getTitle(),bean.getData().getReal_payment(),bean.getData().getOrder_no(), bean.getData().getOrder_id());
                                    }else{
                                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
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

//                    aliPay.pay(INSTANCE, bean.name, bean.name, bean.price, bean.order_no, bean.order_id);


                    } else if (2 == type) {
                        //微信支付
                        WXpayUtils wXpayUtils = new WXpayUtils(INSTANCE);
                        wXpayUtils.pay(bean.getData().getOrder_id(), bean.getData().getOrder_no(),"3");
                    }

                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请选择支付方式", false);
                    dialog.show();
                }
            }
        });

    }


    private void init() {
        dialog=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
        zhifubao_iv = (ImageView) findViewById(R.id.pay_check_02);
        weixin_iv = (ImageView) findViewById(R.id.pay_check_03);
        code_Tv.setText(bean.getData().getOrder_no());
        price_Tv.setText("¥"+bean.getData().getReal_payment());
        bussines.setText(bean.getData().getBusiness());


        zhifubao_iv.setImageResource(R.drawable.pay_select2);
        weixin_iv.setImageResource(R.drawable.pay_unselected);
        status=true;
        type=1;

        payDialog=new PayDialog(INSTANCE, new PayDialog.onButtonCLickListener() {
            @Override
            public void onOk() {
                payDialog.dismiss();

            }

            @Override
            public void onCancel() {

                if (bean!=null&&bean.getData().getOrder_id()!=null) {
                    if (getIntent().getIntExtra("isCart",0)==0) {
                        startActivity(new Intent(INSTANCE, OrderDetilsActivity.class)
                                .putExtra(GlobalConsts.INTENT_DATA, bean.getData().getOrder_id()));
                    }else {
                        startActivity(new Intent(INSTANCE, ShoppingCartActivity.class));
                    }
                }
                finish();
            }
        },R.style.registDialog);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
           payDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
