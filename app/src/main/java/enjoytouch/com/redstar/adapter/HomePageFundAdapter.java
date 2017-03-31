package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.HomePageBean;

/**
 * Created by duan on 2016/7/11.
 */
public class HomePageFundAdapter extends BaseAdapter{
   private HomePageBean.DataBean list;
    private Context context;
    private int state;       //1是设计师推荐，2是为你发现
    public HomePageFundAdapter(Context context,HomePageBean.DataBean list,int state){
        this.list=list;
        this.context=context;
        this.state=state;
    }
    @Override
    public int getCount() {
        return state==1?list.getDesigner_recommend().size():list.getFind().size();
    }

    @Override
    public Object getItem(int position) {
        return list.getDesigner_recommend().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder myHolder;
        if (view == null) {
            view=View.inflate(context, R.layout.item_homepagefund,null);
            myHolder=new ViewHolder(view);
            view.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) view.getTag();
        }
        if(state==1){
            ViewGroup.LayoutParams params = myHolder.iv.getLayoutParams();
            params.height= MyApplication.currDisplay.getWidth()/2;
            myHolder.iv.setLayoutParams(params);
            myHolder.sdv.setImageURI(Uri.parse(list.getDesigner_recommend().get(position).getCover_img()));
            myHolder.name.setText("  "+list.getDesigner_recommend().get(position).getName()+"");
            myHolder.content.setText(" "+list.getDesigner_recommend().get(position).getSubtitle());
            myHolder.price.setText("￥"+list.getDesigner_recommend().get(position).getSale_price());
        }else {
            ViewGroup.LayoutParams params = myHolder.iv.getLayoutParams();
            params.height= MyApplication.currDisplay.getWidth()/2;
            myHolder.iv.setLayoutParams(params);
            myHolder.sdv.setImageURI(Uri.parse(list.getFind().get(position).getCover_img()));
//            myHolder.name.setText("【"+list.getFind().get(position).getName()+"】");
            myHolder.name.setText(list.getFind().get(position).getName());
            myHolder.content.setText(list.getFind().get(position).getSubtitle());
        }
        return view;
    }

    public class ViewHolder{
        RelativeLayout iv;
        SimpleDraweeView sdv;
        TextView name;
        TextView content;
        TextView price;
        public ViewHolder(View view) {
            iv=(RelativeLayout)view.findViewById(R.id.rl_iv1);
            sdv=(SimpleDraweeView)view.findViewById(R.id.iv1);
            name=(TextView)view.findViewById(R.id.iv1_name);
            content=(TextView)view.findViewById(R.id.iv1_content);
            price=(TextView)view.findViewById(R.id.iv1_price);
        }
    }
}
