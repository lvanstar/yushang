package enjoytouch.com.redstar.util;

import android.content.Context;
import android.content.Intent;

import enjoytouch.com.redstar.activity.LoginActivity;
import enjoytouch.com.redstar.application.MyApplication;

/**
 * Created by enjoytouch-ad02 on 2015/8/27.
 */
public class AccountUtil {
    public static boolean showLoginView(Context context){
        if(!MyApplication.isLogin){
            /*Intent intent = new Intent(context, LoginAndRegActivity.class);
            context.startActivity(intent);*/
//            DialogUtil.show(context);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return !MyApplication.isLogin;
    }
}
