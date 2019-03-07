package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.ListnrListBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 10:25
 * @
 */
public class MyAllOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ListnrListBean.OrderListBean.DetailListBean> list;
    private final Context context;

    public MyAllOrderAdapter(Context context, List<ListnrListBean.OrderListBean.DetailListBean> detailList) {
        super();
        this.context = context;
        this.list = detailList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_slistnr_all_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String commodityPic = list.get(i).getCommodityPic();
        String commodityName = list.get(i).getCommodityName();
        double commodityPrice = list.get(i).getCommodityPrice();
        int commodityCount = list.get(i).getCommodityCount();

        if (viewHolder instanceof Homder){
            Homder homder = (Homder) viewHolder;
                String[] split = commodityPic.split(",");
                homder.simpListnrAll.setImageURI(Uri.parse(split[0]));
            homder.textListnrAllName.setText(commodityName);
            homder.textListnrAllPrice.setText("￥"+commodityPrice);
            homder.textListnrNum.setText(commodityCount+"");
            homder.textListnrJia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "没有修改订单接口", Toast.LENGTH_SHORT).show();
                }
            });
            homder.textListnrJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "没有修改订单接口", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Homder extends RecyclerView.ViewHolder {
        @BindView(R.id.simp_listnr_all)
        SimpleDraweeView simpListnrAll;
        @BindView(R.id.text_listnr_all_name)
        TextView textListnrAllName;
        @BindView(R.id.text_listnr_all_price)
        TextView textListnrAllPrice;
        @BindView(R.id.text_listnr_jian)
        TextView textListnrJian;
        @BindView(R.id.text_listnr_num)
        TextView textListnrNum;
        @BindView(R.id.text_listnr_jia)
        TextView textListnrJia;
        public Homder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
