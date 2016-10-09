package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class HomeList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<HomeCategory> android;
    private Context context;

    int mWidth;

    public HomeList(Context context, List<HomeCategory> android) {
        this.android = android;
        this.context = context;
        Point point = Apputil.getDisplayPoint(context);
        mWidth = point.x-100;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        if (i == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_banner_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (i == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_list, parent, false);
            return new ViewHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + i + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof HeaderViewHolder) {


            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            headerViewHolder.autoScrollViewPager.setAdapter(new CustomPagerAdapter(context,android.get(i).getCId(), android.get(i).getCPost(),android.get(i).getCName()));

        } else if (viewHolder instanceof ViewHolder) {




            int pos = i - 1;
            final ViewHolder viewHolde = (ViewHolder) viewHolder;
            viewHolde.title.setText(android.get(i).getCName());

            FrameLayout.LayoutParams layoutParams=null;

            if (pos % 4 == 0) {
                 layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  mWidth);
            }
            else {
                layoutParams = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,  mWidth/2);


            }
            viewHolde.icon.setLayoutParams(layoutParams);
            viewHolde.icon.setAdjustViewBounds(true);
            viewHolde.icon.setScaleType(ImageView.ScaleType.FIT_XY);


            if(new AppPref(context).getDataSaveMode(context)){
                Glide
                        .with(context)
                        .load("")
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolde.icon);
            }else{
                Glide
                        .with(context)
                        .load(android.get(i).getCIcon())
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolde.icon);
            }




        }

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return android.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_home_title);
            icon = (ImageView) view.findViewById(R.id.image_home_icon);
            icon.setAdjustViewBounds(true);
            icon.setScaleType(ImageView.ScaleType.FIT_XY);

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        AutoScrollViewPager autoScrollViewPager;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            autoScrollViewPager = (AutoScrollViewPager) itemView.findViewById(R.id.view_pager);
            autoScrollViewPager.setInterval(4000);
            autoScrollViewPager.startAutoScroll();
            autoScrollViewPager.setCycle(true);

        }
    }

    public HomeCategory getItem(int position) {
        return android.get(position-1);
    }

}
