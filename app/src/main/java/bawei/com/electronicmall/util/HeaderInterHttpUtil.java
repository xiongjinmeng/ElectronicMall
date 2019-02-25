package bawei.com.electronicmall.util;


import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @作者 熊金梦
 * @时间 2019/2/19 0019 16:35
 * @
 */
public class HeaderInterHttpUtil implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();//原始请求对象
        Request nrequest = originalRequest.newBuilder()//创建新的请求对象
                .addHeader("userId",SPUtils.getInstance("person").getString("userId"))
                .addHeader("sessionId",SPUtils.getInstance("person").getString("sessionId"))
                .build();
        Response response = chain.proceed(nrequest);//最终的响应对象
        return response;
    }
}
