package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MakeOrderAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.OrderBean;
import enjoytouch.com.redstar.bean.OrderManager;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.TitleBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 确认订单
 */

public class MakeOrderActivity extends Activity {
    private static MakeOrderActivity INSTANCE;
    private String address_id = "";
    private ShapeLoadingDialog dialog;
    private ProductBean.DataEntity bean;
    private String id;
    private String []datas;

    @InjectView(R.id.make_order_address)
    RelativeLayout address;//填写收货地址
    @InjectView(R.id.make_order_address_Ll)
    LinearLayout address_Ll;//地址显示
    @InjectView(R.id.make_order_name_Tv)
    TextView name_Tv;
    @InjectView(R.id.make_order_phone_Tv)
    TextView phone_Tv;
    @InjectView(R.id.tv_dizhi222)
    TextView address_Tv;
    @InjectView(R.id.make_order_Et)
    EditText order_et;
    @InjectView(R.id.make_order_price_all)
    TextView price;//商品总额
    @InjectView(R.id.make_order_price_service)
    TextView price_serivce;//增值服务费
    @InjectView(R.id.make_order_price_send)
    TextView price_send;//运费
    @InjectView(R.id.make_order_price)
    TextView price_buy;//应付款
    @InjectView(R.id.tv_topaymoney)
    TextView send;//支付
    @InjectView(R.id.order_lv)
    ListView lv;
    @InjectView(R.id.si_close)
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_make_order);
        INSTANCE = this;
        ButterKnife.inject(this);
        id=getIntent().getStringExtra("id");
        bean= (ProductBean.DataEntity) getIntent().getSerializableExtra(GlobalConsts.INTENT_DATA);
        datas=getIntent().getStringArrayExtra("data");
        setViews();
        setListener();
    }

    private void setViews() {

        if(bean.getAddress()!=null){
            address_id=bean.getAddress().getId();
            address.setVisibility(View.GONE);
            address_Ll.setVisibility(View.VISIBLE);
            name_Tv.setText("收件人:" + bean.getAddress().getName());
            phone_Tv.setText(bean.getAddress().getTel());
            address_Tv.setText("收件地址:"+bean.getAddress().getAddress());
        }else{
            address.setVisibility(View.VISIBLE);
            address_Ll.setVisibility(View.GONE);
        }
        price.setText("¥"+bean.getTotal_price());
        price_serivce.setText("- ¥"+bean.getDiscounted_price());
        price_send.setText("¥"+bean.getShipment());
        price_buy.setText(bean.getPay_price() + "");
        init();
    }

    private void init() {

        List<OrderManager>list=new ArrayList<>();
        for (int i=0;i<bean.getProduct().size();i++){
            TitleBean titleBean=new TitleBean();
            titleBean.setTitle(bean.getProduct().get(i).getName());
            list.add(new OrderManager(titleBean));
            for (int j=0;j<bean.getProduct().get(i).getSku_info().size();j++){
                list.add(new OrderManager(bean.getProduct().get(i).getSku_info().get(j)));
            }
        }
        MakeOrderAdapter adapter = new MakeOrderAdapter(INSTANCE, list);
        lv.setAdapter(adapter);
        ListAdapter listAdapter = lv.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (listAdapter.getCount() - 1));
