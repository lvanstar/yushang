package enjoytouch.com.redstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.FoundDetailActivity;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.bean.CreativeBean;
import enjoytouch.com.redstar.bean.ProductListBean;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by lizhaozhao on 16/8/15.
 */
public class HomeStyleAdapter extends BaseAdapter{

    private Context context;
    private List<CreativeBean>list;
    public HomeStyleAdapter(Context context,List<CreativeBean>list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return 2;
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
        ViewHolder myHolder;
        if (convertView == null) {
            convertView=View.inflate(context, R.layout.item_home_style,null);
            myHolder=new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }

        setRelativeLayoutParams(myHolder.style_iv);
        myHolder.style_iv.setImageURI(Uri.parse(list.get(position).getCover_img()));
        if(list.get(position).getProduct().size()>0){
            myHolder.shop_iv01.setImageURI(Uri.parse(list.get(position).getProduct().get(0).getCover_img()));
            myHolder.name1.setText(list.get(position).getProduct().get(0).getName());
            myHolder.value1.setText(list.get(position).getProduct().get(0).getSubtitle());
            onclick(myHolder.shop_iv01, list.get(position).getProduct().get(0).getType(), list.get(position).getProduct().get(0).getId());
        }
        if(list.get(position).getProduct().size()>1){
            myHolder.shop_iv02.setImageURI(Uri.parse(list.get(position).getProduct().get(1).getCover_img()));
            myHolder.name2.setText(list.get(position).getProduct().get(1).getName());
            myHolder.value2.setText(list.get(position).getProduct().get(1).getSubtitle());
            onclick(myHolder.shop_iv02, list.get(position).getProduct().get(1).getType(), list.get(position).getProduct().get(1).getId());
        }
        if (list.get(position).getProduct().size()>2){
            myHolder.shop_iv03.setImageURI(Uri.parse(list.get(position).getProduct().get(2).getCover_img()));
            myHolder.name3.setText(list.get(position).getProduct().get(2).getName());
            myHolder.value3.setText(list.get(position).getProduct().get(2).getSubtitle());
            onclick(myHolder.shop_iv03,list.get(position).getProduct().get(2).getType(),list.get(position).getProduct().get(2).getId());

        }





        return  convertView;
    }
    public class ViewHolder{

        SimpleDraweeView style_iv,shop_iv01,shop_iv02,shop_iv03;
        TextView name1,name2,name3,value1,value2,value3;
        public ViewHolder(View view) {

            style_iv= (SimpleDraweeView) view.findViewById(R.id.item_home_style_iv);
            shop_iv01= (SimpleDraweeView) view.findViewById(R.id.home_style_shop_iv01);
            shop_iv02= (SimpleDraweeView) view.findViewById(R.id.home_style_shop_iv02);
            shop_iv03= (SimpleDraweeView) view.findViewById(R.id.home_style_shop_iv03);
            name1= (TextView) view.findViewById(R.id.name1);
            name2= (TextView) view.findViewById(R.id.name2);
            name3= (TextView) view.findViewById(R.id.name3);
            value1= (TextView) view.findViewById(R.id.content1);
            value2= (TextView) view.findViewById(R.id.content2);
            value3= (TextView) view.findViewById(R.id.content3);
        }
    }
    private void setRelativeLayoutParams(View view) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int h = (int)width;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height=h;
        view.setLayoutParams(params);
    }


    private void onclick(SimpleDraweeView view, final String string, final String id){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (string.equals("3")) {  //线下商品
                    ExclusiveYeUtils.onMyEvent(context, "lookStyleListDetail");
                    Intent intent = new Intent(context, FoundDetailActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                } else {
                    ExclusiveYeUtils.onMyEvent(context, "lookStyleListDetail");
                    Intent intent = new Intent(context, ShopDetailsActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA, id);
                    context.startActivity(intent);
                }
            }
        });
    }
}
