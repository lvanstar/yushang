package enjoytouch.com.redstar.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/9.
 */
public class OrderBean implements Serializable {

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

    public static class DataEntity implements Serializable{
        private String order_id;
        private String order_no;
        private String title;
        private String real_payment;
        private String business;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getReal_payment() {
            return real_payment;
        }

        public void setReal_payment(String real_payment) {
            this.real_payment = real_payment;
        }
    }
}
