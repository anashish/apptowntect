package tech.town.app.com.apptowntech.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.CommentList;
import tech.town.app.com.apptowntech.adapter.NewsRelativeList;
import tech.town.app.com.apptowntech.helper.JustifiedTextView;
import tech.town.app.com.apptowntech.model.itemdetail.DetailPageResult;
import tech.town.app.com.apptowntech.model.itemdetail.ItemComment;
import tech.town.app.com.apptowntech.model.itemdetail.ItemSimiler;
import tech.town.app.com.apptowntech.presenter.DetailPagePresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.ItemClickSupport;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.utils.Navigation;
import tech.town.app.com.apptowntech.view.DetailPageMvpView;

public class NewsDetailActivity extends BaseActivity implements DetailPageMvpView{


    private ProgressBar mProgressBar;
    private Bundle bundle;
    private String mTitle;
    private String mWebsiteLink;
    private List<ItemComment> mItemComment;
    private int mBookMarkFlag=0;
    private DetailPagePresenter homePresenter;

    private Boolean isLike=false;
    private int count;
    private ImageView mDetailPageIcon;
    private int mTextSize;
    private JustifiedTextView mJTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        bundle=getIntent().getExtras();
        if(bundle!=null){
            toolbar.setTitle(bundle.getString(Navigation.CAT_Name));
            setToolBarBackButton(toolbar);
            homePresenter=new DetailPagePresenter();
            homePresenter.attachView(this);
            homePresenter.loadDescription(bundle.getString(Navigation.CAT_ID),bundle.getString(Navigation.POST_ID));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mItemComment==null){
                    return;
                }

                Gson gson = new Gson();

