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
import tech.town.app.com.apptowntech.model.bookmark.Favourite;
import tech.town.app.com.apptowntech.utils.Apputil;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class BookMarkList extends RecyclerView.Adapter<BookMarkList.ViewHolder> {
    private List<Favourite> android;
    private Context context;

    private OnRemoveFavClickListener onRemoveFavClickListener;

    public BookMarkList(Context context, List<Favourite> android, OnRemoveFavClickListener onRemoveFavClickListener) {
        this.android = android;
        this.context = context;
        this.onRemoveFavClickListener=onRemoveFavClickListener;

    }

    @Override
    public BookMarkList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_bookmark, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookMarkList.ViewHolder viewHolder, final int i) {


        viewHolder.title.setText(android.get(i).getFvName());

        Glide.with(context)
                .load(android.get(i).getFvIcon())
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        // you can do something with loaded bitmap here

                        // ....
                        viewHolder.icon.setImageBitmap(resource);
                    }
                });



        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveFavClickListener.RemoveFavourite(android.get(i).getFvId(),i);

            }
        });

        viewHolder.icon_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri au=Apputil.getLocalBitmapUri(context,viewHolder.icon);
                if(au==null){
                    Apputil.share(context, android.get(i).getFvName());
                }else{
                    Apputil.share(context,android.get(i).getFvName(),au);
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
        private ImageView delete;
        private ImageView icon_share    ;
        private ImageView icon;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.textView2);
            icon = (ImageView) view.findViewById(R.id.imageView3);
            delete = (ImageView) view.findViewById(R.id.imageView5);
            icon_share = (ImageView) view.findViewById(R.id.imageView4);
        }
    }

    public Favourite getItem(int position) {
        return android.get(position);
    }

    public interface OnRemoveFavClickListener {
        void RemoveFavourite(String pid, int i);
    }
    public void notifydatachange(List<Favourite> list){
        android=list;
        notifyDataSetChanged();
    }
}
