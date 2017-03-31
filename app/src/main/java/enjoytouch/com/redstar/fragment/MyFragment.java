package enjoytouch.com.redstar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.MyAddressActivity;
import enjoytouch.com.redstar.activity.MyAttentionActivity;
import enjoytouch.com.redstar.activity.MyCollectionShopActivity;
import enjoytouch.com.redstar.activity.MyQuChuActivity;
import enjoytouch.com.redstar.activity.MyTheOderActivity;
import enjoytouch.com.redstar.activity.PersonMessageActivity;
import enjoytouch.com.redstar.activity.RecommendShopActivity;
import enjoytouch.com.redstar.activity.SettingActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.UserIndexBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页第四个模块  我的
 * duan
 */
public class MyFragment extends BaseFragment {
    private UserIndexBean.DataBean data = new UserIndexBean.DataBean();

    private View view;
    private View setting, set;

    private static MyFragment INSTANCE;
    private TextView phone;
    private LinearLayout r11;
    private LinearLayout r12;
    private LinearLayout r13;
    private LinearLayout r14;
    private LinearLayout r15;
    private LinearLayout r16;
    private TextView tv_ordernumber, tv_hotnumber, tv_mybrandnumber, tv_mylikenumber, tv_shop_recommend;
    private TextView tv_phonenumber;

    @InjectView(R.id.my_to_info)
    RelativeLayout to_info_Rl;
    @InjectView(R.id.pic)
    SimpleDraweeView head_img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.my_fragment, null);//实例化fragment
        return view;
    }

    private void init() {
        tv_ordernumber = (TextView) view.findViewById(R.id.tv_ordernumber);//我的订单
        tv_hotnumber = (TextView) view.findViewById(R.id.tv_hotnumber);//我收藏的商品
        tv_mybrandnumber = (TextView) view.findViewById(R.id.tv_mybrandnumber);//我关注的品牌
        tv_phonenumber = (TextView) view.findViewById(R.id.tv_phonenumber);
        tv_mylikenumber = (TextView) view.findViewById(R.id.tv_mylikenumber);//关注的趣处
        tv_shop_recommend = (TextView) view.findViewById(R.id.tv_shop_recommend);//推荐的趣处
        tv_phonenumber.setText(MyApplication.nickname);
        head_img.setImageURI(Uri.parse(MyApplication.head_img));


        Call<UserIndexBean> call = HttpServiceClient.getInstance().user_index(MyApplication.token);
        call.enqueue(new Callback<UserIndexBean>() {
            @Override
            public void onResponse(Call<UserIndexBean> call, Response<UserIndexBean> response) {
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        data = response.body().getData();
                        tv_ordernumber.setText(data.getProduct_order_cnt());
                        tv_hotnumber.setText(data.getProduct_collection_cnt());
                        tv_mybrandnumber.setText(data.getBrand_follow_cnt());
                        tv_mylikenumber.setText(data.getFunplace_collection_cnt());
                        tv_shop_recommend.setText(data.getShop_recommend_cnt());

                    } else {
                        ContentUtil.makeToast(getActivity(), response.body().getError().getMessage());
                        ExclusiveYeUtils.isExtrude(getActivity(),response.body().getError().getCode());

                    }
                }else {
                    ContentUtil.makeToast(getActivity(), getString(R.string.loadding_error));
                }
            }

            @Override
            public void onFailure(Call<UserIndexBean> call, Throwable t) {
                ContentUtil.makeLog("ztt", "2222222");
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        INSTANCE = this;
        ButterKnife.inject(this, view);
        setViews();
        setListeners();

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setViews() {
        phone = (TextView) view.findViewById(R.id.tv_phonenumber);//手机号码
        set = view.findViewById(R.id.my_set);
        setting = view.findViewById(R.id.my_settings); //“设置”布局
        r12 = (LinearLayout) view.findViewById(R.id.tomyhot);    //我收藏的商品
        r11 = (LinearLayout) view.findViewById(R.id.ll_tomyorder);    //我的订单
        r13 = (LinearLayout) view.findViewById(R.id.tv_attention);  //我关注的品牌
        r14 = (LinearLayout) view.findViewById(R.id.ll_tomyintere);  //我关注的趣处
        r15 = (LinearLayout) view.findViewById(R.id.tomyrecommend);  //我推荐的趣处
        r16 = (LinearLayout) view.findViewById(R.id.tomyaddress);    //我的收获地址


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onRes() {
        init();
    }

    @Override
    public void initData() {
        init();
    }

    @Override
    public void resetUserData() {
        init();
    }

    @Override
    public void removeUserData() {

    }


    private void setListeners() {
        /**
         * 跳转个人信息页
         */
        to_info_Rl.setOnClickListener(new View.OnClickListener() {//跳到个人信息页
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyInforAction");
                Intent intent = new Intent(getActivity(), PersonMessageActivity.class);
                startActivity(intent);
            }
        });
        //跳转设置页面
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "moreAction");
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        r11.setOnClickListener(new View.OnClickListener() {//我的订单
            @Override
            public void onClick(View view) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyOrderList");
                Intent intent = new Intent(getActivity(), MyTheOderActivity.class);
                startActivity(intent);
            }
        });
        r12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//我收藏的商品
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyCollectHotStyleList");
                Intent intent = new Intent(getActivity(), MyCollectionShopActivity.class);
                startActivity(intent);
            }
        });
        r13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//我关注的品牌
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyAttentionBrand");
                Intent intent = new Intent(getActivity(), MyAttentionActivity.class);
                startActivity(intent);
            }
        });
        r14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//我关注的趣处
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyAttentionPlace");
                Intent intent = new Intent(getActivity(), MyQuChuActivity.class);
                startActivity(intent);
            }
        });
        r15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//我推荐的趣处
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyRecommendPlace");
                Intent intent = new Intent();
                intent.setClass(getActivity(), RecommendShopActivity.class);
                startActivity(intent);
            }
        });
        r16.setOnClickListener(new View.OnClickListener() {//我的收货地址
            @Override
            public void onClick(View view) {
                ExclusiveYeUtils.onMyEvent(getActivity(), "lookMyAddress");
                Intent intent = new Intent(getActivity(), MyAddressActivity.class);
                startActivity(intent);
            }
        });

    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
