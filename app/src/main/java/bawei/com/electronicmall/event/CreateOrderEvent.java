package bawei.com.electronicmall.event;

import java.util.List;

/**
 * @作者 熊金梦
 * @时间 2019/3/1 0001 20:42
 * @
 */
public class CreateOrderEvent {

    public String string1;
    public String string2;
    public String string3;
    public String trim;

    public CreateOrderEvent(String string1, String string2, String string3, String trim) {
        this.string1 = string1;
        this.string2 = string2;
        this.string3 = string3;
        this.trim = trim;
    }

    public CreateOrderEvent() {
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }
}
