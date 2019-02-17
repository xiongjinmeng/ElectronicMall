package bawei.com.electronicmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.DetailsActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyQueryGoodsAdapter;
import bawei.com.electronicmall.bean.SearchGoodsBean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.HttpRetrofitUile;
import bawei.com.electronicmall.util.WayApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchGoodsFragment extends Fragment implements IView {


    @BindView(R.id.image_search_geoods)
    ImageView imageSearchGeoods;
    @BindView(R.id.edit_search_geoods)
    EditText editSearchGeoods;
    @BindView(R.id.text_search_geoods)
    TextView textSearchGeoods;
    @BindView(R.id.xrecycle_search_geoods)
    XRecyclerView xrecycleSearchGeoods;
    Unbinder unbinder;
    private View view;
    private Presenter presenter;

    public SearchGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_goods, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        presenter = new Presenter(this);
        getDate("板鞋","1","10");
        getRefresh();
        return view;
    }

    private void getDate(String keyword, String page, String count) {
        Map<String, String> map=new HashMap<>();
        map.put("keyword",keyword);
        map.put("page",page);
        map.put("count",count);
        presenter.getQuery(Api.QUERY_GOODS_URL,map,SearchGoodsBean.class);
    }
    @Subscribe
    public void SearchEvent(String s){

    }

    private void getRefresh() {
        xrecycleSearchGeoods.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
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

    @OnClick({R.id.image_search_geoods, R.id.text_search_geoods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_search_geoods:
                break;
            case R.id.text_search_geoods:
                String trim = editSearchGeoods.getText().toString().trim();
                if (trim.isEmpty()){
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    getDate(trim,"1","10");
                }
                break;
        }
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof SearchGoodsBean){
            SearchGoodsBean bean = (SearchGoodsBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals("0000")){
                List<SearchGoodsBean.ResultBean> list = bean.getResult();
                MyQueryGoodsAdapter adapter = new MyQueryGoodsAdapter(getActivity(),list);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
                linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                linearLayoutManager.setReverseLayout(false);
                xrecycleSearchGeoods.setLayoutManager(linearLayoutManager);
                xrecycleSearchGeoods.setAdapter(adapter);
                adapter.setClic(new MyQueryGoodsAdapter.onClicklister() {
                    @Override
                    public void Clicklister(int num) {
                        EventBus.getDefault().postSticky(new DetailsEvent(num+""));
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onError(String e) {
        Log.e("-----",e);
    }
}
