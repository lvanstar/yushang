package enjoytouch.com.redstar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.HiGhestAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CollectionBean;
import enjoytouch.com.redstar.bean.MyCollectionBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhaozhao on 16/6/24.
 */
public class LineFragment extends Fragment {

    private View view;
    private HiGhestAdapter adapter;
    private int PAGE=1;
    private String SIZE="5";
    private List<MyCollectionBean> datas=new ArrayList<>();
    private ShapeLoadingDialog dialog;

    @InjectView(R.id.online_recycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.online_nothing)
    RelativeLayout nothing_rl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_online, null);//实例化fragment
        ExclusiveYeUtils.onMyEvent(getActivity(), "selectShopStytle");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.inject(this, view);


        setListeners();

    }


    private void setViews(){

        datas=new ArrayList<>();

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new HiGhestAdapter(getActivity(),datas,"3");
        mRecyclerView.setAdapter(adapter);
    }

    private void setListeners(){

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
        initData(PAGE, SIZE, 1, false);
    }

    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding){
        dialog=new ShapeLoadingDialog(getActivity());
        dialog.setLoadingText(getString(R.string.loaddings));
        if(isLoadding)dialog.show();
        Call<CollectionBean> call= HttpServiceClient.getInstance().user_product_collection(MyApplication.token, "3", MyApplication.cityId, PAGE, SIZE);
        call.enqueue(new Callback<CollectionBean>() {
            @Override
            public void onResponse(Call<CollectionBean> call, Response<CollectionBean> response) {
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
//                            if(response.body().getData().size()<=3){
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
                                ContentUtil.makeToast(getActivity(),
                                        getString(R.string.no_result));
                            }
                        }

                    } else {

                        ContentUtil.makeToast(getActivity(), response.body().getError().getMessage());
                        ExclusiveYeUtils.isExtrude(getActivity(),response.body().getError().getCode());
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
            public void onFailure(Call<CollectionBean> call, Throwable t) {
                if (isLoadding) dialog.dismiss();
                ContentUtil.makeToast(getActivity(), "加载失败，请联网重试");
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
