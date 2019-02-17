package bawei.com.electronicmall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 9:45
 * @
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragment) {
        super(fm);
        this.list=fragment;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
