package enjoytouch.com.redstar.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.MallListBean;


public class DbUtils {



    /**
     * 查询城市商城数据
     */
    public static List<MallListBean> readMallList() {
        List<MallListBean> list = new ArrayList<MallListBean>();
        DatabaseManager manager = DatabaseManager.getInstance();
        SQLiteDatabase db = manager.openDatabase();
        Cursor cursor = null;
        db.rawQuery("select * from " + MsgDBHelper.TABLE_NAME + " where " + MsgDBHelper.CITY_ID + "=?", new String[]{MyApplication.cityId});
        cursor = db.query(MsgDBHelper.TABLE_NAME, null, MsgDBHelper.CITY_ID + "=?", new String[]{MyApplication.cityId},
                null, null, null);
        //	}
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MallListBean bean = new MallListBean();
                bean.id=cursor.getString(cursor.getColumnIndex(MsgDBHelper.MALL_ID));
                bean.city_id=cursor.getString(cursor.getColumnIndex(MsgDBHelper.CITY_ID));
                bean.name=cursor.getString(cursor.getColumnIndex(MsgDBHelper.MALL_NAME));
                bean.address=cursor.getString(cursor.getColumnIndex(MsgDBHelper.MALL_ADDRESS));
                list.add(bean);
            }
        }
        manager.closeDatabase();
        return list;
    }



    //删除
//    public static void deleteMsg(String msgId) {
//        DatabaseManager manager = DatabaseManager.getInstance();
//        SQLiteDatabase db = manager.openDatabase();
//        String where = MsgDBHelper.INFO_ID + " = ? and " + MsgDBHelper.INFO_USERID + " = ?";
//        String[] whereValue = {msgId, MyApplication.bean.id};
//        db.delete(MsgDBHelper.TABLE_NAME, where, whereValue);
//        manager.closeDatabase();
//    }

    //添加
    public static void insertMsgList(List<MallListBean> msgBeans) {

        DatabaseManager manager = DatabaseManager.getInstance();
        SQLiteDatabase db = manager.openDatabase();
        for (MallListBean bean : msgBeans) {
            ContentValues cv = new ContentValues();
            cv.put(MsgDBHelper.MALL_ID, bean.id);
            cv.put(MsgDBHelper.CITY_ID, bean.city_id);
            cv.put(MsgDBHelper.MALL_NAME, bean.name);
            cv.put(MsgDBHelper.MALL_ADDRESS, bean.address);
            long row = db.insert(MsgDBHelper.TABLE_NAME, null, cv);
            Log.i("row", row + "");
        }
        manager.closeDatabase();
    }
}
