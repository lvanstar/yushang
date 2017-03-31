package enjoytouch.com.redstar.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CollectBean;
import enjoytouch.com.redstar.bean.QuChuPhotoBean;
import enjoytouch.com.redstar.selfview.observableScrollViewo.ObservableScrollView;
import enjoytouch.com.redstar.selfview.observableScrollViewo.OnScrollChangedCallback;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.JsInterface;
import enjoytouch.com.redstar.util.ShareUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandHomeList2Activity extends Activity {
    private String id;
    private BrandHomeList2Activity INSTANCE;
    private QuChuPhotoBean list;
    private ShapeLoadingDialog dialog;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.share)
    ImageView share;
    @InjectView(R.id.zan)
    ImageView zan;
    @InjectView(R.id.collect_cnt)
    TextView collect_cnt;
    @InjectView(R.id.brand_home_list2_name)
    TextView name;
    @InjectView(R.id.brand_home_list2_title)
    TextView title;
    @InjectView(R.id.brand_home_list2_sv)
    SimpleDraweeView sdv;
    @InjectView(R.id.wv_brand)
    WebView webview;
    @InjectView(R.id.home)
    RelativeLayout line;
    @InjectView(R.id.sv)
    ObservableScrollView sv;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.details_rl)
    RelativeLayout details_rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_home_list2);
        INSTANCE=this;
        ButterKnife.inject(this);
        id=getIntent().getStringExtra("id");
        setData();
    }

    private void setViews(){
        up_iv.setVisibility(View.GONE);
        setRelativeLayoutParams(sdv);
        sdv.setImageURI(Uri.parse(list.getData().getCover_img()));
        name.setText(list.getData().getName());
        title.setText(list.getData().getTitle());
        collect_cnt.setText(list.getData().getCollect_cnt());
        WebSettings webSettings = webview.getSettings();
//        webSettings.setSupportZoom(true);
//        webSettings.setTextZoom(20);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL(null, list.getData().getContent(), "text/html", "utf-8", null);
        zan.setImageResource(("1".equals(list.getData().getIs_collection()) ? R.drawable.icon2_1 : R.drawable.icon2_6));
        //开始设置
//        WebSettings webSettings= webview.getSettings();
//        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        webSettings.setBuiltInZoomControls(true);
//        //设置Web视图
//        webview.setWebChromeClient(new WebChromeClient());
//        webview.loadUrl(list.getData().getContent());

    }

    private void setListener() {
        up_iv.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "ImageTextDetailCollectAction");
                collect();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "ImageTextDetailShareAction");
                ShareUtil.share(INSTANCE, line, list.getData().getName(), list.getData().getTitle(), "3", "http://www.yushang001.com/home/qcshare/stylelist"+"?"+"id="+id, list.getData().getCover_img());
            }
        });
        sv.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                details_rl.setAlpha(newAlpha);
                if (t > 700) {
                    up_iv.setVisibility(View.VISIBLE);
                } else {
                    up_iv.setVisibility(View.GONE);
                }
            }
        });

        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
//        webview.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                dialog.show();
//                if (100 == newProgress) {
//                    dialog.dismiss();
//                }
//            }
//        });

        webview.addJavascriptInterface(new JsInterface(INSTANCE),"jsObj");
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
//                    service.loadUrl("javascript:javaMethod()");
                    webview.loadUrl("javascript:jumpValFeedback()");

                }
            }
        });
    }

    private void setData() {
        /**
         * 异步请求方式
         */

        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loading));
        dialog.show();
        Call<QuChuPhotoBean> call= HttpServiceClient.getInstance().quchu_photo(MyApplication.token,MyApplication.cityId,id);
        call.enqueue(new Callback<QuChuPhotoBean>() {
            @Override
            public void onResponse(Call<QuChuPhotoBean> call, Response<QuChuPhotoBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("ok")) {
                        list = response.body();
                        setViews();
                        setListener();
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<QuChuPhotoBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
                dialog.dismiss();
            }
        });
    }
    private void collect() {
        /**
         * 异步请求方式
         */

        Call<CollectBean> call= HttpServiceClient.getInstance().quchu_collect(id, MyApplication.token);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        zan.setImageResource(("1".equals(response.body().getData().getCollect_status()) ? R.drawable.icon2_1 : R.drawable.icon2_6));
                        collect_cnt.setText(response.body().getData().getCollect_cnt());
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz", String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }

    private void setRelativeLayoutParams(SimpleDraweeView view) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int w =width;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height=w;
        params.width=w;
        view.setLayoutParams(params);
    }
}
