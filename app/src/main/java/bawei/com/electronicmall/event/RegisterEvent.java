package bawei.com.electronicmall.event;

/**
 * @作者 熊金梦
 * @时间 2019/2/19 0019 20:56
 * @
 */
public class RegisterEvent {
    public String phone;
    public String pwd;
    public String stro;

    public RegisterEvent() {
    }

    public RegisterEvent(String phone, String pwd, String stro) {
        this.phone = phone;
        this.pwd = pwd;
        this.stro = stro;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStro() {
        return stro;
    }

    public void setStro(String stro) {
        this.stro = stro;
    }
}
