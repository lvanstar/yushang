package enjoytouch.com.redstar.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import enjoytouch.com.redstar.R;

/**
 * 设置  关于
 */
public class AboutActivity extends SlideFinishActivity {

    private View back;
    private TextView ver;
    private LinearLayout line;

    private TextView value_Tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setViews();
        setListeners();
    }

    private void setViews() {
        back = findViewById(R.id.back);
        line=(LinearLayout)findViewById(R.id.home);
        ver= (TextView) findViewById(R.id.banben);
        value_Tv= (TextView) findViewById(R.id.text_content);
//        value_Tv.setText(getString(R.string.about));
        value_Tv.setMovementMethod(ScrollingMovementMethod.getInstance());
         ver.setText("当前版本号：" + getVersion());
    }

    private void setListeners() {
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
