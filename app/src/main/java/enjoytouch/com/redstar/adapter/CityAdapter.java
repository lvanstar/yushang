package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.MapActivity;
import enjoytouch.com.redstar.bean.AddressBean;

/**
 * Created by Administrator on 2016/1/5.
 */
public class CityAdapter extends BaseAdapter {

    private Context context;
    private List<AddressBean> list;

    public CityAdapter(Context context, List<AddressBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_citylist, null);
            viewholder = new ViewHolder(view);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.name.setText(list.get(i).name);
        viewholder.address.setText(list.get(i).address);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapActivity.class);
                intent.putExtra("lat",list.get(i).lat);
                intent.putExtra("lng",list.get(i).lng);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private class ViewHolder {
        private TextView name;  //店铺名
        private TextView address;//店铺地址

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.city_list_name); //店铺名
            address = (TextView) view.findViewById(R.id.city_list_address);//店铺地址
            view.setTag(this);                          //给view设置标签
        }
    }
}
