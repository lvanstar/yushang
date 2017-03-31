package enjoytouch.com.redstar.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;

@SuppressLint("JavascriptInterface")
public class AfterServiceActivity extends Activity {
    private AfterServiceActivity INSTANCE;
    private WebView service;
    private String service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_service);
        INSTANCE = this;
        service_id = getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        setViews();
        setListener();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setViews() {
        service = (WebView) findViewById(R.id.vb_service);
//        WebSettings webSettings = service.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLightTouchEnabled(true);
        //开始设置
        WebSettings webSettings= service.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);

//        service.loadUrl("file:///android_asset/text.html");
//        service.loadUrl("http://www.yushang001.com/data/upload/html/20160203/test.html");

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ContentUtil.makeLog("lzz","3333333");
//                service.loadUrl("javascript: getInterestingInfo()");
//                service.loadUrl("javascript: getStoreInfo()");
//            }
//        });
        service.loadDataWithBaseURL(null, service_id, "text/html", "utf-8",null);
    }

    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    private void setListener() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        service.addJavascriptInterface(new JsInterface(), "jsObj");
//        service.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
////                    service.loadUrl("javascript:javaMethod()");
////                    service.loadUrl("javascript:getInterestingInfo ()");
//
//                }
//            }
//        });

    }

//    public class JsInterface{
//        @JavascriptInterface
//        public String getInterestingInfo (final String param){
//            ContentUtil.makeLog("lzz","id:"+param);
//            return param;
//        }
//
////        public String getStoreInfo(final String param){
////            ContentUtil.makeLog("lzz","ids:"+param);
////            return param;
////        }
//
//    }

}
