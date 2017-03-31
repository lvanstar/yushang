package enjoytouch.com.redstar.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.selfview.ImagePage.AdverInfo;
import enjoytouch.com.redstar.selfview.ImagePage.MyPageView;

/**
 * 轮播帮助类
 * Created by Administrator on 2016/4/15.
 */
public class CarouselUtils {
    //这是正常轮播(tpye是哪个页面过来，   0：首页；1：品牌 2：为你发现；3：趣处；4：上品；)
    public static void getCarousel(String type,LinearLayout ll_page_view,
                                   FragmentActivity context, List<AdverInfo> list_activity,
                                   List<String> list_img, int hight, List<HomePageBean.DataBean.BannerBean> bean){
        ll_page_view.removeAllViews();
        //轮播图片宽为屏幕宽度，通知设置宽高比
        int height = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

//        首页banner－5:3
//        商品详情－1:1
//        趣处详情／品牌详情－3:2
        if(hight==0){       //如果传过来的参数是 首页：0   上品详情：1  趣处详情品牌详情 ：2
            height = (int) (width * 3 / 5);
        }else if(hight==1){
            height=(int)width;
        }else if(hight==2){
            height = (int) (width * 2 / 3);
        }
        //此处设置宽高比为7:3
        //height = (int) (width * hight / 7);
        ViewGroup.LayoutParams lp_fullWidth = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height);
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_slideshow, null);
        //插入
        ll_page_view.addView(view, lp_fullWidth);
        MyPageView myPageView=new MyPageView(view, context, list_activity, list_img,bean,type);
    }



}
