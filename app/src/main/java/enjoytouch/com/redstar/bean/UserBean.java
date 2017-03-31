package enjoytouch.com.redstar.bean;

/**
 * Created by Administrator on 2015/7/30.
 *
 * �用户实体类
 */
public class UserBean {

    private String status;
    private DataBean data;
    private ErrorBean error;


    public static class  DataBean {
        public String id;
        public String mobile;//手机
        public String token="";//登录凭证
        public String head_img;//头像
        public String nickname;//昵称
        public String sex; //1男 2女

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", token='" + token + '\'' +
                    ", head_img='" + head_img + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }
}
