package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.selfview.photopicker.widget.TouchImageView;
import enjoytouch.com.redstar.util.BitmapUtils;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.FileUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.MenuDialogUtils;

public class OneBigImageActivity extends Activity {

    @InjectView(R.id.iv_pager)
    TouchImageView imageView;
    @InjectView(R.id.one_big_coent)
    TextView content;
    Bitmap bm;

    private OneBigImageActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_big_image);
        ButterKnife.inject(this);
        INSTANCE = this;
       // if (bean!=null){
            setViews();
       // }
        setListeners();
    }

//    private void setViews_brand() {
//        //MobclickAgent.onEvent(INSTANCE, "v2_lookHotStyleLargePic");
//       // content.setText(brand.sku_name);
//        final String path = brand.brand_pic.get(0).name;
//        final Uri uri;
//        if (path.startsWith("http")) {
//            uri = Uri.parse(path);
//            ///////////加载图片并获得bitmap
//            Glide.with(INSTANCE).load(path).asBitmap().listener(new RequestListener<String, Bitmap>() {
//                @Override
//                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    bm = resource;
//                    return false;
//                }
//            }).placeholder(R.drawable.def_photo)
//                    .error(R.drawable.def_photo).into(imageView);
//        } else {
//            uri = Uri.fromFile(new File(path));
//            Glide.with(INSTANCE)
//                    .load(uri)
//                            //*.override(800, 800)*//*.placeholder(R.drawable.def_photo)
//                    .placeholder(R.drawable.def_photo).error(R.drawable.def_photo)
//                    .into(imageView);
//        }
//
//    }
    private void setViews() {
        MobclickAgent.onEvent(INSTANCE, "v2_lookHotStyleLargePic");
//        content.setText(bean.sku_name);
//        final String path = bean.image;
//        final Uri uri;
//        if (path.startsWith("http")) {
//            uri = Uri.parse(path);
//            ///////////加载图片并获得bitmap
//            Glide.with(INSTANCE).load(path).asBitmap().listener(new RequestListener<String, Bitmap>() {
//                @Override
//                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    bm = resource;
//                    return false;
//                }
//            }).placeholder(R.drawable.def_photo)
//                    .error(R.drawable.def_photo).into(imageView);
//        } else {
//            uri = Uri.fromFile(new File(path));
//            Glide.with(INSTANCE)
//                    .load(uri)
//                            //*.override(800, 800)*//*.placeholder(R.drawable.def_photo)
//                    .placeholder(R.drawable.def_photo).error(R.drawable.def_photo)
//                    .into(imageView);
//        }

    }


    private void setListeners() {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MenuDialogUtils(INSTANCE, R.style.registDialog, R.layout.menu_save, "", new MenuDialogUtils.ButtonClickListener() {
                    @Override
                    public void onButtonClick(int i) {
                        if (i == 0) {
                            if (bm != null) {
                                FileUtil.savePicture(bm);
                                DialogUtil.show(OneBigImageActivity.this, "图片保存成功", false).show();
                            } else {
                                DialogUtil.show(INSTANCE, "图片加载中,无法保存!", false).show();
                            }
                        }
                    }
                }).show();
                return false;
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
