package bawei.com.electronicmall.fragment.adaptertwo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import bawei.com.electronicmall.DetailsActivity;
import bawei.com.electronicmall.R;
import bawei.com.electronicmall.event.DetailsEvent;

/**
 * @作者 熊金梦
 * @时间 2019/2/26 0026 14:48
 * @
 */
public class SimpleRecyCirclelistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<String> list;
    private final Context context;
    private final String spid;

    public SimpleRecyCirclelistAdapter(Context context, ArrayList<String> arrayList, String commodityId) {
        super();
        this.context = context;
        this.list = arrayList;
        this.spid=commodityId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_simple_recy_circle_list, viewGroup, false);
        return new Homder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof Homder){
            Homder homder = (Homder) viewHolder;
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(list.get(i)))
                    .setAutoPlayAnimations(true)
                    .build();
            homder.simple_simple.setController(draweeController);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new DetailsEvent(spid));
                    Intent intent = new Intent(context,DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class Homder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView simple_simple;

        public Homder(View view) {
            super(view);
            simple_simple = view.findViewById(R.id.simple_simple);
        }
    }
}
