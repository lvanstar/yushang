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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.FristSearchAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandSearchBean;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandSearchInputActivity extends Activity {
    private EditText editText;
    private List<BrandSearchBean.DataBean> list_search=new ArrayList<>();
    private ListView listView,lv_history;
    private FristSearchAdapter adapter;
    private TextView tv_resl, tv_no,tv_histroy_delete;
    //private InputMethodManager go;
    private SharedPreferences sharedPreferences;
     private ArrayList<String> history=new ArrayList<>();
    private LinearLayout ll_showhistory,ll_names;
    HistoryAdapter historyAdapter;
    private BrandSearchInputActivity INSTANCE;
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
        listView = (ListView) findViewById(R.id.lv_searchbrand);
        tv_resl = (TextView) findViewById(R.id.tv_resl);
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
                editText.setText(history.get(position) == null ? "" : history.get(position));
                startActivityForResult(new Intent(INSTANCE, BrandSearchResultActivity.class)
                        .putExtra("keyworld", history.get(position) == null ? "" : history.get(position)), 2);

            }
        });

        tv_histroy_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(BrandSearchInputActivity.this)
                        .setTitle("是否清空？")
                        .setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                qingkong();
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
                if (actionId == EditorInfo.IME_ACTION_SEND||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                   if (!isTo[0]) {
                       if(!"".equals(editText.getText().toString())){
                           startActivityForResult(new Intent(INSTANCE, BrandSearchResultActivity.class)
                                   .putExtra("keyworld", editText.getText().toString()), 2);
                           saveHistory(editText.getText().toString());
                           isTo[0] =true;
                       }
                   }
                    return true;
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
                BrandSearchInputActivity.this.finish();
            }
        });

        String s = editText.getText()==null ? "" : editText.getText().toString();
        adapter = new FristSearchAdapter(INSTANCE, list_search,s);
        listView.setAdapter(adapter);
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
                if (!"".equals(editText.getText().toString())) {
                    ll_showhistory.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    ll_names.setVisibility(View.VISIBLE);
                    Call<BrandSearchBean> call= HttpServiceClient.getInstance().brand_seach(MyApplication.cityId,editText.getText().toString());
                    call.enqueue(new Callback<BrandSearchBean>() {
                        @Override
                        public void onResponse(Call<BrandSearchBean> call, Response<BrandSearchBean> response) {
                            if (response.isSuccessful()&&response.body().status.equals("ok")){
                                list_search = response.body().data;
                                adapter.updata(list_search,editText.getText()==null ? "" : editText.getText().toString());
                            if (list_search.size() != 0) {
                                listView.setVisibility(View.VISIBLE);
                                tv_no.setVisibility(View.GONE);
                            } else {
                                listView.setVisibility(View.GONE);
                                tv_no.setVisibility(View.VISIBLE);
                            }
                        }

                        }

                        @Override
                        public void onFailure(Call<BrandSearchBean> call, Throwable t) {

                        }
                    });
//                    new MyCouponsControl(BrandSearchInputActivity.this).getBrandKeywordList(editText.getText().toString(), new MyCouponsControl.BrandKeywordCallBack() {
//                        @Override
//                        public void getBrandKeyword(List<SearchBrandBean> list) {
//                            list_search = list;
//                            if (list_search.size() != 0) {
//                                listView.setVisibility(View.VISIBLE);
//                                String s = editText.getText()==null ? "" : editText.getText().toString();
//                                adapter = new BrandSearchAdapter(INSTANCE, list_search,s);
//                                listView.setAdapter(adapter);
//                                tv_no.setVisibility(View.GONE);
//                            } else {
//                                listView.setVisibility(View.GONE);
//                                adapter.notifyDataSetChanged();
//                                tv_no.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    });
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
                editText.setText(list_search.get(position).name);
           startActivityForResult(new Intent(INSTANCE,BrandSearchResultActivity.class)
                   .putExtra("keyworld",list_search.get(position).name),2);
                saveHistory(list_search.get(position).name);

            }
        });
    }
    //清空记录
    private void qingkong(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("search_brand");
        editor.commit();
        editor.clear();
        addHistory();
        lv_history.setVisibility(View.GONE);
        lv_history.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
        DialogUtil.show(BrandSearchInputActivity.this, "历史记录已清空", false).show();
    }

    private void addHistory(){
        for (int i=0;i<history.size();i++)history.remove(i);
        if (sharedPreferences != null && sharedPreferences.contains("search_brand")){//如果偏好设置不是null并且包含“search”
            String[] s = sharedPreferences.getString("search_brand", "").split(",");//从偏好设置里得到search和空串，用逗号分离
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
            if (s.length()>0) sharedPreferences.edit().putString("search_brand", s.substring(0, s.length() - 1)).apply();
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
                convertView=View.inflate(BrandSearchInputActivity.this, R.layout.item_brand_search,null);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2)editText.setText("");
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

}
