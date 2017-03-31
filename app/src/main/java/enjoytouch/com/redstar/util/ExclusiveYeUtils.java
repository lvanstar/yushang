package enjoytouch.com.redstar.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import enjoytouch.com.redstar.activity.LoginActivity;
import enjoytouch.com.redstar.selfview.showimage.ImagePagerActivity;

/**
 * Created by ye on 2016/4/19.
 */
public class ExclusiveYeUtils {

   //判断网络是否连接
   public static boolean isNetworkAvailable(Context context) {
       ConnectivityManager connectivity = (ConnectivityManager) context
               .getSystemService(Context.CONNECTIVITY_SERVICE);
       if (connectivity == null) {
           return false;
       } else {
           NetworkInfo[] info = connectivity.getAllNetworkInfo();
           if (info != null) {
               for (int i = 0; i < info.length; i++) {
                   if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                       NetworkInfo netWorkInfo = info[i];
                       if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                           return true;
                       } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                           return true;
                       }
                   }
               }
           }
       }
       return false;
   }

    //得到图片二进制
    public static byte[] getImage(final String URL_STRING) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] data = null;
                try {
                    //建立URL
                    URL url = new URL(URL_STRING);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);

                    InputStream input = conn.getInputStream();
                    data = readInputStream(input);
                    input.close();

                    System.out.println("下载完毕！");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               // return data;
            }
        }).start();
       return null;
    }
    public static byte[] readInputStream(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return output.toByteArray();
    }


    //得到图片二进制
    public static byte[] path2Bytes(String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapFactory.decodeFile(path).compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    //设置字体风格  eg:"fonts/STXINGKA.TTF"
    public static void setFengGe(Context context,TextView tv,String ttf){
        Typeface fontFace = Typeface.createFromAsset(context.getAssets(),ttf);
        tv.setTypeface(fontFace);
    }

    //获取通知栏高度
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }



//    //SCV头部放大
//    public static void toHeardLarge(FragmentActivity context, final ObservableScrollView scrollView, final LinearLayout img){
//        //SCV头部放大
//        // 记录首次按下位置
//        final float[] mFirstPosition = {0};
//        // 是否正在放大
//        final Boolean[] mScaling = {false};
//        final DisplayMetrics metric;
//
//
//        // 获取屏幕宽高
//        metric = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        //设置图片初始大小  这里我设为满屏的16:9
//        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) img.getLayoutParams();
//        lp.width = metric.widthPixels;
//        lp.height = metric.widthPixels * 15 / 16;
//        img.setLayoutParams(lp);
//
//        //监听滚动事件
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint({ "ClickableViewAccessibility", "NewApi" })
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) img.getLayoutParams();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        //手指离开后恢复图片
//                        mScaling[0] = false;
//                        replyImage(img,metric);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (!mScaling[0]) {
//                            if (scrollView.getScrollY() == 0) {
//                                mFirstPosition[0] = event.getY();// 滚动到顶部时记录位置，否则正常返回
//                            } else {
//                                break;
//                            }
//                        }
//                        int distance = (int) ((event.getY() - mFirstPosition[0]) * 0.6); // 滚动距离乘以一个系数
//                        if (distance < 0) { // 当前位置比记录位置要小，正常返回
//                            break;
//                        }
//
//                        // 处理放大
//                        mScaling[0] = true;
//                        lp.width = metric.widthPixels + distance;
//                        lp.height = (metric.widthPixels + distance) * 15 / 16;
//                        img.setLayoutParams(lp);
//                        return true; // 返回true表示已经完成触摸事件，不再处理
//                }
//                return false;
//            }
//        });
//    }
//    // 回弹动画 (使用了属性动画)
//    @SuppressLint("NewApi")
//    public static void replyImage(final LinearLayout img,DisplayMetrics metric) {
//        final ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) img.getLayoutParams();
//        final float w = img.getLayoutParams().width;// 图片当前宽度
//        final float h = img.getLayoutParams().height;// 图片当前高度
//        final float newW = metric.widthPixels;// 图片原宽度
//        final float newH = metric.widthPixels * 15 / 16;// 图片原高度
//
//        // 设置动画
//        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);
//
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float cVal = (Float) animation.getAnimatedValue();
//                lp.width = (int) (w - (w - newW) * cVal);
//                lp.height = (int) (h - (h - newH) * cVal);
//                img.setLayoutParams(lp);
//            }
//        });
//        anim.start();
//    }

    //保存图片
    public static void saveImage(final String url_){
        //开启子线程
        new Thread(){
            public void run() {
                try {

                    URL url = new URL(url_);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
                    if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
                    InputStream inSream = conn.getInputStream();
                    //把图片保存到项目的根目录
                    readAsFile(inSream, new File(Environment.getExternalStorageDirectory()+"/"+ BitmapImageUtil.getTempFileName()+".jpg"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public static void readAsFile(InputStream inSream, File file) throws Exception{
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inSream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inSream.close();
    }


    //跳到大图查看页
    public static void toShowBigImages(Context context, ArrayList<String> uils, int nowIndex){
        context.startActivity(new Intent(context, ImagePagerActivity.class)
                     .putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,uils)
                      .putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,nowIndex));
    }


  public static void onMyEvent(Context context,String eventID){
      MobclickAgent.onEvent(context, eventID);
  }


    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    //判断是否被挤掉
    public static void isExtrude(Activity context, String code){
        if ("1012".equals(code)){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            context.finish();
        }
    }
}
