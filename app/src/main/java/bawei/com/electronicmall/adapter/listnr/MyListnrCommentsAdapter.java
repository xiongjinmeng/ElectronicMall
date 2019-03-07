package bawei.com.electronicmall.adapter.listnr;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
 * @时间 2019/3/5 0005 13:27
 * @
 */
public class MyListnrCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ListnrListBean.OrderListBean> list;

    public MyListnrCommentsAdapter(Context context, List<ListnrListBean.OrderListBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_slistnr_comments_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String orderId = list.get(i).getOrderId();
        List<ListnrListBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        String s1 = orderId.substring(0, 4);
        String s2 = orderId.substring(4, 6);
        String s3 = orderId.substring(6, 8);
        String s4 = orderId.substring(8, 10);
        String s5 = orderId.substring(10, 12);
        String s = s1 +"-"+ s2 +"-"+ s3 +" "+ s4 +":"+s5;
        int orderStatus = list.get(i).getOrderStatus();
        if (viewHolder instanceof Homder) {
            Homder homder = (Homder) viewHolder;
            if (orderStatus == 3 || orderStatus == 9) {

            homder.textListnrCommentOrderid.setText(orderId);
            homder.textListnrCommentTime.setText(s);
            MyCommentsOrderAdapter orderAdapter = new MyCommentsOrderAdapter(context, detailList,orderId);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homder.recyComentLists.setLayoutManager(linearLayoutManager);
            homder.recyComentLists.setAdapter(orderAdapter);
            final View inflate = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow_comment, null, false);
            PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            Button btn_btn = inflate.findViewById(R.id.btn_btn_comment);
            if (orderStatus == 9) {
                popupWindow.showAsDropDown(homder.imageListnr);
            }
            btn_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OrderAddDeleteEvent(orderId, "66"));
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
        @BindView(R.id.text_listnr_comment_orderid)
        TextView textListnrCommentOrderid;
        @BindView(R.id.recy_coment_lists)
        RecyclerView recyComentLists;
        @BindView(R.id.text_listnr_comment_time)
        TextView textListnrCommentTime;
        @BindView(R.id.image_listnr)
        ImageView imageListnr;
        public Homder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
