package bawei.com.electronicmall.activtiy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.AddressBean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.event.ModifytheEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyAddAddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.text_biaoti)
    TextView textBiaoti;
    @BindView(R.id.receipt)
    TextView receipt;
    @BindView(R.id.edit_receipt)
    EditText editReceipt;
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.inthearea)
    TextView inthearea;
    @BindView(R.id.edit_inthearea)
    EditText editInthearea;
    @BindView(R.id.image_addaddress)
    ImageView imageAddaddress;
    @BindView(R.id.detailedaddress)
    TextView detailedaddress;
    @BindView(R.id.edit_detailedaddress)
    EditText editDetailedaddress;
    @BindView(R.id.coding)
    TextView coding;
    @BindView(R.id.edit_coding)
    EditText editCoding;
    @BindView(R.id.btn_addaddadd)
    Button btnAddaddadd;
    private Presenter presenter;
    private Unbinder unbinder;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_address);
        unbinder = ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        presenter = new Presenter(this);
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(MyAddAddressActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                editInthearea.setText(province.trim() + " " + city.trim() + " " + district.trim());
                editCoding.setText(code);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void modifEvent(ModifytheEvent event) {
        String sd = event.sd;
        if (sd.equals("修改")) {
            id = event.id;
            String realName = event.realName;
            String phone = event.phone;
            String address = event.address;
            String zipCode = event.zipCode;
            editReceipt.setText(realName);
            editNumber.setText(phone);
            editDetailedaddress.setText(address);
            editCoding.setText(zipCode);
        }
        textBiaoti.setText(sd + "收货地址");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof AddressBean) {
            AddressBean bean = (AddressBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals("0000")) {
                EventBus.getDefault().post(new DetailsEvent());
                finish();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String message) {
        Log.e("---------", message);

    }

    @OnClick({R.id.image_addaddress, R.id.btn_addaddadd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_addaddress:
                //判断输入法的隐藏状态
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    selectAddress();//调用CityPicker选取区域
                }
                break;
            case R.id.btn_addaddadd:
                String trim = textBiaoti.getText().toString().trim();
                String receipt = editReceipt.getText().toString().trim();
                String number = editNumber.getText().toString().trim();
                String inthearea = editInthearea.getText().toString();
                String detailedaddress = editDetailedaddress.getText().toString();
                String s = inthearea + " " + detailedaddress;
                String coding = editCoding.getText().toString().trim();
                if (receipt.isEmpty() || number.isEmpty() || inthearea.isEmpty() || s.isEmpty() || coding.isEmpty()) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (trim.equals("修改收货地址")) {
                        Map<String,String> map = new HashMap<>();
                        map.put("id",id+"");
                        map.put("realName",receipt);
                        map.put("phone",number);
                        map.put("address",s);
                        map.put("zipCode",coding);
                        presenter.put(Api.CHANGE_RECEIVE,map,AddressBean.class);
                        break;
                    } else {
                        Map<String,String> map = new HashMap<>();
                        map.put("realName",receipt);
                        map.put("phone",number);
                        map.put("address",s);
                        map.put("zipCode",coding);
                        presenter.postone(Api.ADD_RECEIVE,map,AddressBean.class);
                        break;
                    }

                }

        }
    }
}
