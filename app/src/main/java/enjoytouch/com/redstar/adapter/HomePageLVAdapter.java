package enjoytouch.com.redstar.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BrandList2Activity;
import enjoytouch.com.redstar.activity.BrandListActivity;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.util.DisplayUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;

/**
 * Created by duan on 2016/7/11.
 */
public class HomePageLVAdapter extends BaseAdapter{
    private Context context;
    private List<HomePageBean.DataBean.BrandHallBean>list;
    public HomePageLVAdapter(Context context,List<HomePageBean.DataBean.BrandHallBean>list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder myHolder;
        if (view == null) {
            view=View.inflate(context, R.layout.item_homepagelv,null);
            myHolder=new ViewHolder(view);
            view.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) view.getTag();
        }
//        myHolder.alpha.getBackground().setAlpha(77);
//        if(position==0){
//            myHolder.kong.setVisibility(View.GONE);
//        }
//        setRelativeLayoutParams(myHolder.view01);
//        setRelativeLayoutParams(myHolder.view02);
        setRelativeLayoutParams(myHolder.iv10);
        setRelativeLayoutParams(myHolder.iv);
        myHolder.iv10.setImageURI(Uri.parse(list.get(position).getPic()));
        myHolder.iv10_name.setText(list.get(position).getName());
        myHolder.iv10_content.setText(list.get(position).getEnglish_name());
        if(list.get(position).getType().equals("1")){//类别
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExclusiveYeUtils.onMyEvent(context, "lookBrandList");
                    Intent intent=new Intent(context,BrandListActivity.class);
                    intent.putExtra("id",list.get(position).getId());
                    intent.putExtra("parentid",list.get(position).getParentid());
                    context.startActivity(intent);
                }
            });
        }else {                                                         //风格
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExclusiveYeUtils.onMyEvent(context, "lookBrandList");
                    Intent intent=new Intent(context,BrandList2Activity.class);
                    intent.putExtra("id",list.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    public class ViewHolder{
//        TextView kong;
        SimpleDraweeView iv10;
        TextView iv10_name;
        TextView iv10_content;
        RelativeLayout alpha;
        ImageView iv;
//        View view01,view02;
        public ViewHolder(View view) {
//            kong=(TextView)view.findViewById(R.id.kong);
            iv10=(SimpleDraweeView)view.findViewById(R.id.iv10);
            iv10_name=(TextView)view.findViewById(R.id.iv10_name);
            iv10_content=(TextView)view.findViewById(R.id.iv10_content);
            alpha=(RelativeLayout) view.findViewById(R.id.alpha);
            iv= (ImageView) view.findViewById(R.id.homepage_iv01);
//            view01=view.findViewById(R.id.homepage_01);
//            view02=view.findViewById(R.id.homepage_02);
        }
    }
//
    private void setRelativeLayoutParams(View view) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int h = (int) (width * 0.4);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height=h;
        view.setLayoutParams(params);
    }
}
