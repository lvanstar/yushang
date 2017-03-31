package enjoytouch.com.redstar.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.JsInterface;

//此页面可以加载html也可以加载url（需要传一个标题字符串过来）
@SuppressLint("JavascriptInterface")
public class BrandIntroductionActivity extends Activity {
   private WebView wv_brand;
    private String html,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_introduction);
        html=getIntent().getStringExtra("brand");
        title=getIntent().getStringExtra("title");
        init();
        setListeners();
    }


    private void init() {
        wv_brand= (WebView) findViewById(R.id.wv_brand);
        TextView textView= (TextView) findViewById(R.id.title);
        textView.setText(title);
        wv_brand.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //开始设置
        WebSettings webSettings= wv_brand.getSettings();
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setBuiltInZoomControls(true);


        if (!html.contains("<")){
            wv_brand.loadUrl(html);
        }else {
            wv_brand.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
        //设置Web视图
        wv_brand.setWebChromeClient(new WebChromeClient());
        //回退
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandIntroductionActivity.this.finish();
            }
        });
    }
    public void flash(View view){
        DialogUtil.show(BrandIntroductionActivity.this, "已是最新数据", false).show();
    }
    public void right(View view){
        DialogUtil.show(BrandIntroductionActivity.this, "已经是最后一页", false).show();
    }
    public void left(View view){
        DialogUtil.show(BrandIntroductionActivity.this, "已经是第一页", false).show();
    }

    private void setListeners(){
        /**
         * 拦截事件
         *
         */
        wv_brand.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }
        });

        wv_brand.addJavascriptInterface(new JsInterface(BrandIntroductionActivity.this), "jsObj");
        wv_brand.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
//                    service.loadUrl("javascript:javaMethod()");
                    wv_brand.loadUrl("javascript:jumpValFeedback()");

                }
            }
        });
    }



    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_brand.canGoBack()) {
            wv_brand.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }else if ((keyCode == KeyEvent.KEYCODE_BACK)&& !wv_brand.canGoBack())finish();
        return false;
    }


//    public class JsInterface{
//        @JavascriptInterface
//        public String jumpValFeedback(final String value){
//            ContentUtil.makeLog("lzz","value:"+value);
//            return value;
//        }
//    }


}
