package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.OrderInfoAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.LogisticsBean;
import enjoytouch.com.redstar.bean.OrderBean;
import enjoytouch.com.redstar.bean.OrderInfoBean;
import enjoytouch.com.redstar.bean.OrderInfoManager;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.bean.TitleBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//订单详情
public class OrderDetilsActivity extends Activity {
    private static OrderDetilsActivity INSTANCE;
    public OrderInfoBean.DataEntity bean;
    private String id;
    private OptionsPickerView pvOptions;
    private ArrayList<String>list;
    private ShapeLoadingDialog dialogs;
    private long time=0;

    @InjectView(R.id.hotorder_lv)
    ListView hotorder_lv;
    @InjectView(R.id.order_details_order_tv)
    TextView order_no_Tv;
    @InjectView(R.id.order_details_state_iv)
    ImageView state_Iv;
    @InjectView(R.id.order_details_name_Tv)
    TextView name_Tv;
    @InjectView(R.id.order_details_address)
    TextView address_Tv;
    @InjectView(R.id.order_details_phone)
    TextView phone_Tv;
    @InjectView(R.id.order_details_liuyan)
    TextView liuyan_Tv;
    @InjectView(R.id.order_details_price_all)
    TextView price_all_Tv;
    @InjectView(R.id.order_details_yunfei)
    TextView yunfei_Tv;
    @InjectView(R.id.order_details_price)
    TextView price_Tv;
    @InjectView(R.id.order_details_pay_code)
    TextView pay_code_Tv;
    @InjectView(R.id.order_details_time01)
    TextView time01;//下单时间
    @InjectView(R.id.order_details_time02)
    TextView time02;//支付时间
    @InjectView(R.id.order_details_time03)
    TextView time03;//完成时间
    @InjectView(R.id.order_details_fail)
    TextView fail_Tv;//失败原因
    @InjectView(R.id.order_detail_fail_ll)
    LinearLayout fail_ll;
    @InjectView(R.id.order_details_alpha)
    LinearLayout alpha_Ll;// 待付款状态显示 剩余时间以及取消和去支付
    @InjectView(R.id.order_details_shouhuo)
    RelativeLayout shouhuo_Rl;
    @InjectView(R.id.order_details_time)
    TextView time_Tv;//剩余时间
    @InjectView(R.id.order_details_quxiao)
    RelativeLayout cancel_Rl;//取消订单
    @InjectView(R.id.order_details_togopay)
    RelativeLayout pay_Rl;//去支付
    @InjectView(R.id.sv_order)
    ScrollView sv;
    @InjectView(R.id.order_details_youhui)
    TextView youhui_Tv;

