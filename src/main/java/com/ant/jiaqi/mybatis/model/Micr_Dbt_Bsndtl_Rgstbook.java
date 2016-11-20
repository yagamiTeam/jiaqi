package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Micr_Dbt_Bsndtl_Rgstbook extends Micr_Dbt_Bsndtl_RgstbookKey implements Serializable {
    private String rcvPcprBrno;

    private String pyPsnNm;

    private String pyPsnAccno;

    private String rcvpymtpsNm;

    private String rcvpymtpsAccno;

    private BigDecimal rmtAmt;

    private String msgrpPcsst;

    private String rmtArId;

    private static final long serialVersionUID = 1L;

    public String getRcvPcprBrno() {
        return rcvPcprBrno;
    }

    public void setRcvPcprBrno(String rcvPcprBrno) {
        this.rcvPcprBrno = rcvPcprBrno == null ? null : rcvPcprBrno.trim();
    }

    public String getPyPsnNm() {
        return pyPsnNm;
    }

    public void setPyPsnNm(String pyPsnNm) {
        this.pyPsnNm = pyPsnNm == null ? null : pyPsnNm.trim();
    }

    public String getPyPsnAccno() {
        return pyPsnAccno;
    }

    public void setPyPsnAccno(String pyPsnAccno) {
        this.pyPsnAccno = pyPsnAccno == null ? null : pyPsnAccno.trim();
    }

    public String getRcvpymtpsNm() {
        return rcvpymtpsNm;
    }

    public void setRcvpymtpsNm(String rcvpymtpsNm) {
        this.rcvpymtpsNm = rcvpymtpsNm == null ? null : rcvpymtpsNm.trim();
    }

    public String getRcvpymtpsAccno() {
        return rcvpymtpsAccno;
    }

    public void setRcvpymtpsAccno(String rcvpymtpsAccno) {
        this.rcvpymtpsAccno = rcvpymtpsAccno == null ? null : rcvpymtpsAccno.trim();
    }

    public BigDecimal getRmtAmt() {
        return rmtAmt;
    }

    public void setRmtAmt(BigDecimal rmtAmt) {
        this.rmtAmt = rmtAmt;
    }

    public String getMsgrpPcsst() {
        return msgrpPcsst;
    }

    public void setMsgrpPcsst(String msgrpPcsst) {
        this.msgrpPcsst = msgrpPcsst == null ? null : msgrpPcsst.trim();
    }

    public String getRmtArId() {
        return rmtArId;
    }

    public void setRmtArId(String rmtArId) {
        this.rmtArId = rmtArId == null ? null : rmtArId.trim();
    }
}