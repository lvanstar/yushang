package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import enjoytouch.com.redstar.adapter.ShopAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.InterestBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我关注的趣处
 */
public class MyQuChuActivity extends Activity {

    private MyQuChuActivity INSTANCE;
    private static String SIZE="5";
    private static int PAGE=1;
    private ShapeLoadingDialog dialog;
    private List<InterestBean.DataEntity> datas;
    private ShopAdapter adapter;
    @InjectView(R.id.myquchu_xrecy)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.Myquchu)
    RelativeLayout quchu_r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myquchu);
        ButterKnife.inject(this);
        INSTANCE=this;

        setListeners();
    }

    private void setViews(){
        datas=new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        adapter=new ShopAdapter(INSTANCE,datas);
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
        Call<InterestBean> call= HttpServiceClient.getInstance().user_funplace_collection(MyApplication.token, PAGE, SIZE);
        call.enqueue(new Callback<InterestBean>() {
            @Override
            public void onResponse(Call<InterestBean> call, Response<InterestBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        if (1 == TYPE) {
                            setViews();
//                            datas.clear();
                            mergeData(response.body().getData());
                            if(response.body().getData().size()>0){
                                quchu_r1.setVisibility(View.GONE);
                            }else{
                                quchu_r1.setVisibility(View.VISIBLE);
                            }
//                            if(response.body().getData().size()<=2){
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

                        ContentUtil.makeToast(INSTANCE, "请联网重试");
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
            public void onFailure(Call<InterestBean> call, Throwable t) {
                if (isLoadding) dialog.dismiss();
                ContentUtil.makeToast(INSTANCE, "加载失败，请联网重试");
            }
        });
    }

    private void mergeData(List<InterestBean.DataEntity>data){
        if(data!=null){
            datas.addAll(data);
            adapter.updateData(datas);
        }

    }

}
