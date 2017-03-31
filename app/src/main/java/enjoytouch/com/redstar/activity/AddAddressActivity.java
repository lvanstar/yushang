package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.EditAddressBean;
import enjoytouch.com.redstar.bean.GetFirstCityBean;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.bean.UserAddressBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends Activity {
    //这是三级地址的集合
    private AddAddressActivity INSTANCE;
    private static List<GetFirstCityBean.DataBean> cityDatas;
    private UserAddressBean address_list;
    private ArrayList<String> data = new ArrayList();
    private OptionsPickerView pvOptions;
    private String province_id;//省id
    private String province_name;//省名字
    private String city_id;     //市id
    private String city_name;
    private String region_id;   //区域id
    private String region_name;
    private TextView tv_winAdd;  //接收地址值
    private LinearLayout ll_addwin; //所在地区一行
    private TextView tv_save;  //保存
    private EditText et_name, et_phone, et_address;
    private String p_id, c_id, a_id;//省市区id
    private String addressID = "";//收货地址id
    private EditAddressBean b;//收货地址
    private String type;
    private UserAddressBean addressBean = new UserAddressBean();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private String id = "";
    private String [] values;
    private Dialog dialog;


    @InjectView(R.id.detel)
    TextView Del_Tv;
    @InjectView(R.id.et_name)
    EditText name;
    @InjectView(R.id.et_phone)
    EditText phone;
    @InjectView(R.id.tv_address)
    TextView tv_address;
    @InjectView(R.id.ll_addwin)
    LinearLayout add;
    @InjectView(R.id.title)
    TextView title_Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        type = getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        addressBean = (UserAddressBean) getIntent().getSerializableExtra("address");
        ButterKnife.inject(this);
        INSTANCE=this;
        cityDatas=MyApplication.cityDatas;
        init();
        setListeners();
        initdite();
    }

    private void init() {
        dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
        tv_winAdd = (TextView) findViewById(R.id.tv_winAdd);//所选地区
        ll_addwin = (LinearLayout) findViewById(R.id.ll_addwin);
        tv_save = (TextView) findViewById(R.id.add_address_ok);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        // et_postcode= (EditText) findViewById(R.id.et_postcode);
        et_address = (EditText) findViewById(R.id.et_address);
        //如果是编辑
        if ("1".equals(type)) {
            MobclickAgent.onEvent(getApplicationContext(), "v2_editAddress");
            title_Tv.setText("编辑收货地址");
            et_name.setText(addressBean.getName());
            et_phone.setText(addressBean.getTel());
            et_address.setText(addressBean.getAddress());
            province_name = addressBean.getProvince_name();
            city_name = addressBean.getCity_name();
            region_name = addressBean.getArea_name();
            tv_winAdd.setText(province_name + city_name + region_name);
            addressID = addressBean.getId();
            Del_Tv.setVisibility(View.VISIBLE);
            province_id=addressBean.getProvince_id();
            city_id=addressBean.getCity_id();
            region_id=addressBean.getArea_id();
        } else if("2".equals(type)){
            MobclickAgent.onEvent(getApplicationContext(), "v2_createAddress");
            Del_Tv.setVisibility(View.GONE);
            values=getIntent().getStringArrayExtra("value");
        }else if("3".equals(type)){
            Del_Tv.setVisibility(View.GONE);
        }


    }

    private void initdite() {
        int item_1=0;int item_2=0;int item_3=0;
        for (int i = 0; i < cityDatas.size(); i++) {
            ArrayList<String> options2Items_01 = new ArrayList<String>();//每遍历到一个省就会新建一个集合
            ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();

            if ("1".equals(type)) {
                if (addressBean.getProvince_name().equals(cityDatas.get(i).getCityname()))
                item_3 = i;
            }
            for (int j = 0; j < cityDatas.get(i).getChildren().size(); j++) {//遍历每个省中的市的集合
                options2Items_01.add(cityDatas.get(i).getChildren().get(j).getCityname());//把每个市名添加到对应省的集合

                ArrayList<String> options3Items_01_01 = new ArrayList<String>();//每遍历到一个市就会新建一个集合

                if ("1".equals(type)){
                    if (addressBean.getCity_name().equals( cityDatas.get(i).getChildren().get(j).getCityname()))
                item_2=j;
                }
                for (int k = 0; k < cityDatas.get(i).getChildren().get(j).getChildren().size(); k++) {//遍历每个省中每个市的县集合
                    options3Items_01_01.add(cityDatas.get(i).getChildren().get(j).getChildren().get(k).getCityname());//把每个县的名字添加到对应市的集合
                    if ("1".equals(type)) {
                        if (addressBean.getArea_name().equals(cityDatas.get(i).getChildren().get(j).getChildren().get(k).getCityname()))
                        item_1 = k ;
                    }
                }
                options3Items_01.add(options3Items_01_01);//市的集合
            }
            options2Items.add(options2Items_01);
            data.add(cityDatas.get(i).getCityname());//省级集合
            options3Items.add(options3Items_01);
        }
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        pvOptions.setPicker(data, options2Items, options3Items, true);

//        pvOptions.setSelectOptions(Integer.valueOf(addressBean.getProvince_id()),
//                Integer.valueOf(addressBean.getCity_id()),
//                        Integer.valueOf(addressBean.getArea_id()));
            pvOptions.setSelectOptions(item_3, item_2, item_1);

        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, false, false);


        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String tx = data.get(options1)
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tv_winAdd.setText(tx);
                id = options3Items.get(options1).get(option2).get(options3);
                province_id = cityDatas.get(options1).getId();
                ContentUtil.makeLog("zZ~province_id", province_id);
                province_name = cityDatas.get(options1).getCityname();
                ContentUtil.makeLog("zZ~province_name", province_name);
                city_id = cityDatas.get(options1).getChildren().get(option2).getId();
                ContentUtil.makeLog("zZ~city_id", city_id);
                city_name = cityDatas.get(options1).getChildren().get(option2).getCityname();
                ContentUtil.makeLog("zZ~city_name", city_name);
                region_id = cityDatas.get(options1).getChildren().get(option2).getChildren().get(options3).getId();
                ContentUtil.makeLog("zZ~region_id", region_id);
                region_name = cityDatas.get(options1).getChildren().get(option2).getChildren().get(options3).getCityname();
                ContentUtil.makeLog("zZ~region_name", region_name);
                //vMasker.setVisibility(View.GONE);
            }
        });

    }


    private void setListeners() {
        //点击弹出选项选择器
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentUtil.makeLog("监听执行", "");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                pvOptions.show();
            }
        });
        //回退
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.this.finish();
            }
        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!name.getText().toString().equals("") && !phone.getText().toString().
                        equals("") && !et_address.getText().
                        toString().equals("") && !tv_winAdd.getText().toString().equals("")) {

                    if (!ContentUtil.isMobileNO(phone.getText().toString())) {
                        Dialog dialog = DialogUtil.show(INSTANCE, "请输入正确的手机号", false);
                        dialog.show();
                        return;
                    }
                    if ("1".equals(type)) {
                        if (addressBean != null) {
                            ExclusiveYeUtils.onMyEvent(INSTANCE, "saveAddress");
                            edit(addressBean.getId());
                        }
                    } else {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "saveAddress");
                        edit("");
                    }

                } else {
                    Dialog dialog = DialogUtil.show(INSTANCE, "请输入完整信息", false);
                    dialog.show();
                }


            }
        });

        //删除
        Del_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    private void showDeleteDialog() {
        new MiddleDialog(AddAddressActivity.this, "是否确定删除该地址", "","取消","确定", 0, new MiddleDialog.onButtonCLickListener() {
            @Override
            public void onActivieButtonClick(Object bean, int position) {
                delAddress(addressBean.getId());
            }
        }, R.style.registDialog).show();

    }

    private void edit(final String id) {

        dialog.show();
        ContentUtil.makeLog("lzz","1111111111");
        Call<EditAddressBean> call = HttpServiceClient.getInstance().user_editaddress(MyApplication.token, id, name.getText().toString(), phone.getText().toString(),
                province_id, province_name, city_id, city_name, region_id, region_name, et_address.getText().toString());

        call.enqueue(new Callback<EditAddressBean>() {
            @Override
            public void onResponse(Call<EditAddressBean> call, Response<EditAddressBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    address_list = response.body().getData();
                    if ("ok".equals(response.body().getStatus())) {
                        if ("2".equals(type)) {
                            final Dialog dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                            dialog.show();
                            values=getIntent().getStringArrayExtra("value");
                            Call<ProductBean>calls=HttpServiceClient.getInstance().get_shipment(values[0],values[1],MyApplication.cityId,address_list.getId(),MyApplication.token);
                            calls.enqueue(new Callback<ProductBean>() {
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
                                public void onFailure(Call<ProductBean> call, Throwable t) {

                                    dialog.dismiss();
                                }
                            });
                        }else {
                            finish();
                        }

                    } else {
                        Toast.makeText(INSTANCE, response.body().getError().getMessage(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<EditAddressBean> call, Throwable t) {

                dialog.dismiss();
            }
        });

    }

    private void delAddress(String id){
//        final Dialog dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
//        dialog.show();
        Call<StatusBean>call=HttpServiceClient.getInstance().del_address(MyApplication.token,id);
        call.enqueue(new Callback<StatusBean>() {
            @Override
            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
//                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "deleteAddress");
                        finish();
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

}
