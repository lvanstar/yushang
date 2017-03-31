package enjoytouch.com.redstar.selfview.ImagePage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.BrandDetailActivity;
import enjoytouch.com.redstar.activity.BrandHomeList1Activity;
import enjoytouch.com.redstar.activity.BrandHomeList2Activity;
import enjoytouch.com.redstar.activity.BrandIntroductionActivity;
import enjoytouch.com.redstar.activity.QuChuDetailActivity;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.fragment.HomepageFragment;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * 自定义pageview
 * 
 * @author
 * 
 */
public class MyPageView {

	// 自动轮播的时间间隔
	private final static int TIME_INTERVAL = 5;

	// 放轮播图片的ImageView 的list
	private List<SimpleDraweeView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;
	// 服务器获取的数据
	private ArrayList<String> urlList = new ArrayList<String>();
	// private ChildViewPager viewPager;
	private ChildViewPager viewPager;
	private List<AdverInfo> activityDatas = new ArrayList<AdverInfo>();// 活动列表，用于图片轮播
	private List<String> list_str = new ArrayList<String>();// 图片列表，用于图片轮播
	// 当前轮播页
	private int currentItem = 0;
	View view;
	FragmentActivity fragmentActivity;
	private List<HomePageBean.DataBean.BannerBean> bean;
	// SlideShowTask singleTask;
	boolean isUp = true;

	private String type;

