package bawei.com.electronicmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bawei.com.electronicmall.DetailsActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.MyClassifBean;
import bawei.com.electronicmall.event.DetailsEvent;
import bawei.com.electronicmall.fragment.adaptertwo.SimpleRecyCirclelistAdapter;
import bawei.com.electronicmall.fragment.adaptertwo.SimpleRecyCircletwoAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 熊金梦
 * @时间 2019/3/7 0007 12:06
 * @
 */
public class MyMyClassifAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<MyClassifBean.ResultBean> list;

    public MyMyClassifAdapter(Context context, List<MyClassifBean.ResultBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_classif_my, viewGroup, false);
        return new Holderm(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        String commodityId = list.get(i).getCommodityId()+"";
        String content = list.get(i).getContent();
        long createTime = list.get(i).getCreateTime();
        int greatNum = list.get(i).getGreatNum();
        String image = list.get(i).getImage();
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String time = dateFormat.format(createTime);
        if (viewHolder instanceof Holderm){
            Holderm holderm = (Holderm) viewHolder;
            holderm.textClassifTextName.setText(content);
            holderm.textClassifTextTime.setText(time);
            holderm.textALikeClassif.setText(greatNum+"");
            holderm.checkClasifBox.setChecked(list.get(i).isSicktp());
            if (image.isEmpty()){
                holderm.recyClassifView.setVisibility(View.GONE);
                holderm.simpleDraweeView.setVisibility(View.GONE);
            } else if (image.indexOf(",")== -1){
                    holderm.recyClassifView.setVisibility(View.GONE);
                    holderm.simpleDraweeView.setVisibility(View.VISIBLE);
                    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(image))
                            .setAutoPlayAnimations(true)
                            .build();
                    holderm.simpleDraweeView.setController(draweeController);
                } else {
                    holderm.simpleDraweeView.setVisibility(View.GONE);
                    holderm.recyClassifView.setVisibility(View.VISIBLE);
                    String[] split = image.split(",");
//                Log.e("-------",image);
//                Log.e("-------",split.length+"");
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int j = 0; j < split.length; j++) {
                        arrayList.add(split[j]);

                    }
                    if (split.length==2){
                        SimpleRecyCircletwoAdapter simpleAdapter = new SimpleRecyCircletwoAdapter(context,arrayList,commodityId);
                        GridLayoutManager manager = new GridLayoutManager(context, 2);
                        manager.setOrientation(GridLayoutManager.VERTICAL);
                        holderm.recyClassifView.setLayoutManager(manager);
                        holderm.recyClassifView.setAdapter(simpleAdapter);
                    } else {
                        SimpleRecyCirclelistAdapter simplelistAdapter = new SimpleRecyCirclelistAdapter(context,arrayList,commodityId);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                        holderm.recyClassifView.setLayoutManager(gridLayoutManager);
                        holderm.recyClassifView.setAdapter(simplelistAdapter);
                    }
            }
            holderm.checkClasifBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(i).setSicktp(holderm.checkClasifBox.isChecked());
                }
            });
            holderm.textClassifTextName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new DetailsEvent(commodityId));
                    Intent intent = new Intent(context,DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
            holderm.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new DetailsEvent(commodityId));
                    Intent intent = new Intent(context,DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
            holderm.checkClasifBoxLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "这是你自己发布的圈子！", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holderm extends RecyclerView.ViewHolder {
        @BindView(R.id.check_clasif_box)
        CheckBox checkClasifBox;
        @BindView(R.id.text_classif_text_name)
        TextView textClassifTextName;
        @BindView(R.id.recy_classif_view)
        RecyclerView recyClassifView;
        @BindView(R.id.text_classif_text_time)
        TextView textClassifTextTime;
        @BindView(R.id.check_clasif_box_like)
        CheckBox checkClasifBoxLike;
        @BindView(R.id.text_a_like_classif)
        TextView textALikeClassif;
        @BindView(R.id.simple_classif_view)
        SimpleDraweeView simpleDraweeView;
        public Holderm(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
