package enjoytouch.com.redstar.util;

import java.net.URI;

import enjoytouch.com.redstar.bean.SearchBean;

/**
 * Created by Administrator on 2015/7/3.
 */
public class GlobalConsts {

    //是否登陆
    public static final String ISLOGIN = "islogin";

    //是否为第一次登陆

    public static final String FIRST = "first";

    /**
     * 用户信息
     */
    public static final String CONSTANT_USER_ID = "id";
    public static final String CONSTANT_MOBLIE = "mobile";
    public static final String CONSTANT_TOKEN = "token";
    public static final String CONSTANT_NICKNAME = "nickname";
    public static final String CONSTANT_SEX = "sex";
    public static final String CONSTANT_HEAD = "head_img";


    public static final String UNLOGIN_ID = "";

    public static final int PROGRESS_START_OFFSET = 24;

    public static final String INTENT_DATA = "intent_data";
    public static final String INTENT = "intent";
    public static final int CODE = 2;
    public static final int CODES = 3;

    //收货地址
    public static final String address_name = "address_name";
    public static final String address_phone = "请添加";
    public static final String address_id = "address_id";
    public static final String address = "请添加收货地址";


    /**
     * 微信
     */
    public static final String WX_APP_ID = "wxea6787f62e9003cb";
    public static final String WX_APP_SECRET = "e15047a96a8af36348223ead0bf5514d";

    /**
     * QQ
     */
    public static final String QQ_APP_ID = "1104814099";
    public static final String QQ_APP_SECRET="hfNGidB9imNPDMyL";

    /**
     * 微博信息
     */

    /**
     * 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY
     */
    public static final String WEIBO_APP_ID = "677202928";
    public static final String WEIBO_APP_SECRET="3f90836ef4a3c2d058c5340d0e7c686f";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p/>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p/>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p/>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p/>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";


    /**
     * 寓上二期API
     *
     *
     */

    /**
     * //二期接口域名(测试)
     */
    //public static final String URI = "http://meiyu-api.uduoo.com/a3/";

    public static final String URI="http://api.yushang001.com/a3/";
    //首页
    public static final String HOMEPAGE = "index";
    //品牌lieb
    public static final String BRAND_LIST = "brand/index";
    //品牌详情
    //品牌搜索
    public static final String BRAND_SEACH = "brand/search";
    //风格搭配列表
    public static final String PRODUCT_LIST="product/creative";
    //店铺列表
    public static final String SHOPLIST = "shop/index";
    //登录
    public static final String LOGIN = "login/index";
    //注册
    public static final String REGISTER = "register/index";
    //注册信息补全
    public static final String SUBMIT_INFO = "register/submit_info";

    //城市定位
    public static final String CITY = "locate/city";
    //获取城市数据
    public static final String CITY_LIST = "common/get_city";
    public static final String CITY2_LIST="common/city";

