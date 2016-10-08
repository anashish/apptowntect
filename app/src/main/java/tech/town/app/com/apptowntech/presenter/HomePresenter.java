package tech.town.app.com.apptowntech.presenter;


import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import tech.town.app.com.apptowntech.AppTown;
import tech.town.app.com.apptowntech.model.ExtraInfo;
import tech.town.app.com.apptowntech.model.HomeCategory;
import tech.town.app.com.apptowntech.model.HomeResultInfo;
import tech.town.app.com.apptowntech.service.ApiService;
import tech.town.app.com.apptowntech.utils.Logger;
import tech.town.app.com.apptowntech.view.HomeMvpView;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class HomePresenter implements Presenter<HomeMvpView> {
    public static String TAG = "MainPresenter";

    private HomeMvpView homeMvpView;
    private Subscription subscription;
    private List<HomeResultInfo> homeResult;

    @Override
    public void attachView(HomeMvpView view) {
        this.homeMvpView = view;
    }

    @Override
    public void detachView() {
        this.homeMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadHome(String userId,String versionInfo) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.getHomeJson(userId,versionInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<HomeResultInfo>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "Home Result" + homeResult);

                        ExtraInfo extraInfo =new ExtraInfo();
                        List<HomeCategory> homeCategoryList = null;

                            if (homeResult != null) {

                                for(int i=0;i<homeResult.size();i++){
                                    if(homeResult.get(i).getExtraInfo() instanceof ExtraInfo){
                                        extraInfo =homeResult.get(i).getExtraInfo();


                                    }
                                    try {
                                        if(homeResult.get(i).getHomeCategory().get(0) instanceof HomeCategory){
                                            homeCategoryList=homeResult.get(i).getHomeCategory();

                                        }

                                    }catch (Exception e){

                                    }
                                }
                                homeMvpView.showHomeResult(homeCategoryList,extraInfo);
                            }

                    }

                    @Override
                    public void onError(Throwable error) {


                        Logger.d(error.getMessage());
                        homeMvpView.showMessage("Something went wrong please try later");
                    }

                    @Override
                    public void onNext(List<HomeResultInfo> homeResultlist) {
                        HomePresenter.this.homeResult = homeResultlist;
                    }
                });

    }

    public void loadCategory(String catId,String direction,String postId) {
        homeMvpView.showProgressIndicator();
        if (subscription != null) subscription.unsubscribe();
        AppTown application = AppTown.get(homeMvpView.getContext());
        ApiService apiService = application.getApiService();
        subscription = apiService.getCategoryList(catId,direction,postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Subscriber<List<HomeResultInfo>>() {
                    @Override
                    public void onCompleted() {
                        Logger.d(TAG, "Home Result" + homeResult);

                        ExtraInfo extraInfo =new ExtraInfo();
                        List<HomeCategory> homeCategoryList = null;

                        if (homeResult != null) {

                            for(int i=0;i<homeResult.size();i++){
                                if(homeResult.get(i).getExtraInfo() instanceof ExtraInfo){
                                    extraInfo =homeResult.get(i).getExtraInfo();


                                }
                                try {
                                    if(homeResult.get(i).getHomeCategory().get(0) instanceof HomeCategory){
                                        homeCategoryList=homeResult.get(i).getHomeCategory();

                                    }

                                }catch (Exception e){

                                }
                            }
                            homeMvpView.showHomeResult(homeCategoryList,extraInfo);
                        }

                    }

                    @Override
                    public void onError(Throwable error) {


                        Logger.d(error.getMessage());
                        homeMvpView.showMessage("Something went wrong please try later");
                    }

                    @Override
                    public void onNext(List<HomeResultInfo> homeResultlist) {
                        HomePresenter.this.homeResult = homeResultlist;
                    }
                });

    }
}
