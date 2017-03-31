package enjoytouch.com.redstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.FoundDetailActivity;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.bean.FundListBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.util.DisplayUtil;

/**
 * Created by duan on 2016/6/23.
 */
public class FundAdapter extends RecyclerView.Adapter<FundAdapter.ViewHolder> {
    private Context context;
    private FundListBean list;

    public FundAdapter(Context c,FundListBean list){
        context=c;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_founddetail,null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //holder.sdv.setImageURI(Uri.parse(list.get(position).getCover_img()));
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(context, "lookLineProductsDetail");
                Intent intent=new Intent(context,FoundDetailActivity.class);
                intent.putExtra("id",list.getData().get(position).getId());
                context.startActivity(intent);
            }
        });
        setRelativeLayoutParams(holder.sdv);
        holder.sdv.setImageURI(Uri.parse(list.getData().get(position).getCover_img()));
        holder.name.setText(list.getData().get(position).getName());
        holder.subtitle.setText(list.getData().get(position).getSubtitle());
        holder.collect_cnt.setText(list.getData().get(position).getCollect_cnt());
        if(list.getData().get(position).getIs_collect().equals("0")){//未收藏
            holder.pic.setImageResource(R.drawable.shop01);
            holder.collect_cnt.setTextColor(Color.parseColor("#bababa"));
        }else {
            holder.pic.setImageResource(R.drawable.shop02);
            holder.collect_cnt.setTextColor(Color.parseColor("#ffb3bc"));
        }
    }

    @Override
    public int getItemCount() {
        return list.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sdv;
        public LinearLayout line;
        public LinearLayout item;
        public TextView name;
        public TextView subtitle;
        public TextView collect_cnt;
        public ImageView pic;
        public TextView xianxia;
        public ViewHolder(View view){
            super(view);
            line=(LinearLayout)view.findViewById(R.id.home);
            sdv=(SimpleDraweeView)view.findViewById(R.id.sdv_myhot);
            name=(TextView)view.findViewById(R.id.title);
            subtitle=(TextView)view.findViewById(R.id.content);
            collect_cnt=(TextView)view.findViewById(R.id.number);
            pic=(ImageView)view.findViewById(R.id.pic);
            xianxia=(TextView)view.findViewById(R.id.xianxia);
        }
    }

    public void updateData(FundListBean data){
        this.list=data;
        notifyDataSetChanged();
    }

    private void setRelativeLayoutParams(SimpleDraweeView view) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int w = (int) (width * 0.5) - DisplayUtil.dip2px(((Activity) context), 10f);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height=w;
        params.width=w;
        view.setLayoutParams(params);
    }
}