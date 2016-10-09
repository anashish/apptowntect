package tech.town.app.com.apptowntech.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.HomeViewPager;
import tech.town.app.com.apptowntech.fragment.CommonFragment;
import tech.town.app.com.apptowntech.fragment.HomeFragment;
import tech.town.app.com.apptowntech.model.CPost;
import tech.town.app.com.apptowntech.model.ExtraInfo;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.presenter.HomePresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.utils.Navigation;
import tech.town.app.com.apptowntech.view.HomeMvpView;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeMvpView,HomeFragment.OnHomeItemSelectedListener,CommonFragment.OnCategoryRefreshResult {


    private TabLayout mTabLayout;
    private TextView  mTextViewLiveOne;
    private TextView  mTextViewLiveTwo;
    private TextView mLiveTitleOne;
    private TextView mLiveTitleTwo;
    private ViewPager mViewPager;
    private Fragment mCurrentFragment;
    private HomeViewPager mAdapter;
    private List<HomeCategory> homeCategoryList;
    private NavigationView mNavigationView;
    private ProgressBar mProgressBar;
    private HomePresenter homePresenter;
    private String mVideoUrl;
    private String mAudioUrl;
    private AppPref appPref;
    private int mPosition;
    private List<HomeCategory> homeTempList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       /* TextView toolBarTitle=(TextView)findViewById(R.id.toolbar_title);*/
        appPref=new AppPref(this);
        setToolBar(toolbar);
        displayFloatingButton();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTextViewLiveOne=(TextView)findViewById(R.id.textLivePageOne);
        mTextViewLiveTwo=(TextView)findViewById(R.id.textLivePageTwo);
        mLiveTitleOne=(TextView)findViewById(R.id.textViewTitleOne);
        mLiveTitleTwo=(TextView)findViewById(R.id.textViewTitleTwo);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar2);
        mViewPager=(ViewPager)findViewById(R.id.pager) ;
        mTextViewLiveOne.setSelected(true);
        mTextViewLiveTwo.setSelected(true);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);



        mNavigationView.setNavigationItemSelectedListener(this);
        homePresenter=new HomePresenter();
        homePresenter.attachView(this);
        callHomeAPI();

        mAdapter= new HomeViewPager(getFragmentManager());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()!=0){
                    mCurrentFragment= mAdapter.getItem(tab.getPosition());
                    mPosition=tab.getPosition();
                    if(mCurrentFragment instanceof  CommonFragment){
                        CommonFragment commonFragment=(CommonFragment)mCurrentFragment;
                                commonFragment.
                                displayNewsCategory(homeCategoryList.get(tab.getPosition()).getCPost(),
                                homeCategoryList.get(tab.getPosition()).getCId(),homeCategoryList.get(tab.getPosition()).getCName());
                    }
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ImageView reload=(ImageView)findViewById(R.id.reload);
        reload.setOnClickListener(onClickListener);

    }

    private void callHomeAPI() {
        homePresenter.loadHome(appPref.getAccessToken(this),appPref.getVersionNumber(this));
    }

    private void displayFloatingButton() {

       final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        /* final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);*/
        final FloatingActionButton fabEvent = (FloatingActionButton) findViewById(R.id.fab_event);
        final FloatingActionButton fabphto= (FloatingActionButton) findViewById(R.id.fab_photo);
        final FloatingActionButton fabothers = (FloatingActionButton) findViewById(R.id.fab_others);
        fabEvent.setOnClickListener(onClickListener);
        fabphto.setOnClickListener(onClickListener);
        fabothers.setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.fab_event:
                    if(TextUtils.isEmpty(mAudioUrl)){
                        return;
                    }
                    Navigation.launchRadio(HomeActivity.this,mAudioUrl,getString(R.string.radio));
                    break;
                case R.id.fab_photo:
                    if(TextUtils.isEmpty(mVideoUrl)){
                        return;
                    }
                    Navigation.launchVideo(HomeActivity.this,mVideoUrl,getString(R.string.video_tv));
                    break;
                case R.id.fab_others:

                    Navigation.launchReport(HomeActivity.this);
                    break;
                case R.id.reload:
                    appPref.saveVersionHomeAPi("");
                    appPref.saveHomeCategoryJson("");
                    homePresenter.loadHome(appPref.getAccessToken(HomeActivity.this),"0");
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

         switch (id){
             case R.id.action_poll:
                 Navigation.LaunchPoll(this);
                 break;
             case R.id.action_refresh:
                 appPref.saveVersionHomeAPi("test");
                 appPref.saveHomeCategoryJson("");
                 homePresenter.loadHome(appPref.getAccessToken(this),"0");
                 break;
             case R.id.action_account:
                 Navigation.LaunchAccount(this);
                 break;
         }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String itemName=item.getTitle().toString().trim();

        if(itemName.equals(getString(R.string.home))) {
            mViewPager.setCurrentItem(0);
        }if(itemName.equals(getString(R.string.video_tv))) {
            //Navigation.LaunchYouTubeVideo(this,mVideoUrl,getString(R.string.live));
            Navigation.launchVideo(this,mVideoUrl,getString(R.string.video_tv));
        } if(itemName.equals(getString(R.string.radio))) {
            Navigation.launchRadio(this,mAudioUrl,getString(R.string.radio));
        } if(itemName.equals(getString(R.string.facebook))) {
            Navigation.LaunchYouTubeVideo(this,"https://m.facebook.com/livetodayonline/#!/livetodayonline/",getString(R.string.facebook));
        }if(itemName.equals(getString(R.string.google))) {
            Navigation.LaunchYouTubeVideo(this,"https://plus.google.com/+LivetodayOnline",getString(R.string.google));
        }if(itemName.equals(getString(R.string.twitter))) {
            Navigation.LaunchYouTubeVideo(this,"https://twitter.com/search?q=livetodayonline",getString(R.string.twitter));
        }if(id!=0){
            mViewPager.setCurrentItem(id);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void displayUpperLiveSection(final List<HomeCategory> homeResult) {
        final List<CPost> cPostOne= homeResult.get(0).getCPost();
        final List<CPost> cPostTwo= homeResult.get(1).getCPost();

        mLiveTitleOne.setText(homeResult.get(0).getCName()+" :-");
        mLiveTitleTwo.setText(homeResult.get(1).getCName()+" :-");


        StringBuilder sb1=new StringBuilder();
        StringBuilder sb2=new StringBuilder();

        for (CPost postOne: cPostOne) {
            sb1.append(postOne.getPTtl()+"  :  ");
        }
        for (CPost postTwo: cPostTwo) {
            sb2.append(postTwo.getPTtl()+"  :  ");
        }
        mTextViewLiveTwo.setText(sb2.toString());
        mTextViewLiveOne.setText(sb1.toString());
        mTextViewLiveTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();

                /**
                 *Convert into json and save to app pref
                 */
                String json = gson.toJson(cPostTwo);

                Navigation.launchCategoryList(HomeActivity.this,homeResult.get(1).getCId(),json,
                        mTextViewLiveTwo.getText().toString().replace(" :-",""));

            }
        });
        mTextViewLiveOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();

                /**
                 *Convert into json and save to app pref
                 */
                String json = gson.toJson(cPostOne);

                Navigation.launchDescription(HomeActivity.this,homeResult.get(0).getCId(),cPostOne.get(0).getPId(),
                        mTextViewLiveOne.getText().toString().replace(" :-",""));



            }
        });



    }

    private void displayTabInfo(List<HomeCategory> homeCategory) {


        mNavigationView.setItemIconTintList(null);

        Menu topChannelMenu = mNavigationView.getMenu();
       // SubMenu topChannelMenu = m.addSubMenu(" ");
        topChannelMenu.add(0,0,1,R.string.home).setIcon(R.drawable.home);
        topChannelMenu.add(0,0,2,R.string.video_tv).setIcon(R.drawable.livetv);
        topChannelMenu.add(0,0,3,R.string.radio).setIcon(R.drawable.radio);
        topChannelMenu.add(0,0,4,"_________________________________________________");


        mAdapter.addFrag(new HomeFragment(), "Home");
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.home_tab));
        for(int i=0;i<homeCategory.size();i++){


            topChannelMenu.add(0,i+1,i+5,homeCategory.get(i).getCName()).setIcon(R.drawable.commoncategory);
            mTabLayout.addTab(mTabLayout.newTab().setText(homeCategory.get(i).getCName()));
            mAdapter.addFrag(new CommonFragment(),homeCategory.get(i).getCName());
        }

        topChannelMenu.add(0,0,homeCategory.size()+5,"_________________________________________________");
        topChannelMenu.add(0,0,homeCategory.size()+6,R.string.facebook).setIcon(R.drawable.facebook);
        topChannelMenu.add(0,0,homeCategory.size()+7,R.string.google).setIcon(R.drawable.gplus);
        topChannelMenu.add(0,0,homeCategory.size()+8, R.string.twitter).setIcon(R.drawable.twitter);



        mCurrentFragment= mAdapter.getItem(mViewPager.getCurrentItem());
        if(mCurrentFragment instanceof  HomeFragment){
            mViewPager.setAdapter(mAdapter);
            HomeFragment homeFragment=(HomeFragment) mCurrentFragment;
            homeFragment.displayHomeCategory(homeTempList);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showHomeResult(List<HomeCategory> homeResultInfo, ExtraInfo extraInfo) {

        mProgressBar.setVisibility(View.INVISIBLE);

        mVideoUrl=extraInfo.getUrlLivetv();
        mAudioUrl=extraInfo.getUrlRadio();

        List<HomeCategory> tempList;

         Gson gson = new Gson();
         String savedVersion=appPref.getVersionNumber(this);
        if(savedVersion.equalsIgnoreCase(extraInfo.getHomeVersion())){
            Type type = new TypeToken< List< HomeCategory >>() {}.getType();
            tempList= new Gson().fromJson(appPref.getHomeCategoryJson(this), type);
        }else {
            tempList=homeResultInfo;
            String json = gson.toJson(homeResultInfo);
            appPref.saveHomeCategoryJson(json);
            appPref.saveVersionHomeAPi(extraInfo.getHomeVersion());

        }

        try {
            displayUpperLiveSection(tempList);
            if(homeCategoryList!=null){
                homeCategoryList.clear();
                mNavigationView.getMenu().clear();
            }
            homeCategoryList=tempList;
            ;
            List<HomeCategory> one = new ArrayList<>();
            for(int i=3;i<homeCategoryList.size();i++){
                one.add(homeCategoryList.get(i));
            }


            for(int i=2;i<homeCategoryList.size();i++){
                homeTempList.add(homeCategoryList.get(i));
            }
            homeCategoryList.clear();
            homeCategoryList.addAll(homeTempList);
            displayTabInfo(one);
        }catch (Exception e){
            Logger.d(e.getMessage());
        }


    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(this,"Something went wrong .Please try later.",Toast.LENGTH_LONG).show();
        mProgressBar.setVisibility(View.INVISIBLE);
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
    public void onHomeItemPicked(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CustomNewsActivity.isNewPrefUpdate){
            CustomNewsActivity.isNewPrefUpdate=false;
            appPref.saveVersionHomeAPi("");
            appPref.saveHomeCategoryJson("");
            appPref.saveOriention(false);
            finish();
            Intent in=new Intent(this,HomeActivity.class);
            startActivity(in);
        }

    }

    @Override
    public void categoryRefresh(List<HomeCategory> list) {

        if(list==null || list.size()==0 || list.get(0).getCPost()==null ||list.get(0).getCPost().size()==0){
            return;
        }
        if(mCurrentFragment instanceof  CommonFragment) {
            CommonFragment commonFragment = (CommonFragment) mCurrentFragment;

            homeCategoryList.get(mPosition).getCPost().addAll(list.get(0).getCPost());
            commonFragment.notifydataChange(homeCategoryList.get(mPosition).getCPost());

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }
}
