package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.RecommendListBean;

/**
 * Created by duan on 2016/6/13.
 */
public class RecommendShopAdapter extends RecyclerView.Adapter<RecommendShopAdapter.MyHolder> {
    private Context context;
    private List<RecommendListBean.DataEntity>list;

    public RecommendShopAdapter(Context context,List<RecommendListBean.DataEntity>list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_recommendshop, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.itemHotIv.setImageURI(Uri.parse(list.get(position).getCover_img()));
        holder.address.setText(list.get(position).getAddress());
        holder.title.setText(list.get(position).getName());
        holder.value.setText(list.get(position).getReason());
        holder.time.setText(list.get(position).getCreated());
        List<String>datas=list.get(position).getType();
        String value="";
        for (String values:datas)holder.type.setText(value+=values+"  ");
        switch (list.get(position).getStatus()){
            case "1":

                holder.state2.setVisibility(View.VISIBLE);
                holder.state1.setVisibility(View.GONE);
                break;
            case "2":
                holder.state2.setVisibility(View.GONE);
                holder.state1.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView itemHotIv;
        TextView title,address,value,type,time,state1,state2;
        public MyHolder(View itemView) {
            super(itemView);
            itemHotIv= (SimpleDraweeView) itemView.findViewById(R.id.item_hot_Iv);
            title= (TextView) itemView.findViewById(R.id.item_recommend_shop_title);
            address= (TextView) itemView.findViewById(R.id.item_recommend_shop_address);
            value= (TextView) itemView.findViewById(R.id.item_recommend_shop_value);
            type= (TextView) itemView.findViewById(R.id.item_recommend_shop_type);
            time= (TextView) itemView.findViewById(R.id.item_recommend_shop_time);
            state1= (TextView) itemView.findViewById(R.id.state1);
            state2= (TextView) itemView.findViewById(R.id.state2);

        }
    }

    public void updateData(List<RecommendListBean.DataEntity> data){
        this.list=data;
        notifyDataSetChanged();
    }
}
