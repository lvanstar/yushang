package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class BrandSearchBean {

    public String status;

    public List<DataBean> data;



    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public String id;
        public String name;
        public String type;
        public String parentid;
    }

    public ErrorEntity error;


    public ErrorEntity getError() {
        return error;
    }
    public static class ErrorEntity {
        /**
         * code : 1012
         * error : WRONG_PASSWORD
         * message : 密码错误!
         */

        public String code;
        public String error;
        public String message;



        @Override
        public String toString() {
            return "ErrorEntity{" +
                    "code='" + code + '\'' +
                    ", error='" + error + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
