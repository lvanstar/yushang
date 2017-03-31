package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyAttentionAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandBean;
import enjoytouch.com.redstar.bean.BrandFollowBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我关注的品牌
 */
public class MyAttentionActivity extends Activity{
    private MyAttentionActivity INSTANCE;
    private MyAttentionAdapter adapter;
    private List<BrandBean>datas;
    private ShapeLoadingDialog dialog;
    private int PAGE=1;
    private String SIZE="10";


    @InjectView(R.id.myattention_recy)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.attention_nothing)
    RelativeLayout nothing_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        INSTANCE=this;
        ButterKnife.inject(this);

        setListeners();
    }

    private void setViews(){
        datas=new ArrayList<>();

        mRecyclerView.setLayoutManager(new GridLayoutManager(INSTANCE, 2));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        adapter=new MyAttentionAdapter(INSTANCE,datas);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListeners(){

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        PAGE=1;
        initData(PAGE, SIZE, 1, true);
    }


    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding){
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        if(isLoadding)dialog.show();
        Call<BrandFollowBean> call= HttpServiceClient.getInstance().brand_follow(MyApplication.token, PAGE, SIZE);
        call.enqueue(new Callback<BrandFollowBean>() {
            @Override
            public void onResponse(Call<BrandFollowBean> call, Response<BrandFollowBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        if (1 == TYPE) {
                            setViews();
//                            datas.clear();
                            mergeData(response.body().getData());
                            if(response.body().getData().size()>0){
                                nothing_rl.setVisibility(View.GONE);
                            }else{
                                nothing_rl.setVisibility(View.VISIBLE);
                            }
//                            if(response.body().getData().size()<=5){
//                                mRecyclerView.setPullRefreshEnabled(false);
//                            }else {
//                                mRecyclerView.setPullRefreshEnabled(true);
//                            }
                        } else {
                            if (response.body().getData().size() != 0) {
                                mergeData(response.body().getData());
                                mRecyclerView.loadMoreComplete();
                            } else {
                                mRecyclerView.loadMoreComplete();
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
            public void onFailure(Call<BrandFollowBean> call, Throwable t) {
                if (isLoadding) dialog.dismiss();
                ContentUtil.makeToast(INSTANCE, "加载失败，请联网重试");
            }
        });
    }

    private void mergeData(List<BrandBean>dataEntities){
        if(dataEntities!=null){
            datas.addAll(dataEntities);
            adapter.updateData(datas);
        }

    }
}
