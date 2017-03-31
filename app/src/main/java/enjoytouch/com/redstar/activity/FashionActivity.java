package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.FashionAdapter;
import enjoytouch.com.redstar.adapter.StyleAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.FundDatilBean;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.bean.ProductListBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FashionActivity extends Activity {
    private FashionActivity INSTANCE;
    private FashionAdapter adapter;
    private ProductListBean list;
    private ShapeLoadingDialog dialog;
    private Dialog dialog2;
    private String id;
    private String name="全部";
    private static String SIZE="6";
    private int PAGE=1;
    private int SIZE2=1;
    private int TYPE=1;
    private Boolean loading=true;
    private StyleAdapter style_adapter;
    private int style_postion=0;
    private StyleBean style_data;
    private String style_id;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.lv)
    XRecyclerView lv;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.pass)  //筛选
    TextView pass;
    @InjectView(R.id.ll2)
    LinearLayout ll2;
    @InjectView(R.id.rl_shou2)
    RelativeLayout shou;
    @InjectView(R.id.alpha2)
    RelativeLayout alpha;
    @InjectView(R.id.zhankai2_gv)
    GridView gv;
    @InjectView(R.id.attention_nothing)
    RelativeLayout attention_nothing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion);
        INSTANCE=this;
       // id=getIntent().getStringExtra("id");
       // name=getIntent().getStringExtra("name");
        ButterKnife.inject(this);
        setViews();
    }

    private void setViews() {
        up_iv.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        dialog2=DialogUtil.createLoadingDialog(INSTANCE,"正在加载");
        setData(true);
    }
    private void setViews2(){
        text.setText(name);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(linearLayoutManager);
        lv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        View view=new View(INSTANCE);
        lv.addFootView(view);
        adapter=new FashionAdapter(INSTANCE,list);
        lv.setAdapter(adapter);
        setListener();
    }
    private void setListener(){
        lv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                SIZE2=1;
                SIZE="6";
                TYPE=1;
                up_iv.setVisibility(View.GONE);
                setData(false);
                lv.refreshComplete();
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
                lv.smoothScrollToPosition(0);
                up_iv.setVisibility(View.GONE);
            }
        });
        lv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm= (LinearLayoutManager) lv.getLayoutManager();
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
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style();        //筛选
                ll2.setVisibility(View.VISIBLE);
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll2.setVisibility(View.GONE);
            }
        });
        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll2.setVisibility(View.GONE);
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ll2.setVisibility(View.GONE);
                style_postion=i;
                if(i==0){
                    style_id="0";
                    text.setText("全部");
                }else {
                    style_id=style_data.getData().get(i-1).getId();
                    text.setText(style_data.getData().get(i-1).getStyle_name());
                }
                style_adapter.updateData(style_data,i);
                SIZE="6";
                setData(false);
            }
        });
    }

    private void setData(final boolean isLoadding) {
        /**
         * 异步请求方式
         */
        if(isLoadding) dialog.show();
        Call<ProductListBean> call= HttpServiceClient.getInstance().product_list(MyApplication.cityId,style_id,PAGE,SIZE);
        ContentUtil.makeLog("city_id",MyApplication.cityId);
        call.enqueue(new Callback<ProductListBean>() {
            @Override
            public void onResponse(Call<ProductListBean> call, Response<ProductListBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    ProductListBean userBean = response.body();
                    if(userBean.getData().size()>0){
                        attention_nothing.setVisibility(View.GONE);
                    }else {
                        attention_nothing.setVisibility(View.VISIBLE);
                    }
                    list=userBean;
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if (1 == TYPE) {
                        if (isLoadding){
                            setViews2();
                        }else {
                            adapter.updateData(list);
                        }
                    }else {
                        if (response.body().getData().size() != 0) {
                            adapter.updateData(list);
                            lv.loadMoreComplete();
                        } else {
                            lv.loadMoreComplete();
                            ContentUtil.makeToast(INSTANCE,
                                    getString(R.string.no_result));
                        }
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ProductListBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }

    private void style(){       //筛选
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
                            gv.setAdapter(style_adapter);
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
}
