package wang.steven.supermanager;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import wang.steven.supermanager.db.DBManager;
import wang.steven.supermanager.db.DailySaleDao;
import wang.steven.supermanager.db.MarketDao;

/**
 * 这里添加描述
 *
 * @author 汪俊
 * @date 2016-11-08
 */

public class SuperApplication extends Application{

    private SQLiteDatabase superDB;

    private static SuperApplication sInstance;

    public static SuperApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        superDB = DBManager.getInstance(this).getSupermarketDB();
        MarketDao dao = new MarketDao();
        dao.getAllMarkets();
        new DailySaleDao().getAllInfo();
    }

    public SQLiteDatabase getSupermarketDB(){
        return superDB;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        superDB.close();
    }
}
