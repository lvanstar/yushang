package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 地址实体类
 */
public class OneAddressBean {
    /**
     * data : [{"id":"28","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"29","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"30","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"31","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"32","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"33","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"34","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"35","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"36","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"37","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"38","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"39","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "},{"id":"40","linkman":"Chen hua ","tel":"18901468278","zip_code":"226200","province_name":"上海市","city_name":"上海市","region_name":"宝山区","address":"zhen is a great day to be a "}]
     * total : 13
     * status : ok
     */

    private String total;
    private String status;
    /**
     * id : 28
     * linkman : Chen hua
     * tel : 18901468278
     * zip_code : 226200
     * province_name : 上海市
     * city_name : 上海市
     * region_name : 宝山区
     * address : zhen is a great day to be a
     */

    private List<DataBean> data;

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

    public static class DataBean implements Serializable{
        private String id;
        private String linkman;
        private String tel;
        private String zip_code;
        private String province_name;
        private String city_name;
        private String region_name;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

   /* public String id;
    public String name;
    public String tel;
    public String zip_code;
    public String province_name;
    public String city_name;
    public String area_name;
    public String address;
    public String last_used;*/





}
