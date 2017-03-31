package enjoytouch.com.redstar.application;

import android.app.Application;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.Display;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.AppStartActivity;
import enjoytouch.com.redstar.bean.CityBean;
import enjoytouch.com.redstar.bean.GetFirstCityBean;
import enjoytouch.com.redstar.bean.LocateCityBean;
import enjoytouch.com.redstar.bean.MallListBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.HttpUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2015/6/29.
 */
public class MyApplication extends Application implements AMapLocationListener{

    public static boolean isLogin;//是否登录
    public static boolean first;//是否为第一次登录
    public static boolean isTest = false;
    public boolean flag=true;//判断每次是否第一次进入app
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    public static AMapLocation aMapLocation;
    //偏好设置
    public static SharedPreferences sf;
//    public static SharedPreferences sf2;
    public static Typeface font;

    private static Intent locateIntent;
    public static String ACTION_LOCATE_FINISH = "enjoytouch.locate.finish";

    public static String cityId = "35";
    public static String cityName = "上海";//系统定位当前城市
    public static String in_service="2";
    public static String lat = "31.238035";
    public static String lng = "121.384758";
    public static List<MallListBean> mallList = new ArrayList<>();

    private static MyApplication INSTANCE;

    public static String name = "品类";
    public static int postion2=0;
    public static int postion02=0;
    public static int item1 = 0;
    public static List<String>b=new ArrayList<>();

    public static String head_img="";   //头像
    public static String nickname="";  //昵称
    public static String sex="0";   //性别
    public static String mobile="";
    public static String user_id="";
    public static String token="";
    public static Display currDisplay;  //手机屏幕分辨率
    //收货地址的数据
    public static String address_name="请添加";
    public static String address_phone="请添加";
    public static String address="请添加收货地址";
    public static int select=0;
    public static List<String>list=new ArrayList<>();

    public static String address_id="";
    //城市数据
    public static List<CityBean.DataBean> city_list;
    public static List<GetFirstCityBean.DataBean>cityDatas;
    // 微信
    public static IWXAPI wxapi;

      public static String add="";//这是爆款确认订单地址

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        MultiDex.install(INSTANCE);//友们解决初始化异常
        Fresco.initialize(this);
        sf = PreferenceManager.getDefaultSharedPreferences(this);
//        sf2 = PreferenceManager.getDefaultSharedPreferences(this);
        isLogin = sf.getBoolean(GlobalConsts.ISLOGIN, false);
        first = sf.getBoolean(GlobalConsts.FIRST, false);
        font = Typeface.createFromAsset(getAssets(), "fonts/Impact.ttf");
        user_id = sf.getString(GlobalConsts.CONSTANT_USER_ID, "");
        mobile = sf.getString(GlobalConsts.CONSTANT_MOBLIE, "");
        nickname = sf.getString(GlobalConsts.CONSTANT_NICKNAME, "");
        token = sf.getString(GlobalConsts.CONSTANT_TOKEN, "");
        head_img=sf.getString(GlobalConsts.CONSTANT_HEAD,"");
        sex=sf.getString(GlobalConsts.CONSTANT_SEX,"");
        wxapi = WXAPIFactory.createWXAPI(getApplicationContext(), GlobalConsts.WX_APP_ID,
                true);
        wxapi.registerApp(GlobalConsts.WX_APP_ID);


