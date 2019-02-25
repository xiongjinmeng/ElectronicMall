package bawei.com.electronicmall.mvp.presenter;

import java.util.Map;

import bawei.com.electronicmall.bean.ClassifBean;
import bawei.com.electronicmall.bean.DetailsGoodsBean;
import bawei.com.electronicmall.bean.LoginRegisteredBean;
import bawei.com.electronicmall.bean.SearchGoodsBean;
import bawei.com.electronicmall.mvp.molder.IMolder;
import bawei.com.electronicmall.mvp.molder.Molder;
import bawei.com.electronicmall.mvp.view.IView;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 17:19
 * @
 */
public class Presenter {

    private Molder molder;
    private IView iView;

    public Presenter(IView iView){
        this.iView = iView;
        molder = new Molder();
    }
    public void onDestroy() {
        if (iView!=null){
            iView=null;
        }
        if (molder!=null){
            molder=null;
        }
    }

    public void getQuery(String queryGoodsUrl, Map<String, String> map, Class aClass) {
        molder.getQuery(queryGoodsUrl,map,aClass, new IMolder() {
            @Override
            public void onSuccessful(Object date) {
                iView.onSuccessful(date);
            }

            @Override
            public void onError(String e) {
                iView.onError(e);
            }
        });
    }

    public void postone(String login, Map<String, String> map, Class aClass) {
        molder.postone(login,map,aClass, new IMolder() {
            @Override
            public void onSuccessful(Object date) {
                iView.onSuccessful(date);
            }

            @Override
            public void onError(String e) {
                iView.onError(e);
            }
        });
    }

    public void deleteone(String praiseCancel, Map<String, String> map, Class aClass) {
        molder.deleteone(praiseCancel,map,aClass, new IMolder() {
            @Override
            public void onSuccessful(Object date) {
                iView.onSuccessful(date);
            }

            @Override
            public void onError(String e) {
                iView.onError(e);
            }
        });
    }

    public void put(String url, Map<String, String> map, Class aClass) {
        molder.put(url,map,aClass, new IMolder() {
            @Override
            public void onSuccessful(Object date) {
                iView.onSuccessful(date);
            }

            @Override
            public void onError(String e) {
                iView.onError(e);
            }
        });
    }
}
