package bawei.com.electronicmall.bean;

import java.util.List;

/**
 * @作者 熊金梦
 * @时间 2019/2/20 0020 14:58
 * @
 */
public class ClassifBean {

    /**
     * result : [{"commodityId":3,"content":"好用，就是太贵","createTime":1542639261000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2018-11-17/20181117120315.jpg","id":4,"image":"http://172.17.8.100/images/small/circle_pic/2018-11-19/3509720181119085421.jpg,http://172.17.8.100/images/small/circle_pic/2018-11-19/7196220181119085421.jpg","nickName":"风情的人","userId":1,"whetherGreat":2}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodityId : 3
         * content : 好用，就是太贵
         * createTime : 1542639261000
         * greatNum : 0
         * headPic : http://172.17.8.100/images/small/head_pic/2018-11-17/20181117120315.jpg
         * id : 4
         * image : http://172.17.8.100/images/small/circle_pic/2018-11-19/3509720181119085421.jpg,http://172.17.8.100/images/small/circle_pic/2018-11-19/7196220181119085421.jpg
         * nickName : 风情的人
         * userId : 1
         * whetherGreat : 2
         */

        private int commodityId;
        private String content;
        private long createTime;
        private int greatNum;
        private String headPic;
        private int id;
        private String image;
        private String nickName;
        private int userId;
        private int whetherGreat;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWhetherGreat() {
            return whetherGreat;
        }

        public void setWhetherGreat(int whetherGreat) {
            this.whetherGreat = whetherGreat;
        }
    }
}
