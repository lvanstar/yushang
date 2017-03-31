package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.FoundDetailActivity;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.ProductListBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by duan on 2016/6/27.
 */
public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.ViewHolder>{
    private Context context;
    public FashionAdapter adapter;
    private ProductListBean list;

    public FashionAdapter(Context context, ProductListBean list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_fashionlist,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewGroup.LayoutParams ll1_params = holder.ll1.getLayoutParams();
        ll1_params.height= MyApplication.currDisplay.getWidth()/3+80;
        holder.ll1.setLayoutParams(ll1_params);

        ViewGroup.LayoutParams ll2_params = holder.ll2.getLayoutParams();
        ll2_params.height= MyApplication.currDisplay.getWidth()/3+80;
        holder.ll2.setLayoutParams(ll2_params);
        ContentUtil.makeLog("正在执行","adapter");
        ViewGroup.LayoutParams ll3_params = holder.ll3.getLayoutParams();
        ll3_params.height= MyApplication.currDisplay.getWidth()/3+80;
        holder.ll3.setLayoutParams(ll3_params);

        if(list.getData().get(position).getCover_img()!=""){
            ContentUtil.makeLog("正在执行","大图片");
            ViewGroup.LayoutParams params = holder.ll.getLayoutParams();
            params.height= MyApplication.currDisplay.getWidth();
            holder.ll.setLayoutParams(params);
            holder.sdv.setImageURI(Uri.parse(list.getData().get(position).getCover_img()));
            ContentUtil.makeLog("图片",list.getData().get(position).getCover_img());
            holder.sdv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExclusiveYeUtils.onMyEvent(context, "lookStyleImg");
                    ArrayList<String> urlList = new ArrayList<String>();
                    urlList.add(list.getData().get(position).getCover_img());
                    ExclusiveYeUtils.toShowBigImages(context,urlList,1);
                }
            });
        }
        if(list.getData().get(position).getProduct().size()>0){
            holder.ll1.setVisibility(View.VISIBLE);
            holder.iv1.setVisibility(View.VISIBLE);
            holder.sdv1.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(0).getCover_img()));
            holder.name1.setText("[ "+list.getData().get(position).getProduct().get(0).getName()+" ]");
            holder.content1.setText(list.getData().get(position).getProduct().get(0).getSubtitle());
            onclick(holder.sdv1,list.getData().get(position).getProduct().get(0).getType(),list.getData().get(position).getProduct().get(0).getId());
            if(list.getData().get(position).getProduct().size()>1){
                holder.iv2.setVisibility(View.VISIBLE);
                holder.sdv2.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(1).getCover_img()));
                holder.name2.setText("[ "+list.getData().get(position).getProduct().get(1).getName()+" ]");
                holder.content2.setText(list.getData().get(position).getProduct().get(1).getSubtitle());
                onclick(holder.sdv2,list.getData().get(position).getProduct().get(1).getType(),list.getData().get(position).getProduct().get(1).getId());
            }else {
                holder.iv2.setVisibility(View.INVISIBLE);
            }
            if(list.getData().get(position).getProduct().size()>2){
                holder.iv3.setVisibility(View.VISIBLE);
                holder.sdv3.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(2).getCover_img()));
                holder.name3.setText("[ "+list.getData().get(position).getProduct().get(2).getName()+" ]");
                holder.content3.setText(list.getData().get(position).getProduct().get(2).getSubtitle());
                onclick(holder.sdv3,list.getData().get(position).getProduct().get(2).getType(),list.getData().get(position).getProduct().get(2).getId());
            }else {
                holder.iv3.setVisibility(View.INVISIBLE);
            }
        }else {
            holder.ll1.setVisibility(View.GONE);
        }
        if(list.getData().get(position).getProduct().size()>3){
            holder.ll2.setVisibility(View.VISIBLE);
            holder.iv4.setVisibility(View.VISIBLE);
            holder.sdv4.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(3).getCover_img()));
            holder.name4.setText("[ "+list.getData().get(position).getProduct().get(3).getName()+" ]");
            holder.content4.setText(list.getData().get(position).getProduct().get(3).getSubtitle());
            onclick(holder.sdv4,list.getData().get(position).getProduct().get(3).getType(),list.getData().get(position).getProduct().get(3).getId());
            if(list.getData().get(position).getProduct().size()>4){
                holder.iv5.setVisibility(View.VISIBLE);
                holder.sdv5.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(4).getCover_img()));
                holder.name5.setText("[ "+list.getData().get(position).getProduct().get(4).getName()+" ]");
                holder.content5.setText(list.getData().get(position).getProduct().get(4).getSubtitle());
                onclick(holder.sdv5,list.getData().get(position).getProduct().get(4).getType(),list.getData().get(position).getProduct().get(4).getId());
            }
            if(list.getData().get(position).getProduct().size()>5){
                holder.iv6.setVisibility(View.VISIBLE);
                holder.sdv6.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(5).getCover_img()));
                holder.name6.setText("[ "+list.getData().get(position).getProduct().get(5).getName()+" ]");
                holder.content6.setText(list.getData().get(position).getProduct().get(5).getSubtitle());
                onclick(holder.sdv6,list.getData().get(position).getProduct().get(5).getType(),list.getData().get(position).getProduct().get(5).getId());
            }
        }else {
            holder.ll2.setVisibility(View.GONE);
        }
        if(list.getData().get(position).getProduct().size()>6){
            holder.ll3.setVisibility(View.VISIBLE);
            holder.iv7.setVisibility(View.VISIBLE);
            holder.sdv7.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(6).getCover_img()));
            holder.name7.setText("[ "+list.getData().get(position).getProduct().get(6).getName()+" ]");
            holder.content7.setText(list.getData().get(position).getProduct().get(6).getSubtitle());
            onclick(holder.sdv7,list.getData().get(position).getProduct().get(6).getType(),list.getData().get(position).getProduct().get(6).getId());
            if(list.getData().get(position).getProduct().size()>7){
                holder.iv8.setVisibility(View.VISIBLE);
                holder.sdv8.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(7).getCover_img()));
                holder.name8.setText("[ "+list.getData().get(position).getProduct().get(7).getName()+" ]");
                holder.content8.setText(list.getData().get(position).getProduct().get(7).getSubtitle());
                onclick(holder.sdv8,list.getData().get(position).getProduct().get(7).getType(),list.getData().get(position).getProduct().get(7).getId());
            }
            if(list.getData().get(position).getProduct().size()>8){
                holder.iv9.setVisibility(View.VISIBLE);
                holder.sdv9.setImageURI(Uri.parse(list.getData().get(position).getProduct().get(8).getCover_img()));
                holder.name9.setText("[ "+list.getData().get(position).getProduct().get(8).getName()+" ]");
                holder.content9.setText(list.getData().get(position).getProduct().get(8).getSubtitle());
                onclick(holder.sdv9,list.getData().get(position).getProduct().get(8).getType(),list.getData().get(position).getProduct().get(8).getId());
            }
        }else {
            holder.ll3.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.getData().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sdv1;
        public TextView name1;
        public TextView content1;
        public SimpleDraweeView sdv2;
        public TextView name2;
        public TextView content2;
        public SimpleDraweeView sdv3;
        public TextView name3;
        public TextView content3;
        public SimpleDraweeView sdv4;
        public TextView name4;
        public TextView content4;
        public SimpleDraweeView sdv5;
        public TextView name5;
        public TextView content5;
        public SimpleDraweeView sdv6;
        public TextView name6;
        public TextView content6;
        public SimpleDraweeView sdv7;
        public TextView name7;
        public TextView content7;
        public SimpleDraweeView sdv8;
        public TextView name8;
        public TextView content8;
        public SimpleDraweeView sdv9;
        public TextView name9;
        public TextView content9;
        public LinearLayout iv1;
        public LinearLayout iv2;
        public LinearLayout iv3;
        public LinearLayout iv4;
        public LinearLayout iv5;
        public LinearLayout iv6;
        public LinearLayout iv7;
        public LinearLayout iv8;
        public LinearLayout iv9;
        public LinearLayout ll1;
        public LinearLayout ll2;
        public LinearLayout ll3;
        public LinearLayout ll;

        public SimpleDraweeView sdv;
        public ViewHolder(View view){
            super(view);
            ll=(LinearLayout)view.findViewById(R.id.ll);
            ll1=(LinearLayout)view.findViewById(R.id.ll1);
            ll2=(LinearLayout)view.findViewById(R.id.ll2);
            ll3=(LinearLayout)view.findViewById(R.id.ll3);
            sdv=(SimpleDraweeView)view.findViewById(R.id.sdv);
            sdv1=(SimpleDraweeView) view.findViewById(R.id.sdv1);
            name1=(TextView)view.findViewById(R.id.name1);
            content1=(TextView)view.findViewById(R.id.content1);
            sdv2=(SimpleDraweeView) view.findViewById(R.id.sdv2);
            name2=(TextView)view.findViewById(R.id.name2);
            content2=(TextView)view.findViewById(R.id.content2);
            sdv3=(SimpleDraweeView) view.findViewById(R.id.sdv3);
            name3=(TextView)view.findViewById(R.id.name3);
            content3=(TextView)view.findViewById(R.id.content3);
            sdv4=(SimpleDraweeView) view.findViewById(R.id.sdv4);
            name4=(TextView)view.findViewById(R.id.name4);
            content4=(TextView)view.findViewById(R.id.content4);
            sdv5=(SimpleDraweeView) view.findViewById(R.id.sdv5);
            name5=(TextView)view.findViewById(R.id.name5);
            content5=(TextView)view.findViewById(R.id.content5);
            sdv6=(SimpleDraweeView) view.findViewById(R.id.sdv6);
            name6=(TextView)view.findViewById(R.id.name6);
            content6=(TextView)view.findViewById(R.id.content6);
            sdv7=(SimpleDraweeView) view.findViewById(R.id.sdv7);
            name7=(TextView)view.findViewById(R.id.name7);
            content7=(TextView)view.findViewById(R.id.content7);
            sdv8=(SimpleDraweeView) view.findViewById(R.id.sdv8);
            name8=(TextView)view.findViewById(R.id.name8);
            content8=(TextView)view.findViewById(R.id.content8);
            sdv9=(SimpleDraweeView) view.findViewById(R.id.sdv9);
            name9=(TextView)view.findViewById(R.id.name9);
            content9=(TextView)view.findViewById(R.id.content9);
            iv1=(LinearLayout)view.findViewById(R.id.iv1);
            iv2=(LinearLayout)view.findViewById(R.id.iv2);
            iv3=(LinearLayout)view.findViewById(R.id.iv3);
            iv4=(LinearLayout)view.findViewById(R.id.iv4);
            iv5=(LinearLayout)view.findViewById(R.id.iv5);
            iv6=(LinearLayout)view.findViewById(R.id.iv6);
            iv7=(LinearLayout)view.findViewById(R.id.iv7);
            iv8=(LinearLayout)view.findViewById(R.id.iv8);
            iv9=(LinearLayout)view.findViewById(R.id.iv9);
        }
    }

    public void updateData(ProductListBean data){
        this.list=data;
        notifyDataSetChanged();
    }
    private void onclick(SimpleDraweeView view, final String string, final String id){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(string.equals("3")){  //线下商品
                    ExclusiveYeUtils.onMyEvent(context, "lookStyleListDetail");
                    Intent intent=new Intent(context,FoundDetailActivity.class);
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }else {
                    ExclusiveYeUtils.onMyEvent(context, "lookStyleListDetail");
                    Intent intent=new Intent(context,ShopDetailsActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA,id);
                    context.startActivity(intent);
                }
            }
        });
    }
}
