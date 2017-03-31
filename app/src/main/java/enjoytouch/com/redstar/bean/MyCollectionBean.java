package enjoytouch.com.redstar.bean;

/**
 * Created by lzz on 2016/7/4.
 */
public class MyCollectionBean {

    private String id;
    private String name;
    private String cover_img;
    private String subtitle;
    private String collect_cnt;
    private String sale_price;
    private String is_collect;
    private int remaind;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setCollect_cnt(String collect_cnt) {
        this.collect_cnt = collect_cnt;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public void setRemaind(int remaind) {
        this.remaind = remaind;
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

    public String getSubtitle() {
        return subtitle;
    }

    public String getCollect_cnt() {
        return collect_cnt;
    }

    public String getSale_price() {
        return sale_price;
    }

    public int getRemaind() {
        return remaind;
    }
}
