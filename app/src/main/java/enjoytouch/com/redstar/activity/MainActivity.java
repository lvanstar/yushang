package enjoytouch.com.redstar.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyMainAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.fragment.HiGhestFragment;
import enjoytouch.com.redstar.fragment.HomepageFragment;
import enjoytouch.com.redstar.fragment.InterestingFragment;
import enjoytouch.com.redstar.fragment.MyFragment;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.ContentUtil;


public class MainActivity extends FragmentActivity {
    public static ViewPager vp;
    private ArrayList<Fragment> list = new ArrayList<>();
    private MyMainAdapter adapter;
    private boolean status;
    private static MainActivity INSTANCE;
    public static RelativeLayout alpha;

    private ImageView[] ivs;
    private TextView[] tvs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INSTANCE = this;
        status = getIntent().getBooleanExtra("status", false);//取出意图对象里的附件
        setViews();
        initFragmentViewPager();
        setSelectedItem(0);
        setListeners();
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "2PWnF3T8dAl9lCc1r3px06Ga ");//百度推送
//        if(Build.VERSION.SDK_INT>=23){
//            String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,
//                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS};
//                ActivityCompat.requestPermissions(this, mPermissionList, 100);
//            }
    }

    public static void logout() {
        INSTANCE.finish();
    }

    private void setViews() {
        MyApplication.list.add("0");
        alpha = (RelativeLayout) findViewById(R.id.alpha);
        ivs = new ImageView[]{(ImageView) findViewById(R.id.iv0),  (ImageView) findViewById(R.id.iv2),(ImageView) findViewById(R.id.iv1), (ImageView) findViewById(R.id.iv3)};
        tvs = new TextView[]{(TextView) findViewById(R.id.tv0),  (TextView) findViewById(R.id.tv2),(TextView) findViewById(R.id.tv1), (TextView) findViewById(R.id.tv3)};
        vp = (ViewPager) findViewById(R.id.main_viewpager);//ViewPage控件

    }

    private void initFragmentViewPager() {
        list.add(new HomepageFragment());
        list.add(new HiGhestFragment());
        list.add(new InterestingFragment());
        list.add(new MyFragment());
        adapter = new MyMainAdapter(getSupportFragmentManager(), list);
        vp.setOffscreenPageLimit(list.size());                        //设置幕后item的缓存数目
        vp.setAdapter(adapter);                             //给ViewPage设置适配器
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = ivs.length;
                for (int i = 0; i < count; i++) {
                    if (position != 4 || i != 0) {
                        ivs[(position + i) % count].setBackgroundResource(getResources().getIdentifier("t" + (position + i) % count + (i == 0 ? "r" : ""), "drawable", MainActivity.this.getPackageName()));
                        tvs[i].setTextColor(getResources().getColor((R.color.text20)));
                    } else {
                        count += 1;
                    }
                }
                tvs[position].setTextColor(getResources().getColor((R.color.text_lv)));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setSelectedItem(int pos) {
        vp.setCurrentItem(pos, false);
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.rl0:
                setSelectedItem(0);
                if(MyApplication.list.contains("0")){
                    MyApplication.list.remove("0");
                }
                if(MyApplication.list.contains("1")){
                    MyApplication.list.remove("1");
                }
                if(MyApplication.list.contains("2")){
                    MyApplication.list.remove("2");
                }
                if(MyApplication.list.contains("3")){
                    MyApplication.list.remove("3");
                }
                MyApplication.list.add("0");
                ContentUtil.makeLog("集合中的数",String.valueOf(MyApplication.list));
                break;
            case R.id.rl2:
                setSelectedItem(1);
                if(MyApplication.list.contains("1")){
                    MyApplication.list.remove("1");
                }
                if(MyApplication.list.contains("2")){
                    MyApplication.list.remove("2");
                }
                if(MyApplication.list.contains("3")){
                    MyApplication.list.remove("3");
                }
                MyApplication.list.add("1");
                ContentUtil.makeLog("集合中的数",String.valueOf(MyApplication.list));
                break;
            case R.id.rl1:
                setSelectedItem(2);
                if(MyApplication.list.contains("2")){
                    MyApplication.list.remove("2");
                }
                if(MyApplication.list.contains("3")){
                    MyApplication.list.remove("3");
                }
                MyApplication.list.add("2");
                ContentUtil.makeLog("集合中的数",String.valueOf(MyApplication.list));
                break;
            case R.id.rl3:
                if (AccountUtil.showLoginView(this)) return;
                if(MyApplication.isLogin)setSelectedItem(3);
                if(MyApplication.list.contains("3")){
                    MyApplication.list.remove("3");
                }
                MyApplication.list.add("3");
                ContentUtil.makeLog("集合中的数",String.valueOf(MyApplication.list));
                break;
        }
    }

    private void setListeners() {
    }

    public static void switchToWhich(int index) {//点击的按钮位置
        if (vp.getCurrentItem() == index) {//如果item的当前位置是正点击的位置，返回不操作
            return;
        }
    }

    public static void alpha2() {
        alpha.setVisibility(View.GONE);
    }


    public static void alpha() {
        alpha.setVisibility(View.VISIBLE);
        //alpha.getBackground().setAlpha(215);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    public void toAllBrand(View view) {
        MobclickAgent.onEvent(INSTANCE, "lookfindForYouMore");
        Intent intent=new Intent(INSTANCE,FoundListActivity.class);
        startActivity(intent);
    }
    //跳转到爆款
    public void toHotStyleList(View view) {
        vp.setCurrentItem(2);
        MobclickAgent.onEvent(INSTANCE, "v2_lookMoreHotStyle");
    }

    private long exitTimeMillis = System.currentTimeMillis();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if(MyApplication.list.size()==1){
                ContentUtil.makeLog("集合中的数量","1");
                ContentUtil.makeLog("集合中的数",String.valueOf(MyApplication.list));
                if(currentTime-exitTimeMillis==0||currentTime-exitTimeMillis>1500){
                    exitTimeMillis = System.currentTimeMillis();
                    ContentUtil.makeToast(MainActivity.this,"再按一次退出");
                    return false;
                }else{
                    ContentUtil.makeLog("退出","退出");
                    MyApplication.list.clear();
                    finish();
                }
            }else {
                ContentUtil.makeLog("集合中的数量",String.valueOf(MyApplication.list.size()));
                ContentUtil.makeLog("集合中的数",String.valueOf(MyApplication.list));
                setSelectedItem(Integer.valueOf(MyApplication.list.get(MyApplication.list.size()-2)));
                MyApplication.list.remove(MyApplication.list.size()-1);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
