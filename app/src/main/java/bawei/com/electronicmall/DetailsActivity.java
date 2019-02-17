package bawei.com.electronicmall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bawei.com.electronicmall.bean.DetailsGoodsBean;
import bawei.com.electronicmall.bean.SearchGoodsBean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailsActivity extends AppCompatActivity implements IView {

    @BindView(R.id.xbgabanner)
    XBanner xbgabanner;
    @BindView(R.id.webview)
    WebView webview;
    private Unbinder bind;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(DetailsActivity.this);
        presenter = new Presenter(DetailsActivity.this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void DetaiEvent(DetailsEvent event) {
        String num = event.num;
        getDate(num);
    }

    private void getDate(String num) {
        Map<String, String> map=new HashMap<>();
        map.put("commodityId",num);
        presenter.getQuery(Api.DETAILS_GOODS_URL,map,DetailsGoodsBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof DetailsGoodsBean){
            DetailsGoodsBean bean = (DetailsGoodsBean) date;
            String message = bean.getMessage();
            String status = bean.getStatus();
            if (status.equals("0000")){
                DetailsGoodsBean.ResultBean beanResult = bean.getResult();
                if (beanResult==null){
                    Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String picture = beanResult.getPicture();
                    String[] split = picture.split(",");
                    String details = beanResult.getDetails();
                    final ArrayList<String> strings = new ArrayList<>();
                    for (int j = 0; j < split.length; j++) {
                        strings.add(split[j]);
                    }
                    xbgabanner.setData(strings,null);
                    xbgabanner.loadImage(new XBanner.XBannerAdapter() {
                        @Override
                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                            Glide.with(DetailsActivity.this).load(strings.get(position)).into((ImageView) view);
                        }
                    });
                    webview.loadDataWithBaseURL(null,details,"text/html","UTF-8",null);
                    //支持屏幕缩放
                    webview.getSettings().setSupportZoom(true);
                    webview.getSettings().setBuiltInZoomControls(true);
                    webview.getSettings().setDisplayZoomControls(false);
                    webview.getSettings().setUseWideViewPort(true);
                    webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    webview.getSettings().setLoadWithOverviewMode(true);
                }

            } else {
                Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onError(String e) {
        Log.e("-----",e);
    }
}
