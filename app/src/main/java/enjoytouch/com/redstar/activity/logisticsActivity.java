package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.TimelineAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandBean;
import enjoytouch.com.redstar.bean.LogisticsBean;
import enjoytouch.com.redstar.bean.LogisticsListBean;
import enjoytouch.com.redstar.bean.UserBean;
import enjoytouch.com.redstar.control.LoginControl;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class logisticsActivity extends Activity {

    private TimelineAdapter adapter;
    private String[] data;
    private logisticsActivity INSTANCE;
    private ShapeLoadingDialog dialog;
    private List<LogisticsListBean.DataEntity> datas;

    @InjectView(R.id.listview)
    XRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ButterKnife.inject(this);
        INSTANCE=this;
        data=getIntent().getStringArrayExtra(GlobalConsts.INTENT_DATA);
        setViews();
        initData(data[0]);
    }
    private void setViews() {
        datas=new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(INSTANCE);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setPullRefreshEnabled(false);
        View header= LayoutInflater.from(INSTANCE).inflate(R.layout.item_logistics2,null);
        TextView name= (TextView) header.findViewById(R.id.sf);
        TextView code= (TextView) header.findViewById(R.id.danghao);
        name.setText(data[0]);
        code.setText(data[1]);
        mRecyclerView.addHeaderView(header);
        adapter = new TimelineAdapter(this,datas);
        mRecyclerView.setAdapter(adapter);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData(String no){
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loading));
        dialog.show();
        Call<LogisticsListBean>call= HttpServiceClient.getInstance().logistics_list(no, MyApplication.token);
        call.enqueue(new Callback<LogisticsListBean>() {
            @Override
            public void onResponse(Call<LogisticsListBean> call, Response<LogisticsListBean> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    if ("ok".equals(response.body().getStatus())) {
                        mergeData(response.body().getData());
                    } else {
                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                    }
                } else {
                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                }
            }

            @Override
            public void onFailure(Call<LogisticsListBean> call, Throwable t) {

                dialog.dismiss();
            }
        });

    }

    private void mergeData(List<LogisticsListBean.DataEntity>dataEntities){
        if(dataEntities!=null){
            datas.addAll(dataEntities);
            adapter.updateData(datas);
        }

    }

}
