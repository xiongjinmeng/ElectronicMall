package bawei.com.electronicmall.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.adapter.MyCircleAdapter;
import bawei.com.electronicmall.bean.ClassifBean;
import bawei.com.electronicmall.event.CirclePraiseEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifFragment extends Fragment implements IView {


    @BindView(R.id.xrecycle_classif_frag)
    RecyclerView xrecycleClassifFrag;
    Unbinder unbinder;
    private View view;
    private Presenter presenter;

    public ClassifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_classif, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
        Map<String,String> map = new HashMap<>();
        map.put("page","1");
        map.put("count","20");
        getDate(map);
        return view;
    }

    private void getDate(Map<String, String> map) {
        presenter.getQuery(Api.CLASSIF,map,ClassifBean.class);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCircleEvent(CirclePraiseEvent event){
        String sid = event.sid;
        String st = event.st;
        Map<String,String> map = new HashMap<>();
        map.put("circleId",sid);
        if (st.equals("0")){
            postData(Api.PRAISE,map);
        } else {
            deleteData(Api.PRAISE_CANCEL,map);
        }
    }

    private void deleteData(String praiseCancel, Map<String, String> map) {
        presenter.deleteone(praiseCancel,map,ClassifBean.class);
    }

    private void postData(String praise, Map<String, String> map) {
        presenter.postone(praise,map,ClassifBean.class);
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
    @Override
    public void onSuccessful(Object date) {
        if (date instanceof ClassifBean){
            ClassifBean bean = (ClassifBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals(ConditionsUtil.CONDITIO)){
                List<ClassifBean.ResultBean> list = bean.getResult();
                if (list == null) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        String image = list.get(i).getImage();
                        if (image.equals("")) {
                            list.remove(i);
                        } else if (image.equals("mp4")){
                            list.remove(i);
                        } else {
                            MyCircleAdapter adapter = new MyCircleAdapter(getActivity(), list);
                            xrecycleClassifFrag.setAdapter(adapter);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            xrecycleClassifFrag.setLayoutManager(linearLayoutManager);
                        }
                    }
                }
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onError(String e) {
        Log.e("------",e);
    }
}
