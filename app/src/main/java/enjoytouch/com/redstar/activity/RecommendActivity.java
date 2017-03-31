package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.PushImgBean;
import enjoytouch.com.redstar.bean.ShopTypeBean;
import enjoytouch.com.redstar.bean.ShopTypeBeans;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.selfview.*;
import enjoytouch.com.redstar.selfview.MiddleDialog2;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
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

/**
 * 我要推荐
 */
public class RecommendActivity extends Activity {
    int Maxnum = 140;//限制的最大字数
    int num = 0;
    @InjectView(R.id.recommend_gv)
    GridView recommendGv;
    @InjectView(R.id.pic)
    ImageView pic;
    @InjectView(R.id.text_name)
    EditText textName_Et;
    @InjectView(R.id.address)
    EditText address_Et;
    @InjectView(R.id.content)
    EditText content_Et;
    @InjectView(R.id.tv_save)
    TextView ok_save; //提交
    @InjectView(R.id.text_count)
    TextView text_count;


    private RecommendActivity INSTANCE;
    private List<String> pathList = new ArrayList<>();
    private ShopTypeBean shopTypeBean = new ShopTypeBean();
    private ShapeLoadingDialog dialog;
    private RecommendAdapter adapter;
    private String img = "";
    private String address_name = "";
    private String address = "";
    private String content = "";

    private MiddleDialog middleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.inject(this);
        INSTANCE = this;
        getShopList();

    }

    private void setViews() {
        text_count.setText(num + "/140");
        List<ShopTypeBeans> list = new ArrayList<>();
        for (int i = 0; i < shopTypeBean.getData().size(); i++) {
            ShopTypeBeans beans = new ShopTypeBeans();
            beans.select = false;
            beans.value = shopTypeBean.getData().get(i);
            list.add(beans);
        }
        adapter = new RecommendAdapter(INSTANCE, list);
        recommendGv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(recommendGv);
        adapter.notifyDataSetChanged();

        middleDialog = new MiddleDialog(RecommendActivity.this, "确认要离开吗?\n", "","取消","离开", 0, new MiddleDialog.onButtonCLickListener() {
            @Override
            public void onActivieButtonClick(Object bean, int position) {
                final Dialog dialog = DialogUtil.createLoadingDialog(RecommendActivity.this, getResources().getString(R.string.loading));
                finish();
            }
        }, R.style.registDialog);

        pic.setVisibility(View.VISIBLE);
    }

    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    private void setListeners() {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ShopTypeBeans> list = adapter.getValue();
                if (textName_Et.length() >0 || address_Et.length() >0|| content_Et.length()>0 ||  img.length() >0 || list.size() >0){
                    middleDialog.show();
                }else {
                    finish();
                }


            }
        });

        findViewById(R.id.toselect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
        content_Et.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;

            }

            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                text_count.setText(number + "/140");
                selectionStart = content_Et.getSelectionStart();
                selectionEnd = content_Et.getSelectionEnd();
                if (temp.length() > Maxnum) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    content_Et.setText(s);
                    content_Et.setSelection(tempSelection);//设置光标在最后
                }else if (s.length()==0){
                }
            }
        });

        ok_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                address_name = textName_Et.getText().toString();
                address = address_Et.getText().toString();
                content = content_Et.getText().toString();
                List<ShopTypeBeans> list = adapter.getValue();
                if ("".equals(img)){
                    ContentUtil.makeToast(INSTANCE,"图片上传失败请重新选择图片");
                    pic.setVisibility(View.INVISIBLE);
                    return;
                }
                if (address_name.length() > 0 && address.length() > 0 && content.length() > 0  && list.size()>0) {

                    String type = "";
                    for (int i = 0; i < list.size(); i++) {
                        type += list.get(i).value + ",";
                    }
                    type = type.substring(0, type.length() - 1);
//                    final Dialog dialog = DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
//                    dialog.show();
                    Call<StatusBean> call = HttpServiceClient.getInstance().shop_recommend(MyApplication.token, img, address_name, address, content, type);
                    call.enqueue(new Callback<StatusBean>() {
                        @Override
                        public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
//                            dialog.dismiss();
                            if (response.isSuccessful()) {
                                if ("ok".equals(response.body().getStatus())) {
                                    ExclusiveYeUtils.onMyEvent(INSTANCE, "commitRecommend");
                                  //  ContentUtil.makeToast(INSTANCE, "提交成功");
                                    new enjoytouch.com.redstar.selfview.MiddleDialog2(INSTANCE, "提交成功",
                                            "感谢您的推荐", new MiddleDialog2.onBottonListener() {
                                        @Override
                                        public void onOk() {

                                            finish();
                                        }
                                    },R.style.registDialog).show();
                                } else {
                                    ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                    ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                                }
                            } else {

                                try {
                                    ContentUtil.makeToast(INSTANCE, response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusBean> call, Throwable t) {

//                            dialog.dismiss();
                        }
                    });


                }else {
                    ContentUtil.makeToast(INSTANCE, "您还有内容未填写，请补充！");
                }
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
                        // .crop(370,250,370,250)
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
        ContentUtil.makeLog("正在执行", "onActivityResult方法");
 //----------下面是不裁剪 ---------------
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);//得到选中的图片集合
            //压缩图片
          //  upload(BitmapImageUtil.saveCroppedImage(BitmapImageUtil.getimage(pathList.get(0))));      //上传图片，成功以后再把所有数据传给服务器
           startActivityForResult(new Intent(INSTANCE.getApplicationContext(),ShowOneImageActivity.class)
                                .putExtra(ShowOneImageActivity.IMG_URI,pathList==null?
                                        new ArrayList<String>():pathList.get(0)),33);

        }
 //------------------------------------------------------------------------

 //------------下面是裁剪  要想要裁剪取消注释即可------------------------------
