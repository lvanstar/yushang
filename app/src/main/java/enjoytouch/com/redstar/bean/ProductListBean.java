package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/7/4.
 */
public class ProductListBean {

    /**
     * data : [{"id":"2","cover_img":"/data/upload/creative/20160705/577b1fb750b9c.jpg","product":[]}]
     * total : 1
     * status : ok
     */

    private String total;
    private String status;
    private ErrorBean error;
    /**
     * id : 2
     * cover_img : /data/upload/creative/20160705/577b1fb750b9c.jpg
     * product : []
     */

    private List<DataBean> data;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
}
