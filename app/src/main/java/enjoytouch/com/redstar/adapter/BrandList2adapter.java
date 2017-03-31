package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.QuChuPhotoBean;

/**
 * Created by Administrator on 2015/12/1.
 */
public class BrandList2adapter extends RecyclerView.Adapter<BrandList2adapter.ViewHolder> {
    private Context context;
    private List<QuChuPhotoBean.DataBean> list;

    public BrandList2adapter(Context c, List<QuChuPhotoBean.DataBean> list){
        context=c;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_brandlist2,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      public ViewHolder(View itemView) {
          super(itemView);
      }
    }
}
