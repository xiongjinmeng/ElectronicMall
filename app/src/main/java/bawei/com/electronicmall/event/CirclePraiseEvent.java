package bawei.com.electronicmall.event;

/**
 * @作者 熊金梦
 * @时间 2019/2/20 0020 15:50
 * @
 */
public class CirclePraiseEvent {
    public String sid;
    public String st;

    public CirclePraiseEvent(String sid, String st) {
        this.sid = sid;
        this.st = st;
    }

    public CirclePraiseEvent() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}
