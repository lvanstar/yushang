package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.bean.OrderManager;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.SkuInfoBean;
import enjoytouch.com.redstar.bean.TitleBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MakeOrderAdapter extends BaseAdapter {

    private Context context;
    private List<OrderManager>list;

    public MakeOrderAdapter(Context context,List<OrderManager>list) {
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        switch (getItemViewType(position)) {
            case OrderManager.VALUE:
                view = View.inflate(context, R.layout.item_make_order, null);
                SimpleDraweeView iv = (SimpleDraweeView) view.findViewById(R.id.iv_orderimg);
                TextView name = (TextView) view.findViewById(R.id.item_make_order_name);
                TextView price = (TextView) view.findViewById(R.id.text_price);
                TextView number = (TextView) view.findViewById(R.id.text_number);
                final SkuInfoBean infoBeans = (SkuInfoBean) list.get(position).object;
                RelativeLayout order= (RelativeLayout) view.findViewById(R.id.make_orod);
                iv.setImageURI(Uri.parse(infoBeans.getCover_img()));
                name.setText(infoBeans.getName());
                price.setText("¥" + infoBeans.getSale_price());
                number.setText("数量:" + infoBeans.getAmount());
                order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(context, ShopDetailsActivity.class);
                        intent.putExtra(GlobalConsts.INTENT_DATA,infoBeans.getId());
                        context.startActivity(intent);
                    }
                });
                break;
            case OrderManager.TITLE:
                view = View.inflate(context, R.layout.item_make_order_title, null);
                TextView title = (TextView) view.findViewById(R.id.item_make_order_title);
                TitleBean titleBean = (TitleBean) list.get(position).object;
                title.setText("商家:"+titleBean.getTitle());
                break;
        }
        return view;
    }

}
