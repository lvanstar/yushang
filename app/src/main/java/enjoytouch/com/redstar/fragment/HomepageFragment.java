package enjoytouch.com.redstar.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BrandSearchInputActivity;
import enjoytouch.com.redstar.activity.FashionActivity;
import enjoytouch.com.redstar.activity.FoundDetailActivity;
import enjoytouch.com.redstar.activity.LocationActivity;
import enjoytouch.com.redstar.activity.MainActivity;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.adapter.HomePageFundAdapter;
import enjoytouch.com.redstar.adapter.HomePageGVAdapter;
import enjoytouch.com.redstar.adapter.HomePageLVAdapter;
import enjoytouch.com.redstar.adapter.HomeStyleAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.selfview.ImagePage.AdverInfo;
import enjoytouch.com.redstar.selfview.PullToRefreshLayout;
import enjoytouch.com.redstar.selfview.observableScrollViewo.ObservableScrollView;
import enjoytouch.com.redstar.selfview.observableScrollViewo.OnScrollChangedCallback;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.CarouselUtils;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.DisplayUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页第一个模块
 * duan
 */
public class HomepageFragment extends Fragment {
    private ShapeLoadingDialog dialog;
    private View view;
    private final static boolean isAutoPlay = true;
    private ScheduledExecutorService scheduledExecutorService;
    private LinearLayout location;
    private RelativeLayout serch;
    public HomePageBean list;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout hot_more;      //设计师推荐更多
    // 是否正在放大
    private Boolean mScaling = false;
    private DisplayMetrics metric;
    private float mFirstPosition = 0;
    private TextView location_text;
    private List<AdverInfo> list_carouse;
    private List<String>list_img;
    private List<HomePageBean.DataBean.BannerBean> bean;
    private HomePageLVAdapter adapter;
    private HomePageGVAdapter adapter2;
    private HomePageFundAdapter adapter3;
    private HomeStyleAdapter styleAdapter;
    @InjectView(R.id.viewpager)         //轮播
    LinearLayout vp;
    @InjectView(R.id.rl1)
    RelativeLayout rl1;
    @InjectView(R.id.rl2)
    RelativeLayout rl2;
    @InjectView(R.id.rl3)
    RelativeLayout rl3;
    @InjectView(R.id.rl4)
    RelativeLayout rl4;
    @InjectView(R.id.details_rl)
    View details_rl;
    @InjectView(R.id.lv)
    ListView lv;
//    @InjectView(R.id.gv)
//    GridView gv;
    @InjectView(R.id.gv3)
    GridView gv3;
    @InjectView(R.id.gv4)
    GridView gv4;
    @InjectView(R.id.homepage_search_rl)
    RelativeLayout search_rl;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.fl)
    FrameLayout fl;
    @InjectView(R.id.ll)
    LinearLayout ll;
    private static ObservableScrollView sv;
    private static PullToRefreshLayout pullToRefreshLayout;
    @InjectView(R.id.hot_more4)
    LinearLayout style_more_ll;
    @InjectView(R.id.home_style_lv)
    ListView style_lv;
    /*@InjectView(R.id.homepage_scrollview)
    ObservableScrollView sv;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout pullToRefreshLayout;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_homepage, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.inject(this,view);
    }
    private void setViews() {
        dialog=new ShapeLoadingDialog(getActivity());
        dialog.setLoadingText(getString(R.string.loaddings));
        sv=(ObservableScrollView)view.findViewById(R.id.homepage_scrollview) ;
        pullToRefreshLayout=(PullToRefreshLayout)view.findViewById(R.id.refresh_view);
        lv.setFocusable(false);
//        gv.setFocusable(false);
        gv3.setFocusable(false);
        gv4.setFocusable(false);
        style_lv.setFocusable(false);
        MyApplication.currDisplay = getActivity().getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
        ContentUtil.makeLog("setViews","正在执行");
        if( MyApplication.b.contains("#")){
        }else {
            MyApplication.b.add("#");
        }
        /*sv = (ScrollView) view.findViewById(R.id.sv);
        sv.smoothScrollTo(0, 20);*/
        hot_more = (LinearLayout) view.findViewById(R.id.hot_more);
        serch = (RelativeLayout) view.findViewById(R.id.discount_search);
        location = (LinearLayout) view.findViewById(R.id.discount_address_rl);//定位
        location_text = (TextView) view.findViewById(R.id.discount_address);    //定位textview
        location_text.setText(MyApplication.cityName);
        setListener();
        setData(true);
    }


    private void setViews2(){       //服务器成功返回数据以后才会调用的方法
        list_carouse=new ArrayList<>();
        list_img=new ArrayList<>();
        for(int i=0;i<list.getData().getBanner().size();i++){
            AdverInfo adverInfo=new AdverInfo();
            adverInfo.url=list.getData().getBanner().get(i).getImage();
            list_carouse.add(adverInfo);
            list_img.add(list.getData().getBanner().get(i).getImage());
        }
        //轮播参数（vp,context,图片数据的集合,null,轮播的高度）
        CarouselUtils.getCarousel("0",vp, getActivity(), list_carouse, list_img, 2,list.getData().getBanner());
        if(list.getData().getDesigner_recommend().size()>0){
            adapter3=new HomePageFundAdapter(getActivity(),list.getData(),1);
            gv3.setAdapter(adapter3);
            setGridViewHeightBasedOnChildren2(gv3);
        }
        if(list.getData().getFind().size()>0){
            adapter3=new HomePageFundAdapter(getActivity(),list.getData(),2);
            gv4.setAdapter(adapter3);
            setGridViewHeightBasedOnChildren2(gv4);
        }
        if(list.getData().getBrand_hall().size()>0){
            adapter=new HomePageLVAdapter(getActivity(),list.getData().getBrand_hall());
            lv.setAdapter(adapter);
            setListViewHeightBasedOnChildren2(lv);
        }
        if(list.getData().getCreative().size()>0){
            styleAdapter=new HomeStyleAdapter(getActivity(),list.getData().getCreative());
            style_lv.setAdapter(styleAdapter);
            setListViewHeightBasedOnChildren2(style_lv);
        }
//        if(list.getData().getStyle().size()>0){
//            adapter2=new HomePageGVAdapter(getActivity(),list.getData().getStyle());
//            gv.setAdapter(adapter2);
//            setGridViewHeightBasedOnChildren(gv);
//        }
        ll.setVisibility(View.VISIBLE);
        fl.setVisibility(View.VISIBLE);
        rl1.setVisibility(View.VISIBLE);
        rl2.setVisibility(View.VISIBLE);
        rl3.setVisibility(View.VISIBLE);
        rl4.setVisibility(View.VISIBLE);
        dialog.dismiss();
    }
    private void setRelativeLayoutParams(View view, int h, int w, int type) {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        if (h > height) {
            if (1 == type) {
                h = (int) (height * 0.8);
            } else {
                h = (int) (height * 0.8) + DisplayUtil.dip2px(getActivity(), 20f);
            }

            ContentUtil.makeLog("lzz", "height:" + h);
        }
        if (w > width) {
            if (1 == type) {
                w = (int) (height * 0.8);
            } else {
                w = (int) (height * 0.8) + DisplayUtil.dip2px(getActivity(), 20f);
            }

            ContentUtil.makeLog("lzz", "width:" + w);
        }
        ContentUtil.makeLog("lzz", "wwww:" + w);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = h;
        params.width = w;
        view.setLayoutParams(params);
    }
    private void setParm(View view){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        int kuan = params.width;
        params.height=kuan;
        view.setLayoutParams(params);
        //view.requestLayout();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setListener() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
                //MobclickAgent.onEvent(getActivity(), "v2_lookCityList");
            }
        });
        serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BrandSearchInputActivity.class);
                startActivity(intent);
                ExclusiveYeUtils.onMyEvent(getActivity(), "searchBrand");
            }
        });
        hot_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMoreProduct");
                MainActivity.vp.setCurrentItem(1, false);
                if (MyApplication.list.contains("1")) {
                    MyApplication.list.remove("1");
                }
                if (MyApplication.list.contains("2")) {
                    MyApplication.list.remove("2");
                }
                if (MyApplication.list.contains("3")) {
                    MyApplication.list.remove("3");
                }
                MyApplication.list.add("1");
            }
        });

        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//                setGone();
                setData(false);
            }


        });

        pullToRefreshLayout.setOnChangeListener(new PullToRefreshLayout.OnChangeListener() {
            @Override
            public void onStarRefresh() {
                setGone();
            }

            @Override
            public void onEndRefresh() {

                setVisible();
            }
        });
        up_iv.setVisibility(View.GONE);
       // up_iv.setImageBitmap(BitmapImageUtil.drawImageDropShadow(getActivity().getApplicationContext(),BitmapImageUtil.drawableToBitmap(getActivity().getResources().getDrawable(R.drawable.up))));
        sv.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                ContentUtil.makeLog("首页正在滑动监听", "监听");
                float newAlpha = (float) t / 500;
                details_rl.setAlpha(newAlpha);
                if (t > 700) {
                    ContentUtil.makeLog("首页t大于300", "监听");
                    up_iv.setVisibility(View.VISIBLE);
                } else {
                    up_iv.setVisibility(View.GONE);
                }
            }
        });
