package bawei.com.electronicmall.bean;

import java.util.List;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 15:29
 * @
 */
public class WalletBean {

    /**
     * result : {"balance":99999999,"detailList":[{"amount":2,"createTime":1542476199000}]}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
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

    public static class ResultBean {
        /**
         * balance : 99999999
         * detailList : [{"amount":2,"createTime":1542476199000}]
         */

        private double balance;
        private List<DetailListBean> detailList;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 2
             * createTime : 1542476199000
             */

            private double amount;
            private long createTime;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
