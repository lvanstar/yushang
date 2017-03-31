package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.ViewPagerApdater;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.MenuDialogUtils;

/**
 * 查看大图（for chenye）
 */
public class ActivityBigShopImage extends Activity {
    private ImageView point_1, point_2;
   //        R.drawable.pic1, R.drawable.pic1};
   // private GestureDetector gestureDetector;
   // private ViewFlipper viewFlipper;
   private int[] imagesResIds;
    private List<String>imagesResIds2;
    private ViewPager vp;
    private int currentItem  = 0;
    private List<View> dotViewsList;           //点
    private List<View> imageViewsList;      //图片集合
    private Activity mActivity;
    private LinearLayout ll_vpimage;
    //private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_shop_image);
        initData();
        mActivity = this;
        setViews();

    }
    private void initData(){
/*        imagesResIds = new int[]{           //设置图片
                R.drawable.pic_002,
                R.drawable.pic_004,
                R.drawable.pic_002,
                R.drawable.pic_004,
                R.drawable.pic_002,
        };*/
        imagesResIds2 = new ArrayList();
        ContentUtil.makeLog("图片集合",String.valueOf(imagesResIds2.size()));
        imageViewsList = new ArrayList<View>();
        dotViewsList = new ArrayList<View>();
    }
    private void setViews(){                    //初始化数据
        ll_vpimage= (LinearLayout) findViewById(R.id.ll_vpimage);

/*        for(int imageID : imagesResIds2.size()){            //添加五张图片
            ImageView view =  new ImageView(this);  //新建图片对象
            view.setImageResource(imageID);         //给图片对象设置对应位置的图片
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(view);               //像集合中添加对象
        }*/
        for (int imageID=0;imageID<imagesResIds2.size();imageID++){
            SimpleDraweeView view =  new SimpleDraweeView(this);  //新建图片对象
            view.setImageURI(Uri.parse(imagesResIds2.get(imageID)));         //给图片对象设置对应位置的图片
            //view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(view);               //像集合中添加对象
        }
        for (int num = 0;num<imageViewsList.size();num++){      //设置下方小圆点
            ImageView imageView=new ImageView(this);
            if(num==0){
                imageView.setBackgroundResource(R.drawable.dot_white_222);      //给0位置设置白色圆点，其它位置设置暗色圆点
            }else{
                imageView.setBackgroundResource(R.drawable.dot_little_white);
            }


            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(10, 10);     //新建布局参数
            params.setMargins(5,0,0,0);                                                 //设置布局的外间距
            imageView.setLayoutParams(params);                                          //给小圆点设置布局参数
            dotViewsList.add(imageView);                                                //向集合中添加小圆点对象
            ll_vpimage.addView(dotViewsList.get(num));                                  //向线性布局中添加所有小圆点

        }
        vp=(ViewPager)findViewById(R.id.big_viewpager);
        ViewPagerApdater vpadapter = new ViewPagerApdater(imageViewsList);
        vp.setAdapter(vpadapter);
        vp.setFocusable(true);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {           //给viewpage设置滑动监听
            boolean isAutoPlay = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {              //滑动的当前位置
                currentItem = position;
                for (int i = 0; i < dotViewsList.size(); i++) {     //遍历小圆点的集合
                    if (i == position) {                            //如果遍历的位置是当前显示的位置
                        ((View) dotViewsList.get(position)).setBackgroundResource(R.drawable.dot_white_222);
                    } else {
                        ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_little_white);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



}
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}




