
package tech.town.app.com.apptowntech.model.itemdetail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemComment {

    @SerializedName("cmt_txt")
    @Expose
    private String cmtTxt;
    @SerializedName("cmt_icon")
    @Expose
    private String cmtIcon;
    @SerializedName("cmt_name")
    @Expose
    private String cmtName;

    /**
     * 
     * @return
     *     The cmtTxt
     */
    public String getCmtTxt() {
        return cmtTxt;
    }

    /**
     * 
     * @param cmtTxt
     *     The cmt_txt
     */
    public void setCmtTxt(String cmtTxt) {
        this.cmtTxt = cmtTxt;
    }

    /**
     * 
     * @return
     *     The cmtIcon
     */
    public String getCmtIcon() {
        return cmtIcon;
    }

    /**
     * 
     * @param cmtIcon
     *     The cmt_icon
     */
    public void setCmtIcon(String cmtIcon) {
        this.cmtIcon = cmtIcon;
    }

    /**
     * 
     * @return
     *     The cmtName
     */
    public String getCmtName() {
        return cmtName;
    }

    /**
     * 
     * @param cmtName
     *     The cmt_name
     */
    public void setCmtName(String cmtName) {
        this.cmtName = cmtName;
    }

}
