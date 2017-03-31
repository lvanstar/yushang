package enjoytouch.com.redstar.callback;

import enjoytouch.com.redstar.activity.BrandDetailActivity;
import enjoytouch.com.redstar.bean.BrandBean;
import enjoytouch.com.redstar.bean.BrandDetailBean;
import enjoytouch.com.redstar.bean.BrandDtailBean;
import enjoytouch.com.redstar.bean.BrandFollowBean;
import enjoytouch.com.redstar.bean.BrandDtailBean;
import enjoytouch.com.redstar.bean.BrandSearchBean;
import enjoytouch.com.redstar.bean.CityBean;
import enjoytouch.com.redstar.bean.CollectBean;
import enjoytouch.com.redstar.bean.CollectionBean;
import enjoytouch.com.redstar.bean.ContentListBean;
import enjoytouch.com.redstar.bean.EditAddressBean;
import enjoytouch.com.redstar.bean.FundDatilBean;
import enjoytouch.com.redstar.bean.FundListBean;
import enjoytouch.com.redstar.bean.FundTypeBean;
import enjoytouch.com.redstar.bean.GetFirstCityBean;
import enjoytouch.com.redstar.bean.HiGhestBean;
import enjoytouch.com.redstar.bean.HomePageBean;
import enjoytouch.com.redstar.bean.InterestBean;
import enjoytouch.com.redstar.bean.LocateCityBean;
import enjoytouch.com.redstar.bean.LogisticsListBean;
import enjoytouch.com.redstar.bean.MyAddressBean;
import enjoytouch.com.redstar.bean.OrderBean;
import enjoytouch.com.redstar.bean.OrderInfoBean;
import enjoytouch.com.redstar.bean.OrderListBean;
import enjoytouch.com.redstar.bean.ProductBean;
import enjoytouch.com.redstar.bean.ProductListBean;
import enjoytouch.com.redstar.bean.PushImgBean;
import enjoytouch.com.redstar.bean.QuChuDetail;
import enjoytouch.com.redstar.bean.QuChuPhotoBean;
import enjoytouch.com.redstar.bean.RecommendListBean;
import enjoytouch.com.redstar.bean.RegisterBean;
import enjoytouch.com.redstar.bean.SearchBean;
import enjoytouch.com.redstar.bean.ShopCarBean;
import enjoytouch.com.redstar.bean.ShopDetailsBean;
import enjoytouch.com.redstar.bean.ShopTypeBean;
import enjoytouch.com.redstar.bean.ShoppingCartBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.bean.StyleBean;
import enjoytouch.com.redstar.bean.StylistBean;
import enjoytouch.com.redstar.bean.TypeBean;
import enjoytouch.com.redstar.bean.UserBean;
import enjoytouch.com.redstar.bean.UserIndexBean;
import enjoytouch.com.redstar.bean.UserUpdateBean;
import enjoytouch.com.redstar.bean.WXPayBean;
import enjoytouch.com.redstar.bean.ZhuanTiBean;
import enjoytouch.com.redstar.util.GlobalConsts;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lizhaozhao on 16/6/14.
 */
