package bawei.com.electronicmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.adapter.MyDetailsAdapter;
import bawei.com.electronicmall.bean.CommentBean;
import bawei.com.electronicmall.bean.DetailsGoodsBean;
import bawei.com.electronicmall.bean.QueryShoppingCartBean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xbgabanner_details)
    XBanner xbgabannerDetails;
    @BindView(R.id.text_details_price)
    TextView textDetailsPrice;
    @BindView(R.id.text_details_salenum)
    TextView textDetailsSalenum;
    @BindView(R.id.text_details_name)
    TextView textDetailsName;
    @BindView(R.id.text_details_describe)
    TextView textDetailsDescribe;
    @BindView(R.id.text_details_weight)
    TextView textDetailsWeight;
    @BindView(R.id.webview_details)
    WebView webviewDetails;
    @BindView(R.id.text_details_commentNum)
    TextView textDetailsCommentNum;
    @BindView(R.id.image_details)
    ImageView imageDetails;
    @BindView(R.id.recycler_details)
    RecyclerView recyclerDetails;
    @BindView(R.id.relativ_details_purchase)
    RelativeLayout relativDetailsPurchase;
    @BindView(R.id.relativ_details_buy)
    RelativeLayout relativDetailsBuy;
    private Unbinder bind;
    private Presenter presenter;
    private DetailsGoodsBean.ResultBean list;
    private int buo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bind = ButterKnife.bind(this);
        presenter = new Presenter(DetailsActivity.this);
        EventBus.getDefault().register(DetailsActivity.this);
        imageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativDetailsBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //购买

            }
        });
        relativDetailsPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同步购物车
                Map<String, String> map = new HashMap<>();
                presenter.getQuery(Api.QUERY_SHOPPING_CART, map, QueryShoppingCartBean.class);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void DetaiEvent(DetailsEvent event) {
        String num = event.num;
        getDate(num);
    }

    private void getDate(String num) {
        Map<String, String> map = new HashMap<>();
        map.put("commodityId", num);
        presenter.getQuery(Api.DETAILS_GOODS_URL, map, DetailsGoodsBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof QueryShoppingCartBean){
            QueryShoppingCartBean cartBean = (QueryShoppingCartBean) date;
            String status = cartBean.getStatus();
            String message = cartBean.getMessage();
            if (status.equals(ConditionsUtil.CONDITIO)){
                if (cartBean.getResult()==null){
                    Toast.makeText(this, "已加入购物车", Toast.LENGTH_SHORT).show();

                } else {
                    List<QueryShoppingCartBean.ResultBean> shoppinglist = cartBean.getResult();
                    int id = list.getCommodityId();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < shoppinglist.size(); i++) {
                        int commodityId = shoppinglist.get(i).getCommodityId();
                        int count = shoppinglist.get(i).getCount();
                        JSONObject jsonObjet = new JSONObject();
                        try {
                            if (id == commodityId) {
                                buo = 1;
                                jsonObjet.put("commodityId", commodityId);
                                jsonObjet.put("count", count + 1);
                            } else {
                                jsonObjet.put("commodityId", commodityId);
                                jsonObjet.put("count", count);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObjet);
                    }
                    if (buo != 1) {
                        JSONObject jsonObjet = new JSONObject();
                        int commodityId = list.getCommodityId();
                        try {
                            jsonObjet.put("commodityId", commodityId);
                            jsonObjet.put("count", 1);
                            jsonArray.put(jsonObjet);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    String string = jsonArray.toString();
                    Map<String, String> map = new HashMap<>();
                    map.put("data", string);
                    getPutData(map);
                }
            } else {
                int commodityId = list.getCommodityId();
                if (list!=null){
                    try {
                        JSONObject jsonObjet = new JSONObject();
                        jsonObjet.put("commodityId", commodityId);
                        jsonObjet.put("count", 1);
                        String string = jsonObjet.toString();
                        Map<String, String> map = new HashMap<>();
                        map.put("data", string);
                        getPutData(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        } else if (date instanceof DetailsGoodsBean) {
            DetailsGoodsBean bean = (DetailsGoodsBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)) {
                list = bean.getResult();
                if (list == null) {

                } else {
                    //商品详情 html格式
                    String details = list.getDetails();
                    //销量
                    int saleNum = list.getSaleNum();
                    //价格
                    double price = list.getPrice();
                    //商品名称
                    String categoryName = list.getCategoryName();
                    //商品描述
                    String describe = list.getDescribe();
                    //重量
                    int weight = list.getWeight();
                    //评论数
                    int commentNum = list.getCommentNum();

                    textDetailsName.setText(categoryName);
                    textDetailsDescribe.setText(describe);
                    textDetailsPrice.setText("￥" + price);
                    textDetailsWeight.setText(weight + "kg");
                    textDetailsSalenum.setText("已售" + saleNum + "件");
                    textDetailsCommentNum.setText("当前评论总数 " + commentNum);
                    String picture = list.getPicture();
                    String[] split = picture.split(",");
                    final ArrayList<String> strings = new ArrayList<>();
                    for (int j = 0; j < split.length; j++) {
                        strings.add(split[j]);
                    }
                    xbgabannerDetails.setData(strings, null);
                    xbgabannerDetails.loadImage(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            Glide.with(DetailsActivity.this).load(strings.get(position)).into((ImageView) view);
                        }
                    });
//                    webviewDetails.loadData(details, "text/html", "UTF-8");
                    webviewDetails.loadDataWithBaseURL(null, details, "text/html", "UTF-8", null);
                    //支持屏幕缩放
                    webviewDetails.getSettings().setSupportZoom(true);
                    webviewDetails.getSettings().setBuiltInZoomControls(true);
                    webviewDetails.getSettings().setDisplayZoomControls(false);
                    webviewDetails.getSettings().setUseWideViewPort(true);
                    webviewDetails.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    webviewDetails.getSettings().setLoadWithOverviewMode(true);

                }
                if (list!=null){
                    String categoryId = list.getCategoryId();
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("commodityId", categoryId);
                    map1.put("page", "1");
                    map1.put("count", "5");
                    presenter.getQuery(Api.COMMENT_GOODS_URL, map1, CommentBean.class);
                }

            } else {
                Toast.makeText(DetailsActivity.this, "无效链接", Toast.LENGTH_SHORT).show();
            finish();
            }

        } else if (date instanceof CommentBean){
            CommentBean commentBean = (CommentBean) date;
            List<CommentBean.ResultBean> commlist = commentBean.getResult();
            MyDetailsAdapter detailsAdapter = new MyDetailsAdapter(DetailsActivity.this, commlist);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerDetails.setLayoutManager(linearLayoutManager);
            recyclerDetails.setAdapter(detailsAdapter);
        }
    }

    private void getPutData(Map<String, String> map) {
        presenter.put(Api.SYNCHRONOUS_SHOPPING_CART,map,QueryShoppingCartBean.class);
    }

    @Override
    public void onError(String e) {
        Log.e("-----", e);
    }
}
