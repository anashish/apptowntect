package tech.town.app.com.apptowntech.presenter;


import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tech.town.app.com.apptowntech.AppTown;
import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.login.Login;
import tech.town.app.com.apptowntech.model.poll.PollResult;
import tech.town.app.com.apptowntech.service.ApiService;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.PollMvpView;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class PollPresenter implements Presenter<PollMvpView> {
    public static String TAG = "MainPresenter";

    private PollMvpView homeMvpView;
    private Subscription subscription;
    private List<PollResult> homeResult;
    private List<Response> mPollSubmitResult;

    @Override
    public void attachView(PollMvpView view) {
        this.homeMvpView = view;
    }

    @Override
    public void detachView() {
        this.homeMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadPollResult() {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.getPollList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<PollResult>>() {
                    @Override
                    public void onCompleted() {
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
                    public void onNext(List<PollResult> homeResultlist) {
                        PollPresenter.this.homeResult = homeResultlist;
                    }
                });

    }

    public void submitPoll(String userId,String pollId,String tagId) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.submitPoll(userId,pollId,tagId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<Response>>() {
                    @Override
                    public void onCompleted() {
                        try {

                            if (homeResult != null) {
                                homeMvpView.submitPollResult(mPollSubmitResult.get(0));
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
                        PollPresenter.this.mPollSubmitResult = homeResultlist;
                    }
                });

    }
}
