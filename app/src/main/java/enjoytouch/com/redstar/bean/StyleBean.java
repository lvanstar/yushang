package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/24.
 */
public class StyleBean {

    /**
     * data : [{"id":"11","style_name":"简约欧式"}]
     * status : ok
     */

    private String status;
    /**
     * id : 11
     * style_name : 简约欧式
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
        private String style_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStyle_name() {
            return style_name;
        }

        public void setStyle_name(String style_name) {
            this.style_name = style_name;
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
