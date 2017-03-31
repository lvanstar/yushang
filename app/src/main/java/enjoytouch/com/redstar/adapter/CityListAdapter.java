package enjoytouch.com.redstar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CityBean;
import enjoytouch.com.redstar.selfview.MyLetterListView;
import enjoytouch.com.redstar.util.ContentUtil;


/**
 * Created by enjoytouch-ad02 on 2015/4/1.
 */
public class CityListAdapter extends BaseAdapter {
    private List<CityBean.DataBean> beans;
    private final Context mContext;
    public HashMap<String, Integer> alphaIndexer;
    public String[] sections;
    private Comparator<CityBean.DataBean> comparator = new Comparator<CityBean.DataBean>() {
        @Override
        public int compare(CityBean.DataBean c0, CityBean.DataBean c1) {
            String s0 = c0.getPingyin();
            String s1 = c1.getPingyin();
            ContentUtil.makeLog("正在执行","排序方法");
            int i=0;
            while (true){
                if (s0.length()<=i && s1.length()>i){
                    ContentUtil.makeLog("正在执行","-1");
                    return -1;
                }else if (s0.length()>i && s1.length()<=i){
                    ContentUtil.makeLog("正在执行","1");
                    return 1;
                }else if (s0.length()<=i && s1.length()<=i) {
                    ContentUtil.makeLog("正在执行","0");
                    return 0;
                }else {
                    char a0 = s0.charAt(i);
                    char a1 = s1.charAt(i);
                    if (a0 == a1) {
                        i++;
                        ContentUtil.makeLog("正在执行","继续排序方法");
                        continue;
                    }else {
                        ContentUtil.makeLog("正在执行","返回排序方法");
                        return a0 - a1;
                    }
                }
            }
        }
    };


    public CityListAdapter(Context context, CityBean list) {
        mContext = context;
        beans = list.getData();
        alphaIndexer = new HashMap<String, Integer>();
        Log.i("list:",""+beans.size());
        Collections.sort(beans, comparator);
        sections = new String[beans.size()];
        for (int i = 0; i < beans.size(); i++)
        {
            char current = beans.get(i).getInitial();   //得到所有item的首字母
            char previous = (i - 1) >= 0 ? beans.get(i - 1).getInitial() : 0;//得到前一个item的首字母
            if (current != previous)                    //如果当前首字母不相同于前一个item的首字母
            {
                String name = new String(new char[]{beans.get(i).getInitial()});
                alphaIndexer.put(name, i);      //向键值对中输入
                sections[i] = name;
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(beans, comparator);//字母自动排序（第一个参数是城市的数据，第二个参数是排序的方法）
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null)        //
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.alpha = (TextView) convertView.findViewById(R.id.alpha);     //item的首字母
            holder.name = (TextView) convertView.findViewById(R.id.name);       //城市名
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(beans.get(position).getName());     //设置item的城市名
        char current = beans.get(position).getInitial();        //得到当前item的首字母
        char previous = (position - 1) >= 0 ? beans.get(position - 1).getInitial() : 0;//只要不是第一个item,就取前一个item的首字母
        if (previous != current)//如果前一个item的首字母不是当前的首字母就设置字母，否则不设置字母行
        {
            holder.alpha.setLayoutParams(visible);              //给首字母这一行设置布局参数
            holder.alpha.setText(new String(new char[]{current}));//设置当前字母
            if( MyApplication.b.contains(new String(new char[]{current}))){
            }else {
                 MyApplication.b.add(new String(new char[]{current}));
            }
            ContentUtil.makeLog("适配器中拿到的字母",String.valueOf(MyApplication.b));

        } else
        {
            holder.alpha.setLayoutParams(invisible);        //给首字母这一行设置布局参数
        }
        return convertView;
    }

    private static RelativeLayout.LayoutParams visible = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    private static RelativeLayout.LayoutParams invisible = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);

    class ViewHolder {
        TextView alpha;     //字母
        TextView name;      //城市名
    }

    public char getInitial(String pinyin){
        return (char) (pinyin.charAt(0)-32);
    }


}
