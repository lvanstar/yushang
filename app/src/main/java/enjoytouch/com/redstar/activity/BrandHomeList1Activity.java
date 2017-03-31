package enjoytouch.com.redstar.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.progressindicator.OnScrollChangedCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.BrandListadapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CollectBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.selfview.LLinearLayoutManager;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.ShareUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandHomeList1Activity extends Activity {
    private String id;
    private BrandHomeList1Activity INSTANCE;
    private BrandListadapter adapter;
    private List<ZhuanTiBean.DataBean.CoolShopBean>datas;
    private ZhuanTiBean.DataBean bean;
    private static String SIZE="5";
    private static int PAGE=1;
    private ShapeLoadingDialog dialog;
    private Boolean loading=true;
    private int a=0;


    @InjectView(R.id.brand_home_list1_xrecycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.share)
    ImageView share;
    @InjectView(R.id.zan)
    ImageView zan;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.home)
    RelativeLayout line;
    @InjectView(R.id.collect_cnt)
    TextView collect_cnt;
    LLinearLayoutManager linearLayoutManager;
    @InjectView(R.id.details_rl)
    RelativeLayout details_rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_home_list1);
        INSTANCE=this;
        ButterKnife.inject(this);
        id=getIntent().getStringExtra("id");

        initData(PAGE,SIZE,1,true);
    }
    @TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
    private void setViews() {
        datas=new ArrayList<>();

        linearLayoutManager = new LLinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        /*mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);*/
        mRecyclerView.setPullRefreshEnabled(false);
        /*mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setIsnomore(true);
        View view=new View(INSTANCE);
        mRecyclerView.addFootView(view);*/

//        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        /*View header= LayoutInflater.from(INSTANCE).inflate(R.layout.item_brand_list_head,null);
        SimpleDraweeView header_iv= (SimpleDraweeView) header.findViewById(R.id.brand_home_list2_sv);
        TextView name= (TextView) header.findViewById(R.id.brand_list_head_name);
        TextView value= (TextView) header.findViewById(R.id.brand_list_head_title);
        header_iv.setImageURI(Uri.parse(bean.getCover_img()));
        name.setText(bean.getName());
        value.setText(bean.getTitle());
        if(a==0){
            mRecyclerView.addHeaderView(header);
        }*/
        if(bean.getCool_shop().size()>0){
            adapter=new BrandListadapter(INSTANCE,bean.getCool_shop(),id,bean);
            mRecyclerView.setAdapter(adapter);
        }
        zan.setImageResource(("1".equals(bean.is_collection) ? R.drawable.icon2_1 : R.drawable.icon2_6));
        collect_cnt.setText(bean.collect_cnt);
        //a++;
    }

    private void setListener() {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "SpecialInterestingShareAction");
                ShareUtil.share(INSTANCE, line, bean.getName(), bean.getTitle(), "3", "http://www.yushang001.com/home/qcshare/qclist"+"?"+"id="+id+"&"+"city_id="+MyApplication.cityId, bean.getCover_img());
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "SpecialInterestingCollectAction");
                collect();
            }
        });
        mRecyclerView.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                if (linearLayoutManager!=null) {
                    ContentUtil.makeLog("yc", " getScrollY()==" + linearLayoutManager.getScrollY());
                    float newAlpha = (float) linearLayoutManager.getScrollY() / 600;
                    details_rl.setAlpha(newAlpha);
                }
            }
        });
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager lm= (LinearLayoutManager) mRecyclerView.getLayoutManager();
////                int visibleItemCount = lm.getChildCount();
////                int totalItemCount = lm.getItemCount();
//                int pastVisiblesItems = lm.findFirstVisibleItemPosition();
//                float newAlpha = (float) dy / 500;
////                details_rl.setAlpha(newAlpha);
////                float newAlpha = (float) pastVisiblesItems / 5;
////                details_rl.setAlpha(newAlpha);
//                ContentUtil.makeLog("第一个item的位置",String.valueOf(pastVisiblesItems));
//                if (loading) {
//                    if(pastVisiblesItems==0||pastVisiblesItems==1||pastVisiblesItems==2){
////                        details_rl.getBackground().setAlpha(0);
//                        up_iv.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
                up_iv.setVisibility(View.GONE);
            }
        });
      /*  mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                PAGE = 1;
                up_iv.setVisibility(View.GONE);
                initData(PAGE, SIZE, 1, false);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                PAGE++;
                if (PAGE > 1) {
                    up_iv.setVisibility(View.VISIBLE);
                }
                initData(PAGE, SIZE, 2, false);
            }
        });*/

//        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                datas.clear();
//                PAGE = 1;
//                up_iv.setVisibility(View.GONE);
//                initData(PAGE, SIZE, 1, false);
//                mRecyclerView.refreshComplete();
//            }
//
//            @Override
//            public void onLoadMore() {
//                PAGE++;
//        if(PAGE>1){
//            up_iv.setVisibility(View.VISIBLE);
//       }
//                initData(PAGE, SIZE, 2, false);
//            }
//        });

    }
    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding){
        /**
         * 异步请求方式
         */
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        if(isLoadding)dialog.show();
        Call<ZhuanTiBean> call= HttpServiceClient.getInstance().quchu_zhuanti(MyApplication.token,MyApplication.cityId,id);
        call.enqueue(new Callback<ZhuanTiBean>() {
            @Override
            public void onResponse(Call<ZhuanTiBean> call, Response<ZhuanTiBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("ok")) {
//                            if (response.body().getData().getCool_shop().size() != 0){
                                bean=response.body().getData();
//                                mergeData(response.body().getData().getCool_shop());

                                setViews();
                                setListener();
//                            }
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ZhuanTiBean> call, Throwable t) {
                ContentUtil.makeToast(INSTANCE, "加载失败，请联网重试");
            }
        });
    }

//    private void mergeData(List<ZhuanTiBean.DataBean.CoolShopBean>data){
//        if(data!=null){
//            datas.addAll(data);
//            adapter.updateData(datas);
//        }
//
//    }

    private void collect() {
        /**
         * 异步请求方式
         */

        Call<CollectBean> call= HttpServiceClient.getInstance().quchu_collect(id, MyApplication.token);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        zan.setImageResource(("1".equals(response.body().getData().getCollect_status()) ? R.drawable.icon2_1 : R.drawable.icon2_6));
                        collect_cnt.setText(response.body().getData().getCollect_cnt());
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz", String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
}
