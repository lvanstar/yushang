package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/7/5.
 */
public class TypeBean {

    /**
     * data : [{"id":"123","name":"厨房","parentid":"7"}]
     * status : ok
     */

    private String status;
    private ErrorBean Error;
    /**
     * id : 123
     * name : 厨房
     * parentid : 7
     */

    private List<DataBean> data;

    public ErrorBean getError() {
        return Error;
    }

    public void setError(ErrorBean error) {
        Error = error;
    }

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
        private String parentid;

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

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }
    }
}
