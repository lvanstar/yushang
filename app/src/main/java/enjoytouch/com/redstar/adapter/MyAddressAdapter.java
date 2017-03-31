package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.HiGhestBean;
import enjoytouch.com.redstar.bean.MyAddressBean;
import enjoytouch.com.redstar.bean.OneAddressBean;
import enjoytouch.com.redstar.bean.UserAddressBean;

/**
 * Created by Administrator on 2015/12/14.
 */
public class MyAddressAdapter extends BaseAdapter{
    private Context context;
    private List<UserAddressBean> data;
    private int type;
    private int select;
    public MyAddressAdapter(Context context, List<UserAddressBean> data,int type) {
        this.context = context;
        this.data = data;
        this.type=type;
    }
    public void refresh(List<UserAddressBean> list) {
        this.data = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if (convertView==null){
            convertView=View.inflate(context,type==1?R.layout.item_myaddress:R.layout.item_myaddress2,null);
            myHolder=new MyHolder(convertView);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder) convertView.getTag();
        }
        myHolder.tv_shouhuoren.setText(data.get(position).getName());
        myHolder.tv_dianhua.setText(data.get(position).getTel());
        myHolder.tv_dizhi.setText("收货地址： " + data.get(position).getProvince_name() +
                data.get(position).getCity_name() + data.get(position).getArea_name() + data.get(position).getAddress());
        if(type==2){
            if(MyApplication.select==position){
                myHolder.iv.setImageResource(R.drawable.pay_select2);
            }else {
                myHolder.iv.setImageResource(R.drawable.pay_unselected);
            }
        }
        return convertView;
    }
    class MyHolder{
        public TextView tv_shouhuoren,tv_dianhua,tv_dizhi;
        public ImageView iv;
        public MyHolder(View view) {
            tv_shouhuoren= (TextView) view.findViewById(R.id.tv_shouhuoren);
            tv_dianhua= (TextView) view.findViewById(R.id.tv_dianhua);
            tv_dizhi= (TextView) view.findViewById(R.id.tv_dizhi666);
            iv=(ImageView)view.findViewById(R.id.iv);
        }
    }




}
