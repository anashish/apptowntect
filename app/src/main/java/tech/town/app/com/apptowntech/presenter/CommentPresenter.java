package tech.town.app.com.apptowntech.presenter;


import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tech.town.app.com.apptowntech.AppTown;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.login.Login;
import tech.town.app.com.apptowntech.service.ApiService;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.CommentMvpView;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class CommentPresenter implements Presenter<CommentMvpView> {
    public static String TAG = "MainPresenter";

    private CommentMvpView homeMvpView;
    private Subscription subscription;
    private List<Response> homeResult;

    @Override
    public void attachView(CommentMvpView view) {
        this.homeMvpView = view;
    }

    @Override
    public void detachView() {
        this.homeMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void sendComment(String id,String comment,String postId) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.postComment(id,comment,postId)
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
                        CommentPresenter.this.homeResult = homeResultlist;
                    }
                });

    }
}
