package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.LogisticsListBean;

/**
 * Created by Administrator on 2016/6/6.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private Context context;
    private List<LogisticsListBean.DataEntity>list;

    public TimelineAdapter(Context context,List<LogisticsListBean.DataEntity>list){
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.logisticslist_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(getItemCount()-1==position){
            holder.xian.setVisibility(View.GONE);
        }
        if(position==0){
            holder.iv1.setVisibility(View.GONE);
            holder.iv2.setVisibility(View.VISIBLE);
            holder.info.setTextColor(context.getResources().getColor(R.color.refresh_color));
        }else {

            holder.iv1.setVisibility(View.VISIBLE);
            holder.iv2.setVisibility(View.GONE);
            holder.info.setTextColor(context.getResources().getColor(R.color.text04));
        }
        holder.info.setText("["+list.get(position).getAccept_address()+"]  "+list.get(position).getRemark());
        holder.time.setText(list.get(position).getAccept_time());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView time;
        public TextView info;
        private ImageView iv1,iv2;
        private View xian;
        public ViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.tv_time);
            info = (TextView) view.findViewById(R.id.title);
            iv1= (ImageView) view.findViewById(R.id.logisticslist_iv1);
            iv2= (ImageView) view.findViewById(R.id.logisticslist_iv2);
            xian=view.findViewById(R.id.logisticslist_xian);
        }
    }


    public void updateData(List<LogisticsListBean.DataEntity>list){
        this.list=list;
        notifyDataSetChanged();
    }


}