public interface InterfaceService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(GlobalConsts.LOGIN)
    Call<UserBean> login(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 退出登录
     */
    @FormUrlEncoded
    @POST(GlobalConsts.LOGINOUT)
    Call<StatusBean>loginout(@Field("token")String token);


    /**
     * 第三方登录
     */
    @FormUrlEncoded
    @POST(GlobalConsts.THIRD_LOGIN)
    Call<UserBean> third_login(@Field("open_id") String open_id, @Field("type") String type);

    /**
     * 绑定手机号
     */
    @FormUrlEncoded
    @POST(GlobalConsts.LOGIN_BIND)
    Call<UserBean> login_bind(@Field("open_id")String open_id,@Field("type")String type,@Field("mobile") String mobile, @Field("code") String code, @Field("nickname") String nickname,
                              @Field("sex") String sex, @Field("head_img") String head_img);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(GlobalConsts.REGISTER)
    Call<RegisterBean> register(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 注册信息补全
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SUBMIT_INFO)
    Call<StatusBean> submit_info(@Field("id") String id, @Field("head_img") String head_img, @Field("sex") String sex, @Field("nickname") String nickname);


    /**
     * 城市定位
     *
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalConsts.CITY)
    Call<LocateCityBean> locate_city(@Field("lat") String lat, @Field("lng") String lng);

    /**
     * 城市列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(GlobalConsts.CITY2_LIST)
    Call<CityBean> city_list(@Field("token") String token);

    /**
     * 城市基础数据
     *
     * @return
     */
    @POST(GlobalConsts.CITY_LIST)
    Call<GetFirstCityBean>city();

    //图片上传
    @Multipart
    @POST("image/upload")
    Call<PushImgBean> pushimg(@Part("file[]\"; filename=\"image.png\"") RequestBody img1,
                              @Part("file[]\"; filename=\"image.png\"") RequestBody img2,
                              @Part("file[]\"; filename=\"image.png\"") RequestBody img3);

    /**
     * 图片上传获得相对路径
     */
    @Multipart
    @POST("image/upload")
    Call<PushImgBean> pushimg2(@Part("file") String fileName, @Part("file[]\"; filename=\"image.png\"") RequestBody imgs);

    /**
     * 上品列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PRODUCT_INDEX)
    Call<HiGhestBean> product_index(@Field("token")String token,@Field("city_id") String city_id, @Field("page") int page, @Field("size") String size, @Field("keyword") String keyword);

    /**
     * 上品关键词搜索
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PRODUCT_SEARCH)
    Call<SearchBean> product_search(@Field("city_id") String city_id, @Field("keyword") String keyword);

    /**
     * 首页
     */
    @FormUrlEncoded
    @POST(GlobalConsts.HOMEPAGE)
    Call<HomePageBean> homepage(@Field("city_id") String city_id);

    /**
     * 为你发现列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.FUND_LIST)
    Call<FundListBean> fund_list(@Field("city_id") String city_id,
                                 @Field("token")String token,
                                 @Field("page") int page,
                                 @Field("size") String size,
                                 @Field("category") String category,
                                 @Field("style_id") String style_id);

    /**
     * 为你发现详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.FUND_DATIL)
    Call<FundDatilBean> fund_datil(@Field("id") String id, @Field("token") String token);

    /**
     * 为你发现类别基础数据
     */
    @FormUrlEncoded
    @POST(GlobalConsts.FUND_TYPE)
    Call<FundTypeBean> fund_type(@Field("city_id") String city_id);


    /**
     * 风格基础数据
     */
    @FormUrlEncoded
    @POST(GlobalConsts.STYLE)
    Call<StyleBean> style(@Field("type") String type, @Field("city_id") String city_id);

    /**
     * 趣处列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SHOP_INDEX)
    Call<InterestBean> shop_index(@Field("city_id") String city_id, @Field("page") int page, @Field("size") String size);

    /**
     * 趣处关注
     */
    @FormUrlEncoded
    @POST(GlobalConsts.QUCHU_COLLECT)
    Call<CollectBean> quchu_collect(@Field("id") String id, @Field("token") String token);

    /**
     * 品牌关注
     * */
    @FormUrlEncoded
    @POST(GlobalConsts.BRAN_COLLECT)
    Call<CollectBean>brand_collect(@Field("brand_id")String brand_id,@Field("token")String token);

    /**
     * 趣处单店详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.ShOP_DETAIL)
    Call<QuChuDetail> shop_detail(@Field("shop_id") String shop_id, @Field("id") String id, @Field("token") String token);

    /**
     * 趣处专题详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.QUCHU_ZHUANTI)
    Call<ZhuanTiBean> quchu_zhuanti(@Field("token")String token,@Field("city_id") String city_id, @Field("id") String id);

    /**
     * 趣处图文详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.QUCHU_PHOTO)
    Call<QuChuPhotoBean> quchu_photo(@Field("token")String token,@Field("city_id") String city_id, @Field("id") String id);

    /**
     * 品类的类别基础数据
     */
    @FormUrlEncoded
    @POST(GlobalConsts.TYPE)
    Call<TypeBean> type(@Field("city_id") String city_id, @Field("category_id") String category_id);



    /**
     * 品牌列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.BRAND_LIST)
    Call<BrandDetailBean> brand_list(@Field("style_id") String style_id,
                                     @Field("order_by") String order_by,
                                     @Field("size") String size,
                                     @Field("page") int page,
                                     @Field("brand_id") String brand_id,
                                     @Field("category_id") String category_id,
                                     @Field("keyword")String keyword,
                                     @Field("city_id")String city_id);

    /**
     * 品牌详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.BRAND_DETAIL)
    Call<BrandDtailBean> brand_detail(@Field("brand_id") String brand_id,
                                      @Field("city_id")String city_id,
                                      @Field("token") String token);

    /**
     * 品牌搜索
     */
    @FormUrlEncoded
    @POST(GlobalConsts.BRAND_SEACH)
    Call<BrandSearchBean> brand_seach(@Field("city_id") String city_id, @Field("keyword") String keyword);

    /**
     * 风格搭配列表
     * */
    @FormUrlEncoded
    @POST(GlobalConsts.PRODUCT_LIST)
    Call<ProductListBean>product_list(@Field("city_id")String city_id,@Field("style_id")String style_id, @Field("page")int page, @Field("size")String size);

    /**
     * 购物车列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SHOPCAR_LIST)
    Call<ShoppingCartBean> shopCar_list(@Field("token") String token, @Field("city_id") String city_id);

    /**
     * 删除购物车
     */
    @FormUrlEncoded
    @POST(GlobalConsts.DEL_SHOPCAR)
    Call<ShoppingCartBean> del_shopCar(@Field("id") String id, @Field("token") String token, @Field("city_id") String city_id);

    /**
     * 编辑购物车 type为1 增加 type为2 减
     */
    @FormUrlEncoded
    @POST(GlobalConsts.EDIT_SHOPCAR)
    Call<ShopCarBean> edit_shopCar(@Field("id") String id, @Field("type") String type, @Field("token") String token, @Field("city_id") String city_id);

    /**
     * 上品详情页
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PRODUCT_DTAIL)
    Call<ShopDetailsBean> product_dtail(@Field("id") String id, @Field("token") String token, @Field("city_id") String city_id);

    /**
     * 订单预览
     */
    @FormUrlEncoded
    @POST(GlobalConsts.ORDER_CONFIRM)
    Call<ProductBean> order_confirm(@Field("product") String product, @Field("amount") String amount, @Field("city_id") String city_id, @Field("token") String token);

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.ORDER_DETAILS)
    Call<OrderInfoBean> order_details(@Field("token") String token, @Field("id") String id);

    /**
     * 我的收货地址
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_ADDREAALIST)
    Call<MyAddressBean> user_addreaalist(@Field("token") String token);

    /**
     * 编辑用户地址
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_EDITADDRESS)
    Call<EditAddressBean> user_editaddress(@Field("token") String token, @Field("id") String id, @Field("name") String name, @Field("tel") String tel,
                                           @Field("province_id") String province_id, @Field("province_name") String province_name,
                                           @Field("city_id") String city_id, @Field("city_name") String city_name, @Field("area_id") String area_id, @Field("area_name") String area_name,
                                           @Field("address") String address);

    /**
     * 删除我的地址
     */
    @FormUrlEncoded
    @POST(GlobalConsts.EDLADDRESS)
    Call<StatusBean>del_address(@Field("token")String token,@Field("id")String id);

    /**
     * 商品下单
     */
    @FormUrlEncoded
    @POST(GlobalConsts.ORDER)
    Call<OrderBean> order(@Field("product") String product, @Field("amount") String amount, @Field("city_id") String city_id, @Field("address_id") String address_id,
                          @Field("token") String token, @Field("remark") String remark,@Field("type")String type);

    /**
     * 支付宝支付方式
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PAY_WAY)
    Call<StatusBean> pay_way(@Field("token") String token, @Field("id") String id);

    /**
     * 支付完成
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PAY_FINISH)
    Call<StatusBean> pay_finish(@Field("token") String token, @Field("id") String id);


    /**
     * 微信下单
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PREPAY)
    Call<WXPayBean> prepay(@Field("id") String id, @Field("token") String token, @Field("type") String type);

    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_ORDER_LIST)
    Call<OrderListBean> user_orderList(@Field("token") String token, @Field("page") int page, @Field("size") String size);

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_FEEDBACK)
    Call<StatusBean> user_feedback(@Field("token") String token, @Field("content") String content, @Field("lat") String lat, @Field("lng") String lng);

    /**
     * 信息编辑
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_UPDATEINFO)
    Call<UserBean> user_updateinfo(@Field("token") String token, @Field("type") String type, @Field("value") String value);

    /**
     * 更换手机号
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_UPDATEMOBILE)
    Call<StatusBean> user_updatemobile(@Field("token") String token, @Field("code") String code, @Field("mobile") String mobile);

    /**
     * 上品收藏和取消收藏
     */
    @FormUrlEncoded
    @POST(GlobalConsts.PRODUCT_COLLECTION)
    Call<CollectBean> product_collection(@Field("product_id") String id, @Field("token") String token);

    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST(GlobalConsts.COMFIR_RECEIPT)
    Call<StatusBean>comfir_receipt(@Field("token")String token,@Field("id")String id);

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST(GlobalConsts.ORDER_CANCEL)
    Call<StatusBean> order_cancel(@Field("token") String token, @Field("id") String id, @Field("reason") String reason);

    /**
     * 申请退货
     */
    @FormUrlEncoded
    @POST(GlobalConsts.ORDER_REFUND)
    Call<StatusBean>order_refund(@Field("token")String token,@Field("id")String id,@Field("product_sale_id")String product_sale_id,@Field("sku_id")String sku_id);

    /**
     * 推荐趣处
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SHOP_RECOMMEND)
    Call<StatusBean> shop_recommend(@Field("token") String token, @Field("cover_img") String cover_img, @Field("name") String name, @Field("address") String address,
                                    @Field("reason") String reason, @Field("type") String type);

    /**
     * 店铺基础数据
     */
    @POST(GlobalConsts.GET_SHOP_TYPE)
    Call<ShopTypeBean> get_shopType();

    /**
     * 我的推荐趣处列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SHOP_COMMEND_LIST)
    Call<RecommendListBean> shop_commend_list(@Field("token") String token, @Field("page") int page, @Field("size") String size);

    /**
     * 我的关注品牌列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.BRAND_FOLLOW)
    Call<BrandFollowBean> brand_follow(@Field("token") String token, @Field("page") int page, @Field("size") String size);

    /**
     * 设计师详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.DESIGNERS_DETAIL)
    Call<StylistBean> designers_detail(@Field("id") String id);

    /**
     * 说说提交
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SHOP_COMMENT)
    Call<StatusBean> shop_comment(@Field("shop_id") String shop_id,@Field("fun_place_id")String fun_place_id, @Field("comment") String comment, @Field("pic") String pic, @Field("token") String token);

    /**
     * 说说列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.SHOP_COMMENT_LIST)
    Call<ContentListBean> shop_comment_list(@Field("shop_id") String shop_id, @Field("fun_place_id")String fun_place_id,@Field("page") int page, @Field("size") String size, @Field("token") String token);

    /**
     * 感受列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.BRAND_FEEL)
    Call<ContentListBean>brand_feel(@Field("brand_id")String shop_id,@Field("page")int page,@Field("size")String size,@Field("token")String token);

    /**
     * 感受提交
     */
    @FormUrlEncoded
    @POST(GlobalConsts.FEEL_SUB)
    Call<StatusBean>feel_sub(@Field("brand_id")String shop_id,@Field("comment")String comment,@Field("feel_img")String pic,@Field("token")String token);

    /**
     * 我收藏的商品列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.COLLECTION)
    Call<CollectionBean> user_product_collection(@Field("token") String token, @Field("type") String type, @Field("city_id") String city_id, @Field("page") int page, @Field("size") String size);

    /**
     * 趣处推荐列表
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_FUNPLACE_COLLECTION)
    Call<InterestBean> user_funplace_collection(@Field("token") String token, @Field("page") int page, @Field("size") String size);
    /**
     *用户首页
     */
    @FormUrlEncoded
    @POST(GlobalConsts.USER_INDEX)
    Call<UserIndexBean>user_index(@Field("token")String token);

    /**
     * 获取运费信息
     */
    @FormUrlEncoded
    @POST(GlobalConsts.GETSHIPMENT)
    Call<ProductBean>get_shipment(@Field("product")String product,@Field("amount")String amount,@Field("city_id")String city_id,
                                   @Field("address_id")String address_id,@Field("token")String token);

    /**
     * 物流详情
     */
    @FormUrlEncoded
    @POST(GlobalConsts.LOGISTICS)
    Call<LogisticsListBean>logistics_list(@Field("logistics_no")String ogistics_no,@Field("token")String token);

    /**
     * 检测手机号是否注册
     */
    @FormUrlEncoded
    @POST(GlobalConsts.REGISTER_CHECKMOBILEEISEXISTS)
    Call<StatusBean>register_checkmobileisexists(@Field("mobile")String mobile);
}
