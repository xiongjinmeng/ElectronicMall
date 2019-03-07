package bawei.com.electronicmall.adapter.listnr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.ListnrListBean;
import bawei.com.electronicmall.bean.OrderAddDeleteEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 11:48
 * @
 */
public class MyListnrPaymentGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ListnrListBean.OrderListBean> list;

    public MyListnrPaymentGoodsAdapter(Context context, List<ListnrListBean.OrderListBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_slistnr_goods_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String expressCompName = list.get(i).getExpressCompName();
        String expressSn = list.get(i).getExpressSn();
        String orderId = list.get(i).getOrderId();
        List<ListnrListBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        String s1 = orderId.substring(0, 4);
        String s2 = orderId.substring(4, 6);
        String s3 = orderId.substring(6, 8);
        String s = s1 + "-" + s2 + "-" + s3;
        int orderStatus = list.get(i).getOrderStatus();

        if (viewHolder instanceof Homder){
            Homder homder = (Homder) viewHolder;
            if (orderStatus==2) {
                homder.textGoodsTime.setText(s);
                homder.textGoodsOrderid.setText(orderId);
                homder.textListnrGoodsExpr.setText(expressCompName);
                homder.textListnrGoodsExpresssn.setText(expressSn);
                MyGoodsOrderAdapter orderAdapter = new MyGoodsOrderAdapter(context, detailList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                homder.recyGoodsLists.setLayoutManager(linearLayoutManager);
                homder.recyGoodsLists.setAdapter(orderAdapter);

                homder.btnListnrGoods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OrderAddDeleteEvent(orderId, "22"));
                    }
                });
            }else {
                viewHolder.itemView.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Homder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_goods_orderid)
        TextView textGoodsOrderid;
        @BindView(R.id.text_goods_time)
        TextView textGoodsTime;
        @BindView(R.id.recy_goods_lists)
        RecyclerView recyGoodsLists;
        @BindView(R.id.text_listnr_goods_expr)
        TextView textListnrGoodsExpr;
        @BindView(R.id.text_listnr_goods_expresssn)
        TextView textListnrGoodsExpresssn;
        @BindView(R.id.btn_listnr_goods)
        Button btnListnrGoods;
        public Homder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
