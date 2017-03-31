package enjoytouch.com.redstar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by enjoytouch-ad02 on 2015/7/17.
 */
public class MsgDBHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "RedStar.db";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = "mall_table";
    public final static String ID = "id";
    public final static String MALL_ID = "mall_id";
    public final static String CITY_ID = "city_id";
    public final static String MALL_NAME = "name";
    public final static String MALL_ADDRESS = "address";
    public final static String INFO_USERID = "userid";




    public MsgDBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public synchronized void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( " + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + MALL_ID + " varchar(10), "+ CITY_ID +" varchar(10), "+INFO_USERID+" varchar(20),"+MALL_NAME+" varchar(300), "+MALL_ADDRESS+" varchar(300), "+" INTEGER ); ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public Cursor select(String userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }



    //增加操作
    public synchronized long insert(String mall_id,String city_id,String mall_name,String mall_address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MALL_ID, mall_id);
        cv.put(CITY_ID,city_id);
        cv.put(MALL_NAME,mall_name);
        cv.put(MALL_ADDRESS,mall_address);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public synchronized void delete(String mall_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = MALL_ID + " = ?";
        String[] whereValue ={ mall_id };
        db.delete(TABLE_NAME, where, whereValue);
    }

    //修改操作
    public synchronized void update(String mall_id,String city_id,String mall_name,String mall_address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = MALL_ID + " = ?";
        String[] whereValue = {mall_id };

        ContentValues cv = new ContentValues();
        cv.put(CITY_ID,city_id);
        cv.put(MALL_NAME,mall_name);
        cv.put(MALL_ADDRESS,mall_address);
        db.update(TABLE_NAME, cv, where, whereValue);
    }

   /* public synchronized void close() {
      this.close();
    }*/
}
