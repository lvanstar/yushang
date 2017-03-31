package enjoytouch.com.redstar.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class Constants {


    //appid
  public static final String APP_ID = "wxea6787f62e9003cb";//yum




  //商户号
   public static final String MCH_ID = "1274855301";//yum


//  API密钥，在商户平台设置
    public static final  String API_KEY="654DEBC12268FC326D73757F7BFD3764";//yum


    /**
     * 获取MAC地址
     * @return
     */
    public static String getMac()
     {
             String macSerial = null;
            String str = "";

             try
             {
                     Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
                     InputStreamReader ir = new InputStreamReader(pp.getInputStream());
                     LineNumberReader input = new LineNumberReader(ir);

                     for (; null != str;)
                         {str = input.readLine();
                             if (str != null)
                                 {
                                     macSerial = str.trim();// 去空格
                                    break;
                                 }
                         }
                 } catch (IOException ex) {
                     // 赋予默认值
                     ex.printStackTrace();
                 }
             return macSerial;
         }


    /**
     * 獲取IMSI和IMEI
     * @param context
     * @return
     */
    public static String getIMSIAndIMEI(Context context){
        TelephonyManager mTelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        String imei = mTelephonyMgr.getDeviceId();
        return imsi+imei;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}
