package tech.town.app.com.apptowntech.model.bookmark;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ${="Ashish"} on 17/9/16.
 */
public class Favourite {
    @SerializedName("fv_id")
    @Expose
    private String fvId;
    @SerializedName("fv_name")
    @Expose
    private String fvName;
    @SerializedName("fv_icon")
    @Expose
    private String fvIcon;

    /**
     *
     * @return
     * The fvId
     */
    public String getFvId() {
        return fvId;
    }

    /**
     *
     * @param fvId
     * The fv_id
     */
    public void setFvId(String fvId) {
        this.fvId = fvId;
    }

    /**
     *
     * @return
     * The fvName
     */
    public String getFvName() {
        return fvName;
    }

    /**
     *
     * @param fvName
     * The fv_name
     */
    public void setFvName(String fvName) {
        this.fvName = fvName;
    }

    /**
     *
     * @return
     * The fvIcon
     */
    public String getFvIcon() {
        return fvIcon;
    }

    /**
     *
     * @param fvIcon
     * The fv_icon
     */
    public void setFvIcon(String fvIcon) {
        this.fvIcon = fvIcon;
    }

}

