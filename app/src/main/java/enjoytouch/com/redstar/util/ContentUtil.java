package enjoytouch.com.redstar.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import enjoytouch.com.redstar.application.MyApplication;


public class ContentUtil {

    /**
     * Toast显示
     *
     * @param context
     * @param text
     */
    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeTestToast(Context context, String text) {
        if (MyApplication.isTest == true) {

            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * LOG显示
     *
     * @param tag
     * @param msg
     */
    public static void makeLog(String tag, String msg) {
        if (MyApplication.isTest == true) {
            Log.i(tag, msg);
        }

    }

    public static String toString(String[] array) {
        StringBuffer sb = new StringBuffer();
        sb.append("baba");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }
        sb.append("yuyue");
        return sb.toString();
    }

    public static String CalendarToString(Date d) {
        String t;
        t = String.valueOf(d.getMonth() + 1) + "/"
                + String.valueOf(d.getDate()) + "/"
                + String.valueOf(d.getHours());
        return t;
    }

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    public static String getDateToString(long time) {
        Date d = new Date(time * 1000L);
        return format.format(d);
    }


    public static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public static String getDevice(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }
}
