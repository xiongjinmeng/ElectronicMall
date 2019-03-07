package bawei.com.electronicmall.activtiy;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyMyClassifAdapter;
import bawei.com.electronicmall.bean.MyClassifBean;
import bawei.com.electronicmall.bean.UserDateBean;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyClassifActivity extends AppCompatActivity implements IView {

    @BindView(R.id.image_myclassif)
    CheckBox imageMyclassif;
    @BindView(R.id.recy_myclassif)
    RecyclerView recyMyclassif;
    @BindView(R.id.text_classif_text)
    TextView textClassifText;
    private Unbinder bind;
    private Presenter presenter;
    private MyMyClassifAdapter adapter;
    private List<MyClassifBean.ResultBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classif);
        bind = ButterKnife.bind(this);
        textClassifText.setVisibility(View.GONE);
        recyMyclassif.setVisibility(View.GONE);
        presenter = new Presenter(this);
        imageMyclassif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmp="";
                for (int i = 0; i < list.size(); i++) {
                    boolean sicktp = list.get(i).isSicktp();
                    if (sicktp){
                        String id = list.get(i).getId()+"";
                        tmp += id+",";
                    }
                }
                if (tmp.isEmpty()){

                    Toast.makeText(MyClassifActivity.this, "请选择", Toast.LENGTH_SHORT).show();

                } else {
                    String substring = tmp.substring(0, tmp.length() - 1);
                    Map<String, String> map = new HashMap<>();
                    map.put("circleId", substring);
                    presenter.deleteone(Api.DELETE_CIRCLE,map,UserDateBean.class);

                }


            }
        });
        getDate();
    }

    private void getDate() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("count", "50");
        presenter.getQuery(Api.FIND_MYCIRCLE, map, MyClassifBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
        bind.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof MyClassifBean) {
            MyClassifBean bean = (MyClassifBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)) {
                if (bean.getResult() == null) {
                    textClassifText.setVisibility(View.VISIBLE);
                    recyMyclassif.setVisibility(View.GONE);
                } else {
                    textClassifText.setVisibility(View.GONE);
                    recyMyclassif.setVisibility(View.VISIBLE);
                    list = bean.getResult();
                    adapter = new MyMyClassifAdapter(this, list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyMyclassif.setLayoutManager(linearLayoutManager);
                    recyMyclassif.setAdapter(adapter);
                }
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } else if (date instanceof UserDateBean){
            UserDateBean dateBean = (UserDateBean) date;
            String message = dateBean.getMessage();
            String status = dateBean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)){
                getDate();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(String e) {
        Log.e("-------", e);
    }
}
