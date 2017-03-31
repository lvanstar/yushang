package enjoytouch.com.redstar.bean;

/**
 * Created by Administrator on 2016/6/27.
 */
public class UserUpdateBean {

    /**
     * id : 1691
     * mobile : 18851538929
     * token : c128493c84ec15bedffd823debd04814
     * head_img : http://meiyu-api.uduoo.com/default_women.png
     * nickname : 188****8929
     * sex : 2
     */

    private DataBean data;
    /**
     * data : {"id":"1691","mobile":"18851538929","token":"c128493c84ec15bedffd823debd04814","head_img":"http://meiyu-api.uduoo.com/default_women.png","nickname":"188****8929","sex":"2"}
     * status : ok
     */

    private String status;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        private String id;
        private String mobile;
        private String token;
        private String head_img;
        private String nickname;
        private String sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
