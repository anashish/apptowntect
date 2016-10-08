package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Logger;

public class VideoActivity extends BaseActivity {

    ProgressDialog pDialog;
    private ImageView exit;
    private Toolbar toolbar;


    private AppPref mAppPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit=(ImageView)findViewById(R.id.exit);

        mAppPref=new AppPref(this);

        if(mAppPref.getBooleanOri(this)){
            toolbar.setVisibility(View.GONE);
            exit.setVisibility(View.VISIBLE);
        }else {
            toolbar.setVisibility(View.VISIBLE);
            exit.setVisibility(View.GONE);
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

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mAppPref.saveOriention(false);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_in:
                mAppPref.saveOriention(true);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
