package enjoytouch.com.redstar.selfview.showimage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;


/**
 * 图片查看器（直接跳到这个页面，给两个参数，一个事字符串集合，
 *           另一个是当前的位置，即点击的位置，
 *           标识用我这个类里面的公开的自负串字段，EXTRA_IMAGE_INDEX，EXTRA_IMAGE_URLS
 *           ）
 */
public class ImagePagerActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index"; 
	public static final String EXTRA_IMAGE_URLS = "image_urls";

	private HackyViewPager mPager;
	private int pagerPosition;
	//private TextView indicator;
	private LinearLayout ll_dot;
	// 放圆点的View的list
	private List<View> dotViewsList;

	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_pager);

		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

		mPager = (HackyViewPager) findViewById(R.id.pager);
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
		//indicator = (TextView) findViewById(R.id.indicator);
		//----------------------------------------------
		dotViewsList=new ArrayList<View>();
		// 设置圆点的大小，可根据需要设置圆点大小与间距
		int y = 11;
		for (int i = 0; i < urls.size(); i++) {
			View dotView = new View(this);
			LinearLayout dotarea = (LinearLayout)findViewById(R.id.ll_dot);
			if (urls.size() > 0) {
				dotarea.setGravity(Gravity.CENTER_HORIZONTAL);
			}

			if (i > 0) {

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						y, y);
				params.setMargins(9, 0, 0, 0);
				dotView.setLayoutParams(params);
				dotView.setBackgroundResource(R.drawable.white_round2);
			} else {
				dotView.setLayoutParams(new ViewGroup.LayoutParams(y, y));
				dotView.setBackgroundResource(R.drawable.white_round1);
			}
			dotarea.addView(dotView);
			dotViewsList.add(dotView);
		}
		//----------------------------------------------------

//		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
//		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				// currentItem = pos;
				int size = dotViewsList.size();
				int curPos = arg0 % size;
				for (int i = 0; i < size; i++) {
					if (i == curPos) {
						((View) dotViewsList.get(i))
								.setBackgroundResource(R.drawable.white_round1);
					} else {
						((View) dotViewsList.get(i))
								.setBackgroundResource(R.drawable.white_round2);
					}
				}
				//-------------------------------------------------------
//				CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
//				indicator.setText(text);
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public ArrayList<String> fileList;

		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return ImageDetailFragment.newInstance(url);
		}

	}
}
