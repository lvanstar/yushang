package enjoytouch.com.redstar.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.List;
import enjoytouch.com.redstar.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ReleaseAdapter extends BaseAdapter {
    private Context context;
    private List<String> result;

    public ReleaseAdapter(Context context, List<String> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.size()<3?(result.size()+1):result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.size()<3?result.get(position-1):result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return result.size()<3?(position+1):position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.image,null);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.image);
        if (result.size()<3) {
            if (result.size() > 0) {
                if (position == (result.size())) {
                    imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.post_add_pic));
                } else {
                    Glide.with(context).load(result.get(position)).centerCrop().into(imageView);
                }
            } else if (result.size() == 0) {
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.post_add_pic));
            }
        }else {
            Glide.with(context).load(result.get(position)).centerCrop().into(imageView);
        }
        return convertView;
    }


    public void update(List<String> result_1){
        this.result=result_1;
        notifyDataSetChanged();
    }
}
