package wang.steven.supermanager.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 这里添加描述
 *
 * @author 汪俊
 * @date 2016-11-08
 */

public class DBManager {

    private String mDbPath = "/data/data/%s/databases";

    private Context mContext;

    private static DBManager sInstance;

    private DBManager(Context context){
        mContext = context;
    }

    public static DBManager getInstance(Context context){
        if(sInstance == null){
            sInstance = new DBManager(context);
        }
        return sInstance;
    }

    public SQLiteDatabase getSupermarketDB(){
        return getDatabase("supermarket.db");
    }

    /**
     * Get a assets database, if this database is opened this method is only return a copy of the opened database
     * @param dbfile, the assets file which will be opened for a database
     * @return, if success it return a SQLiteDatabase object else return null
     */
    private SQLiteDatabase getDatabase(String dbfile) {

        String spath = getDatabaseFilepath();
        String sfile = spath +"/"+dbfile;
        File file = new File(sfile);
        SharedPreferences dbs = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
        // Get Database file flag, if true means this database file was copied and valid
        //boolean flag = dbs.getBoolean(dbfile, false);
        //TODO refresh data when restart app
        boolean flag = false;
        if(!flag || !file.exists()){
            file = new File(spath);
            if(!file.exists() && !file.mkdirs()){
                return null;
            }
            if(!copyAssetsToFilesystem(dbfile, sfile)){
                return null;
            }
            dbs.edit().putBoolean(dbfile, true).apply();
        }
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return db;
    }

    private String getDatabaseFilepath(){
        return String.format(mDbPath, mContext.getApplicationInfo().packageName);
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des){
        InputStream istream = null;
        OutputStream ostream = null;
        try{
            AssetManager am = mContext.getAssets();
            istream = am.open(assetsSrc);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer))>0){
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                if(istream!=null)
                    istream.close();
                if(ostream!=null)
                    ostream.close();
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }





}
