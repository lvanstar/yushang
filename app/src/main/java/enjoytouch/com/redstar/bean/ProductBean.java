package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhaozhao on 16/6/23.
 */
public class ProductBean implements Serializable{

    /**
     * data : {"product":[{"name":"寓上","sku_info":[{"id":"443","name":"测试数据","price":"199.00","amount":1,"pic":"http://meiyu-api.uduoo.com/data/upload/sku/20160413/570dfa0c80b84.jpg"}]}],"total_price":199,"pay_price":213,"shipment":14,"address":{"id":"30","name":"蔡振喜","city_id":"35","tel":"15000059840","zip_code":"236200","province_name":"上海市","city_name":"上海市","area_name":"宝山区","address":"啊董事长徐和规范市场经济"}}
     * status : ok
     */

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

    public static class DataEntity implements Serializable{
        /**
         * product : [{"name":"寓上","sku_info":[{"id":"443","name":"测试数据","price":"199.00","amount":1,"pic":"http://meiyu-api.uduoo.com/data/upload/sku/20160413/570dfa0c80b84.jpg"}]}]
         * total_price : 199
         * pay_price : 213
         * shipment : 14
         * address : {"id":"30","name":"蔡振喜","city_id":"35","tel":"15000059840","zip_code":"236200","province_name":"上海市","city_name":"上海市","area_name":"宝山区","address":"啊董事长徐和规范市场经济"}
         */

        private String total_price;
        private String pay_price;
        private String shipment;
        private String discounted_price;
        private UserAddressBean address;
        private List<ProductEntity> product;

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public void setShipment(String shipment) {
            this.shipment = shipment;
        }

        public String getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(String discounted_price) {
            this.discounted_price = discounted_price;
        }

        public void setProduct(List<ProductEntity> product) {
            this.product = product;
        }

        public String getTotal_price() {
            return total_price;
        }

        public String getPay_price() {
            return pay_price;
        }

        public String getShipment() {
            return shipment;
        }

        public UserAddressBean getAddress() {
            return address;
        }

        public void setAddress(UserAddressBean address) {
            this.address = address;
        }

        public List<ProductEntity> getProduct() {
            return product;
        }



        public static class ProductEntity implements Serializable{
            /**
             * name : 寓上
             * sku_info : [{"id":"443","name":"测试数据","price":"199.00","amount":1,"pic":"http://meiyu-api.uduoo.com/data/upload/sku/20160413/570dfa0c80b84.jpg"}]
             */

            private String name;
            private List<SkuInfoBean> sku_info;

            public void setName(String name) {
                this.name = name;
            }


            public String getName() {
                return name;
            }

            public List<SkuInfoBean> getSku_info() {
                return sku_info;
            }

            public void setSku_info(List<SkuInfoBean> sku_info) {
                this.sku_info = sku_info;
            }
        }
    }
}
