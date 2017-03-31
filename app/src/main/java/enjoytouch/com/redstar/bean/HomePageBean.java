package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/3.
 */
public class HomePageBean implements Serializable {


    private ErrorEntity error;
    private DataBean data;
    /**
     * data : {"banner":[{"id":"21","title":"友邦爆款","image":"http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg","url":"168","type":"5"}],"designer_recommend":[{"id":"443","sale_price":"199.00","name":"测试sku","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg"},{"id":"439","sale_price":"0.02","name":"海尔整体厨房罗马家园","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg"},{"id":"441","sale_price":"680.00","name":"多乐士抗甲醛五合一套装","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg"},{"id":"440","sale_price":"950.00","name":"多乐士抗甲醛全效套装 ","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg"}],"find":[{"id":"431","name":"瑞宝城市月光系列","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg"},{"id":"418","name":"好粉之家舒适简约风格卫客厅家具五件套","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg"},{"id":"416","name":"好粉之家美式简约风格卫生间组合使用","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg"},{"id":"410","name":"尼琪思丹妮落地灯ADA-0079","subtitle":"","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg"}],"brand_hall":[{"id":"7","name":"精品饰材","english_name":"","pic":"http://meiyu-api.uduoo.com/data/upload/category/20160701/57763010499a7.jpg","parentid":"0","type":"1"}],"style":[]}
     * status : ok
     */

    private String status;

    public ErrorEntity getError() {
        return error;
    }

    public void setError(ErrorEntity error) {
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
        /**
         * id : 21
         * title : 友邦爆款
         * image : http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg
         * url : 168
         * type : 5
         */

        private List<BannerBean> banner;
        /**
         * id : 443
         * sale_price : 199.00
         * name : 测试sku
         * subtitle :
         * cover_img : http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg
         */

        private List<DesignerRecommendBean> designer_recommend;
        /**
         * id : 431
         * name : 瑞宝城市月光系列
         * subtitle :
         * cover_img : http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg
         */

        private List<FindBean> find;
        /**
         * id : 7
         * name : 精品饰材
         * english_name :
         * pic : http://meiyu-api.uduoo.com/data/upload/category/20160701/57763010499a7.jpg
         * parentid : 0
         * type : 1
         */

        private List<BrandHallBean> brand_hall;

        private List<CreativeBean>creative;

        public List<CreativeBean> getCreative() {
            return creative;
        }

        public void setCreative(List<CreativeBean> creative) {
            this.creative = creative;
        }


        //        private List<Style2Bean> style;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<DesignerRecommendBean> getDesigner_recommend() {
            return designer_recommend;
        }

        public void setDesigner_recommend(List<DesignerRecommendBean> designer_recommend) {
            this.designer_recommend = designer_recommend;
        }

        public List<FindBean> getFind() {
            return find;
        }

        public void setFind(List<FindBean> find) {
            this.find = find;
        }

        public List<BrandHallBean> getBrand_hall() {
            return brand_hall;
        }

        public void setBrand_hall(List<BrandHallBean> brand_hall) {
            this.brand_hall = brand_hall;
        }
//        public List<Style2Bean> getStyle() {
//            return style;
//        }
//
//        public void setStyle(List<Style2Bean> style) {
//            this.style = style;
//        }
        public static class Style2Bean{
            private String id;
            private String style_name;
            private String english_name;
            private String cover_img;

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public String getEnglish_name() {
                return english_name;
            }

            public void setEnglish_name(String english_name) {
                this.english_name = english_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStyle_name() {
                return style_name;
            }

            public void setStyle_id(String style_name) {
                this.style_name = style_name;
            }

        }
        public static class BannerBean {
            private String id;
            private String title;
            private String image;
            private String url;
            private String type;
            private String shop_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }
        }

        public static class DesignerRecommendBean {
            private String id;
            private String sale_price;
            private String name;
            private String subtitle;
            private String cover_img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSale_price() {
                return sale_price;
            }

            public void setSale_price(String sale_price) {
                this.sale_price = sale_price;
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

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }
        }

        public static class FindBean {
            private String id;
            private String name;
            private String subtitle;
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

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }
        }

        public static class BrandHallBean {
            private String id;
            private String name;
            private String english_name;
            private String pic;
            private String parentid;
            private String type;

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

            public String getEnglish_name() {
                return english_name;
            }

            public void setEnglish_name(String english_name) {
                this.english_name = english_name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
