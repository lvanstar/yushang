package enjoytouch.com.redstar.bean;

/**
 * Created by lizhaozhao on 16/6/22.
 */
public class ShopCarBean {
    /**
     * data : {"product_status":"1","id":"434","name":"开洋免漆木门187","remaind":20,"cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160229/56d43094053b6.jpg","amount":4,"stock_statas":"3"}
     * status : ok
     */

    private DataEntity data;
    private String status;
    private ErrorBean  error;


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
         * product_status : 1
         * id : 434
         * name : 开洋免漆木门187
         * remaind : 20
         * cover_img : http://meiyu-api.uduoo.com/data/upload/product/20160229/56d43094053b6.jpg
         * amount : 4
         * stock_statas : 3
         */

        private String product_status;
        private String id;
        private String name;
        private int remaind;
        private String cover_img;
        private int amount;
        private String stock_statas;
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

        public void setRemaind(int remaind) {
            this.remaind = remaind;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setStock_statas(String stock_statas) {
            this.stock_statas = stock_statas;
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

        public int getRemaind() {
            return remaind;
        }

        public String getCover_img() {
            return cover_img;
        }

        public int getAmount() {
            return amount;
        }

        public String getStock_statas() {
            return stock_statas;
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
    }
}
