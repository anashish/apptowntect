package tech.town.app.com.apptowntech.view;

import tech.town.app.com.apptowntech.model.body.Response;

/**
 * Created by ${="Ashish"} on 17/6/16.
 */
public interface CommentMvpView extends MvpView {

    void Result(Response result);
    void showMessage(String message);
    void showProgressIndicator();
}
