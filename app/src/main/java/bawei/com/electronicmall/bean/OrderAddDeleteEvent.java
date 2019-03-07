package bawei.com.electronicmall.bean;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 11:02
 * @
 */
public class OrderAddDeleteEvent {
    public String orderId;
    public String sto;

    public OrderAddDeleteEvent(String orderId, String sto) {
        this.orderId = orderId;
        this.sto = sto;
    }
}
