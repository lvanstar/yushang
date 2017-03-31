package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyTheOderAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.OrderListBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//我的订单列表
public class MyTheOderActivity extends Activity {
    private MyTheOderAdapter adapter;
    private MyTheOderActivity INSTANCE;
    private static String SIZE="10";
    private static int PAGE=1;
    private List<OrderListBean.DataEntity>datas=new ArrayList<>();
   // private ShapeLoadingDialog dialog;

    @InjectView(R.id.my_order_recycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.sr_nothing)
    RelativeLayout nothing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_the_oder);
       // MobclickAgent.onEvent(getApplicationContext(), "v2_lookMyOrderList");
        INSTANCE=this;
        ButterKnife.inject(this);
        initData(PAGE,SIZE,1,true);
        setListener();
    }

    private void setViews(){
        datas=new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new MyTheOderAdapter(INSTANCE,datas);
        mRecyclerView.setAdapter(adapter);

    }
    private void setListener(){
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    protected void onResume() {
        super.onResume();
        PAGE=1;
        initData(PAGE, SIZE, 1, true);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding){
//        dialog = new ShapeLoadingDialog(INSTANCE);
//        dialog.setLoadingText(getString(R.string.loaddings));
//        if(isLoadding)dialog.show();
        Call<OrderListBean>call= HttpServiceClient.getInstance().user_orderList(MyApplication.token,PAGE,SIZE);
        call.enqueue(new Callback<OrderListBean>() {
            @Override
            public void onResponse(Call<OrderListBean> call, Response<OrderListBean> response) {
              //   dialog.dismiss();
                if (response.isSuccessful()) {

                    if ("ok".equals(response.body().getStatus())) {
                        if (1 == TYPE) {
                            setViews();
//                            datas.clear();
                            mergeData(response.body().getData());
                                if(response.body().getData().size()>0){

                                    nothing.setVisibility(View.GONE);
                                }else {
                                    nothing.setVisibility(View.VISIBLE);
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
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                } else {
                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                }
            }

            @Override
            public void onFailure(Call<OrderListBean> call, Throwable t) {

                // dialog.dismiss();
            }
        });
    }

    private void mergeData(List<OrderListBean.DataEntity>data){
        if(data!=null){
            datas.addAll(data);
            adapter.updateData(datas);
        }
    }
}
