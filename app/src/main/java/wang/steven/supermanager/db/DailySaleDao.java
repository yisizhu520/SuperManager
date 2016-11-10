package wang.steven.supermanager.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import wang.steven.supermanager.SuperApplication;
import wang.steven.supermanager.util.DateUtil;

/**
 * 这里添加描述
 *
 * @author 汪俊
 * @date 2016-11-08
 */

public class DailySaleDao {

    private SQLiteDatabase db;

    public DailySaleDao() {
        db = SuperApplication.getInstance().getSupermarketDB();
    }

    public List<DailySale> getAllInfo() {
        Cursor cursor = db.rawQuery("select * from daily_sale_info", null);
        List<DailySale> results = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            DailySale sale = new DailySale();
            int id = cursor.getInt(cursor.getColumnIndex(DailySale.ID));
            int marketId = cursor.getInt(cursor.getColumnIndex(DailySale.MARKET_ID));
            float volume = cursor.getFloat(cursor.getColumnIndex(DailySale.VOLUME));
            String date = cursor.getString(cursor.getColumnIndex(DailySale.DATE));
            sale
                    .setId(id)
                    .setMarketId(marketId)
                    .setVolume(volume)
                    .setDate(DateUtil.parseDate(date))
            ;
            results.add(sale);
        }
        cursor.close();
        return results;
    }


}
