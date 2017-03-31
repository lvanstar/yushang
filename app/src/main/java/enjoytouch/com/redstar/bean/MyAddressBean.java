package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 */
public class MyAddressBean implements Serializable{
    private String status;
    private List<UserAddressBean> data;
    private ErrorBean error;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserAddressBean> getData() {
        return data;
    }

    public void setData(List<UserAddressBean> data) {
        this.data = data;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }




}
