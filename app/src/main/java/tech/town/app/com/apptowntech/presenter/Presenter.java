package tech.town.app.com.apptowntech.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
