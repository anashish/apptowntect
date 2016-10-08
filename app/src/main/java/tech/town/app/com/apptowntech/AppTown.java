package tech.town.app.com.apptowntech;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import tech.town.app.com.apptowntech.service.ApiService;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class AppTown extends Application {
    private ApiService apiService;
    private Scheduler defaultSubscribeScheduler;

    public static AppTown get(Context context) {

        return (AppTown) context.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }


    public ApiService getApiService() {

        if (apiService == null) {
            apiService = ApiService.Factory.create();
        }
        return apiService;
    }

    //For setting mocks during testing
    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

}
