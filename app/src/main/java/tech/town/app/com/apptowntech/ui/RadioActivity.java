package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import tech.town.app.com.apptowntech.R;

public class RadioActivity extends BaseActivity {
    ProgressDialog pDialog;
    private MediaController mc;
    private VideoView myVideoView;

    private boolean isPlaying=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();
        toolbar.setTitle(bundle.getString("title"));
        setToolBarBackButton(toolbar);


        myVideoView = (VideoView)this.findViewById(R.id.videoview);
        mc = new MediaController(this);
        myVideoView.setMediaController(mc);

        pDialog=new ProgressDialog(RadioActivity.this);
        pDialog.setTitle("Wait");
        pDialog.setMessage("please wait");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
/*
        ImageView prev=(ImageView)findViewById(R.id.prv);
        ImageView next=(ImageView)findViewById(R.id.nxt);
        ImageView play=(ImageView)findViewById(R.id.play);
        prev.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
        play.setOnClickListener(onClickListener);*/


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

                        mediaPlayer.start();
                        mc.show();
                    }

                });
            }
        });
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
               case R.id.prv:

                break;
                case R.id.nxt:
                    break;
                case R.id.play:
                    if(isPlaying){
                        myVideoView.start();
                        isPlaying=false;
                    }else{
                        myVideoView.stopPlayback();
                        isPlaying=true;
                    }

                    break;

            }
        }
    };

}
