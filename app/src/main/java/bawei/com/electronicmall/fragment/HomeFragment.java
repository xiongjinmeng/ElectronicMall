package bawei.com.electronicmall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyFragmentPagerAdapter;
import bawei.com.electronicmall.fragment.eventtwo.GoodsBeanEvent;
import bawei.com.electronicmall.fragment.fragmenttwo.FragmentHomeGoods;
import bawei.com.electronicmall.fragment.fragmenttwo.FragmentHomeSearch;
import bawei.com.electronicmall.util.CustomViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private View view;
    private CustomViewPager view_pager_home;
    private ArrayList<Fragment> fragment;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        EventBus.getDefault().register(this);
        view_pager_home = view.findViewById(R.id.view_pager_home);
        fragment = new ArrayList<>();
        fragment.add(new FragmentHomeGoods());
        fragment.add(new SearchGoodsFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getFragmentManager());
        adapter.setList(fragment);
        view_pager_home.setAdapter(adapter);
        view_pager_home.setCurrentItem(0);
        view_pager_home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        view_pager_home.setScanScroll(false);
                        break;
                    case 1:
                        view_pager_home.setScanScroll(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGoodsEvent(GoodsBeanEvent event){
            view_pager_home.setCurrentItem(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
