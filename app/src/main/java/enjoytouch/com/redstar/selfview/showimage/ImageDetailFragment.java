package enjoytouch.com.redstar.selfview.showimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.selfview.showimage.photoview.PhotoViewAttacher;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.MenuDialogUtils;


/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	private Activity context;

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=getActivity();
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);

		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});

		progressBar = (ProgressBar) v.findViewById(R.id.loading);

		mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

					new MenuDialogUtils(getActivity(), R.style.registDialog, R.layout.menu_save, "", new MenuDialogUtils.ButtonClickListener() {
						@Override
						public void onButtonClick(int i) {
							ExclusiveYeUtils.saveImage(mImageUrl);
							ContentUtil.makeToast(getActivity().getApplicationContext(), "保存成功");
						}
					}).show();

				return false;
			}
		});
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ImageLoader.getInstance().displayImage(mImageUrl.contains("http")?mImageUrl:
				"file://" +mImageUrl, mImageView, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "下载错误";
						break;
					case DECODING_ERROR:
						message = "图片无法显示";
						break;
					case NETWORK_DENIED:
						message = "网络有问题，无法下载";
						break;
					case OUT_OF_MEMORY:
						message = "图片太大无法显示";
						break;
					case UNKNOWN:
						message = "未知的错误";
						break;
				}
				Toast.makeText(context, message!=null?message:"", Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressBar.setVisibility(View.GONE);
				mAttacher.update();
			}
		});
	}

//	public class PopupWindows extends PopupWindow
//	{
//
//		public PopupWindows(Context mContext, View parent)
//		{
//
//			View view = View.inflate(mContext, R.layout.item_popupwindow_save, null);
//			view.startAnimation(AnimationUtils.loadAnimation(mContext,
//					R.anim.fade_ins));
//			LinearLayout ll_popup = (LinearLayout) view
//					.findViewById(R.id.ll_popup);
//			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
//					R.anim.push_bottom_in_2));
//
//			setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//			setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//			setFocusable(true);
//			setOutsideTouchable(true);
//			setContentView(view);
//			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//			update();
//
//			Button bt1 = (Button) view
//					.findViewById(R.id.item_popupwindows_save);
//
//			Button bt2 = (Button) view
//					.findViewById(R.id.item_popupwindows_cancel);
//			bt1.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View v)
//				{
//					ExclusiveYeUtils.saveImage(mImageUrl);
//					ContentUtil.makeToast(getActivity().getApplicationContext(),"保存成功");
//					dismiss();
//				}
//			});
//			bt2.setOnClickListener(new View.OnClickListener()
//			{
//				public void onClick(View v)
//				{
//					dismiss();
//				}
//			});
//
//		}
//	}



}
