package enjoytouch.com.redstar.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.PagerFragmentAdapter;
import enjoytouch.com.redstar.fragment.LineFragment;
import enjoytouch.com.redstar.fragment.OnLineFragment;
import enjoytouch.com.redstar.selfview.PagerSlidingTabStrip;

/**
 * Created by lizhaozhao on 16/6/24.
 */
public class MyCollectionShopActivity extends FragmentActivity{

    private MyCollectionShopActivity INSTANCE;
    private PagerFragmentAdapter adapter;
    private ArrayList<Fragment> fragmentlist;

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tab;
    @InjectView(R.id.vp)
    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_shop);
        ButterKnife.inject(this);
        INSTANCE=this;
        setViews();
        setListeners();
    }

    private void setViews(){
        fragmentlist=new ArrayList<>();
        fragmentlist.add(new OnLineFragment());
        fragmentlist.add(new LineFragment());
        adapter = new PagerFragmentAdapter(getSupportFragmentManager(),fragmentlist,new String[]{"线上购买","线下购买"});//新建适配器
        vp.setAdapter(adapter);                 //给viewpage设置适配器
        tab.setViewPager(vp);
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
