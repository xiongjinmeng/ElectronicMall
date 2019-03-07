package bawei.com.electronicmall.adapter.listnr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.PublishedActivity;
import bawei.com.electronicmall.bean.ListnrListBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/5 0005 13:53
 * @
 */
class MyCommentsOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ListnrListBean.OrderListBean.DetailListBean> list;
    private final String orderId;

    public MyCommentsOrderAdapter(Context context, List<ListnrListBean.OrderListBean.DetailListBean> detailList, String orderId) {
        super();
        this.context = context;
        this.list = detailList;
        this.orderId=orderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_slistnr_comment_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String commodityPic = list.get(i).getCommodityPic();
        String commodityName = list.get(i).getCommodityName();
        double commodityPrice = list.get(i).getCommodityPrice();
        int commentStatus = list.get(i).getCommentStatus();
        String commodityId = list.get(i).getCommodityId()+"";
        if (viewHolder instanceof Homder){
            Homder homder = (Homder) viewHolder;
            String[] split = commodityPic.split(",");
            homder.simpListnrComment.setImageURI(Uri.parse(split[0]));
            homder.textListnrCommentName.setText(commodityName);
            homder.textListnrCommentPrice.setText("￥"+commodityPrice);
            homder.btnListnrComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,PublishedActivity.class);
                    intent.putExtra("commodityPic",split[0]);
                    intent.putExtra("commodityName",commodityName);
                    intent.putExtra("commodityPrice",commodityPrice+"");
                    intent.putExtra("commodityId",commodityId);
                    intent.putExtra("orderId",orderId);
                    context.startActivity(intent);
//                    Toast.makeText(context, "...", Toast.LENGTH_SHORT).show();
                }
            });
            if (commentStatus==2){
                homder.btnListnrComment.setVisibility(View.GONE);
            } else {
                homder.btnListnrComment.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Homder extends RecyclerView.ViewHolder {
        @BindView(R.id.simp_listnr_comment)
        SimpleDraweeView simpListnrComment;
        @BindView(R.id.text_listnr_comment_name)
        TextView textListnrCommentName;
        @BindView(R.id.text_listnr_comment_price)
        TextView textListnrCommentPrice;
        @BindView(R.id.btn_listnr_comment)
        Button btnListnrComment;
        public Homder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
