package wang.steven.supermanager.db;

/**
 * 这里添加描述
 *
 * @author 汪俊
 * @date 2016-11-08
 */

public class Market {

    public static final String ID = "id";
    public static final String CNNAME = "CNName";
    public static final String ENNAME = "ENName";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String REGION = "region";


    private int id;
    private String CNName;
    private String ENName;
    private String address;
    private String phone;
    private String region;


    public int getId() {
        return id;
    }

    public Market setId(int id) {
        this.id = id;
        return this;
    }

    public String getCNName() {
        return CNName;
    }

    public Market setCNName(String CNName) {
        this.CNName = CNName;
        return this;
    }

    public String getENName() {
        return ENName;
    }

    public Market setENName(String ENName) {
        this.ENName = ENName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Market setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Market setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Market setRegion(String region) {
        this.region = region;
        return this;
    }
}
