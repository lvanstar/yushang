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

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.FundAdapter;
import enjoytouch.com.redstar.adapter.FundType1Adapter;
import enjoytouch.com.redstar.adapter.FundType2Adapter;
import enjoytouch.com.redstar.adapter.StyleAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.FundListBean;
import enjoytouch.com.redstar.bean.FundTypeBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 为你发现列表
 * */
public class FoundListActivity extends Activity {
    private static String SIZE="6";
    private int PAGE=1;
    private int SIZE2=1;
    private int TYPE=1;
    private FundListBean list;
    private FundAdapter adapter;
    private FundType1Adapter type1_adapter;
    private List<FundTypeBean.DataBean> type1_data;
    private List<FundTypeBean.DataBean.ChildrendBean> type2_data;
    private StyleBean style_data;
    private int type1_postion=0;
    private int type2_postion=0;
    private int style_postion=0;
    private FundType2Adapter type2_adapter;
    private StyleAdapter style_adapter;
    private ShapeLoadingDialog dialog;
    private Dialog dialog2;
    private String category;
    private String style_id;
    private FoundListActivity INSTANCE;
    private Boolean loading=true;
    @InjectView(R.id.back)
    View back;

    @InjectView(R.id.ll_type_buzhan)    //不展开时的整个布局
    LinearLayout buzhan;
    @InjectView(R.id.ll_type_zhankai1) //展开类别时的布局
    LinearLayout zhankai1;
    @InjectView(R.id.ll_type_zhankai2)  //展开风格时的布局
    LinearLayout zhankai2;


    @InjectView(R.id.buzhan_button1)
    RelativeLayout button1;     //未展开时的类别按钮
    @InjectView(R.id.text1)
    TextView text1;
    @InjectView(R.id.buzhan_button2)
    RelativeLayout button2;     //未展开时的风格按钮
    @InjectView(R.id.text2)
    TextView text2;
    @InjectView(R.id.gv)
    XRecyclerView gv;                //未展开时的gridview

    @InjectView(R.id.zhankai1_button1)//展开类别时的类别按钮
    RelativeLayout zhankai1_button1;
    @InjectView(R.id.zhankai1_text1)
    TextView zhankai1_text1;
    @InjectView(R.id.zhankai1_text2)
    TextView zhankai1_text2;
    @InjectView(R.id.zhankai1_button2)//展开类别时的风格按钮
    RelativeLayout zhankai1_button2;
    @InjectView(R.id.shou)
    ImageView shou;
    @InjectView(R.id.list1)         //展开类别时的左边listview
    ListView list1;
    @InjectView(R.id.list2)         //展开类别时的右边listview
    ListView list2;
    @InjectView(R.id.alpha1)
    LinearLayout alpha1;

