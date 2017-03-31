package enjoytouch.com.redstar.bean;

/**
 * Created by Administrator on 2015/8/5.
 */
public class MallListBean {

    public String id;
    public String city_id;
    public String name;
    public String address;
    public String created;

    @Override
    public String toString() {
        return "MallListBean{" +
                "id='" + id + '\'' +
                ", city_id='" + city_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
