package enjoytouch.com.redstar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.util.GlobalConsts;


/**
 * Created by yw on 2015/5/3.
 * 懒加载fragment，当切换到该fragment时才会对数据进行更新,仅适用于activity 设置过setScreenOffset的viewpager下的fragment
 */
public abstract class BaseFragment extends Fragment {

    protected boolean mIsLogin;
    protected boolean visible;
    protected String userId = GlobalConsts.UNLOGIN_ID;
    private boolean inited;//标记是否已初始化布局
    private boolean firstLoad = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsLogin = MyApplication.isLogin;
        if (mIsLogin) {
            userId = MyApplication.user_id;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inited = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (visible) {
            if (MyApplication.isLogin != mIsLogin) {
                firstLoad = false;
                mIsLogin = !mIsLogin;
                if (userId.equals(GlobalConsts.UNLOGIN_ID)) {
                    userId = MyApplication.user_id;
                    //未登录 - 登录
                    resetUserData();
                } else {
                    userId = GlobalConsts.UNLOGIN_ID;
                    //登录 - 未登录
                    removeUserData();
                }
            } else if (MyApplication.isLogin && !userId.equals(MyApplication.user_id)) {
                firstLoad = false;
                userId = MyApplication.user_id;
                // 账号切换
                resetUserData();
            } else if (firstLoad && inited) {
                // 加载初始数据
                firstLoad = false;
                initData();
            } else if (mIsLogin) {
                // 登录状态下从其它页面返回
                onRes();
            }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        visible = isVisibleToUser;
        if (isVisibleToUser /*&& inited*/) {
        /*    Log.i("visible to user","true");
            Log.i("isLogin equal",(MyApplication.isLogin != mIsLogin)+"");
            Log.i("firstLoad",firstLoad+"");
            Log.i("inited",inited+"");*/

            //登录状态发生改变
            if (MyApplication.isLogin != mIsLogin) {
                firstLoad = false;
                mIsLogin = !mIsLogin;
                if (userId.equals(GlobalConsts.UNLOGIN_ID)) {
                    userId = MyApplication.user_id;
                    //未登录 - 登录
                    resetUserData();
                } else {
                    userId = GlobalConsts.UNLOGIN_ID;
                    //登录 - 未登录
                    removeUserData();
                }
            } else if (MyApplication.isLogin && !userId.equals(MyApplication.user_id)) {
                firstLoad = false;
                userId = MyApplication.user_id;
                // 账号切换
                resetUserData();
            } else if (firstLoad && inited) {
                firstLoad = false;
                initData();
            } else if (mIsLogin) {
                onRes();
            }
        }

    }

    public abstract void onRes();

    public abstract void initData();

    public abstract void resetUserData();

    public abstract void removeUserData();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
