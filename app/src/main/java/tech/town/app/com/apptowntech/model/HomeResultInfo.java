package tech.town.app.com.apptowntech.model;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by ${="Ashish"} on 9/9/16.
 */
public class HomeResultInfo {

    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("HomeCategory")
    @Expose
    private List<HomeCategory> homeCategory = new ArrayList<HomeCategory>();
    @SerializedName("extra_info")
    @Expose
    private ExtraInfo extraInfo;

    /**
     *
     * @return
     *     The _return
     */
    public Return getReturn() {
        return _return;
    }

    /**
     *
     * @param _return
     *     The return
     */
    public void setReturn(Return _return) {
        this._return = _return;
    }

    /**
     *
     * @return
     *     The homeCategory
     */
    public List<HomeCategory> getHomeCategory() {
        return homeCategory;
    }

    /**
     *
     * @param homeCategory
     *     The HomeCategory
     */
    public void setHomeCategory(List<HomeCategory> homeCategory) {
        this.homeCategory = homeCategory;
    }

    /**
     *
     * @return
     *     The extraInfo
     */
    public ExtraInfo getExtraInfo() {
        return extraInfo;
    }

    /**
     *
     * @param extraInfo
     *     The extra_info
     */
    public void setExtraInfo(ExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
    }
}
