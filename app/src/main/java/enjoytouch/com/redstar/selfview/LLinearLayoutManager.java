package enjoytouch.com.redstar.selfview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2016/8/8.
 */
public class LLinearLayoutManager extends LinearLayoutManager {

    private XRecyclerView.Recycler mRecycler;

    public LLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public void onMeasure(XRecyclerView.Recycler recycler, XRecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        mRecycler = recycler;
    }

    public int getScrollY() {
        int scrollY = getPaddingTop();
        int firstVisibleItemPosition = findFirstVisibleItemPosition();

        if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition < getItemCount()) {
            for (int i = 0; i < firstVisibleItemPosition; i++) {
                View view = mRecycler.getViewForPosition(i);
                if (view == null) {
                    continue;
                }
                if (view.getMeasuredHeight() <= 0) {
                    measureChildWithMargins(view, 0, 0);
                }
                XRecyclerView.LayoutParams lp = (XRecyclerView.LayoutParams) view.getLayoutParams();
                scrollY += lp.topMargin;
                scrollY += getDecoratedMeasuredHeight(view);
                scrollY += lp.bottomMargin;
//                 mRecycler.recycleView(view);
            }

            View firstVisibleItem = findViewByPosition(firstVisibleItemPosition);
            XRecyclerView.LayoutParams firstVisibleItemLayoutParams = (XRecyclerView.LayoutParams) firstVisibleItem.getLayoutParams();
            scrollY += firstVisibleItemLayoutParams.topMargin;
            scrollY -= getDecoratedTop(firstVisibleItem);
        }

        return scrollY;
    }
}
