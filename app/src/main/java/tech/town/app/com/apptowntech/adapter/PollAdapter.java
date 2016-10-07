package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.poll.PollList;
import tech.town.app.com.apptowntech.utils.Logger;

/**
 * Created by ${="Ashish"} on 28/9/16.
 */
public class PollAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PollList> imageList;

    private String value1;
    private OnSubmitClickListener onSubmitClickListener;

    private boolean isShow=false;

    public PollAdapter(Context context, List<PollList> imageList,OnSubmitClickListener onSubmitClickListener) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageList = imageList;
        this.onSubmitClickListener=onSubmitClickListener;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ScrollView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.row_poll, container, false);
        TextView title = (TextView)itemView.findViewById(R.id.poll_title);
        ImageView icon = (ImageView)itemView.findViewById(R.id.poll_image);
        final RadioGroup mRadioGroupOne=(RadioGroup)itemView.findViewById(R.id.radio);

        RadioButton yes=(RadioButton)itemView.findViewById(R.id.radio_yes) ;
        RadioButton no=(RadioButton)itemView.findViewById(R.id.radio_no) ;
        RadioButton dontknow=(RadioButton)itemView.findViewById(R.id.radio_dontknow) ;

        Button pollButton = (Button)itemView.findViewById(R.id.poll_submit);
        ProgressBar firstBar = (ProgressBar)itemView.findViewById(R.id.firstBar);
        ProgressBar secondBar = (ProgressBar)itemView.findViewById(R.id.secondBar);
        ProgressBar thirdBar = (ProgressBar)itemView.findViewById(R.id.thirdBar);

        TextView one = (TextView)itemView.findViewById(R.id.textone);
        TextView two = (TextView)itemView.findViewById(R.id.texttwo);
        TextView three = (TextView)itemView.findViewById(R.id.textthree);


        if(isShow){

            one.setText(""+imageList.get(position).getTlpOp1Cnt()+"%");
            two.setText(""+imageList.get(position).getTlpOp2Cnt()+"%");
            three.setText(""+imageList.get(position).getTlpOp3Cnt()+"%");


            firstBar.setProgress(imageList.get(position).getTlpOp1Cnt());
            secondBar.setProgress(imageList.get(position).getTlpOp2Cnt());
            thirdBar.setProgress(imageList.get(position).getTlpOp3Cnt());

        }




        title.setText(imageList.get(position).getTlpTtl());
        yes.setText(imageList.get(position).getTlpOp1());
        no.setText(imageList.get(position).getTlpOp2());
        dontknow.setText(imageList.get(position).getTlpOp3());


        pollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    value1 = ((RadioButton)itemView.findViewById(mRadioGroupOne.getCheckedRadioButtonId())).getTag().toString();
                    if(TextUtils.isEmpty(value1)){
                        return;
                    }
                    onSubmitClickListener.onSubmit(value1,imageList.get(position).getTlpId());
                }catch (Exception e){
                    Logger.d(e.getMessage());
                }

            }
        });



        Glide
                .with(mContext)
                .load(imageList.get(position).getTlpIcon())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(icon);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ScrollView) object);
    }

    public interface OnSubmitClickListener {
        void onSubmit(String value,int pos);
    }

    public  void notidatachange(boolean b){

        isShow=b;
        notifyDataSetChanged();
    }
}
