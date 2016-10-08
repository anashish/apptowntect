package tech.town.app.com.apptowntech.view;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
