package bawei.com.electronicmall.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import bawei.com.electronicmall.R;


/**
 * @作者 熊金梦
 * @时间 2019/2/15 0015 19:36
 * @
 */
public class MyPagerAdapter extends PagerAdapter {
    private final ArrayList<Integer> list;

    public MyPagerAdapter(ArrayList<Integer> integers) {
        super();
        this.list=integers;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        View viewq = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_viewpager, container, false);
        ImageView viewpager = viewq.findViewById(R.id.viewimage);
        Integer integer = list.get(position);
        viewpager.setImageResource(integer);
        container.addView(viewq);
        return viewq;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
