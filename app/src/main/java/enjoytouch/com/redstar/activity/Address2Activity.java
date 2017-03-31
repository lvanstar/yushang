package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyAddressAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.MyAddressBean;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.UserAddressBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address2Activity extends Activity {
    private View back;
    private ListView lv_myaddress;
    MyAddressAdapter adapter;
    private List<UserAddressBean> datas;
    private Address2Activity INSTANCE;
    private ShapeLoadingDialog dialog;
    private String type;// type 1为确认订单页更换地址
    private String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order2);
        type = getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        INSTANCE = this;
        setViews();
        setListeners();

    }

    private void setListeners() {
        findViewById(R.id.toAddAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(INSTANCE,MyAddressActivity.class);
                startActivity(intent);
            }
        });
        //回退
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address2Activity.this.finish();
            }
        });
        lv_myaddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyApplication.select=position;
                    final Dialog dialog = DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
                    dialog.show();
                    values = getIntent().getStringArrayExtra("value");
                    Call<ProductBean> call = HttpServiceClient.getInstance().get_shipment(values[0], values[1], MyApplication.cityId, datas.get(position).getId(), MyApplication.token);
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

                }
        });
    }

    private void setViews() {
        lv_myaddress = (ListView) findViewById(R.id.lv_myaddress);
        lv_myaddress.setDivider(null);
        INSTANCE = this;
        datas = new ArrayList<>();
        back = findViewById(R.id.back);

        adapter = new MyAddressAdapter(INSTANCE, datas,2);
        lv_myaddress.setAdapter(adapter);
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
        dialog = new ShapeLoadingDialog(INSTANCE);
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
                        if (response.body().getData().size() < 0) {
                            findViewById(R.id.sr_nothing).setVisibility(View.VISIBLE);
                        } else {
                            adapter.refresh(response.body().getData());
                            findViewById(R.id.sr_nothing).setVisibility(View.GONE);
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
}
