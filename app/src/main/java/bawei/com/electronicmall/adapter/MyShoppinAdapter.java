package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.QueryShoppingCartBean;
import bawei.com.electronicmall.event.ShoppinEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/1 0001 14:28
 * @
 */
public class MyShoppinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<QueryShoppingCartBean.ResultBean> list;
    private final Context context;

    public MyShoppinAdapter(Context context, List<QueryShoppingCartBean.ResultBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_shoppin, viewGroup, false);
        return new Holderm(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String commodityName = list.get(i).getCommodityName();
        String pic = list.get(i).getPic();
        double price = list.get(i).getPrice();
        int count = list.get(i).getCount();
        boolean check = list.get(i).isCheck();
        if (viewHolder instanceof Holderm) {
            Holderm holderm = (Holderm) viewHolder;
            holderm.checkShoppinBox.setChecked(check);
            holderm.simpShoppin.setImageURI(Uri.parse(pic));
            holderm.textShoppinTitle.setText(commodityName);
            holderm.textShoppinPrice.setText("￥" + price);
            holderm.textShoppinNum.setText(count + "");
            holderm.btnShoppinList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(i);
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new ShoppinEvent(1));
                    holderm.swipmen.quickClose();
                }
            });
            holderm.checkShoppinBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(i).setCheck(holderm.checkShoppinBox.isChecked());
                    EventBus.getDefault().post(new ShoppinEvent(2));
                }
            });
            holderm.textShoppinJia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count1 = list.get(i).getCount();
                    int i1 = count1 + 1;
                    list.get(i).setCount(i1);
                    holderm.textShoppinNum.setText(i1+"");
                    EventBus.getDefault().post(new ShoppinEvent(3));
                }
            });
            holderm.textShoppinJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count1 = list.get(i).getCount();
                    if (count1==1){
                        Toast.makeText(context, "数量最少为1", Toast.LENGTH_SHORT).show();
                    } else {
                        int i1 = count1 - 1;
                        list.get(i).setCount(i1);
                        holderm.textShoppinNum.setText(i1+"");
                        EventBus.getDefault().post(new ShoppinEvent(3));
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holderm extends RecyclerView.ViewHolder {
        @BindView(R.id.swipmen)
        SwipeMenuLayout swipmen;
        @BindView(R.id.check_shoppin_box)
        CheckBox checkShoppinBox;
        @BindView(R.id.simp_shoppin)
        SimpleDraweeView simpShoppin;
        @BindView(R.id.text_shoppin_title)
        TextView textShoppinTitle;
        @BindView(R.id.text_shoppin_price)
        TextView textShoppinPrice;
        @BindView(R.id.text_shoppin_jian)
        TextView textShoppinJian;
        @BindView(R.id.text_shoppin_num)
        TextView textShoppinNum;
        @BindView(R.id.text_shoppin_jia)
        TextView textShoppinJia;
        @BindView(R.id.btn_shoppin_list)
        Button btnShoppinList;

        public Holderm(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
