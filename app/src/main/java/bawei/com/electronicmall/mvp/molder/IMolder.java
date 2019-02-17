package bawei.com.electronicmall.mvp.molder;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 17:20
 * @
 */
public interface IMolder<T> {
    void onSuccessful(T date);
    void onError(String e);

}
