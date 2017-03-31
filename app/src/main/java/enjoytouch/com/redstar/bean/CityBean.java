package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/27.
 */
public class CityBean {

    /**
     * data : [{"id":"35","name":"上海市","pingyin":"shanghai"}]
     * status : ok
     */

    private String status;
    /**
     * id : 35
     * name : 上海市
     * pingyin : shanghai
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String pingyin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPingyin() {
            return pingyin;
        }

        public void setPingyin(String pingyin) {
            this.pingyin = pingyin;
        }
        public char getInitial(){
            return (char) (pingyin.charAt(0)-32);
        }
    }

    private ErrorEntity error;
    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public ErrorEntity getError() {
        return error;
    }
    public static class ErrorEntity {
        /**
         * code : 1012
         * error : WRONG_PASSWORD
         * message : 密码错误!
         */

        private String code;
        private String error;
        private String message;

        public void setCode(String code) {
            this.code = code;
        }

        public void setError(String error) {
            this.error = error;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

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
