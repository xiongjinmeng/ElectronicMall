package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.MyWalletActivity;
import bawei.com.electronicmall.bean.WalletBean;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 15:32
 * @
 */
public class MyWalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<WalletBean.ResultBean.DetailListBean> list;
    private final Context context;

    public MyWalletAdapter(Context context, List<WalletBean.ResultBean.DetailListBean> detailList) {
        super();
        this.context=context;
        this.list=detailList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wallet, viewGroup, false);
        return new Hodlert(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof Hodlert){
            Hodlert hodlert = (Hodlert) viewHolder;
            double amount = list.get(i).getAmount();
            long createTime = list.get(i).getCreateTime();
            hodlert.text_price_wallet.setText("￥"+amount);
            String format = "yyyy-MM-dd HH";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String time= dateFormat.format(createTime);
            hodlert.text_time_wallet.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class Hodlert extends RecyclerView.ViewHolder {
        private final TextView text_price_wallet;
        private final TextView text_time_wallet;
        private final ImageView image_wallet;
        public Hodlert(View view) {
            super(view);
            text_price_wallet = view.findViewById(R.id.text_price_wallet);
            text_time_wallet = view.findViewById(R.id.text_time_wallet);
            image_wallet = view.findViewById(R.id.image_wallet);
        }
    }
}
