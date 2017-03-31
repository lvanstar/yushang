package enjoytouch.com.redstar.bean;

/**
 * Created by duan on 2016/6/22.
 */
public class QuChuPhotoBean {

    /**
     * id : 3
     * cover_img : http://meiyu-api.uduoo.com3.jpg
     * name : 文艺青年的"性冷淡"生活
     * title : 厌倦了奢华和浮夸，这里的简单生活更适合你
     * content : 测试
     * collect_cnt : 1K
     */

    private DataBean data;
    /**
     * data : {"id":"3","cover_img":"http://meiyu-api.uduoo.com3.jpg","name":"文艺青年的\"性冷淡\"生活","title":"厌倦了奢华和浮夸，这里的简单生活更适合你","content":"测试","collect_cnt":"1K"}
     * status : ok
     */

    private String status;

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
        private String cover_img;
        private String name;
        private String title;
        private String content;
        private String is_collection;
        private String collect_cnt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(String is_collection) {
            this.is_collection = is_collection;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCollect_cnt() {
            return collect_cnt;
        }

        public void setCollect_cnt(String collect_cnt) {
            this.collect_cnt = collect_cnt;
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