//        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        lv.setLayoutParams(params);
    }

    public void setListener() {
        order_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int l = s.length();//字符序列的长度
                if (l > 0) {
                    close.setVisibility(View.VISIBLE);
                }else {
                    close.setVisibility(View.GONE);
                }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 支付
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"productToPayAction");
                MobclickAgent.onEvent(INSTANCE, "v2_goPayClicked");
                if (!"".equals(address_id)) {
                    final Dialog dialog1=DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                    dialog1.show();
                    String value = order_et.getText().toString();
                    Call<OrderBean>call=HttpServiceClient.getInstance().order(datas[0],datas[1],MyApplication.cityId,address_id,MyApplication.token,value,"0".equals(getIntent().getStringExtra("isCart"))?"1":"2");
                    call.enqueue(new Callback<OrderBean>() {
                        @Override
                        public void onResponse(Call<OrderBean> call, Response<OrderBean> response) {
                            dialog1.dismiss();
                            if(response.isSuccessful()){

                                if("ok".equals(response.body().getStatus())){
                                    Intent intent=new Intent(INSTANCE,PaymentDetailsActivity.class);
                                    intent.putExtra(GlobalConsts.INTENT_DATA,response.body());
                                    intent.putExtra("isCart", getIntent().getIntExtra("isCart",0)==0?
                                            0: getIntent().getIntExtra("isCart",0));
                                    startActivity(intent);
                                    finish();
                                }else{
                                    ContentUtil.makeToast(INSTANCE,response.body().getError().getMessage());
                                    ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                                }
                            }else{
                                ContentUtil.makeToast(INSTANCE,getString(R.string.loadding_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderBean> call, Throwable t) {

                            dialog1.dismiss();
                        }
                    });

                }else {
                    ContentUtil.makeToast(INSTANCE,"地址不能为空!");
                }

            }
        });

        address_Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(INSTANCE, Address2Activity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,"1");
                intent.putExtra("value",new String[]{datas[0],datas[1]});
                startActivityForResult(intent, GlobalConsts.CODES);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_et.setText("");
            }
        });
        /**
         * 添加我的收货地址
         */
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"addMyAddress");
                Intent intent = new Intent(INSTANCE, AddAddressActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,"2");
                intent.putExtra("value",new String[]{datas[0],datas[1]});
                startActivityForResult(intent,GlobalConsts.CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GlobalConsts.CODE:
                    address_id = "";
                    bean = (ProductBean.DataEntity) data.getSerializableExtra(GlobalConsts.INTENT_DATA);
                    if (bean!=null) {
                        address.setVisibility(View.GONE);
                        address_Ll.setVisibility(View.VISIBLE);
                        name_Tv.setText("收货人:" + bean.getAddress().getName());
                        phone_Tv.setText(bean.getAddress().getTel());
                        address_Tv.setText("收货地址:" + bean.getAddress().getProvince_name() + bean.getAddress().getCity_name() + bean.getAddress().getArea_name() + bean.getAddress().getAddress());
                        address_id = bean.getAddress().getId();
                        price.setText("¥"+bean.getTotal_price());
                        price_serivce.setText("¥"+bean.getDiscounted_price());
                        price_send.setText("¥"+bean.getShipment());
                        price_buy.setText(bean.getPay_price() + "");
                    } else {
                        address.setVisibility(View.VISIBLE);
                        address_Ll.setVisibility(View.GONE);
                    }

                    break;
                case GlobalConsts.CODES:
                    //更换
                    address.setVisibility(View.GONE);
                    address_Ll.setVisibility(View.VISIBLE);
                    bean = (ProductBean.DataEntity) data.getSerializableExtra(GlobalConsts.INTENT_DATA);
                    ContentUtil.makeLog("lzz", "bean:" + bean);
                    name_Tv.setText("收货人:" + bean.getAddress().getName());
                    phone_Tv.setText(bean.getAddress().getTel());
                    address_Tv.setText("收货地址:" + bean.getAddress().getProvince_name() + bean.getAddress().getCity_name() + bean.getAddress().getArea_name() + bean.getAddress().getAddress());
                    address_id = bean.getAddress().getId();
                    price.setText("¥"+bean.getTotal_price());
                    price_serivce.setText("¥"+bean.getDiscounted_price());
                    price_send.setText("¥"+bean.getShipment());
                    price_buy.setText(bean.getPay_price() + "");
                    break;
            }
        }
    }
//    private void a(){
//        final Dialog dialog = DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
//        dialog.show();
//        Call<ProductBean> call = HttpServiceClient.getInstance().order_confirm(id, "1", MyApplication.cityId, MyApplication.token);
//        call.enqueue(new Callback<ProductBean>() {
//            @Override
//            public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
//                dialog.dismiss();
//                if (response.isSuccessful()) {
//                    if ("ok".equals(response.body().getStatus())) {
//                        bean=response.body().getData();
//                        setViews();
//                        setListener();
//                    } else {
//                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
//                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
//                    }
//                } else {
//                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductBean> call, Throwable t) {
//
//                ContentUtil.makeLog("lzz", "11111");
//                dialog.dismiss();
//            }
//        });
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        a();
//    }
}
