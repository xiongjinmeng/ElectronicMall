package bawei.com.electronicmall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import bawei.com.electronicmall.bean.LoginRegisteredBean;
import bawei.com.electronicmall.event.MyUserEvent;
import bawei.com.electronicmall.event.RegisterEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements IView {

    @BindView(R.id.edit_login_phone)
    EditText editLoginPhone;
    @BindView(R.id.edit_login_password)
    EditText editLoginPassword;
    @BindView(R.id.image_login_password_shop)
    ImageView imageLoginPasswordShop;
    @BindView(R.id.check_box_remember_password)
    CheckBox checkBoxRememberPassword;
    @BindView(R.id.text_fast_registered)
    TextView textFastRegistered;
    @BindView(R.id.bun_login)
    Button bunLogin;
    private Unbinder bind;
    private Presenter presenter;
    private boolean basd = false;
    private SharedPreferences.Editor edit;
    private boolean box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ButterKnife.bind(this);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
        SharedPreferences person = getSharedPreferences("person", MODE_PRIVATE);
        edit = person.edit();
        box = person.getBoolean("box",false);
        if (box){
            String name = person.getString("name", null);
            String prass = person.getString("prass", null);
            checkBoxRememberPassword.setChecked(true);
            editLoginPhone.setText(name);
            editLoginPassword.setText(prass);
        }
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
    public void wae(RegisterEvent event){
        String phone = event.phone;
        String pwd = event.pwd;
        String stro = event.stro;
        editLoginPhone.setText(phone);
        editLoginPassword.setText(pwd);
        if (stro.equals("0")){
            checkBoxRememberPassword.setChecked(true);
            Map<String,String> map = new HashMap<>();
            map.put("phone",phone);
            map.put("pwd",pwd);
            login(map);
        } else {
            checkBoxRememberPassword.setChecked(false);
        }
    }

    private void login(Map<String, String> map) {
        if (checkBoxRememberPassword.isChecked()) {
            String name = editLoginPhone.getText().toString().trim();
            String prass = editLoginPassword.getText().toString().trim();
            edit.putString("name", name);
            edit.putString("prass", prass);
            edit.putBoolean("box", true);
            edit.commit();
        } else {
            edit.putBoolean("box", false);
            edit.commit();
        }
        presenter.postone(Api.LOGIN,map,LoginRegisteredBean.class);
    }

    @OnClick({R.id.image_login_password_shop, R.id.check_box_remember_password, R.id.text_fast_registered, R.id.bun_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_login_password_shop:
                basd=!basd;
                if (basd==true){
                    editLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                editLoginPassword.setSelection(editLoginPassword.getText().length());
                break;
            case R.id.check_box_remember_password:
                break;
            case R.id.text_fast_registered:
                Intent intent2 = new Intent(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent2);
                break;
            case R.id.bun_login:
                String name = editLoginPhone.getText().toString().trim();
                String prass = editLoginPassword.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (prass.isEmpty()) {
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("phone", name);
                        map.put("pwd", prass);
                        login(map);
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof LoginRegisteredBean){
            LoginRegisteredBean bean = (LoginRegisteredBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)){
                String sessionId = bean.getResult().getSessionId();
                String userId = bean.getResult().getUserId()+"";
                edit.putString("sessionId",sessionId);
                edit.putString("userId",userId);
                edit.commit();
                String nickName = bean.getResult().getNickName();
                String headPic = bean.getResult().getHeadPic();
                EventBus.getDefault().postSticky(new MyUserEvent(headPic,nickName));
                Intent intent = new Intent(LoginActivity.this, ShopActivity.class);
                startActivity(intent);
            } else {
                editLoginPassword.setText("");
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(String e) {
        Log.e("------",e);
    }
}
