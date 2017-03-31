package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/24.
 */
public class FundTypeBean {

    /**
     * data : [{"id":"0","name":"全部品类","category_pic":"","desc":"","parentid":"0","childrend":[{"id":"0","name":"全部品类","category_pic":"","desc":"","parentid":"0","childrend":[]},{"id":"22","name":"淋浴房","category_pic":"http://meiyu-api.uduoo.com/data/upload/category/20160203/56b17d9c844b6.png","desc":"","parentid":"2","childrend":[]}]},{"id":"2","name":"厨房卫浴","category_pic":"http://meiyu-api.uduoo.com/data/upload/category/20160104/568a6ef32d5a2.jpg","desc":null,"parentid":"0","childrend":[{"id":"22","name":"淋浴房","category_pic":"http://meiyu-api.uduoo.com/data/upload/category/20160203/56b17d9c844b6.png","desc":"","parentid":"2","childrend":[]}]}]
     * status : ok
     */

    private String status;
    /**
     * id : 0
     * name : 全部品类
     * category_pic :
     * desc :
     * parentid : 0
     * childrend : [{"id":"0","name":"全部品类","category_pic":"","desc":"","parentid":"0","childrend":[]},{"id":"22","name":"淋浴房","category_pic":"http://meiyu-api.uduoo.com/data/upload/category/20160203/56b17d9c844b6.png","desc":"","parentid":"2","childrend":[]}]
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
        private String category_pic;
        private String desc;
        private String parentid;
        /**
         * id : 0
         * name : 全部品类
         * category_pic :
         * desc :
         * parentid : 0
         * childrend : []
         */

        private List<ChildrendBean> childrend;

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

        public String getCategory_pic() {
            return category_pic;
        }

        public void setCategory_pic(String category_pic) {
            this.category_pic = category_pic;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public List<ChildrendBean> getChildrend() {
            return childrend;
        }

        public void setChildrend(List<ChildrendBean> childrend) {
            this.childrend = childrend;
        }

        public static class ChildrendBean {
            private String id;
            private String name;
            private String category_pic;
            private String desc;
            private String parentid;
            private List<?> childrend;

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

            public String getCategory_pic() {
                return category_pic;
            }

            public void setCategory_pic(String category_pic) {
                this.category_pic = category_pic;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public List<?> getChildrend() {
                return childrend;
            }

            public void setChildrend(List<?> childrend) {
                this.childrend = childrend;
            }
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
