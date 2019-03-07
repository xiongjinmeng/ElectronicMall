package bawei.com.electronicmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

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

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.CreateOrderActivity;
import bawei.com.electronicmall.adapter.MyShoppinAdapter;
import bawei.com.electronicmall.bean.QueryShoppingCartBean;
import bawei.com.electronicmall.bean.UserDateBean;
import bawei.com.electronicmall.event.CreateOrderEvent;
import bawei.com.electronicmall.event.ShoppinEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppinFragment extends Fragment implements IView {


    @BindView(R.id.xrecycle_shoppin)
    RecyclerView xrecycleShoppin;
    @BindView(R.id.check_box_shoppin)
    CheckBox checkBoxShoppin;
    @BindView(R.id.text_price_shoppin)
    TextView textPriceShoppin;
    @BindView(R.id.button_shoppin)
    Button buttonShoppin;
    Unbinder unbinder;
    private View view;
    private Presenter presenter;
    private List<QueryShoppingCartBean.ResultBean> list;
    private MyShoppinAdapter adapter;
    private double pter;

    public ShoppinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shoppin, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
        checkBoxShoppin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pter = 0.00;
                if (list!=null){
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck(checkBoxShoppin.isChecked());
                        double price = list.get(i).getPrice();
                        int count = list.get(i).getCount();
                        double p = price * count;
                        pter +=p;
                    }
                    if (checkBoxShoppin.isChecked()){
                        textPriceShoppin.setText("￥"+pter);
                    } else {
                        pter = 0.00;
                        textPriceShoppin.setText("￥"+pter);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        buttonShoppin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray jsonArray = new JSONArray();
                JSONArray jsonArray2 = new JSONArray();
                JSONArray jsonArray3 = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    boolean check = list.get(i).isCheck();
                    int commodityId = list.get(i).getCommodityId();
                    int count = list.get(i).getCount();
                    if (check){
                        JSONObject jsonObjet = new JSONObject();
                        try {
                            jsonObjet.put("commodityId",commodityId);
                            jsonObjet.put("count",count);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray2.put(jsonObjet);

                    } else {
                        JSONObject jsonObjet = new JSONObject();
                        try {
                            jsonObjet.put("commodityId",commodityId);
                            jsonObjet.put("count",count);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObjet);
                    }
                    JSONObject jsonObjet = new JSONObject();
                    try {
                        jsonObjet.put("commodityId",commodityId);
                        jsonObjet.put("count",count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray3.put(jsonObjet);
                }
                String string1 = jsonArray.toString();
                String string3 = jsonArray3.toString();
                String string2 = jsonArray2.toString();
//                Log.e("-----",string);
//                Log.e("-----",string2);
                if (string2.equals("[]")){
                    Toast.makeText(getActivity(), "请选择商品！", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("data", string2);
                    presenter.put(Api.SYNCHRONOUS_SHOPPING_CART,map,UserDateBean.class);
                    String trim = textPriceShoppin.getText().toString().trim();
                    if (string1.equals("[]")){

                        try {
                            JSONArray jsonArray9 = new JSONArray();
                            JSONObject jsonObjet9 = new JSONObject();
                            jsonObjet9.put("commodityId",0);
                            jsonObjet9.put("count",1);
                            jsonArray9.put(jsonObjet9);
                            String string = jsonArray9.toString();
                            EventBus.getDefault().postSticky(new CreateOrderEvent(string,string2,string3,trim));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        EventBus.getDefault().postSticky(new CreateOrderEvent(string1,string2,string3,trim));
                    }
                    Intent intent = new Intent(getActivity(),CreateOrderActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()){
            checkBoxShoppin.setChecked(false);
            pter = 0.00;
            textPriceShoppin.setText("￥"+pter);
            Map<String, String> map = new HashMap<>();
            presenter.getQuery(Api.QUERY_SHOPPING_CART,map,QueryShoppingCartBean.class);
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ShoppinEvent(ShoppinEvent event) {
        int st = event.st;
        if (st==1){
            getSynchronous();
        } else if (st==2){
            checkBoxShoppin.setChecked(false);
            getPrice();
        } else if (st==3){
            getPrice();
            getSynchronous();
        } else if (st==4){
            checkBoxShoppin.setChecked(false);
            Map<String, String> map = new HashMap<>();
            presenter.getQuery(Api.QUERY_SHOPPING_CART,map,QueryShoppingCartBean.class);

        }



    }
    private void getPrice() {
        pter = 0.00;
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                boolean check = list.get(i).isCheck();
                if (check){
                    double price = list.get(i).getPrice();
                    int count = list.get(i).getCount();
                    double p = price * count;
                    pter +=p;
                }
//                Log.e("----------","12345+6879"+check);
            }

            textPriceShoppin.setText("￥"+pter);
        }


    }

    private void getSynchronous() {
        if (list!=null){
            JSONArray jsonArray = new JSONArray();
            if (list.size()==0){
                try {
                    JSONObject jsonObjet9 = new JSONObject();
                    jsonObjet9.put("commodityId",0);
                    jsonObjet9.put("count",1);
                    jsonArray.put(jsonObjet9);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    int commodityId = list.get(i).getCommodityId();
                    int count = list.get(i).getCount();
                    JSONObject jsonObjet = new JSONObject();
                    try {
                        jsonObjet.put("commodityId",commodityId);
                        jsonObjet.put("count",count);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObjet);
                }
            }

            String string = jsonArray.toString();
//            Log.e("------",string);
            Map<String, String> map = new HashMap<>();
            map.put("data", string);
            presenter.put(Api.SYNCHRONOUS_SHOPPING_CART,map,UserDateBean.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof UserDateBean){
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
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    list = cartBean.getResult();
                    adapter = new MyShoppinAdapter(getActivity(), list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    xrecycleShoppin.setLayoutManager(linearLayoutManager);
                    xrecycleShoppin.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }
        }

    }

    @Override
    public void onError(String e) {
        Log.e("-----", e);
    }
}
