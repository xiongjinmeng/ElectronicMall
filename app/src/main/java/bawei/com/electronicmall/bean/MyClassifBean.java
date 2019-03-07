package bawei.com.electronicmall.bean;

import java.util.List;

/**
 * @作者 熊金梦
 * @时间 2019/3/7 0007 11:55
 * @
 */
public class MyClassifBean {

    /**
     * result : [{"commodityId":6,"content":"给大家推荐一个好商品","createTime":1551991522000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg","id":767,"image":"","nickName":"天情","userId":195},{"commodityId":6,"content":"给大家推荐一个好商品","createTime":1551991518000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg","id":766,"image":"http://172.17.8.100/images/small/circle_pic/2019-03-07/5372120190307144518.jpg","nickName":"天情","userId":195},{"commodityId":6,"content":"给大家推荐一个好商品","createTime":1551991510000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg","id":765,"image":"http://172.17.8.100/images/small/circle_pic/2019-03-07/5814620190307144510.jpg","nickName":"天情","userId":195},{"commodityId":6,"content":"给大家推荐一个好商品","createTime":1551991500000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg","id":764,"image":"http://172.17.8.100/images/small/circle_pic/2019-03-07/4932020190307144500.jpg,http://172.17.8.100/images/small/circle_pic/2019-03-07/6870920190307144500.jpg","nickName":"天情","userId":195},{"commodityId":6,"content":"给大家推荐一个好商品","createTime":1551991482000,"greatNum":0,"headPic":"http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg","id":763,"image":"http://172.17.8.100/images/small/circle_pic/2019-03-07/0581420190307144442.jpg,http://172.17.8.100/images/small/circle_pic/2019-03-07/1396220190307144442.jpg,http://172.17.8.100/images/small/circle_pic/2019-03-07/4816720190307144442.jpg,http://172.17.8.100/images/small/circle_pic/2019-03-07/0346520190307144442.jpg,http://172.17.8.100/images/small/circle_pic/2019-03-07/0873420190307144442.jpg","nickName":"天情","userId":195}]
     * message : 查詢成功
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
         * commodityId : 6
         * content : 给大家推荐一个好商品
         * createTime : 1551991522000
         * greatNum : 0
         * headPic : http://172.17.8.100/images/small/head_pic/2019-02-27/20190227200822.jpg
         * id : 767
         * image :
         * nickName : 天情
         * userId : 195
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
        private boolean sicktp;

        public boolean isSicktp() {
            return sicktp;
        }

        public void setSicktp(boolean sicktp) {
            this.sicktp = sicktp;
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
    }
}
