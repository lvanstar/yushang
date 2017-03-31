package enjoytouch.com.redstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.BrandDtailBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DisplayUtil;

/**
 * Created by duan on 2016/7/1.
 */
public class BrandDetailHiGhestAdapter extends BaseAdapter{
    public int state=0;   //0不显示全部,1显示全部
    public Context context;
    public List<BrandDtailBean.DataBean.ProductBean>list;
    public BrandDetailHiGhestAdapter(Context context,List<BrandDtailBean.DataBean.ProductBean>list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return state==0?4:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ContentUtil.makeLog("item的数量",String.valueOf(list.size()));
        MyHolder myHolder;
        if (view==null){
            view = View.inflate(context, R.layout.item_brandteail_gv, null);
            myHolder=new MyHolder(view);
            view.setTag(myHolder);
        }else{
            myHolder = (MyHolder) view.getTag();
        }
        setRelativeLayoutParams(myHolder.sdv);
        myHolder.sdv.setImageURI(Uri.parse(list.get(position).getCover_img()));
        myHolder.name.setText(list.get(position).getName());
        myHolder.title.setText(list.get(position).getSubtitle());
        if(list.get(position).getType().equals("3")){
            myHolder.price.setText("仅线下购买");
            myHolder.price.setTextColor(Color.parseColor("#cecece"));
        }else {
            myHolder.price.setText("￥"+list.get(position).getSale_price());
        }
        if(list.get(position).getIs_collect().equals("0")){//未收藏
            myHolder.comment_iv.setImageResource(R.drawable.shop01);
            myHolder.number.setTextColor(Color.parseColor("#bababa"));
        }else {                                             //已收藏
            myHolder.comment_iv.setImageResource(R.drawable.shop02);
            myHolder.number.setTextColor(Color.parseColor("#ffb3bc"));
        }
        myHolder.number.setText(list.get(position).getCollect_cnt());
        return view;
    }
    class MyHolder{
        private SimpleDraweeView sdv;
        private TextView name;
        private TextView title;
        private TextView price;
        private TextView number;
        private ImageView comment_iv;
        public MyHolder(View view){
            sdv=(SimpleDraweeView)view.findViewById(R.id.item_shangpin_sv);
            name=(TextView)view.findViewById(R.id.shangpin_title);
            title=(TextView)view.findViewById(R.id.shangpin_values);
            price=(TextView)view.findViewById(R.id.shangpin_price);
            number=(TextView)view.findViewById(R.id.shangpin_number);
            comment_iv=(ImageView)view.findViewById(R.id.item_shang_collect_iv);
        }
    }

    public void updateData(boolean isbolean){
        if(isbolean==true){
            state=1;
            ContentUtil.makeLog("state状态","1");
        } else {
            state=0;
            ContentUtil.makeLog("state状态","0");
        }
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
