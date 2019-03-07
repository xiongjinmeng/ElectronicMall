package bawei.com.electronicmall.event;

/**
 * @作者 熊金梦
 * @时间 2019/2/26 0026 20:07
 * @
 */
public class MyUserEvent {
    public String headPic;
    public String nickName;

    public MyUserEvent(String headPic, String nickName) {
        this.headPic = headPic;
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
