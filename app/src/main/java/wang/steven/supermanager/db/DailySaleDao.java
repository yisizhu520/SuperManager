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
            int productId = cursor.getInt(cursor.getColumnIndex(DailySale.PRODUCT_ID));
            float sellPrice = cursor.getInt(cursor.getColumnIndex(DailySale.SELL_PRICE));
            float costPrice = cursor.getInt(cursor.getColumnIndex(DailySale.COST_PRICE));
            int volume = cursor.getInt(cursor.getColumnIndex(DailySale.VOLUME));
            String date = cursor.getString(cursor.getColumnIndex(DailySale.DATE));
            sale
                    .setId(id)
                    .setProductId(productId)
                    .setVolume(volume)
                    .setSellPrice(sellPrice)
                    .setCostPrice(costPrice)
                    .setDate(DateUtil.parseDate(date))
            ;
            results.add(sale);
        }
        cursor.close();
        return results;
    }

    public List<SaleTopVO> getSaleTopData(String from, String to) {
        String getProductProfitsSql =
                "select product_id,sum(profit) as profits,sum(sales_volume) as volumes ,product_type from\n" +
                        "(\n" +
                        "\tselect product_id,(sell_price-cost_price)*sales_volume as profit,sales_volume, product_type\n" +
                        "\tfrom daily_sales where date >= '%s' and date<='%s' \n" +
                        ") \n" +
                        "group by product_id order by product_type,profits;";
        getProductProfitsSql = String.format(getProductProfitsSql, from, to);
        Cursor cursor = db.rawQuery(getProductProfitsSql, null);
        List<ProductProfitsInfo> results = new ArrayList<>(cursor.getCount());
        ProductDao productDao = new ProductDao();
        while (cursor.moveToNext()) {
            ProductProfitsInfo info = new ProductProfitsInfo();
            info.productId = cursor.getInt(cursor.getColumnIndex("product_id"));
            info.profits = cursor.getFloat(cursor.getColumnIndex("profits"));
            info.volumes = cursor.getInt(cursor.getColumnIndex("volumes"));
            info.productType = cursor.getString(cursor.getColumnIndex("product_type"));
            // 添加商品名称
            info.productName = productDao.getProductById(info.productId).getName();
            results.add(info);
        }
        cursor.close();
        return getSaleTopData(results);


    }

    private List<SaleTopVO> getSaleTopData(List<ProductProfitsInfo> data) {
        List<SaleTopVO> results = new ArrayList<>();
        if(data.isEmpty()) return results;
        SaleTopVO vo = new SaleTopVO();
        List<ProductProfitsInfo> saleInfos = new ArrayList<>();
        saleInfos.add(data.get(0));
        for (int i = 1; i < data.size(); i++) {
            boolean isSameType = data.get(i).productType.equals(data.get(i - 1).productType);
            if (saleInfos.size() < 3 && isSameType) {
                saleInfos.add(data.get(i));
            } else if (saleInfos.size() >= 3) {
                vo.sales = saleInfos;
                vo.typeName = saleInfos.get(0).productType;
                results.add(vo);
                vo = new SaleTopVO();
                saleInfos = new ArrayList<>();
                if (!isSameType) {
                    saleInfos.add(data.get(i));
                }
            }
            if (i == data.size() - 1 && saleInfos.size() >= 3) {
                vo.sales = saleInfos;
                vo.typeName = saleInfos.get(0).productType;
                results.add(vo);
            }
        }
        return results;
    }

    public List<ProductProfitsInfo> getProductWeekProfits(int productId,String fromDate,String toDate){
        String sql = "select product_id,(sell_price-cost_price)*sales_volume as profits,sales_volume, product_type,date\n" +
                "from daily_sales where product_id = %d and date >= '%s' and date<='%s' order by date;";
        sql = String.format(sql,productId,fromDate,toDate);
        Cursor cursor = db.rawQuery(sql, null);
        List<ProductProfitsInfo> results = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            ProductProfitsInfo info = new ProductProfitsInfo();
            info.productId = cursor.getInt(cursor.getColumnIndex("product_id"));
            info.profits = cursor.getFloat(cursor.getColumnIndex("profits"));
            info.volumes = cursor.getInt(cursor.getColumnIndex("sales_volume"));
            info.productType = cursor.getString(cursor.getColumnIndex("product_type"));
            info.date = cursor.getString(cursor.getColumnIndex("date"));
            results.add(info);
        }
        cursor.close();
        return results;

    }


}
