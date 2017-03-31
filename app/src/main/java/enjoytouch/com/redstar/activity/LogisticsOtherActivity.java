package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by lizhaozhao on 16/7/30.
 */
public class LogisticsOtherActivity extends Activity{

    private LogisticsOtherActivity INSTANCE;
    private String []datas;

    @InjectView(R.id.sf)
    TextView name_tv;
    @InjectView(R.id.danghao)
    TextView code_tv;
    @InjectView(R.id.logistics_tv01)
    TextView logistics_name;
    @InjectView(R.id.logistics_tv02)
    TextView logistics_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logistics_other);
        INSTANCE=this;
        ButterKnife.inject(this);
        datas=getIntent().getStringArrayExtra(GlobalConsts.INTENT_DATA);
        setViews();
        setListeners();


    }


    private void setViews(){

        name_tv.setText(datas[1]);
        code_tv.setText(datas[0]);
        String newString01="<font color='#58b8d5'>" + datas[1]+"官网" +"</font>";
        logistics_name.setText(Html.fromHtml("进入"+newString01+"查询快递信息"));
        logistics_web.setText(datas[2]);
    }

    private void setListeners(){

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
