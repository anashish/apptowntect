package tech.town.app.com.apptowntech.model.body;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import tech.town.app.com.apptowntech.model.Return;

/**
 * Created by ${="Ashish"} on 4/10/16.
 */
public class Response {

    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("msg")
    @Expose
    private MessageBody msg;

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
     * The msg
     */
    public MessageBody getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(MessageBody msg) {
        this.msg = msg;
    }

}

