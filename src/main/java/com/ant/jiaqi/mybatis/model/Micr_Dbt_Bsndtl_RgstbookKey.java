package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class Micr_Dbt_Bsndtl_RgstbookKey implements Serializable {
    private String dtlIdNo;

    private String ittPcprBrno;

    private String msgidno;

    private Date msgrpEntrstDt;

    private String msgrpRcvSndIdcd;

    private static final long serialVersionUID = 1L;

    public String getDtlIdNo() {
        return dtlIdNo;
    }

    public void setDtlIdNo(String dtlIdNo) {
        this.dtlIdNo = dtlIdNo == null ? null : dtlIdNo.trim();
    }

    public String getIttPcprBrno() {
        return ittPcprBrno;
    }

    public void setIttPcprBrno(String ittPcprBrno) {
        this.ittPcprBrno = ittPcprBrno == null ? null : ittPcprBrno.trim();
    }

    public String getMsgidno() {
        return msgidno;
    }

    public void setMsgidno(String msgidno) {
        this.msgidno = msgidno == null ? null : msgidno.trim();
    }

    public Date getMsgrpEntrstDt() {
        return msgrpEntrstDt;
    }

    public void setMsgrpEntrstDt(Date msgrpEntrstDt) {
        this.msgrpEntrstDt = msgrpEntrstDt;
    }

    public String getMsgrpRcvSndIdcd() {
        return msgrpRcvSndIdcd;
    }

    public void setMsgrpRcvSndIdcd(String msgrpRcvSndIdcd) {
        this.msgrpRcvSndIdcd = msgrpRcvSndIdcd == null ? null : msgrpRcvSndIdcd.trim();
    }
}