package bawei.com.electronicmall.daobean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @作者 熊金梦
 * @时间 2019/2/17 0017 19:06
 * @
 */
@Entity
public class SearchGoodsFragmentdaobean {
    @Id(autoincrement = true)//自增
    private long id;//不能是int
    private int commodityId;
    private String commodityName;
    private String masterPic;
    private double price;
    private int saleNum;
    @Generated(hash = 645697621)
    public SearchGoodsFragmentdaobean(long id, int commodityId,
            String commodityName, String masterPic, double price, int saleNum) {
        this.id = id;
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.masterPic = masterPic;
        this.price = price;
        this.saleNum = saleNum;
    }
    @Generated(hash = 839792852)
    public SearchGoodsFragmentdaobean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getCommodityId() {
        return this.commodityId;
    }
    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
    public String getCommodityName() {
        return this.commodityName;
    }
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
    public String getMasterPic() {
        return this.masterPic;
    }
    public void setMasterPic(String masterPic) {
        this.masterPic = masterPic;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getSaleNum() {
        return this.saleNum;
    }
    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }
}
