package tech.town.app.com.apptowntech.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.CPost;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class HomeList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<CPost> android;
    private Context context;
    private String mCategoryName;
    private String mCategoryID;

    public HomeList(Activity activity, List<CPost> cPost, String cId, String cName) {
        this.android = cPost;
        this.context = activity;
        this.mCategoryName=cName;
        this.mCategoryID=cId;
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
            headerViewHolder.autoScrollViewPager.setAdapter(new CustomPagerAdapter(context,mCategoryID, android,mCategoryName));

        } else if (viewHolder instanceof ViewHolder) {

            int pos = i - 1;

            ((ViewHolder) viewHolder).title.setText(android.get(pos).getPTtl());
            ((ViewHolder) viewHolder).date.setText(android.get(pos).getPDt());


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
        private TextView date;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.cat_title);
            date = (TextView) view.findViewById(R.id.text_home_date);
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

    public CPost getItem(int position) {
        return android.get(position-1);
    }

    public void notifyHomeDataChange(List<CPost> list){
        android=list;
        notifyDataSetChanged();
    }

}