    @InjectView(R.id.zhankai2_button1)//展开风格时的类别按钮
    RelativeLayout zhankai2_button1;
    @InjectView(R.id.zhankai2_text1)
    TextView zhankai2_text1;
    @InjectView(R.id.zhankai2_button2)//展开风格时的风格按钮
    RelativeLayout zhankai2_button2;
    @InjectView(R.id.zhankai2_text2)
    TextView zhankai2_text2;
    @InjectView(R.id.zhankai2_gv)     //展开风格时的gridview
    GridView zhankai_gv;
    @InjectView(R.id.shou2)
    ImageView shou2;
    @InjectView(R.id.alpha2)
    RelativeLayout alpha2;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.attention_nothing)
    RelativeLayout attention_nothing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        INSTANCE=this;
        ButterKnife.inject(this);
        setViews();
    }
    private void setViews() {
        up_iv.setVisibility(View.GONE);
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        dialog2=DialogUtil.createLoadingDialog(INSTANCE,"正在加载");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                INSTANCE.finish();
            }
        });
    }
    private void setViews2(){
        buzhan.setVisibility(View.VISIBLE);
        zhankai1.setVisibility(View.GONE);
        zhankai2.setVisibility(View.GONE);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(INSTANCE,2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gv.setLayoutManager(linearLayoutManager);
        gv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        gv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //gv.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new FundAdapter(INSTANCE,list);
        gv.setAdapter(adapter);
        setListener();
    }

    private void setListener() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsCategory");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.VISIBLE);
                zhankai2.setVisibility(View.GONE);
                type();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsStyle");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.VISIBLE);
                style();
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
                style();
            }
        });
        zhankai2_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.VISIBLE);
                zhankai2.setVisibility(View.GONE);
                type();
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

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type1_adapter.updateData(type1_data,position);
                type1_postion=position;
                type2_data=type1_data.get(position).getChildrend();
                type2_postion=0;
                type2_adapter.updateData(type2_data,type2_postion);
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterLineProducts");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                type2_postion=position;
                type2_adapter.updateData(type2_data,type2_postion);
                if(position==0){
                    category="0";
                    text1.setText("全部品类");
                    zhankai1_text1.setText("全部品类");
                    zhankai2_text1.setText("全部品类");
                }else {
                    category=type2_data.get(type2_postion).getParentid()+","+type2_data.get(type2_postion).getId();
                    text1.setText(type2_data.get(type2_postion).getName());
                    zhankai1_text1.setText(type2_data.get(type2_postion).getName());
                    zhankai2_text1.setText(type2_data.get(type2_postion).getName());
                }
                style_id="0";
                SIZE="6";
                setData(false);
            }
        });
        zhankai_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "filterLineProducts");
                buzhan.setVisibility(View.VISIBLE);
                zhankai1.setVisibility(View.GONE);
                zhankai2.setVisibility(View.GONE);
                style_postion=i;
                if(i==0){
                    style_id="0";
                    text2.setText("全部");
                    zhankai1_text2.setText("全部");
                    zhankai2_text2.setText("全部");
                }else {
                    style_id=style_data.getData().get(i-1).getId();
                    text2.setText(style_data.getData().get(i-1).getStyle_name());
                    zhankai1_text2.setText(style_data.getData().get(i-1).getStyle_name());
                    zhankai2_text2.setText(style_data.getData().get(i-1).getStyle_name());
                }
                category="0";
                style_adapter.updateData(style_data,i);
                SIZE="6";
                setData(false);
            }
        });

        gv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                SIZE2=1;
                TYPE=1;
                SIZE="6";
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
    }
    private void setData(final boolean isLoadding){
        /**
         * 异步请求方式
         */
        if(isLoadding){
            dialog.show();
        }else {
            dialog2.show();
        }
        Call<FundListBean> call= HttpServiceClient.getInstance().fund_list(MyApplication.cityId,MyApplication.token,PAGE,SIZE,category,style_id);
        call.enqueue(new Callback<FundListBean>() {
            @Override
            public void onResponse(Call<FundListBean> call, Response<FundListBean> response) {
                if(isLoadding){
                    dialog.dismiss();
                }else {
                    dialog2.dismiss();
                }
                if (response.isSuccessful()) {
                    FundListBean userBean = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if(userBean.getStatus().toString().equals("ok")){
                        if(userBean.getData().size()>0){
                            attention_nothing.setVisibility(View.GONE);
                        }else {
                            attention_nothing.setVisibility(View.VISIBLE);
                        }

                        if (1 == TYPE) {
                            if(isLoadding){
                                list=userBean;
                                setViews2();
                            }else {
                                adapter.updateData(userBean);
                            }
                        }else {
                            if (userBean.getData().size() != 0) {
                                adapter.updateData(userBean);
                                gv.loadMoreComplete();
                            } else {
                                gv.loadMoreComplete();
                                ContentUtil.makeToast(INSTANCE,
                                        getString(R.string.no_result));
                            }
                        }
                    }else {
                        Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FundListBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
    private void type(){        //类别
        dialog2.show();
        Call<FundTypeBean> call= HttpServiceClient.getInstance().fund_type(MyApplication.cityId);
        call.enqueue(new Callback<FundTypeBean>() {
            @Override
            public void onResponse(Call<FundTypeBean> call, Response<FundTypeBean> response) {
                if (response.isSuccessful()) {
                    final FundTypeBean userBean = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if(userBean.getStatus().toString().equals("ok")){
                        dialog2.dismiss();
                        type1_data=userBean.getData();
                        if(type1_data.size()!=0){
                            type2_data=type1_data.get(type1_postion).getChildrend();
                            if(type1_adapter==null){
                                type1_adapter=new FundType1Adapter(INSTANCE,type1_data);
                                list1.setAdapter(type1_adapter);
                                type2_adapter=new FundType2Adapter(INSTANCE,type1_data.get(0).getChildrend());
                                list2.setAdapter(type2_adapter);
                            }else {
                                type1_adapter.updateData(type1_data,type1_postion);
                                type2_adapter.updateData(type2_data,type2_postion);
                            }
                        }
                    }else {
                        Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FundTypeBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
    private void style(){       //风格
        dialog2.show();
        Call<StyleBean> call= HttpServiceClient.getInstance().style("2",MyApplication.cityId);
        call.enqueue(new Callback<StyleBean>() {
            @Override
            public void onResponse(Call<StyleBean> call, Response<StyleBean> response) {
                if (response.isSuccessful()) {
                    style_data = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(style_data));
                    if(style_data.getStatus().toString().equals("ok")){
                        dialog2.dismiss();
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

    @Override
    protected void onResume() {
        super.onResume();
        setData(true);
    }
}
