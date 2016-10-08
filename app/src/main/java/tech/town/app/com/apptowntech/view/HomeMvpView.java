package tech.town.app.com.apptowntech.view;

import java.util.List;

import tech.town.app.com.apptowntech.model.ExtraInfo;
import tech.town.app.com.apptowntech.model.HomeCategory;

/**
 * Created by ${="Ashish"} on 17/6/16.
 */
public interface  HomeMvpView extends MvpView {

    void showHomeResult(List<HomeCategory> categoryList, ExtraInfo extraInfo);
    void showMessage(String message);
    void showProgressIndicator();
}
