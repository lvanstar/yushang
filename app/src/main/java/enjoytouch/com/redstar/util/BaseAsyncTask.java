package enjoytouch.com.redstar.util;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import enjoytouch.com.redstar.activity.LoginActivity;
import enjoytouch.com.redstar.application.MyApplication;

/**
 * Created by Administrator on 2015/7/27.
 */
public class BaseAsyncTask extends AsyncTask<Void,Void,JSONObject>{

    Context context;
    InputStream is;
    BaseAsynckCallBack callBack;
    String uri;
    List<NameValuePair> params;
    public BaseAsyncTask(Context context,String uri,List<NameValuePair> params,BaseAsynckCallBack callBack){
        this.context=context;
        this.callBack=callBack;
        this.uri=uri;
        this.params=params;
        Log.i("lzz","11111111111111111111"+params+  uri);
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {
       // Log.i("lzz","22222222222222222");
        try {
            HttpEntity entity = HttpUtils.getEntity(uri, params, HttpUtils.METHOD_POST);
            is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String res = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                res = res + line;
            }
            Log.i("res", res);
            JSONObject jo = new JSONObject(res);

            return jo;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            callBack.getBaseAsynck(jsonObject);
            if (!jsonObject.getString("status").toLowerCase().equals("ok")){
                String error = jsonObject.getJSONObject("error").getString("code");
                String message = jsonObject.getJSONObject("error").getString("message");
                if (error.equals("1012")){
                    Intent intent = new Intent(context, LoginActivity.class);
                    MyApplication.sf.edit().putBoolean(GlobalConsts.ISLOGIN,false).commit();
                    MyApplication.isLogin=false;
                    intent.putExtra("from", true);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface BaseAsynckCallBack{
        public void getBaseAsynck(JSONObject object);
    }
}
