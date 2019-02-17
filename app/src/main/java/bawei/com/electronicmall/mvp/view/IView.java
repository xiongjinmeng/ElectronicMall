package bawei.com.electronicmall.mvp.view;

/**
 * @作者 熊金梦
 * @时间 2019/2/16 0016 17:19
 * @
 */
public interface IView<T> {
    void onSuccessful(T date);
    void onError(String e);
}
