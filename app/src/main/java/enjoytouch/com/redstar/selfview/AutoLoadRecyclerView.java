package enjoytouch.com.redstar.selfview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import enjoytouch.com.redstar.adapter.BaseRecyclerAdapter;
import enjoytouch.com.redstar.callback.LoadFinishCallBack;

//import enjoytouch.com.carcool.util.DisplayUtil;

/**
 * 封装了加载时机的RecyclerView
 *
 * isLoadingMore 标记网络加载状态，
 * 默认为false,正在loading时值变更为true
 * 网络请求完之后需要手动调用loadingFinishCallback的loadfinish回调方法
 * 网络请求之前需要手动调用loadingFinishCallback的loadStart回调方法
 * 添加了HeaderView，适用于需要带顶部视图 的recyclerview
 * 必须配合BaseRecyclerAdapter使用
 */

public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallBack {

	private onLoadMoreListener loadMoreListener;
	private ArrayList<View> mHeaderView = null;
	private ArrayList<View> mFooterView = null;
	protected boolean isLoadingMore;


	public AutoLoadRecyclerView(Context context) {
		this(context, null);
	}

	public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		isLoadingMore = false;
		mHeaderView = new ArrayList<>();
		mFooterView = new ArrayList<>();
		//添加底部加载FooterView
		//setFooterView();
		setOnScrollListener(new AutoLoadScrollListener(true, true));
	}

	@Override
	public void setAdapter(Adapter adapter) {
		super.setAdapter(adapter);
		if (adapter instanceof BaseRecyclerAdapter) {
			((BaseRecyclerAdapter) adapter).mOnItemClickListener = mOnItemClickListener;
			((BaseRecyclerAdapter) adapter).mOnItemLongClickListener = mOnItemLongClickListener;
			((BaseRecyclerAdapter) adapter).mHeaderViews = mHeaderView;
			((BaseRecyclerAdapter) adapter).mFooterViews = mFooterView;
		}
	}


	//针对瀑布流的header和footer的跨列进行设置
//	@Override
//	public void setLayoutManager(final LayoutManager layoutManager) {
//		super.setLayoutManager(layoutManager);
//		if (layoutManager instanceof GridLayoutManager) {
//			GridLayoutManager lm = (GridLayoutManager) layoutManager;
//			lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
//		} else if (layoutManager instanceof ExStaggeredGridLayoutManager) {
//			ExStaggeredGridLayoutManager lm = (ExStaggeredGridLayoutManager) layoutManager;
//			lm.setSpanSizeLookup(new GridSpanSizeLookup(lm.getSpanCount()));
//		}
//	}

	/**
	 * 平滑滚动到某个位置
	 *
	 * @param isAbsolute position是否是绝对的，如果是绝对的，那么header的位置就是0
	 *                   如果是相对的，那么position就是相对内容的list的位置
	 */
	public void smoothScrollToPosition(int position, boolean isAbsolute) {
		if (!isAbsolute && mHeaderView != null&&mHeaderView.size()>0) {
			position+=mHeaderView.size();
		}
		smoothScrollToPosition(position);
	}

	/**
	 * 设置Grid/StaggeredGrid LayoutManager的布局样式
	 */
	private class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

		private int mSpanSize = 1;

		public GridSpanSizeLookup(int spanSize) {
			mSpanSize = spanSize;
		}

		@Override
		public int getSpanSize(int position) {
			BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) getAdapter();
			// 如果是头或底的类型，那么就设置横跨所有列
			if (adapter.getItemViewType(position) == BaseRecyclerAdapter.HEADERS_START + position ||
					adapter.getItemViewType(position) == BaseRecyclerAdapter.FOOTERS_START + position) {
				return mSpanSize;
			}
			return 1;
		}
	}

	public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
	}


	//在确认请求网络加载数据前调用
	@Override
	public void loadStart(){
		isLoadingMore = true;
		//mFooterView.get(0).setVisibility(View.VISIBLE);
	}


	//在加载完之后不管是否成功必须调用
	@Override
	public void loadFinish(Object obj) {
		isLoadingMore = false;
		//mFooterView.get(0).setVisibility(View.GONE);
	}

	public interface onLoadMoreListener {
		void loadMore();

	}


	/**
	 * 滑动自动加载监听器
	 */
	private class AutoLoadScrollListener extends OnScrollListener {

		private final boolean pauseOnScroll;
		private final boolean pauseOnFling;

		public AutoLoadScrollListener( boolean pauseOnScroll, boolean pauseOnFling) {
			super();
			this.pauseOnScroll = pauseOnScroll;
			this.pauseOnFling = pauseOnFling;
		}

		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);

			//由于GridLayoutManager是LinearLayoutManager子类，所以也适用
			if (getLayoutManager() instanceof LinearLayoutManager) {
				int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
				int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();

				//有回调接口，并且不是加载状态，并且剩下2个item，并且向下滑动，则自动加载
				if (loadMoreListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount -
						2 && dy > 0) {
					loadMoreListener.loadMore();
				}
			}
		}

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

				switch (newState) {
					case 0:
						//imageLoader.resume();
						break;
					case 1:
						if (pauseOnScroll) {
						//	imageLoader.pause();
						} else {
						//	imageLoader.resume();
						}
						break;
					case 2:
						if (pauseOnFling) {
						//	imageLoader.pause();
						} else {
						//	imageLoader.resume();
						}
						break;
				}
		}
	}

//	protected boolean overScrollBy(int deltaX, int deltaY,int scrollX, int scrollY,int scrollRangeX,int scrollRangeY,int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){
//		//This is where the magic happens， we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
//		return super.overScrollBy( deltaX,  deltaY, scrollX, scrollY, scrollRangeX,scrollRangeY, maxOverScrollX, DisplayUtil.dip2px(getContext(),30), isTouchEvent);
//	}

	/**
	 * Set the header view of the adapter.
	 */
	public void addHeaderView(View headerView) {
		mHeaderView.add(headerView);
	}


	/**
	 * 设置底部的视图
	 */
	public void addFooterView(View footerView) {
		mFooterView.add(footerView);
	}


	//获取顶部指定位置的视图
	public View getHeaderView(int position){
		if(mHeaderView==null||position>=mHeaderView.size()){
			return null;
		}
		return mHeaderView.get(position);
	}
	/**
	 * 设置item的点击事件
	 */
	public AdapterView.OnItemClickListener mOnItemClickListener = null;

	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	/**
	 * 设置item的长按事件
	 */
	public AdapterView.OnItemLongClickListener mOnItemLongClickListener = null;

	public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
		mOnItemLongClickListener = listener;
	}

	/*private void setFooterView() {
		Button footerBtn = new Button(this.getContext());
		footerBtn.setText("正在加载...");
		footerBtn.getBackground().setAlpha(80);
		addFooterView(footerBtn);
		mFooterView.get(0).setVisibility(View.VISIBLE);
	}*/

}
