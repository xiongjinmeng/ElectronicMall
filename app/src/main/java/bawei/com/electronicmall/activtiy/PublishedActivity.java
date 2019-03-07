package bawei.com.electronicmall.activtiy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.UserDateBean;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PublishedActivity extends AppCompatActivity implements IView {

    @BindView(R.id.simple_publish)
    SimpleDraweeView simplePublish;
    @BindView(R.id.text_publish_comm)
    TextView textPublishComm;
    @BindView(R.id.image_publish)
    ImageView imagePublish;
    @BindView(R.id.check_publish_box)
    CheckBox checkPublishBox;
    @BindView(R.id.btn_publish)
    Button btnPublish;
    @BindView(R.id.text_publish_name)
    TextView textPublishName;
    @BindView(R.id.text_publish_price)
    TextView textPublishPrice;
    private Unbinder bind;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published);
        bind = ButterKnife.bind(this);
        presenter = new Presenter(this);
        Intent intent = getIntent();
        String commodityPic = intent.getStringExtra("commodityPic");
        String commodityName = intent.getStringExtra("commodityName");
        String commodityPrice = intent.getStringExtra("commodityPrice");
        String commodityId = intent.getStringExtra("commodityId");
        String orderId = intent.getStringExtra("orderId");
        textPublishName.setText(commodityName);
        textPublishPrice.setText("￥"+commodityPrice);
        simplePublish.setImageURI(Uri.parse(commodityPic));
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textPublishComm.getText().toString().trim();
                if (trim.isEmpty()){
                    Toast.makeText(PublishedActivity.this, "请输入你评论的内容", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("commodityId",commodityId);
                    map.put("content",trim);
                    map.put("orderId",orderId);
                    presenter.postpart(Api.ADD_COMMDITY,map,UserDateBean.class);
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
        bind.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof UserDateBean){
            UserDateBean bean = (UserDateBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (status.equals(ConditionsUtil.CONDITIO)){
                finish();
            }
        }


    }

    @Override
    public void onError(String e) {
        Log.e("------",e);
    }
}
