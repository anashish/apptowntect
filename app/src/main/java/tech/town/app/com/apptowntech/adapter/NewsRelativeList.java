package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.itemdetail.ItemSimiler;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class NewsRelativeList extends RecyclerView.Adapter<NewsRelativeList.ViewHolder> {
    private final int mScreenWidth;
    private List<ItemSimiler> android;
    private Context context;

    public NewsRelativeList(Context context, List<ItemSimiler> android) {
        this.android = android;
        this.context = context;
        mScreenWidth = Apputil.getDisplayPoint(context).x;
    }

    @Override
    public NewsRelativeList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_news_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRelativeList.ViewHolder viewHolder, int i) {


        viewHolder.title.setText(android.get(i).getPTtl());
        viewHolder.date.setText(android.get(i).getPDt());


       int  imageWidth = mScreenWidth / 2;
       float imageWidthRatio = 4;
       float imageHeightRatio = 3;


        int panelHeight = Apputil.getHeightOfImage(imageWidth, imageWidthRatio, imageHeightRatio);
        viewHolder.conatiner.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, panelHeight));
        viewHolder.icon.setLayoutParams(new RelativeLayout.LayoutParams(imageWidth, panelHeight));

        if(new AppPref(context).getDataSaveMode(context)){
            Glide
                    .with(context)
                    .load("")
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(viewHolder.icon);
        }else{
            Glide
                    .with(context)
                    .load(android.get(i).getPIcon())
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(viewHolder.icon);
        }



    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final RelativeLayout conatiner;
        private TextView title;
        private TextView date;
        private ImageView icon;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.text_home_title);
            date = (TextView)view.findViewById(R.id.textView_date);
            icon = (ImageView) view.findViewById(R.id.image_home_icon);
            conatiner=(RelativeLayout)view.findViewById(R.id.relateConatiner);
        }
    }
    public ItemSimiler getItem(int position) {
        return android.get(position);
    }
}
