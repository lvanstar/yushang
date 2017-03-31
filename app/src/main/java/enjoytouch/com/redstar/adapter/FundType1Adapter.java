package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.FundTypeBean;


/**
 * Created by Administrator on 2015/7/7.
 */
public class FundType1Adapter extends BaseAdapter {

    private Context context;
    private List<FundTypeBean.DataBean>list;
    private int select=0;         //点击选中的item位置
    public FundType1Adapter(Context context, List<FundTypeBean.DataBean>list) {
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
            view=View.inflate(context, R.layout.item_select1,null);
            myHolder=new MyHolder(view);
            view.setTag(myHolder);
        }else{
            myHolder = (MyHolder) view.getTag();
        }
        myHolder.text.setText(list.get(position).getName());
        if(select==position){
            myHolder.line.setBackgroundResource(R.drawable.text_select);
            myHolder.text.setTextColor(Color.parseColor("#9fd1f7"));
            myHolder.pic.setVisibility(View.VISIBLE);
        }else {
            myHolder.line.setBackgroundResource(R.color.bg3);
            myHolder.text.setTextColor(Color.parseColor("#666666"));
            myHolder.pic.setVisibility(View.GONE);
        }
        return view;
    }
    class MyHolder{
        public TextView text;
        public ImageView pic;       //右箭头图标
        public LinearLayout line;   //图标显示时text背景设置为text_select,text字体颜色为lv,否则bg3,text字体颜色为text10
        public MyHolder(View view){
            text=(TextView)view.findViewById(R.id.text);
            pic=(ImageView)view.findViewById(R.id.pic);
            line=(LinearLayout)view.findViewById(R.id.home);
        }
    }
    public void updateData(List<FundTypeBean.DataBean>data,int select){
        this.list=data;
        this.select=select;
        notifyDataSetChanged();
    }
}
