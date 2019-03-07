package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.MyAddAddressActivity;
import bawei.com.electronicmall.activtiy.MyAddressActivity;
import bawei.com.electronicmall.bean.AddressBean;
import bawei.com.electronicmall.event.ModifytheEvent;
import bawei.com.electronicmall.event.RefreshEvent;

/**
 * @作者 熊金梦
 * @时间 2019/3/4 0004 16:58
 * @
 */
public class MyAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<AddressBean.ResultBean> list;
    private final Context context;

    public MyAddressAdapter(Context context, List<AddressBean.ResultBean> list) {
        super();
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_address, viewGroup, false);
        return new Hoderta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof Hoderta){
            final Hoderta hoderta = (Hoderta) viewHolder;
            final String realName = list.get(i).getRealName();
            final String phone = list.get(i).getPhone();
            final String address = list.get(i).getAddress();
            final String zipCode = list.get(i).getZipCode();
            hoderta.text_name_add.setText(realName);
            hoderta.text_emil_add.setText(phone);
            hoderta.text_date_add.setText(address);
            final int id = list.get(i).getId();
            String s = list.get(i).getWhetherDefault() + "";
            if (s.equals("1")){
                hoderta.checkbox_add.setChecked(true);
            } else {
                hoderta.checkbox_add.setChecked(false);
            }
            hoderta.btn_add_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sd = "修改";
                    EventBus.getDefault().postSticky(new ModifytheEvent(sd,id,realName,phone,address,zipCode));
                    Intent intent = new Intent(context,MyAddAddressActivity.class);
                    context.startActivity(intent);
                }
            });
            hoderta.btn_add_delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "不能删除", Toast.LENGTH_SHORT).show();
                }
            });
            hoderta.checkbox_add.setOnClickListener(new View.OnClickListener() {
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
    private class Hoderta extends RecyclerView.ViewHolder {
        private final TextView text_name_add;
        private final TextView text_emil_add;
        private final TextView text_date_add;
        private final CheckBox checkbox_add;
        private final Button btn_add_update;
        private final Button btn_add_delect;


        public Hoderta(View view) {
            super(view);
            text_name_add = view.findViewById(R.id.text_name_add);
            text_emil_add = view.findViewById(R.id.text_emil_add);
            text_date_add = view.findViewById(R.id.text_date_add);
            checkbox_add = view.findViewById(R.id.checkbox_add);
            btn_add_update = view.findViewById(R.id.btn_add_update);
            btn_add_delect = view.findViewById(R.id.btn_add_delect);

        }
    }
}
