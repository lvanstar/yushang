package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.ReleaseAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.PushImgBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.ListUtils;
import enjoytouch.com.redstar.util.selectSDimage.GlideLoader;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReleaseActivity extends Activity {
    private ReleaseActivity INSTANCE;
    private GridView rl_push;
    private ReleaseAdapter adapter;
    RelativeLayout rl_release;
    EditText et_text;
    private TextView text_abc;
    private String value;
    private ArrayList<String> path = new ArrayList<>();
    private ArrayList<String> path_uri = new ArrayList<>();
    private ArrayList<String> path_url = new ArrayList<>();
    private String shop_id;
    private String TYPE="";
    private String fun_place_id="";
    Dialog dialog;
    private MiddleDialog middleDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        INSTANCE = this;


        shop_id=getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        TYPE=getIntent().getStringExtra("type");
        fun_place_id=getIntent().getStringExtra("id");
        setViews();
        setListeners();
    }

    private void setViews() {
       dialog =DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
        rl_push = (GridView) findViewById(R.id.rl_push);
        rl_release = (RelativeLayout) findViewById(R.id.rl_release);//提交
        et_text = (EditText) findViewById(R.id.et_text);
        text_abc= (TextView) findViewById(R.id.text_abc);
        middleDialog = new MiddleDialog(ReleaseActivity.this, "确认要离开吗?\n", "","取消","离开", 0, new MiddleDialog.onButtonCLickListener() {
            @Override
            public void onActivieButtonClick(Object bean, int position) {
                final Dialog dialog = DialogUtil.createLoadingDialog(ReleaseActivity.this, getResources().getString(R.string.loading));
                finish();
            }
        }, R.style.registDialog);

    }

    private void setListeners() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_text.length()>0||path.size()>0){
                    middleDialog.show();
                }else {
                    finish();
                }

            }
        });
        ListUtils.setGridViewHeightBasedOnChildren(rl_push);
        adapter = new ReleaseAdapter(this, path);
        rl_push.setAdapter(adapter);
        rl_push.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //-----老框架--------------
                if (position == getDataSize()) {
                    selectPicture();
                }else {
                    ExclusiveYeUtils.toShowBigImages(INSTANCE,path,position);
                }
                //-------------------------

                //新框架-----------------------
//                if (position == getDataSize()) {
//                    selectPicture();
//                } else {
//                    PhotoPreview.builder()
//                            .setPhotos(path)
//                            .setCurrentItem(position)
//                            .start(INSTANCE);
//                }

                //----------------------------------------
            }
        });

        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                text_abc.setText(s.length()+"/140");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rl_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value=et_text.getText().toString();
                if("".equals(value)){
                    ContentUtil.makeToast(getApplicationContext(), "文字信息不能为空");

                }else if(value.length()<5 ){
                    ContentUtil.makeToast(getApplicationContext(), "再多写一点吧");
                }else if(path.size()==0){
                    comment(shop_id,value,"",TYPE);
                }else if(path.size()>0){
                    upload();
                }

            }
        });

        rl_push.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //-----老框架--------------
                if (position == getDataSize()) {
                    selectPicture();
                }else {
                    showDeleteDialog(position);
                }
                //-------------------------


                return false;
            }
        });

    }
  //去相册选择图片
  private ArrayList<String> path_old = new ArrayList<>();
  private void selectPicture(){
      //------------------新框架--------------
//      path_old.clear();
//      if (path.size()!=0)path_old.addAll(path);
//      PhotoPicker.builder()
//              .setPhotoCount(3)
//              .setShowCamera(true)
//              .setShowGif(true)
//              .setSelected(path)
//              .setPreviewEnabled(false)
//              .start(this, PhotoPicker.REQUEST_CODE);
      //---------------------------------------

      //-----老框架---------------------------------------------------------
    ImageConfig imageConfig
            = new ImageConfig.Builder(new GlideLoader())
            .steepToolBarColor(getResources().getColor(R.color.bg))
            .titleBgColor(getResources().getColor(R.color.bg))
            .titleSubmitTextColor(getResources().getColor(R.color.white))
            .titleTextColor(getResources().getColor(R.color.white))
                    // 开启多选   （默认为多选）
            .mutiSelect()
                    // 多选时的最大数量   （默认 9 张）
            .mutiSelectMaxSize(3)
                    // 开启拍照功能 （默认关闭）
            .showCamera()
                    // 已选择的图片路径
            .pathList(path)
                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
            .filePath("/ImageSelector/Pictures")
            .build();
    ImageSelector.open(ReleaseActivity.this, imageConfig);   // 开启图片选择器
      //-------------------------------------------------------------------
}
    public static final int REQUEST_CODE = 1002;//老框架值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //新框架-------------------------------------------------
