package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/7/12.
 */
public class LogisticsListBean {

    private String status;
    private ErrorBean error;
    private List<DataEntity>data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity{
        private String accept_address;
        private String remark;
        private String accept_time;

        public String getAccept_address() {
            return accept_address;
        }

        public void setAccept_address(String accept_address) {
            this.accept_address = accept_address;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getAccept_time() {
            return accept_time;
        }

        public void setAccept_time(String accept_time) {
            this.accept_time = accept_time;
        }
    }
}
