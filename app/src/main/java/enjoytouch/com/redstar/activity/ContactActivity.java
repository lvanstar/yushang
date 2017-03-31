package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.MenuDialogUtils;

/**
 * Created by Administrator on 2016/1/12.
 * <p/>
 * 联系我们
 */
public class ContactActivity extends Activity {
    private MenuDialogUtils menuDialog;
    private ContactActivity INSTANCE;
    @InjectView(R.id.contact_title)
    TextView title;
    @InjectView(R.id.tell_s)
    TextView tell_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);
        INSTANCE = this;
        setListeners();

    }


    private void setListeners() {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tell_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhone(v);
            }
        });

    }

    public void makePhone(View V){
        menuDialog= new MenuDialogUtils(INSTANCE, R.style.registDialog, R.layout.menu_phone, "400-607-5500", new MenuDialogUtils.ButtonClickListener() {
            @Override
            public void onButtonClick(int i) {
                Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "400-607-5500"));
                 startActivity(phoneIntent);
            }
        });
        menuDialog.show();
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
