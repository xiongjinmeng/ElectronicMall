package bawei.com.electronicmall.activtiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyFootpAdapter;
import bawei.com.electronicmall.bean.FootprintBean;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFootprintActivity extends AppCompatActivity implements IView {

    @BindView(R.id.footpr_recycler)
    RecyclerView footprRecycler;
    @BindView(R.id.image_bbbbba)
    ImageView imageBbbbba;
    @BindView(R.id.text_aaaaaa)
    TextView textAaaaaa;
    private Unbinder unbinder;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_footprint);
        unbinder = ButterKnife.bind(this);
        imageBbbbba.setVisibility(View.GONE);
        textAaaaaa.setVisibility(View.GONE);
        request();
    }

    private void request() {
        presenter = new Presenter(MyFootprintActivity.this);
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","100");
        presenter.getQuery(Api.MY_BROWSE, map, FootprintBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof FootprintBean) {
            FootprintBean bean = (FootprintBean) date;
            String status = bean.getStatus();
            String message = bean.getMessage();
            List<FootprintBean.ResultBean> result = bean.getResult();
            if (status.equals("0000")) {
                if (result.size()>0){
                    imageBbbbba.setVisibility(View.GONE);
                    textAaaaaa.setVisibility(View.GONE);
                    footprRecycler.setVisibility(View.VISIBLE);
                    MyFootpAdapter adapter = new MyFootpAdapter(MyFootprintActivity.this, result);
                    footprRecycler.setAdapter(adapter);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    footprRecycler.setLayoutManager(staggeredGridLayoutManager);
                } else {
                    imageBbbbba.setVisibility(View.VISIBLE);
                    textAaaaaa.setVisibility(View.VISIBLE);
                    footprRecycler.setVisibility(View.GONE);
                }
            } else {
                imageBbbbba.setVisibility(View.VISIBLE);
                textAaaaaa.setVisibility(View.VISIBLE);
                footprRecycler.setVisibility(View.GONE);
                Toast.makeText(MyFootprintActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(String message) {
        Log.e("-------", message);

    }
}
