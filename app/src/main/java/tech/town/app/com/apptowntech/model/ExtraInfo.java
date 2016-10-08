
package tech.town.app.com.apptowntech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExtraInfo {

    @SerializedName("home_version")
    @Expose
    private String homeVersion;
    @SerializedName("icon_path")
    @Expose
    private String iconPath;
    @SerializedName("url_radio")
    @Expose
    private String urlRadio;
    @SerializedName("url_livetv")
    @Expose
    private String urlLivetv;

    /**
     * 
     * @return
     *     The homeVersion
     */
    public String getHomeVersion() {
        return homeVersion;
    }

    /**
     * 
     * @param homeVersion
     *     The home_version
     */
    public void setHomeVersion(String homeVersion) {
        this.homeVersion = homeVersion;
    }

    /**
     * 
     * @return
     *     The iconPath
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * 
     * @param iconPath
     *     The icon_path
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * 
     * @return
     *     The urlRadio
     */
    public String getUrlRadio() {
        return urlRadio;
    }

    /**
     * 
     * @param urlRadio
     *     The url_radio
     */
    public void setUrlRadio(String urlRadio) {
        this.urlRadio = urlRadio;
    }

    /**
     * 
     * @return
     *     The urlLivetv
     */
    public String getUrlLivetv() {
        return urlLivetv;
    }

    /**
     * 
     * @param urlLivetv
     *     The url_livetv
     */
    public void setUrlLivetv(String urlLivetv) {
        this.urlLivetv = urlLivetv;
    }

}
