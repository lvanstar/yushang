package enjoytouch.com.redstar.bean;


import android.text.TextUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * 此类提供网络请求参数设置
 *
 * lzz
 */

public class ParamsWrapper {

    public ArrayList<NameValuePair> params;

    public ParamsWrapper() {
        params = new ArrayList<>();
    }

    public ParamsWrapper userToken(String value) {
        //if(TextUtils.isEmpty(value)||!MyApplication.isLogin)return this;
        return add("token", value);
    }

    public ParamsWrapper userToken() {
        return add("token", MyApplication.isLogin ? MyApplication.token : GlobalConsts.UNLOGIN_ID);
    }

    public ParamsWrapper lat(String value) {
        return add("lat", value);
    }

    public ParamsWrapper type(String value) {
        return add("type", value);
    }
    public ParamsWrapper content(String value) {
        return add("content", value);
    }
    public ParamsWrapper city_id(String value){return add("city",value);}


    //品牌列表
    public ParamsWrapper login_type(String value) {
        return add("login_type", value);
    }
    public ParamsWrapper token(String value) {
        return add("token", value);
    }
    public ParamsWrapper has_coupon(String value) {
        return add("has_coupon", value);
    } public ParamsWrapper keyword(String value) {
        return add("keyword", value);
    }








    private ParamsWrapper add(String key, String value) {
        return add(key, value, true);
    }

    private ParamsWrapper add(String key, String value, boolean nullable) {
        if (TextUtils.isEmpty(value) && !nullable) return this;
        params.add(new BasicNameValuePair(key, value));
        return this;
    }
}
