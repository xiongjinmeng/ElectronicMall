package bawei.com.electronicmall.event;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 16:38
 * @
 */
public class DetailsEvent {
    public String num;

    public DetailsEvent(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
