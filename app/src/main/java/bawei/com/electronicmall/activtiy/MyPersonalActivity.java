package bawei.com.electronicmall.activtiy;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import bawei.com.electronicmall.LoginActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.ModifyBean;
import bawei.com.electronicmall.bean.UserDateBean;
import bawei.com.electronicmall.event.MyUserEvent;
import bawei.com.electronicmall.mvp.presenter.Presenter;
import bawei.com.electronicmall.mvp.view.IView;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.ConditionsUtil;
import bawei.com.electronicmall.util.HttpRetrofitUile;
import bawei.com.electronicmall.util.ImageTool;
import bawei.com.electronicmall.util.WayApi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MyPersonalActivity extends AppCompatActivity implements IView {


    @BindView(R.id.simple_sdv)
    SimpleDraweeView simpleSdv;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_prass)
    TextView textPrass;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.btn_login_tuei)
    Button btnLoginTuei;
    private Unbinder bind;
    private Presenter presenter;
    private PopupWindow popupWindow;
    private AlertDialog.Builder builder;
    private TextView text_determine;
    private TextView text_cancel;
    private EditText text_usernick;
    private EditText text_oldpwd;
    private EditText text_newpwd;
    private PopupWindow popupWindow2;
    private View parent;
    private Uri imageUri;
    //弹窗中调用摄像头拍照
    public static final int TAKE_PHOTO = 1;
    //弹窗中调用相册
    public static final int CHOOSE_PHOTO = 0;

    private static final int TAKE_OOOP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_personal);
        bind = ButterKnife.bind(this);
        presenter = new Presenter(this);
        EventBus.getDefault().register(this);
        getDate();
        getPopupWindow();
    }

    private void getPopupWindow() {
        parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View changeuserportrait = LayoutInflater.from(MyPersonalActivity.this).inflate(R.layout.layout_changeuserportrait, null, false);
        Button btnCamera = (Button) changeuserportrait.findViewById(R.id.btn_head_camera);
        Button btnAlbum = (Button) changeuserportrait.findViewById(R.id.btn_head_album);
        Button btnCancel = (Button) changeuserportrait.findViewById(R.id.btn_head_cancel);
        popupWindow2 = new PopupWindow(changeuserportrait, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setTouchable(true);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_head_camera:
                        //创建File对象，用于存储拍照后的照片
                        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                        try {
                            if (outputImage.exists()){
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT >= 24){
                            imageUri = FileProvider.getUriForFile(MyPersonalActivity.this,"",outputImage);
                        }else {
                            imageUri = Uri.fromFile(outputImage);
                        }
                        //启动相机程序
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        startActivityForResult(intent,TAKE_PHOTO);
//                        //调用相机
//                        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
//
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE,Uri.fromFile(new File(imageUri.getPath())));
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                        startActivityForResult(intent, TAKE_PHOTO);
                        break;
                    case R.id.btn_head_album:
                        if (ContextCompat.checkSelfPermission(MyPersonalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MyPersonalActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                        }else {
                            openAlbum();
                        }
//                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(i, TAKE_ALBUM);

                        //判断sd卡是否挂载
//                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//挂载状态
////                            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"wdmallimgs/hi.jpg";
//
////                            Intent intent2 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                            startActivityForResult(intent2, TAKE_ALBUM);
//                            Intent intent3 = new Intent(Intent.ACTION_PICK);//"android.intent.action.GET_CONTENT"
//                            intent3.setType("image/*");
//                            startActivityForResult(intent3, TAKE_ALBUM);
//
//
//
//
////                            System.out.println("path:====="+path);
////                            //takephoto，选择器，选择图片，只能选择一张，回调回来一个file对象
////                            File file = new File(path);
////                            //如果文件存在
////                            if (file!=null&&file.exists()){
////
////                                //图片请求体
////                                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);//只能上传图片
////                                //对图片请求体对象，封装成multipart对象，文件表单对象
////                                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image",file.getName(),requestBody);
////
////                                postDate(filePart);
////
////                            }else{
////                                ToastUtils.showShort("请选择文件");
////                            }
//
//
//                        }
//                        Toast.makeText(MyPersonalActivity.this, "相册", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_head_cancel:
                        popupWindow2.dismiss();
                        break;
                }
                popupWindow2.dismiss();
            }
        };

        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                             lp.alpha=1.0f;
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            getWindow().setAttributes(lp);
            }
        });

        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        //PopupWindow
        final View inflate = LayoutInflater.from(MyPersonalActivity.this).inflate(R.layout.layout_popupwindow, null, false);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });
        text_determine = inflate.findViewById(R.id.text_determine);
        text_cancel = inflate.findViewById(R.id.text_cancel);
        //AlertDialog.Builder
        builder = new AlertDialog.Builder(MyPersonalActivity.this);

        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        text_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text_det = inflate.findViewById(R.id.text_determine);
                String trim = text_det.getText().toString().trim();
                if (trim.equals("修改昵称")) {
                    //修改昵称
                    View changeusernick = LayoutInflater.from(MyPersonalActivity.this).inflate(R.layout.layout_changeusernick, null, false);
                    text_usernick = changeusernick.findViewById(R.id.text_usernick);

                    builder.setTitle("修改昵称");
                    builder.setView(changeusernick);
                } else if (trim.equals("修改密码")) {
                    //修改昵称
                    final View changeuserpwdk = LayoutInflater.from(MyPersonalActivity.this).inflate(R.layout.layout_changeuserpwdk, null);
                    text_oldpwd = changeuserpwdk.findViewById(R.id.text_oldpwd);
                    text_newpwd = changeuserpwdk.findViewById(R.id.text_newpwd);


                    builder.setTitle("修改密码");
                    builder.setView(changeuserpwdk);
                } else {

                }
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView text_det = inflate.findViewById(R.id.text_determine);
                        String trim = text_det.getText().toString().trim();
                         if (trim.equals("修改昵称")) {
                            String usernick = text_usernick.getText().toString().trim();
                            if (usernick.equals("")) {
                                Toast.makeText(MyPersonalActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, String> map = new HashMap<>();
                                map.put("nickName",usernick);
                                presenter.put(Api.UPMODIFY_USER_NICKNAME, map, UserDateBean.class);
                            }
                        } else if (trim.equals("修改密码")) {
                            String oldpwd = text_oldpwd.getText().toString().trim();
                            String newpwd = text_newpwd.getText().toString().trim();
                            if (oldpwd.equals("") || oldpwd.equals("")) {
                                Toast.makeText(MyPersonalActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, String> map = new HashMap<>();
                                map.put("oldPwd",oldpwd);
                                map.put("newPwd",newpwd);
                                presenter.put(Api.UPMODIFY_USER_PASSWORD,map, UserDateBean.class);
                            }
                        } else {

                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                popupWindow.dismiss();
                builder.show();
            }
        });


    }
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //将拍摄的照片显示出来
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        picture.setImageBitmap(bitmap);
                        Uri uri = Uri.parse("file://" + imageUri);
                        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                                .setResizeOptions(new ResizeOptions(100, 100))
                                .build();
                        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                                .setOldController(simpleSdv.getController())
                                .setImageRequest(request)
                                .build();
                        simpleSdv.setController(controller);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4以下系统使用这个放出处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        diaplayImage(imagePath);//根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        diaplayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void diaplayImage(String imagePath) {
        if (imagePath != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            simpleSdv.setImageBitmap(bitmap);
            Environment.getExternalStorageDirectory().getAbsolutePath();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+imagePath;
            File file = new File(path);
            //如果文件存在
                            if (file!=null&&file.exists()){

                                //图片请求体
                                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);//只能上传图片
                                //对图片请求体对象，封装成multipart对象，文件表单对象
                                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image",file.getName());

                                postDate(filePart);

                            }else{
                                ToastUtils.showShort("请选择文件");
                            }


