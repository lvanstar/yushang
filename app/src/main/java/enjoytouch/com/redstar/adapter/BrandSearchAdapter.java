package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.SearchBean;

/**
 * Created by Administrator on 2016/1/19.
 */
public class BrandSearchAdapter extends BaseAdapter {
    private Context context;
    private List<SearchBean.DataEntity> list;
    private String key;

    public BrandSearchAdapter(Context context, List<SearchBean.DataEntity> list,String key) {
        this.context = context;
        this.list = list;
        this.key = key;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.item_brand_search,null);
            myHolder=new MyHolder(convertView);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder) convertView.getTag();
        }
       // myHolder.tv_searchbrand.setText(list.get(position).name);
        SpannableStringBuilder span = new SpannableStringBuilder(list.get(position).getName());
        int i = -1;
        if (key.length()>0) while ((i=list.get(position).getName().toLowerCase().indexOf(key, i+1))>=0) span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.key_world)), i, i + key.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        myHolder.tv_searchbrand.setText(span);
        return convertView;
    }
    class MyHolder{
        public TextView tv_searchbrand;
        public MyHolder(View view) {
            tv_searchbrand=(TextView)view.findViewById(R.id.tv_searchbrand);
        }
    }
}
