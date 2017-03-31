package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/6/20.
 */
public class HiGhestBean {

    /**
     * total : 4
     * data : [{"id":"443","name":"测试sku","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","subtitle":null,"collect_cnt":"1","sale_price":"199.00","remaind":2},{"id":"435","name":"瑞宝费罗拉系列6卷一组","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160229/56d42d6f405cf.jpg","subtitle":null,"collect_cnt":"1","sale_price":"3150.00","remaind":16},{"id":"441","name":"多乐士抗甲醛五合一套装","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160304/56d8fdc8b7130.jpg","subtitle":null,"collect_cnt":"1","sale_price":"680.00","remaind":100},{"id":"434","name":"开洋免漆木门187","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160229/56d43094053b6.jpg","subtitle":null,"collect_cnt":"1","sale_price":"799.00","remaind":20}]
     * status : ok
     */

    private String total;
    private String status;
    private String car_status;
    private List<MyCollectionBean> data;
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

    public void setData(List<MyCollectionBean> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }


    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    public List<MyCollectionBean> getData() {
        return data;
    }


}
