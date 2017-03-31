package enjoytouch.com.redstar.selfview.photopicker.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.util.BitmapUtils;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.FileUtil;
import enjoytouch.com.redstar.util.MenuDialogUtils;


/**
 * Created by donglua on 15/6/21.
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<String> paths = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    Bitmap bm;
    private Bitmap mBitmap;
    private String mFileName;
    private Thread connectThread;
    private Thread saveThread;

    public PhotoPagerAdapter(Context mContext, List<String> paths) {
        this.mContext = mContext;
        this.paths = paths;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_pager, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);

        final String path = paths.get(position);
        final Uri uri;
        if (path.startsWith("http")) {
            uri = Uri.parse(path);
            ///////////加载图片并获得bitmap
            Glide.with(mContext).load(path).asBitmap().listener(new RequestListener<String, Bitmap>() {
                @Override
                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    bm = resource;
                    return false;
                }
            }).placeholder(R.drawable.def_photo)
                    .error(R.drawable.def_photo).into(imageView);
        } else {
            uri = Uri.fromFile(new File(path));
            Glide.with(mContext)
                    .load(uri)
                            //*.override(800, 800)*//*.placeholder(R.drawable.def_photo)
                    .placeholder(R.drawable.def_photo).error(R.drawable.def_photo)
                    .into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((Activity) mContext).isFinishing()) {
                    ((Activity) mContext).onBackPressed();
                }
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MenuDialogUtils(mContext, R.style.registDialog, R.layout.menu_save, "", new MenuDialogUtils.ButtonClickListener() {
                    @Override
                    public void onButtonClick(int i) {
                        if (i == 0) {
                            if (bm != null) {
                                FileUtil.savePicture(bm);
                            } else {
                                DialogUtil.show(mContext, "图片加载中,无法保存!", false).show();
                            }

                        }
                    }
                }).show();


                return false;
            }
        });

//         imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                initBtnDialog(uri).show();
//                return true;
//            }
//        });
        container.addView(itemView);
        return itemView;
    }


//    public Dialog initBtnDialog(final  Uri uri) {
//        btnDialog = new MutiButtonDialog(mContext, R.style.registDialog, new String[]{mContext.getResources().getString(R.string.save_img)});
//        btnDialog.setButtonClickListener(new MutiButtonDialog.ButtonClickListener() {
//            @Override
//            public void onFirstButtonClick() {
//                saveImg();
//            }
//
//            @Override
//            public void onSecondButtonClick() {
//            }
//        });
//        return btnDialog;
//    }
//
//    private void saveImg() {
//        if(bm!=null) {
//            Date date = new Date();
//            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
//            String filename = format.format(date) + ".jpg";
//            String path = Environment.getExternalStorageDirectory() + "/" + GlobalConsts.SAVE_IMG + "/" + GlobalConsts.SAVE_IMG + filename;
//            ImageUtils.saveImageToGallery(mContext.getApplicationContext(), bm, path);
//            btnDialog.dismiss();
//        }else{
//            ContentUtil.makeTestToast(mContext,mContext.getResources().getString(R.string.save_img_err));
//        }
//    }


    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
