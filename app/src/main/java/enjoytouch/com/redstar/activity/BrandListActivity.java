package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;

import enjoytouch.com.redstar.adapter.BrandTypeAdapter;
import enjoytouch.com.redstar.adapter.MyBrandAdapter;

import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.bean.TypeBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 品牌列表
 */
public class BrandListActivity extends Activity {
    private ShapeLoadingDialog dialog;
    private int style_postion;        //风格的gridview被点击的位置
    private BrandListActivity INSTANCE;
    private BrandDetailBean data;
    private StyleBean style_data;
    private TypeBean type_data;
    private int type_postion;
    private MyBrandAdapter adapter;
    private BrandTypeAdapter type_adapter;

    private String style_id;        //风格id
    private String order_by;        //排序
    private static String SIZE="6";
    private int PAGE=1;
    private int SIZE2=1;
    private int TYPE=1;
    private Boolean loading=true;
    private String brand_id="";        //品牌id
    private String category_id;     //类别id
    private String id;
    private String parentid;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.ll_type_buzhan)    //不展开时的整个布局
    LinearLayout buzhan;
    @InjectView(R.id.ll_type_zhankai1) //展开类别时的布局
    LinearLayout zhankai1;
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.ll_type_zhankai2)  //展开排序时的布局
    LinearLayout zhankai2;
    @InjectView(R.id.text2)
    TextView text2;


    @InjectView(R.id.buzhan_button1)
    RelativeLayout button1;     //未展开时的类别按钮
    @InjectView(R.id.buzhan_button2)
    RelativeLayout button2;     //未展开时的风格按钮
    @InjectView(R.id.gv)
    XRecyclerView gv;                //未展开时的gridview

    @InjectView(R.id.zhankai1_button1)//展开类别时的类别按钮
    RelativeLayout zhankai1_button1;
    @InjectView(R.id.zhankai1_text1)
    TextView zhankai1_text1;
    @InjectView(R.id.zhankai1_button2)//展开类别时的排序按钮
    RelativeLayout zhankai1_button2;
    @InjectView(R.id.zhankai1_text2)
    TextView zhankai1_text2;
    @InjectView(R.id.shou)
    ImageView shou;
    @InjectView(R.id.list)         //展开类别时的listview
    ListView list;
    @InjectView(R.id.alpha1)
    LinearLayout alpha1;

    @InjectView(R.id.zhankai2_button1)//展开排序时的类别按钮
    RelativeLayout zhankai2_button1;
    @InjectView(R.id.zhankai2_text1)
    TextView zhankai2_text1;
    @InjectView(R.id.zhankai2_button2)//展开排序时的排序按钮
    RelativeLayout zhankai2_button2;
    @InjectView(R.id.zhankai2_text2)
    TextView zhankai2_text2;
    @InjectView(R.id.shou2)
    ImageView shou2;
    @InjectView(R.id.alpha2)
    RelativeLayout alpha2;
    @InjectView(R.id.paixu1)
    LinearLayout paixu1;
    @InjectView(R.id.paixu1_button1)
    LinearLayout paixu1_button1;
    @InjectView(R.id.paixu1_button2)
    LinearLayout paixu1_button2;
    @InjectView(R.id.paixu2)
    LinearLayout paixu2;
    @InjectView(R.id.paixu2_button1)
    LinearLayout paixu2_button1;
    @InjectView(R.id.paixu2_button2)
    LinearLayout paixu2_button2;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.attention_nothing)
    RelativeLayout attention_nothing;
    /*Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            adapter222 = new MyBrandAdapter(BrandListActivity.this, listBrand);
            lv.setAdapter(adapter222);
           // lv.setSelectionAfterHeaderView();
            //customFast.listItemsChanged();
            super.handleMessage(msg);
        }
    };*/
    int m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        INSTANCE=this;
        ButterKnife.inject(this);
        id = getIntent().getStringExtra("id");
        parentid=getIntent().getStringExtra("parentid");
        category_id=parentid+","+id;
        setViews();
    }

    private void setViews() {
        up_iv.setVisibility(View.GONE);
        ContentUtil.makeLog("正在执行","setViews");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        setData(true);
    }

    private void setListerner() {
        ContentUtil.makeLog("正在执行","setListener");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.VISIBLE);
                zhankai2.setVisibility(View.GONE);
                type(false);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "sortFilterBrand");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.VISIBLE);
            }
        });
        zhankai1_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
            }
        });
        zhankai1_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.VISIBLE);
            }
        });
        zhankai2_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.VISIBLE);
                zhankai2.setVisibility(View.GONE);
                type(false);
            }
        });
        zhankai2_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
            }
        });
        alpha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
            }
        });
        shou2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
            }
        });
        alpha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
            }
        });
        paixu1_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterBrand");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                text2.setText("智能排序");
                zhankai1_text2.setText("智能排序");
                zhankai2_text2.setText("智能排序");
                order_by="1";
                setData(false);
            }
        });
        paixu1_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterBrand");
                paixu1.setVisibility(View.GONE);
                paixu2.setVisibility(View.VISIBLE);
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                text2.setText("人气排序");
                zhankai1_text2.setText("人气排序");
                zhankai2_text2.setText("人气排序");
                order_by="2";
                setData(false);
            }
        });
        paixu2_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterBrand");
                paixu1.setVisibility(View.VISIBLE);
                paixu2.setVisibility(View.GONE);
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                text2.setText("智能排序");
                zhankai1_text2.setText("智能排序");
                zhankai2_text2.setText("智能排序");
                order_by="1";
                setData(false);
            }
        });
        paixu2_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterBrand");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                text2.setText("人气排序");
                zhankai1_text2.setText("人气排序");
                zhankai2_text2.setText("人气排序");
                order_by="2";
                setData(false);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterBrand");
                type_postion=position;
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                if(position==0){  //如果点击的是第一个item
                    category_id="0";
                    text1.setText("全部");
                    zhankai1_text1.setText("全部");
                    zhankai2_text1.setText("全部");
                }else {
                    category_id=type_data.getData().get(position-1).getParentid()+","+type_data.getData().get(position-1).getId();
                    text1.setText(type_data.getData().get(position-1).getName());
                    zhankai1_text1.setText(type_data.getData().get(position-1).getName());
                    zhankai2_text1.setText(type_data.getData().get(position-1).getName());
                }
                type_adapter.updateData(type_data,position);
                setData(false);
            }
        });

        gv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                SIZE2=1;
                SIZE="6";
                TYPE=1;
                up_iv.setVisibility(View.GONE);
                setData(false);
                gv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                SIZE2++;
                SIZE=String.valueOf(SIZE2*6);
                if(SIZE2>1){
                    up_iv.setVisibility(View.VISIBLE);
                }
                TYPE=2;
                setData(false);
            }
        });
        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gv.smoothScrollToPosition(0);
                up_iv.setVisibility(View.GONE);
            }
        });
        gv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm= (LinearLayoutManager) gv.getLayoutManager();
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisiblesItems = lm.findFirstVisibleItemPosition();
                ContentUtil.makeLog("第一个item的位置",String.valueOf(pastVisiblesItems));
                if (loading) {
                    if(pastVisiblesItems==0||pastVisiblesItems==1||pastVisiblesItems==2){
                        up_iv.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    private void setViews2(){
        ContentUtil.makeLog("正在执行","setViews2");
        GridLayoutManager linearLayoutManager = new GridLayoutManager(INSTANCE,2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gv.setLayoutManager(linearLayoutManager);
        gv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        gv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //gv.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new MyBrandAdapter(INSTANCE,data,"0");
        gv.setAdapter(adapter);
        setListerner();
    }
    @Override
    public void onResume(){
        super.onResume();
      /*  if (adapter222!=null) {
            new CouponsControl(this).getBrandList(MyApplication.cityId, "", "", "", "", "", parentid+","+categoryID, "", "", "", "", new CouponsControl.CouponsBrandListCallBack() {

                @Override
                public void getbrands(List<BrandListBean> list) {
                    //Log.i("aaa","list.size()= "+list.size());
                    listBrand = list;
                    if (list.size() == 0) {
                        findViewById(R.id.sr_nothing).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.sr_nothing).setVisibility(View.GONE);
                    }
                    BrandListActivity.this.myHandler.sendEmptyMessage(0x01);
                }
            });
        }*/
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void setData(final boolean isLoadding){
        /**
         * 异步请求方式
         */
        if(isLoadding) dialog.show();
        Call<BrandDetailBean> call = HttpServiceClient.getInstance().brand_list(style_id,order_by,SIZE,PAGE,brand_id,category_id,"",MyApplication.cityId);
        call.enqueue(new Callback<BrandDetailBean>() {
            @Override
            public void onResponse(Call<BrandDetailBean> call, Response<BrandDetailBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    BrandDetailBean userBean = response.body();
                    data=userBean;
                    ContentUtil.makeLog("data数据",String.valueOf(data));
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if (userBean.getStatus().toString().equals("ok")) {
                        if(userBean.getData().size()>0){
                            attention_nothing.setVisibility(View.GONE);
                        }else {
                            attention_nothing.setVisibility(View.VISIBLE);
                        }
                        if (1 == TYPE) {
                            if (isLoadding){
                                setViews2();
                            }else {
                                adapter.updateData(data);
                            }
                        }else {
                            if (response.body().getData().size() != 0) {
                                adapter.updateData(data);
                                gv.loadMoreComplete();
                            } else {
                                gv.loadMoreComplete();
                                ContentUtil.makeToast(INSTANCE,
                                        getString(R.string.no_result));
                            }
                        }
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BrandDetailBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
                ContentUtil.makeLog("zzzzz", String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }

    private void type(final boolean isLoadding) {
        /**
         * 异步请求方式
         */

        Call<TypeBean> call = HttpServiceClient.getInstance().type(MyApplication.cityId,id);
        call.enqueue(new Callback<TypeBean>() {
            @Override
            public void onResponse(Call<TypeBean> call, Response<TypeBean> response) {
                if (response.isSuccessful()) {
                    TypeBean userBean = response.body();
                    type_data = userBean;
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if (userBean.getStatus().toString().equals("ok")) {
                        if(type_adapter==null){
                            type_adapter=new BrandTypeAdapter(INSTANCE,type_data);
                            list.setAdapter(type_adapter);
                        }else {
                            type_adapter.updateData(type_data,type_postion);
                        }
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TypeBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
                ContentUtil.makeLog("zzzzz", String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
}
