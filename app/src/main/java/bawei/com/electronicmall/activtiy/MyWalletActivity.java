package bawei.com.electronicmall.activtiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyWalletAdapter;
import bawei.com.electronicmall.bean.WalletBean;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyWalletActivity extends AppCompatActivity implements IView {

    @BindView(R.id.text_balance)
    TextView textBalance;
    @BindView(R.id.textzz)
    TextView textzz;
    @BindView(R.id.wallet_recycler)
    RecyclerView walletRecycler;
    @BindView(R.id.text_detailList)
    TextView textDetailList;
    private Unbinder bind;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = new Presenter(this);
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","50");
        presenter.getQuery(Api.FIND_USER_WALLET,map,WalletBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCileEvent(String event){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof WalletBean) {
            WalletBean bean = (WalletBean) date;
            String status = bean.getStatus();
            String message = bean.getMessage();
            List<WalletBean.ResultBean.DetailListBean> detailList = bean.getResult().getDetailList();
            if (status.equals("0000")) {
                double balance = bean.getResult().getBalance();
                textBalance.setText("￥"+balance);
                if (detailList.size()>0){
                    textDetailList.setVisibility(View.GONE);
                    walletRecycler.setVisibility(View.VISIBLE);
                    MyWalletAdapter adapter = new MyWalletAdapter(MyWalletActivity.this, detailList);
                    walletRecycler.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyWalletActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    walletRecycler.setLayoutManager(linearLayoutManager);
                } else {
                    textDetailList.setVisibility(View.VISIBLE);
                    walletRecycler.setVisibility(View.GONE);
                }
            } else {
                textDetailList.setVisibility(View.VISIBLE);
                walletRecycler.setVisibility(View.GONE);
                Toast.makeText(MyWalletActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(String e) {
        Log.e("-------", e);
    }
}
