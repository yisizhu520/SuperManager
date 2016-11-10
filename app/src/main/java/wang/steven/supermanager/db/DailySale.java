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
    public static final String MARKET_ID = "marketId";
    public static final String VOLUME = "volume";
    public static final String DATE = "date";

    private int id;
    private int marketId;
    private float volume;
    private Date date;

    public int getId() {
        return id;
    }

    public DailySale setId(int id) {
        this.id = id;
        return this;
    }

    public int getMarketId() {
        return marketId;
    }

    public DailySale setMarketId(int marketId) {
        this.marketId = marketId;
        return this;
    }

    public float getVolume() {
        return volume;
    }

    public DailySale setVolume(float volume) {
        this.volume = volume;
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
