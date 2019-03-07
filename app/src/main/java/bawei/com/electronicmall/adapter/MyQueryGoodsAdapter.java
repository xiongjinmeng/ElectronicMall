package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.SearchGoodsBean;
import bawei.com.electronicmall.daobean.SearchGoodsFragmentdaobean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 11:39
 * @
 */
public class MyQueryGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
//    private final List<SearchGoodsFragmentdaobean> list;
    private final List<SearchGoodsBean.ResultBean> list;

    public MyQueryGoodsAdapter(Context context, List<SearchGoodsBean.ResultBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_query_goods_fragment_list, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyHolder) {
            MyHolder holder = (MyHolder) viewHolder;
            String masterPic = list.get(i).getMasterPic();
            int commodityId = list.get(i).getCommodityId();
            double price = list.get(i).getPrice();
            String name = list.get(i).getCommodityName();
            int saleNum = list.get(i).getSaleNum();
            //重试,创建DraweeController对象
            Uri parse = Uri.parse(masterPic);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(parse)//URL地址
                    .setTapToRetryEnabled(true)//开启点击重试
                    .setOldController(holder.simpleQueryGoods.getController())
                    .build();//构建
            holder.simpleQueryGoods.setController(controller);
            holder.textTitleQueryGoods.setText(name);
            holder.textPriceQueryGoods.setText("￥"+price);
            holder.textNumberQueryGoods.setText("已售"+saleNum+"件");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClicklister!=null){
                        onClicklister.Clicklister(commodityId);
                    }
                }
            });

        }
    }
    public onClicklister onClicklister;
    public void setClic(onClicklister clicklister){
        this.onClicklister=clicklister;
    }
    public interface onClicklister{
        void Clicklister(int num);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_query_goods)
        SimpleDraweeView simpleQueryGoods;
        @BindView(R.id.text_title_query_goods)
        TextView textTitleQueryGoods;
        @BindView(R.id.text_price_query_goods)
        TextView textPriceQueryGoods;
        @BindView(R.id.text_number_query_goods)
        TextView textNumberQueryGoods;
        public MyHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
