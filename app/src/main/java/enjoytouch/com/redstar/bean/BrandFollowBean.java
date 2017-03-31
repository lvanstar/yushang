package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/6/30.
 */
public class BrandFollowBean {

    private String status;
    private String total;
    private ErrorBean error;
    private List<BrandBean>data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<BrandBean> getData() {
        return data;
    }

    public void setData(List<BrandBean> data) {
        this.data = data;
    }
}
