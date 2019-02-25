package bawei.com.electronicmall.fragment.adaptertwo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bawei.com.electronicmall.DetailsActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.GoodsListBean;
import bawei.com.electronicmall.event.DetailsEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/2/21 0021 16:06
 * @
 */
class MyRecyelPzshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<GoodsListBean.ResultBean.PzshBean.CommodityListBeanX> list;
    private final Context context;

    public MyRecyelPzshAdapter(Context context, List<GoodsListBean.ResultBean.PzshBean.CommodityListBeanX> pzshList) {
        super();
        this.context = context;
        this.list = pzshList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_layout_pzsh, viewGroup, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String name = list.get(i).getCommodityName();
        double price = list.get(i).getPrice();
        String pic = list.get(i).getMasterPic();
        final String commodityId = list.get(i).getCommodityId() + "";
        if (viewHolder instanceof HolderView){
            HolderView holderView = (HolderView) viewHolder;
            holderView.imageGrid3.setImageURI(Uri.parse(pic));
            holderView.textGridName3.setText(name);
            holderView.textGridPras3.setText("￥"+price);
            holderView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new DetailsEvent(commodityId));
                    Intent intent = new Intent(context,DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        @BindView(R.id.image_grid3)
        SimpleDraweeView imageGrid3;
        @BindView(R.id.text_grid_name3)
        TextView textGridName3;
        @BindView(R.id.text_grid_pras3)
        TextView textGridPras3;
        public HolderView(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
