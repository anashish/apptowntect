package tech.town.app.com.apptowntech.model.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ${="Ashish"} on 4/10/16.
 */
public class MessageBody {

    @SerializedName("user_message")
    @Expose
    private String userMessage;

    /**
     *
     * @return
     * The userMessage
     */
    public String getUserMessage() {
        return userMessage;
    }

    /**
     *
     * @param userMessage
     * The user_message
     */
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
