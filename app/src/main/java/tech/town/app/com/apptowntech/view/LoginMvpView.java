package tech.town.app.com.apptowntech.view;

import tech.town.app.com.apptowntech.model.login.Login;

/**
 * Created by ${="Ashish"} on 17/6/16.
 */
public interface LoginMvpView extends MvpView {

    void Result(Login result);
    void showMessage(String message);
    void showProgressIndicator();
}
