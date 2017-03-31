package enjoytouch.com.redstar.bean;

import java.io.Serializable;

/**
 * Created by lizhaozhao on 16/6/23.
 */
public class SkuInfoBean implements Serializable{
    /**
     * id : 443
     * name : 测试数据
     * price : 199.00
     * amount : 1
     * pic : http://meiyu-api.uduoo.com/data/upload/sku/20160413/570dfa0c80b84.jpg
     */

    private String id;
    private String name;
    private String sale_price;
    private int amount;
    private String cover_img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }
}
