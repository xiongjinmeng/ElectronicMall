package bawei.com.electronicmall.util;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import org.robolectric.shadows.multidex.ShadowMultiDex;

import bawei.com.electronicmall.greendao.DaoMaster;
import bawei.com.electronicmall.greendao.DaoSession;

/**
 * @作者 熊金梦
 * @时间 2019/2/15 0015 19:24
 * @
 */
public class App extends Application {
    private static DaoSession daoSession;

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
//        Fresco.initialize(this);
        ShadowMultiDex.install(this);
        setDatabase();

    }
    private void setDatabase() {
        //第一步创建OpenHelper类
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "dalist.db");
        //开启一个可写数据库类
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        //通过DaoMaster封装
        DaoMaster master = new DaoMaster(writableDatabase);
        daoSession = master.newSession();
    }
    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
