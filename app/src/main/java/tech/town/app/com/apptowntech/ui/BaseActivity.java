package tech.town.app.com.apptowntech.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import tech.town.app.com.apptowntech.R;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class BaseActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    //private TextView mToolBarTitle;
    private ImageView mToolBarIcon;


    public void setToolBar(Toolbar toolBar){

        mToolbar=toolBar;
        mToolbar.setTitle("");
       // mToolbar.setLogo(R.drawable.action_bar_drawable);
        setSupportActionBar(mToolbar);
       // getSupportActionBar().setIcon(R.drawable.sample);


    }

    public void setToolBarBackButton(Toolbar toolBarBackButton){

        toolBarBackButton.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBarBackButton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.white_arrow);

        toolBarBackButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