        PlatformConfig.setWeixin(GlobalConsts.WX_APP_ID, GlobalConsts.WX_APP_SECRET);
        PlatformConfig.setSinaWeibo(GlobalConsts.WEIBO_APP_ID, GlobalConsts.WEIBO_APP_SECRET);
        Config.REDIRECT_URL=GlobalConsts.REDIRECT_URL;
        PlatformConfig.setQQZone(GlobalConsts.QQ_APP_ID, GlobalConsts.QQ_APP_SECRET);
        // 初始化定位信息
        initLocation();
        request();
        getCitys();//获取全国所以数据

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }


    private static Context CONTEXT;

    public static void getLatestVersion(Context context) {
        CONTEXT = context;
        HttpUtils.getResultToHandler(context, "common", "version", params(), handler, 1);
    }


    private static Dialog updateDialog;
    private static LocateHandler handler = new LocateHandler();


    private void initLocation(){
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                MyApplication.aMapLocation=aMapLocation;
                lat= String.valueOf(aMapLocation.getLatitude());
                lng=String.valueOf(aMapLocation.getLongitude());
                if(flag){
                    flag=false;
                    updateCity();
                    mlocationClient.stopLocation();
                }

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }


    }

    private static class LocateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject jo = (JSONObject) msg.obj;
                try {
                    if (jo.getString("status").toLowerCase().equals("ok")) {
                        JSONObject jcity = jo.getJSONObject("data");
                        MyApplication.cityId = jcity.getString("id");
                        MyApplication.cityName = jcity.getString("name");
                        ContentUtil.makeLog("lzz", "application:" + cityId + "name:" + cityName);
                        locateIntent.setAction(ACTION_LOCATE_FINISH);
                        INSTANCE.sendBroadcast(locateIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                if (msg.what == 1) {
                    JSONObject jo = (JSONObject) msg.obj;
                    try {
                        if (jo.getString("status").toLowerCase().equals("ok")) {
                            final JSONObject jdata = jo.getJSONObject("data");
                            int verCode = Integer.parseInt(jdata.getString("build"));
                            if (INSTANCE.getPackageManager().getPackageInfo(INSTANCE.getPackageName(), 0).versionCode < verCode) {
                                updateDialog = new MiddleDialog<>(CONTEXT, "发现新版本：" + jdata.getString("version") + "，是否立即下载？", "","","", 0, new MiddleDialog.onButtonCLickListener<Object>() {
                                    @Override
                                    public void onActivieButtonClick(Object bean, int position) {
                                        updateDialog.dismiss();
                                        try {
                                            Uri uri = Uri.parse(jdata.getString("url"));
//                                        Intent intent = new Intent();
//                                        intent.setAction(Intent.ACTION_VIEW);
//                                        intent.setData(uri);
//                                        CONTEXT.startActivity(intent);
                                            DownloadManager dManager = (DownloadManager) INSTANCE.getSystemService(Context.DOWNLOAD_SERVICE);
                                            DownloadManager.Request request = new DownloadManager.Request(uri);
                                            request.setDestinationInExternalPublicDir("apk", "RedStar" + jdata.getString("version") + ".apk");
                                            request.setDescription("寓上_版本更新");
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            request.setMimeType("application/vnd.android.package-archive");
// 设置为可被媒体扫描器找到
                                            request.allowScanningByMediaScanner();
// 设置为可见和可管理
                                            request.setVisibleInDownloadsUi(true);
                                            long reference = dManager.enqueue(request);
// 把当前下载的ID保存起来
                                            INSTANCE.sf.edit().putLong("reference", reference).commit();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, R.style.registDialog);
                                updateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        if (CONTEXT instanceof AppStartActivity)
                                            ((AppStartActivity) CONTEXT).gotoMain();
                                    }
                                });
                                updateDialog.show();
                            } else {
                                if (CONTEXT instanceof AppStartActivity)
                                    ((AppStartActivity) CONTEXT).gotoMain();
                            }
                        } else {
                            if (CONTEXT instanceof AppStartActivity)
                                ((AppStartActivity) CONTEXT).gotoMain();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static List<MallListBean> getMallList(String cityId) {
        if (cityId == null) return getMallList(MyApplication.cityId);
        ArrayList<MallListBean> selectedList = new ArrayList<>();
        for (MallListBean item : mallList) if (item.city_id.equals(cityId)) selectedList.add(item);
        return selectedList;
    }


    private static List<NameValuePair> params() {
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("platform", "android"));
        params.add(new BasicNameValuePair("login_type", "1"));
        return params;
    }

    private void updateCity(){
        Call<LocateCityBean>call= HttpServiceClient.getInstance().locate_city(MyApplication.lat,MyApplication.lng);
        call.enqueue(new Callback<LocateCityBean>() {
            @Override
            public void onResponse(Call<LocateCityBean> call, Response<LocateCityBean> response) {
                if (response.isSuccessful()) {

                    if ("ok".equals(response.body().getStatus())) {

                        MyApplication.cityId = response.body().getData().getId();
                        MyApplication.cityName = response.body().getData().getName();
                        MyApplication.in_service = response.body().getData().getIn_service();
                    } else {
                        ContentUtil.makeToast(INSTANCE, response.body().getError().getMessage());
                    }
                } else {
                    ContentUtil.makeToast(INSTANCE, getString(R.string.loadding_error));
                }
            }

            @Override
            public void onFailure(Call<LocateCityBean> call, Throwable t) {

            }
        });
    }
    private void request(){
        /**
         * 异步请求方式
         */
        Call<CityBean> call= HttpServiceClient.getInstance().city_list(MyApplication.token);
        call.enqueue(new Callback<CityBean>() {
            @Override
            public void onResponse(Call<CityBean> call, Response<CityBean> response) {
                //dialog.dismiss();
                if (response.isSuccessful()) {
                    CityBean userBean = response.body();
                    if (userBean.getStatus().toString().equals("ok")) {
                        MyApplication.city_list = userBean.getData();
                    } else {
                        Dialog dialog = DialogUtil.show(MyApplication.this, userBean.getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
            }
        });
    }

    private void getCitys(){
        Call<GetFirstCityBean> call= HttpServiceClient.getInstance().city();
        call.enqueue(new Callback<GetFirstCityBean>() {
            @Override
            public void onResponse(Call<GetFirstCityBean> call, Response<GetFirstCityBean> response) {
                //dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equals("ok")) {
                        MyApplication.cityDatas = response.body().getData();
                    } else {
                        Dialog dialog = DialogUtil.show(MyApplication.this, response.body().getError().getMessage().toString(), false);
                        dialog.show();
                    }
                } else {
                    ContentUtil.makeLog("lzz", "ok:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GetFirstCityBean> call, Throwable t) {
                ContentUtil.makeLog("失败", "失败了");
            }
        });
    }

}
