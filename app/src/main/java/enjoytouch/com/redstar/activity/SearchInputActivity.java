package enjoytouch.com.redstar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.HttpUtils;
/**
 * 搜索
 * duan
 * */
public class SearchInputActivity extends SlideFinishActivity {

    private View cancel, clear, hint;
    private EditText input;
    private TextView title;
    private LinearLayout relative;
    private ArrayList<String> relatives = new ArrayList<>();

    private boolean relating = false;

    private InputMethodManager imm;
    private SharedPreferences sp;
    private ArrayList<String> history = new ArrayList<>();  //优惠券的历史记录集合
    private ArrayList<String> history2 = new ArrayList<>(); //店铺的历史记录集合
    private static SearchInputActivity INSTANCE;
    private ImageView iv;
    private String state;
    private TextView false1;
    private TextView title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);
        INSTANCE = this;
        state=getIntent().getStringExtra("state");
        cancel = findViewById(R.id.si_cancel);//“取消”文本
        clear = findViewById(R.id.si_close);//X号
        hint = findViewById(R.id.si_hint);//放大镜
        input = (EditText) findViewById(R.id.si_input);//整个输入框
        if(state==null){
        }else if(state.equals("shop")){
            input.setHint("店铺名称");
        }else if(state.equals("discount")){
            input.setHint("优惠券名称");
        }
        title = (TextView) findViewById(R.id.si_title);//显示框中的文本控件
        title2=(TextView)findViewById(R.id.si_title2);//清空历史记录
        relative = (LinearLayout) findViewById(R.id.si_relative);//文本控件下面的线性布局（搜索提示内容）
        iv=(ImageView)findViewById(R.id.iv);
        false1=(TextView)findViewById(R.id.false1);//未搜索到结果时的提示框

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        cancel.setOnClickListener(new View.OnClickListener() {//给取消按钮设置点击监听
            @Override
            public void onClick(View view) {
                finish();               //结束
            }
        });

        sp = MyApplication.sf;
        if (sp != null && sp.contains("search")){//如果偏好设置不是null并且包含“search”
            String[] s = sp.getString("search", "").split(",");//从偏好设置里得到search和空串，用逗号分离
            history = new ArrayList<>();        //新建历史记录的集合
            for (String s1:s) history.add(s1);
        }
        if (sp != null && sp.contains("shop")){//如果偏好设置不是null并且包含“search”
            String[] s = sp.getString("shop", "").split(",");//从偏好设置里得到search和空串，用逗号分离
            history2 = new ArrayList<>();        //新建历史记录的集合
            for (String s1:s) history2.add(s1);
        }

        input.addTextChangedListener(new TextWatcher() {//给整个输入框框设置文本改变监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//文本改变时执行
                int l = charSequence.length();//字符序列的长度
                if (l > 0) {                //如果长度大于0
                    hint.setVisibility(View.VISIBLE);//给图片设置显示
                    iv.setVisibility(View.GONE);
                    relate(true);       //搜索提示
                }
                else {
                    hint.setVisibility(View.VISIBLE);
                    relate(false);      //历史记录
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        input.setOnKeyListener(new View.OnKeyListener() {//键盘监听
            @Override
            public boolean onKey(View view, int k, KeyEvent keyEvent) {
                if(state.equals("shop")){
                    if (k == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        saveHistoryAndGo2(null, input.getText().toString());
                        return true;
                    }
                }else if(state.equals("discount")){
                    if (k == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        saveHistoryAndGo(null, input.getText().toString());
                        return true;
                    }
                }

                return false;
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {//点击x号时，将输入框设置成空
            @Override
            public void onClick(View view) {
                input.setText("");
            }
        });

        relate(false);
    }

    private void saveHistoryAndGo(String id, String text){
        while (history.contains(text)) history.remove(text);//当历史记录集合包含当前文本，从集合中移除文本
        history.add(0, text);                               //将文本添加到0下标位置
        if (sp != null) {
            String s = "";
            for (int i = 0; i < 10; i++) {
                if (history.size() > i) s += history.get(i) + ",";
                else break;
            }
            if (s.length()>0) sp.edit().putString("search", s.substring(0, s.length() - 1)).apply();
        }
    }
    private void saveHistoryAndGo2(String id, String text){
        while (history2.contains(text)) history2.remove(text);//当历史记录集合包含当前文本，从集合中移除文本
        history2.add(0, text);                               //将文本添加到0下标位置
        if (sp != null) {
            String s = "";
            for (int i = 0; i < 10; i++) {
                if (history2.size() > i) s += history2.get(i) + ",";
                else break;
            }
            if (s.length()>0) sp.edit().putString("shop", s.substring(0, s.length() - 1)).apply();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) finish();
        else if (resultCode == 2) input.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(500);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        imm.hideSoftInputFromWindow(input.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private List<NameValuePair> params() {
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("keyword", input.getText().toString()));
        params.add(new BasicNameValuePair("city_id", MyApplication.cityId));
        params.add(new BasicNameValuePair("login_type","1"));
        return params;
    }

    private void relate(boolean r){         //设置提示内容
        title.setVisibility(View.VISIBLE);//给显示框中的文本控件设置成显示状态
        relating = r;               //将r值赋给relating
        if (relating){              //如果r是true
            String s = input.getText().toString();//从搜索框中得到用户输入的内容
            SpannableStringBuilder span = new SpannableStringBuilder("搜索“" + s + "”相关的品牌");//设置
            span.setSpan(new ForegroundColorSpan(0xff449dff), 2, s.length() + 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//
            title.setText(span);                    //给提示文本框设置内容
            if(state.equals("discount")) {HttpUtils.getResultToHandler(this, "search", "brand", params(), handler, 1);}
            if(state.equals("shop")){HttpUtils.getResultToHandler(this, "shop", "search", params(), handler, 1);}
        }else {
            title.setText("搜索历史");                      //给文本框设置文字
            title2.setVisibility(View.VISIBLE);
            if(state.equals("shop")){
                if (history2 != null && history2.size()>0){       //如果历史记录集合中不是空的并且数量大于0
                    relative.removeAllViews();                  //文本框下方的布局清除历史所有控件
                    for (final String s:history2){               //将新的控件添加到布局
                        TextView v = getTextView(s);           //得到当前下标的textview并设置监听
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                saveHistoryAndGo2(null, s);      //自定义方法，保存记录并跳转页面
                                input.setText(s);               //给搜索框设置用户输入的内容
                            }
                        });
                        relative.addView(v);
                        iv.setVisibility(View.GONE);
                    }
                }else {
                    title.setVisibility(View.GONE);
                    title2.setVisibility(View.GONE);
                }
            }else if(state.equals("discount")){
                if (history != null && history.size()>0){       //如果历史记录集合中不是空的并且数量大于0
                    relative.removeAllViews();                  //文本框下方的布局清除历史所有控件
                    for (final String s:history){               //将新的控件添加到布局
                        TextView v = getTextView(s);           //得到当前下标的textview并设置监听
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                saveHistoryAndGo(null, s);      //自定义方法，保存记录并跳转页面
                                input.setText(s);               //给搜索框设置用户输入的内容
                            }
                        });
                        relative.addView(v);
                        iv.setVisibility(View.GONE);
                    }
                }else {
                    title.setVisibility(View.GONE);
                    title2.setVisibility(View.GONE);
                }
            }
            title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(state.equals("shop")){
                        relative.removeAllViews();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("shop");
                        editor.commit();
                        editor.clear();
                    }else if(state.equals("discount")){
                        relative.removeAllViews();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove("search");
                        editor.commit();
                        editor.clear();
                    }
                }
            });
        }
    }

    private void clearRelative(){
        relative.removeAllViews();
        relatives.clear();
    }

    private void addRelative(final String id, final String s){
        relatives.add(s);
        TextView v = getTextView(s);
        relative.addView(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state.equals("shop")){
                    saveHistoryAndGo2(id, s);        //保存记录并跳转
                }else if(state.equals("discount")){
                    saveHistoryAndGo(id, s);        //保存记录并跳转
                }
                ContentUtil.makeLog("点击的优惠券id",id);
                input.setText(s);
            }
        });
    }

    private static LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(-1, -2);
    static { lparams.setMargins(0, 1, 1, 0); }
    private TextView getTextView(final String text){
        TextView v = new TextView(this);
        v.setLayoutParams(lparams);     //设置布局参数
        v.setPadding(32, 16, 0, 16);    //设置内间距
        SpannableStringBuilder span = new SpannableStringBuilder(text);
        String key = input.getText().toString().toLowerCase();//得到用户输入的内容，字符串中的字母转成小写字母
        int i = -1;
        if (key.length()>0) while ((i=text.toLowerCase().indexOf(key, i+1))>=0) span.setSpan(new ForegroundColorSpan(0xff449dff), i, i + key.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        v.setText(span);
        v.setTextColor(0xff9e9e9e);
        v.setTextSize(18);
        v.setBackgroundColor(0xffffffff);
        return v;
    }

    private SearchHandler handler = new SearchHandler();
    private static class SearchHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                INSTANCE.input.setFocusable(true);
                INSTANCE.input.setFocusableInTouchMode(true);
                INSTANCE.input.requestFocus();
                INSTANCE.imm.showSoftInput(INSTANCE.input, InputMethodManager.SHOW_FORCED);
            }else if (msg.what == 1){
                JSONObject jo = (JSONObject) msg.obj;
                try {
                    if (jo.getString("status").toLowerCase().equals("ok")){
                        INSTANCE.clearRelative();
                        JSONArray ja = jo.getJSONArray("data");
                        ContentUtil.makeLog("得到返回数据","得到返回数据" );
                        if(ja.length()==0){
                            INSTANCE.false1.setVisibility(View.VISIBLE);
                        }else {
                            INSTANCE.false1.setVisibility(View.INVISIBLE);
                        }
                        for (int i=0;i<ja.length();i++){
                            JSONObject jcity = ja.getJSONObject(i);
                            INSTANCE.addRelative(jcity.getString("id"), jcity.getString("name"));//添加textview
                            ContentUtil.makeLog("优惠券的id", jcity.getString("id"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
