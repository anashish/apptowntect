
package tech.town.app.com.apptowntech.model.poll;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import tech.town.app.com.apptowntech.model.Return;


public class PollResult {

    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("PollList")
    @Expose
    private List<PollList> pollList = new ArrayList<PollList>();

    /**
     * 
     * @return
     *     The _return
     */
    public Return getReturn() {
        return _return;
    }

    /**
     * 
     * @param _return
     *     The return
     */
    public void setReturn(Return _return) {
        this._return = _return;
    }

    /**
     * 
     * @return
     *     The pollList
     */
    public List<PollList> getPollList() {
        return pollList;
    }

    /**
     * 
     * @param pollList
     *     The PollList
     */
    public void setPollList(List<PollList> pollList) {
        this.pollList = pollList;
    }

}
