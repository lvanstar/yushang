package enjoytouch.com.redstar.bean;

import java.util.List;

/**
 * Created by duan on 2016/6/23.
 */
public class FundListBean {
    private ErrorEntity error;
    /**
     * total : 14
     * data : [{"id":"420","name":"顾家爱舒仕沙发3人位+贵妃躺","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg","subtitle":"","collect_cnt":"1"},{"id":"431","name":"瑞宝城市月光系列","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160229/56d42c97f3fed.jpg","subtitle":"","collect_cnt":"1"},{"id":"422","name":"顾家爱舒仕沙发套餐","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160302/56d64e0dd67c4.jpg","subtitle":"","collect_cnt":"1"},{"id":"426","name":"德意丽博宁馨系列-白璧流瑕","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160226/56d01765a6e0a.jpg","subtitle":"","collect_cnt":"1"},{"id":"410","name":"尼琪思丹妮落地灯ADA-0079","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160225/56ce686f3fe90.jpg","subtitle":"","collect_cnt":"1"},{"id":"408","name":"利米缇思菲利躺椅SSM-0909-B","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160225/56ce64e93fcbf.jpg","subtitle":"","collect_cnt":"1"},{"id":"409","name":"利米缇思海豚灯（大）AL-1023","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160225/56ce64fed2c27.jpg","subtitle":"","collect_cnt":"1"},{"id":"406","name":"亚细亚圣马砾石SK6003B、SK6003D复古砖","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160225/56ce6a58e9847.jpg","subtitle":"","collect_cnt":"1"},{"id":"418","name":"好粉之家舒适简约风格卫客厅家具五件套","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160128/56a9d95b6361e.jpg","subtitle":"","collect_cnt":"1"},{"id":"407","name":"亚细亚新维纳斯CPB80803微晶石","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160225/56ce6a8b69c96.jpg","subtitle":"","collect_cnt":"1"},{"id":"419","name":"好粉之家现代清新风格卧室家具六件套","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160128/56a9d94acd070.jpg","subtitle":"","collect_cnt":"1"},{"id":"427","name":"德意丽博恒美系列-写意空间","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160226/56d018449fbbe.jpg","subtitle":"","collect_cnt":"1"},{"id":"417","name":"好粉之家实用简欧风格厨房组合使用","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160128/56a9d7a8a802c.jpg","subtitle":"","collect_cnt":"1"},{"id":"416","name":"好粉之家美式简约风格卫生间组合使用","cover_img":"http://meiyu-api.uduoo.com/data/upload/product/20160128/56a9d79c25139.jpg","subtitle":"","collect_cnt":"1"}]
     * status : ok
     */

    private String total;
    private String status;
    /**
     * id : 420
     * name : 顾家爱舒仕沙发3人位+贵妃躺
     * cover_img : http://meiyu-api.uduoo.com/data/upload/product/20160405/57037b58bf001.jpg
     * subtitle :
     * collect_cnt : 1
     */

    private List<DataBean> data;

    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public ErrorEntity getError() {
        return error;
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
        private String cover_img;
        private String subtitle;
        private String collect_cnt;
        private String is_collect;

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

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getCollect_cnt() {
            return collect_cnt;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public void setCollect_cnt(String collect_cnt) {
            this.collect_cnt = collect_cnt;
        }
    }
}
