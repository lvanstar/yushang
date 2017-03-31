package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

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
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFocusQuchuList extends Activity {
    private MyFocusQuchuList INSTANCE;
    private static String SIZE="5";
    private static int PAGE=1;
    private ShapeLoadingDialog dialog;
    private List<InterestBean.DataEntity> datas;
    private ShopAdapter adapter;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.lv)
    XRecyclerView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus_quchu_list);
        INSTANCE=this;
        ButterKnife.inject(this);
        setViews();
    }

    private void setViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        datas=new ArrayList<>();
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        //initData(PAGE,SIZE,1,true);
    }
    private void setViews2(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(linearLayoutManager);
        lv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        lv.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new ShopAdapter(INSTANCE,datas);
        lv.setAdapter(adapter);
    }

    private void initData(int PAGE, final String SIZE, final int TYPE, final boolean isLoadding){
        if(isLoadding)dialog.show();
        Call<InterestBean> call= HttpServiceClient.getInstance().shop_index(MyApplication.cityId,PAGE, SIZE);
        call.enqueue(new Callback<InterestBean>() {
            @Override
            public void onResponse(Call<InterestBean> call, Response<InterestBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        if (1 == TYPE) {
                            datas.clear();
                            mergeData(response.body().getData());
                        } else {
                            if (response.body().getData().size() != 0) {
                                mergeData(response.body().getData());
                                lv.loadMoreComplete();
                            } else {
                                lv.loadMoreComplete();
                                ContentUtil.makeToast(INSTANCE,
                                        getString(R.string.no_result));
                            }
                        }

                    } else {
                        ContentUtil.makeToast(INSTANCE, "请联网重试");
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
