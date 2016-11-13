package wang.steven.supermanager.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import wang.steven.supermanager.SuperApplication;

/**
 * Created by WangJun on 2016/11/13.
 */

public class ProductDao {

    private SQLiteDatabase db;

    public ProductDao(){
        db = SuperApplication.getInstance().getSupermarketDB();
    }

    public Product getProductById(int productId){
        Cursor cursor = db.rawQuery("select * from product where id = "+productId, null);
        Product p = null;
        while (cursor.moveToNext()) {
            p = buildFromCursor(cursor);
        }
        cursor.close();
        return p;
    }

    private Product buildFromCursor(Cursor cursor){
        Product p = new Product();
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String type = cursor.getString(cursor.getColumnIndex("type"));
        int stock = cursor.getInt(cursor.getColumnIndex("stock"));
        float price = cursor.getInt(cursor.getColumnIndex("price"));
        int minStockRatio = cursor.getInt(cursor.getColumnIndex("min_stock_ratio"));
        int needAlarm = cursor.getInt(cursor.getColumnIndex("stock_need_alarm"));
        p
                .setId(id)
                .setName(name)
                .setType(type)
                .setPrice(price)
                .setStock(stock)
                .setMinStockRatio(minStockRatio)
                .setStockNeedAlarm(needAlarm)
        ;
        return p;
    }


    public List<Product> getAllProducts(){
        Cursor cursor = db.rawQuery("select * from product", null);
        List<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            products.add(buildFromCursor(cursor));
        }
        cursor.close();
        return products;
    }


    public List<Product> getProductsByType(String type){
        Cursor cursor = db.rawQuery("select * from product where type = ?", new String[]{type});
        List<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            products.add(buildFromCursor(cursor));
        }
        cursor.close();
        return products;
    }



}
