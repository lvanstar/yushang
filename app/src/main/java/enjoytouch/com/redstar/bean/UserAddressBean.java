package enjoytouch.com.redstar.bean;

import java.io.Serializable;

/**
 * Created by lizhaozhao on 16/6/27.
 */
public class UserAddressBean implements Serializable{

        /**
         * id : 30
         * name : 蔡振喜
         * city_id : 35
         * tel : 15000059840
         * zip_code : 236200
         * province_name : 上海市
         * city_name : 上海市
         * area_name : 宝山区
         * address : 啊董事长徐和规范市场经济
         */
        private String id;
        private String name;
        private String city_id;
        private String tel;
        private String zip_code;
        private String province_name;
        private String city_name;
        private String area_name;
        private String address;
        private String province_id;
        private String area_id;


        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCity_id() {
            return city_id;
        }

        public String getTel() {
            return tel;
        }

        public String getZip_code() {
            return zip_code;
        }

        public String getProvince_name() {
            return province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public String getArea_name() {
            return area_name;
        }

        public String getAddress() {
            return address;
        }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }
}
