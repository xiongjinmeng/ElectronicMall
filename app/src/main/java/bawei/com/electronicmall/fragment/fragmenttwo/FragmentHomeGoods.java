package bawei.com.electronicmall.fragment.fragmenttwo;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.GoodsListBean;
import bawei.com.electronicmall.fragment.adaptertwo.MyHomeAdapter;
import bawei.com.electronicmall.fragment.eventtwo.GoodsBeanEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeGoods extends Fragment implements IView {


    @BindView(R.id.image_choose)
    ImageView imageChoose;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.recycler_view_home)
    XRecyclerView recyclerViewHome;
    Unbinder unbinder;
    private View view;
    private Presenter presenter;
    private Handler handler = new Handler();

    public FragmentHomeGoods() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_home_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        presenter = new Presenter(this);
        getDate();
        return view;
    }

    private void getDate() {
        Map<String,String> map = new HashMap<>();
        presenter.getQuery(Api.GOODS_LIST_URL,map,GoodsListBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onvent(String event){

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.image_choose, R.id.image_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_choose:

                break;
            case R.id.image_search:
                EventBus.getDefault().post(new GoodsBeanEvent("888","卫衣"));
                break;
        }
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof GoodsListBean){
            GoodsListBean bean = (GoodsListBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)){
                GoodsListBean.ResultBean resultBean = bean.getResult();
                recyclerViewHome.setPullRefreshEnabled(true);
                recyclerViewHome.setLoadingMoreEnabled(true);
                recyclerViewHome.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
                recyclerViewHome.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getDate();
                                //刷新完成
                                recyclerViewHome.refreshComplete();
                            }
                        },2000);
                    }

                    @Override
                    public void onLoadMore() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                    Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                                //刷新完成
                                recyclerViewHome.refreshComplete();
                            }
                        },3000);
                    }
                });
                MyHomeAdapter adapter = new MyHomeAdapter(getActivity(),resultBean);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewHome.setLayoutManager(linearLayoutManager);
                recyclerViewHome.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(String e) {
        Log.e("-----", e);
    }
}
