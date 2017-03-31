package enjoytouch.com.redstar.util;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;

import enjoytouch.com.redstar.R;


/**
 * Created by Administrator on 2015/8/7.
 * <p/>
 * 分享界面
 */
public class ShareUtil {
    // 微信

    public static PopupWindow popupWindow;
    public static View wechat, weibo, wepyq,qq;
    private static Context context;
    private static String title="分享测试";
    private static String amount;
    private static String type;//类型是“1”是上品，“2”是为你发现详情，“3”是趣处详情
    private static String url="http://www.yushang001.com/home/qcshare/styleinfo";
    private static String content="丰胸的秘密";
    private static String ima="http://www.umeng.com/images/pic/social/chart_1.png";//标题图片
    private static UMImage urlImage;//分享的标题图片

    static HashMap<String,String> map = new HashMap<String,String>();

    public static View share(final Context context, View line,String title,String content,String type,String url,String ima) {
       // if (AccountUtil.showLoginView(context)) return null;
        ShareUtil.context = context;
        ShareUtil.title=title;
        ShareUtil.content=content;
        ShareUtil.type=type;
        ShareUtil.url=url;
        ShareUtil.ima=ima;

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.share, null);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.showAtLocation(line, Gravity.CENTER_VERTICAL, 0, 0);

        final View mainView = view.findViewById(R.id.share_view);
        LinearLayout share_main= (LinearLayout) view.findViewById(R.id.share_main);
        share_main.getBackground().setAlpha(230);
        RelativeLayout share_cancle= (RelativeLayout) view.findViewById(R.id.share_cancel);
        share_cancle.getBackground().setAlpha(230);
        float pix = context.getResources().getDisplayMetrics().density;
        Animation a = new TranslateAnimation(0, 0, (int) (161 * pix + 0.5f), 0);
        a.setDuration(150);
        a.setInterpolator(new LinearInterpolator());
        mainView.startAnimation(a);
        final Animation b = new TranslateAnimation(0, 0, 0, (int) (161 * pix + 0.5f));
        b.setDuration(150);
        b.setInterpolator(new LinearInterpolator());
        b.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainView.startAnimation(b);
            }
        });
        urlImage = new UMImage(context, ima);
        wechat = view.findViewById(R.id.share_wechat);
//        wepyq = view.findViewById(R.id.share_pengyouquan);
        weibo = view.findViewById(R.id.share_sinaweibo);
        wepyq = view.findViewById(R.id.share_pyq);
        qq=view.findViewById(R.id.qq);
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.put("share_type","weichat");
               // MobclickAgent.onEvent(context, "shareCoupon");
                map.clear();
                weChatShare();
                popupWindow.dismiss();
            }
        });

        wepyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("share_type","weipyq");
                //MobclickAgent.onEvent(context,"shareCoupon");
                map.clear();
                weChatfriendShare();
                popupWindow.dismiss();
            }
        });

        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("share_type","weipyq");
                //MobclickAgent.onEvent(context,"shareCoupon");
                map.clear();
                weiboShare();
                popupWindow.dismiss();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("share_type","weipyq");
                //MobclickAgent.onEvent(context,"shareCoupon");
                map.clear();
                qqShare();
                popupWindow.dismiss();
            }
        });
        return view;
    }


    /**
     *  微信朋友圈
     *
     */
    public static void weChatfriendShare() {
      //  if (AccountUtil.showLoginView(context)) return ;
        if(type.equals("1")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                    .withText("寓上设计师推荐|"+title+"_"+content)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else if(type.equals("2")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                    .withText("寓上为你发现|"+title+"_"+content)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else {
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                    .withText("寓上趣处推荐|"+title+"_"+content)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }
    }
    public static void qqShare(){
        if(type.equals("1")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                    .withText(content)
                    .withTitle("寓上设计师推荐|"+title)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else if(type.equals("2")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                    .withText(content)
                    .withTitle( "寓上为你发现|"+title)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else {
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                    .withText(content)
                    .withTitle("寓上趣处推荐|"+title)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }
    }
    /**
     * 微信好友
     */
    public static void weChatShare(){
      //  if (AccountUtil.showLoginView(context)) return ;
        if(type.equals("1")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                    .withText(content)
                    .withTitle("寓上设计师推荐|"+title)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else if(type.equals("2")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                    .withText(content)
                    .withTitle( "寓上为你发现|"+title)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else {
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                    .withText(content)
                    .withTitle("寓上趣处推荐|"+title)
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }
    }
    /**
     * 微博分享
     *
     */
    public static void weiboShare() {
       // if (AccountUtil.showLoginView(context)) return ;
        if(type.equals("1")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                    .withText("分享【寓上设计师推荐】"+title+content+"点击查看:网页链接|下载寓上APP查看更多:网页链接来自@寓上 ")
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else if(type.equals("2")){
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                    .withText("分享【寓上为你发现】"+title+content+"点击查看:网页链接|下载寓上APP查看更多:网页链接来自@寓上 ")
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }else {
            new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                    .withText("分享【寓上趣处推荐】"+title+content+"点击查看:网页链接|下载寓上APP查看更多:网页链接来自@寓上 ")
                    .withTargetUrl(url)
                    .withMedia(urlImage)
                    .share();
        }
    }


    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context," 分享失败", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,platform + " 分享取消", Toast.LENGTH_SHORT).show();
        }
    };
}
