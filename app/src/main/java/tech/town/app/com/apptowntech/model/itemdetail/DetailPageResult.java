
package tech.town.app.com.apptowntech.model.itemdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import tech.town.app.com.apptowntech.model.Return;

public class DetailPageResult {

    @SerializedName("return")
    @Expose
    private Return _return;
    @SerializedName("itemDetail")
    @Expose
    private ItemDetail itemDetail;

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
     *     The itemDetail
     */
    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    /**
     * 
     * @param itemDetail
     *     The itemDetail
     */
    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

}
