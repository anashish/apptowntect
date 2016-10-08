package tech.town.app.com.apptowntech.view;

import tech.town.app.com.apptowntech.model.HomeResultInfo;
import tech.town.app.com.apptowntech.model.itemdetail.DetailPageResult;

/**
 * Created by ${="Ashish"} on 17/6/16.
 */
public interface DetailPageMvpView extends MvpView {

    void showResult(DetailPageResult homeResult);
    void showMessage(String message);
    void showProgressIndicator();
}
