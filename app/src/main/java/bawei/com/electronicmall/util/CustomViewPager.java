package bawei.com.electronicmall.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @作者 熊金梦
 * @时间 2019/2/20 0020 11:31
 * @
 */
public class CustomViewPager extends ViewPager {

    private boolean isCanScroll;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);
    }
}
