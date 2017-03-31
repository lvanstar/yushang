package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MyReceiptAddressAdapter extends BaseAdapter {

    private Context context;
    public int i = -1;
    private String id="";

    public MyReceiptAddressAdapter(Context context,String id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewholder;
        view = View.inflate(context, R.layout.item_receipt, null);
        viewholder = new ViewHolder(view);
//        viewholder.name.setText("收货人:" + list.get(position).name);
//        viewholder.address.setText("收货地址：" + list.get(position).province_name + list.get(position).city_name + list.get(position).area_name + list.get(position).address);
//        viewholder.phone.setText(list.get(position).tel);
//
//        if(id.equals(list.get(position).id)){
//            viewholder.iv.setImageResource(R.drawable.pay_select2);
//        }else{
//            viewholder.iv.setImageResource(R.drawable.pay_unselected);
//        }
//        if (i != position) {
//            viewholder.iv.setImageResource(R.drawable.pay_unselected);
//        } else {
//            viewholder.iv.setImageResource(R.drawable.pay_select2);
//        }
        return view;
    }

    private class ViewHolder {
        private TextView name;  //店铺名
        private TextView address;//店铺地址
        private TextView phone;
        private ImageView iv;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.receipt_name); //店铺名
            address = (TextView) view.findViewById(R.id.receipt_address);//店铺地址
            phone = (TextView) view.findViewById(R.id.receipt_phone);
            iv = (ImageView) view.findViewById(R.id.receipt_Iv);
        }
    }

}
