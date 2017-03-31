package enjoytouch.com.redstar.bean;

/**
 * Created by lzz on 2016/6/30.
 */
public class StylistBean {


    /**
     * id : 6
     * name : T先生
     * snap : http://meiyu-api.uduoo.com/data/upload/designers_snap/20160630/5774c7d9e530a.jpg
     * background : http://meiyu-api.uduoo.com/data/upload/designers_background/20160630//data/upload/designers_background/20160630//data/upload/designers_background/20160630//data/upload/designers_background/20160630/5774c7db5e5c9.jpg
     * introduction : 华人首席逼格体验师，没有之一
     * description : 华人首席逼格体验师，没有之一；

     一个集帅气、美貌，智慧于一身的男纸；

     你，值得崇拜！


     * brand : {"id":"368","logo":"http://meiyu-api.uduoo.com/data/upload/brand/20160323/56f25ee6b1eb8.jpg","name":"产品补差","brand_des":""}
     */

    private DataBean data;
    /**
     * data : {"id":"6","name":"T先生","snap":"http://meiyu-api.uduoo.com/data/upload/designers_snap/20160630/5774c7d9e530a.jpg","background":"http://meiyu-api.uduoo.com/data/upload/designers_background/20160630//data/upload/designers_background/20160630//data/upload/designers_background/20160630//data/upload/designers_background/20160630/5774c7db5e5c9.jpg","introduction":"华人首席逼格体验师，没有之一","description":"华人首席逼格体验师，没有之一；\r\n\r\n一个集帅气、美貌，智慧于一身的男纸；\r\n\r\n你，值得崇拜！\r\n\r\n","brand":{"id":"368","logo":"http://meiyu-api.uduoo.com/data/upload/brand/20160323/56f25ee6b1eb8.jpg","name":"产品补差","brand_des":""}}
     * status : ok
     */

    private String status;

    private ErrorBean error;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String snap;
        private String background;
        private String introduction;
        private String description;
        /**
         * id : 368
         * logo : http://meiyu-api.uduoo.com/data/upload/brand/20160323/56f25ee6b1eb8.jpg
         * name : 产品补差
         * brand_des :
         */

        private BrandBean brand;

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

        public String getSnap() {
            return snap;
        }

        public void setSnap(String snap) {
            this.snap = snap;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BrandBean getBrand() {
            return brand;
        }

        public void setBrand(BrandBean brand) {
            this.brand = brand;
        }

        public static class BrandBean {
            private String id;
            private String logo;
            private String name;
            private String brand_des;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBrand_des() {
                return brand_des;
            }

            public void setBrand_des(String brand_des) {
                this.brand_des = brand_des;
            }
        }
    }
}
