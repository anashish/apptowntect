package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.HomeCategoryList;
import tech.town.app.com.apptowntech.model.CPost;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.bookmark.Favourite;
import tech.town.app.com.apptowntech.presenter.FavouritePresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.utils.ItemClickSupport;
import tech.town.app.com.apptowntech.utils.Navigation;
import tech.town.app.com.apptowntech.view.FavouriteMvpView;

public class CategoryListActivity extends BaseActivity implements HomeCategoryList.OnAddFavClickListener,FavouriteMvpView {


    private RecyclerView recyclerView;
    private  List<CPost> homeCategoryList;
    private HomeCategoryList mAdapter;
    private String mCatID;
    private String mCatName;
    private ProgressDialog mProgressDialog;
    private AppPref mAppPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBarBackButton(toolbar);



        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            Type type = new TypeToken< List< CPost >>() {}.getType();
            homeCategoryList = new Gson().fromJson(bundle.getString(Navigation.POST_ID), type);
            mCatID=bundle.getString(Navigation.CAT_ID);
            mCatName =bundle.getString(Navigation.CAT_Name);
        }
        initViews();
    }

    private void initViews(){
        mAppPref=new AppPref(this);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                CPost cPost=mAdapter.getItem(position);

                Navigation.launchDescription(CategoryListActivity.this,mCatID,cPost.getPId(),mCatName);
            }
        });
        mAdapter = new HomeCategoryList(this,homeCategoryList,this);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void addedFavourite(String pid, int postion) {

        FavouritePresenter favouritePresenter=new FavouritePresenter();
        favouritePresenter.attachView(this);
        if(TextUtils.isEmpty(mAppPref.getAccessToken(this))) {
            Apputil.showToast(this,"Please login");
            return;
        }
        if(homeCategoryList.get(postion).isAddedToFavourite()) {
            homeCategoryList.get(postion).setAddedToFavourite(false);
            favouritePresenter.removeFavourite(mAppPref.getAccessToken(this),pid);
        }else{
            homeCategoryList.get(postion).setAddedToFavourite(true);
            favouritePresenter.addToFavourite(mAppPref.getAccessToken(this),pid,mCatID);
        }
        mAdapter.notifydatachange(homeCategoryList);

    }

    @Override
    public void Result(Response result) {

        if(result.getReturn().getMessage().equalsIgnoreCase("success")){
            mProgressDialog.dismiss();
            Apputil.showToast(this,result.getMsg().getUserMessage());

        }
    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();
        Apputil.showToast(this,"Something went wrong.Please try later");
    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog = Apputil.createProgressDialog(this);
        mProgressDialog.show();
    }

    @Override
    public void getBookMarkList(List<Favourite> favouriteList) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
