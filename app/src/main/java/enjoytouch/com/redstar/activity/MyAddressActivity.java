package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyAddressAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.MyAddressBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.bean.UserAddressBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenu;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenuCreator;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenuItem;
import enjoytouch.com.redstar.selfview.swipemenuleft.SwipeMenuListView;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的  管理收货地址
 */
public class MyAddressActivity extends Activity {
    private View back;
    private SwipeMenuListView swipeMenuListView;
    MyAddressAdapter adapter;
    private List<UserAddressBean> datas;
    private MyAddressActivity INSTANCE;
    private ShapeLoadingDialog dialog;
    private String type;// type 1为确认订单页更换地址
    private String [] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        type=getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        INSTANCE=this;
        setViews();
        setListeners();

    }

    private void setListeners(){
        findViewById(R.id.toAddAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "createAddress");
                toAddAddress(v);
            }
        });
        //回退
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAddressActivity.this.finish();
            }
        });
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
                        showDeleteDialog(position);
                        break;
                }
            }
        });

        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "editAddress");
                Intent intent = new Intent(INSTANCE, AddAddressActivity.class);
                intent.putExtra("address", datas.get(position));
                intent.putExtra(GlobalConsts.INTENT_DATA, "1");
                startActivity(intent);
                /*if("1".equals(type)){
                    final Dialog dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                    dialog.show();
                    values=getIntent().getStringArrayExtra("value");
                    Call<ProductBean>call=HttpServiceClient.getInstance().get_shipment(values[0],values[1],MyApplication.cityId,datas.get(position).getId(),MyApplication.token);
                    call.enqueue(new Callback<ProductBean>() {
                        @Override
                        public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
                            dialog.dismiss();
                            if (response.isSuccessful()) {
                                if ("ok".equals(response.body().getStatus())) {
                                    Intent intent = new Intent(INSTANCE, MakeOrderActivity.class);
                                    intent.putExtra(GlobalConsts.INTENT_DATA, response.body().getData());
                                    setResult(RESULT_OK, intent);
                                    finish();

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
                        public void onFailure(Call<ProductBean> call, Throwable t) {

                            dialog.dismiss();
                        }
                    });

                }else{
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "editAddress");
                    Intent intent = new Intent(INSTANCE, AddAddressActivity.class);
                    intent.putExtra("address",datas.get(position));
                    intent.putExtra(GlobalConsts.INTENT_DATA,"1");
                    startActivity(intent);
                }
*/
            }
        });
    }

    private void setViews() {
        swipeMenuListView = (SwipeMenuListView) findViewById(R.id.lv_myaddress);
        swipeMenuListView.setDivider(null);
        INSTANCE = this;
        datas = new ArrayList<>();
        back = findViewById(R.id.back);

        adapter=new MyAddressAdapter(INSTANCE,datas,1);
        swipeMenuListView.setAdapter(adapter);


    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void toAddAddress(View view) {
        Intent intent = new Intent();
        intent.setClass(this, AddAddressActivity.class);
        intent.putExtra(GlobalConsts.INTENT_DATA, "3");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        dialog.show();
        Call<MyAddressBean> call = HttpServiceClient.getInstance().user_addreaalist(MyApplication.token);
        call.enqueue(new Callback<MyAddressBean>() {
            @Override
            public void onResponse(Call<MyAddressBean> call, Response<MyAddressBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        datas = response.body().getData();
                        if (datas.size()>0) {
                            swipeMenuListView.setVisibility(View.VISIBLE);
                            adapter.refresh(response.body().getData());
                            findViewById(R.id.sr_nothing).setVisibility(View.GONE);
                        } else {
                            swipeMenuListView.setVisibility(View.GONE);
                            findViewById(R.id.sr_nothing).setVisibility(View.VISIBLE);
                        }

                    } else {

                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                        ExclusiveYeUtils.isExtrude(INSTANCE, response.body().getError().getCode());
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
            public void onFailure(Call<MyAddressBean> call, Throwable t) {

                dialog.dismiss();
            }
        });
    }
    private void delAddress(final int id){
//        final Dialog dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
//        dialog.show();
        Call<StatusBean>call=HttpServiceClient.getInstance().del_address(MyApplication.token, datas.get(id).getId());
        call.enqueue(new Callback<StatusBean>() {
            @Override
            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
//                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        datas.remove(id);
                        adapter.refresh(datas);
                        initData();
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
            public void onFailure(Call<StatusBean> call, Throwable t) {

//                dialog.dismiss();
            }
        });
    }


    private void showDeleteDialog(final int id) {
        new MiddleDialog(INSTANCE, "是否确定删除", "","取消","确定", 0, new MiddleDialog.onButtonCLickListener() {
            @Override
            public void onActivieButtonClick(Object bean, int position) {
                delAddress(id);
            }
        }, R.style.registDialog).show();

    }

}