    //第三方登录
    public static final String THIRD_LOGIN = "login/third_party_login";
    //绑定手机号
    public static final String LOGIN_BIND = "login/login_bind";
    //上品列表
    public static final String PRODUCT_INDEX = "product/index";
    // 上品关键词搜索
    public static final String PRODUCT_SEARCH = "product/search";
    //趣处列表
    public static final String SHOP_INDEX = "shop/index";
    //趣处详情
    public static final String ShOP_DETAIL = "shop/cool_shop_detail";
    //趣处关注
    public static final String QUCHU_COLLECT = "shop/fun_place_collect";
    //品牌关注
    public static final String BRAN_COLLECT="brand/follow";
    //趣处专题详情
    public static final String QUCHU_ZHUANTI = "shop/cool_shop_list";
    //趣处图文详情
    public static final String QUCHU_PHOTO = "shop/photo_fun_place";
    //购物车列表
    public static final String SHOPCAR_LIST = "product/shopping_car_list";
    //删除购物车数据
    public static final String DEL_SHOPCAR = "product/del_shop_car";
    //购物车编辑
    public static final String EDIT_SHOPCAR = "product/edit_shop_car";
    //上品详情页
    public static final String PRODUCT_DTAIL = "product/detail";
    //为你发现列表
    public static final String FUND_LIST = "product/product_offline";
    //为你发现详情
    public static final String FUND_DATIL = "product/product_offline_detail";
    //品类的类别基础数据
    public static final String TYPE = "common/get_brand_category";
    //品牌的关注列表
    public static final String BRAND_FOCUS = "user/brand_follow";
    //品牌详情
    public static final String BRAND_DETAIL="brand/detail";
    //推荐的趣处列表
    public static final String RECOMMEND_QUCHU = "user/shop_recommend_list";
    //我关注的趣处列表
    public static final String FOCUS_QUCHU = "user_funplace_collection";
    //为你发现的类别的基础数据
    public static final String FUND_TYPE = "common/get_product_category";
    //风格的基础数据
    public static final String STYLE = "common/get_style";
    //订单预览
    public static final String ORDER_CONFIRM = "product/order_confirm";
    //我的收货地址
    public static final String USER_ADDREAALIST = "user/addressList";
    //编辑用户地址
    public static final String USER_EDITADDRESS = "user/editAddress";
    //删除我的地址
    public static final String EDLADDRESS = "user/delAddress";
    //商品下单
    public static final String ORDER = "product/order";
    //支付宝支付方式
    public static final String PAY_WAY = "product/pay_way";
    //支付完成
    public static final String PAY_FINISH = "product/pay_finish";
    //微信支付
    public static final String PREPAY = "wxpay/prepay";
    //我的订单列表
    public static final String USER_ORDER_LIST = "user/user_product_order";
    //意见反馈
    public static final String USER_FEEDBACK = "user/feedback";
    //用户信息编辑
    public static final String USER_UPDATEINFO = "user/update_info";
    //更换手机号
    public static final String USER_UPDATEMOBILE = "user/update_mobile";
    //订单详情
    public static final String ORDER_DETAILS = "user/product_order_detail";
    //上品收藏和取消收藏
    public static final String PRODUCT_COLLECTION = "product/product_collection";
    //确认收货
    public static final String COMFIR_RECEIPT = "user/comfirmation_receipt";
    //取消订单
    public static final String ORDER_CANCEL = "user/product_order_cancel";
    //申请退货
    public static final String ORDER_REFUND = "user/product_order_refund";
    //推荐趣处
    public static final String SHOP_RECOMMEND = "shop/shop_recommend";
    //店铺类型基础数据
    public static final String GET_SHOP_TYPE = "common/get_shop_type";
    //我推荐的趣处列表
    public static final String SHOP_COMMEND_LIST = "user/shop_recommend_list";
    //我关注的品牌列表
    public static final String BRAND_FOLLOW = "user/brand_follow";
    //设计师详情
    public static final String DESIGNERS_DETAIL = "designers/detail";
    //我的商品收藏列表
    public static final String COLLECTION = "user/product_collection";
    //说说提交
    public static final String SHOP_COMMENT = "shop/shop_comment";
    //说说列表
    public static final String SHOP_COMMENT_LIST = "shop/shop_comment_list";
    //感受列表
    public static final String BRAND_FEEL = "brand/feel";
    //感受提交
    public static final String FEEL_SUB = "brand/feel_sub";
    //趣处关注列表
    public static final String USER_FUNPLACE_COLLECTION="user/user_funplace_collection";
    //用户首页
    public static final String USER_INDEX="user/index";
    //退出登录
    public static final String LOGINOUT="logout/index";
    //获取运费信息
    public static final String GETSHIPMENT="product/get_shipment";
    //物流信息
    public static final String LOGISTICS="user/product_order_logistics";
    //检测手机号是否注册
    public static final String REGISTER_CHECKMOBILEEISEXISTS="register/checkmobileisexists";
    //

}
