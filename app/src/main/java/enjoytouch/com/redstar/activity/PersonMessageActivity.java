package enjoytouch.com.redstar.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.PushImgBean;
import enjoytouch.com.redstar.bean.UserUpdateBean;
import enjoytouch.com.redstar.control.LoginControl;
import enjoytouch.com.redstar.selfview.ActionSheetDialog;
import enjoytouch.com.redstar.util.BitmapImageUtil;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.selectSDimage.GlideLoader;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 个人信息页
 */
public class PersonMessageActivity extends Activity {
    private PersonMessageActivity INSTANCE;
    private String head_img="";//头像
    private Boolean state = true;
    private OptionsPickerView pvOptions;
    private ArrayList<String>list;
    @InjectView(R.id.back)
    LinearLayout back;
    @InjectView(R.id.rl1)
    RelativeLayout rl1;
    @InjectView(R.id.rl2)
    RelativeLayout rl2;
    @InjectView(R.id.rl3)
    RelativeLayout rl3;
    @InjectView(R.id.rl4)
    RelativeLayout rl4;
    @InjectView(R.id.pic)//取头像
            SimpleDraweeView pic;
//    @InjectView(R.id.tv_nan)//男
//            TextView nan;
//    @InjectView(R.id.tv_nv)//女
//            TextView nv;
//    @InjectView(R.id.tv_false)//取消
//            TextView tvFalse;
//    @InjectView(R.id.ll_zan)//性别选择
//            RelativeLayout pop;
    @InjectView(R.id.tv_name)//取昵称
            TextView nikename;
    @InjectView(R.id.tv_sex)//取性别
            TextView sex;
    @InjectView(R.id.tv_phone)//取电话号码
            TextView replace;
    private String value = "";
    private List<String> pathList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        ButterKnife.inject(this);
        INSTANCE=this;

        setViews();
        setListener();
    }
    private void setViews() {
        ContentUtil.makeLog("lzz", "mobile:" + MyApplication.mobile);
        if(!"".equals(MyApplication.mobile)){
            replace.setText(MyApplication.mobile.substring(0, 3) + "****" + MyApplication.mobile.substring(7, 11));
        }
        nikename.setText(MyApplication.nickname);
        pic.setImageURI(Uri.parse(MyApplication.head_img));
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        list=new ArrayList<>();
        list.add("男");
        list.add("女");
        pvOptions.setPicker(list);
        pvOptions.setTitle("请选择性别");
        pvOptions.setCyclic(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1);
        switch (MyApplication.sex) {
            case "1":
                sex.setText("男");
                break;
            case "2":
                sex.setText("女");
                break;
            case "0":
                sex.setText("未填写");
                break;
        }
    }
    private void  setListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl1.setOnClickListener(new View.OnClickListener() {//头像
            @Override
            public void onClick(View v) {

//                if(Build.VERSION.SDK_INT>=23){
//                    String[] mPermissionList = new String[]{Manifest.permission.CAMERA};
//                    ActivityCompat.requestPermissions(INSTANCE, mPermissionList, 100);
//                }

                selectPicture();//去相册选择照片
            }
        });
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//昵称
                ExclusiveYeUtils.onMyEvent(INSTANCE, "modifyNickName");
                Intent intent = new Intent(PersonMessageActivity.this, EditNikenName.class);
                intent.putExtra(GlobalConsts.INTENT_DATA, MyApplication.nickname);
                startActivityForResult(intent, 0);
            }
        });
        rl3.setOnClickListener(new View.OnClickListener() {//修改性别
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "modifySex");
//                pvOptions.show();

                new ActionSheetDialog(INSTANCE)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
//                        .addSheetItem("请选择性别", ActionSheetDialog.SheetItemColor.Blue,
//                                new ActionSheetDialog.OnSheetItemClickListener() {
//                                    @Override
//                                    public void onClick(int which) {
//
//                                    }
//
//                                })
                        .addSheetItem("男", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        ExclusiveYeUtils.onMyEvent(INSTANCE, "modifySex");
                                        sex.setText("男");
                                        new LoginControl(INSTANCE).uPData("3", "1");
                                    }

                                })
                        .addSheetItem("女", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        ExclusiveYeUtils.onMyEvent(INSTANCE, "modifySex");
                                        sex.setText("女");
                                        new LoginControl(INSTANCE).uPData("3", "2");
                                    }

                                }).show();

            }
        });
        rl4.setOnClickListener(new View.OnClickListener() {//修改手机号
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PersonMessageActivity.this, ReplacePhoneActivity.class);
                startActivity(intent);
            }
        });

        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                sex.setText(list.get(options1));
                new LoginControl(INSTANCE).uPData("3", ("男".equals(list.get(options1))?"1":"2"));
            }
        });
    }

    //上传图片获得相对路径
    public void upload(String path){
        File file = new File(path);
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/jpeg"), file);
        Call<PushImgBean> call = HttpServiceClient.getInstance().pushimg2(file.getName(), requestBody);
        call.enqueue(new retrofit2.Callback<PushImgBean>() {
            @Override
            public void onResponse(Call<PushImgBean> call, Response<PushImgBean> response) {
                PushImgBean userBean = response.body();
                if (response.isSuccessful() && response.body().status.equals("ok")) {
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "modifyHeadImg");
                    ContentUtil.makeLog("yc", "uri==" + response.body().data.get(0).uri);
                    ContentUtil.makeLog("yc", "url==" + response.body().data.get(0).uri);
                    ContentUtil.makeToast(INSTANCE, "上传成功");
                    head_img = userBean.data.get(0).uri;
                    pic.setImageURI(Uri.parse(userBean.data.get(0).url));
                    new LoginControl(INSTANCE).uPData("1",head_img);
                }
            }

            @Override
            public void onFailure(Call<PushImgBean> call, Throwable t) {

            }
        });

    }


    //去相册选择图片
    private void selectPicture() {

        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.bg))
                .titleBgColor(getResources().getColor(R.color.bg))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                .crop(140,140,140,140)//截图
                        // 开启单选   （默认为多选）
                .singleSelect()
                        // 开启拍照功能 （默认关闭）
                .showCamera()
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        ImageSelector.open(INSTANCE, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentUtil.makeLog("正在执行","个人资料的onActivityResult方法");
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ContentUtil.makeLog("正在执行","得到路径");
            pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);//得到选中的图片集合
            //压缩图片
            upload(BitmapImageUtil.saveCroppedImage(BitmapImageUtil.getimage(pathList.get(0))));      //上传图片，成功以后再把所有数据传给服务器
        }
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:

                break;
            case 2:
                if (data != null) {
                    String nickname = data.getStringExtra(GlobalConsts.INTENT_DATA);
                    nikename.setText(nickname);
                }
                break;
            default:
                break;
        }
    }
}
