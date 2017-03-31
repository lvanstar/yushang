package enjoytouch.com.redstar.bean;

/**
 * Created by lizhaozhao on 16/6/28.
 */
public class SkuBean {

    private String id;
    private String name;
    private String cover_img;
    private String sale_price;
    private String amount;
    private String refund_enable;
    private String refund_status;
    private String return_reason;
    private String return_time;
    private String sku_id;

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
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

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setRefund_enable(String refund_enable) {
        this.refund_enable = refund_enable;
    }


    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }

    public void setReturn_reason(String return_reason) {
        this.return_reason = return_reason;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
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

    public String getSale_price() {
        return sale_price;
    }

    public String getAmount() {
        return amount;
    }

    public String getRefund_enable() {
        return refund_enable;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public String getReturn_reason() {
        return return_reason;
    }

    public String getReturn_time() {
        return return_time;
    }
}
