package bawei.com.electronicmall.bean;

/**
 * @作者 熊金梦
 * @时间 2019/3/4 0004 20:55
 * @
 */
public class CreateOrderBean {

    /**
     * orderId : 20190304190302753195
     * message : 创建订单成功
     * status : 0000
     */

    private String orderId;
    private String message;
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
