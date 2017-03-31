package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/6/29.
 */
public class ShopTypeBean {
    /**
     * data : ["餐厅","床上用品","服装","饰品","古董","家具","酒店","咖啡馆","书店","下午茶","综合"]
     * status : ok
     */

    private String status;
    private List<String> data;
    private ErrorBean error;
    public boolean select=false;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getData() {
        return data;
    }
}
