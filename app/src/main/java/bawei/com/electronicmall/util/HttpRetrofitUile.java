package bawei.com.electronicmall.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 10:53
 * @
 */
public class HttpRetrofitUile {
    private static HttpRetrofitUile uile;
    private final Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(20,TimeUnit.MINUTES)
                .writeTimeout(20,TimeUnit.MINUTES)
                .readTimeout(20,TimeUnit.MINUTES)
                .addInterceptor(new HeaderInterHttpUtil())
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();
    }
    public static HttpRetrofitUile getInstance(){
        if (uile==null){
            synchronized (HttpRetrofitUile.class){
                if (uile==null){
                    uile = new HttpRetrofitUile();
                }
            }
        }
        return uile;
    }
    private HttpRetrofitUile(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .build();
    }
    public <T> T create(Class<T> clazz){
        return retrofit.create(clazz);
    }
}
