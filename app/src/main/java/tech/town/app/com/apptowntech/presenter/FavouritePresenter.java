package tech.town.app.com.apptowntech.presenter;


import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tech.town.app.com.apptowntech.AppTown;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.bookmark.FavouriteResult;
import tech.town.app.com.apptowntech.service.ApiService;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.FavouriteMvpView;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class FavouritePresenter implements Presenter<FavouriteMvpView> {
    public static String TAG = "MainPresenter";

    private FavouriteMvpView homeMvpView;
    private Subscription subscription;
    private List<Response> homeResult;

    private List<FavouriteResult> mFavouriteList;

    @Override
    public void attachView(FavouriteMvpView view) {
        this.homeMvpView = view;
    }

    @Override
    public void detachView() {
        this.homeMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void addToFavourite(String id,String pid,String catId) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.addFavourite(id,pid,catId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Response>>() {
                    @Override
                    public void onCompleted() {
                        try {

                            if (homeResult != null) {
                                homeMvpView.Result(homeResult.get(0));
                            }
                        } catch (Exception e) {
                           // homeMvpView.showMessage("Something went wrong please try later");
                        }
                    }

                    @Override
                    public void onError(Throwable error) {

                        Logger.d("***********************OOO0");
                        Logger.d(error.getMessage());
                    }

                    @Override
                    public void onNext(List<Response> homeResultlist) {
                        FavouritePresenter.this.homeResult = homeResultlist;
                    }
                });
    }

    public void removeFavourite(String id,String pid) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.removeFavourite(id,pid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Response>>() {
                    @Override
                    public void onCompleted() {
                        try {

                            if (homeResult != null) {
                                homeMvpView.Result(homeResult.get(1));
                            }
                        } catch (Exception e) {
                            homeMvpView.showMessage("Something went wrong please try later");
                        }
                    }

                    @Override
                    public void onError(Throwable error) {


                        Logger.d(error.getMessage());
                        homeMvpView.showMessage("Something went wrong please try later");
                    }

                    @Override
                    public void onNext(List<Response> homeResultlist) {
                        FavouritePresenter.this.homeResult = homeResultlist;
                    }
                });
    }

    public void getBookMarkList(String id) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.getList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<FavouriteResult>>() {
                    @Override
                    public void onCompleted() {
                        try {

                            if (mFavouriteList != null) {
                                homeMvpView.getBookMarkList(mFavouriteList.get(1).getFavResult());
                            }
                        } catch (Exception e) {
                            Logger.d(" " +e.getMessage());
                            homeMvpView.showMessage("Something went wrong please try later");
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        homeMvpView.showMessage("Something went wrong please try later");
                    }

                    @Override
                    public void onNext(List<FavouriteResult> favouriteResults) {
                        FavouritePresenter.this.mFavouriteList = favouriteResults;
                    }
                });
    }
}
