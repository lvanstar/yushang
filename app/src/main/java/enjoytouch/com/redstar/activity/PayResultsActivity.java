package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * 支付结果（by chenye）
 */
public class PayResultsActivity extends Activity {

    @InjectView(R.id.pay_title)
    TextView title_tv;
    @InjectView(R.id.pay_ok_code)
    TextView code_tv;
    @InjectView(R.id.pay_ok_look)
    TextView ok_tv;
    @InjectView(R.id.pay_info)
    TextView info_tv;
    @InjectView(R.id.text_resust)
    TextView text_resust;
    @InjectView(R.id.pay_Iv)
    ImageView iv;
    @InjectView(R.id.pay_result_Rl)
    RelativeLayout result;
    private String[] datas;
    private PayResultsActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_results);
        ButterKnife.inject(this);
        INSTANCE = this;
        datas = getIntent().getStringArrayExtra(GlobalConsts.INTENT_DATA);
        init();
        setListeners();
    }

    private void init() {
        //回退
        LinearLayout back_btn = (LinearLayout) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayResultsActivity.this.finish();
            }
        });

        if ("1".equals(datas[0])) {
            ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"productPaySucc");
            //MobclickAgent.onEvent(INSTANCE, "v2_HotStylePaySucc");
            title_tv.setText("支付结果");
            ok_tv.setVisibility(View.VISIBLE);
            info_tv.setVisibility(View.GONE);
            text_resust.setText("支付成功！");
            code_tv.setText("订单号:" + datas[1]);
            iv.setImageResource(R.drawable.icon_sucess);
        } else {
            ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"productPayFail");
            //MobclickAgent.onEvent(INSTANCE, "v2_HotStylePayFail");
            title_tv.setText("支付结果");
            ok_tv.setVisibility(View.GONE);
            info_tv.setVisibility(View.VISIBLE);
            text_resust.setText("支付失败！");
            code_tv.setText("订单号:" + datas[1]);
            iv.setImageResource(R.drawable.icon_failure);
            info_tv.setText(datas[2]);
        }
    }

    private void setListeners() {
        ok_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(INSTANCE, MyTheOderActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
