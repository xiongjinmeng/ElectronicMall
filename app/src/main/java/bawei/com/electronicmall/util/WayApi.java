package bawei.com.electronicmall.util;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Observable<ResponseBody> post(@Url String url,@FieldMap Map<String,String> map);

    @DELETE
    Observable<ResponseBody> delete(@Url String url,@QueryMap Map<String,String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url,@QueryMap Map<String,String> map);

}