	// Handler用于控制没五秒轮播一次
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				if (!isUp) {
					return;
				}
				currentItem = viewPager.getCurrentItem();
				if (currentItem == Integer.MAX_VALUE) {
					currentItem = -1;
				}
				currentItem++;
				viewPager.setCurrentItem(currentItem);
				currentItem = currentItem % imageViewsList.size();
				int size = dotViewsList.size();
				for (int i = 0; i < size; i++) {
					if (i == currentItem) {
						((View) dotViewsList.get(i))
								.setBackgroundResource(R.drawable.white_round1);
					} else {
						((View) dotViewsList.get(i))
								.setBackgroundResource(R.drawable.white_round2);
					}
				}
				handler.sendEmptyMessageDelayed(1, TIME_INTERVAL*1000);

			}
		}
	};

	@SuppressLint("ValidFragment")
	public MyPageView(View view, FragmentActivity fragmentActivity,
					  List<AdverInfo> data, List<String> str, List<HomePageBean.DataBean.BannerBean> bean,
					  String type) {
		this.view = view;
		this.fragmentActivity = fragmentActivity;
		this.type=type;
		if (data != null) {
			this.activityDatas = data;
		} else if (str != null) {
			this.list_str = str;
		}
		this.bean=bean;
		initData();

		initUI(fragmentActivity);
	}

	/**
	 * 初始化相关Data
	 */
	private void initData() {

		imageViewsList = new ArrayList<SimpleDraweeView>();
		dotViewsList = new ArrayList<View>();

	}

	/**
	 * 初始化Views等UI
	 */
	@SuppressLint("ResourceAsColor")
	private void initUI(final Context context) {
		urlList.clear();
		if (activityDatas.size() > 0) {

			for (int i = 0; i < activityDatas.size(); i++) {
				urlList.add(activityDatas.get(i).url);
			}
		} else if (list_str.size() > 0) {

			for (int i = 0; i < list_str.size(); i++) {
				urlList.add(list_str.get(i));
			}
		} else {
			return;
		}

		int size = urlList.size();
		for (int i = 0; i < size; i++) {
			SimpleDraweeView view = new SimpleDraweeView(context);

			/*
			 * 我这边原本加载网络图片用的是imageloader，现在demo直接加载本地图片
			 * ImageLoader.getInstance().displayImage(url, view,
			 * MyApplication.options);
			 */

			view.setImageURI(Uri.parse(urlList.get(i)));

			if (list_str.size() > 0) {
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				view.setLayoutParams(params);
				view.setScaleType(ScaleType.FIT_CENTER);
			} else {
				view.setScaleType(ScaleType.FIT_XY);
			}

			imageViewsList.add(view);
		}

		// 设置圆点的大小，可根据需要设置圆点大小与间距
		int y = 15;
		for (int i = 0; i < size; i++) {
			View dotView = new View(context);
			LinearLayout dotarea = (LinearLayout) view
					.findViewById(R.id.ll_viewarea_dot);
			if (list_str.size() > 0) {
				dotarea.setGravity(Gravity.CENTER_HORIZONTAL);
			}

			if (i > 0) {

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						y, y);
				params.setMargins(9, 0, 0, 0);
				dotView.setLayoutParams(params);
				dotView.setBackgroundResource(R.drawable.white_round2);
			} else {
				dotView.setLayoutParams(new LayoutParams(y, y));
				dotView.setBackgroundResource(R.drawable.white_round1);
			}
			dotarea.addView(dotView);
			dotViewsList.add(dotView);
		}

		viewPager = (ChildViewPager) view.findViewById(R.id.viewPager);

		// 设置滚动效果
		viewPager.setFocusable(true);
		ViewPagerScroller vs = new ViewPagerScroller(context);
		vs.initViewPagerScroll(viewPager);

		viewPager.setAdapter(new MyFragmentPagerAdapter1(fragmentActivity
				.getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		viewPager.setCurrentItem(size * 100);
		/*
		 * viewPager.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { return ; } });
		 */

		viewPager.setOnSingleTouchListener(new ChildViewPager.OnSingleTouchListener() {

			@Override
			public void onTouchUp() {
				setState(true);
			}

			@Override
			public void onTouchDown() {
				// handler.removeCallbacks(singleTask);
				setState(false);
			}

			@Override
			public void onSingleTouch() {
				// TODO 单击事件
				int x = viewPager.getCurrentItem() % (activityDatas.size());
				if(bean==null){
					ExclusiveYeUtils.toShowBigImages(context,urlList,x);
				}else {
					ExclusiveYeUtils.onMyEvent(context, "v3_lookHomeAdver");
					for(int i=0;i<bean.size();i++){
						if(i==x){
							switch (bean.get(i).getType()){
								case "1"://自定义URL
									Intent intent5 = new Intent(context, BrandIntroductionActivity.class);
									intent5.putExtra("brand",bean.get(i).getUrl());
									intent5.putExtra("title","广告详情");
									context.startActivity(intent5);
									break;
								case "4"://品牌
									Intent intent=new Intent(context, BrandDetailActivity.class);
									intent.putExtra("id",bean.get(i).getUrl());
									context.startActivity(intent);
									break;
								case "6"://上品
									Intent intent1=new Intent(context, ShopDetailsActivity.class);
									intent1.putExtra(GlobalConsts.INTENT_DATA,bean.get(i).getUrl());
									context.startActivity(intent1);
									break;
								case "7"://趣处
									Intent intent2=new Intent(context, QuChuDetailActivity.class);
									intent2.putExtra(GlobalConsts.INTENT_DATA,new String[]{bean.get(i).getShop_id(),bean.get(i).getUrl()});
									context.startActivity(intent2);
									break;
								case "8"://趣处
									Intent intent3=new Intent(context, BrandHomeList1Activity.class);
									intent3.putExtra("id",bean.get(i).getUrl());
									context.startActivity(intent3);
									break;
								case "9"://趣处
									Intent intent4=new Intent(context, BrandHomeList2Activity.class);
									intent4.putExtra("id",bean.get(i).getUrl());
									context.startActivity(intent4);
									break;
							}
						}
					}
				}
				//Toast.makeText(fragmentActivity, "当前点击了第"+(x+1)+"条数据", Toast.LENGTH_SHORT).show();
               switch (type){
				   case "0":
					   break;
				   case "1":
					   ExclusiveYeUtils.onMyEvent(context.getApplicationContext(),"lookBrandLargePic");
					   break;
				   case "2":
					   ExclusiveYeUtils.onMyEvent(context.getApplicationContext(),"lookLineProductsAdver");
					   break;
				   case "3":
					   ExclusiveYeUtils.onMyEvent(context.getApplicationContext(),"lookCoverImg");
					   break;
				   case "4":
					  // ExclusiveYeUtils.onMyEvent(context.getApplicationContext(),"");
					   break;

			   }
			}
		});
		/*viewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					HomepageFragment.a(true);
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					HomepageFragment.a(false);
				}
				return true;
			}
		});*/

		// singleTask = new SlideShowTask();
		// handler.postDelayed(singleTask, 5000);

		handler.sendEmptyMessageDelayed(1, 5000);
	}

	private final class MyFragmentPagerAdapter1 extends
			FragmentStatePagerAdapter {

		public MyFragmentPagerAdapter1(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new MyFragment();
			Bundle args = new Bundle();
			args.putInt("arg", position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
	}

	@SuppressLint("ValidFragment")
	public class MyFragment extends Fragment {

		@SuppressLint("ResourceAsColor")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = null;
			try {
				view = inflater.inflate(R.layout.pager_item, container, false);
				SimpleDraweeView mImageView = (SimpleDraweeView) view
						.findViewById(R.id.imageView1);
				Bundle args = getArguments();
				final int resId = args.getInt("arg");
				/*imageview加载网络图片
				 * ImageLoader.getInstance()
						.displayImage(
								Tools.getCompleteUrl(urlList.get(resId
										% urlList.size())), mImageView,
								MyApplication.options);*/
				mImageView.setImageURI(Uri.parse(urlList.get(resId % urlList.size())));
//				//demo加载本地图片
//				if (resId% urlList.size()%2==0) {
//					mImageView.setImageResource(R.mipmap.bikewrite);
//				}else {
//					mImageView.setImageResource(R.mipmap.bikewrite);
//				}
				if (list_str.size() > 0) {
					LayoutParams params = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					view.setLayoutParams(params);
					mImageView.setScaleType(ScaleType.FIT_CENTER);
				} else {
					mImageView.setScaleType(ScaleType.FIT_XY);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}
	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author caizhiming
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if(arg0==0){
				ContentUtil.makeLog("滑动状态","什么都没做");
			}else if(arg0==1){
				HomepageFragment.a(false);
				ContentUtil.makeLog("滑动状态","正在滑动");
			}else if(arg0==2){
				HomepageFragment.a(true);
				ContentUtil.makeLog("滑动状态","滑动结束");
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {//页面在滑动的时候会调用（0是当前滑动的页面
																	//,1是当前页面偏移的百分比，2是当前页面便宜的像素位置
//			ContentUtil.makeLog("当前滑动的页面",String.valueOf(arg0));
//			ContentUtil.makeLog("当前页面偏移的百分比",String.valueOf(arg1));
//			ContentUtil.makeLog("当前页面便宜的像素位置",String.valueOf(arg2));
		}

		@Override
		public void onPageSelected(int pos) {		//此方法是页面跳转完后得到调用，pos是你当前选中的页面的Position(位置编号)
			ContentUtil.makeLog("跳转后当前页面",String.valueOf(pos));
			// currentItem = pos;
			int size = dotViewsList.size();
			int curPos = pos % size;
			for (int i = 0; i < size; i++) {
				if (i == curPos) {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.white_round1);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.white_round2);
				}
			}
		}
	}

	/**
	 * 执行轮播图切换任务
	 * 
	 */
	// private class SlideShowTask implements Runnable {
	//
	// @Override
	// public void run() {
	// synchronized (viewPager) {
	// // currentItem = (currentItem + 1) % imageViewsList.size();
	// handler.obtainMessage().sendToTarget();
	// }
	// }
	//
	// }

	/**
	 * 销毁ImageView资源，回收内存
	 * 
	 * @author caizhiming
	 */
	// private void destoryBitmaps() {
	//
	// for (int i = 0; i < urlList.size(); i++) {
	// ImageView imageView = imageViewsList.get(i);
	// Drawable drawable = imageView.getDrawable();
	// if (drawable != null) {
	// // 解除drawable对view的引用
	// drawable.setCallback(null);
	// }
	// }
	// }

	public void setState(boolean b) {
		isUp = b;
		if (!isUp) {
			handler.removeMessages(1);
		} else {
			handler.sendEmptyMessageDelayed(1, 5000);
		}
	}

}
