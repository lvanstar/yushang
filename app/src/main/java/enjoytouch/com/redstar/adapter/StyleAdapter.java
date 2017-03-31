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
import enjoytouch.com.redstar.bean.StyleBean;

/**
 * Created by duan on 2016/6/24.
 */
public class StyleAdapter extends BaseAdapter{
    private StyleBean data;
    private Context context;
    private int select=0;
    public StyleAdapter (Context context,StyleBean data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.getData().size()+1;
    }

    @Override
    public Object getItem(int position) {
        return data.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        MyHolder myHolder;
        if (view==null){
            view=View.inflate(context, R.layout.item_style,null);
            myHolder=new MyHolder(view);
            view.setTag(myHolder);
        }else{
            myHolder = (MyHolder) view.getTag();
        }
        int position2 = position - 1;
        myHolder.text.getPaint().setAntiAlias(true);//抗锯齿
        if(position2==-1){//如果位置是第一个
            myHolder.text.setText("全部");
        }else {
            myHolder.text.setText(data.getData().get(position2).getStyle_name());
        }
        if(select==position){
            myHolder.text.setBackgroundResource(R.drawable.text_view_boder15);
            myHolder.text.setTextColor(Color.parseColor("#ffffff"));
        }else {
            myHolder.text.setBackgroundResource(R.drawable.style_selector);
            myHolder.text.setTextColor(Color.parseColor("#333333"));
        }

        return view;
    }
    class MyHolder{
        TextView text;
        public MyHolder(View view){
            text=(TextView)view.findViewById(R.id.textbutton1);
        }
    }

    public void updateData(StyleBean data, int select){
        this.data=data;
        this.select=select;
        notifyDataSetChanged();
    }
}
