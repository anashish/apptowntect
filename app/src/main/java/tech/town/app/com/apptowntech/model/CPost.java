
package tech.town.app.com.apptowntech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CPost {

    @SerializedName("p_id")
    @Expose
    private String pId;
    @SerializedName("p_ttl")
    @Expose
    private String pTtl;
    @SerializedName("p_dt")
    @Expose
    private String pDt;
    @SerializedName("p_icon")
    @Expose
    private String pIcon;

    private boolean isAddedToFavourite;


    public boolean isAddedToFavourite() {
        return isAddedToFavourite;
    }

    public void setAddedToFavourite(boolean addedToFavourite) {
        isAddedToFavourite = addedToFavourite;
    }

    /**
     * 
     * @return
     *     The pId
     */
    public String getPId() {
        return pId;
    }

    /**
     * 
     * @param pId
     *     The p_id
     */
    public void setPId(String pId) {
        this.pId = pId;
    }

    /**
     * 
     * @return
     *     The pTtl
     */
    public String getPTtl() {
        return pTtl;
    }

    /**
     * 
     * @param pTtl
     *     The p_ttl
     */
    public void setPTtl(String pTtl) {
        this.pTtl = pTtl;
    }

    /**
     * 
     * @return
     *     The pDt
     */
    public String getPDt() {
        return pDt;
    }

    /**
     * 
     * @param pDt
     *     The p_dt
     */
    public void setPDt(String pDt) {
        this.pDt = pDt;
    }

    /**
     * 
     * @return
     *     The pIcon
     */
    public String getPIcon() {
        return pIcon;
    }

    /**
     * 
     * @param pIcon
     *     The p_icon
     */
    public void setPIcon(String pIcon) {
        this.pIcon = pIcon;
    }

}
