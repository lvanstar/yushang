package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.RecommendShopAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.RecommendListBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我推荐的趣处
 */
public class RecommendShopActivity extends Activity {
    private RecommendShopActivity INSTANCE;
    private RecommendShopAdapter adapter;
    private List<RecommendListBean.DataEntity> datas;
    private ShapeLoadingDialog dialog;
    private int PAGE=1;
    private String SIZE="5";
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.recommend)
    TextView recommend;
    @InjectView(R.id.recommend_shop_xrecy)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.MyRecommd)
    RelativeLayout recommind_r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_shop);
        INSTANCE=this;
        ButterKnife.inject(this);

        setListener();

    }

    private void setViews() {
        datas=new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new RecommendShopAdapter(INSTANCE, datas);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "toRecommend");
                Intent intent=new Intent(INSTANCE,RecommendActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                datas.clear();
                PAGE = 1;
                initData(PAGE, SIZE, 1, false);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                PAGE++;
                initData(PAGE, SIZE, 2, false);
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding){
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        if(isLoadding)dialog.show();
        Call<RecommendListBean> call= HttpServiceClient.getInstance().shop_commend_list(MyApplication.token,PAGE,SIZE);
        call.enqueue(new Callback<RecommendListBean>() {
            @Override
            public void onResponse(Call<RecommendListBean> call, Response<RecommendListBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        if (1 == TYPE) {
                            setViews();
//                            datas.clear();
                            mergeData(response.body().getData());
                            if(response.body().getData().size()>0){
                                recommind_r1.setVisibility(View.GONE);
                            }else{
                                recommind_r1.setVisibility(View.VISIBLE);
                            }
//                            if(response.body().getData().size()<=1){
//                                mRecyclerView.setPullRefreshEnabled(false);
//                            }else {
//                                mRecyclerView.setPullRefreshEnabled(true);
//                            }
                        } else {
                            if (response.body().getData().size() != 0) {
                                mergeData(response.body().getData());
                            } else {

                                ContentUtil.makeToast(INSTANCE,
                                        getString(R.string.no_result));
                            }
                        }

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
            public void onFailure(Call<RecommendListBean> call, Throwable t) {
                if (isLoadding) dialog.dismiss();
            }
        });
    }

    private void mergeData(List<RecommendListBean.DataEntity>data){
        if(data!=null){
            datas.addAll(data);
            adapter.updateData(datas);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        PAGE=1;
        initData(PAGE, SIZE, 1, true);
    }
}
