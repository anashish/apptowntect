package tech.town.app.com.apptowntech.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ${="Ashish"} on 15/9/16.
 */
public class Msg {

    @SerializedName("user_id")
    @Expose
    private Integer userId;

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
