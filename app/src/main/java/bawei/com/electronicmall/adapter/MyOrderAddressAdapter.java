package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.AddressBean;
import bawei.com.electronicmall.event.RefreshEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/4 0004 20:04
 * @
 */
public class MyOrderAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<AddressBean.ResultBean> list;
    private final Context context;

    public MyOrderAddressAdapter(Context context, List<AddressBean.ResultBean> beanList) {
        super();
        this.context = context;
        this.list = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_oeder_address, viewGroup, false);
        return new Hodert(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        String phone = list.get(i).getPhone();
        String realName = list.get(i).getRealName();
        String address = list.get(i).getAddress();
        int id = list.get(i).getId();
        if (viewHolder instanceof Hodert){
            Hodert hodert = (Hodert) viewHolder;
            hodert.textOraddName.setText(realName);
            hodert.textOraddPhone.setText(phone);
            hodert.textOraddTitie.setText(address);
            hodert.textOraddXzz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new RefreshEvent(id));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Hodert extends RecyclerView.ViewHolder {
        @BindView(R.id.text_oradd_name)
        TextView textOraddName;
        @BindView(R.id.text_oradd_titie)
        TextView textOraddTitie;
        @BindView(R.id.text_oradd_xzz)
        TextView textOraddXzz;
        @BindView(R.id.text_oradd_phone)
        TextView textOraddPhone;

        public Hodert(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
