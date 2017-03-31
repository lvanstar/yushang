package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.CityAdapter;
import enjoytouch.com.redstar.bean.AddressBean;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by Administrator on 2016/1/5.
 * <p/>
 * 查看城市自提点
 */
public class CityListActivity extends Activity {

    @InjectView(R.id.citylist_Lv)
    ListView lv;
    private List<AddressBean> list;
    private CityAdapter adapter;
    private CityListActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        ButterKnife.inject(this);
        INSTANCE = this;
        list = (List<AddressBean>) getIntent().getSerializableExtra(GlobalConsts.INTENT_DATA);
        setViews();
        setListeners();
    }

    private void setViews() {

        adapter = new CityAdapter(INSTANCE, list);
        lv.setAdapter(adapter);
    }

    private void setListeners() {
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
