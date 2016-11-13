package wang.steven.supermanager.db;

import java.util.Date;

/**
 * 这里添加描述
 *
 * @author 汪俊
 * @date 2016-11-08
 */

public class DailySale {


    public static final String ID = "id";
    public static final String PRODUCT_ID = "product_id";
    public static final String VOLUME = "sales_volume";
    public static final String DATE = "date";
    public static final String SELL_PRICE = "sell_price";
    public static final String COST_PRICE = "cost_price";

    private int id;
    private int productId;
    private int volume;
    private float sellPrice;
    private float costPrice;
    private Date date;

    public int getId() {
        return id;
    }

    public DailySale setId(int id) {
        this.id = id;
        return this;
    }

    public int getProductId() {
        return productId;
    }

    public DailySale setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getVolume() {
        return volume;
    }

    public DailySale setVolume(int volume) {
        this.volume = volume;
        return this;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public DailySale setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public DailySale setCostPrice(float costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public DailySale setDate(Date date) {
        this.date = date;
        return this;
    }
}
