package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/6/24.
 */
public class OrderListBean {
    /**
     * total : 20
     * data : [{"id":"441","order_no":"PO20160624112749661","remainder_time":0,"real_payment":"209.00","title":"","status":"3","product":[{"id":"443","name":"测试sku","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","sale_price":"199.00","amount":"1"}],"business":"寓上","created":"2016/06/24 11:27:49"}]
     * status : ok
     */

    private String total;
    private String status;
    private List<DataEntity> data;
    private ErrorBean error;


    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 441
         * order_no : PO20160624112749661
         * remainder_time : 0
         * real_payment : 209.00
         * title :
         * status : 3
         * product : [{"id":"443","name":"测试sku","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","sale_price":"199.00","amount":"1"}]
         * business : 寓上
         * created : 2016/06/24 11:27:49
         */

        private String id;
        private String order_no;
        private int remainder_time;
        private String real_payment;
        private String title;
        private String status;
        private String business;
        private String created;
        private String receiving_show;
        private List<SkuInfoBean> product;

        public void setId(String id) {
            this.id = id;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public void setRemainder_time(int remainder_time) {
            this.remainder_time = remainder_time;
        }

        public void setReal_payment(String real_payment) {
            this.real_payment = real_payment;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public int getRemainder_time() {
            return remainder_time;
        }

        public String getReal_payment() {
            return real_payment;
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }

        public String getBusiness() {
            return business;
        }

        public String getCreated() {
            return created;
        }

        public String getReceiving_show() {
            return receiving_show;
        }

        public void setReceiving_show(String receiving_show) {
            this.receiving_show = receiving_show;
        }

        public List<SkuInfoBean> getProduct() {
            return product;
        }

        public void setProduct(List<SkuInfoBean> product) {
            this.product = product;
        }
    }
}
