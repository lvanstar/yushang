package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/6/20.
 */
public class InterestBean {

    /**
     * total : 3
     * data : [{"id":"1","name":"水舍酒店","title":"上海的独特空间酒店","cover_img":"http://meiyu-api.uduoo.com1.jpg","circle":"老码头","type":"1","shop_id":"613","label":[{"icon":"http://meiyu-api.uduoo.com2.jpg","name":"生活"},{"icon":"http://meiyu-api.uduoo.com3.jpg","name":"咖啡馆"}]}]
     * status : ok
     */

    private String total;
    private String status;
    private List<DataEntity> data;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 1
         * name : 水舍酒店
         * title : 上海的独特空间酒店
         * cover_img : http://meiyu-api.uduoo.com1.jpg
         * circle : 老码头
         * type : 1
         * shop_id : 613
         * label : [{"icon":"http://meiyu-api.uduoo.com2.jpg","name":"生活"},{"icon":"http://meiyu-api.uduoo.com3.jpg","name":"咖啡馆"}]
         */

        private String id;
        private String name;
        private String title;
        private String cover_img;
        private String circle;
        private String type;
        private String shop_id;
        private List<LabelEntity> label;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public void setCircle(String circle) {
            this.circle = circle;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public void setLabel(List<LabelEntity> label) {
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public String getCover_img() {
            return cover_img;
        }

        public String getCircle() {
            return circle;
        }

        public String getType() {
            return type;
        }

        public String getShop_id() {
            return shop_id;
        }

        public List<LabelEntity> getLabel() {
            return label;
        }

        public static class LabelEntity {
            /**
             * icon : http://meiyu-api.uduoo.com2.jpg
             * name : 生活
             */

            private String icon;
            private String name;

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public String getName() {
                return name;
            }
        }
    }
}