//
        gv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookHomeProductDetail");
                Intent intent=new Intent(getActivity(),ShopDetailsActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getDesigner_recommend().get(position).getId());
                startActivity(intent);
            }
        });
        gv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookLineProductsDetail");
                Intent intent=new Intent(getActivity(),FoundDetailActivity.class);
                intent.putExtra("id",list.getData().getFind().get(position).getId());
                startActivity(intent);
            }
        });

        /**
         * 跳转风格详情
         */
        style_more_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FashionActivity.class);
                startActivity(intent);
            }
        });

        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        setViews();
    }
    // 回弹动画 (使用了属性动画)
    @SuppressLint("NewApi")
    public void replyImage() {
        final ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) vp.getLayoutParams();
        final float w = vp.getLayoutParams().width;// 图片当前宽度
        final float h = vp.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 9 / 16;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.width = (int) (w - (w - newW) * cVal);
                lp.height = (int) (h - (h - newH) * cVal);
                vp.setLayoutParams(lp);
            }
        });
        anim.start();

    }
    //图片放大
    private void showBig(){
        metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        //设置图片初始大小  这里我设为满屏的16:9
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) vp.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 9 / 16;
        vp.setLayoutParams(lp);
        //监听滚动事件
//        sv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) vp.getLayoutParams();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        //手指离开后恢复图片
//                        mScaling = false;
//                        replyImage();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (!mScaling) {
//                            if (sv.getScrollY() == 0) {
//                                mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
//                            } else {
//                                break;
//                            }
//                        }
//                        int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
//                        if (distance < 0) { // 当前位置比记录位置要小，正常返回
//                            break;
//                        }
//
//                        // 处理放大
//                        mScaling = true;
//                        lp.width = metric.widthPixels + distance;
//                        lp.height = (metric.widthPixels + distance) * 9 / 16;
//                        vp.setLayoutParams(lp);
//                        return true; // 返回true表示已经完成触摸事件，不再处理
//                }
//                return false;
//            }
//
//        });
    }
    private void setData(final Boolean isLoadding) {
            /**
             * 异步请求方式
             */
        if(isLoadding) dialog.show();
            Call<HomePageBean> call= HttpServiceClient.getInstance().homepage(MyApplication.cityId);
            ContentUtil.makeLog("city_id",MyApplication.cityId);
            call.enqueue(new Callback<HomePageBean>() {
                @Override
                public void onResponse(Call<HomePageBean> call, Response<HomePageBean> response) {
                   // if(isLoadding) dialog.show();
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    if (response.isSuccessful()) {
                        HomePageBean userBean = response.body();
                        if (userBean.getStatus().toString().equals("ok")) {
                            list = userBean;
                            setViews2();
                        } else {
                            Dialog dialog = DialogUtil.show(getActivity(), userBean.getError().getMessage().toString(), false);
                            dialog.show();
                        }
                    } else {
                        ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<HomePageBean> call, Throwable t) {
                   // if(isLoadding) dialog.show();
                    ContentUtil.makeLog("失败", "失败了");
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            });
    }

    //GridView高度自适应  num=2
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null)return;
        int col = 2;
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight+(listAdapter.getCount()/2-1)*25;
        //((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        gridView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren2(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null)return;
        int col = 2;
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        //((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        gridView.setLayoutParams(params);
    }
    public static void setListViewHeightBasedOnChildren2(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)return;
        int col = 1;
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        //((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

    private void setVisible(){
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(400);
        search_rl.startAnimation(mShowAction);
        search_rl.setVisibility(View.VISIBLE);
    }

    private void setGone(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(400);
        search_rl.startAnimation(mHiddenAction);
        search_rl.setVisibility(View.GONE);
    }
    public static void a(Boolean a){
//        pullToRefreshLayout.requestDisallowInterceptTouchEvent(a);
        if (pullToRefreshLayout==null)return;
        pullToRefreshLayout.setCanPullDown(a);
        pullToRefreshLayout.setCanPullUp(a);
    }
}
