package tech.town.app.com.apptowntech.model.bookmark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.town.app.com.apptowntech.model.Return;

/**
 * Created by ${="Ashish"} on 17/9/16.
 */
public class FavouriteResult {
    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("FavResult")
    @Expose
    private List<Favourite> favResult = new ArrayList<Favourite>();

    /**
     *
     * @return
     * The _return
     */
    public Return getReturn() {
        return _return;
    }

    /**
     *
     * @param _return
     * The return
     */
    public void setReturn(Return _return) {
        this._return = _return;
    }

    /**
     *
     * @return
     * The favResult
     */
    public List<Favourite> getFavResult() {
        return favResult;
    }

    /**
     *
     * @param favResult
     * The FavResult
     */
    public void setFavResult(List<Favourite> favResult) {
        this.favResult = favResult;
    }
}
