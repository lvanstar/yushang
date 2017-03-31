package enjoytouch.com.redstar.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/28.
 */
public class AddressBean implements Serializable{

    public String name;
    public String address;
    public String lat;
    public String lng;


    @Override
    public String toString() {
        return "AddressBean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
