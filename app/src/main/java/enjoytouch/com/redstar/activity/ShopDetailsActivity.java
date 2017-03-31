package enjoytouch.com.redstar.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CollectBean;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.ShopCarBean;
import enjoytouch.com.redstar.bean.ShopDetailsBean;
import enjoytouch.com.redstar.selfview.ImagePage.AdverInfo;
import enjoytouch.com.redstar.selfview.observableScrollViewo.ObservableScrollable;
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

/**
 * 上品详情
 * duan
 */
public class ShopDetailsActivity extends FragmentActivity {
    ShopDetailsBean.DataBean bean = new ShopDetailsBean.DataBean();

    public List<AdverInfo> list_carouse;//图片集合
    private boolean isInit=false;
    private static final String OPEN = "展开";
    private static final String CLOSE = "收起";
    @InjectView(R.id.contentsv)
    TextView contentsv;
    private List<String> list_img;
    private String id;
    public ShopDetailsActivity INSTANCE;
    int s = 0;
//    private int count = 0;
    @InjectView(R.id.text_sale_price)
    TextView textSalePrice;  //价格
    @InjectView(R.id.text_refund_enable)
    TextView textRefundEnable; //不可退
    @InjectView(R.id.text_refund_able)
    TextView textRefundable; //可退
    @InjectView(R.id.text_name)
    TextView textName;
    @InjectView(R.id.text_subtitle)
    TextView textSubtitle;  //副标题

    @InjectView(R.id.iv_shopitem)
    SimpleDraweeView ivShopitem;  //品牌logo
    @InjectView(R.id.text_collect_cnt)
    TextView textCollectCnt;  //收藏数
    @InjectView(R.id.home)
    RelativeLayout line;
    @InjectView(R.id.button1)
    TextView button1;           //加入购物车
    @InjectView(R.id.button2)
    TextView button2;           //立即购买
    @InjectView(R.id.share)
    ImageView share;
    @InjectView(R.id.zan)
    ImageView zan;
    @InjectView(R.id.sigin_name)
    TextView sigin_name;
    @InjectView(R.id.sigin_desc)
    TextView sigin_desc;
    @InjectView(R.id.design_name)
    TextView design_name;
    @InjectView(R.id.design_snap)
    SimpleDraweeView design_snap;
    @InjectView(R.id.design_id)
    LinearLayout design_id;
    @InjectView(R.id.text_notice_sale)
    TextView text_noticesale;
    @InjectView(R.id.shop_details_viewpager)
    LinearLayout vp;
    @InjectView(R.id.shejishi)
    LinearLayout shejishi;
    @InjectView(R.id.shop_details_rl)
    RelativeLayout details_rl;
    @InjectView(R.id.shop_details_os)
    ObservableScrollable scrollable;
    @InjectView(R.id.pinpai)
    LinearLayout pinpai;
    @InjectView(R.id.shangping)
    LinearLayout shangping;
    @InjectView(R.id.design_id_sale)
    LinearLayout design_id_sale;
    @InjectView(R.id.design_name_sale)
    TextView design_name_sale;
    @InjectView(R.id.design_snap_sale)
    SimpleDraweeView design_snap_sale;

