package tech.town.app.com.apptowntech.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import tech.town.app.com.apptowntech.model.Return;

/**
 * Created by ${="Ashish"} on 15/9/16.
 */
public class Login {

    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("msg")
    @Expose
    private Msg msg;

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
    public Msg getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(Msg msg) {
        this.msg = msg;
    }

}
