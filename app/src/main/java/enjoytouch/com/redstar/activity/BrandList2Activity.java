package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyBrandAdapter;
import enjoytouch.com.redstar.adapter.StyleAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandList2Activity extends Activity {
    private BrandList2Activity INSTANCE;
    private BrandDetailBean data;
    private StyleBean style_data;
    private MyBrandAdapter adapter;
    private StyleAdapter style_adapter;
    private int style_postion;
    private ShapeLoadingDialog dialog;
    private Dialog dialog2;
    private String style_id;        //风格id
    private String order_by;        //排序
    private static String SIZE="6";
    private int PAGE=1;
    private int SIZE2=1;
    private int TYPE=1;
    private Boolean loading=true;
    private String brand_id;        //品牌id
    private String category_id;     //类别id
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.ll_type_buzhan)    //不展开时的整个布局
    LinearLayout buzhan;
    @InjectView(R.id.ll_type_zhankai1) //展开风格时的布局
    LinearLayout zhankai1;
    @InjectView(R.id.ll_type_zhankai2)  //展开排序时的布局
    LinearLayout zhankai2;


    @InjectView(R.id.buzhan_button1)
    RelativeLayout button1;     //未展开时的风格按钮
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.buzhan_button2)
    RelativeLayout button2;     //未展开时的排序按钮
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.gv)
    XRecyclerView gv;           //未展开时的gridview

    @InjectView(R.id.zhankai1_button1)//展开风格时的风格按钮
    RelativeLayout zhankai1_button1;
    @InjectView(R.id.zhankai1_text1)
    TextView zhankai1_text1;
    @InjectView(R.id.zhankai1_button2)//展开风格时的排序按钮
     RelativeLayout zhankai1_button2;
    @InjectView(R.id.zhankai1_text2)
    TextView zhankai1_text2;
    @InjectView(R.id.shou)
    ImageView shou;
    @InjectView(R.id.zhankai_gv)         //展开风格时的gridview
    GridView zhankai_gv;
    @InjectView(R.id.alpha)
    RelativeLayout alpha1;

    @InjectView(R.id.zhankai2_button1)//展开排序时的风格按钮
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list2);
        INSTANCE=this;
        ButterKnife.inject(this);
        style_id = getIntent().getStringExtra("id");
        setViews();
    }

    private void setViews() {
        up_iv.setVisibility(View.GONE);
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        dialog2=DialogUtil.createLoadingDialog(INSTANCE,"正在加载");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        GridLayoutManager linearLayoutManager = new GridLayoutManager(INSTANCE,2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gv.setLayoutManager(linearLayoutManager);
        gv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        gv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //gv.setArrowImageView(R.drawable.iconfont_downgrey);
        setData(true);
    }

    private void setListerner() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "styleFilterBrand");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.VISIBLE);
                zhankai2.setVisibility(View.GONE);
                style();
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
                style();
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
                SIZE="6";
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
                SIZE="6";
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
                SIZE="6";
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
                SIZE="6";
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
                TYPE=2;
                if(SIZE2>1){
                    up_iv.setVisibility(View.VISIBLE);
                }
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
        zhankai_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterBrand");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                style_postion=position;
                if(position==0){  //如果点击的是第一个item
                    style_id="0";
                    text1.setText("全部");
                    zhankai1_text1.setText("全部");
                    zhankai2_text1.setText("全部");
                }else {
                    style_id=style_data.getData().get(position-1).getId();
                    text1.setText(style_data.getData().get(position-1).getStyle_name());
                    zhankai1_text1.setText(style_data.getData().get(position-1).getStyle_name());
                    zhankai2_text1.setText(style_data.getData().get(position-1).getStyle_name());
                }
                style_adapter.updateData(style_data,position);
                SIZE="6";
                setData(false);
            }
        });
    }
    private void setViews2(){
        adapter=new MyBrandAdapter(INSTANCE,data,"0");
        gv.setAdapter(adapter);
        setListerner();
    }

    private void style(){       //风格
        dialog2.show();
        Call<StyleBean> call= HttpServiceClient.getInstance().style("1", MyApplication.cityId);
        call.enqueue(new Callback<StyleBean>() {
            @Override
            public void onResponse(Call<StyleBean> call, Response<StyleBean> response) {
                if (response.isSuccessful()) {
                    dialog2.dismiss();
                    style_data = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(style_data));
                    if(style_data.getStatus().toString().equals("ok")){
                        if(style_adapter==null){
                            style_adapter=new StyleAdapter(INSTANCE,style_data);
                            zhankai_gv.setAdapter(style_adapter);
                        }else {
                            style_adapter.updateData(style_data,style_postion);
                        }
                    }else {
                        Dialog dialog = DialogUtil.show(INSTANCE, style_data.getError().getMessage().toString(), false);
                        dialog.show();
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<StyleBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
    private void setData(final boolean isLoadding){
        /**
         * 异步请求方式
         */
        if(isLoadding) dialog.show();
        Call<BrandDetailBean> call = HttpServiceClient.getInstance().brand_list(style_id,order_by,SIZE,PAGE,"",category_id,"",MyApplication.cityId);
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
}
