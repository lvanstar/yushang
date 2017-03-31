package enjoytouch.com.redstar.bean;

import java.io.Serializable;

/**
 * Created by lzz on 2016/6/29.
 */
public class ShopBean implements Serializable{

    private String id;
    private String name;
    private String address;
    private String lat;
    private String lng;
    private String tel;
    private String business_hours;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getBusiness_hours() {
        return business_hours;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getTel() {
        return tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
