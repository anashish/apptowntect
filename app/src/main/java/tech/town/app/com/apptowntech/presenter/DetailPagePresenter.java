package tech.town.app.com.apptowntech.presenter;


import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tech.town.app.com.apptowntech.AppTown;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.itemdetail.DetailPageResult;
import tech.town.app.com.apptowntech.service.ApiService;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.DetailPageMvpView;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class DetailPagePresenter implements Presenter<DetailPageMvpView> {
    public static String TAG = "MainPresenter";

    private DetailPageMvpView homeMvpView;
    private Subscription subscription;
    private List<DetailPageResult> homeResult;

    @Override
    public void attachView(DetailPageMvpView view) {
        this.homeMvpView = view;
    }

    @Override
    public void detachView() {
        this.homeMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadDescription(String itemId,String catId) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.getDetailPage(itemId,catId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<DetailPageResult>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "Home Result" + homeResult);

                        try {

                            if (homeResult != null) {
                                homeMvpView.showResult(homeResult.get(1));
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
                    public void onNext(List<DetailPageResult> homeResultlist) {
                        DetailPagePresenter.this.homeResult = homeResultlist;
                    }
                });

    }

    public void addToFavourite(String id,String pid) {
       // homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.addFavourite(id,pid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Response>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onNext(List<Response> homeResultlist) {
                    }
                });
    }

    public void removeFavourite(String id,String pid) {
        //homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.removeFavourite(id,pid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Response>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable error) {

                    }

                    @Override
                    public void onNext(List<Response> homeResultlist) {
                    }
                });
    }
}
