package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/21.
 */
public class QuChuDetail {


    private ErrorEntity error;
    /**
     * id : 613
     * name : 顾家皮具-红星美凯龙浦江店
     * title : 顾家皮具-红星美凯龙浦江店
     * cover_img : http://meiyu-api.uduoo.com1.jpg
     * tel : -
     * content :
     * business_hours :
     * address : 闵行区浦星公路1969弄一楼A8082顾家家居
     * lat : 31.005592
     * lng : 121.513512
     * characteristic : ["新古典","色彩","下午茶"]
     * label : [{"icon":"http://meiyu-api.uduoo.com2.jpg","name":"生活"},{"icon":"http://meiyu-api.uduoo.com3.jpg","name":"咖啡馆"}]
     * fun_place_id : 1
     * fun_place_type : 1
     * fun_place_collect_cnt : 0
     * pic : [{"pic":"http://meiyu-api.uduoo.com1.jpg","height":"300","width":"250"}]
     * comment_cnt : 0
     * comment : []
     */

    private DataBean data;
    /**
     * data : {"id":"613","name":"顾家皮具-红星美凯龙浦江店","title":"顾家皮具-红星美凯龙浦江店","cover_img":"http://meiyu-api.uduoo.com1.jpg","tel":"-","content":"","business_hours":"","address":"闵行区浦星公路1969弄一楼A8082顾家家居","lat":"31.005592","lng":"121.513512","characteristic":["新古典","色彩","下午茶"],"label":[{"icon":"http://meiyu-api.uduoo.com2.jpg","name":"生活"},{"icon":"http://meiyu-api.uduoo.com3.jpg","name":"咖啡馆"}],"fun_place_id":"1","fun_place_type":"1","fun_place_collect_cnt":"0","pic":[{"pic":"http://meiyu-api.uduoo.com1.jpg","height":"300","width":"250"}],"comment_cnt":"0","comment":[]}
     * status : ok
     */

    private String status;

    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public ErrorEntity getError() {
        return error;
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

    public static class DataBean {
        private String id;
        private String name;
        private String title;
        private String cover_img;
        private String tel;
        private String content;
        private String business_hours;
        private String address;
        private String lat;
        private String lng;
        private String fun_place_id;
        private String fun_place_type;
        private String fun_place_collect_cnt;
        private String comment_cnt;
        private String is_collection;
        private List<String> characteristic;
        /**
         * icon : http://meiyu-api.uduoo.com2.jpg
         * name : 生活
         */

        private List<LabelBean> label;
        /**
         * pic : http://meiyu-api.uduoo.com1.jpg
         * height : 300
         * width : 250
         */

        private List<PicBean> pic;
        private List<ContentBean> comment;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBusiness_hours() {
            return business_hours;
        }

        public void setBusiness_hours(String business_hours) {
            this.business_hours = business_hours;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getFun_place_id() {
            return fun_place_id;
        }

        public void setFun_place_id(String fun_place_id) {
            this.fun_place_id = fun_place_id;
        }

        public String getFun_place_type() {
            return fun_place_type;
        }

        public void setFun_place_type(String fun_place_type) {
            this.fun_place_type = fun_place_type;
        }

        public String getFun_place_collect_cnt() {
            return fun_place_collect_cnt;
        }

        public void setFun_place_collect_cnt(String fun_place_collect_cnt) {
            this.fun_place_collect_cnt = fun_place_collect_cnt;
        }

        public String getComment_cnt() {
            return comment_cnt;
        }

        public void setComment_cnt(String comment_cnt) {
            this.comment_cnt = comment_cnt;
        }

        public List<String> getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(List<String> characteristic) {
            this.characteristic = characteristic;
        }

        public List<LabelBean> getLabel() {
            return label;
        }

        public void setLabel(List<LabelBean> label) {
            this.label = label;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public List<ContentBean> getComment() {
            return comment;
        }

        public void setComment(List<ContentBean> comment) {
            this.comment = comment;
        }

        public static class LabelBean {
            private String icon;
            private String name;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class PicBean {
            private String image;
            private String height;
            private String width;

            public String getPic() {
                return image;
            }

            public void setPic(String image) {
                this.image = image;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }
        }
    }
}
