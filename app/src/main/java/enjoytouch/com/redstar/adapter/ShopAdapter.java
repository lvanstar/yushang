package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BrandHomeList1Activity;
import enjoytouch.com.redstar.activity.BrandHomeList2Activity;
import enjoytouch.com.redstar.activity.QuChuDetailActivity;
import enjoytouch.com.redstar.bean.HiGhestBean;
import enjoytouch.com.redstar.bean.InterestBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by Administrator on 2015/11/25.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context context;
    public List<InterestBean.DataEntity> list;
    private int type;
    public ShopAdapter(Context c,List<InterestBean.DataEntity> list){
        this.context=c;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case 1:
                type=1;
               view =View.inflate(context,R.layout.item_myshop,null);
                break;
            case 2:
                type=2;
                view =View.inflate(context,R.layout.item_zhuanti,null);
                break;
            case 3:
                type=3;
                view =View.inflate(context,R.layout.item_yuedu,null);
                break;
        }

        return new ViewHolder(view,type);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.name_Tv.setText(list.get(position).getName());
        holder.value_Tv.setText(list.get(position).getTitle());
        holder.pic.setImageURI(Uri.parse(list.get(position).getCover_img()));
        switch (list.get(position).getType()){
            case "1":
                holder.line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(context, "lookStoreDetails");
                        Intent intent=new Intent(context,QuChuDetailActivity.class);
                        intent.putExtra(GlobalConsts.INTENT_DATA,new String[]{list.get(position).getShop_id(),list.get(position).getId()});
                        context.startActivity(intent);
                    }
                });
                holder.address_Tv.setText(list.get(position).getCircle());
                holder.name_Tv.setText(list.get(position).getName());
                holder.value_Tv.setText(list.get(position).getTitle());

                if(list.get(position).getLabel()!=null){
                    for (int i=0;i<list.get(position).getLabel().size();i++){
                        holder.sivs[i].setVisibility(View.VISIBLE);
                        holder.sivs[i].setImageURI(Uri.parse(list.get(position).getLabel().get(i).getIcon()));
                    }
                }
                break;
            case "2":
                holder.line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(context, "lookSpecialInteresting");
                        Intent intent2=new Intent(context,BrandHomeList1Activity.class);
                        intent2.putExtra("id",list.get(position).getId());
                        context.startActivity(intent2);
                    }
                });
                break;
            case "3":
                holder.line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(context, "lookImageTextDetail");
                        Intent intent3=new Intent(context,BrandHomeList2Activity.class);
                        intent3.putExtra("id",list.get(position).getId());
                        context.startActivity(intent3);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getType()){
            case "1":
                type=1;
                break;
            case "2":
                type=2;
                break;
            case "3":
                type=3;
                break;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View line;
        private TextView name_Tv,value_Tv,address_Tv;
        private SimpleDraweeView pic;
        private SimpleDraweeView[]sivs;

        public ViewHolder(View view,int type) {
            super(view);
            switch (type){
                case 1:
                    pic= (SimpleDraweeView) view.findViewById(R.id.item_hot_Iv);
                    name_Tv= (TextView) view.findViewById(R.id.myshop_name);
                    value_Tv= (TextView) view.findViewById(R.id.myshop_address);
                    address_Tv= (TextView) view.findViewById(R.id.address_tv);
                    line=view.findViewById(R.id.myshop_view);
                    sivs= new SimpleDraweeView[]{(SimpleDraweeView) view.findViewById(R.id.my_shop_iv01),(SimpleDraweeView) view.findViewById(R.id.my_shop_iv02),
                            (SimpleDraweeView) view.findViewById(R.id.my_shop_iv03),(SimpleDraweeView) view.findViewById(R.id.my_shop_iv04)};
                    break;
                case 2:
                    pic= (SimpleDraweeView) view.findViewById(R.id.item_zhuanti_iv);
                    name_Tv= (TextView) view.findViewById(R.id.item_zhuanti_tv01);
                    value_Tv= (TextView) view.findViewById(R.id.item_zhuanti_tv02);
                    line=view.findViewById(R.id.item_zhuanti_view);
                    break;
                case 3:
                    pic= (SimpleDraweeView) view.findViewById(R.id.item_yuedu_iv);
                    name_Tv= (TextView) view.findViewById(R.id.item_yuedu_tv01);
                    value_Tv= (TextView) view.findViewById(R.id.item_yuedu_tv02);
                    line=view.findViewById(R.id.item_yuedu_view);
                    break;
            }
        }
    }

    public void updateData(List<InterestBean.DataEntity> data){
        this.list=data;
        notifyDataSetChanged();
    }
}
