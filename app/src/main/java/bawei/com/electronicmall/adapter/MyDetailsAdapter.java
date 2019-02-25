package bawei.com.electronicmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.CommentBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/2/24 0024 20:36
 * @
 */
public class MyDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<CommentBean.ResultBean> list;

    public MyDetailsAdapter(Context context, List<CommentBean.ResultBean> beanResult) {
        super();
        this.context = context;
        this.list = beanResult;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_details_list, viewGroup, false);
        return new Holdert(view);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String headPic = list.get(i).getHeadPic();
        String nickName = list.get(i).getNickName();
        long createTime = list.get(i).getCreateTime();
        String content = list.get(i).getContent();
        String image = list.get(i).getImage();
        if (viewHolder instanceof Holdert){
            Holdert holdert = (Holdert) viewHolder;
            holdert.simpleDetailsListName.setImageURI(Uri.parse(headPic));
            holdert.textDetailsListName.setText(nickName);
            holdert.textDetailsListTitile.setText(content);
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String time = dateFormat.format(createTime);
            holdert.textDetailsListTime.setText(time);
            holdert.simpleDetailsListTitile.setImageURI(Uri.parse(image));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holdert extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_details_list_name)
        SimpleDraweeView simpleDetailsListName;
        @BindView(R.id.text_details_list_name)
        TextView textDetailsListName;
        @BindView(R.id.text_details_list_time)
        TextView textDetailsListTime;
        @BindView(R.id.text_details_list_titile)
        TextView textDetailsListTitile;
        @BindView(R.id.simple_details_list_titile)
        SimpleDraweeView simpleDetailsListTitile;
        public Holdert(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
