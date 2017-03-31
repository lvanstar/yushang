package enjoytouch.com.redstar.bean;

/**
 * Created by Administrator on 2015/10/14.
 */
public class WXPayBean {


    private String status;
    private ErrorBean error;
    private DataEntity data;

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

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public class DataEntity{
        public String appid;
        public String partnerid;
        public String prepayid;
        public String noncestr;
        public String Sign;
        public String timestamp;
        public String sign;

        @Override
        public String toString() {
            return "WXPayBean{" +
                    "appid='" + appid + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", Sign='" + Sign + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }



}
