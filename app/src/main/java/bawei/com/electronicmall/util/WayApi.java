package bawei.com.electronicmall.util;

import java.util.Map;

import bawei.com.electronicmall.bean.CreateOrderBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
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
    //上传头像
    @POST
    @Multipart
    Observable<ResponseBody> postPart(@Url String url,@Part MultipartBody.Part f);
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> postparter(@Url String url,@FieldMap Map<String,String> map);

    @DELETE
    Observable<ResponseBody> delete(@Url String url,@QueryMap Map<String,String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url,@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> posttwo(@Url String url,
                                      @Field("orderInfo") String orderInfo,
                                      @Field("totalPrice") double totalPrice,
                                      @Field("addressId") int addressId);

}
