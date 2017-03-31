package enjoytouch.com.redstar.bean;

/**
 * Created by lizhaozhao on 16/6/16.
 *
 * 城市定位数据
 */
public class LocateCityBean {

    private String status;
    private ErrorBean error;
    private DataBean data;


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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String id;
        private String in_service;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIn_service() {
            return in_service;
        }

        public void setIn_service(String in_service) {
            this.in_service = in_service;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", in_service='" + in_service + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
