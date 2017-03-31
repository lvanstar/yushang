package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duan on 2016/6/12.
 */
public class ShoppingCartBean implements Serializable{
    /**
     * data : [{"product_status":"1","id":"443","name":"测试sku","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","amount":"2","stock_status":"3"}]
     * status : ok
     */

    private String status;
    private List<DataEntity> data;
    private ErrorBean error;


    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * product_status : 1
         * id : 443
         * name : 测试sku
         * cover_img : http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg
         * amount : 2
         * stock_status : 3
         */

        public boolean select=false;
        private String product_status;
        private String id;
        private String name;
        private String cover_img;
        private int amount;
        private String stock_status;
        private String sale_price;
        private String stock_notice;

        public void setProduct_status(String product_status) {
            this.product_status = product_status;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setStock_status(String stock_status) {
            this.stock_status = stock_status;
        }

        public String getProduct_status() {
            return product_status;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCover_img() {
            return cover_img;
        }

        public int getAmount() {
            return amount;
        }

        public String getStock_status() {
            return stock_status;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getStock_notice() {
            return stock_notice;
        }

        public void setStock_notice(String stock_notice) {
            this.stock_notice = stock_notice;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "select=" + select +
                    ", product_status='" + product_status + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover_img='" + cover_img + '\'' +
                    ", amount=" + amount +
                    ", stock_status='" + stock_status + '\'' +
                    ", sale_price='" + sale_price + '\'' +
                    ", stock_notice='" + stock_notice + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShoppingCartBean{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
