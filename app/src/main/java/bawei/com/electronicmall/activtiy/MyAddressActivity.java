package bawei.com.electronicmall.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyAddressAdapter;
import bawei.com.electronicmall.bean.AddressBean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.event.ModifytheEvent;
import bawei.com.electronicmall.event.RefreshEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.text_address_into)
    TextView textAddressInto;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.recycler_myaddress)
    RecyclerView recyclerMyaddress;
    @BindView(R.id.btn_address)
    Button btnAddress;
    private Presenter presenter;
    private Unbinder unbinder;
    private MyAddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        unbinder = ButterKnife.bind(this);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
        textAddress.setVisibility(View.GONE);
        request();
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void detaiaddev(DetailsEvent event) {
      request();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshEvent(RefreshEvent event) {
        int id = event.id;
        Map<String,String> map = new HashMap<>();
        map.put("id",id+"");
        presenter.postone(Api.SET_DEFAULT,map,AddressBean.class);
    }

    private void request() {
        Map<String,String> map = new HashMap<>();
        presenter.getQuery(Api.RECEIVE_ADD,map,AddressBean.class);
    }

    @OnClick({R.id.text_address_into, R.id.btn_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_address_into:
                Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btn_address:
                String sd = "新增";
                EventBus.getDefault().postSticky(new ModifytheEvent(sd));
                Intent intent = new Intent(MyAddressActivity.this,MyAddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof AddressBean){
            AddressBean bean = (AddressBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            List<AddressBean.ResultBean> list = bean.getResult();
            if (status.equals("0000")){
                if (list==null){
                    textAddress.setVisibility(View.GONE);
//                    textAddress.setVisibility(View.VISIBLE);
                    textAddress.setText(message);
                    request();
                } else {
                    textAddress.setVisibility(View.GONE);
                    adapter = new MyAddressAdapter(MyAddressActivity.this, list);
                    recyclerMyaddress.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyAddressActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerMyaddress.setLayoutManager(linearLayoutManager);
                }

            } else {
                Toast.makeText(MyAddressActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(String message) {
        Log.e("-------", message);
    }
}