        private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what){
                case 1:
                    time--;
                    time_Tv.setText((time) / 60 + "分钟");
                    if (time > 0) {
                        if(time<=60){
                            time_Tv.setText("1分钟");
                        }
                        handler.sendEmptyMessageDelayed(1,1000);
                    } else {
                        time_Tv.setVisibility(View.GONE);
                        alpha_Ll.setVisibility(View.GONE);
                        time01.setVisibility(View.VISIBLE);
                        time02.setVisibility(View.VISIBLE);
                        time01.setText("下单时间:" + bean.getCreated());
                        time02.setText("关闭时间:"+ Utils.dateToStrLong(System.currentTimeMillis()));
                        state_Iv.setImageResource(R.drawable.jiaoyiguanbi);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        setContentView(R.layout.activity_order_detils);
        ButterKnife.inject(this);
        id=getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        MobclickAgent.onEvent(getApplicationContext(), "v2_lookMyOrderDetail");

    }
    private void setViews(){
        init();
        order_no_Tv.setText("订单号:"+bean.getOrder_no());
        name_Tv.setText("收货人:"+bean.getAddress().getName());
        address_Tv.setText("收货地址:"+bean.getAddress().getProvince_name()+bean.getAddress().getCity_name()+bean.getAddress().getArea_name()+bean.getAddress().getAddress());
        phone_Tv.setText(bean.getAddress().getTel());
        liuyan_Tv.setText(bean.getUser_remark());
        price_all_Tv.setText(bean.getOrder_price());
        yunfei_Tv.setText(bean.getDelivery_price());
        price_Tv.setText(bean.getReal_payment());
        youhui_Tv.setText("- ¥"+bean.getDiscounted_price());//优惠金额
        pay_code_Tv.setText("支付单号:"+bean.getPay_code());
        time01.setText("下单时间:"+bean.getCreated());
        time02.setText("支付时间:"+bean.getPay_time());
        time03.setText("完成时间:"+bean.getFinish_time());
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        list=new ArrayList<>();
        list.add("我不想买了");
        list.add("信息填写错误,重新购买");
        list.add("卖家缺货");
        list.add("其他原因");
        pvOptions.setPicker(list);
        pvOptions.setTitle("取消订单原因");
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1);

        /**
         * 1为待付款 2为已付款 3为关闭 7为发货 8为完成
         */
        switch (bean.getStatus()){
            case "1":
                state_Iv.setImageResource(R.drawable.daifukuan);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.GONE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.GONE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.VISIBLE);
                shouhuo_Rl.setVisibility(View.GONE);
                time_Tv.setText(bean.getRemainder_time()/60+"分钟");
                time = Long.valueOf(bean.getRemainder_time());
                handler.sendEmptyMessageDelayed(1, 1000);
                break;
            case "2":
                state_Iv.setImageResource(R.drawable.yifukuan);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.GONE);
                shouhuo_Rl.setVisibility(View.GONE);
                break;
            case "3":
                state_Iv.setImageResource(R.drawable.jiaoyiguanbi);
                fail_ll.setVisibility(View.VISIBLE);
                shouhuo_Rl.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.GONE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.GONE);
                time02.setText("关闭时间:"+bean.getFinish_time());
                fail_Tv.setText("关闭原因:"+bean.getCancel_reson());
                break;
            case "7":
                state_Iv.setImageResource(R.drawable.yifahuo);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.GONE);
                shouhuo_Rl.setVisibility(View.VISIBLE);
                break;
            case "8":
                state_Iv.setImageResource(R.drawable.jiaoyichenggong);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.VISIBLE);
                alpha_Ll.setVisibility(View.GONE);
                shouhuo_Rl.setVisibility(View.GONE);
                break;
            case "9":
                state_Iv.setImageResource(R.drawable.jiaoyiguanbi);
                fail_ll.setVisibility(View.VISIBLE);
                shouhuo_Rl.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.VISIBLE);
                alpha_Ll.setVisibility(View.GONE);
                time02.setText("关闭时间:"+bean.getFinish_time());
                fail_Tv.setText("关闭原因:"+bean.getCancel_reson());
                break;
            case "95":
                state_Iv.setImageResource(R.drawable.yifahuo);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.GONE);
                shouhuo_Rl.setVisibility(View.VISIBLE);
                break;
            case "96":
                state_Iv.setImageResource(R.drawable.yifahuo);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.GONE);
                shouhuo_Rl.setVisibility(View.VISIBLE);
                break;
            case "97":
                state_Iv.setImageResource(R.drawable.yifahuo);
                fail_ll.setVisibility(View.GONE);
                pay_code_Tv.setVisibility(View.VISIBLE);
                time01.setVisibility(View.VISIBLE);
                time02.setVisibility(View.VISIBLE);
                time03.setVisibility(View.GONE);
                alpha_Ll.setVisibility(View.GONE);
                shouhuo_Rl.setVisibility(View.VISIBLE);
                break;

        }

