package bawei.com.electronicmall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bawei.com.electronicmall.adapter.MyPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者 熊金梦
 * @时间 2019/2/15 0015 19:25
 * @
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.button)
    Button button;
    private Unbinder bind;
    private ArrayList<Integer> integers;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        button.setVisibility(View.GONE);
        SharedPreferences preferences = getSharedPreferences("boxt", MODE_PRIVATE);
        edit = preferences.edit();

        boolean box = preferences.getBoolean("box", false);
        if (box){
            tiaozhuan();
        } else {
            integers = new ArrayList<>();
            integers.add(R.drawable.zzz1);
            integers.add(R.drawable.zzz2);
            integers.add(R.drawable.zzz3);
            integers.add(R.drawable.zzz4);
            MyPagerAdapter adapter = new MyPagerAdapter(integers);
            viewpager.setAdapter(adapter);
        }

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==3){
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putBoolean("box",true);
                edit.commit();
                tiaozhuan();
            }
        });


    }

    private void tiaozhuan() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
