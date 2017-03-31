package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.AddressBean;

/**
 * Created by Administrator on 2015/12/2.
 */
public class AllShopAdapter extends BaseAdapter {
    private Context context;
    private List<AddressBean> list;
    public AllShopAdapter(Context c,List<AddressBean> list){
        context=c;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if(view==null){
            view=View.inflate(context, R.layout.item_allshop,null);
            viewholder=new ViewHolder(view);
        }else {
            viewholder=(ViewHolder)view.getTag();
        }
        viewholder.name.setText( list.get(i).name);
        viewholder.address.setText(list.get(i).address);
       // view=View.inflate(context, R.layout.item_allshop,null);
        return view;
    }
    private class ViewHolder{
        private TextView name;  //店铺名
        private TextView address;//店铺地址
        public ViewHolder(View view){
            name=(TextView)view.findViewById(R.id.tv1); //店铺名
            address=(TextView)view.findViewById(R.id.tv2);//店铺地址
            view.setTag(this);                          //给view设置标签
        }
    }
}