//        //是否显示收货按钮  0隐藏 1显示
//        if("0".equals(bean.getReceiving_show())){
//            shouhuo_Rl.setVisibility(View.GONE);
//        }else {
//            shouhuo_Rl.setVisibility(View.VISIBLE);
//        }


    }
    private void setListener(){
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetilsActivity.this.finish();
            }
        });


        /**
         * 去付款
         */
        pay_Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "OrderDetailToPayAction");
                OrderBean orderBean = new OrderBean();
                orderBean.setData(new OrderBean.DataEntity());
                orderBean.getData().setOrder_id(bean.getId());
                orderBean.getData().setOrder_no(bean.getOrder_no());
                orderBean.getData().setBusiness(bean.getBusiness());
                orderBean.getData().setTitle(bean.getTitle());
                orderBean.getData().setReal_payment(bean.getReal_payment());
                Intent intent = new Intent(INSTANCE, PaymentDetailsActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA, orderBean);
                startActivity(intent);
            }
        });

        /**
         * 确认收货
         */
        shouhuo_Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MiddleDialog dialogs = new MiddleDialog(INSTANCE, "是否确认收货?\n", "","否","是", 0, new MiddleDialog.onButtonCLickListener() {
                    @Override
                    public void onActivieButtonClick(Object beans, int position) {

                        final Dialog dialog=DialogUtil.createLoadingDialog(INSTANCE,INSTANCE.getString(R.string.loading));
                        dialog.show();
                        Call<StatusBean>call= HttpServiceClient.getInstance().comfir_receipt(MyApplication.token,bean.getId());
                        call.enqueue(new Callback<StatusBean>() {
                            @Override
                            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                dialog.dismiss();
                                if (response.isSuccessful()) {
                                    if ("ok".equals(response.body().getStatus())) {
                                        ExclusiveYeUtils.onMyEvent(INSTANCE, "OrderDetailConfirmReceipt");
                                        upload(bean.getId());
                                    } else {

                                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                    }
                                } else {

                                    try {
                                        ContentUtil.makeToast(INSTANCE, response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<StatusBean> call, Throwable t) {

                                dialog.dismiss();
                            }
                        });
                    }
                }, R.style.registDialog);
                dialogs.show();
            }
        });

        /**
         * 取消订单
         */
        cancel_Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "cancelOrderAction");
                pvOptions.show();
            }
        });

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                final Dialog dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                dialog.show();
                Call<StatusBean> call= HttpServiceClient.getInstance().order_cancel(MyApplication.token, id, list.get(options1));
                call.enqueue(new Callback<StatusBean>() {
                    @Override
                    public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if ("ok".equals(response.body().getStatus())) {
                                upload(bean.getId());
                                DialogUtil.show(INSTANCE, "取消订单成功!", false).show();
//                                state_Iv.setImageResource(R.drawable.jiaoyiguanbi);
//                                fail_ll.setVisibility(View.VISIBLE);
//                                fail_Tv.setText("关闭原因:订单关闭");

                            } else {

                                ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                            }
                        } else {

                            try {
                                ContentUtil.makeToast(INSTANCE, response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusBean> call, Throwable t) {

                        dialog.dismiss();
                    }
                });
            }
        });
    }
    //购买的商品信息
    private void init() {
        List<OrderInfoManager>managers=new ArrayList<>();
        for (int i=0;i<bean.getProduct().size();i++){
            TitleBean titleBean=new TitleBean();
            titleBean.setTitle(bean.getProduct().get(i).getName());
            managers.add(new OrderInfoManager(titleBean));
            for (int j=0;j<bean.getProduct().get(i).getSku_info().size();j++){
                managers.add(new OrderInfoManager(bean.getProduct().get(i).getSku_info().get(j)));
            }
            if(bean.getProduct().get(i).getLogistics()!=null){
                LogisticsBean logisticsBean=new LogisticsBean();
                logisticsBean.setLogistics_name(bean.getProduct().get(i).getLogistics().getLogistics_name());
                logisticsBean.setLogistics_no(bean.getProduct().get(i).getLogistics().getLogistics_no());
                logisticsBean.setWebsite(bean.getProduct().get(i).getLogistics().getWebsite());
                logisticsBean.setType(bean.getProduct().get(i).getLogistics().getType());
                managers.add(new OrderInfoManager(logisticsBean));
            }
        }
        OrderInfoAdapter adapter = new OrderInfoAdapter(INSTANCE,managers,bean.getId(),bean.getStatus());
        hotorder_lv.setAdapter(adapter);
        ListAdapter listAdapter = hotorder_lv.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, hotorder_lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = hotorder_lv.getLayoutParams();
        params.height = totalHeight + (hotorder_lv.getDividerHeight() * (listAdapter.getCount()-1));
//        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        hotorder_lv.setLayoutParams(params);
    }


    private void upload(String id){
        dialogs=new ShapeLoadingDialog(INSTANCE);
        dialogs.setLoadingText(getString(R.string.loading));
        dialogs.show();
        Call<OrderInfoBean>call= HttpServiceClient.getInstance().order_details(MyApplication.token,id);
        call.enqueue(new Callback<OrderInfoBean>() {
            @Override
            public void onResponse(Call<OrderInfoBean> call, Response<OrderInfoBean> response) {
                dialogs.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        bean=response.body().getData();
                        setViews();
                        setListener();
                    } else {

                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                } else {

                    try {
                        ContentUtil.makeToast(INSTANCE, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderInfoBean> call, Throwable t) {

                dialogs.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        upload(id);

    }
}
