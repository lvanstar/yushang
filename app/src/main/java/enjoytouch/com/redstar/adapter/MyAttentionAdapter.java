package enjoytouch.com.redstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BrandDetailActivity;
import enjoytouch.com.redstar.bean.BrandBean;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;

/**
 * Created by lizhaozhao on 16/6/29.
 */
public class MyAttentionAdapter extends RecyclerView.Adapter<MyAttentionAdapter.ViewHodler> {

    private Context context;
    private List<BrandBean> list;

    public MyAttentionAdapter(Context context, List<BrandBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_brandlist, null);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {
//        setRelativeLayoutParams(holder.sdv);
        holder.sdv.setImageURI(Uri.parse(list.get(position).getLogo()));
        holder.text1.setText(list.get(position).getName());
        holder.text2.setText(list.get(position).getSub_name());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(context, "lookMyAttentionDetailBrand");
                Intent intent = new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        SimpleDraweeView sdv;
        TextView text1, text2;
        RelativeLayout view;

        public ViewHodler(View itemView) {
            super(itemView);
            sdv = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            view = (RelativeLayout) itemView.findViewById(R.id.view);
        }
    }

    private void setRelativeLayoutParams(SimpleDraweeView view) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int w = (int) (width * 0.5);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = w;
        params.width = w;
        view.setLayoutParams(params);
    }

    public void updateData(List<BrandBean> data) {
        this.list = data;
        notifyDataSetChanged();
    }

}
