package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by lizhaozhao on 16/8/15.
 */
public class CreativeBean {

    private String id;
    private String cover_img;
    private List<productBean> product;

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

    public List<productBean> getProduct() {
        return product;
    }

    public void setProduct(List<productBean> product) {
        this.product = product;
    }

    public static class productBean{
        private String id;
        private String name;
        private String cover_img;
        private String subtitle;
        private String type;

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

        public String getSubtitle() {
            return subtitle;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }
}
