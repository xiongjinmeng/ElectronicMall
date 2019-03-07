package bawei.com.electronicmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import bawei.com.electronicmall.adapter.MyFragmentPagerAdapter;
import bawei.com.electronicmall.fragment.ClassifFragment;
import bawei.com.electronicmall.fragment.HomeFragment;
import bawei.com.electronicmall.fragment.ListnrFragment;
import bawei.com.electronicmall.fragment.MymallFragment;
import bawei.com.electronicmall.fragment.SearchGoodsFragment;
import bawei.com.electronicmall.fragment.ShoppinFragment;
import bawei.com.electronicmall.util.CustomViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_shop)
    CustomViewPager viewpagerShop;
    @BindView(R.id.btn_home)
    RadioButton btnHome;
    @BindView(R.id.btn_classif)
    RadioButton btnClassif;
    @BindView(R.id.btn_shoppin)
    RadioButton btnShoppin;
    @BindView(R.id.btn_listnr)
    RadioButton btnListnr;
    @BindView(R.id.btn_mymall)
    RadioButton btnMymall;
    @BindView(R.id.radio_btn)
    RadioGroup radioBtn;
    private Unbinder bind;
    private ArrayList<Fragment> fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        viewpagerShop.setOffscreenPageLimit(4);
        viewpagerShop.setScanScroll(false);
        fragment = new ArrayList<>();
//        SearchGoodsFragment searchGoodsFragment = new SearchGoodsFragment();
        fragment.add(new HomeFragment());
        fragment.add(new ClassifFragment());
        fragment.add(new ShoppinFragment());
        fragment.add(new ListnrFragment());
        fragment.add(new MymallFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.setShop(fragment);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewpagerShop.setAdapter(adapter);
            }
        });

        getDate();
    }

    private void getDate() {
        btnHome.setChecked(true);
        viewpagerShop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        btnHome.setChecked(true);
                        break;
                    case 1:
                        btnClassif.setChecked(true);
                        break;
                    case 2:
                        btnShoppin.setChecked(true);
                        break;
                    case 3:
                        btnListnr.setChecked(true);
                        break;
                    case 4:
                        btnMymall.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        radioBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.btn_home:
                        viewpagerShop.setCurrentItem(0);
                        break;
                    case R.id.btn_classif:
                        viewpagerShop.setCurrentItem(1);
                        break;
                    case R.id.btn_shoppin:
                        viewpagerShop.setCurrentItem(2);
                        break;
                    case R.id.btn_listnr:
                        viewpagerShop.setCurrentItem(3);
                        break;
                    case R.id.btn_mymall:
                        viewpagerShop.setCurrentItem(4);
                        break;
                }
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void esentry(String s){

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }
}
