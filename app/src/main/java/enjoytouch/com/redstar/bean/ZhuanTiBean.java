package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/22.
 */
public class ZhuanTiBean {

    /**
     * id : 2
     * cover_img : http://meiyu-api.uduoo.com2.jpg
     * name : 寻找喵星人
     * title : 寻找不一样的喵咪生活馆
     * cool_shop : [{"id":"611","name":"新中源陶瓷-红星美凯龙汶水店","title":"新中源陶瓷-红星美凯龙汶水店","cover_img":"http://meiyu-api.uduoo.com2.jpg"},{"id":"612","name":"顾家皮具-金盛家居普陀店","title":"顾家皮具-金盛家居普陀店","cover_img":"http://meiyu-api.uduoo.com1.jpg"}]
     */

    private DataBean data;
    /**
     * data : {"id":"2","cover_img":"http://meiyu-api.uduoo.com2.jpg","name":"寻找喵星人","title":"寻找不一样的喵咪生活馆","cool_shop":[{"id":"611","name":"新中源陶瓷-红星美凯龙汶水店","title":"新中源陶瓷-红星美凯龙汶水店","cover_img":"http://meiyu-api.uduoo.com2.jpg"},{"id":"612","name":"顾家皮具-金盛家居普陀店","title":"顾家皮具-金盛家居普陀店","cover_img":"http://meiyu-api.uduoo.com1.jpg"}]}
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
        public String is_collection;
        public String collect_cnt;
        /**
         * id : 611
         * name : 新中源陶瓷-红星美凯龙汶水店
         * title : 新中源陶瓷-红星美凯龙汶水店
         * cover_img : http://meiyu-api.uduoo.com2.jpg
         */

        private List<CoolShopBean> cool_shop;

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

        public List<CoolShopBean> getCool_shop() {
            return cool_shop;
        }

        public void setCool_shop(List<CoolShopBean> cool_shop) {
            this.cool_shop = cool_shop;
        }

        public static class CoolShopBean {
            private String id;
            private String name;
            private String title;
            private String cover_img;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
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
