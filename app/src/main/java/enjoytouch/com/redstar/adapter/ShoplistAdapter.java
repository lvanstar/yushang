package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.MapActivity;
import enjoytouch.com.redstar.bean.ShopBean;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.MenuDialogUtils;

/**
 * Created by lzz on 2016/6/29.
 */
public class ShoplistAdapter extends BaseAdapter {
    private MenuDialogUtils menuDialog;
    private Context context;
    private List<ShopBean> list;

    public ShoplistAdapter(Context context, List<ShopBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_shoplist, null);
            holder = new ViewHolder(convertView);


            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        holder.shopTitle.setText(list.get(position).getName());
        holder.shop_address.setText(list.get(position).getAddress());
        holder.time1.setText(list.get(position).getBusiness_hours());
        holder.tell.setText(list.get(position).getTel());
        holder.tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhone(v,position);
            }
        });

        holder.shop_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("lat",list.get(position).getLat());
                intent.putExtra("lng",list.get(position).getLng());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView shopTitle;
        public TextView shop_address;
        public TextView time1;
        public TextView tell;

        public ViewHolder(View view) {
            shopTitle = (TextView) view.findViewById(R.id.shopTitle);
            shop_address = (TextView) view.findViewById(R.id.shop_address);
            time1 = (TextView) view.findViewById(R.id.time1);
            tell = (TextView) view.findViewById(R.id.tell);
            tell.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            tell.getPaint().setAntiAlias(true);
            shop_address.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            shop_address.getPaint().setAntiAlias(true);
        }
    }

    public void makePhone(View V, final int position){
        menuDialog= new MenuDialogUtils(context, R.style.registDialog, R.layout.menu_phone, list.get(position).getTel(), new MenuDialogUtils.ButtonClickListener() {
            @Override
            public void onButtonClick(int i) {
                if (i==0){
                    if (!"".equals(list.get(position).getTel())) {
                        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + list.get(position).getTel()));
                       context. startActivity(phoneIntent);
                    } else {
                        DialogUtil.show(context, "号码未知，无法拨打", false).show();
                    }
                }
            }
        });
        menuDialog.show();
    }

}
