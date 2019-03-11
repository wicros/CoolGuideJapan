package jp.co.jpmobile.coolguidejapan.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wicors on 2016/8/24.
 */
public class GpsOpenHelper extends SQLiteOpenHelper {
    private static GpsOpenHelper helper;

    public static GpsOpenHelper  getInstance (Context context){
        if (helper == null) {
            helper = new GpsOpenHelper(context, "jpmob.db", null, 1);
        }
        return helper;
    }

    private GpsOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建表 或者初始化表数据      数据库第一次创建的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //唯一标示    姓名   年龄
        String sql = "create table gps(_id integer primary key autoincrement," +
                "datetime text," +
                "latitude text," +
                "longitude text)";
        //添加30条数据
        db.execSQL(sql);
    }

    //数据库升级使用    在persons表里面去添加一个字段  账户 account   修改数据库的版本号
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table persons add account integer null");
    }

}
