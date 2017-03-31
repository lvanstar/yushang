package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/7/1.
 */
public class ContentListBean {
    private String status;
    private String total;
    private ErrorBean error;
    private List<ContentBean>data;

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

    public List<ContentBean> getData() {
        return data;
    }

    public void setData(List<ContentBean> data) {
        this.data = data;
    }
}
