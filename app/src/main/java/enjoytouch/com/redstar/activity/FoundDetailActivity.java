package enjoytouch.com.redstar.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CollectBean;
import enjoytouch.com.redstar.bean.FundDatilBean;
import enjoytouch.com.redstar.selfview.ImagePage.AdverInfo;
import enjoytouch.com.redstar.selfview.observableScrollViewo.ObservableScrollView;
import enjoytouch.com.redstar.selfview.observableScrollViewo.OnScrollChangedCallback;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.CarouselUtils;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.ShareUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoundDetailActivity extends FragmentActivity {
    private FoundDetailActivity INSTANCE;
    private String id;
    private FundDatilBean list;
    private ShapeLoadingDialog dialog;
    private List<AdverInfo> list_carouse;
    private List<String>list_img;
    @InjectView(R.id.viewpager)         //轮播
    LinearLayout vp;
    @InjectView(R.id.details_rl)
    RelativeLayout details_rl;
    @InjectView(R.id.sv)
    ObservableScrollView sv;
    @InjectView(R.id.share)
    ImageView share;
    @InjectView(R.id.zan)
    ImageView zan;
    @InjectView(R.id.home)
    RelativeLayout line;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.name2)
    TextView name2;
    @InjectView(R.id.content)
    TextView content;
    @InjectView(R.id.rl_zhankai)
    RelativeLayout rl_zhankai;
    @InjectView(R.id.zhankai)
    LinearLayout zhankai;
    @InjectView(R.id.shou)
    LinearLayout shou;
    @InjectView(R.id.collect_cnt)
    TextView collect_cnt;       //收藏数量
    @InjectView(R.id.designers)
    LinearLayout designers;     //整个设计师模块
    @InjectView(R.id.designers1)
    LinearLayout designers1;    //第一个设计师
    @InjectView(R.id.designers_snap)
    SimpleDraweeView designers_snap;
    @InjectView(R.id.designers_name)
    TextView designers_name;
    @InjectView(R.id.designers2)
    LinearLayout designers2;    //第二个设计师
    @InjectView(R.id.designers_snap2)
    SimpleDraweeView designers_snap2;
    @InjectView(R.id.designers_name2)
    TextView designers_name2;
    @InjectView(R.id.brand)
    LinearLayout brand;         //商品品牌
    @InjectView(R.id.brand_logo)
    SimpleDraweeView logo;      //品牌logo
    @InjectView(R.id.brand_name)
    TextView brand_name;        //品牌名字
    @InjectView(R.id.brand_desc)
    TextView brand_desc;        //品牌描述
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.ll_brand)
    LinearLayout ll_brand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_detail);
        INSTANCE=this;
        ButterKnife.inject(this);
        id=getIntent().getStringExtra("id");
        setViews();
    }

    private void setViews() {
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        setData(true);
    }
    private void setViews2(){
        list_carouse=new ArrayList<>();
        list_img=new ArrayList<>();
        for(int i=0;i<list.getData().getPic().size();i++){
            AdverInfo adverInfo=new AdverInfo();
            adverInfo.url=list.getData().getPic().get(i).getImage();
            list_carouse.add(adverInfo);
            list_img.add(list.getData().getPic().get(i).getImage());
        }
        //轮播参数（vp,context,图片数据的集合,null,轮播的高度）
        CarouselUtils.getCarousel("2",vp, INSTANCE, list_carouse, list_img, 1,null);
        name.setText(list.getData().getName());
        name2.setText(list.getData().getSubtitle());
        if (list.getData().getDesc()==null||list.getData().getDesc().equals("")){
            content.setVisibility(View.GONE);
        }else {
            content.setText(list.getData().getDesc());
        }
        ContentUtil.makeLog("lzz","conut:"+content.getLineCount());
        if(content.getLineCount()<=6){          //判断文本是否大于6行
            rl_zhankai.setVisibility(View.GONE);
        }else {
            rl_zhankai.setVisibility(View.VISIBLE);
            content.setLines(6);
            content.setEllipsize(TextUtils.TruncateAt.END);
        }
        if(list.getData().getIs_collect().equals("0")){//未收藏
            zan.setImageResource(R.drawable.shoucang2);
        }else {
            zan.setImageResource(R.drawable.shoucang1);
        }
        collect_cnt.setText(list.getData().getCollect_cnt());
        if(list.getData().getBrand().size()>0){
            logo.setImageURI(Uri.parse(list.getData().getBrand().get(0).getLogo()));
            brand_name.setText(list.getData().getBrand().get(0).getName());
            brand_desc.setText(list.getData().getBrand().get(0).getSub_name());
        }
        if(list.getData().getDesigners().size()>0){
            designers.setVisibility(View.VISIBLE);
            designers_name.setText(list.getData().getDesigners().get(0).getName());
            designers_snap.setImageURI(Uri.parse(list.getData().getDesigners().get(0).getSnap()));
            if(list.getData().getDesigners().size()>1){
                designers2.setVisibility(View.VISIBLE);
                designers_name2.setText(list.getData().getDesigners().get(1).getName());
                designers_snap2.setImageURI(Uri.parse(list.getData().getDesigners().get(1).getSnap()));
            }else {
                designers2.setVisibility(View.GONE);
            }
        }else {
            designers.setVisibility(View.GONE);
        }
        if(list.getData().getBrand().size()>0){
            ll_brand.setVisibility(View.VISIBLE);
        }else {
            ll_brand.setVisibility(View.GONE);
        }
        setListener();
    }
    private void setListener() {
        up_iv.setVisibility(View.GONE);
        sv.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                details_rl.setAlpha(newAlpha);
                ContentUtil.makeLog("正在滑动监听","监听");
                if (t> 700){
                    ContentUtil.makeLog("t大于300","监听");
                    up_iv.setVisibility(View.VISIBLE);
                }else {
                    up_iv.setVisibility(View.GONE);
                }
            }
        });
        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsShareAction");
               // if (AccountUtil.showLoginView(INSTANCE)) return;
                    ShareUtil.share(INSTANCE,line,list.getData().getName(),list.getData().getSubtitle(),"2","http://www.yushang001.com/home/qcshare/qcinfo"+"?"+"id="+id,list.getData().getPic().get(0).getImage());

            }
        });
        zan.setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsCollectAction");
                if (AccountUtil.showLoginView(INSTANCE)) return;
                    collect();
            }
        });
        zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "productDescOpenAndClose");
                content.setSingleLine(false);
                zhankai.setVisibility(View.GONE);
                shou.setVisibility(View.VISIBLE);
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "productDescOpenAndClose");
                zhankai.setVisibility(View.VISIBLE);
                shou.setVisibility(View.GONE);
                content.setLines(6);
                content.setEllipsize(TextUtils.TruncateAt.END);
            }
        });
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsBrandsAction");
                Intent intent=new Intent(INSTANCE,BrandDetailActivity.class);
                intent.putExtra("id",list.getData().getBrand().get(0).getId());
                startActivity(intent);
            }
        });
        designers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsDesignerClicked");
                Intent intent=new Intent(INSTANCE,StylistActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getDesigners().get(0).getId());
                startActivity(intent);
            }
        });
        designers2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "LineProductsDesignerClicked");
                Intent intent=new Intent(INSTANCE,StylistActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getDesigners().get(1).getId());
                startActivity(intent);
            }
        });
        sv.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                details_rl.setAlpha(newAlpha);
            }
        });
    }

    private void setData(final boolean isLoadding) {
        /**
         * 异步请求方式
         */
        dialog.show();
        Call<FundDatilBean> call= HttpServiceClient.getInstance().fund_datil(id,MyApplication.token);
        call.enqueue(new Callback<FundDatilBean>() {
            @Override
            public void onResponse(Call<FundDatilBean> call, Response<FundDatilBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    FundDatilBean userBean = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if(userBean.getStatus().toString().equals("ok")){
                        list=userBean;
                        setViews2();
                    }else {
                        /*Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();*/
                        ContentUtil.makeToast(INSTANCE, userBean.getError().getMessage());
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FundDatilBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                INSTANCE.finish();
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
    private void collect() {
        /**
         * 异步请求方式
         */

        Call<CollectBean> call= HttpServiceClient.getInstance().product_collection(list.getData().getProduct_id(), MyApplication.token);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                String state;
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        if(("1".equals(response.body().getData().getCollect_status()))){
                            state="收藏成功";
                        }else {
                            state="取消收藏";
                        }
                        Dialog d= DialogUtil.show(INSTANCE, state, false);
                        d.show();
                        zan.setImageResource(("1".equals(response.body().getData().getCollect_status()) ? R.drawable.shoucang1 : R.drawable.shoucang2));
                        collect_cnt.setText(response.body().getData().getCollect_cnt());
                    } else {
                        ContentUtil.makeToast(INSTANCE,response.body().getError().getMessage().toString());
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz", String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }

}
