package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/29.
 */
public class BrandDetailBean {


    private List<BrandBean> data;
    private String status;
    private String total;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

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

    public List<BrandBean> getData() {
        return data;
    }

    public void setData(List<BrandBean> data) {
        this.data = data;
    }



}
