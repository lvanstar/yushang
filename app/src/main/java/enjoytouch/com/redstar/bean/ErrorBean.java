package enjoytouch.com.redstar.bean;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by lizhaozhao on 16/4/21.
 * <p/>
 * 请求错误类
 */
public class ErrorBean implements Serializable {
    /**
     * status : error
     * error : {"code":"1012","error":"WRONG_PASSWORD","message":"密码错误!"}
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
        ContentUtil.makeLog("lzz", "code:"+code);
        if("1012".equals(code)){
            MyApplication.isLogin = false;
            MyApplication.sf.edit().putBoolean(GlobalConsts.ISLOGIN, false).commit();
            ContentUtil.makeLog("lzz","1012");
        }
        return message;
    }

    @Override
    public String toString() {
        return "ErrorBean{" +
                "code='" + code + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