//        if (requestCode == PhotoPicker.REQUEST_CODE||requestCode == PhotoPreview.REQUEST_CODE
//                && resultCode == RESULT_OK ) {
//
//            // List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
//            List<String> pathList=new ArrayList<>();
//            if (data != null)
//                pathList= data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)==null?
//                        (data.getStringArrayListExtra("SELECTED_PHOTOS")==null?new ArrayList<String>()
//                                :data.getStringArrayListExtra("SELECTED_PHOTOS"))
//                        :data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//
//            path.clear();
//            path_uri.clear();
//            path_url.clear();
//            if (pathList.size()==0)path.addAll(path_old);
//            path.addAll(pathList);
//            adapter.notifyDataSetChanged();
//        }
        //---------------------------------------------------------------


        //------------老框架------------------------------
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            path.clear();
            path_uri.clear();
            path_url.clear();
            path.addAll(pathList);
            adapter.notifyDataSetChanged();
        }
        //--------------------------------------------------
    }


    //上传图片获得相对路径
    public void upload(){
        final int[] err_num = {0};
        dialog.show();
        final List<RequestBody> data=new ArrayList<>();
        for (int i=0;i<path.size();i++) {
            File file = new File(path.get(i));
            ContentUtil.makeLog("yc","压缩前大小："+file.length()/1024+"KB");
            Compressor.getDefault(INSTANCE)
                    .compressToFileAsObservable(file)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            ContentUtil.makeLog("yc","压缩后大小："+file.length()/1024+"KB");
                            final RequestBody requestBody =
                                    RequestBody.create(MediaType.parse("image/jpeg"), file);
                            data.add(requestBody);
                            if (data.size()==path.size()||
                                    (data.size()+err_num[0])==path.size())push(data);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            err_num[0]++;
                        }
                    });

        }


    }

    private void push(List<RequestBody> data){
        Call<PushImgBean> call = HttpServiceClient.getInstance().pushimg(buData(data,0),buData(data, 1),
                buData(data, 2));
        call.enqueue(new retrofit2.Callback<PushImgBean>() {
            @Override
            public void onResponse(Call<PushImgBean> call, Response<PushImgBean> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().status.equals("ok")) {
                    path_uri.clear();
                    path_url.clear();
                    for (int i=0;i<response.body().data.size();i++) {

                        path_uri.add(response.body().data.get(i).uri);
                        path_url.add(response.body().data.get(i).url);
                    }
                    StringBuffer sb=new StringBuffer();
                    if (path_uri.size()==1){
                        sb.append("[\""+path_uri.get(0)+"\"]");
                    }else {
                        for (int i=0;i<path_uri.size();i++) {
                            if (i==0){
                                sb.append("[\""+path_uri.get(i)+"\",");
                            }else if (i==(path_uri.size()-1)){
                                sb.append("\""+path_uri.get(i)+ "\"]");
                            }else {
                                sb.append("\""+path_uri.get(i)+"\",");
                            }
                        }
                    }
                    ContentUtil.makeLog("lzz", "pic:"+sb.toString());
                    comment(shop_id,value,sb.toString(),TYPE);
                    ContentUtil.makeLog("yc", "path_uri.size()=="+path_uri.size());

                }
            }

            @Override
            public void onFailure(Call<PushImgBean> call, Throwable t) {
                dialog.dismiss();
                ContentUtil.makeLog("lzz", "222222");
            }
        });
    }
    //补全参数
    private RequestBody buData(List<RequestBody> data,int i){
        if (data.size()<(i+1))
            return null;
        return data.get(i);
    }

    private void comment(String shop_id,String comment,String pic,String TYPE){

        dialog.show();
        Call<StatusBean>call;
        if("1".equals(TYPE)){
            call= HttpServiceClient.getInstance().shop_comment(shop_id,fun_place_id,comment,pic, MyApplication.token);
        }else{
            call= HttpServiceClient.getInstance().feel_sub(shop_id, comment, pic, MyApplication.token);
        }
        call.enqueue(new Callback<StatusBean>() {
            @Override
            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus().toString().equals("ok")){
                        ContentUtil.makeToast(INSTANCE,"提交成功");
                        finish();
                    }else {
                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage().toString());
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<StatusBean> call, Throwable t) {

                dialog.dismiss();
            }
        });
    }



    private int getDataSize()
    {
        return path == null ? 0 : path.size();
    }



    private void showDeleteDialog(final int id) {
        new MiddleDialog(INSTANCE, "是否确定删除", "","取消","确定", 0, new MiddleDialog.onButtonCLickListener() {
            @Override
            public void onActivieButtonClick(Object bean, int position) {
                deleteImage(id);
            }
        }, R.style.registDialog).show();

    }

    private void deleteImage( int id){
        path.remove(id);
        adapter.update(path);
    }
}
