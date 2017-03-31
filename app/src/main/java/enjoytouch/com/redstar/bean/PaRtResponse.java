package enjoytouch.com.redstar.bean;

import enjoytouch.com.redstar.util.ContentUtil;

/**
 * Created by lizhaozhao on 16/7/13.
 */
public class PaRtResponse<T> extends BaseBean{

    private String status;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        ContentUtil.makeLog("lzz","data");
        if("erroe".equals(status)){
            ErrorBean bean= (ErrorBean) data;
            ContentUtil.makeLog("lzz","code");
            if(bean!=null){
                if("1021".equals(bean.getCode())){
                    ContentUtil.makeLog("lzz","1021");
                }
            }
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
