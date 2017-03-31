package enjoytouch.com.redstar.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.CityListAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CityBean;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.fragment.HomepageFragment;
import enjoytouch.com.redstar.selfview.MyLetterListView;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.HttpUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 城市选择
 * duan
 * */
public class LocationActivity extends SlideFinishActivity {
    private CityListAdapter adapter;
    private View back, here;
    private boolean status;
    private TextView title;
    private TextView address;
    private TextView content;
    /*private ArrayList<String> list = new ArrayList<>();*/
    private CityBean bean;
    String[] list = { "A","C","S","T"};
    private ListView lv;
    private static MyLetterListView letterListView;
    private TextView overlay;
    private Handler handler1;
    private OverlayThread overlayThread;

    private String cname;
    private static LocationActivity INSTANCE;
    private int item;
    private ShapeLoadingDialog dialog;
//    private static LocateReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        INSTANCE = this;
        setContentView(R.layout.location);
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        status = getIntent().getBooleanExtra("status", false);
        cname = getIntent().getStringExtra("cname");
        if (cname == null) cname = MyApplication.cityName;
        ExclusiveYeUtils.onMyEvent(this, "changeCity");
        setData();
        /*dialog = DialogUtil.createLoadingDialog(this, "正在努力加载");
        dialog.show();*/
    }

    private List<NameValuePair> params(){
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", MyApplication.token));
        return params;
    }

    @Override
    public void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }


    private void setViews() {
        //dialog2=DialogUtil.createLoadingDialog(INSTANCE,"正在加载");
        //initOverlay();
        back = findViewById(R.id.back);    //回退键
        here = findViewById(R.id.location_here);    //当前城市布局
        title = (TextView) findViewById(R.id.location_title);//标题名
        address = (TextView) findViewById(R.id.location_address);//当前城市textView
        if (AppStartActivity.isOpen()){
            address.setText(MyApplication.cityName);
        }//如果已开启GPS定位，设置默认城市“上海”
        else {address.setText("未打开GPS,无法定位当前城市");}
        lv = (ListView) findViewById(R.id.city_list);                           //城市ListView
        adapter=new CityListAdapter(INSTANCE,bean);
        lv.setAdapter(adapter);
        letterListView = (MyLetterListView) findViewById(R.id.cityLetterListView);//右边首字母ListView
        letterListView.postInvalidate();
        ContentUtil.makeLog("集合现有数据1", String.valueOf(MyApplication.b));
        ContentUtil.makeLog("已处理消息", String.valueOf(MyApplication.b));
        letterListView.setOnTouchingLetterChangedListener(new
                LetterListViewListener());

        if (cname != null) {        //如果城市名不是空，给标题设置城市名
            title.setText("当前城市-"+cname);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.removeViewImmediate(overlay);*/
//        unregisterReceiver(receiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setListeners() {

        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationActivity.this.finish();
            }
        });
        //当前城市布局
        here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationActivity.this, HomepageFragment.class);
                //intent.putExtra("cid", MyApplication.cityId);
                //intent.putExtra("cname", MyApplication.cityName);
                setResult(0, intent);
                finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {       //给城市listview设置监听
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (bean != null) {
                    MobclickAgent.onEvent(LocationActivity.this, "changeCity");
                    ContentUtil.makeLog("正在执行","listview点击监听");
                    Intent intent = new Intent(LocationActivity.this, HomepageFragment.class);
                    MyApplication.cityId=bean.getData().get(i).getId();
                    MyApplication.cityName=bean.getData().get(i).getName();
                    setResult(0, intent);
                    finish();
                }
            }
        });
        //  letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());//给右边首字母ListView设置滑动监听
    }

    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(AbsoluteLayout.LayoutParams.WRAP_CONTENT, AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }
    public static Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            letterListView.postInvalidate();
            ContentUtil.makeLog("处理消息完成：", String.valueOf(MyApplication.b));
            super.handleMessage(msg);
        }
    };


    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (adapter.alphaIndexer.get(s) != null) {      //如果适配器中的当前滑动的字母对应的位置值不是空
                int position = adapter.alphaIndexer.get(s); //将位置值赋给postion
                lv.setSelection(position);                  //给城市lietview设置当前位置
                /*overlay.setText(adapter.sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler1.removeCallbacks(overlayThread);
                //
                handler1.postDelayed(overlayThread, 1500);*/
            }
        }

    }
    private class OverlayThread implements Runnable {

        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }

    private static class LocateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            INSTANCE.title.setText(MyApplication.cityName);
        }
    }
    private void setData() {
        /**
         * 异步请求方式
         */
        dialog.show();
        Call<CityBean> call= HttpServiceClient.getInstance().city_list(MyApplication.cityId);
        call.enqueue(new Callback<CityBean>() {
            @Override
            public void onResponse(Call<CityBean> call, Response<CityBean> response) {
                if (response.isSuccessful()) {
                    CityBean userBean = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if(userBean.getStatus().toString().equals("ok")){
                        dialog.dismiss();
                        bean=userBean;
                        setViews();
                        setListeners();
                    }else {
                        Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
}
