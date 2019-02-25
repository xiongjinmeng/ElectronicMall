package bawei.com.electronicmall.mvp.molder;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import bawei.com.electronicmall.util.HttpRetrofitUile;
import bawei.com.electronicmall.util.WayApi;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 17:21
 * @
 */
public class Molder {
    public void getQuery(String queryGoodsUrl, Map<String, String> map, Class aClass, IMolder iMolder) {
        HttpRetrofitUile.getInstance()
                .create(WayApi.class)
                .get(queryGoodsUrl,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            Log.e("--------",string);
                            Gson gson = new Gson();
                            Object o = gson.fromJson(string, aClass);
                            iMolder.onSuccessful(o);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        iMolder.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postone(String login, Map<String, String> map, Class aClass, IMolder iMolder) {
        HttpRetrofitUile.getInstance().create(WayApi.class).post(login,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String string = null;
                        try {
                            string = responseBody.string();
//                            Log.e("--------",string);
                            Gson gson = new Gson();
                            Object o = gson.fromJson(string, aClass);
                            iMolder.onSuccessful(o);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        iMolder.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteone(String praiseCancel, Map<String, String> map, Class aClass, IMolder iMolder) {
        HttpRetrofitUile.getInstance().create(WayApi.class).delete(praiseCancel,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String string = null;
                        try {
                            string = responseBody.string();
//                            Log.e("--------",string);
                            Gson gson = new Gson();
                            Object o = gson.fromJson(string, aClass);
                            iMolder.onSuccessful(o);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        iMolder.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void put(String url, Map<String, String> map, Class aClass, IMolder iMolder) {
        HttpRetrofitUile.getInstance().create(WayApi.class).put(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String string = null;
                        try {
                            string = responseBody.string();
                            Log.e("--------",string);
                            Gson gson = new Gson();
                            Object o = gson.fromJson(string, aClass);
                            iMolder.onSuccessful(o);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        iMolder.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
