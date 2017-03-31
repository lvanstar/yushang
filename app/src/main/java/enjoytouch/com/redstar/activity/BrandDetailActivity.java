package enjoytouch.com.redstar.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.BrandDetailHiGhestAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.BrandDtailBean;
import enjoytouch.com.redstar.bean.CollectBean;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandDetailActivity extends FragmentActivity {
    private String id;
    private BrandDetailActivity INSTANCE;
    private BrandDetailHiGhestAdapter adapter;
    private BrandDtailBean list;
    private ShapeLoadingDialog dialog;
    private List<AdverInfo> list_carouse;
    private List<String>list_img;
    private int postion=1;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.vp)
    LinearLayout vp;
    @InjectView(R.id.sv)
    ObservableScrollView sv;
    @InjectView(R.id.cut)
    TextView cnt;
    @InjectView(R.id.details_rl)
    RelativeLayout details_rl;
    @InjectView(R.id.gv)
    GridView gv;
    @InjectView(R.id.ll_content)
    LinearLayout ll_content;
    @InjectView(R.id.content)           //趣处必读
    TextView content;
    @InjectView(R.id.rl_zhankai)
    RelativeLayout rl_zhankai;
    @InjectView(R.id.zhankai)
    LinearLayout zhankai;
    @InjectView(R.id.shou)
    LinearLayout shou;

    @InjectView(R.id.logo)
    SimpleDraweeView logo;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.zan)
    ImageView zan;
    @InjectView(R.id.text_brand)    //品牌故事
    TextView text_brand;
    @InjectView(R.id.ll_designers)  //设计师整行
    LinearLayout designers;
    @InjectView(R.id.designers1)    //第一个设计师
    LinearLayout designers1;
    @InjectView(R.id.designers1_logo)
    SimpleDraweeView designers1_logo;
    @InjectView(R.id.designers1_name)
    TextView designers1_name;
    @InjectView(R.id.designers2)    //第二个设计师
    LinearLayout designers2;
    @InjectView(R.id.designers2_logo)
    SimpleDraweeView designers2_logo;
    @InjectView(R.id.designers2_name)
    TextView designers2_name;
    @InjectView(R.id.tag)
    LinearLayout tag;
    @InjectView(R.id.tag1)
    TextView tag1;
    @InjectView(R.id.tag2)
    TextView tag2;
    @InjectView(R.id.tag3)
    TextView tag3;
    @InjectView(R.id.comment)
    LinearLayout comment;
    @InjectView(R.id.list1)
    LinearLayout list1;
    @InjectView(R.id.list1_logo)
    SimpleDraweeView list1_logo;
    @InjectView(R.id.name1)
    TextView name1;
    @InjectView(R.id.comment1)
    TextView comment1;
    @InjectView(R.id.list1_image)
    LinearLayout list1_image;
    @InjectView(R.id.list1_sdv1)
    SimpleDraweeView list1_sdv1;
    @InjectView(R.id.list1_sdv2)
    SimpleDraweeView list1_sdv2;
    @InjectView(R.id.list1_sdv3)
    SimpleDraweeView list1_sdv3;

    @InjectView(R.id.list2)
    LinearLayout list2;
    @InjectView(R.id.list2_logo)
    SimpleDraweeView list2_logo;
    @InjectView(R.id.name2)
    TextView name2;
    @InjectView(R.id.comment2)
    TextView comment2;
    @InjectView(R.id.list2_image)
    LinearLayout list2_image;
    @InjectView(R.id.list2_sdv1)
    SimpleDraweeView list2_sdv1;
    @InjectView(R.id.list2_sdv2)
    SimpleDraweeView list2_sdv2;
    @InjectView(R.id.list2_sdv3)
    SimpleDraweeView list2_sdv3;

    @InjectView(R.id.list3)
    LinearLayout list3;
    @InjectView(R.id.list3_logo)
    SimpleDraweeView list3_logo;
    @InjectView(R.id.name3)
    TextView name3;
    @InjectView(R.id.comment3)
    TextView comment3;
    @InjectView(R.id.list3_image)
    LinearLayout list3_image;
    @InjectView(R.id.list3_sdv1)
    SimpleDraweeView list3_sdv1;
    @InjectView(R.id.list3_sdv2)
    SimpleDraweeView list3_sdv2;
    @InjectView(R.id.list3_sdv3)
    SimpleDraweeView list3_sdv3;
    @InjectView(R.id.I_comment)
    TextView I_comment;
    @InjectView(R.id.comment_list)
    TextView comment_list;
    @InjectView(R.id.gv_zhankai)
    RelativeLayout gv_zhankai;
    @InjectView(R.id.zhankai2)
    LinearLayout zhankai2;
    @InjectView(R.id.shou2)
    LinearLayout shou2;
    @InjectView(R.id.shop)
    TextView shop;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.ll_kong)
    LinearLayout ll_kong;
    @InjectView(R.id.ll_shop)
    LinearLayout ll_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
        INSTANCE=this;
        ButterKnife.inject(this);
        id=getIntent().getStringExtra("id");
        setViews();
    }

    private void setViews() {
        gv.setFocusable(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loaddings));
        //setData(true);
    }
    private void setViews2(){
        list1_sdv1.setVisibility(View.GONE);
        list1_sdv2.setVisibility(View.GONE);
        list1_sdv3.setVisibility(View.GONE);
        list2_sdv1.setVisibility(View.GONE);
        list2_sdv2.setVisibility(View.GONE);
        list2_sdv3.setVisibility(View.GONE);
        list3_sdv1.setVisibility(View.GONE);
        list3_sdv2.setVisibility(View.GONE);
        list3_sdv3.setVisibility(View.GONE);
        list_carouse=new ArrayList<>();
        list_img=new ArrayList<>();
        for(int i=0;i<list.getData().getPic().size();i++){
            AdverInfo adverInfo=new AdverInfo();
            adverInfo.url=list.getData().getPic().get(i).getImg();
            list_carouse.add(adverInfo);
            list_img.add(list.getData().getPic().get(i).getImg());
        }
        //轮播参数（vp,context,图片数据的集合,null,轮播的高度）
        CarouselUtils.getCarousel("1",vp, INSTANCE, list_carouse, list_img,2,null);

        if(list.getData().getFollow()==1){  //已关注
            zan.setImageResource(R.drawable.icon2_1);
        }else {                             //未关注
            zan.setImageResource(R.drawable.icon2_6);
        }
        cnt.setText(list.getData().getFollow_cnt());
        if(list.getData().getShop().size()>0){
            ll_kong.setVisibility(View.VISIBLE);
            ll_shop.setVisibility(View.VISIBLE);
        }else {
            ll_kong.setVisibility(View.GONE);
            ll_shop.setVisibility(View.GONE);
        }
        logo.setImageURI(Uri.parse(list.getData().getLogo()));
        name.setText(list.getData().getName());
        title.setText(list.getData().getSub_name());
        if(list.getData().getDesigners().size()>0){
            text_brand.setVisibility(View.VISIBLE);
            designers.setVisibility(View.VISIBLE);
            designers1_logo.setImageURI(Uri.parse(list.getData().getDesigners().get(0).getSnap()));
            designers1_name.setText(list.getData().getDesigners().get(0).getName());
        }
        if(list.getData().getDesigners().size()>1){
            designers2.setVisibility(View.VISIBLE);
            designers1_logo.setImageURI(Uri.parse(list.getData().getDesigners().get(1).getSnap()));
            designers1_name.setText(list.getData().getDesigners().get(1).getName());
        }
        if (list.getData().getBrand_des()==null||list.getData().getBrand_des().equals("")){
            ll_content.setVisibility(View.GONE);
        }else {
            ll_content.setVisibility(View.VISIBLE);
            content.setText(list.getData().getBrand_des());
        }
        ContentUtil.makeLog("lzz","conut:"+content.getLineCount());
        if(content.getLineCount()<=6){          //判断文本是否大于6行
            rl_zhankai.setVisibility(View.GONE);
        }else {
            rl_zhankai.setVisibility(View.VISIBLE);
//            content.setLines(6);
            content.setEllipsize(TextUtils.TruncateAt.END);
        }
        if(list.getData().getTag_desc().size()>0){
            tag.setVisibility(View.VISIBLE);
            tag1.setText(list.getData().getTag_desc().get(0));
        }
        if(list.getData().getTag_desc().size()>1){
            tag2.setVisibility(View.VISIBLE);
            tag2.setText(list.getData().getTag_desc().get(1));
        }
        if(list.getData().getTag_desc().size()>2){
            tag3.setVisibility(View.VISIBLE);
            tag3.setText(list.getData().getTag_desc().get(2));
        }
        setComment();
        setListerner();
        postion=0;
    }
    private void setComment(){
        if(Integer.valueOf(list.getData().getFeel().getTotal())>3){
            comment_list.setText("全部感受");
            comment_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "allFeelingsAction");
                    Intent intent=new Intent(INSTANCE,ContentActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getId());
                    startActivity(intent);
                }
            });

        }else {
            if(list.getData().getFeel().getData().size()==0){
                comment_list.setText("暂无内容");
            }else {
                comment_list.setVisibility(View.INVISIBLE);
            }

        }
        if(list.getData().getFeel().getData().size()>0){
            list1.setVisibility(View.VISIBLE);
            list1_logo.setImageURI(Uri.parse(list.getData().getFeel().getData().get(0).getHead_img()));
            name1.setText(list.getData().getFeel().getData().get(0).getNickname());
            ContentUtil.makeLog("名字的值",list.getData().getFeel().getData().get(0).getNickname());
            comment1.setText(list.getData().getFeel().getData().get(0).getComment());
            if(list.getData().getFeel().getData().get(0).getPic().size()>0){
                ContentUtil.makeLog("正在执行第一个item","图片数量大于0");
                list1_image.setVisibility(View.VISIBLE);
                list1_sdv1.setVisibility(View.VISIBLE);
                list1_sdv1.setImageURI(Uri.parse(list.getData().getFeel().getData().get(0).getPic().get(0)));
                list1_sdv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(0).getPic().get(0));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
            if(list.getData().getFeel().getData().get(0).getPic().size()>1){
                list1_sdv2.setVisibility(View.VISIBLE);
                list1_sdv2.setImageURI(Uri.parse(list.getData().getFeel().getData().get(0).getPic().get(1)));
                list1_sdv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(0).getPic().get(1));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
            if(list.getData().getFeel().getData().get(0).getPic().size()>2){
                list1_sdv3.setVisibility(View.VISIBLE);
                list1_sdv3.setImageURI(Uri.parse(list.getData().getFeel().getData().get(0).getPic().get(2)));
                list1_sdv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(0).getPic().get(2));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
        }
        if(list.getData().getFeel().getData().size()>1){
            list2.setVisibility(View.VISIBLE);
            list2_logo.setImageURI(Uri.parse(list.getData().getFeel().getData().get(1).getHead_img()));
            name2.setText(list.getData().getFeel().getData().get(1).getNickname());
            comment2.setText(list.getData().getFeel().getData().get(1).getComment());
            if(list.getData().getFeel().getData().get(1).getPic().size()>0){
                ContentUtil.makeLog("正在执行第二个item","图片数量大于0");
                list2_image.setVisibility(View.VISIBLE);
                list2_sdv1.setVisibility(View.VISIBLE);
                list2_sdv1.setImageURI(Uri.parse(list.getData().getFeel().getData().get(1).getPic().get(0)));
                list2_sdv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(1).getPic().get(0));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
            if(list.getData().getFeel().getData().get(1).getPic().size()>1){
                list2_sdv2.setVisibility(View.VISIBLE);
                list2_sdv2.setImageURI(Uri.parse(list.getData().getFeel().getData().get(1).getPic().get(1)));
                list2_sdv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(1).getPic().get(1));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
            if(list.getData().getFeel().getData().get(1).getPic().size()>2){
                list2_sdv3.setVisibility(View.VISIBLE);
                list2_sdv3.setImageURI(Uri.parse(list.getData().getFeel().getData().get(1).getPic().get(2)));
                list2_sdv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(1).getPic().get(2));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
        }
        if(list.getData().getFeel().getData().size()>2){
            list3.setVisibility(View.VISIBLE);
            list3_logo.setImageURI(Uri.parse(list.getData().getFeel().getData().get(2).getHead_img()));
            name3.setText(list.getData().getFeel().getData().get(2).getNickname());
            comment3.setText(list.getData().getFeel().getData().get(2).getComment());
            if(list.getData().getFeel().getData().get(2).getPic().size()>0){
                ContentUtil.makeLog("正在执行第三个item","图片数量大于0");
                list3_image.setVisibility(View.VISIBLE);
                list3_sdv1.setVisibility(View.VISIBLE);
                list3_sdv1.setImageURI(Uri.parse(list.getData().getFeel().getData().get(2).getPic().get(0)));
                list3_sdv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(2).getPic().get(0));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
            if(list.getData().getFeel().getData().get(2).getPic().size()>1){
                list3_sdv2.setVisibility(View.VISIBLE);
                list3_sdv2.setImageURI(Uri.parse(list.getData().getFeel().getData().get(2).getPic().get(1)));
                list3_sdv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(2).getPic().get(1));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
            if(list.getData().getFeel().getData().get(2).getPic().size()>2){
                list3_sdv3.setVisibility(View.VISIBLE);
                list3_sdv3.setImageURI(Uri.parse(list.getData().getFeel().getData().get(2).getPic().get(2)));
                list3_sdv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(INSTANCE, "lookCommentListImg");
                        ArrayList<String> urlList = new ArrayList<String>();
                        urlList.add(list.getData().getFeel().getData().get(2).getPic().get(2));
                        ExclusiveYeUtils.toShowBigImages(INSTANCE,urlList,1);
                    }
                });
            }
        }
        if(postion!=0){
            if(list.getData().getProduct().size()>4){
                gv_zhankai.setVisibility(View.VISIBLE);
                adapter=new BrandDetailHiGhestAdapter(INSTANCE,list.getData().getProduct());
                gv.setAdapter(adapter);
                setGridViewHeightBasedOnChildren(gv);
            }else {
                gv_zhankai.setVisibility(View.GONE);
                adapter=new BrandDetailHiGhestAdapter(INSTANCE,list.getData().getProduct());
                ContentUtil.makeLog("正在新建gridview","开始");
                adapter.state=1;
                gv.setAdapter(adapter);
                setGridViewHeightBasedOnChildren(gv);
            }
        }
    }

    private void setListerner() {
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
        gv_zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // adapter.updateData(true);
                adapter=new BrandDetailHiGhestAdapter(INSTANCE,list.getData().getProduct());
                ContentUtil.makeLog("正在新建gridview","开始");
                adapter.state=1;
                gv.setAdapter(adapter);
                setGridViewHeightBasedOnChildren(gv);
            }
        });
        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "BrandStoryOpenAndClose");
                content.setSingleLine(false);
                zhankai.setVisibility(View.GONE);
                shou.setVisibility(View.VISIBLE);
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "BrandStoryOpenAndClose");
                zhankai.setVisibility(View.VISIBLE);
                shou.setVisibility(View.GONE);
                content.setLines(6);
                content.setEllipsize(TextUtils.TruncateAt.END);
            }
        });
        I_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "wantSaySomethingAction");
                if (AccountUtil.showLoginView(INSTANCE)) return ;
                Intent intent=new Intent(INSTANCE,ReleaseActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getId());
                startActivity(intent);
            }
        });
        zhankai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "BrandLineProductsOpenAndClose");
                adapter.updateData(true);
                zhankai2.setVisibility(View.GONE);
                shou2.setVisibility(View.VISIBLE);
                setGridViewHeightBasedOnChildren(gv);
            }
        });
        shou2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "BrandLineProductsOpenAndClose");
                adapter.updateData(false);
                shou2.setVisibility(View.GONE);
                zhankai2.setVisibility(View.VISIBLE);
                setGridViewHeightBasedOnChildren(gv);
            }
        });
        /*sv.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                details_rl.setAlpha(newAlpha);
            }
        });*/
        zan.setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "isOrNoCollectAttentionBrand");
                    collect();
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.getData().getProduct().get(position).getType().equals("3")){//线下购买
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "lookLineProductsList");
                    Intent intent=new Intent(INSTANCE,FoundDetailActivity.class);
                    intent.putExtra("id",String.valueOf(list.getData().getProduct().get(position).getId()));
                    startActivity(intent);
                }else {
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "lookLineProductsList");
                    Intent intent=new Intent(INSTANCE,ShopDetailsActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA,String.valueOf(list.getData().getProduct().get(position).getId()));
                    startActivity(intent);
                }
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "lookBrandShop");
                Intent intent=new Intent(INSTANCE,ShopActivity.class);
                intent.putExtra("data", (Serializable) list.getData().getShop());
                startActivity(intent);
            }
        });
        designers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "lookBrandDetailDesigner");
                Intent intent=new Intent(INSTANCE,StylistActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getDesigners().get(0).getId());
                startActivity(intent);
            }
        });
        designers2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "lookBrandDetailDesigner");
                Intent intent=new Intent(INSTANCE,StylistActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.getData().getDesigners().get(0).getId());
                startActivity(intent);
            }
        });
    }

    private void setData(final boolean isLoadding){
        /**
         * 异步请求方式
         */
        if(isLoadding) dialog.show();
        Call<BrandDtailBean> call= HttpServiceClient.getInstance().brand_detail(id, MyApplication.cityId,MyApplication.token);
        call.enqueue(new Callback<BrandDtailBean>() {
            @Override
            public void onResponse(Call<BrandDtailBean> call, Response<BrandDtailBean> response) {
                if (isLoadding) dialog.dismiss();
                if (response.isSuccessful()) {
                    BrandDtailBean userBean = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if(userBean.getStatus().toString().equals("ok")){
                        list=userBean;
                        setViews2();
                    }else {
                        /*Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();*/
                        ContentUtil.makeToast(INSTANCE, userBean.getError().getMessage());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BrandDtailBean> call, Throwable t) {
                ContentUtil.makeLog("失败","失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
    private void collect() {
        /**
         * 异步请求方式
         */

        Call<CollectBean> call= HttpServiceClient.getInstance().brand_collect(id, MyApplication.token);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                String state;
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        if(("1".equals(response.body().getData().getStatus()))){
                            state="关注成功";
                        }else {
                            state="取消关注";
                        }
                        Dialog d= DialogUtil.show(INSTANCE, state, false);
                        d.show();
                        zan.setImageResource(("1".equals(response.body().getData().getStatus()) ? R.drawable.icon2_1 : R.drawable.icon2_6));
                        cnt.setText(response.body().getData().getFollow_cnt());
                    } else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
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
    //GridView高度自适应  num=2
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        int number;
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null)return;
        int col = 2;
        int totalHeight = 0;
        if(listAdapter.getCount()%2>0){
            number=listAdapter.getCount()+1;
        }else {
            number=listAdapter.getCount();
        }
        for (int i = 0; i < number; i += col) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if(listAdapter.getCount()<3){
            params.height = totalHeight;
        }else {
            params.height = totalHeight+(number/2-1)*25;
        }

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        gridView.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(true);
    }
}
