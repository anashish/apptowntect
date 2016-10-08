
package tech.town.app.com.apptowntech.model.poll;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PollList {

    @SerializedName("tlp_id")
    @Expose
    private Integer tlpId;
    @SerializedName("tlp_ttl")
    @Expose
    private String tlpTtl;
    @SerializedName("tlp_op1")
    @Expose
    private String tlpOp1;
    @SerializedName("tlp_op2")
    @Expose
    private String tlpOp2;
    @SerializedName("tlp_op3")
    @Expose
    private String tlpOp3;
    @SerializedName("tlp_op1_cnt")
    @Expose
    private Integer tlpOp1Cnt;
    @SerializedName("tlp_op2_cnt")
    @Expose
    private Integer tlpOp2Cnt;
    @SerializedName("tlp_op3_cnt")
    @Expose
    private Integer tlpOp3Cnt;



    @SerializedName("tlp_icon")
    @Expose

    private String tlpIcon;


    public String getTlpIcon() {
        return tlpIcon;
    }

    public void setTlpIcon(String tlpIcon) {
        this.tlpIcon = tlpIcon;
    }


    /**
     * 
     * @return
     *     The tlpId
     */
    public Integer getTlpId() {
        return tlpId;
    }

    /**
     * 
     * @param tlpId
     *     The tlp_id
     */
    public void setTlpId(Integer tlpId) {
        this.tlpId = tlpId;
    }

    /**
     * 
     * @return
     *     The tlpTtl
     */
    public String getTlpTtl() {
        return tlpTtl;
    }

    /**
     * 
     * @param tlpTtl
     *     The tlp_ttl
     */
    public void setTlpTtl(String tlpTtl) {
        this.tlpTtl = tlpTtl;
    }

    /**
     * 
     * @return
     *     The tlpOp1
     */
    public String getTlpOp1() {
        return tlpOp1;
    }

    /**
     * 
     * @param tlpOp1
     *     The tlp_op1
     */
    public void setTlpOp1(String tlpOp1) {
        this.tlpOp1 = tlpOp1;
    }

    /**
     * 
     * @return
     *     The tlpOp2
     */
    public String getTlpOp2() {
        return tlpOp2;
    }

    /**
     * 
     * @param tlpOp2
     *     The tlp_op2
     */
    public void setTlpOp2(String tlpOp2) {
        this.tlpOp2 = tlpOp2;
    }

    /**
     * 
     * @return
     *     The tlpOp3
     */
    public String getTlpOp3() {
        return tlpOp3;
    }

    /**
     * 
     * @param tlpOp3
     *     The tlp_op3
     */
    public void setTlpOp3(String tlpOp3) {
        this.tlpOp3 = tlpOp3;
    }

    /**
     * 
     * @return
     *     The tlpOp1Cnt
     */
    public Integer getTlpOp1Cnt() {
        return tlpOp1Cnt;
    }

    /**
     * 
     * @param tlpOp1Cnt
     *     The tlp_op1_cnt
     */
    public void setTlpOp1Cnt(Integer tlpOp1Cnt) {
        this.tlpOp1Cnt = tlpOp1Cnt;
    }

    /**
     * 
     * @return
     *     The tlpOp2Cnt
     */
    public Integer getTlpOp2Cnt() {
        return tlpOp2Cnt;
    }

    /**
     * 
     * @param tlpOp2Cnt
     *     The tlp_op2_cnt
     */
    public void setTlpOp2Cnt(Integer tlpOp2Cnt) {
        this.tlpOp2Cnt = tlpOp2Cnt;
    }

    /**
     * 
     * @return
     *     The tlpOp3Cnt
     */
    public Integer getTlpOp3Cnt() {
        return tlpOp3Cnt;
    }

    /**
     * 
     * @param tlpOp3Cnt
     *     The tlp_op3_cnt
     */
    public void setTlpOp3Cnt(Integer tlpOp3Cnt) {
        this.tlpOp3Cnt = tlpOp3Cnt;
    }

}
