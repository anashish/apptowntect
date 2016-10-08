package tech.town.app.com.apptowntech.view;

import java.util.List;

import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.bookmark.Favourite;

/**
 * Created by ${="Ashish"} on 17/6/16.
 */
public interface FavouriteMvpView extends MvpView {

    void Result(Response result);
    void showMessage(String message);
    void showProgressIndicator();
    void getBookMarkList(List<Favourite> favouriteList);
}
