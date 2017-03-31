package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.ShoplistAdapter;
import enjoytouch.com.redstar.bean.ShopBean;

/**
 * Created by lzz on 2016/6/29.
 */
public class ShopActivity extends Activity {
    @InjectView(R.id.listView)
    ListView listView;
    private ShoplistAdapter adapter;
    private ShopActivity INSTANCE;
    private List<ShopBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        INSTANCE = this;
        ButterKnife.inject(this);
        list= (List<ShopBean>) getIntent().getSerializableExtra("data");
        setViews();
        setListener();
    }

    public void setViews() {
        adapter = new ShoplistAdapter(INSTANCE, list);
        listView.setAdapter(adapter);
    }

    public void setListener() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
