package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.itemdetail.ItemComment;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class CommentList extends RecyclerView.Adapter<CommentList.ViewHolder> {
    private List<ItemComment> android;
    private Context context;


    public CommentList(Context context, List<ItemComment> android) {
        this.context = context;
        this.android = android;
    }

    @Override
    public CommentList.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_comment, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentList.ViewHolder viewHolder, final int i) {


        viewHolder.title.setText(android.get(i).getCmtName());
        viewHolder.desc.setText(android.get(i).getCmtTxt());
       /* Glide
                .with(context)
                .load(android.get(i).getCmtIcon())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .crossFade()
                .into(viewHolder.icon);*/

        Glide.with(context).load(android.get(i).getCmtIcon()).asBitmap().placeholder(R.drawable.placeholder).centerCrop().into(new BitmapImageViewTarget(viewHolder.icon) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                viewHolder.icon.setImageDrawable(circularBitmapDrawable);
            }

        });



    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView desc;
        private ImageView icon;
        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.textView_name);
            icon = (ImageView) view.findViewById(R.id.profile_icon);
            desc = (TextView)view.findViewById(R.id.textView_desc);

        }
    }



}