//        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            if (data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0) != null
//                    && !"".equals(data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0))
//                    && BitmapImageUtil.getBitmapByPath(data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0)) != null) {
//                startActivityForResult(new Intent(INSTANCE, ClipImageActivity.class)
//                        .putExtra(ClipImageActivity.IMAGE_URI, data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT).get(0)), 22);
//            } else {
//                DialogUtil.show(INSTANCE, "所选图片为空文件", false).show();
//            }
//        }
//        if (resultCode == 22) {
//            // pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);//得到选中的图片集合
//            //压缩图片
//            // upload(BitmapImageUtil.saveCroppedImage(BitmapImageUtil.getimage(data.getStringExtra(ClipImageActivity.IMAGE_URI))));      //上传图片，成功以后再把所有数据传给服务器
//            upload(data.getStringExtra(ClipImageActivity.IMAGE_URI));
//        }
//------------------------------------------------------------------------------
        if (resultCode==33){
            if (data.getBooleanExtra(ShowOneImageActivity.IS_SAVE,true)) {
                pic.setVisibility(View.VISIBLE);
                Glide.with(INSTANCE)
                        .load(pathList.get(0))
                        .placeholder(R.drawable.b1)
                        .fitCenter()
                        .into(pic);
                upload(pathList.get(0));
            }else {
                pathList.clear();
            }
        }
    }

    //上传图片获得相对路径
    public void upload(final String path) {

        final File old_file = new File(path);
        ContentUtil.makeLog("yc","压缩前大小："+old_file.length()/1024+"KB");
        Compressor.getDefault(INSTANCE)
                .compressToFileAsObservable(old_file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        ContentUtil.makeLog("yc","压缩后大小："+file.length()/1024+"KB");
                        final RequestBody requestBody =
                                RequestBody.create(MediaType.parse("image/jpeg"), file);
                       push(old_file,requestBody);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ContentUtil.makeToast(INSTANCE,"上传失败,请检查网络");
                    }
                });
    }


    private void push(File old_file,RequestBody requestBody){
        Call<PushImgBean> call = HttpServiceClient.getInstance().pushimg2(old_file.getName(), requestBody);
        call.enqueue(new Callback<PushImgBean>() {
            @Override
            public void onResponse(Call<PushImgBean> call, Response<PushImgBean> response) {
                PushImgBean userBean = response.body();
                if (response.isSuccessful() && response.body().status.equals("ok")) {
                    ContentUtil.makeLog("yc", "uri==" + response.body().data.get(0).uri);
                    ContentUtil.makeLog("yc", "url==" + response.body().data.get(0).url);
                    ContentUtil.makeToast(INSTANCE, "上传成功");
                    img = userBean.data.get(0).uri;
                }else {
                    ContentUtil.makeToast(INSTANCE, "上传失败,请检查网络");
                }
            }

            @Override
            public void onFailure(Call<PushImgBean> call, Throwable t) {
                ContentUtil.makeToast(INSTANCE, "上传失败,请检查网络");
            }
        });
    }

    private void getShopList() {
        dialog = new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loading));
        dialog.show();
        Call<ShopTypeBean> call = HttpServiceClient.getInstance().get_shopType();
        call.enqueue(new Callback<ShopTypeBean>() {
            @Override
            public void onResponse(Call<ShopTypeBean> call, Response<ShopTypeBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        shopTypeBean = response.body();
                        setViews();
                        setListeners();
                    } else {

                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                    }
                } else {

                    try {
                        ContentUtil.makeToast(INSTANCE, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopTypeBean> call, Throwable t) {

                dialog.dismiss();
            }
        });
    }

    public class RecommendAdapter extends BaseAdapter {

        private Context context;
        private List<ShopTypeBeans> list;
        private ArrayList<String> values = new ArrayList<>();

        public RecommendAdapter(Context context, List<ShopTypeBeans> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.item_recommend, null);
            final CheckBox box = (CheckBox) convertView.findViewById(R.id.item_recommend_cb);
            box.setText(list.get(position).value);

            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        list.get(position).select = true;
                    } else {

                        list.get(position).select = false;
                    }
                }
            });
            return convertView;
        }

        public List<ShopTypeBeans> getValue() {
            List<ShopTypeBeans> shopTypeBeanses = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).select) {
                    shopTypeBeanses.add(list.get(i));
                }
            }
            return shopTypeBeanses;
        }
    }

}
