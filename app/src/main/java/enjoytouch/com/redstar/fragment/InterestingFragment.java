package enjoytouch.com.redstar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.RecommendActivity;
import enjoytouch.com.redstar.adapter.ShopAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.InterestBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhaozhao on 16/6/20.
 */
public class InterestingFragment extends BaseFragment {

    private View view;
    private static String SIZE = "5";
    private static int PAGE = 1;
    private InterestingFragment INSTANCE;
    private ShapeLoadingDialog dialog;
    private List<InterestBean.DataEntity> datas;
    private ShopAdapter adapter;
    private boolean loading = true;

    @InjectView(R.id.interest_recycler)
    XRecyclerView mRecyclerView;
    @InjectView(R.id.recommend)
    TextView recommend_Tv;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_interest, null);
        ButterKnife.inject(this, view);
        INSTANCE = this;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListeners();

    }

    @Override
    public void onRes() {

    }

    @Override
    public void initData() {
        initData(PAGE, SIZE, 1, true);
    }

    @Override
    public void resetUserData() {
        initData(PAGE,SIZE,1,true);
    }

    @Override
    public void removeUserData() {

    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        initData(PAGE, SIZE, 1, true);
//    }

    private void setViews() {
        up_iv.setVisibility(View.GONE);
        datas = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        //mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        adapter = new ShopAdapter(getActivity(), datas);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListeners() {
        recommend_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(getActivity())) return;
                ExclusiveYeUtils.onMyEvent(getActivity(), "InterestingToRecommend");
                Intent intent = new Intent(getActivity(), RecommendActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                datas.clear();
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
                LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int visibleItemCount = lm.getChildCount();
                int totalItemCount = lm.getItemCount();
                int pastVisiblesItems = lm.findFirstVisibleItemPosition();
                ContentUtil.makeLog("第一个item的位置", String.valueOf(pastVisiblesItems));
                if (loading) {
                    if (pastVisiblesItems == 0 || pastVisiblesItems == 1 || pastVisiblesItems == 2) {
                        up_iv.setVisibility(View.GONE);
                    }
                }
            }
        });

    }


    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding) {
        dialog = new ShapeLoadingDialog(getActivity());
        dialog.setLoadingText(getString(R.string.loaddings));
        if (isLoadding) dialog.show();
        Call<InterestBean> call = HttpServiceClient.getInstance().shop_index(MyApplication.cityId, PAGE, SIZE);
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

                        ContentUtil.makeToast(getActivity(), "请联网重试");
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
            public void onFailure(Call<InterestBean> call, Throwable t) {
                if (isLoadding) dialog.dismiss();
                ContentUtil.makeToast(getActivity(), "加载失败，请联网重试");
            }
        });
    }

    private void mergeData(List<InterestBean.DataEntity> data) {
        if (data != null) {
            datas.addAll(data);
            adapter.updateData(datas);
        }

    }

}
