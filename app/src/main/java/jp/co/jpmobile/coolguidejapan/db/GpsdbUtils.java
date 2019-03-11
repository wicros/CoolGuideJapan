package jp.co.jpmobile.coolguidejapan.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.GPSbean;

/**
 * Created by wicors on 2016/8/24.
 */
public class GpsdbUtils {

//            "datetime text," +
//            "latitude text," +
//            "longitude text)";

    private  GpsOpenHelper helper ;

    public GpsdbUtils(Context context){
        helper = GpsOpenHelper.getInstance(context);
    }

    public  void save(GPSbean gpSbean){
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()){
            db.execSQL("insert into gps(datetime,latitude,longitude) values(?,?,?)",
                    new Object[]{gpSbean.getDatetime(),String.valueOf(gpSbean.getLatitude()),String.valueOf(gpSbean.getLongtitude())});
            db.close();
        }
    }

    public  List<GPSbean> findAll(){
        List<GPSbean> gpSbeanList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from gps", null);
            while(cursor.moveToNext()){
                GPSbean gpSbean = new GPSbean();
                int _id = cursor.getInt(0);
                String datetime = cursor.getString(cursor.getColumnIndex("datetime"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                gpSbean.setId(_id);
                gpSbean.setDatetime(datetime);
                gpSbean.setLatitude(Double.parseDouble(latitude));
                gpSbean.setLongtitude(Double.parseDouble(longitude));
                gpSbeanList.add(gpSbean);
            }
            cursor.close();
            db.close();
        }
        return gpSbeanList;
    }

    public void delete(GPSbean gpSbean){
        SQLiteDatabase db = helper.getWritableDatabase();
        if(db.isOpen()){
            db.execSQL("delete from gps where _id = ?", new Object[]{gpSbean.getId()});
            db.close();
        }
    }
}
