package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.ShoppingCartAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.ShoppingCartBean;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenu;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenuCreator;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenuItem;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenuListView;
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
 * 购物车
 */
public class ShoppingCartActivity extends Activity {
    private ShoppingCartActivity INSTANCE;
    private ShoppingCartAdapter adapter;
    private ShapeLoadingDialog dialog;
    public static double moeny=0;
    private List<ShoppingCartBean.DataEntity> datas=new ArrayList<>();

    @InjectView(R.id.lv)
    SwipeMenuListView swipeMenuListView;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.pay)
    TextView pay;
    @InjectView(R.id.botton)
    RelativeLayout bottom;
    @InjectView(R.id.wu)
    RelativeLayout wu;
    @InjectView(R.id.you)
    RelativeLayout you;
    public static TextView price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        INSTANCE=this;
        ButterKnife.inject(this);

        setViews();
        setListener();
        shopList();
    }

    private void setViews() {
        price= (TextView) findViewById(R.id.shopping_price);

        adapter=new ShoppingCartAdapter(INSTANCE,datas);
        swipeMenuListView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu,int position) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(60));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(16);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
                menu.setViewType(0);
            }
        };
        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        new AlertDialog.Builder(INSTANCE)
                                .setTitle("是否删除?")
                                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        delectOne(position);
                                    }
                                })
                                .setPositiveButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();

                        break;
                }
            }
        });

        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(INSTANCE, ShopDetailsActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,datas.get(position).getId());
               startActivity(intent);
            }
        });
    }

    private void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                INSTANCE.finish();
            }
        });

        //支付
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"toSettleAccounts");
                  String product="";
                  String amount="";
                List<ShoppingCartBean.DataEntity>list=adapter.getProducts();
                if(list.size()>0){
                    for (int i=0;i<list.size();i++){
                        product+=list.get(i).getId()+",";
                        amount+=String.valueOf(list.get(i).getAmount())+",";
                    }

                    final Dialog dialog=DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
                    dialog.show();
                    Call<ProductBean>call= HttpServiceClient.getInstance().order_confirm(product,amount, MyApplication.cityId,MyApplication.token);
                    final String finalProduct = product;
                    final String finalAmount = amount;
                    call.enqueue(new Callback<ProductBean>() {
                        @Override
                        public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
                            dialog.dismiss();
                            if (response.isSuccessful()) {

                                if ("ok".equals(response.body().getStatus())) {
                                    Intent intent = new Intent(INSTANCE, MakeOrderActivity.class);
                                    intent.putExtra(GlobalConsts.INTENT_DATA, response.body().getData());
                                    intent.putExtra("data",new String[]{finalProduct, finalAmount});
                                    intent.putExtra("isCart",1);
                                    startActivity(intent);
                                    back();
                                } else {
                                    ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                    ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                                }
                            } else {
                                ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductBean> call, Throwable t) {

                            ContentUtil.makeLog("lzz", "11111");
                            dialog.dismiss();
                        }
                    });

                }else {
                    ContentUtil.makeToast(INSTANCE,"请至少选择一件商品");
                }
            }
        });
    }

    private void shopList(){
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loading));
        dialog.show();
        Call<ShoppingCartBean>call= HttpServiceClient.getInstance().shopCar_list(MyApplication.token,MyApplication.cityId);
        call.enqueue(new Callback<ShoppingCartBean>() {
            @Override
            public void onResponse(Call<ShoppingCartBean> call, Response<ShoppingCartBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        datas = response.body().getData();
                        adapter.setDataChanged(response.body().getData());
                        if(datas.size()>0){
                            bottom.setVisibility(View.VISIBLE);
                            wu.setVisibility(View.GONE);
                            you.setVisibility(View.VISIBLE);
                        }else {
                            bottom.setVisibility(View.GONE);
                            wu.setVisibility(View.VISIBLE);
                            you.setVisibility(View.GONE);
                        }
                        startSelectAll();

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
            public void onFailure(Call<ShoppingCartBean> call, Throwable t) {

                dialog.dismiss();
            }
        });
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public static void  setPrice(double value){
        ContentUtil.makeLog("yc",value+"");
        ShoppingCartActivity.moeny=value;
        DecimalFormat df = new DecimalFormat("#0.00");
        price.setText(df.format(value));

    }


    private void delectOne(final int position){
        final Dialog dialogs = DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
        dialogs.show();
        ContentUtil.makeLog("lzz", "postion:" + position);
        Call<ShoppingCartBean> call = HttpServiceClient.getInstance().del_shopCar(datas.get(position).getId(), MyApplication.token, MyApplication.cityId);
        call.enqueue(new Callback<ShoppingCartBean>() {
            @Override
            public void onResponse(Call<ShoppingCartBean> call, Response<ShoppingCartBean> response) {
                dialogs.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        if(datas.get(position).select){
                            setPrice(moeny-(datas.get(position).getAmount()*Double.parseDouble(datas.get(position).getSale_price())));
                        }
                        datas.remove(position);
                        adapter.setDataChanged(response.body().getData());
                        if (response.body().getData().size() > 0) {
                            wu.setVisibility(View.GONE);
                            bottom.setVisibility(View.VISIBLE);
                        } else {
                            wu.setVisibility(View.VISIBLE);
                            bottom.setVisibility(View.GONE);
                        }
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
            public void onFailure(Call<ShoppingCartBean> call, Throwable t) {

                dialogs.dismiss();
            }
        });
    }






     private void back(){
         finish();
     }

    private void startSelectAll(){
        if (datas==null&&datas.size()==0)return;
        ShoppingCartAdapter.price=0.00;
        for (int i=0;i<datas.size();i++){
            if ("1".equals(datas.get(i).getProduct_status())&&!"1".equals(datas.get(i).getStock_status())) {
                datas.get(i).select = true;
                ShoppingCartAdapter.price += (datas.get(i).getAmount() * Double.parseDouble(datas.get(i).getSale_price()));
            }
            if (i==(datas.size()-1)) {
                setPrice(ShoppingCartAdapter.price);
            }
        }
        adapter.setDataChanged(datas);
    }



}
