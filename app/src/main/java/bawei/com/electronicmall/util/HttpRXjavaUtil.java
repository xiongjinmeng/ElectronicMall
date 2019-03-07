package bawei.com.electronicmall.util;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @作者 熊金梦
 * @时间 2019/3/6 0006 21:12
 * @
 */
public class HttpRXjavaUtil {



    private static HttpRXjavaUtil retrofitUtil;

    public static HttpRXjavaUtil get(){
        if (retrofitUtil==null){
            synchronized (HttpRXjavaUtil.class){
                if (retrofitUtil==null){
                    retrofitUtil = new HttpRXjavaUtil();
                }
            }
        }
        return retrofitUtil;
    }
    public HttpRXjavaUtil getrt(String url, Map<String,String> map) {
        HttpRetrofitUile.getInstance().create(WayApi.class).get(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return HttpRXjavaUtil.get();
    }
    private Observer observer = new Observer<ResponseBody>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                httpListener.onSuccess(responseBody.string());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (httpListener!=null){
                httpListener.onError(e.getMessage());
            }
        }

        @Override
        public void onComplete() {

        }
    };
    public interface HttpListener {
        void onSuccess(String data);

        void onError(String error);
    }
    private HttpListener httpListener;

    public void setHttpListener(HttpListener listener) {
        this.httpListener = listener;
    }
}
