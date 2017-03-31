package enjoytouch.com.redstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.FoundDetailActivity;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.bean.MyCollectionBean;
import enjoytouch.com.redstar.util.DisplayUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by lizhaozhao on 16/6/15.
 */
public class HiGhestAdapter extends RecyclerView.Adapter<HiGhestAdapter.ViewHolder> {
    private Context context;
    private List<MyCollectionBean>list;
    private String type;

    public HiGhestAdapter(Context context,List<MyCollectionBean>list,String type) {
        this.context = context;
        this.list=list;
        this.type=type;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shangpin, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        setRelativeLayoutParams(holder.sv);
        holder.sv.setImageURI(Uri.parse(list.get(position).getCover_img()));
        holder.price_Tv.setText(list.get(position).getSale_price());
        holder.title_Tv.setText(list.get(position).getName());
        holder.values_Tv.setText(list.get(position).getSubtitle());
        holder.number_Tv.setText(list.get(position).getCollect_cnt());
        if(!"3".equals(type)){
            holder.price_Tv.setText("￥"+list.get(position).getSale_price());
            holder.shop_clear.setVisibility(list.get(position).getRemaind()==0?View.VISIBLE:View.GONE);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(context, "lookMyCollectHotStyleDetail");
                if (type=="3"){
                    Intent intent=new Intent(context, FoundDetailActivity.class);
                    intent.putExtra("id",list.get(position).getId());
                    context.startActivity(intent);
                }else {
                    ExclusiveYeUtils.onMyEvent(context, "lookMyCollectHotStyleDetail");
                    Intent intent=new Intent(context,ShopDetailsActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA,list.get(position).getId());
                    context.startActivity(intent);

                }

            }
        });

        if("0".equals(list.get(position).getIs_collect())){
            holder.status_iv.setImageResource(R.drawable.shop01);
            holder.number_Tv.setTextColor(context.getResources().getColor(R.color.xuanzhong));

        }else{
            holder.status_iv.setImageResource(R.drawable.shop02);
            holder.number_Tv.setTextColor(context.getResources().getColor(R.color.text06));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView sv;
        private TextView title_Tv,values_Tv,price_Tv,number_Tv;
        private ImageView shop_clear;
        private LinearLayout view;
        private ImageView status_iv;

        public ViewHolder(View itemView) {
            super(itemView);

            sv = (SimpleDraweeView) itemView.findViewById(R.id.item_shangpin_sv);
            title_Tv= (TextView) itemView.findViewById(R.id.shangpin_title);
            values_Tv= (TextView) itemView.findViewById(R.id.shangpin_values);
            price_Tv= (TextView) itemView.findViewById(R.id.shangpin_price);
            price_Tv.getResources().getColor(R.color.text04);
            number_Tv= (TextView) itemView.findViewById(R.id.shangpin_number);
            shop_clear= (ImageView) itemView.findViewById(R.id.shangpin_clear_iv);
            view= (LinearLayout) itemView.findViewById(R.id.shangpin_ll);
            status_iv= (ImageView) itemView.findViewById(R.id.item_shang_collect_iv);
        }
    }

    private void setRelativeLayoutParams(SimpleDraweeView view) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int w = (int) (width * 0.5) -DisplayUtil.dip2px(((Activity) context), 10f);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height=w;
        params.width=w;
        view.setLayoutParams(params);
 }
    public void updateData(List<MyCollectionBean> data){
        this.list=data;
        notifyDataSetChanged();
    }
}
