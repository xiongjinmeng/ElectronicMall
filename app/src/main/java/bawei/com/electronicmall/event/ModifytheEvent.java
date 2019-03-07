package bawei.com.electronicmall.event;

/**
 * @作者 熊金梦
 * @时间 2019/3/4 0004 16:44
 * @
 */
public class ModifytheEvent {
    public String sd;
    public int id;
    public String realName;
    public String phone;
    public String address;
    public String zipCode;

    public ModifytheEvent(String sd, int id, String realName, String phone, String address, String zipCode) {
        this.sd = sd;
        this.id = id;
        this.realName = realName;
        this.phone = phone;
        this.address = address;
        this.zipCode = zipCode;
    }

    public ModifytheEvent(String sd) {
        this.sd = sd;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