    @InjectView(R.id.desc)
    TextView desc;  //内容
    @InjectView(R.id.rl_zhankai)
    RelativeLayout rl_zhankai;
    @InjectView(R.id.ll_detile)
    LinearLayout ll_detile;
    @InjectView(R.id.zhankai)
    LinearLayout zhankai;
    @InjectView(R.id.shou)
    LinearLayout shou;
    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.ll2)
    LinearLayout ll2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        INSTANCE = this;
        ButterKnife.inject(this);
        ExclusiveYeUtils.onMyEvent(INSTANCE,"lookProductDetail");
        id = getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        getDetails(id);

    }


    private void setViews() {
        if(bean.getRemaind()==0){
            ll.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
        }else {
            ll.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
        }
//        count = Integer.parseInt(bean.getCollect_cnt());
        textSalePrice.setText("￥" + bean.getSale_price());  //价格
        textName.setText(bean.getName()); //标题
        textSubtitle.setText(bean.getSubtitle()); //副标题
        textCollectCnt.setText(bean.getCollect_cnt());//收藏数
        if (!"".equals(bean.getContent())){
            contentsv.setText(bean.getContent());
        }else {
            ll_detile.setVisibility(View.GONE);

        }

        if (bean.getBrand().size() > 0) {
            sigin_name.setText(bean.getBrand().get(0).getName());
            sigin_desc.setText(Html.fromHtml(bean.getBrand().get(0).getSub_name()));//getBrand_des
            ivShopitem.setImageURI(Uri.parse(bean.getBrand().get(0).getLogo()));
        } else {
            shangping.setVisibility(View.GONE);
        }
        if (bean.getDesigners().size() > 0) {
            if (bean.getDesigners().size() == 1) {
                design_name.setText(bean.getDesigners().get(0).getName());
                design_snap.setImageURI(Uri.parse(bean.getDesigners().get(0).getSnap()));
                design_id_sale.setVisibility(View.GONE);
                design_id.setVisibility(View.VISIBLE);
            }
            if (bean.getDesigners().size() == 2) {
                design_name.setText(bean.getDesigners().get(0).getName());
                design_snap.setImageURI(Uri.parse(bean.getDesigners().get(0).getSnap()));
                design_name_sale.setText(bean.getDesigners().get(1).getName());
                design_snap_sale.setImageURI(Uri.parse(bean.getDesigners().get(1).getSnap()));
            }

        } else {
            shejishi.setVisibility(View.GONE);
        }

        if ("1".equals(bean.getRefund_enable())) {  //1可退 2不可退
            textRefundable.setVisibility(View.VISIBLE);
            textRefundEnable.setVisibility(View.INVISIBLE);
        } else {
            textRefundEnable.setVisibility(View.VISIBLE);
            textRefundable.setVisibility(View.INVISIBLE);
        }
        zan.setImageResource(("1".equals(bean.getIs_collect()) ? R.drawable.shoucang1 : R.drawable.shoucang2));

        if ("".equals(bean.getDesc())) {
            desc.setVisibility(View.GONE);

        } else {
            desc.setText(bean.getDesc());
        }
        ContentUtil.makeLog("ztt","conut:"+desc.getLineCount());
        if(desc.getLineCount()<=6){          //判断文本是否大于6行
            rl_zhankai.setVisibility(View.GONE);
        }else {
            rl_zhankai.setVisibility(View.VISIBLE);
            desc.setLines(6);
            desc.setEllipsize(TextUtils.TruncateAt.END);
        }

        setViews2();

    }

    private void setViews2() {       //服务器成功返回数据以后才会调用的方法
        list_carouse = new ArrayList<>();
        list_img = new ArrayList<>();
        for (int i = 0; i < bean.getPic().size(); i++) {
            AdverInfo adverInfo = new AdverInfo();
            adverInfo.url = bean.getPic().get(i).getImage();
            list_carouse.add(adverInfo);
            list_img.add(bean.getPic().get(i).getImage());
        }
        //轮播参数（vp,context,图片数据的集合,null,轮播的高度）
        CarouselUtils.getCarousel("4",vp, INSTANCE, list_carouse, list_img, 1,null);
    }


    private void setListener() {

        zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE,"DetailproductOpenAndClose");
                desc.setSingleLine(false);
                zhankai.setVisibility(View.GONE);
                shou.setVisibility(View.VISIBLE);
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE,"DetailproductOpenAndClose");
                zhankai.setVisibility(View.VISIBLE);
                shou.setVisibility(View.GONE);
                desc.setLines(6);
                desc.setEllipsize(TextUtils.TruncateAt.END);
            }
        });        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopDetailsActivity.this.finish();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {//加入购物车
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE,"shoppingCartClicked");
                if (AccountUtil.showLoginView(INSTANCE)) return;
                editShopCar(id, "1");

            }
        });
        //立即购买
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"buyImmediately");
                if (bean.getRemaind()==0){
                    ContentUtil.makeToast(INSTANCE,"库存不足");
                    return;
                }
                final Dialog dialog = DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
                dialog.show();
                Call<ProductBean> call = HttpServiceClient.getInstance().order_confirm(id, "1", MyApplication.cityId, MyApplication.token);
                call.enqueue(new Callback<ProductBean>() {
                    @Override
                    public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if ("ok".equals(response.body().getStatus())) {
                                Intent intent = new Intent(INSTANCE, MakeOrderActivity.class);
                                intent.putExtra(GlobalConsts.INTENT_DATA, response.body().getData());
                                intent.putExtra("data", new String[]{bean.getId(), "1"});
                                intent.putExtra("id",id);
                                startActivity(intent);
                            } else {
                                ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                                ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                            }
                        } else {
                            ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductBean> call, Throwable t) {

                        ContentUtil.makeLog("lzz", "11111");
                        dialog.dismiss();
                    }
                });

            }
        });
        share.setOnClickListener(new View.OnClickListener() { //分享
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE,"productShareAction");
               // if (AccountUtil.showLoginView(INSTANCE)) return;
                ShareUtil.share(INSTANCE, line,bean.getName(), bean.getSubtitle(), "1", "http://www.yushang001.com/home/qcshare/productinfo"+"?"+"id="+id+"&"+"city_id="+MyApplication.cityId, bean.getPic().get(0).getImage());
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                collect();
            }
        });
        design_id.setOnClickListener(new View.OnClickListener() {//跳转设计师详情
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"DesignerClicked");
                Intent intent = new Intent(INSTANCE, StylistActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA, bean.getDesigners().get(0).getId());
                startActivity(intent);
            }
        });
        design_id_sale.setOnClickListener(new View.OnClickListener() {//跳转设计师详情
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"DesignerClicked");
                Intent intent = new Intent(INSTANCE, StylistActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA, bean.getDesigners().get(1).getId());
                startActivity(intent);
            }
        });
        text_noticesale.setOnClickListener(new View.OnClickListener() {//跳转售后
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"lookAfterSalesNotes");
                Intent intent = new Intent(INSTANCE, AfterServiceActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA, bean.getNotice_sale());
                startActivity(intent);
            }
        });

        scrollable.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                details_rl.setAlpha(newAlpha);
            }
        });
        pinpai.setOnClickListener(new View.OnClickListener() {//跳转品牌详情页
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"goBrandDetail");
                Intent intent = new Intent(INSTANCE,BrandDetailActivity.class);
                intent.putExtra("id",bean.getBrand().get(0).getId());
                startActivity(intent);
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

    private void editShopCar(String id, String type) {

        final Dialog dialog = DialogUtil.createLoadingDialog(INSTANCE, getString(R.string.loading));
        dialog.show();
        Call<ShopCarBean> call = HttpServiceClient.getInstance().edit_shopCar(id, type, MyApplication.token, MyApplication.cityId);
        call.enqueue(new Callback<ShopCarBean>() {
            @Override
            public void onResponse(Call<ShopCarBean> call, Response<ShopCarBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        Dialog d = DialogUtil.show(INSTANCE, "成功加入购物车", false);
                        d.show();
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
            public void onFailure(Call<ShopCarBean> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void getDetails(String id) {
        final ShapeLoadingDialog dialog = new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loading));
        dialog.show();
        Call<ShopDetailsBean> call = HttpServiceClient.getInstance().product_dtail(id, MyApplication.token, MyApplication.cityId);
        call.enqueue(new Callback<ShopDetailsBean>() {
            @Override
            public void onResponse(Call<ShopDetailsBean> call, Response<ShopDetailsBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        ContentUtil.makeLog("lzz", "11111");
                        bean = response.body().getData();
                        setViews();
                        setListener();
                    } else {
                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                    }
                } else {
                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                }

            }

            @Override
            public void onFailure(Call<ShopDetailsBean> call, Throwable t) {
                ContentUtil.makeLog("lzz", "2222222");
                dialog.dismiss();
            }
        });
    }

    private void collect() {
        ExclusiveYeUtils.onMyEvent(INSTANCE,"productCollectAction");
        /**
         * 异步请求方式
         */

        Call<CollectBean> call = HttpServiceClient.getInstance().product_collection(bean.getProduct_id(), MyApplication.token);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                String state;
                if (response.isSuccessful()) {
                    ContentUtil.makeLog("ztt", "-----------");
                    if ("ok".equals(response.body().getStatus())) {
                        if(("1".equals(response.body().getData().getCollect_status()))){
                            state="收藏成功";
                        }else {
                            state="取消收藏";
                        }
                        Dialog d= DialogUtil.show(INSTANCE, state, false);
                        d.show();
                        zan.setImageResource(("1".equals(response.body().getData().getCollect_status()) ? R.drawable.shoucang1 : R.drawable.shoucang2));
                        textCollectCnt.setText(response.body().getData().getCollect_cnt());

                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
            }
        });
    }


    /**
     * 判断文字是否超过四行，返回布尔值
     * @param shortTxt
     * @param longTxt
     * @return
     */
    public boolean isShowAll(TextView shortTxt, TextView longTxt) {
       ExclusiveYeUtils.onMyEvent(INSTANCE.getApplicationContext(),"DetailproductOpenAndClose");
        int shortHeight = shortTxt.getHeight();
        int longHeight = longTxt.getHeight();
        if (longHeight > shortHeight) {
            shortTxt.setVisibility(View.VISIBLE);
            longTxt.setVisibility(View.GONE);
            return true;
        } else {
            shortTxt.setVisibility(View.GONE);
            longTxt.setVisibility(View.VISIBLE);
            return false;
        }

    }

}
