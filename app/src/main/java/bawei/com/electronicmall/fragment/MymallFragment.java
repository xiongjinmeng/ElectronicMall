package bawei.com.electronicmall.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.activtiy.MyAddressActivity;
import bawei.com.electronicmall.activtiy.MyClassifActivity;
import bawei.com.electronicmall.activtiy.MyFootprintActivity;
import bawei.com.electronicmall.activtiy.MyPersonalActivity;
import bawei.com.electronicmall.activtiy.MyWalletActivity;
import bawei.com.electronicmall.event.MyUserEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MymallFragment extends Fragment {


    @BindView(R.id.text_myname)
    TextView textMyname;
    @BindView(R.id.text_mydata)
    TextView textMydata;
    @BindView(R.id.text_myclassif)
    TextView textMyclassif;
    @BindView(R.id.text_myfootprint)
    TextView textMyfootprint;
    @BindView(R.id.text_mywallet)
    TextView textMywallet;
    @BindView(R.id.text_myaddress)
    TextView textMyaddress;
    @BindView(R.id.image_head_portrait)
    SimpleDraweeView imageHeadPortrait;
    Unbinder unbinder;
    private View view;

    public MymallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mymall, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void myuser(MyUserEvent event){
        String headPic = event.headPic;
        String nickName = event.nickName;
//        Log.e("----",headPic);
        imageHeadPortrait.setImageURI(Uri.parse(headPic));
        textMyname.setText(nickName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.text_mydata, R.id.text_myclassif, R.id.text_myfootprint, R.id.text_mywallet, R.id.text_myaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_myname:
                break;
            case R.id.text_mydata:
                Intent intentdata = new Intent(getActivity(),MyPersonalActivity.class);
                startActivity(intentdata);
                break;
            case R.id.text_myclassif:
                Intent intentclassif = new Intent(getActivity(),MyClassifActivity.class);
                startActivity(intentclassif);
                break;
            case R.id.text_myfootprint:
                Intent intentfootprint = new Intent(getActivity(),MyFootprintActivity.class);
                startActivity(intentfootprint);
                break;
            case R.id.text_mywallet:
                Intent intentwallet = new Intent(getActivity(),MyWalletActivity.class);
                startActivity(intentwallet);
                break;
            case R.id.text_myaddress:
                Intent intent = new Intent(getActivity(),MyAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.image_head_portrait:
                break;
        }
    }
}