//            Uri uri = Uri.parse("file://" + imagePath);
//            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                    .setResizeOptions(new ResizeOptions(100, 100))
//                    .build();
//            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setOldController(simpleSdv.getController())
//                    .setImageRequest(request)
//                    .build();
//            simpleSdv.setController(controller);
        } else {
            Toast.makeText(this, "failed to get iamge", Toast.LENGTH_SHORT).show();
        }
    }
    private void postDate(MultipartBody.Part filePart) {
        HttpRetrofitUile.getInstance().create(WayApi.class).postPart(Api.UPMODIFY_HEAD_PIC,filePart)
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
                            Gson gson = new Gson();
                            ModifyBean fromJson = gson.fromJson(string, ModifyBean.class);
                            String status = fromJson.getStatus();
                            String message = fromJson.getMessage();
                            if (status.equals(ConditionsUtil.CONDITIO)){
                                getDate();
                            }
                            Toast.makeText(MyPersonalActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("-----",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getDate() {
        Map<String, String> map = new HashMap<>();
        presenter.getQuery(Api.QUERY_USER_DATE,map,UserDateBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sxx(String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }

    @OnClick({R.id.simple_sdv, R.id.text_name, R.id.text_prass, R.id.btn_login_tuei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.simple_sdv:
                //修改头像
                popupWindow2.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha=0.3f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
                break;
            case R.id.text_name:
                text_determine.setText("修改昵称");
                popupWindow.showAsDropDown(textName);
                WindowManager.LayoutParams lp2=getWindow().getAttributes();
                lp2.alpha=0.3f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp2);
                break;
            case R.id.text_prass:
                text_determine.setText("修改密码");
                popupWindow.showAsDropDown(textPrass);
                WindowManager.LayoutParams lp3=getWindow().getAttributes();
                lp3.alpha=0.3f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp3);
                break;
            case R.id.btn_login_tuei:
                Intent intent = new Intent(MyPersonalActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSuccessful(Object date) {
        if (date instanceof UserDateBean) {
            UserDateBean bean = (UserDateBean) date;
            String status = bean.getStatus();
            String message = bean.getMessage();
            UserDateBean.ResultBean result = bean.getResult();
            if (status.equals("0000")) {
                if (result == null) {
                    Toast.makeText(MyPersonalActivity.this, message, Toast.LENGTH_SHORT).show();
                    getDate();
                } else {
                    String nickName = result.getNickName();
                    String headPic = result.getHeadPic();
                    String password = result.getPassword();
                    String sex = result.getSex() + "";
                    EventBus.getDefault().post(new MyUserEvent(headPic, nickName));
                    textName.setText(nickName);
                    simpleSdv.setImageURI(Uri.parse(headPic));
                    textPrass.setText(password);
                    if (sex.equals("1")) {
                        textSex.setText("男");
                    } else {
                        textSex.setText("女");
                    }
                }
            } else {
                Toast.makeText(MyPersonalActivity.this, "个人信息" + message, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onError(String e) {
        Log.e("-----", e);
    }
}
