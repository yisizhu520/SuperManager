package wang.steven.supermanager.db;

import java.util.Date;

/**
 * Created by WangJun on 2016/11/13.
 */

public class Product {

    private int id;
    private String name;
    private String type;
    private Date expire_date;
    private float price;
    private int stock;
    private int minStockRatio;
    private int stockNeedAlarm;


    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Product setType(String type) {
        this.type = type;
        return this;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public Product setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Product setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public int getMinStockRatio() {
        return minStockRatio;
    }

    public Product setMinStockRatio(int minStockRatio) {
        this.minStockRatio = minStockRatio;
        return this;
    }

    public int getStockNeedAlarm() {
        return stockNeedAlarm;
    }

    public Product setStockNeedAlarm(int stockNeedAlarm) {
        this.stockNeedAlarm = stockNeedAlarm;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Product setPrice(float price) {
        this.price = price;
        return this;
    }
}
