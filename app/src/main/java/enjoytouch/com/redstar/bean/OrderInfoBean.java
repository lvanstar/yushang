package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/6/27.
 */
public class OrderInfoBean {


    private DataEntity data;
    private String status;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public static class DataEntity {
        /**
         * id : 431
         * order_no : PO20160623162937243
         * created : 2016/06/23 16:29:37
         * order_price : 6353.00
         * real_payment : 6419.00
         * user_remark :
         * delivery_price : 66.00
         * pay_code : null
         * pay_time :
         * status : 3
         * business : 寓上
         * title :
         * finish_time :
         * address : {"id":null,"name":"蔡振喜","province_name":"上海市","city_id":"35","city_name":"上海市","area_name":"宝山区","address":"啊董事长徐和规范市场经济"}
         * product : [{"sku_info":[{"id":"443","name":"测试sku","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","sale_price":"199.00","amount":"199","refund_enable":"1","status":null,"refund_status":"","return_reason":"","return_time":""}],"logistics":null},{"sku_info":[{"id":"434","name":"开洋免漆木门187","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160229/56d43094053b6.jpg","sale_price":"799.00","amount":"799","refund_enable":"1","status":null,"refund_status":"","return_reason":"","return_time":""},{"id":"441","name":"多乐士抗甲醛五合一套装","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160304/56d8fdc8b7130.jpg","sale_price":"680.00","amount":"680","refund_enable":"2","status":null,"refund_status":"","return_reason":"","return_time":""}],"logistics":null}]
         * remainder_time : 0
         */

        private String id;
        private String order_no;
        private String created;
        private String order_price;
        private String real_payment;
        private String user_remark;
        private String delivery_price;
        private String pay_code;
        private String pay_time;
        private String status;
        private String business;
        private String title;
        private String finish_time;
        private String receiving_show;
        private String cancel_reson;
        private String discounted_price;
        private AddressEntity address;
        private int remainder_time;
        private List<ProductEntity> product;

        public void setId(String id) {
            this.id = id;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public void setReal_payment(String real_payment) {
            this.real_payment = real_payment;
        }

        public void setUser_remark(String user_remark) {
            this.user_remark = user_remark;
        }

        public void setDelivery_price(String delivery_price) {
            this.delivery_price = delivery_price;
        }

        public String getReceiving_show() {
            return receiving_show;
        }

        public void setReceiving_show(String receiving_show) {
            this.receiving_show = receiving_show;
        }

        public String getCancel_reson() {
            return cancel_reson;
        }

        public void setCancel_reson(String cancel_reson) {
            this.cancel_reson = cancel_reson;
        }

        public void setPay_code(String pay_code) {
            this.pay_code = pay_code;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public void setAddress(AddressEntity address) {
            this.address = address;
        }

        public void setRemainder_time(int remainder_time) {
            this.remainder_time = remainder_time;
        }

        public void setProduct(List<ProductEntity> product) {
            this.product = product;
        }

        public String getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(String discounted_price) {
            this.discounted_price = discounted_price;
        }

        public String getId() {
            return id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public String getCreated() {
            return created;
        }

        public String getOrder_price() {
            return order_price;
        }

        public String getReal_payment() {
            return real_payment;
        }

        public String getUser_remark() {
            return user_remark;
        }

        public String getDelivery_price() {
            return delivery_price;
        }

        public String getPay_code() {
            return pay_code;
        }

        public String getPay_time() {
            return pay_time;
        }

        public String getStatus() {
            return status;
        }

        public String getBusiness() {
            return business;
        }

        public String getTitle() {
            return title;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public AddressEntity getAddress() {
            return address;
        }

        public int getRemainder_time() {
            return remainder_time;
        }

        public List<ProductEntity> getProduct() {
            return product;
        }

        public static class AddressEntity {
            /**
             * id : null
             * name : 蔡振喜
             * province_name : 上海市
             * city_id : 35
             * city_name : 上海市
             * area_name : 宝山区
             * address : 啊董事长徐和规范市场经济
             */

            private Object id;
            private String name;
            private String province_name;
            private String city_id;
            private String city_name;
            private String area_name;
            private String address;
            private String tel;


            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getProvince_name() {
                return province_name;
            }

            public String getCity_id() {
                return city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public String getArea_name() {
                return area_name;
            }

            public String getAddress() {
                return address;
            }
        }

        public static class ProductEntity {
            /**
             * sku_info : [{"id":"443","name":"测试sku","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","sale_price":"199.00","amount":"199","refund_enable":"1","status":null,"refund_status":"","return_reason":"","return_time":""}]
             * logistics : null
             */

            private LogisticsBean logistics;
            private List<SkuBean> sku_info;
            private String name;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public LogisticsBean getLogistics() {
                return logistics;
            }

            public void setLogistics(LogisticsBean logistics) {
                this.logistics = logistics;
            }

            public List<SkuBean> getSku_info() {
                return sku_info;
            }

            public void setSku_info(List<SkuBean> sku_info) {
                this.sku_info = sku_info;
            }
        }
    }
}
