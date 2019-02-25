package bawei.com.electronicmall.fragment.eventtwo;

/**
 * @作者 熊金梦
 * @时间 2019/2/21 0021 13:50
 * @
 */
public class GoodsBeanEvent {
    public String qb;
    public String tp;

    public GoodsBeanEvent(String qb, String tp) {
        this.qb = qb;
        this.tp = tp;
    }

    public String getQb() {
        return qb;
    }

    public void setQb(String qb) {
        this.qb = qb;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }
}
