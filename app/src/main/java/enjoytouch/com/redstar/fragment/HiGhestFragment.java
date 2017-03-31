package enjoytouch.com.redstar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import enjoytouch.com.redstar.activity.HiGhestSearchActivity;
import enjoytouch.com.redstar.activity.ShoppingCartActivity;
import enjoytouch.com.redstar.adapter.HiGhestAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.HiGhestBean;
import enjoytouch.com.redstar.bean.MyCollectionBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 上品列表
 */
public class HiGhestFragment extends BaseFragment{

    private View view;
    private HiGhestAdapter adapter;
    private int PAGE=1;
    private String SIZE="6";
    private List<MyCollectionBean>datas;
    private ShapeLoadingDialog dialog;
    private String car_status="";
    private boolean isOldLogin=false;
    private Boolean loading=true;
    private int TYPE=1;

    @InjectView(R.id.highest_recycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.highest_serch)
    RelativeLayout serch;
    @InjectView(R.id.higest_car)
    RelativeLayout car;//购物车
    @InjectView(R.id.higest_car_Tv)
    TextView higest_car_Tv;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_highest, null);//实例化fragment

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.inject(this, view);


        setListeners();

    }

    @Override
    public void onRes() {

    }

    @Override
    public void initData() {
//        initData(PAGE,SIZE,1,true);
    }

    @Override
    public void resetUserData() {

//        initData(PAGE,SIZE,1,true);
    }

    @Override
    public void removeUserData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        initData(PAGE, SIZE,true);
    }

    private void setViews(){
        up_iv.setVisibility(View.GONE);
        datas=new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new HiGhestAdapter(getActivity(),datas,"2");
        mRecyclerView.setAdapter(adapter);
    }

    private void setListeners(){

        /**
         * 搜索
         */
        serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(getActivity().getApplicationContext(),"searchAction");
                Intent intent = new Intent(getActivity(), HiGhestSearchActivity.class);
                startActivity(intent);
            }
        });

        /**
         *
         * 购物车
         */
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(getActivity().getApplicationContext(),"shoppingCart");
                if (AccountUtil.showLoginView(getActivity().getApplicationContext())) {
                    isOldLogin=true;
                    return;
                }
                Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                datas.clear();
                PAGE=1;
                TYPE=1;
                SIZE="6";
                up_iv.setVisibility(View.GONE);
                initData(PAGE,SIZE,false);
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                PAGE++;
                TYPE=2;
                SIZE=String.valueOf(PAGE*6);
                if(PAGE>1){
                    up_iv.setVisibility(View.VISIBLE);
                }
                initData(PAGE,SIZE,false);
            }
        });
        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
                up_iv.setVisibility(View.GONE);
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm= (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisiblesItems = lm.findFirstVisibleItemPosition();
                if (loading) {
                    if(pastVisiblesItems==0||pastVisiblesItems==1||pastVisiblesItems==2){
                        up_iv.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public void initData(final int page, final String SIZE, final boolean isLoadding){
        dialog=new ShapeLoadingDialog(getActivity());
        dialog.setLoadingText(getString(R.string.loaddings));
        if(isLoadding&&isOldLogin==false)dialog.show();
        Call<HiGhestBean>call= HttpServiceClient.getInstance().product_index(MyApplication.token,MyApplication.cityId,1,SIZE,"");
        call.enqueue(new Callback<HiGhestBean>() {
            @Override
            public void onResponse(Call<HiGhestBean> call, Response<HiGhestBean> response) {
                if(isLoadding&&isOldLogin==false)dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {

                        if (1 == TYPE) {
                            setViews();
                            higest_car_Tv.setVisibility("0".equals(response.body().getCar_status()) ? View.GONE : View.VISIBLE);
//                            datas.clear();
                            mergeData(response.body().getData());
                        } else {
                            ContentUtil.makeLog("lzz","=========");
                            if (response.body().getData().size() != 0) {
                                mergeData(response.body().getData());
                                mRecyclerView.loadMoreComplete();
                            } else {
//                                PAGE--;
                                mRecyclerView.loadMoreComplete();
                                ContentUtil.makeToast(getActivity(),
                                        getString(R.string.no_result));
                            }
                        }

                    } else {

                        ContentUtil.makeToast(getActivity(), response.body().getError().getMessage());
                    }
                } else {

                    try {
                        ContentUtil.makeToast(getActivity(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HiGhestBean> call, Throwable t) {
                if(isLoadding&&isOldLogin==false)dialog.dismiss();
                ContentUtil.makeToast(getActivity(),"加载失败，请联网重试");
            }
        });
    }

    private void mergeData(List<MyCollectionBean>dataEntities){
        if(dataEntities!=null){
//            datas.addAll(dataEntities);
            adapter.updateData(dataEntities);
        }

    }
}
