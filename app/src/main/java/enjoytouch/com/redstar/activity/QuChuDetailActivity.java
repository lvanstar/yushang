package enjoytouch.com.redstar.activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.ContentAdapter;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.CollectBean;
import enjoytouch.com.redstar.bean.QuChuDetail;
import enjoytouch.com.redstar.selfview.ImagePage.AdverInfo;
import enjoytouch.com.redstar.selfview.observableScrollViewo.ObservableScrollView;
import enjoytouch.com.redstar.selfview.observableScrollViewo.OnScrollChangedCallback;
import enjoytouch.com.redstar.selfview.widget.ShapeLoadingDialog;
import enjoytouch.com.redstar.util.AccountUtil;
import enjoytouch.com.redstar.util.CarouselUtils;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.DisplayUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.MenuDialogUtils;
import enjoytouch.com.redstar.util.ShareUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuChuDetailActivity extends FragmentActivity {
    private MenuDialogUtils menuDialog;
    private String [] datas;
    private QuChuDetail.DataBean bean;
    private QuChuDetailActivity INSTANCE;
    private List<AdverInfo> list_carouse;
    private List<String>list_img;
    private boolean isInit=false;
    private static final String OPEN = "展开";
    private static final String CLOSE = "收起";
    private String state="1";   //1显示分享和收藏，2就不显示
    @InjectView(R.id.ll_viewpage_image)
    LinearLayout pointContainer;        //轮播的小数点
    @InjectView(R.id.viewpager)         //轮播
    LinearLayout vp;
    @InjectView(R.id.home)
    RelativeLayout line;
    @InjectView(R.id.share)
    ImageView share;
    @InjectView(R.id.zan)
    ImageView zan;
    @InjectView(R.id.quchu_comment)
    TextView comment;
    @InjectView(R.id.cover_image)       //封面图片
    SimpleDraweeView cover_image;
    @InjectView(R.id.name)              //名称
    TextView name;
    @InjectView(R.id.title)             //副标题
    TextView title;
    @InjectView(R.id.tel)               //电话
    TextView tel;
    @InjectView(R.id.ll_content)
    LinearLayout ll_content;
    @InjectView(R.id.business_hours)              //营业时间
    TextView time;
    @InjectView(R.id.address)           //地址
    TextView address;
    @InjectView(R.id.lable_ll1)         //属性标签
    RelativeLayout label_ll1;
    @InjectView(R.id.label_iv1)
    SimpleDraweeView label_iv1;
    @InjectView(R.id.label_text1)
    TextView label_text1;
    @InjectView(R.id.lable_ll2)
    RelativeLayout label_ll2;
    @InjectView(R.id.label_iv2)
    SimpleDraweeView label_iv2;
    @InjectView(R.id.label_text2)
    TextView label_text2;
    @InjectView(R.id.lable_ll3)
    RelativeLayout label_ll3;
    @InjectView(R.id.label_iv3)
    SimpleDraweeView label_iv3;
    @InjectView(R.id.label_text3)
    TextView label_text3;
    @InjectView(R.id.lable_ll4)
    RelativeLayout label_ll4;
    @InjectView(R.id.label_iv4)
    SimpleDraweeView label_iv4;
    @InjectView(R.id.label_text4)
    TextView label_text4;
    @InjectView(R.id.comment_cnt)       //小鱼感受总数
    TextView comment_cnt;
    @InjectView(R.id.tag1)
    TextView tag1;
    @InjectView(R.id.tag2)
    TextView tag2;
    @InjectView(R.id.tag3)
    TextView tag3;
    @InjectView(R.id.I_comment)
    TextView I_comment;
    @InjectView(R.id.quchu_details_os)
    ObservableScrollView scrollable;
    @InjectView(R.id.quchu_details_rl)
    RelativeLayout quchu_rl;
    @InjectView(R.id.qu_text_collect_cnt)
    TextView collect_Tv;
    @InjectView(R.id.quchu_details_value_ll)
    LinearLayout value_ll;
    @InjectView(R.id.quchu_details_lv)
    ListView comment_lv;
    @InjectView(R.id.show_more)
    RelativeLayout click;
    @InjectView(R.id.quchu_content_sort)
    TextView shortTxt;
    @InjectView(R.id.quchu_content_long)
    TextView longTxt;
    @InjectView(R.id.Spread)
    ImageView imgOpenOrClose;
    /*@InjectView(R.id.quchu_details_tv)
    TextView txtOpenOrClose;*/
    @InjectView(R.id.quchu_details_ll)
    LinearLayout linearLayout;
    @InjectView(R.id.homepage_up_iv)
    ImageView up_iv;
    @InjectView(R.id.open)
    LinearLayout zhankai;
    @InjectView(R.id.open2)
    LinearLayout shou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qu_chu_detail);
        INSTANCE=this;
        ButterKnife.inject(this);
        datas=getIntent().getStringArrayExtra(GlobalConsts.INTENT_DATA);
        state=getIntent().getStringExtra("share");
    }


    private void setListener() {
        if(state==null){
            share.setVisibility(View.VISIBLE);
            zan.setVisibility(View.VISIBLE);
            collect_Tv.setVisibility(View.VISIBLE);
        }else {
            share.setVisibility(View.GONE);
            zan.setVisibility(View.GONE);
            collect_Tv.setVisibility(View.GONE);
        }
        up_iv.setVisibility(View.GONE);
        scrollable.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                quchu_rl.setAlpha(newAlpha);
                ContentUtil.makeLog("正在滑动监听", "监听");
                if (t > 700) {
                    ContentUtil.makeLog("t大于300", "监听");
                    up_iv.setVisibility(View.VISIBLE);
                } else {
                    up_iv.setVisibility(View.GONE);
                }
            }
        });
        up_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollable.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTANCE.finish();
            }
        });
        I_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "storeDetailsWantSaySomethingAction");
                if (AccountUtil.showLoginView(INSTANCE)) return ;
                Intent intent=new Intent(INSTANCE,ReleaseActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,bean.getId());
                intent.putExtra("type","1");
                intent.putExtra("id", bean.getFun_place_id());
                startActivity(intent);
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return ;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "StoreDetailscollectAction");
                collect();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountUtil.showLoginView(INSTANCE)) return;
                ExclusiveYeUtils.onMyEvent(INSTANCE, "StoreDetailsshareAction");
                ShareUtil.share(INSTANCE, line, bean.getName(), bean.getTitle(), "3", "http://www.yushang001.com/home/qcshare/styleinfo" + "?" + "shop_id=" + datas[0] + "&" + "id=" + datas[1], bean.getCover_img());

            }
        });

        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "phoneAction");
                makePhone(v);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(INSTANCE, "lookAddressAction");
                Intent intent=new Intent(INSTANCE,MapActivity.class);
                intent.putExtra("lat",bean.getLat());
                intent.putExtra("lng",bean.getLng());
                startActivity(intent);
            }
        });
        zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortTxt.setSingleLine(false);
                zhankai.setVisibility(View.GONE);
                shou.setVisibility(View.VISIBLE);
            }
        });
        shou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhankai.setVisibility(View.VISIBLE);
                shou.setVisibility(View.GONE);
                shortTxt.setLines(6);
                shortTxt.setEllipsize(TextUtils.TruncateAt.END);
            }
        });
        /*scrollable.setOnScrollChangedCallback(new OnScrollChangedCallback() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onScroll(int l, int t) {
                float newAlpha = (float) t / 500;
                quchu_rl.setAlpha(newAlpha);
            }
        });*/

   }
    private void setViews(){
        list_carouse=new ArrayList<>();
        list_img=new ArrayList<>();
        for(int i=0;i<bean.getPic().size();i++){
            AdverInfo adverInfo=new AdverInfo();
            adverInfo.url=bean.getPic().get(i).getPic();
            list_carouse.add(adverInfo);
            list_img.add(bean.getPic().get(i).getPic());
        }
        CarouselUtils.getCarousel("3", vp, INSTANCE, list_carouse, list_img, 2, null);

        cover_image.setImageURI(Uri.parse(bean.getCover_img()));
        name.setText(bean.getName());
        title.setText(bean.getTitle());
        tel.setText(bean.getTel());
        tel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tel.getPaint().setAntiAlias(true);//抗锯齿
        time.setText(bean.getBusiness_hours());
        address.setText(bean.getAddress());
        address.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        address.getPaint().setAntiAlias(true);//抗锯齿
        if(Integer.valueOf(bean.getComment_cnt())>3){
            comment_cnt.setVisibility(View.VISIBLE);
            comment.setVisibility(View.VISIBLE);
            comment_cnt.setText("小鱼的感受");
            comment.setText("——全部感受——");

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AccountUtil.showLoginView(INSTANCE)) return;
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "storeDetailsAllFeelingsAction");
                    Intent intent = new Intent(INSTANCE, ContentActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA, bean.getId());
                    intent.putExtra("type", "1");
                    intent.putExtra("id", bean.getFun_place_id());
                    startActivity(intent);
                }
            });
        }else if(bean.getComment_cnt().equals("0")){
            comment_cnt.setText("小鱼的感受");
            comment.setVisibility(View.VISIBLE);
            comment.setText("暂无内容");
        }else {
            comment_cnt.setText("小鱼的感受");
            comment.setVisibility(View.GONE);
            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AccountUtil.showLoginView(INSTANCE)) return ;
                    ExclusiveYeUtils.onMyEvent(INSTANCE, "storeDetailsAllFeelingsAction");
                    Intent intent = new Intent(INSTANCE, ContentActivity.class);
                    intent.putExtra(GlobalConsts.INTENT_DATA, bean.getId());
                    intent.putExtra("type", "1");
                    intent.putExtra("id", bean.getFun_place_id());
                    startActivity(intent);
                }
            });
        }
        /*//判断全部感受是否显示
        if(bean.getComment().size()>=3){
            comment.setVisibility(View.VISIBLE);
        }else{
            comment.setVisibility(View.GONE);
        }*/
        if(bean.getLabel().size()>0){
            label_ll1.setVisibility(View.VISIBLE);
            label_iv1.setImageURI(Uri.parse(bean.getLabel().get(0).getIcon()));
            label_text1.setText(bean.getLabel().get(0).getName());
        }
        if(bean.getLabel().size()>1){
            label_ll2.setVisibility(View.VISIBLE);
            label_iv2.setImageURI(Uri.parse(bean.getLabel().get(1).getIcon()));
            label_text2.setText(bean.getLabel().get(1).getName());
        }
        if(bean.getLabel().size()>2){
            label_ll3.setVisibility(View.VISIBLE);
            label_iv3.setImageURI(Uri.parse(bean.getLabel().get(2).getIcon()));
            label_text4.setText(bean.getLabel().get(2).getName());
        }
        if(bean.getLabel().size()>3){
            label_ll3.setVisibility(View.VISIBLE);
            label_iv3.setImageURI(Uri.parse(bean.getLabel().get(3).getIcon()));
            label_text3.setText(bean.getLabel().get(3).getName());
        }
        if(bean.getLabel().size()>4){
            label_ll4.setVisibility(View.VISIBLE);
            label_iv4.setImageURI(Uri.parse(bean.getLabel().get(4).getIcon()));
            label_text4.setText(bean.getLabel().get(4).getName());
        }
        if(bean.getCharacteristic().size()> 0){
            tag1.setVisibility(View.VISIBLE);
            tag1.setText(bean.getCharacteristic().get(0));
        }
        if(bean.getCharacteristic().size()> 1){
            tag2.setVisibility(View.VISIBLE);
            tag2.setText(bean.getCharacteristic().get(1));
        }
        if(bean.getCharacteristic().size()> 2){
            tag3.setVisibility(View.VISIBLE);
            tag3.setText(bean.getCharacteristic().get(2));
        }
        zan.setImageResource(("1".equals(bean.getIs_collection()) ? R.drawable.icon2_1 : R.drawable.icon2_6));
        collect_Tv.setText(bean.getFun_place_collect_cnt());

        if(bean.getComment().size()>0){
            comment_lv.setFocusable(false);
            ContentAdapter contentAdapter=new ContentAdapter(INSTANCE,bean.getComment());
            comment_lv.setAdapter(contentAdapter);
            DisplayUtil.initHeight(comment_lv);
        }


        if("".equals(bean.getContent())){
            ll_content.setVisibility(View.GONE);
            click.setVisibility(View.GONE);
        }else{
            shortTxt.setText(bean.getContent());
            longTxt.setText(bean.getContent());
                //添加回调方法，此方法在每次绘制视图的时候回调，如点击展开时就会调用此方法。
            ViewTreeObserver observer = linearLayout.getViewTreeObserver();
            observer.addOnPreDrawListener(onPreDrawListener);
        }
    }

    private void setData(String shop_id,String id) {
        /**
         * 异步请求方式
         */

        final ShapeLoadingDialog dialog=new ShapeLoadingDialog(INSTANCE);
        dialog.setLoadingText(getString(R.string.loading));
        dialog.show();
        Call<QuChuDetail> call= HttpServiceClient.getInstance().shop_detail(shop_id,id,MyApplication.token);
        call.enqueue(new Callback<QuChuDetail>() {
            @Override
            public void onResponse(Call<QuChuDetail> call, Response<QuChuDetail> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus().toString().equals("ok")){
                        bean=response.body().getData();
                        setViews();
                        setListener();
                    }else {
                        Dialog dialog = DialogUtil.show(INSTANCE, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<QuChuDetail> call, Throwable t) {
                dialog.dismiss();
                ContentUtil.makeLog("失败","失败了");
            }
        });
    }

    private void collect() {
        /**
         * 异步请求方式
         */

        Call<CollectBean> call= HttpServiceClient.getInstance().quchu_collect(bean.getFun_place_id(), MyApplication.token);
        call.enqueue(new Callback<CollectBean>() {
            @Override
            public void onResponse(Call<CollectBean> call, Response<CollectBean> response) {
                String state;
                if (response.isSuccessful()) {
                    CollectBean userBean = response.body();
                    ContentUtil.makeLog("uesrBean", String.valueOf(userBean));
                    if(userBean.getStatus().toString().equals("ok")){
                        if(("1".equals(response.body().getData().getCollect_status()))){
                            state="关注成功";
                        }else {
                            state="取消关注";
                        }
                        Dialog d= DialogUtil.show(INSTANCE, state, false);
                        d.show();
                        zan.setImageResource(("1".equals(response.body().getData().getCollect_status()) ? R.drawable.icon2_1 : R.drawable.icon2_6));
                        collect_Tv.setText(response.body().getData().getCollect_cnt());
                    }else {
                        Dialog dialog = DialogUtil.show(INSTANCE, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                        ExclusiveYeUtils.isExtrude(INSTANCE,response.body().getError().getCode());
                    }
                }else{
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CollectBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
                ContentUtil.makeLog("zzzzz",String.valueOf(t));
                //DialogUtil.show(INSTANCE, t.getJSONObject("error").getString("message"), false).show();
            }
        });
    }
    public void makePhone(View V){
        menuDialog= new MenuDialogUtils(this, R.style.registDialog, R.layout.menu_phone, bean.getTel(), new MenuDialogUtils.ButtonClickListener() {
            @Override
            public void onButtonClick(int i) {
                if (i==0){
                    if (!"".equals(bean.getTel())) {
                        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + bean.getTel()));
                        startActivity(phoneIntent);
                    } else {
                        DialogUtil.show(getApplicationContext(), "号码未知，无法拨打", false).show();
                    }
                }
            }
        });
        menuDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(datas[0], datas[1]);
    }


    /**
     * 视图绘制回调方法
     */
    private ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {

        @Override
        public boolean onPreDraw() {
            // TODO Auto-generated method stub
            //如果已经初始化布局，则不执行后面的方法
            if(isInit){
                return true;
            }
            //如果超过三行就显示 “展开”  的按钮
            if (isShowAll(shortTxt, longTxt)) {
                zhankai.setVisibility(View.VISIBLE);
            }else {
                zhankai.setVisibility(View.GONE);
            }

            isInit=true;
            return true;
        }
    };

    /**
     * 判断文字是否超过四行，返回布尔值
     * @param shortTxt
     * @param longTxt
     * @return
     */
    public boolean isShowAll(TextView shortTxt, TextView longTxt) {

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
