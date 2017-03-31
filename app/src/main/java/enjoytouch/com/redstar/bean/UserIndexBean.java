package enjoytouch.com.redstar.bean;

/**
 * Created by lzz on 2016/7/4.
 */
public class UserIndexBean {

    /**
     * product_order_cnt : 6
     * funplace_collection_cnt : 3
     * brand_follow_cnt : 0
     * product_collection_cnt : 8
     * shop_recommend_cnt : 3
     */

    private DataBean data;
    /**
     * data : {"product_order_cnt":"6","funplace_collection_cnt":"3","brand_follow_cnt":"0","product_collection_cnt":"8","shop_recommend_cnt":"3"}
     * status : ok
     */

    private String status;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        private String product_order_cnt;
        private String funplace_collection_cnt;
        private String brand_follow_cnt;
        private String product_collection_cnt;
        private String shop_recommend_cnt;

        public String getProduct_order_cnt() {
            return product_order_cnt;
        }

        public void setProduct_order_cnt(String product_order_cnt) {
            this.product_order_cnt = product_order_cnt;
        }

        public String getFunplace_collection_cnt() {
            return funplace_collection_cnt;
        }

        public void setFunplace_collection_cnt(String funplace_collection_cnt) {
            this.funplace_collection_cnt = funplace_collection_cnt;
        }

        public String getBrand_follow_cnt() {
            return brand_follow_cnt;
        }

        public void setBrand_follow_cnt(String brand_follow_cnt) {
            this.brand_follow_cnt = brand_follow_cnt;
        }

        public String getProduct_collection_cnt() {
            return product_collection_cnt;
        }

        public void setProduct_collection_cnt(String product_collection_cnt) {
            this.product_collection_cnt = product_collection_cnt;
        }

        public String getShop_recommend_cnt() {
            return shop_recommend_cnt;
        }

        public void setShop_recommend_cnt(String shop_recommend_cnt) {
            this.shop_recommend_cnt = shop_recommend_cnt;
        }
    }
}
