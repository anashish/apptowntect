
package tech.town.app.com.apptowntech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeCategory {

    private int columnSpan;
    private int rowSpan;
    private int position;


    @SerializedName("c_id")
    @Expose
    private String cId;
    @SerializedName("c_name")
    @Expose
    private String cName;
    @SerializedName("c_icon")
    @Expose
    private String cIcon;
    @SerializedName("c_post")
    @Expose
    private List<CPost> cPost = new ArrayList<CPost>();


    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    /**
     * 
     * @return
     *     The cId
     */
    public String getCId() {
        return cId;
    }

    /**
     * 
     * @param cId
     *     The c_id
     */
    public void setCId(String cId) {
        this.cId = cId;
    }

    /**
     * 
     * @return
     *     The cName
     */
    public String getCName() {
        return cName;
    }

    /**
     * 
     * @param cName
     *     The c_name
     */
    public void setCName(String cName) {
        this.cName = cName;
    }

    /**
     * 
     * @return
     *     The cIcon
     */
    public String getCIcon() {
        return cIcon;
    }

    /**
     * 
     * @param cIcon
     *     The c_icon
     */
    public void setCIcon(String cIcon) {
        this.cIcon = cIcon;
    }

    /**
     * 
     * @return
     *     The cPost
     */
    public List<CPost> getCPost() {
        return cPost;
    }

    /**
     * 
     * @param cPost
     *     The c_post
     */
    public void setCPost(List<CPost> cPost) {
        this.cPost = cPost;
    }



}
