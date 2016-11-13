package wang.steven.supermanager.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import wang.steven.supermanager.SuperApplication;

/**
 * Created by WangJun on 2016/11/13.
 */

public class CategoryDailySaleDao {

    private SQLiteDatabase db;

    public CategoryDailySaleDao() {
        db = SuperApplication.getInstance().getSupermarketDB();
    }

    public List<CategoryDailySale> getProductProfits(String from,String to){
        String sql =
                "select category_id,category_name,sum(profit) as profits \n" +
                        "from category_daily_sale group by category_id \n" +
                        "having date>='%s' and date<='%s';";
        sql = String.format(sql, from, to);
        Cursor cursor = db.rawQuery(sql, null);
        List<CategoryDailySale> results = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            CategoryDailySale info = new CategoryDailySale();
            info.categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            info.categoryName = cursor.getString(cursor.getColumnIndex("category_name"));
            info.profit = cursor.getFloat(cursor.getColumnIndex("profits"));
            results.add(info);
        }
        cursor.close();
        return results;
    }

    public List<CategoryDailySale> getCateProfitsByDate(int cateId,String from,String to){
        String sql =
                "select * from category_daily_sale \n" +
                        "where category_id = %d and date>='%s' and date<='%s'\n" +
                        "order by date;";
        sql = String.format(sql, cateId,from, to);
        Cursor cursor = db.rawQuery(sql, null);
        List<CategoryDailySale> results = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            CategoryDailySale info = new CategoryDailySale();
            info.categoryId = cursor.getInt(cursor.getColumnIndex("category_id"));
            info.categoryName = cursor.getString(cursor.getColumnIndex("category_name"));
            info.profit = cursor.getFloat(cursor.getColumnIndex("profit"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            results.add(info);
        }
        cursor.close();
        return results;
    }



}
