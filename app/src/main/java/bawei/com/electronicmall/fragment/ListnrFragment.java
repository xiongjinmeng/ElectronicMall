package bawei.com.electronicmall.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.MyPersonalActivity;
import bawei.com.electronicmall.adapter.MyListnrAdapter;
import bawei.com.electronicmall.adapter.listnr.MyListnrCommentsAdapter;
import bawei.com.electronicmall.adapter.listnr.MyListnrListAdapter;
import bawei.com.electronicmall.adapter.listnr.MyListnrPaymentGoodsAdapter;
import bawei.com.electronicmall.bean.ListnrListBean;
import bawei.com.electronicmall.bean.OrderAddDeleteEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListnrFragment extends Fragment implements IView {


    @BindView(R.id.linear_orders)
    LinearLayout linearOrders;
    @BindView(R.id.linear_payment)
    LinearLayout linearPayment;
    @BindView(R.id.linear_thegoods)
    LinearLayout linearThegoods;
    @BindView(R.id.linear_evaluation)
    LinearLayout linearEvaluation;
    @BindView(R.id.linear_complete)
    LinearLayout linearComplete;
    Unbinder unbinder;
    @BindView(R.id.recy_listne)
    RecyclerView recyListne;
    private View view;
    private Presenter presenter;
    private int quse = 0;
    private PopupWindow popupWindow;

    public ListnrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listnr, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
       getdate();
        final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pwas_listnr, null, false);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCircleEvent(OrderAddDeleteEvent event){
        String orderId = event.orderId;
        String sto = event.sto;
        if (sto.equals("99")){

//            popupWindow.showAtLocation(linearComplete, Gravity.BOTTOM, 0, 0);

            Map<String, String> map = new HashMap<>();
            map.put("orderId",orderId);
            map.put("payType","1");
            presenter.postone(Api.PAY_URL, map, ListnrListBean.class);
        } else if (sto.equals("66")){
            Map<String, String> map = new HashMap<>();
            map.put("orderId",orderId);
            presenter.deleteone(Api.DELETE_ORDER, map, ListnrListBean.class);
        } else if (sto.equals("22")){
            Map<String, String> map = new HashMap<>();
            map.put("orderId",orderId);
            presenter.put(Api.COFIRM_RECEIPT, map, ListnrListBean.class);
        }

    }

    private void getdate() {
        Map<String, String> map = new HashMap<>();
        map.put("status", quse+"");
        map.put("page", "1");
        map.put("count", "50");
        presenter.getQuery(Api.FIND_ORDER, map, ListnrListBean.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.linear_orders, R.id.linear_payment, R.id.linear_thegoods, R.id.linear_evaluation, R.id.linear_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_orders:
                quse=0;
                getdate();
                break;
            case R.id.linear_payment:
                quse=1;
                getdate();
                break;
            case R.id.linear_thegoods:
                quse=2;
                getdate();
                break;
            case R.id.linear_evaluation:
                quse=3;
                getdate();
                break;
            case R.id.linear_complete:
                quse=9;
                getdate();
                break;
        }
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof ListnrListBean) {
            ListnrListBean bean = (ListnrListBean) date;
            String status = bean.getStatus();
            if (status.equals("0000")) {
                if (bean.getOrderList()==null){
                    getdate();
                } else {
                    List<ListnrListBean.OrderListBean> list = bean.getOrderList();
                    if (quse == 0) {
                        MyListnrListAdapter adapter = new MyListnrListAdapter(getActivity(), list);
                        recyListne.setAdapter(adapter);
                    } else if (quse == 1) {
                        MyListnrAdapter adapter = new MyListnrAdapter(getActivity(), list);
                        recyListne.setAdapter(adapter);
                    } else if (quse == 2) {
                        MyListnrPaymentGoodsAdapter adapter = new MyListnrPaymentGoodsAdapter(getActivity(), list);
                        recyListne.setAdapter(adapter);
                    } else if (quse == 3) {
                        MyListnrCommentsAdapter adapter = new MyListnrCommentsAdapter(getActivity(), list);
                        recyListne.setAdapter(adapter);
                    } else if (quse == 9) {
                        MyListnrCommentsAdapter adapter = new MyListnrCommentsAdapter(getActivity(), list);
                        recyListne.setAdapter(adapter);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyListne.setLayoutManager(linearLayoutManager);
                }
            } else {
                Toast.makeText(getActivity(), "订单错误", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onError(String e) {
        Log.e("---------", e);
    }
}
