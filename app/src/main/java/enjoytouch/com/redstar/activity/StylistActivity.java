package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.ShopDetailsBean;
import enjoytouch.com.redstar.bean.StylistBean;
import enjoytouch.com.redstar.selfview.observableScrollViewo.ObservableScrollable;
import enjoytouch.com.redstar.selfview.observableScrollViewo.OnScrollChangedCallback;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DisplayUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StylistActivity extends Activity {
    private StylistBean.DataBean bean;
    private StylistActivity INSTANCE;
    private String id;
    private ShapeLoadingDialog dialog;

    @InjectView(R.id.image_bg)
    SimpleDraweeView imageBg;//背景图
    @InjectView(R.id.pic)
    SimpleDraweeView pic;//设计师头像
    @InjectView(R.id.design_name)
    TextView textEname;  //设计师名字
    @InjectView(R.id.desc_name)
    TextView introduction;
    @InjectView(R.id.content)
    TextView content;    //简介
    @InjectView(R.id.logo)
    SimpleDraweeView logo;
    @InjectView(R.id.text01)
    TextView text01;
    @InjectView(R.id.text02)
    TextView text02;
    @InjectView(R.id.stylist_sv)
    ObservableScrollable scrollable;
    @InjectView(R.id.stylist_rl)
    RelativeLayout rl;
    @InjectView(R.id.cancelpinpai)
    LinearLayout cancelpinpai;
    @InjectView(R.id.pinpai)
    RelativeLayout pinpai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist);
        ButterKnife.inject(this);
        INSTANCE=this;
        id = getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        editShopCar(id,true);
        setListeners();
    }

    private void setView() {
        imageBg.setImageURI(Uri.parse(bean.getBackground()));
        pic.setImageURI(Uri.parse(bean.getSnap()));
        textEname.setText(bean.getName());
        content.setText(bean.getDescription());
        introduction.setText(bean.getIntroduction());
        if (bean.getBrand() != null) {
            logo.setImageURI(Uri.parse(bean.getBrand().getLogo()));
            text01.setText(bean.getBrand().getName());
            text02.setText(bean.getBrand().getBrand_des());
        } else {
            cancelpinpai.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scrollable.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                rl.setAlpha(newAlpha);
            }
        });
        pinpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StylistActivity.this, BrandDetailActivity.class);
                intent.putExtra("id", bean.getBrand().getId());
                startActivity(intent);
            }
        });
    }

    private void editShopCar(String id , final boolean isLoadding) {
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        if(isLoadding)dialog.show();
        Call<StylistBean> call = HttpServiceClient.getInstance().designers_detail(id);
        call.enqueue(new Callback<StylistBean>() {
            @Override
            public void onResponse(Call<StylistBean> call, Response<StylistBean> response) {
                if (isLoadding)dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        bean = response.body().getData();
                        setView();
                    } else {
                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                    }
                } else {
                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                }

            }

            @Override
            public void onFailure(Call<StylistBean> call, Throwable t) {
                if (isLoadding)dialog.dismiss();
                ContentUtil.makeToast(INSTANCE,"加载失败，请联网重试");

                ContentUtil.makeLog("lzz", "2222222");
            }
        });
    }


}
