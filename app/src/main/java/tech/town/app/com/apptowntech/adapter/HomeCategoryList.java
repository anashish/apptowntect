package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.CPost;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class HomeCategoryList extends RecyclerView.Adapter<HomeCategoryList.ViewHolder> {
    private List<CPost> android;
    private Context context;

    private OnAddFavClickListener onAddFavClickListener;
    public HomeCategoryList(Context context, List<CPost> android,OnAddFavClickListener onAddFavClickListener) {
        this.android = android;
        this.context = context;
        this.onAddFavClickListener=onAddFavClickListener;
    }

    @Override
    public HomeCategoryList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_home_category_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeCategoryList.ViewHolder viewHolder, final int i) {


        viewHolder.title.setText(android.get(i).getPTtl());
        viewHolder.date.setText(android.get(i).getPDt());



        if(new AppPref(context).getDataSaveMode(context)){
            Glide.with(context)
                    .load("")
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            // you can do something with loaded bitmap here

                            // .....

                            viewHolder.icon.setImageBitmap(resource);
                        }
                    });
        }else{
            Glide.with(context)
                    .load(android.get(i).getPIcon())
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            // you can do something with loaded bitmap here

                            // .....

                            viewHolder.icon.setImageBitmap(resource);
                        }
                    });
        }





        if(android.get(i).isAddedToFavourite()){

            viewHolder.icon_fvrt.setImageResource(R.drawable.bookmark_filled);
        }else{
            viewHolder.icon_fvrt.setImageResource(R.drawable.bookmarkborder);
        }


        viewHolder.icon_fvrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddFavClickListener.addedFavourite(android.get(i).getPId(),i);

            }
        });
        viewHolder.icon_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Apputil.getLocalBitmapUri(context,viewHolder.icon);
                if(uri==null){
                    Apputil.share(context,android.get(i).getPTtl()+" "+android.get(i).getPIcon()+" ");
                }else{
                    Apputil.share(context,android.get(i).getPTtl(),uri);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView date;
        private ImageView icon_fvrt;
        private ImageView icon_share    ;
        private ImageView icon;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.text_home_title);
            date = (TextView)view.findViewById(R.id.textView_date);
            icon = (ImageView) view.findViewById(R.id.image_home_icon);
            icon_fvrt = (ImageView) view.findViewById(R.id.imageView_favourite);
            icon_share = (ImageView) view.findViewById(R.id.imageView_share);
        }
    }

    public CPost getItem(int position) {
        return android.get(position);
    }

    public interface OnAddFavClickListener {
        void addedFavourite(String pid, int i);
    }
    public void notifydatachange(List<CPost> list){
        android=list;
        notifyDataSetChanged();
    }
}
