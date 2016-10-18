package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.NewsRelativeList;
import tech.town.app.com.apptowntech.model.CPost;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.model.itemdetail.ItemSimiler;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.ItemClickSupport;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.utils.Navigation;

public class VideoActivity extends BaseActivity {

    ProgressDialog pDialog;
    private ImageView exit;
    private Toolbar toolbar;

    private boolean isTapOnVideo=false;
    private AppPref mAppPref;
    private List<HomeCategory> mCategoryList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit=(ImageView)findViewById(R.id.exit);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_video);
        mAppPref=new AppPref(this);

        if(mAppPref.getBooleanOri(this)){
            toolbar.setVisibility(View.GONE);
            exit.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            toolbar.setVisibility(View.VISIBLE);
            exit.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        Bundle bundle = getIntent().getExtras();
        toolbar.setTitle(bundle.getString("title"));
        setToolBarBackButton(toolbar);



        final VideoView myVideoView = (VideoView)this.findViewById(R.id.videoview);
        final MediaController mc = new MediaController(this);
        myVideoView.setMediaController(mc);

        pDialog=new ProgressDialog(VideoActivity.this);
        pDialog.setTitle("Wait");
        pDialog.setMessage("please wait");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();


        final String  urlStream = bundle.getString("url");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myVideoView.setVideoURI(Uri.parse(urlStream));

            }
        });

         myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


             @Override
             public void onPrepared(MediaPlayer mediaPlayer) {

                 mediaPlayer.start();
                  pDialog.dismiss();
                  mc.show();
                 mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                     @Override
                     public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

                         Logger.d("onPause");
                         mediaPlayer.start();
                         mc.show();
                     }

                 });
             }
         });

        myVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(mAppPref.getBooleanOri(VideoActivity.this)){
                    if(isTapOnVideo){
                        recyclerView.setVisibility(View.INVISIBLE);
                        isTapOnVideo=false;
                    }else{

                        recyclerView.setVisibility(View.VISIBLE);
                        isTapOnVideo=true;
                    }
                }

                return false;
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mAppPref.saveOriention(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });




        String json=mAppPref.getHomeCategoryJson(this);
        Type type = new TypeToken< List< HomeCategory >>() {}.getType();
        mCategoryList = new Gson().fromJson(json, type);

        for(int i=0;i<mCategoryList.size();i++){

            if(mCategoryList.get(i).getCName().equals(getString(R.string.video_extra))) {


                List<CPost> cPosts=mCategoryList.get(i).getCPost();
                displayRelatedNews(cPosts);
            }

        }

    }

    @Override
    protected void onPause() {
        Logger.d("onPause");
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_drawer, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_in:
                mAppPref.saveOriention(true);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void displayRelatedNews(final List<CPost> itemSimiler) {
        if(itemSimiler==null && itemSimiler.size()==0){
            return;
        }

        List<ItemSimiler> item=new ArrayList<>();;
     for(int i=0;i<itemSimiler.size();i++){

         ItemSimiler similer=new ItemSimiler();
         similer.setPTtl(itemSimiler.get(i).getPTtl());
         similer.setPDt(itemSimiler.get(i).getPDt());
         similer.setPIcon(itemSimiler.get(i).getPIcon());
         similer.setPId(itemSimiler.get(i).getPId());
         item.add(similer);
     }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        final NewsRelativeList adapter=new NewsRelativeList(this,item);
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Navigation.LaunchYouTubeVideo(VideoActivity.this,itemSimiler.get(position).getWebsite(),getString(R.string.video_tv));

            }
        });
    }
}
