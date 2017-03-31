package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyBrandAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandSearchResultActivity extends Activity {
   // @InjectView(R.id.gv_search_brand)
    XRecyclerView gv_search_brand;
   // @InjectView(R.id.result_te_keyword)
    TextView result_te_keyword;
   // String id,type;
    BrandSearchResultActivity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_search_result2);
        context=this;
//        id=getIntent().getStringExtra("id")==null?"":getIntent().getStringExtra("id");
//        type=getIntent().getStringExtra("type")==null?"":getIntent().getStringExtra("type");
        init();
        setDatas();
        setListener();
    }

    private void init() {
        result_te_keyword= (TextView) findViewById(R.id.result_te_keyword);
        gv_search_brand= (XRecyclerView) findViewById(R.id.gv_search_brand);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(context,2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gv_search_brand.setLayoutManager(linearLayoutManager);
        gv_search_brand.setPullRefreshEnabled(false);
        gv_search_brand.setLoadingMoreEnabled(false);
        gv_search_brand.setIsnomore(true);
        View view=new View(context);
        view.setVisibility(View.GONE);
        gv_search_brand.addFootView(view);
//        gv_search_brand.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
//        gv_search_brand.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        gv_search_brand.setArrowImageView(R.drawable.iconfont_downgrey);
        result_te_keyword.setText(getIntent().getStringExtra("keyworld")
                      !=null?getIntent().getStringExtra("keyworld"):"");
    }

    private void setListener() {
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
    findViewById(R.id.result_te_keyword).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
       }
     });
    }


    private void setDatas() {
        final ShapeLoadingDialog dialog= new ShapeLoadingDialog(context);
        dialog.setLoadingText("搜索中...");
        dialog.show();
        HttpServiceClient.getInstance().brand_list("","","",1, "","",
                getIntent().getStringExtra("keyworld") !=null?getIntent().getStringExtra("keyworld"):"", MyApplication.cityId)
                .enqueue(new Callback<BrandDetailBean>() {
                    @Override
                    public void onResponse(Call<BrandDetailBean> call, Response<BrandDetailBean> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body().getStatus().equals("ok")){
                            if (response.body().getData().size()==0){
                                findViewById(R.id.sr_nothing).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.sr_nothing).setVisibility(View.GONE);
                            }
                            gv_search_brand.setAdapter(new MyBrandAdapter(context,response.body(),"1"));
                        }else {
                            //判断是否被挤掉
                            ExclusiveYeUtils.isExtrude(context,response.body().getError().getCode());
                        }
                    }

                    @Override
                    public void onFailure(Call<BrandDetailBean> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
        
        
    }
}
