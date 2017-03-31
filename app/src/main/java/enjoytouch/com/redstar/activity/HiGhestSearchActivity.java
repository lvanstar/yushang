package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.BrandSearchAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.SearchBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HiGhestSearchActivity extends Activity {
    private EditText editText;
    private List<SearchBean.DataEntity > list_search;
    private ListView listView,lv_history;
    private BrandSearchAdapter adapter;
    private TextView tv_resl, tv_no,tv_histroy_delete,tv_resl_3;
    //private InputMethodManager go;
    private SharedPreferences sharedPreferences;
     private ArrayList<String> history=new ArrayList<>();
    private LinearLayout ll_showhistory,ll_names;
    HistoryAdapter historyAdapter;
    private HiGhestSearchActivity INSTANCE;
    private ImageView remove;
    final boolean[] isTo = {false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand_search_input);
        INSTANCE=this;
        init();
    }

    private void init() {
        editText = (EditText) findViewById(R.id.et_input);
        editText.setHint("请搜索上品关键字");
        listView = (ListView) findViewById(R.id.lv_searchbrand);
        tv_resl = (TextView) findViewById(R.id.tv_resl);
        tv_resl_3= (TextView) findViewById(R.id.tv_resl_3);
        tv_no = (TextView) findViewById(R.id.tv_no);
        lv_history= (ListView) findViewById(R.id.lv_history);
        ll_showhistory = (LinearLayout) findViewById(R.id.ll_showhistory);
        ll_names= (LinearLayout) findViewById(R.id.ll_names);
        sharedPreferences = MyApplication.sf;
        tv_histroy_delete= (TextView) findViewById(R.id.tv_histroy_delete);
        remove= (ImageView) findViewById(R.id.iv_remove);
        addHistory();
        historyAdapter=new HistoryAdapter();
        lv_history.setAdapter(historyAdapter);
        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HiGhestSearchActivity.this, HiGhestSearchResultActivity.class);
                intent.putExtra("key", history.get(position));
                startActivityForResult(intent,2);
            }
        });
        tv_histroy_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(INSTANCE)
                        .setTitle("是否清空？")
                        .setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("search_highest");
                                editor.commit();
                                editor.clear();
                                addHistory();
                                lv_history.setVisibility(View.GONE);
                                lv_history.setAdapter(historyAdapter);
                                historyAdapter.notifyDataSetChanged();
                                DialogUtil.show(HiGhestSearchActivity.this, "历史记录已清空", false).show();
                            }
                        })
                        .setPositiveButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();

            }
        });
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){
                    if (!isTo[0]) {
                     if(!"".equals(editText.getText().toString())){
                        Intent intent = new Intent(HiGhestSearchActivity.this, HiGhestSearchResultActivity.class);
                        intent.putExtra("key", editText.getText().toString());
                        saveHistory(editText.getText().toString());
                         startActivityForResult(intent,2);
                         isTo[0] =true;
                        return true;
                     }
                    }
                }
                return false;
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        findViewById(R.id.tv_cancelall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiGhestSearchActivity.this.finish();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (s.length()>0){
                   remove.setVisibility(View.VISIBLE);
               }else {
                   remove.setVisibility(View.GONE);
               }
                tv_resl.setText(editText.getText().toString());
                tv_resl_3.setText("相关的上品");
                if (!"".equals(editText.getText().toString())) {

                    ll_showhistory.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    ll_names.setVisibility(View.VISIBLE);
                    Call<SearchBean>call= HttpServiceClient.getInstance().product_search(MyApplication.cityId,editText.getText().toString());
                    call.enqueue(new Callback<SearchBean>() {
                        @Override
                        public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                            if (response.isSuccessful()) {
                                if ("ok".equals(response.body().getStatus())) {
                                    list_search = response.body().getData();
                                    if (list_search.size() != 0) {
                                        listView.setVisibility(View.VISIBLE);
                                        String s = editText.getText()==null ? "" : editText.getText().toString();
                                        adapter = new BrandSearchAdapter(INSTANCE, list_search,s);
                                        listView.setAdapter(adapter);
                                        tv_no.setVisibility(View.GONE);
                                    } else {
                                        listView.setVisibility(View.GONE);
                                        tv_no.setVisibility(View.VISIBLE);
                                    }
                                } else {

                                    ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                }
                            } else {
                                try {
                                    ContentUtil.makeToast(INSTANCE, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SearchBean> call, Throwable t) {
                            ContentUtil.makeToast(INSTANCE, "加载失败，请联网重试");
                        }
                    });
                } else {

                    ll_names.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    ll_showhistory.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    remove.setVisibility(View.GONE);
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(INSTANCE, HiGhestSearchResultActivity.class);
                intent.putExtra("key", list_search.get(position).getName());
                saveHistory(list_search.get(position).getName());
                startActivity(intent);
            }
        });
    }
    private void addHistory(){
        for (int i=0;i<history.size();i++)history.remove(i);
        if (sharedPreferences != null && sharedPreferences.contains("search_highest")){//如果偏好设置不是null并且包含“search”
            String[] s = sharedPreferences.getString("search_highest", "").split(",");//从偏好设置里得到search和空串，用逗号分离
            for (String s1:s) history.add(s1);
            if (history.size()==0){
                tv_histroy_delete.setVisibility(View.GONE);
            }else {
                tv_histroy_delete.setVisibility(View.VISIBLE);
            }
        }
        ll_names.setVisibility(View.GONE);
    }

    private void saveHistory( String text){
        while (history.contains(text)) history.remove(text);//当历史记录集合包含当前文本，从集合中移除文本
        history.add(0, text);                               //将文本添加到0下标位置
        if (sharedPreferences != null) {
            String s = "";
            for (int i = 0; i < 10; i++) {
                if (history.size() > i) s += history.get(i) + ",";
                else break;
            }
            if (s.length()>0) sharedPreferences.edit().putString("search_highest", s.substring(0, s.length() - 1)).apply();
        }
        ll_names.setVisibility(View.VISIBLE);
    }

    private class HistoryAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return history == null ? 0 : history.size();
        }
        @Override
        public Object getItem(int position) {
            return history.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder myHolder;
            if (convertView==null){
                convertView=View.inflate(HiGhestSearchActivity.this, R.layout.item_brand_search,null);
                myHolder=new MyHolder(convertView);
                convertView.setTag(myHolder);
            }else{
                myHolder = (MyHolder) convertView.getTag();
            }
            myHolder.tv_searchbrand.setText(history.get(position));
            return convertView;
        }
        class MyHolder{
            public TextView tv_searchbrand;
            public MyHolder(View view) {
                tv_searchbrand=(TextView)view.findViewById(R.id.tv_searchbrand);
            }
        }
    }
    public void onResume() {
        super.onResume();
        isTo[0]=false;
        MobclickAgent.onResume(this);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.showSoftInput(editText, 0);
            }
        }, 300);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2)editText.setText("");
    }

}
