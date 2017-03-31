package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ShopDetailsBean {

    /**
     * id : 443
     * name : 测试sku
     * subtitle :
     * desc : 111
     * collect_cnt : 1
     * sale_price : 199.00
     * refund_enable : 1
     * is_collect : 0
     * designers : []
     * brand : [{"id":"367","name":"海尔 ","brand_des":"&lt;p&gt;&lt;img src=&quot;/data/upload/editor/image/20160229/1456717833971635.jpg&quot; title=&quot;1456717833971635.jpg&quot; alt=&quot;海尔.jpg&quot; style=&quot;width:100%&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;/data/upload/editor/image/20160229/1456717841279365.jpg&quot; title=&quot;1456717841279365.jpg&quot; alt=&quot;haier2.jpg&quot; style=&quot;width:100%&quot;/&gt;&lt;/p&gt;&lt;p&gt;&amp;nbsp; &amp;nbsp;青岛海尔厨房设施有限公司是在1997年成立的。在这十多年来，海尔整体厨房致力于为全球的消费者提供厨房居住生活解决方案。&lt;/p&gt;&lt;p&gt;&amp;nbsp; &amp;nbsp;海尔整体厨房为了提升客户的家居生活品味，最早提出了\u201c厨电一体化\u201d的设计理念，并且在2002年就正式投入了世界一流的数字化厨房工厂。目前，海尔整体厨房已经享有了\u201c中国厨卫行业领头雁\u201d的盛誉。 &amp;nbsp;&lt;/p&gt;","logo":"http://meiyu-api.uduoo.com/data/upload/brand/20160229/56d41f984174b.jpg"}]
     * pic : [{"image":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","width":323,"height":220}]
     * notice_sale :
     * remaind : 48
     */

    private DataBean data;

    /**
     * data : {"id":"443","name":"测试sku","subtitle":"","desc":"111","collect_cnt":"1","sale_price":"199.00","refund_enable":"1","is_collect":"0","designers":[],"brand":[{"id":"367","name":"海尔 ","brand_des":"&lt;p&gt;&lt;img src=&quot;/data/upload/editor/image/20160229/1456717833971635.jpg&quot; title=&quot;1456717833971635.jpg&quot; alt=&quot;海尔.jpg&quot; style=&quot;width:100%&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;/data/upload/editor/image/20160229/1456717841279365.jpg&quot; title=&quot;1456717841279365.jpg&quot; alt=&quot;haier2.jpg&quot; style=&quot;width:100%&quot;/&gt;&lt;/p&gt;&lt;p&gt;&amp;nbsp; &amp;nbsp;青岛海尔厨房设施有限公司是在1997年成立的。在这十多年来，海尔整体厨房致力于为全球的消费者提供厨房居住生活解决方案。&lt;/p&gt;&lt;p&gt;&amp;nbsp; &amp;nbsp;海尔整体厨房为了提升客户的家居生活品味，最早提出了\u201c厨电一体化\u201d的设计理念，并且在2002年就正式投入了世界一流的数字化厨房工厂。目前，海尔整体厨房已经享有了\u201c中国厨卫行业领头雁\u201d的盛誉。 &amp;nbsp;&lt;/p&gt;","logo":"http://meiyu-api.uduoo.com/data/upload/brand/20160229/56d41f984174b.jpg"}],"pic":[{"image":"http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg","width":323,"height":220}],"notice_sale":"","remaind":48}
     * status : ok
     */


    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    private ErrorBean error;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataBean {
        private String id;
        private String name;
        private String subtitle;
        private String desc;
        private String collect_cnt;
        private String sale_price;
        private String refund_enable;
        private String is_collect;
        private String notice_sale;
        private int remaind;
        private String content;
        private String product_id;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private List<designersBean> designers;
        /**
         * id : 367
         * name : 海尔
         * brand_des : &lt;p&gt;&lt;img src=&quot;/data/upload/editor/image/20160229/1456717833971635.jpg&quot; title=&quot;1456717833971635.jpg&quot; alt=&quot;海尔.jpg&quot; style=&quot;width:100%&quot;/&gt;&lt;/p&gt;&lt;p&gt;&lt;img src=&quot;/data/upload/editor/image/20160229/1456717841279365.jpg&quot; title=&quot;1456717841279365.jpg&quot; alt=&quot;haier2.jpg&quot; style=&quot;width:100%&quot;/&gt;&lt;/p&gt;&lt;p&gt;&amp;nbsp; &amp;nbsp;青岛海尔厨房设施有限公司是在1997年成立的。在这十多年来，海尔整体厨房致力于为全球的消费者提供厨房居住生活解决方案。&lt;/p&gt;&lt;p&gt;&amp;nbsp; &amp;nbsp;海尔整体厨房为了提升客户的家居生活品味，最早提出了“厨电一体化”的设计理念，并且在2002年就正式投入了世界一流的数字化厨房工厂。目前，海尔整体厨房已经享有了“中国厨卫行业领头雁”的盛誉。 &amp;nbsp;&lt;/p&gt;
         * logo : http://meiyu-api.uduoo.com/data/upload/brand/20160229/56d41f984174b.jpg
         */

        private List<BrandBean> brand;
        /**
         * image : http://meiyu-api.uduoo.com/data/upload/product/20160413/570e10f91e168.jpg
         * width : 323
         * height : 220
         */

        private List<PicBean> pic;


        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCollect_cnt() {
            return collect_cnt;
        }

        public void setCollect_cnt(String collect_cnt) {
            this.collect_cnt = collect_cnt;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getRefund_enable() {
            return refund_enable;
        }

        public void setRefund_enable(String refund_enable) {
            this.refund_enable = refund_enable;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getNotice_sale() {
            return notice_sale;
        }

        public void setNotice_sale(String notice_sale) {
            this.notice_sale = notice_sale;
        }

        public int getRemaind() {
            return remaind;
        }

        public void setRemaind(int remaind) {
            this.remaind = remaind;
        }

        public List<designersBean> getDesigners() {
            return designers;
        }

        public void setDesigners(List<designersBean> designers) {
            this.designers = designers;
        }

        public List<BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<BrandBean> brand) {
            this.brand = brand;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class BrandBean {
            private String id;
            private String name;
            private String sub_name;
            private String logo;

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

            public String getSub_name() {
                return sub_name;
            }

            public void setSub_name(String brand_des) {
                this.sub_name = brand_des;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

        public static class PicBean {
            private String image;
            private int width;
            private int height;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        public static class designersBean {
            private String id;
            private String name;
            private String snap;

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
        }
    }
}
