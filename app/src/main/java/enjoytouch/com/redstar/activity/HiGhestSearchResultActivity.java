package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.HiGhestAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.HiGhestBean;
import enjoytouch.com.redstar.bean.MyCollectionBean;
import enjoytouch.com.redstar.selfview.CustomFastScrollView;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 上品搜索结果类
 */
public class HiGhestSearchResultActivity extends Activity {
    private String key;
    private HiGhestSearchResultActivity INSTANCE;
    private HiGhestAdapter adapter;
    private List<MyCollectionBean>datas;
    private int PAGE=1;
    private String SIZE="5";

    @InjectView(R.id.hifhest_result_recycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.result_te_keyword)
    TextView te_keyword;
    @InjectView(R.id.result_sr_nothing)
    RelativeLayout nothing_Rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_search_result);
        key=getIntent().getStringExtra("key");
        ContentUtil.makeLog("lzz","key=="+key);
        INSTANCE=this;
        ButterKnife.inject(this);
        init();

        setListeners();
        initData(PAGE, SIZE, 1);
    }


    private void setViews(){

        datas=new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(INSTANCE,2));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new HiGhestAdapter(INSTANCE,datas,"2");
        mRecyclerView.setAdapter(adapter);
    }

    private void setListeners(){

        //回退
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.si_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                finish();
            }
        });


        te_keyword.setOnClickListener(new View.OnClickListener() {
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
                initData(PAGE, SIZE, 1);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                PAGE++;
                initData(PAGE, SIZE, 2);
            }
        });
    }
    private void init() {
        te_keyword.setText(key + "");
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initData(int PAGE, final String SIZE, final int TYPE){
        Call<HiGhestBean>call= HttpServiceClient.getInstance().product_index(MyApplication.token,MyApplication.cityId,PAGE,SIZE,key);
        call.enqueue(new Callback<HiGhestBean>() {
            @Override
            public void onResponse(Call<HiGhestBean> call, Response<HiGhestBean> response) {
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        if (1 == TYPE) {
                            setViews();
                            if(response.body().getData().size()>0){
                                nothing_Rl.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }else{
                                nothing_Rl.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                            }
//                            datas.clear();
                            mergeData(response.body().getData());
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
            public void onFailure(Call<HiGhestBean> call, Throwable t) {
                ContentUtil.makeToast(INSTANCE, "加载失败，请联网重试");
            }
        });
    }

    private void mergeData(List<MyCollectionBean>dataEntities){
        if(dataEntities!=null){
            datas.addAll(dataEntities);
            adapter.updateData(datas);
        }

    }

}
