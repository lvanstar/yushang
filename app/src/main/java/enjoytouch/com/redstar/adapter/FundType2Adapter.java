package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.FundTypeBean;

/**
 * Created by Administrator on 2015/7/7.
 */
public class FundType2Adapter extends BaseAdapter {
    private int select;
    private Context context;
    private List<FundTypeBean.DataBean.ChildrendBean>list;

    public FundType2Adapter(Context context, List<FundTypeBean.DataBean.ChildrendBean>list) {
        this.context = context;
        this.list=list;
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
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder myHolder;
        if (view==null){
            view=View.inflate(context, R.layout.item_select2,null);
            myHolder=new MyHolder(view);
            view.setTag(myHolder);
        }else{
            myHolder = (MyHolder) view.getTag();
        }
        int position2 = position - 1;
        if(position==0){
            myHolder.text.setText("全部");
        }else {
            myHolder.text.setText(list.get(position).getName());
        }
        myHolder.text.setText(list.get(position).getName());
        if(select==position){
            myHolder.text.setTextColor(Color.parseColor("#9fd1f7"));
        }else {
            myHolder.text.setTextColor(Color.parseColor("#666666"));
        }
        return view;
    }
    class MyHolder{
        public TextView text;
        public MyHolder(View view){
            text=(TextView)view.findViewById(R.id.text2);
        }
    }

    public void updateData(List<FundTypeBean.DataBean.ChildrendBean>data,int select){
        this.list=data;
        this.select=select;
        notifyDataSetChanged();
    }
}
