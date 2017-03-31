package enjoytouch.com.redstar.bean;

/**
 * Created by duan on 2016/6/22.
 */
public class CollectBean {


    private DataEntity data;
    private String status;
    private ErrorBean error;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

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

    public static class DataEntity{
        private String collect_cnt;
        private String collect_status;
        private String status;
        private String follow_cnt;

        public String getFollow_cnt() {
            return follow_cnt;
        }

        public void setFollow_cnt(String follow_cnt) {
            this.follow_cnt = follow_cnt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getCollect_cnt() {
            return collect_cnt;
        }

        public void setCollect_cnt(String collect_cnt) {
            this.collect_cnt = collect_cnt;
        }

        public String getCollect_status() {
            return collect_status;
        }

        public void setCollect_status(String collect_status) {
            this.collect_status = collect_status;
        }
    }
}