                /**
                 *Convert into json and save to app pref
                 */
                String json = gson.toJson(mItemComment);
               Navigation.launchComment(NewsDetailActivity.this,json);
            }
        });
        ImageView facebook = (ImageView) findViewById(R.id.facebook_social);
        ImageView google = (ImageView) findViewById(R.id.google_social);
        ImageView twiiter= (ImageView) findViewById(R.id.twiiter_social);
        ImageView whatsup = (ImageView) findViewById(R.id.whatsup_social);
        ImageView more = (ImageView) findViewById(R.id.more_social);
        ImageView textSize = (ImageView) findViewById(R.id.text_size);

        facebook.setOnClickListener(onClickListener);
        google.setOnClickListener(onClickListener);
        twiiter.setOnClickListener(onClickListener);
        whatsup.setOnClickListener(onClickListener);
        more.setOnClickListener(onClickListener);
        textSize.setOnClickListener(onClickListener);

    }

    @Override
    public void showResult(final DetailPageResult result) {
        mProgressBar.setVisibility(View.GONE);
        if(result==null){
            return;
        }

        mTitle=result.getItemDetail().getPTtl()+"   "+result.getItemDetail().getPWww();
        mItemComment=result.getItemDetail().getItemComment();

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        List<ItemComment> itemComments = result.getItemDetail().getItemComment();
        CommentList adapter=new CommentList(this,itemComments);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        mWebsiteLink=result.getItemDetail().getPWww();
        ((TextView)findViewById(R.id.title)).setText(result.getItemDetail().getPTtl());
        ((TextView)findViewById(R.id.date)).setText(result.getItemDetail().getPDt());

        mJTv=(JustifiedTextView) findViewById(R.id.desc);

        mJTv.setText(Html.fromHtml(result.getItemDetail().getPDetail()).toString());
        mJTv.setLineSpacing(8);
        mJTv.setAlignment(Paint.Align.LEFT);
        mJTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);




        //((JustifiedTextView)findViewById(R.id.desc)).setText(Html.fromHtml(result.getItemDetail().getPDetail()).toString());
        final TextView likeCount= (TextView)findViewById(R.id.textView_like_count);

         count=Integer.parseInt(result.getItemDetail().getPLikes());


        likeCount.setText("" +count);
        likeCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLike){
                    likeCount.setText("" +  --count);
                    Drawable img = getContext().getResources().getDrawable( R.drawable.likeblank );
                    img.setBounds( 0, 0, 70, 70 );
                    likeCount.setCompoundDrawables( img, null, null, null );
                    isLike=false;
                }else{
                    likeCount.setText("" + ++count);
                    Drawable img = getContext().getResources().getDrawable( R.drawable.likefilled );
                    img.setBounds( 0, 0, 70, 70 );
                    likeCount.setCompoundDrawables( img, null, null, null );
                    isLike=true;
                }

            }
        });

        final LikeView likeView = (LikeView) findViewById(R.id.like_view);

        likeView.setLikeViewStyle(LikeView.Style.STANDARD);


        final ImageView bookmark=(ImageView)findViewById(R.id.bookmark);

        final AppPref app=new AppPref(this);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mBookMarkFlag==0){
                    bookmark.setImageResource(R.drawable.bookmark_filled);
                    homePresenter.addToFavourite(app.getAccessToken(NewsDetailActivity.this),result.getItemDetail().getPId());
                    mBookMarkFlag=1;
                }else{

                    bookmark.setImageResource(R.drawable.bookmarkblank);
                    homePresenter.removeFavourite(app.getAccessToken(NewsDetailActivity.this),result.getItemDetail().getPId());
                    mBookMarkFlag=0;
                }
            }
        });


        ImageView playVideo=(ImageView)findViewById(R.id.youTubeIcon);
        if(TextUtils.isEmpty(result.getItemDetail().getPVurl())){
            playVideo.setVisibility(View.INVISIBLE);
        }else{
            playVideo.setVisibility(View.VISIBLE);
        }
        TextView  openWebsiteLink=(TextView)findViewById(R.id.textView_read_website);

        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Navigation.LaunchYouTubeVideo(NewsDetailActivity.this,result.getItemDetail().getPVurl(),result.getItemDetail().getPTtl());
            }
        });
        openWebsiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.LaunchYouTubeVideo(NewsDetailActivity.this,result.getItemDetail().getPWww(),result.getItemDetail().getPTtl());
            }
        });



        mDetailPageIcon=(ImageView)findViewById(R.id.icon);

        Glide.with(this)
                .load(result.getItemDetail().getPIcon())
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        // you can do something with loaded bitmap here

                        // ....
                        mDetailPageIcon.setImageBitmap(resource);
                    }
                });




        displayRelatedNews(result.getItemDetail().getItemSimiler());

    }

    private void displayRelatedNews(List<ItemSimiler> itemSimiler) {
        if(itemSimiler==null && itemSimiler.size()==0){
            return;
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view_list);
        recyclerView.setLayoutManager(layoutManager);
        final NewsRelativeList adapter=new NewsRelativeList(this,itemSimiler);
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ItemSimiler cPost=adapter.getItem(position);
                finish();
                Navigation.launchDescription(NewsDetailActivity.this,bundle.getString(Navigation.CAT_ID),cPost.getPId(),bundle.getString(Navigation.CAT_Name));
            }
        });





    }

    @Override
    public void showMessage(String message) {

        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this,"Something went wrong .Please try later.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressIndicator() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Navigation.LaunchAccount(this);
        return super.onOptionsItemSelected(item);

    }
    int textSize=0;

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){
                case R.id.facebook_social:
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(mWebsiteLink))
                            .build();
                    ShareDialog.show(NewsDetailActivity.this,content);
                    break;
                case R.id.twiiter_social:
                    String url = "http://www.twitter.com/intent/tweet?text='"+mTitle+"'";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    break;
                case R.id.google_social:
                    Uri uri=Apputil.getLocalBitmapUri(NewsDetailActivity.this,mDetailPageIcon);
                    Intent yahoo = new Intent(Intent.ACTION_SEND);
                    yahoo.setType("message/rfc822");
                    yahoo.putExtra(Intent.EXTRA_STREAM, uri);
                    yahoo.putExtra(Intent.EXTRA_SUBJECT, "");
                    yahoo.putExtra(Intent.EXTRA_TEXT   , mTitle);
                    try {
                        startActivity(Intent.createChooser(yahoo, "Send ..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(NewsDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.whatsup_social:
                    PackageManager pm=getPackageManager();
                    try {

                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                        waIntent.setType("image/jpeg");
                        String text = mTitle;

                        PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                        //Check if package exists or not. If not then code
                        //in catch block will be called
                        waIntent.setPackage("com.whatsapp");
                        Uri a=Apputil.getLocalBitmapUri(NewsDetailActivity.this,mDetailPageIcon);
                        waIntent.putExtra(Intent.EXTRA_STREAM, a);
                        waIntent.putExtra(Intent.EXTRA_TEXT, text);
                        startActivity(Intent.createChooser(waIntent, "Share with"));

                    } catch (PackageManager.NameNotFoundException e) {
                        Toast.makeText(NewsDetailActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case R.id.more_social:

                    Uri au=Apputil.getLocalBitmapUri(NewsDetailActivity.this,mDetailPageIcon);
                    if(au==null){
                        Apputil.share(NewsDetailActivity.this, mTitle);
                    }else{
                        Apputil.share(NewsDetailActivity.this,mTitle,au);
                    }

                    break;
                case R.id.text_size:

                    mTextSize=0;
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewsDetailActivity.this);

                    dialogBuilder
                            .setMessage(R.string.size_of_text)
                            .setCancelable(true)
                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    if(mTextSize>14){
                                        mJTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTextSize);
                                    }else {
                                        mJTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                                    }

                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });



                    LayoutInflater inflater = NewsDetailActivity.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.fragment_dialog, null);
                    dialogBuilder.setView(dialogView);
                    SeekBar progressBar=(SeekBar)dialogView.findViewById(R.id.progress_bar_size);
                    progressBar.setProgress(50);
                    progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                            if(i<25){
                                Logger.d("TEXT SIZEE "+mTextSize);
                                mTextSize=i;
                            }else{
                                mTextSize=30;
                            }


                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {


                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });



                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    break;
            }
        }
    };


}
