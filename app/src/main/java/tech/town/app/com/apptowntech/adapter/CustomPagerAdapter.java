package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.CPost;
import tech.town.app.com.apptowntech.utils.Navigation;

/**
 * Created by ${="Ashish"} on 14/9/16.
 */
public class CustomPagerAdapter extends PagerAdapter {
    CoordinatorLayout coordinatorLayout;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<CPost> imageList;
    private String mCatID;
    private String mCatName;

    public CustomPagerAdapter(Context context, String cId, List<CPost> imageList, String cName) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageList = imageList;
        this.mCatID=cId;
        this.mCatName=cName;


    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.row_home_banner, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.image_home_icon);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        TextView  title = (TextView)itemView.findViewById(R.id.text_home_title);
        TextView  news = (TextView)itemView.findViewById(R.id.textView3);

        news.setText(mCatName);
        title.setText(imageList.get(position).getPTtl());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Gson gson = new Gson();

                *//**
                 *Convert into json and save to app pref
                 *//*
                String json = gson.toJson(imageList);*/

                Navigation.launchDescription(mContext,mCatID,imageList.get(position).getPId(),mCatName);
            }
        });

        Glide
                .with(mContext)
                .load(imageList.get(position).getPIcon())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(imageView);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }

}
