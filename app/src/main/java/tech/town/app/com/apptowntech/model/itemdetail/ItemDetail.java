
package tech.town.app.com.apptowntech.model.itemdetail;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetail {

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
    @SerializedName("p_vurl")
    @Expose
    private String pVurl;
    @SerializedName("p_detail")
    @Expose
    private String pDetail;
    @SerializedName("p_www")
    @Expose
    private String pWww;
    @SerializedName("p_likes")
    @Expose
    private String pLikes;
    @SerializedName("p_isbookmark")
    @Expose
    private String pIsbookmark;
    @SerializedName("item_comment")
    @Expose
    private List<ItemComment> itemComment = new ArrayList<ItemComment>();
    @SerializedName("item_similer")
    @Expose
    private List<ItemSimiler> itemSimiler = new ArrayList<ItemSimiler>();

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

    /**
     * 
     * @return
     *     The pVurl
     */
    public String getPVurl() {
        return pVurl;
    }

    /**
     * 
     * @param pVurl
     *     The p_vurl
     */
    public void setPVurl(String pVurl) {
        this.pVurl = pVurl;
    }

    /**
     * 
     * @return
     *     The pDetail
     */
    public String getPDetail() {
        return pDetail;
    }

    /**
     * 
     * @param pDetail
     *     The p_detail
     */
    public void setPDetail(String pDetail) {
        this.pDetail = pDetail;
    }

    /**
     * 
     * @return
     *     The pWww
     */
    public String getPWww() {
        return pWww;
    }

    /**
     * 
     * @param pWww
     *     The p_www
     */
    public void setPWww(String pWww) {
        this.pWww = pWww;
    }

    /**
     * 
     * @return
     *     The pLikes
     */
    public String getPLikes() {
        return pLikes;
    }

    /**
     * 
     * @param pLikes
     *     The p_likes
     */
    public void setPLikes(String pLikes) {
        this.pLikes = pLikes;
    }

    /**
     * 
     * @return
     *     The pIsbookmark
     */
    public String getPIsbookmark() {
        return pIsbookmark;
    }

    /**
     * 
     * @param pIsbookmark
     *     The p_isbookmark
     */
    public void setPIsbookmark(String pIsbookmark) {
        this.pIsbookmark = pIsbookmark;
    }

    /**
     * 
     * @return
     *     The itemComment
     */
    public List<ItemComment> getItemComment() {
        return itemComment;
    }

    /**
     * 
     * @param itemComment
     *     The item_comment
     */
    public void setItemComment(List<ItemComment> itemComment) {
        this.itemComment = itemComment;
    }

    /**
     * 
     * @return
     *     The itemSimiler
     */
    public List<ItemSimiler> getItemSimiler() {
        return itemSimiler;
    }

    /**
     * 
     * @param itemSimiler
     *     The item_similer
     */
    public void setItemSimiler(List<ItemSimiler> itemSimiler) {
        this.itemSimiler = itemSimiler;
    }

}
