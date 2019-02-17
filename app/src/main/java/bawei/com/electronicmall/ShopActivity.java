package bawei.com.electronicmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import bawei.com.electronicmall.adapter.MyFragmentPagerAdapter;
import bawei.com.electronicmall.fragment.SearchGoodsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_shop)
    ViewPager viewpagerShop;
    private Unbinder bind;
    private ArrayList<Fragment> fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        bind = ButterKnife.bind(this);
        fragment = new ArrayList<>();
        SearchGoodsFragment searchGoodsFragment = new SearchGoodsFragment();
        fragment.add(searchGoodsFragment);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragment);
        viewpagerShop.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
