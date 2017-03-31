package enjoytouch.com.redstar.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 用户编辑的地址保存的对象(解析)
 */
public class EditAddressBean implements Serializable{
    private String status;
    private UserAddressBean data;
    private ErrorBean error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserAddressBean getData() {
        return data;
    }

    public void setData(UserAddressBean data) {
        this.data = data;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }




}
