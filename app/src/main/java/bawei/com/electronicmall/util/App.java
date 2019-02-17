package bawei.com.electronicmall.util;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * @作者 熊金梦
 * @时间 2019/2/15 0015 19:24
 * @
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig images = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(images)
                .build();
        Fresco.initialize(this,config);


    }
}
