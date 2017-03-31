package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.ContentBean;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;

/**
 * Created by lizhaozhao on 16/6/13.
 */
public class ContentAdapter extends BaseAdapter{

    private Context context;
    private List<ContentBean>list;
    private String type;

    public ContentAdapter(Context context,List<ContentBean>list){
        this.context=context;
        this.list=list;
        this.type=type;
    }

    @Override
    public int getCount() {
            if(list.size()>3){
                return 3;
            }else {
                return list.size();
            }


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null)        //
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_content, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.head.setImageURI(Uri.parse(list.get(position).getHead_img()));
        holder.title.setText(list.get(position).getNickname());
        holder.value.setText(list.get(position).getComment());
        holder.date.setText(list.get(position).getCreated());
        int size=list.get(position).getPic().size();
        if(size>0){
            holder.rl.setVisibility(View.VISIBLE);
            for (int i=0;i<size;i++){
                holder.sivs[i].setVisibility(View.VISIBLE);
                holder.sivs[i].setImageURI(Uri.parse(list.get(position).getPic().get(i)));
                final int finalI = i;
                holder.sivs[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(context, "lookAddressAction");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.get(position).getPic().get(finalI));
                        ExclusiveYeUtils.toShowBigImages(context,urlList,1);
                    }
                });
            }
        }else{
            holder.rl.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder{

        private SimpleDraweeView head;
        private SimpleDraweeView[]sivs;
        private TextView title,value,date;
        private LinearLayout rl;
        public ViewHolder(View itemView) {
            head= (SimpleDraweeView) itemView.findViewById(R.id.content_sv);
            sivs=new SimpleDraweeView[]{(SimpleDraweeView) itemView.findViewById(R.id.content_sv001),(SimpleDraweeView) itemView.findViewById(R.id.content_sv002),
                    (SimpleDraweeView) itemView.findViewById(R.id.content_sv003)};
            title= (TextView) itemView.findViewById(R.id.content_title);
            value= (TextView) itemView.findViewById(R.id.content_value);
            date= (TextView) itemView.findViewById(R.id.content_date);
            rl= (LinearLayout) itemView.findViewById(R.id.content_ll);
        }
    }
}
