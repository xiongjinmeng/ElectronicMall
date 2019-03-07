package bawei.com.electronicmall.adapter;

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

import java.text.SimpleDateFormat;
import java.util.List;

import bawei.com.electronicmall.DetailsActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.MyFootprintActivity;
import bawei.com.electronicmall.bean.FootprintBean;
import bawei.com.electronicmall.event.DetailsEvent;

/**
 * @作者 熊金梦
 * @时间 2019/3/4 0004 16:03
 * @
 */
public class MyFootpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<FootprintBean.ResultBean> list;
    private final Context context;

    public MyFootpAdapter(Context context, List<FootprintBean.ResultBean> result) {
        super();
        this.context=context;
        this.list=result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_myfootp, viewGroup, false);
        return new Holdert(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof Holdert){
            Holdert holdert = (Holdert) viewHolder;
            int num = list.get(i).getBrowseNum();
            long browseTime = list.get(i).getBrowseTime();
            String commodityName = list.get(i).getCommodityName();
            String commodityId = list.get(i).getCommodityId()+"";
            double price = list.get(i).getPrice();
            String masterPic = list.get(i).getMasterPic();
            holdert.simp_myfootp_image.setImageURI(Uri.parse(masterPic));
            holdert.text_myfootp_data.setText(commodityName);
            holdert.text_myfootp_price.setText("￥"+price);
            holdert.text_myfootp_num.setText("已游览"+num+"次");
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String time= dateFormat.format(browseTime);
            holdert.text_myfootp_time.setText(time);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    private class Holdert extends RecyclerView.ViewHolder {
        private final SimpleDraweeView simp_myfootp_image;
        private final TextView text_myfootp_data;
        private final TextView text_myfootp_price;
        private final TextView text_myfootp_num;
        private final TextView text_myfootp_time;

        public Holdert(View view) {
            super(view);
            simp_myfootp_image = view.findViewById(R.id.simp_myfootp_image);
            text_myfootp_data = view.findViewById(R.id.text_myfootp_data);
            text_myfootp_price = view.findViewById(R.id.text_myfootp_price);
            text_myfootp_num = view.findViewById(R.id.text_myfootp_num);
            text_myfootp_time = view.findViewById(R.id.text_myfootp_time);
        }
    }
}
