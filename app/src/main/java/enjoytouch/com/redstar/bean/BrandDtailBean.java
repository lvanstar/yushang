package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by duan on 2016/6/30.
 */
public class BrandDtailBean {

    private DataBean data;
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

    public static class DataBean implements Serializable{
        private String id;
        private String name;
        private String logo;
        private String sub_name;
        private String brand_des;
        private String follow_cnt;
        private FeelBean feel;
        private int follow;
        private List<String> tag_desc;
        private List<PicBean> pic;
        private List<DesignersBean> designers;
        private List<ShopBean> shop;
        private List<ProductBean> product;
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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSub_name() {
            return sub_name;
        }

        public void setSub_name(String sub_name) {
            this.sub_name = sub_name;
        }

        public String getBrand_des() {
            return brand_des;
        }

        public void setBrand_des(String brand_des) {
            this.brand_des = brand_des;
        }

        public String getFollow_cnt() {
            return follow_cnt;
        }

        public void setFollow_cnt(String follow_cnt) {
            this.follow_cnt = follow_cnt;
        }

        public FeelBean getFeel() {
            return feel;
        }

        public void setFeel(FeelBean feel) {
            this.feel = feel;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public List<String> getTag_desc() {
            return tag_desc;
        }

        public void setTag_desc(List<String> tag_desc) {
            this.tag_desc = tag_desc;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public List<DesignersBean> getDesigners() {
            return designers;
        }

        public void setDesigners(List<DesignersBean> designers) {
            this.designers = designers;
        }

        public List<ShopBean> getShop() {
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }
        public static class DesignersBean{
            private  String name;
            private String snap;
            private String id;
            private String introduction;
            private String description;

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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }
        }
        public static class ProductBean{
            private int id;
            private String name;
            private String cover_img;
            private String subtitle;
            private String collect_cnt;
            private String sale_price;
            private String type;
            private String is_collect;

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCollect_cnt() {
                return collect_cnt;
            }

            public void setCollect_cnt(String collect_cnt) {
                this.collect_cnt = collect_cnt;
            }

            public void setIs_collect(String is_collect) {
                this.is_collect = is_collect;
            }

            public String getIs_collect() {
                return is_collect;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public void setSale_price(String sale_price) {
                this.sale_price = sale_price;
            }

            public String getSale_price() {
                return sale_price;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public String getType() {
                return type;
            }
        }
        public static class FeelBean {
            private String total;
            private List<dataBean> data;

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public List<dataBean> getData() {
                return data;
            }
            public void setData(List<dataBean> data) {
                this.data = data;
            }
            public static class dataBean{
                private String id;
                private String feel;
                private String head_img;
                private String nickname;
                private String comment;
                private List<String> pic;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getFeel() {
                    return feel;
                }

                public void setFeel(String feel) {
                    this.feel = feel;
                }

                public String getHead_img() {
                    return head_img;
                }

                public void setHead_img(String head_img) {
                    this.head_img = head_img;
                }

                public List<String> getPic() {
                    return pic;
                }

                public void setPic(List<String> pic) {
                    this.pic = pic;
                }

                public String getComment() {
                    return comment;
                }

                public void setComment(String comment) {
                    this.comment = comment;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }
            }
        }

        public static class PicBean {
            private String img;
            private String width;
            private String height;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }
        }
    }
}
