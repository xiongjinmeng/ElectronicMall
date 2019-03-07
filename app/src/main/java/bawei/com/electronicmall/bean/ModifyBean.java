package bawei.com.electronicmall.bean;

/**
 * @作者 熊金梦
 * @时间 2019/2/27 0027 20:35
 * @
 */
public class ModifyBean {

    /**
     * headPath : http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg
     * message : 上传成功
     * status : 0000
     */

    private String headPath;
    private String message;
    private String status;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
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
