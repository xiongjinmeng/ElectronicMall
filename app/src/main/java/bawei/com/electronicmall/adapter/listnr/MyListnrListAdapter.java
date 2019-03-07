package bawei.com.electronicmall.adapter.listnr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyAllOrderAdapter;
import bawei.com.electronicmall.adapter.MyListnrAdapter;
import bawei.com.electronicmall.bean.ListnrListBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 14:31
 * @
 */
public class MyListnrListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
//    private final ListnrListBean lists;
    private final List<ListnrListBean.OrderListBean> list;
    private double pstr=0.00;

    public MyListnrListAdapter(Context context, List<ListnrListBean.OrderListBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_slistnr_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String orderId = list.get(i).getOrderId();
        List<ListnrListBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        int sizes = detailList.size();
        pstr=0.00;
        for (int j = 0; j < detailList.size(); j++) {
            double price = detailList.get(j).getCommodityPrice();
            int count = detailList.get(j).getCommodityCount();
            double p = price * count;
            pstr += p;
        }
        String s1 = orderId.substring(0, 4);
        String s2 = orderId.substring(4, 6);
        String s3 = orderId.substring(6, 8);
        String s = s1 +"-"+ s2 +"-"+ s3;
        if (viewHolder instanceof Homder){
            Homder homder = (Homder) viewHolder;
            homder.textListnrNum.setText(sizes+"");
            homder.textListnrPrice.setText(pstr+"");
            homder.textListnrOrderid.setText(orderId);
            homder.textListnrTime.setText(s);
            MyAllOrderAdapter orderAdapter = new MyAllOrderAdapter(context,detailList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homder.recyAllLists.setLayoutManager(linearLayoutManager);
            homder.recyAllLists.setAdapter(orderAdapter);
            homder.btnListnrDetler.setVisibility(View.GONE);
            homder.btnListnrHuaqian.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Homder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_listnr_orderid)
        TextView textListnrOrderid;
        @BindView(R.id.text_listnr_time)
        TextView textListnrTime;
        @BindView(R.id.recy_all_lists)
        RecyclerView recyAllLists;
        @BindView(R.id.text_listnr_num)
        TextView textListnrNum;
        @BindView(R.id.text_listnr_price)
        TextView textListnrPrice;
        @BindView(R.id.btn_listnr_detler)
        Button btnListnrDetler;
        @BindView(R.id.btn_listnr_huaqian)
        Button btnListnrHuaqian;

        public Homder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
