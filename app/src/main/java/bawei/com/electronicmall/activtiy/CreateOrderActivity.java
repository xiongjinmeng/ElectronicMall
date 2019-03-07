package bawei.com.electronicmall.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyOrdAdapter;
import bawei.com.electronicmall.adapter.MyOrderAddressAdapter;
import bawei.com.electronicmall.bean.AddressBean;
import bawei.com.electronicmall.bean.CreateOrderBean;
import bawei.com.electronicmall.bean.QueryShoppingCartBean;
import bawei.com.electronicmall.bean.UserDateBean;
import bawei.com.electronicmall.event.CreateOrderEvent;
import bawei.com.electronicmall.event.OrderEvent;
import bawei.com.electronicmall.event.RefreshEvent;
import bawei.com.electronicmall.event.ShoppinEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import bawei.com.electronicmall.util.HttpRetrofitUile;
import bawei.com.electronicmall.util.WayApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateOrderActivity extends AppCompatActivity implements IView {

    @BindView(R.id.text_order)
    TextView textOrder;
    @BindView(R.id.recy_order)
    RecyclerView recyOrder;
    @BindView(R.id.text_order_num)
    TextView textOrderNum;
    @BindView(R.id.text_order_price)
    TextView textOrderPrice;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.text_name_order)
    TextView textNameOrder;
    @BindView(R.id.text_phone_order)
    TextView textPhoneOrder;
    @BindView(R.id.text_address_order)
    TextView textAddressOrder;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.recycl_order_list)
    RecyclerView recyclOrderList;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.image_shop_order)
    CheckBox imageShopOrder;
    private Unbinder bind;
    private Presenter presenter;
    private List<QueryShoppingCartBean.ResultBean> list;
    private MyOrdAdapter adapter;
    private String string1;
    private double pter = 0.00;
    private int id = 0;
    private double pterss = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        linear2.setVisibility(View.GONE);
        linear3.setVisibility(View.GONE);
        textOrder.setVisibility(View.VISIBLE);
        imageShopOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageShopOrder.isChecked()){
                    linear3.setVisibility(View.VISIBLE);
                } else {
                    linear3.setVisibility(View.GONE);
                }
            }
        });
        textOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateOrderActivity.this, MyAddAddressActivity.class);
                startActivity(intent);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pterss=0.00;
                if (list==null){
                    Toast.makeText(CreateOrderActivity.this, "错误", Toast.LENGTH_SHORT).show();
                }
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jsonObjet = new JSONObject();
                    int commodityId = list.get(i).getCommodityId();
//                    int count = list.get(i).getCount();
                    double price = list.get(i).getPrice();
                    int count = list.get(i).getCount();
                    double p = price * count;
                    pterss += p;
                    try {
                        jsonObjet.put("commodityId",commodityId);
                        jsonObjet.put("amount",count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObjet);
                }
                if (id == 0) {
                    Toast.makeText(CreateOrderActivity.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
                } else {
                    String s = pterss + "";
                    String sids = id + "";
                    String string = jsonArray.toString();
                    Map<String, String> map = new HashMap<>();
                    map.put("orderInfo", string);
                    map.put("totalPrice", s);
                    map.put("addressId", sids);
                    presenter.postone(Api.CREATE_ORDER_SHOPPIN,map,CreateOrderBean.class);
//                    presenter.posttwo(Api.CREATE_ORDER_SHOPPIN, string,pterss,id, CreateOrderBean.class);
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void OerEvent(OrderEvent event) {
        getPrice();
    }

    private void getPrice() {
        pter = 0.00;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                double price = list.get(i).getPrice();
                int count = list.get(i).getCount();
                double p = price * count;
                pter += p;
//                Log.e("----------","12345+6879"+check);
            }
            textOrderNum.setText(list.size() + "");
            textOrderPrice.setText("￥" + pter);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OerAddEvent(RefreshEvent event) {
        int id = event.id;
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        presenter.postone(Api.SET_DEFAULT, map, UserDateBean.class);


    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void OrderEvent(CreateOrderEvent event) {
        string1 = event.string1;
        String string2 = event.string2;
        String string3 = event.string3;
        String trim = event.trim;
        textOrderPrice.setText(trim);
        presenter = new Presenter(this);
        Map<String, String> map = new HashMap<>();
        presenter.getQuery(Api.QUERY_SHOPPING_CART, map, QueryShoppingCartBean.class);
        Map<String, String> map3 = new HashMap<>();
        map3.put("data", string3);
        presenter.put(Api.SYNCHRONOUS_SHOPPING_CART, map3, UserDateBean.class);
        presenter.getQuery(Api.RECEIVE_ADD, map, AddressBean.class);
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
        if (date instanceof UserDateBean){
            UserDateBean userDateBean = (UserDateBean) date;
            String status = userDateBean.getStatus();
            if (status.equals("0000")){
                linear3.setVisibility(View.GONE);
                Map<String, String> map1 = new HashMap<>();
                presenter.getQuery(Api.RECEIVE_ADD, map1, AddressBean.class);
            }
        } else if (date instanceof AddressBean) {
            AddressBean addressBean = (AddressBean) date;
            String status = addressBean.getStatus();
            if (status.equals("0000")) {
                if (addressBean.getResult() == null) {
                    linear2.setVisibility(View.GONE);
                    textOrder.setVisibility(View.VISIBLE);
                } else {
                    linear2.setVisibility(View.VISIBLE);
                    textOrder.setVisibility(View.GONE);
                    List<AddressBean.ResultBean> beanList = addressBean.getResult();
                    for (int i = 0; i < beanList.size(); i++) {
                        int whetherDefault = beanList.get(i).getWhetherDefault();
                        if (whetherDefault == 1) {
                            id = beanList.get(i).getId();
                            String realName = beanList.get(i).getRealName();
                            String phone = beanList.get(i).getPhone();
                            String address = beanList.get(i).getAddress();
                            textNameOrder.setText(realName);
                            textPhoneOrder.setText(phone);
                            textAddressOrder.setText(address);
                        }
                    }
                    MyOrderAddressAdapter addressAdapter = new MyOrderAddressAdapter(CreateOrderActivity.this, beanList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclOrderList.setLayoutManager(layoutManager);
                    recyclOrderList.setAdapter(addressAdapter);
                }
            }


        } else if (date instanceof CreateOrderBean) {
            CreateOrderBean orderBean = (CreateOrderBean) date;
            String status = orderBean.getStatus();
            String message = orderBean.getMessage();
            if (status.equals("0000")) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("data", string1);
                presenter.put(Api.SYNCHRONOUS_SHOPPING_CART, map1, UserDateBean.class);
                EventBus.getDefault().postSticky(new ShoppinEvent(4));
                finish();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        } else if (date instanceof UserDateBean) {
            UserDateBean dateBean = (UserDateBean) date;
            String message = dateBean.getMessage();
            String status = dateBean.getStatus();
//            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else if (date instanceof QueryShoppingCartBean) {
            QueryShoppingCartBean cartBean = (QueryShoppingCartBean) date;
            String status = cartBean.getStatus();
            String message = cartBean.getMessage();
            if (status.equals(ConditionsUtil.CONDITIO)) {
                if (cartBean.getResult() == null) {
//                    Toast.makeText(CreateOrderActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    list = cartBean.getResult();
                    textOrderNum.setText(list.size() + "");
                    adapter = new MyOrdAdapter(CreateOrderActivity.this, list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateOrderActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyOrder.setLayoutManager(linearLayoutManager);
                    recyOrder.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void onError(String e) {
        Log.e("-----", e);
    }
}
