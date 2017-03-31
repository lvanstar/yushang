package enjoytouch.com.redstar.bean;

/**
 * Created by Administrator on 2016/1/7.
 * 用户首页json
 */
public class MyBean {
    public String coupon_cnt;
    public String coupon_collect_cnt;
    public String brand_follow_cnt;
    public String shop_follow_cnt;
    public String product_collect_cnt;
    public String product_order_cnt;

    @Override
    public String toString() {
        return "MyBean{" +
                "coupon_cnt='" + coupon_cnt + '\'' +
                ", coupon_collect_cnt='" + coupon_collect_cnt + '\'' +
                ", brand_follow_cnt='" + brand_follow_cnt + '\'' +
                ", shop_follow_cnt='" + shop_follow_cnt + '\'' +
                ", product_collect_cnt='" + product_collect_cnt + '\'' +
                ", product_order_cnt='" + product_order_cnt + '\'' +
                '}';
    }
}
