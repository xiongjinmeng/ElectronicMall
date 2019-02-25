package bawei.com.electronicmall.util;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 11:05
 * @
 */
public class Api {
    //地址
    public static final String BASE_URL = "http://172.17.8.100/small/";
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
}
