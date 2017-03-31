package enjoytouch.com.redstar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

/**
 * Created by 95 on 2015/8/4.
 */
public class PagerFragmentAdapter extends MyMainAdapter{
    private String[] titles;

    public PagerFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list, String[] titles) {
        super(fm, list);
        if(titles.length!=list.size()){
            //throw Exception
        }
        this.titles = titles;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
