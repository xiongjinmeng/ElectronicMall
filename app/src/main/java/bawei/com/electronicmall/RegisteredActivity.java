package bawei.com.electronicmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import bawei.com.electronicmall.bean.LoginRegisteredBean;
import bawei.com.electronicmall.event.RegisterEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RegisteredActivity extends AppCompatActivity implements IView {

    @BindView(R.id.edit_registered_phone)
    EditText editRegisteredPhone;
    @BindView(R.id.edit_validation)
    EditText editValidation;
    @BindView(R.id.text_validation)
    TextView textValidation;
    @BindView(R.id.edit_registered_password)
    EditText editRegisteredPassword;
    @BindView(R.id.image_registered_password_shop)
    ImageView imageRegisteredPasswordShop;
    @BindView(R.id.text_an_account)
    TextView textAnAccount;
    @BindView(R.id.text_immediately_login)
    TextView textImmediatelyLogin;
    @BindView(R.id.bun_registered)
    Button bunRegistered;
    private Unbinder bind;
    private Presenter presenter;
    private boolean basd = false;
    private String st = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        bind = ButterKnife.bind(this);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(String s) {

    }
    @OnClick({R.id.text_validation, R.id.image_registered_password_shop, R.id.text_an_account, R.id.text_immediately_login, R.id.bun_registered})
    public void onViewClicked(View view) {
        String name = editRegisteredPhone.getText().toString().trim();
        String prass = editRegisteredPassword.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("phone", name);
        map.put("pwd", prass);
        switch (view.getId()) {
            case R.id.text_validation:
                editValidation.setText("3684");
                Random rand = new Random();
                String i1 = rand.nextInt(10)+"";
                String i2 = rand.nextInt(10)+"";
                String i3 = rand.nextInt(10)+"";
                String i4 = rand.nextInt(10) + "";
                editValidation.setText(i1+i2+i3+i4);
                break;
            case R.id.image_registered_password_shop:
                basd=!basd;
                if (basd==true){
                    editRegisteredPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editRegisteredPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                editRegisteredPassword.setSelection(editRegisteredPassword.getText().length());
                break;
            case R.id.text_an_account:
                finish();
                break;
            case R.id.text_immediately_login:
                if(name.isEmpty()){
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (prass.isEmpty()){
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        st="0";
                        register(map);
                    }
                }
                break;
            case R.id.bun_registered:
                if(name.isEmpty()){
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (prass.isEmpty()){
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        st="1";
                        register(map);
                    }
                }
                break;
        }
    }

    private void register(Map<String, String> map) {
        presenter.postone(Api.REGISTERED,map,LoginRegisteredBean.class);
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof LoginRegisteredBean){
            LoginRegisteredBean bean = (LoginRegisteredBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)){
                String name = editRegisteredPhone.getText().toString().trim();
                String prass = editRegisteredPassword.getText().toString().trim();
                EventBus.getDefault().post(new RegisterEvent(name,prass,st));
                finish();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(String e) {
        Log.e("------",e);
    }
}
