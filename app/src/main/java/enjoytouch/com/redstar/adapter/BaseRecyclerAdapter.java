package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;


/**
 * 配合AutoLoadRecyclerView使用，可以添加多个Header和多个Footer
 * 需注意：添加Header和Footer不能通过直接赋值，而需要在AutoLoadRecyclerView  setAdapter前调用AutoLoadRecyclerView的addFooter(Header)View方法添加
 * 继承该adapter重写getAdapterItemType(position)方法时需注意:返回值不能为负数
 */

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG = getClass().getSimpleName();
    public ArrayList<View> mHeaderViews = null;
    public ArrayList<View> mFooterViews = null;
    public AdapterView.OnItemClickListener mOnItemClickListener;
    public AdapterView.OnItemLongClickListener mOnItemLongClickListener;
    public static final int HEADERS_START = Integer.MIN_VALUE;
    public static final int FOOTERS_START = Integer.MIN_VALUE + 10;


    class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < HEADERS_START + getHeaderCount())
            return new SimpleViewHolder(mHeaderViews.get(viewType - HEADERS_START));
        else if (viewType < FOOTERS_START + getFooterCount())
            return new SimpleViewHolder(mFooterViews.get(viewType - FOOTERS_START));
        else {
            return onCreateItemViewHolder(parent.getContext(), viewType);
        }
    }

    /**
     * 返回adapter中总共的item数目，包括头部和底部
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return getAdapterItemCount() + getHeaderCount() + getFooterCount();
    }


    @Override
    public int getItemViewType(int position) {
        int hCount = getHeaderCount();
        if (position < hCount) return HEADERS_START + position;
        else {
            int itemCount = getAdapterItemCount();
            if (position < hCount + itemCount) {
                return getAdapterItemType(position-hCount);
            } else return FOOTERS_START + position;
        }
    }

    /**
     * 根据位置得到view的类型
     */
    public abstract int getAdapterItemType(int position);

    /**
     * 载入ViewHolder，这里仅仅处理header和footer视图的逻辑
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        int hCount = getHeaderCount();
        if (position >= hCount && position < hCount + getAdapterItemCount()) {
            final int pos =  position - hCount;
            onBindViewHolder(viewHolder, pos, true);
            // 设置点击事件
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(null, viewHolder.itemView, pos, pos);
                    }
                });
            }
            // 设置长按事件
            if (mOnItemLongClickListener != null) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return  mOnItemLongClickListener.onItemLongClick(null, viewHolder.itemView, pos, pos);
                    }
                });
            }
        }
    }


    /**
     * @return The number of header views added
     */
    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    /**
     * @return The number of footer views added
     */
    public int getFooterCount() {
        return mFooterViews.size();
    }

    /**
     * 返回adapter中不包括头和底view的item数目
     *
     * @return The number of items in the bound adapter
     */
    public abstract int getAdapterItemCount();


    /**
     * 得到一个view holder对象
     *
     * @param viewType 视图类型
     */
    protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(Context context, int viewType);


    /**
     * 载入viewHolder
     *
     * @param nothing 无意义的参数
     */
    public abstract void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position, boolean nothing);

    public void setmOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setmOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
/**
 * 爆款首页Adapter（by chenye）
 */
//public class BaseRecyclerAdapter extends RecyclerView.Adapter {
//    private Context context;
//    private List<HotStyleFirstBeen> mDatas;
//
//    public BaseRecyclerAdapter(Context context) {
//        this.context = context;
//    }
//     MyViewHolder  holder;
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        holder = new MyViewHolder(LayoutInflater.from(
//                context).inflate(R.layout.item_hotstyle, parent,
//                false));
//        return holder;
//    }
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.ll_back_goods.setBackgroundResource(mDatas.get(position).getId());
//        holder.tv_day.setText(mDatas.get(position).getDay());
//        holder.title_goods.setText(mDatas.get(position).getTitle());
//        holder.tv_price.setText(mDatas.get(position).getPrice_now());
//        holder.tv_old_price.setText(mDatas.get(position).getPrice_original());
//        holder.kucun.setText(mDatas.get(position).getInventory());
//        holder.zengpin.setText(mDatas.get(position).getGift());
//        holder.zengpinkucun.setText(mDatas.get(position).getGift_inventory());
//    }
//    //    @Override
////    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
////    }
//    @Override
//    public int getItemCount() {
//        return mDatas.size();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout ll_back_goods;
//        TextView tv_day,title_goods,tv_price,tv_old_price,kucun,zengpin,zengpinkucun;
//
//        public MyViewHolder(View view) {
//            super(view);
//            ll_back_goods= (LinearLayout) view.findViewById(R.id.ll_back_goods);
//             tv_day = (TextView) view.findViewById(R.id.tv_day);
//            title_goods = (TextView) view.findViewById(R.id.title_goods);
//            tv_price = (TextView) view.findViewById(R.id.tv_price);
//            tv_old_price = (TextView) view.findViewById(R.id.tv_old_price);
//            kucun = (TextView) view.findViewById(R.id.kucun);
//            zengpin = (TextView) view.findViewById(R.id.zengpin);
//            zengpinkucun = (TextView) view.findViewById(R.id.zengpinkucun);
//        }
//    }
//
//}
