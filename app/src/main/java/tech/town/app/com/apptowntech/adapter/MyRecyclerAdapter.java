package tech.town.app.com.apptowntech.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.model.Account;
import tech.town.app.com.apptowntech.model.AccountHeader;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Logger;

/**
 * Created by ${="Ashish"} on 12/9/16.
 */


public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private OnGoogleClickListener googleClickListener;
    private OnFacebookClickListener facebookClickListener;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    int[] drawable = {
            R.drawable.customize,
            R.drawable.favorite,
            R.drawable.feedback,
            R.drawable.textonly,
            R.drawable.notification,
            R.drawable.feedback,
            R.drawable.cache,
            R.drawable.rating,
            R.drawable.cache,
            R.drawable.rating,
            R.drawable.appshare,
            R.drawable.about,
            R.drawable.terms,
            R.drawable.update,
            R.drawable.logout};
    AccountHeader header;
    List<Account> listItems;
    private Context mContext;
    private AppPref appPref;

    public MyRecyclerAdapter(Context context, AccountHeader header, List<Account> listItems
            , OnGoogleClickListener googleClickListener, OnFacebookClickListener onfacebook) {
        this.mContext = context;
        this.header = header;
        this.listItems = listItems;
        this.googleClickListener = googleClickListener;
        this.facebookClickListener = onfacebook;
        appPref = new AppPref(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_header_info, parent, false);
            return new VHHeader(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_account_info, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    public Account getItem(int position) {
        return listItems.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHHeader) {
            final VHHeader VHheader = (VHHeader) holder;

            if (TextUtils.isEmpty(appPref.getAccessToken(mContext))) {

                VHheader.loggedIn.setVisibility(View.GONE);
                VHheader.loggedOut.setVisibility(View.VISIBLE);
            } else {
                VHheader.txtTitle.setText(appPref.getUserName(mContext));
                Glide.with(mContext).load(appPref.getImageIcon(mContext)).asBitmap().centerCrop().placeholder(R.drawable.com_facebook_profile_picture_blank_square).into(new BitmapImageViewTarget(VHheader.profileIcon) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        VHheader.profileIcon.setImageDrawable(circularBitmapDrawable);
                    }
                });
                VHheader.loggedOut.setVisibility(View.GONE);
                VHheader.loggedIn.setVisibility(View.VISIBLE);

            }
            VHheader.google.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    googleClickListener.onGoogleClick();
                }
            });
            VHheader.facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    facebookClickListener.onFbClick();
                }
            });


        } else if (holder instanceof VHItem) {
            Account currentItem = getItem(position - 1);
            VHItem VHitem = (VHItem) holder;

            if(position==4 ||position==5){
                VHitem.arrow.setVisibility(View.GONE);
                VHitem.aSwitch.setVisibility(View.VISIBLE);
            }else{
                VHitem.aSwitch.setVisibility(View.GONE);
                VHitem.arrow.setVisibility(View.VISIBLE);
            }

            if(appPref.getDataSaveMode(mContext)){
                VHitem.aSwitch.setChecked(true);
            }else{
                VHitem.aSwitch.setChecked(false);
            }
            VHitem.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    appPref.saveDataSavingMode(b);
                }
            });

            if(position==12){
                PackageInfo pInfo = null;
                try {
                    pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
                    String version = pInfo.versionName;
                    VHitem.info.setText(version+"v");
                } catch (PackageManager.NameNotFoundException e) {
                    VHitem.info.setText("");
                }
            }else if(position==7){
                VHitem.info.setText(""+mContext.getExternalCacheDir().length()/1000+" MB");
            }else{
                VHitem.info.setText("");
            }

            VHitem.txtName.setText(currentItem.getName());
            Logger.d("Postion  "+position);
            VHitem.iv.setImageResource(drawable[position-1]);
        }


    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return listItems.size() + 1;
    }

    class VHHeader extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView profileIcon;
        ImageView google;
        ImageView facebook;
        LinearLayout loggedIn;
        LinearLayout loggedOut;


        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.textView_title);
            this.profileIcon = (ImageView) itemView.findViewById(R.id.image_profile);
            this.google = (ImageView) itemView.findViewById(R.id.button_google);
            this.facebook = (ImageView) itemView.findViewById(R.id.fb);
            this.loggedIn = (LinearLayout) itemView.findViewById(R.id.container_logged_in);
            this.loggedOut = (LinearLayout) itemView.findViewById(R.id.container_not_logged_in);


        }

    }

    class VHItem extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView iv;
        ImageView arrow;
        Switch  aSwitch;
        TextView info;


        public VHItem(View itemView) {
            super(itemView);
            this.txtName = (TextView) itemView.findViewById(R.id.textView_info);
            this.iv = (ImageView) itemView.findViewById(R.id.image_2);
            this.arrow = (ImageView) itemView.findViewById(R.id.arrow);
            this.aSwitch = (Switch) itemView.findViewById(R.id.switch_mobile);
            this.info=(TextView)itemView.findViewById(R.id.textView_size);
        }
    }

    public interface OnFacebookClickListener {
        void onFbClick();
    }

    public interface OnGoogleClickListener {
        void onGoogleClick();
    }
}

