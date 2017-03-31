package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.PushImgBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.util.BitmapImageUtil;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.selectSDimage.GlideLoader;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDataActivity extends Activity {

    @InjectView(R.id.pass)
    TextView pass;
    @InjectView(R.id.pic)
    SimpleDraweeView pic;
    @InjectView(R.id.image_nan)
    ImageView imageNan;
    @InjectView(R.id.image_lv)
    ImageView imageLv;
    @InjectView(R.id.editText_nikeName)
    EditText editTextNikeName;
    @InjectView(R.id.login_commit)
    RelativeLayout loginCommit;

    private PersonDataActivity INSTANCE;
    private String user_id;
    private String head_img="";//头像
    private String sex="";
    private String nickname="";
    private List<String> pathList=new ArrayList<>();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        ButterKnife.inject(this);
        INSTANCE = this;
        user_id=getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        setViews();
        setListeners();
    }


    private void setViews(){

    }
    private void setListeners() {//跳过
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {//头像
            @Override
            public void onClick(View v) {
             selectPicture();
            }
        });
        imageNan.setOnClickListener(new View.OnClickListener() {//男
            @Override
            public void onClick(View v) {
                sex="1";
                imageNan.setImageResource(R.drawable.male1);
                imageLv.setImageResource(R.drawable.female2);

            }
        });
        imageLv.setOnClickListener(new View.OnClickListener() {//女
            @Override
            public void onClick(View v) {
                sex="2";
                imageLv.setImageResource(R.drawable.female1);
                imageNan.setImageResource(R.drawable.male2);
            }
        });

        loginCommit.setOnClickListener(new View.OnClickListener() {//提交
            @Override
            public void onClick(View v) {

                nickname=editTextNikeName.getText().toString();

                if(!"".equals(head_img)&&!"".equals(sex)&&!"".equals(nickname)){

                    dialog= DialogUtil.createLoadingDialog(INSTANCE,getString(R.string.loading));
                    dialog.show();
                    Call<StatusBean>call=HttpServiceClient.getInstance().submit_info(user_id,head_img,sex,nickname);
                    call.enqueue(new Callback<StatusBean>() {
                        @Override
                        public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                            dialog.dismiss();
                            if(response.isSuccessful()){

                                if("ok".equals(response.body().getStatus())){
                                    ExclusiveYeUtils.onMyEvent(INSTANCE, "commitPersonInforAction");
                                    finish();
                                }else{
                                    ContentUtil.makeToast(INSTANCE,response.body().getError().getMessage());
                                }
                            }else{
                                ContentUtil.makeToast(INSTANCE,getString(R.string.loadding_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusBean> call, Throwable t) {

                            dialog.dismiss();
                        }
                    });
                }else{
                    ContentUtil.makeToast(INSTANCE,"请填写完整");
                }
            }
        });
    }


    //去相册选择图片
    private void selectPicture(){

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
            pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);//得到选中的图片集合
            //压缩图片
            upload(BitmapImageUtil.saveCroppedImage(BitmapImageUtil.getimage(pathList.get(0))));      //上传图片，成功以后再把所有数据传给服务器
        }
    }


    //上传图片获得相对路径
    public void upload(String path){
        ContentUtil.makeLog("正在执行","upload");
        File file = new File(path);
        final RequestBody requestBody =
                RequestBody.create(MediaType.parse("image/jpeg"), file);
        Call<PushImgBean> call = HttpServiceClient.getInstance().pushimg2(file.getName(), requestBody);
        call.enqueue(new retrofit2.Callback<PushImgBean>() {
            @Override
            public void onResponse(Call<PushImgBean> call, Response<PushImgBean> response) {
                PushImgBean userBean = response.body();
                if (response.isSuccessful() && response.body().status.equals("ok")) {
                    ContentUtil.makeLog("yc", "uri==" + response.body().data.get(0).uri);
                    ContentUtil.makeLog("yc", "url==" + response.body().data.get(0).uri);
                    ContentUtil.makeToast(INSTANCE, "上传成功");
//                    URL = userBean.data.get(0).url;
                     head_img = userBean.data.get(0).uri;
                     pic.setImageURI(Uri.parse(userBean.data.get(0).url));
                }
            }

            @Override
            public void onFailure(Call<PushImgBean> call, Throwable t) {

            }
        });

    }
}
