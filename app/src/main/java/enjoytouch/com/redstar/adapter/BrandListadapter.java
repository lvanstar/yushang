package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.QuChuDetailActivity;
import enjoytouch.com.redstar.bean.FundListBean;
import enjoytouch.com.redstar.bean.InterestBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by Administrator on 2015/12/1.
 */
public class BrandListadapter extends RecyclerView.Adapter<BrandListadapter.ViewHolder> {
    private Context context;
    private List<ZhuanTiBean.DataBean.CoolShopBean> list;
    private String id;
    private ZhuanTiBean.DataBean bean;

    public BrandListadapter(Context c,List<ZhuanTiBean.DataBean.CoolShopBean> list,String id,ZhuanTiBean.DataBean bean){
        context=c;
        this.list=list;
        this.id=id;
        this.bean=bean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_brandlist1,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.sdv.setImageURI(Uri.parse(list.get(position).getCover_img()));
        holder.text1.setText(list.get(position).getName());
        holder.text2.setText(list.get(position).getTitle());
        holder.alpha.getBackground().setAlpha(77);
        if(position==0){
            holder.rl1.setVisibility(View.VISIBLE);
            holder.head_iv.setImageURI(Uri.parse(bean.getCover_img()));
            holder.name.setText(bean.getName());
            holder.value.setText(bean.getTitle());
        }else {
            holder.rl1.setVisibility(View.GONE);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(context, "lookSpecialInterestingList");
                Intent intent=new Intent(context, QuChuDetailActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,new String[]{list.get(position).getId(),id});
                intent.putExtra("share","2");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView sdv;
        public TextView text1;
        public TextView text2;
        public View rl;
        public RelativeLayout alpha;
        public RelativeLayout rl1;
        public SimpleDraweeView head_iv;
        public TextView name;
        public TextView value;
        public ViewHolder(View view){
            super(view);
            sdv=(SimpleDraweeView)view.findViewById(R.id.brandlist_sv);
            text1=(TextView)view.findViewById(R.id.brandlist_name);
            text2=(TextView)view.findViewById(R.id.brandlist_title);
            rl=view.findViewById(R.id.item_brandlist_rl);
            alpha=(RelativeLayout)view.findViewById(R.id.alpha);
            rl1=(RelativeLayout)view.findViewById(R.id.rl1);
            head_iv=(SimpleDraweeView)view.findViewById(R.id.brand_home_list2_sv);
            name=(TextView)view.findViewById(R.id.brand_list_head_name);
            value=(TextView)view.findViewById(R.id.brand_list_head_title);
        }
    }

    public void updateData(List<ZhuanTiBean.DataBean.CoolShopBean> data){
        this.list=data;
        notifyDataSetChanged();
    }
}
