package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.ContentAdapter;
import enjoytouch.com.redstar.adapter.ContentListAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandBean;
import enjoytouch.com.redstar.bean.BrandFollowBean;
import enjoytouch.com.redstar.bean.ContentBean;
import enjoytouch.com.redstar.bean.ContentListBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhaozhao on 16/6/13.
 */
public class ContentActivity extends Activity{

    private ContentActivity INSTANCE;
    private List<ContentBean>datas;
    private ContentListAdapter adapter;
    private ShapeLoadingDialog dialog;
    private String id;
    private int PAGE=1;
    private String SIZE="10";
    private String STATUS="";
    private String fun_place_id="";

    @InjectView(R.id.content_recycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.attention_nothing)
    RelativeLayout nothing_rl;
    @InjectView(R.id.content_rl)
    RelativeLayout send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        id=getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        STATUS=getIntent().getStringExtra("type");
        fun_place_id=getIntent().getStringExtra("id");
        INSTANCE=this;
        ButterKnife.inject(this);
        setViews();
        setListeners();
    }


    private void setViews(){
        datas=new ArrayList<>();
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new ContentListAdapter(INSTANCE,datas);
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
                datas.clear();
                PAGE = 1;
                initData(PAGE, SIZE, 1, false, STATUS);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                PAGE++;
                initData(PAGE, SIZE, 2, false, STATUS);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(INSTANCE,ReleaseActivity.class);
                if("1".equals(STATUS)){
                    intent.putExtra("id",fun_place_id);
                }
                intent.putExtra(GlobalConsts.INTENT_DATA,id);
                intent.putExtra("type",STATUS);
                startActivity(intent);
            }
        });
    }

    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding,String STATUS){
        if(isLoadding)dialog.show();
        Call<ContentListBean> call;
        if("1".equals(STATUS)){
            call= HttpServiceClient.getInstance().shop_comment_list(id,fun_place_id,PAGE, SIZE, MyApplication.token);
        }else{
            call= HttpServiceClient.getInstance().brand_feel(id, PAGE, SIZE, MyApplication.token);
        }

        call.enqueue(new Callback<ContentListBean>() {
            @Override
            public void onResponse(Call<ContentListBean> call, Response<ContentListBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        if (1 == TYPE) {
                            datas.clear();
                            mergeData(response.body().getData());
                            if (response.body().getData().size() > 0) {
                                nothing_rl.setVisibility(View.GONE);
                            } else {
                                nothing_rl.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getData().size() <= 3) {
                                mRecyclerView.setPullRefreshEnabled(false);
                            } else {
                                mRecyclerView.setPullRefreshEnabled(true);
                            }
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
            public void onFailure(Call<ContentListBean> call, Throwable t) {
                if (isLoadding) dialog.dismiss();
                ContentUtil.makeToast(INSTANCE, "加载失败，请联网重试");
            }
        });
    }

    private void mergeData(List<ContentBean>dataEntities){
        if(dataEntities!=null){
            datas.addAll(dataEntities);
            adapter.updateData(datas);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        PAGE=1;
        initData(PAGE,SIZE,1,true,STATUS);
    }
}
