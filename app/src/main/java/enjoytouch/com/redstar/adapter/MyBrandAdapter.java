package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import enjoytouch.com.redstar.R;

import enjoytouch.com.redstar.activity.BrandDetailActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;

/**
 * Created by Administrator on 2015/12/16.
 * 这是品牌
 */
public class MyBrandAdapter extends RecyclerView.Adapter<MyBrandAdapter.ViewHolder>{
    private Context context;
    private BrandDetailBean list;
    private String type;        //用来判断这个adapter是哪个页面在用，1是搜索结果列表，0是列表

    public MyBrandAdapter(Context c, BrandDetailBean list,String type){
        context=c;
        this.list=list;
        this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_brandlist,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.sdv.setImageURI(Uri.parse(list.getData().get(position).getLogo()));
        holder.name.setText(list.getData().get(position).getName());
        holder.content.setText(list.getData().get(position).getSub_name());
        ViewGroup.LayoutParams gv_params1 = holder.line.getLayoutParams();
        gv_params1.height= MyApplication.currDisplay.getWidth()*11/20;
        holder.line.setLayoutParams(gv_params1);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("0")){
                    ExclusiveYeUtils.onMyEvent(context, "lookBrandDetail");
                }
                if(type.equals("1")){
                    ExclusiveYeUtils.onMyEvent(context, "brandDetailWithSearch");
                }
                Intent intent=new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id",list.getData().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sdv;
        public RelativeLayout line;
        public TextView name;
        public TextView content;
        public ViewHolder(View view){
            super(view);
            //home=(RelativeLayout)view.findViewById(R.id.view);
            sdv=(SimpleDraweeView)view.findViewById(R.id.sdv);
            line=(RelativeLayout)view.findViewById(R.id.view);
            name=(TextView)view.findViewById(R.id.text1);
            content=(TextView)view.findViewById(R.id.text2);
        }
    }

    public void updateData(BrandDetailBean data){
        this.list=data;
        notifyDataSetChanged();
    }
}
