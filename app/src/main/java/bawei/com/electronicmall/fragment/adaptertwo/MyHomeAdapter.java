package bawei.com.electronicmall.fragment.adaptertwo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bawei.com.electronicmall.R;
import bawei.com.electronicmall.bean.BannerBean;
import bawei.com.electronicmall.bean.GoodsListBean;
import bawei.com.electronicmall.fragment.eventtwo.GoodsBeanEvent;
import bawei.com.electronicmall.util.Api;
import bawei.com.electronicmall.util.HttpRetrofitUile;
import bawei.com.electronicmall.util.WayApi;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @作者 熊金梦
 * @时间 2019/2/21 0021 15:00
 * @
 */
public class MyHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final GoodsListBean.ResultBean list;
    private final Context context;

    public MyHomeAdapter(Context context, GoodsListBean.ResultBean resultBean) {
        super();
        this.context=context;
        this.list=resultBean;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        } else if (position==1){
            return 1;
        } else if (position==2){
            return 2;
        } else {
            return 3;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==0){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bgabanner, viewGroup, false);
            return new Hodler1(view);
        } else if (i==1){
            View view = View.inflate(context, R.layout.layout_recycler_list_text, null);
            return new Hodler2(view);
        } else if (i==2){
            View view = View.inflate(context, R.layout.layout_recycler_list_text, null);
            return new Hodler3(view);
        } else {
            View view = View.inflate(context, R.layout.layout_recycler_list_text, null);
            return new Hodler4(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof Hodler1){
            Hodler1 hodler1 = (Hodler1) viewHolder;
            Map<String,String> map = new HashMap<>();
            HttpRetrofitUile.getInstance().create(WayApi.class).get(Api.QUERY_BANNER_URL,map)
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
                                BannerBean json = gson.fromJson(string, BannerBean.class);
                                List<BannerBean.ResultBean> beanList = json.getResult();
                                String message = json.getMessage();
                                final String status = json.getStatus();

                                if (status.equals("0000")) {
                                    final ArrayList<String> strings = new ArrayList<>();
                                    for (int j = 0; j < beanList.size(); j++) {
                                        String imageUrl = beanList.get(j).getImageUrl();
                                        strings.add(imageUrl);
                                    }
                                    hodler1.bgabanner.setData(strings,null);
                                    hodler1.bgabanner.loadImage(new XBanner.XBannerAdapter() {
                                        @Override
                                        public void loadBanner(XBanner banner, Object model, View view, int position) {
                                            Glide.with(context).load(strings.get(position)).into((ImageView) view);
                                        }
                                    });
                                    hodler1.bgabanner.setPageTransformer(Transformer.Default);
                                    hodler1.bgabanner.setPageChangeDuration(1000);
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (viewHolder instanceof Hodler2){
            Hodler2 hodler2 = (Hodler2) viewHolder;
            //热销新品
                final String id = this.list.getRxxp().getId() + "";
                String name = this.list.getRxxp().getName();
                List<GoodsListBean.ResultBean.RxxpBean.CommodityListBean> rxxpList = this.list.getRxxp().getCommodityList();

                hodler2.text_recycler_rxxp.setText(name);
                hodler2.text_recycler_rxxp.setTextColor(Color.parseColor("#FF7F57"));
                hodler2.image_recycler_backg.setImageResource(R.mipmap.bitmapone);
                hodler2.image_recycler.setImageResource(R.mipmap.common_btn_more_yellow_n_hdpi);
                MyRecyelGridAdapter recyclerAdapter = new MyRecyelGridAdapter(context, rxxpList);
                hodler2.grid_view.setAdapter(recyclerAdapter);
                GridLayoutManager linearLasa = new GridLayoutManager(context, 3);
                linearLasa.setOrientation(LinearLayoutManager.VERTICAL);
                linearLasa.setReverseLayout(false);
                hodler2.grid_view.setLayoutManager(linearLasa);
                hodler2.image_recycler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new GoodsBeanEvent(name,id));
                    }
                });

        } else if (viewHolder instanceof Hodler3){
            Hodler3 hodler3 = (Hodler3) viewHolder;
            //魔力时尚
                final String id = this.list.getMlss().getId() + "";
                String name2 = this.list.getMlss().getName();
                List<GoodsListBean.ResultBean.MlssBean.CommodityListBeanXX> mlssList = this.list.getMlss().getCommodityList();
                hodler3.text_recycler_rxxp.setText(name2);
                hodler3.text_recycler_rxxp.setTextColor(Color.parseColor("#7D7FF6"));
                hodler3.image_recycler_backg.setImageResource(R.mipmap.bitmap);
                hodler3.image_recycler.setImageResource(R.mipmap.home_btn_more_purple_n_hdpi);
                RecyclermlssAdapter recyclermlssAdapter = new RecyclermlssAdapter(context,mlssList);
                hodler3.grid_view.setAdapter(recyclermlssAdapter);
                LinearLayoutManager linearLayout = new LinearLayoutManager(context);
                linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayout.setReverseLayout(false);
                hodler3.grid_view.setLayoutManager(linearLayout);
                hodler3.image_recycler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new GoodsBeanEvent(name2,id));
                    }
                });

        }if (viewHolder instanceof Hodler4){
            Hodler4 hodler4 = (Hodler4) viewHolder;
            //品质生活
                final String id = this.list.getPzsh().getId() + "";
                String name1 = this.list.getPzsh().getName();
                List<GoodsListBean.ResultBean.PzshBean.CommodityListBeanX> pzshList = this.list.getPzsh().getCommodityList();
                hodler4.text_recycler_rxxp.setText(name1);
                hodler4.text_recycler_rxxp.setTextColor(Color.parseColor("#FF5F71"));
                hodler4.image_recycler_backg.setImageResource(R.mipmap.bitmaptwo);
                hodler4.image_recycler.setImageResource(R.mipmap.home_btn_moer_pink_n_hdpi);
                MyRecyelPzshAdapter gridlistAdapter = new MyRecyelPzshAdapter(context,pzshList);
                hodler4.grid_view.setAdapter(gridlistAdapter);
                GridLayoutManager linearLa = new GridLayoutManager(context,2);
                linearLa.setOrientation(LinearLayoutManager.VERTICAL);
                linearLa.setReverseLayout(false);
                hodler4.grid_view.setLayoutManager(linearLa);
                hodler4.image_recycler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new GoodsBeanEvent(name1,id));
                    }
                });
            }
        }

    @Override
    public int getItemCount() {
        return 4;
    }

    private class Hodler1 extends RecyclerView.ViewHolder {
        private final XBanner bgabanner;

        public Hodler1(View view) {
            super(view);
            bgabanner = view.findViewById(R.id.bgabanner);
        }
    }

    private class Hodler2 extends RecyclerView.ViewHolder {
        private final ImageView image_recycler_backg;
        private final TextView text_recycler_rxxp;
        private final ImageView image_recycler;
        private final RecyclerView grid_view;
        public Hodler2(View view) {
            super(view);
            image_recycler_backg = view.findViewById(R.id.image_recycler_backg);
            text_recycler_rxxp = view.findViewById(R.id.text_recycler_rxxp);
            image_recycler = view.findViewById(R.id.image_recycler);
            grid_view = view.findViewById(R.id.grid_view);
        }
    }

    private class Hodler3 extends RecyclerView.ViewHolder {
        private final ImageView image_recycler_backg;
        private final TextView text_recycler_rxxp;
        private final ImageView image_recycler;
        private final RecyclerView grid_view;
        public Hodler3(View view) {
            super(view);
            image_recycler_backg = view.findViewById(R.id.image_recycler_backg);
            text_recycler_rxxp = view.findViewById(R.id.text_recycler_rxxp);
            image_recycler = view.findViewById(R.id.image_recycler);
            grid_view = view.findViewById(R.id.grid_view);
        }
    }

    private class Hodler4 extends RecyclerView.ViewHolder {
        private final ImageView image_recycler_backg;
        private final TextView text_recycler_rxxp;
        private final ImageView image_recycler;
        private final RecyclerView grid_view;
        public Hodler4(View view) {
            super(view);
            image_recycler_backg = view.findViewById(R.id.image_recycler_backg);
            text_recycler_rxxp = view.findViewById(R.id.text_recycler_rxxp);
            image_recycler = view.findViewById(R.id.image_recycler);
            grid_view = view.findViewById(R.id.grid_view);
        }
    }
}
