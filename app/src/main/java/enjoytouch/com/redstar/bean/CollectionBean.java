package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lzz on 2016/7/4.
 */
public class CollectionBean {

    /**
     * data : [{"id":"434","name":"开洋免漆木门187","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"1","remaind":494},{"id":"429","name":"爱舒爱之翼","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"3","remaind":500},{"id":"440","name":"多乐士抗甲醛全效套装 ","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"2","remaind":500},{"id":"430","name":"多乐士家易涂刷新服务","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"2","remaind":500},{"id":"428","name":"科勒花洒套装 K-99290T-4-CP","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"2","remaind":500},{"id":"432","name":"诺贝尔凯撒石代FR系列FR80583K","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"2","remaind":500},{"id":"436","name":"友邦扣板M1601","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"3","remaind":500},{"id":"437","name":"友邦照明P330LD-5","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":"","collect_cnt":"2","remaind":500}]
     * total : 8
     * status : ok
     */

    private String total;
    private String status;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    /**
     * id : 434
     * name : 开洋免漆木门187
     * cover_img : http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg
     * subtitle :
     * collect_cnt : 1
     * remaind : 494
     */

    private List<MyCollectionBean> data;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyCollectionBean> getData() {
        return data;
    }

    public void setData(List<MyCollectionBean> data) {
        this.data = data;
    }


}
