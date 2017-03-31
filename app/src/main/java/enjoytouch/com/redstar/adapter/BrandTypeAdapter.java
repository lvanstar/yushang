package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.Type;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.bean.TypeBean;

/**
 * Created by duan on 2016/6/27.
 */
public class BrandTypeAdapter extends BaseAdapter{
    private Context context;
    private TypeBean data;
    private int select=0;         //点击选中的item位置
    public BrandTypeAdapter(Context context,TypeBean data){
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
    public View getView(int position, View view, ViewGroup parent) {
        MyHolder myHolder;
        if (view==null){
            view=View.inflate(context, R.layout.item_type,null);
            myHolder=new MyHolder(view);
            view.setTag(myHolder);
        }else{
            myHolder = (MyHolder) view.getTag();
        }
        int position2 = position - 1;
        if(position2==-1){
            myHolder.text.setText("全部");
        }else {
            myHolder.text.setText(data.getData().get(position2).getName());
        }
        if(select==position){
            myHolder.text.setTextColor(Color.parseColor("#9fd1f7"));
        }else {
            myHolder.text.setTextColor(Color.parseColor("#666666"));
        }
        return view;
    }

    class MyHolder{
        TextView text;
        public MyHolder(View view){
            text=(TextView)view.findViewById(R.id.text);
        }
    }

    public void updateData(TypeBean data,int select){
        this.data=data;
        this.select=select;
        notifyDataSetChanged();
    }
}
