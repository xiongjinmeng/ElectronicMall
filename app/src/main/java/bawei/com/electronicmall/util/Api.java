package bawei.com.electronicmall.util;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 11:05
 * @
 */
public class Api {
    //外网mobile.bwstudent.com
    //内网172.17.8.100
    //地址
    public static final String BASE_URL = "http://mobile.bwstudent.com/small/";
//    public static final String BASE_URL = "http://172.17.8.100/small/";
    //首页商品信息列表
    public static final String GOODS_LIST_URL = "commodity/v1/commodityList";
    //根据关键词查询商品信息
    public static final String QUERY_GOODS_URL = "commodity/v1/findCommodityByKeyword";
    //根据商品列表归属标签查询商品信息
    public static final String QUERY_ID_URL = "commodity/v1/findCommodityListByLabel";
    //banner展示列表
    public static final String QUERY_BANNER_URL = "commodity/v1/bannerShow";
    //详情
    public static final String DETAILS_GOODS_URL = "commodity/v1/findCommodityDetailsById";
    //商品评论列表
    public static final String COMMENT_GOODS_URL = "commodity/v1/CommodityCommentList";
    //登录
    public static final String LOGIN = "user/v1/login";
    //注册
    public static final String REGISTERED = "user/v1/register";
    //圈子
    public static final String CLASSIF = "circle/v1/findCircleList";
    //圈子点赞
    public static final String PRAISE = "circle/verify/v1/addCircleGreat";
    //取消点赞
    public static final String PRAISE_CANCEL = "circle/verify/v1/cancelCircleGreat";
    //查询购物车
    public static final String QUERY_SHOPPING_CART = "order/verify/v1/findShoppingCart";
    //同步购物车
    public static final String SYNCHRONOUS_SHOPPING_CART = "order/verify/v1/syncShoppingCart";
    //根据用户ID查询用户信息
    public static final String QUERY_USER_DATE = "user/verify/v1/getUserById";
    //修改用户密码
    public static final String UPMODIFY_USER_PASSWORD = "user/verify/v1/modifyUserPwd";
    //修改昵称
    public static final String UPMODIFY_USER_NICKNAME = "user/verify/v1/modifyUserNick";
    //用户上传头像
    public static final String UPMODIFY_HEAD_PIC = "user/verify/v1/modifyHeadPic";
    //我的圈子
    public static final String FIND_MYCIRCLE = "circle/verify/v1/findMyCircleById";
    //创建订单
    public static final String CREATE_ORDER_SHOPPIN = "order/verify/v1/createOrder";
    //我的足迹
    public static final String MY_BROWSE = "commodity/verify/v1/browseList";
    //收货地址列表http://172.17.8.100/small/user/verify/v1/receiveAddressList
    public static final String RECEIVE_ADD = "user/verify/v1/receiveAddressList";
    //修改收货信息http://172.17.8.100/small/user/verify/v1/changeReceiveAddress
    public static final String CHANGE_RECEIVE = "user/verify/v1/changeReceiveAddress";
    //新增收货地址
    public static final String ADD_RECEIVE = "user/verify/v1/addReceiveAddress";
    //设置默认收货地址
    public static final String SET_DEFAULT = "user/verify/v1/setDefaultReceiveAddress";
    //根据订单状态查询订单信息
    public static final String FIND_ORDER = "order/verify/v1/findOrderListByStatus";
    // 删除订单
    public static final String DELETE_ORDER = "order/verify/v1/deleteOrder";
    //支付
    public static final String PAY_URL = "order/verify/v1/pay";
    //收货
    public static final String COFIRM_RECEIPT = "order/verify/v1/confirmReceipt";
    //查询用户钱包
    public static final String FIND_USER_WALLET = "user/verify/v1/findUserWallet";
    //删除我发表过的圈子
    public static final String DELETE_CIRCLE = "circle/verify/v1/deleteCircle";
    //发布圈子
    public static final String RELEASE_CIRCLE = "circle/verify/v1/releaseCircle";
    // 商品评论
    public static final String ADD_COMMDITY = "commodity/verify/v1/addCommodityComment";
}
