package wang.steven.supermanager.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import wang.steven.supermanager.SuperApplication;

/**
 * 这里添加描述
 *
 * @author 汪俊
 * @date 2016-11-08
 */

public class MarketDao {

    private SQLiteDatabase db;

    public MarketDao() {
        db = SuperApplication.getInstance().getSupermarketDB();
    }

    public List<Market> getAllMarkets() {
        Cursor cursor = db.rawQuery("select * from market_info", null);
        List<Market> results = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            Market market = new Market();
            int id = cursor.getInt(cursor.getColumnIndex(Market.ID));
            String cnname = cursor.getString(cursor.getColumnIndex(Market.CNNAME));
            String enname = cursor.getString(cursor.getColumnIndex(Market.ENNAME));
            String address = cursor.getString(cursor.getColumnIndex(Market.ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndex(Market.PHONE));
            String region = cursor.getString(cursor.getColumnIndex(Market.REGION));
            market
                    .setId(id)
                    .setCNName(cnname)
                    .setENName(enname)
                    .setAddress(address)
                    .setPhone(phone)
                    .setRegion(region)
            ;
            results.add(market);
        }
        cursor.close();
        return results;
    }


}
