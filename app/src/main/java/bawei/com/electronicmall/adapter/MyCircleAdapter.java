package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.ClassifBean;
import bawei.com.electronicmall.event.CirclePraiseEvent;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/2/20 0020 15:10
 * @
 */
public class MyCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ClassifBean.ResultBean> list;

    public MyCircleAdapter(Context context, List<ClassifBean.ResultBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_circle, viewGroup, false);
        return new Hoderil(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof Hoderil){
            Hoderil hoderil = (Hoderil) viewHolder;
            String commodityId = list.get(i).getCommodityId()+"";
            String s = list.get(i).getId() + "";
            String headPic = list.get(i).getHeadPic();
            String nickName = list.get(i).getNickName();
            long createTime = list.get(i).getCreateTime();
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String time = dateFormat.format(createTime);
            String content = list.get(i).getContent();
            String image = list.get(i).getImage();
            int greatNum = list.get(i).getGreatNum();
            hoderil.simpleCircleNameSdv.setImageURI(Uri.parse(headPic));
            hoderil.textCircleName.setText(nickName);
            hoderil.textCircleTime.setText(time);
            hoderil.textCircleData.setText(content);
            hoderil.textALike.setText(greatNum+"");
            hoderil.simpleCircleTitle.setImageURI(Uri.parse(image));
//            hoderil.recySimpleCircleTitle.setVisibility(View.GONE);
            hoderil.buttonCircle.setOnClickListener(new View.OnClickListener() {
                private String st = "";

                @Override
                public void onClick(View v) {
                    if (hoderil.buttonCircle.isChecked()){
                        st="0";
                        int itr = greatNum + 1;
                        hoderil.textALike.setText(itr+"");
                    } else {
                        st="1";
                        hoderil.textALike.setText(greatNum+"");
                    }
                    EventBus.getDefault().post(new CirclePraiseEvent(s,st));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Hoderil extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_circle_name_sdv)
        SimpleDraweeView simpleCircleNameSdv;
        @BindView(R.id.text_circle_name)
        TextView textCircleName;
        @BindView(R.id.text_circle_time)
        TextView textCircleTime;
        @BindView(R.id.text_circle_data)
        TextView textCircleData;
        @BindView(R.id.simple_circle_title)
        SimpleDraweeView simpleCircleTitle;
//        @BindView(R.id.recy_simple_circle_title)
//        RecyclerView recySimpleCircleTitle;
        @BindView(R.id.button_circle)
        CheckBox buttonCircle;
        @BindView(R.id.text_a_like)
        TextView textALike;
        public Hoderil(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
