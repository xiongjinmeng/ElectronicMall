package bawei.com.electronicmall.util;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 11:10
 * @
 */
public interface WayApi {

    @GET
    Observable<ResponseBody> get(@Url String url,@QueryMap Map<String,String> map);
    @FormUrlEncoded
    @POST
    Call<String> post(@HeaderMap Map<String,String> header, @FieldMap Map<String,String> map);

}
