package bawei.com.electronicmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.DetailsActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyQueryGoodsAdapter;
import bawei.com.electronicmall.bean.SearchGoodsBean;
import bawei.com.electronicmall.daobean.SearchGoodsFragmentdaobean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.fragment.eventtwo.GoodsBeanEvent;
import bawei.com.electronicmall.greendao.SearchGoodsFragmentdaobeanDao;
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
    @BindView(R.id.linear_search_geoods_failure)
    LinearLayout linearSearchGeoodsFailure;
    private View view;
    private Presenter presenter;
    private SearchGoodsFragmentdaobean searchGoodsFragmentdaobean;
    private SearchGoodsFragmentdaobeanDao dao;
    private int page=1;
    private Handler handler = new Handler();
    private MyQueryGoodsAdapter adapter;

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
        linearSearchGeoodsFailure.setVisibility(View.GONE);
        return view;
    }

    private void getDate(String url,String keyword, String page, String count) {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("page", page);
        map.put("count", count);
        presenter.getQuery(url, map, SearchGoodsBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGoodsEvent(GoodsBeanEvent event){
        String qb = event.qb;
        String tp = event.getTp();
        if (qb.equals("888")){
            getDate(Api.QUERY_GOODS_URL,tp, "1", "10");
            editSearchGeoods.setText(tp);
        } else {
            editSearchGeoods.setText(qb);
            Map<String, String> map = new HashMap<>();
            map.put("labelId", tp);
            map.put("page", "1");
            map.put("count", "10");
            presenter.getQuery(Api.QUERY_ID_URL, map, SearchGoodsBean.class);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
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
                if (trim.isEmpty()) {
                    Toast.makeText(getActivity(), "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    getDate(Api.QUERY_GOODS_URL,trim, "1", "10");
                }
                break;
        }
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof SearchGoodsBean) {
            SearchGoodsBean bean = (SearchGoodsBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)) {
//                Log.e("-----",bean.getResult().size()+"");
                if (bean.getResult().size() == 0) {
                    linearSearchGeoodsFailure.setVisibility(View.VISIBLE);
                    xrecycleSearchGeoods.setVisibility(View.GONE);
                    editSearchGeoods.setText("");
                } else {
                    linearSearchGeoodsFailure.setVisibility(View.GONE);
                    xrecycleSearchGeoods.setVisibility(View.VISIBLE);
                    List<SearchGoodsBean.ResultBean> list = bean.getResult();
                    getQuery(list);
                }
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void getQuery(List<SearchGoodsBean.ResultBean> list) {
//        List<SearchGoodsFragmentdaobean> list = dao.loadAll();
//        xrecycleSearchGeoods.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        xrecycleSearchGeoods.setPullRefreshEnabled(true);
        xrecycleSearchGeoods.setLoadingMoreEnabled(true);
        xrecycleSearchGeoods.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xrecycleSearchGeoods.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
//                xrecycleSearchGeoods.refreshComplete();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String trim = editSearchGeoods.getText().toString().trim();
                        page=1;
                        getDate(Api.QUERY_GOODS_URL,trim, "1", "10");
                        //刷新完成
                        xrecycleSearchGeoods.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
//                getDate(trim, page+"", "10");
//                xrecycleSearchGeoods.refreshComplete();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (page==5){
                            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                        } else {
                            // flag = false;
//                        type ++;
//                        OkUrl();
                            //刷新适配器
//                            list.add()
                            Toast.makeText(getActivity(), ""+page, Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        //刷新完成
                        xrecycleSearchGeoods.refreshComplete();
                    }
                },3000);
            }
        });
        adapter = new MyQueryGoodsAdapter(getActivity(), list);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        xrecycleSearchGeoods.setLayoutManager(linearLayoutManager);
        xrecycleSearchGeoods.setAdapter(adapter);
        //添加头布局
//        TextView title=new TextView(getActivity());
//        adapter.addHeader(title);
        //刷新适配器
        adapter.notifyDataSetChanged();
        adapter.setClic(new MyQueryGoodsAdapter.onClicklister() {
            @Override
            public void Clicklister(int num) {
                EventBus.getDefault().postSticky(new DetailsEvent(num + ""));
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(String e) {
        Log.e("-----", e);
    }
}
