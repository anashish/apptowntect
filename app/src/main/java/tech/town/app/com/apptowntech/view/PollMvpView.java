package tech.town.app.com.apptowntech.view;

import tech.town.app.com.apptowntech.model.body.Response;
import tech.town.app.com.apptowntech.model.poll.PollResult;

/**
 * Created by ${="Ashish"} on 17/6/16.
 */
public interface PollMvpView extends MvpView {

    void showResult(PollResult pollResult);
    void showMessage(String message);
    void showProgressIndicator();
    void submitPollResult(Response result);
}
