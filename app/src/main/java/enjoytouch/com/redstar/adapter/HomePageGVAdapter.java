package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BrandList2Activity;
import enjoytouch.com.redstar.activity.BrandListActivity;
import enjoytouch.com.redstar.activity.FashionActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;

/**
 * Created by duan on 2016/7/11.
 */
public class HomePageGVAdapter extends BaseAdapter{
    private Context context;
    private List<HomePageBean.DataBean.Style2Bean>list;
    public HomePageGVAdapter(Context context, List<HomePageBean.DataBean.Style2Bean>list){
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
            view=View.inflate(context, R.layout.item_homepagegridview,null);
            myHolder=new ViewHolder(view);
            view.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) view.getTag();
        }
        myHolder.alpha.getBackground().setAlpha(77);
        ViewGroup.LayoutParams gv_params1 = myHolder.rl.getLayoutParams();
        gv_params1.height= MyApplication.currDisplay.getWidth()*9/25;
        myHolder.rl.setLayoutParams(gv_params1);
        myHolder.iv11.setImageURI(Uri.parse(list.get(position).getCover_img()));
        myHolder.iv11_name.setText(list.get(position).getStyle_name());
        myHolder.iv11_content.setText(list.get(position).getEnglish_name());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(context, "lookHomeStyleList");
                Intent intent=new Intent(context,FashionActivity.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("name",list.get(position).getStyle_name());
                context.startActivity(intent);
            }
        });
        return view;
    }

    public class ViewHolder{
        SimpleDraweeView iv11;
        TextView iv11_name;
        TextView iv11_content;
        RelativeLayout rl;
        RelativeLayout alpha;
        public ViewHolder(View view) {
            iv11=(SimpleDraweeView)view.findViewById(R.id.iv11);
            iv11_name=(TextView)view.findViewById(R.id.iv11_name);
            iv11_content=(TextView)view.findViewById(R.id.iv11_content);
            rl=(RelativeLayout)view.findViewById(R.id.rl);
            alpha=(RelativeLayout) view.findViewById(R.id.alpha);
        }
    }
}
