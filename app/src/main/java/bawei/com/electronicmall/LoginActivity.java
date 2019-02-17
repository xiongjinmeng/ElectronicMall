package bawei.com.electronicmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ButterKnife.bind(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @OnClick({R.id.image_login_password_shop, R.id.check_box_remember_password, R.id.text_fast_registered, R.id.bun_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_login_password_shop:
                break;
            case R.id.check_box_remember_password:
                break;
            case R.id.text_fast_registered:
                break;
            case R.id.bun_login:
                Intent intent = new Intent(LoginActivity.this, ShopActivity.class);
                startActivity(intent);
                break;
        }
    }
}
