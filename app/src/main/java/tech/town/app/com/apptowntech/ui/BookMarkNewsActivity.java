package tech.town.app.com.apptowntech.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import java.util.List;

import tech.town.app.com.apptowntech.R;
import tech.town.app.com.apptowntech.adapter.BookMarkList;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.bookmark.Favourite;
import tech.town.app.com.apptowntech.presenter.FavouritePresenter;
import tech.town.app.com.apptowntech.utils.AppPref;
import tech.town.app.com.apptowntech.utils.Apputil;
import tech.town.app.com.apptowntech.view.FavouriteMvpView;

public class BookMarkNewsActivity extends BaseActivity implements FavouriteMvpView,BookMarkList.OnRemoveFavClickListener {

    private RecyclerView recyclerView;
    private BookMarkList markListAdapter;
    private ProgressDialog mProgressDialog;
    private FavouritePresenter favouritePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.likes_news);

        favouritePresenter=new FavouritePresenter();
        favouritePresenter.attachView(this);
        if(TextUtils.isEmpty(new AppPref(this).getAccessToken(this))){
            Apputil.showToast(this,"Please logged in");
            finish();
            return;
        }
        favouritePresenter.getBookMarkList(new AppPref(this).getAccessToken(this));
        setToolBarBackButton(toolbar);

    }

    @Override
    public void Result(Response result) {
        mProgressDialog.dismiss();
        favouritePresenter.getBookMarkList(new AppPref(this).getAccessToken(this));

    }

    @Override
    public void showMessage(String message) {
        mProgressDialog.dismiss();
        Apputil.showToast(this,"Something went wrong.Please try later");
    }

    @Override
    public void showProgressIndicator() {
        mProgressDialog = Apputil.createProgressDialog(BookMarkNewsActivity.this);
        mProgressDialog.show();
    }

    @Override
    public void getBookMarkList(List<Favourite> favouriteList) {
        mProgressDialog.dismiss();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        markListAdapter = new BookMarkList(this,favouriteList,this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(markListAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void RemoveFavourite(String pid, int i) {

        favouritePresenter.removeFavourite(new AppPref(this).getAccessToken(this),pid);
    }
}
