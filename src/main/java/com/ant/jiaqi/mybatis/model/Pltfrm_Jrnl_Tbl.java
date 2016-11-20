package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class Pltfrm_Jrnl_Tbl extends Pltfrm_Jrnl_TblKey implements Serializable {
    private String pystSvcNm;

    private Date sysRecvTime;

    private Date sysRespTime;

    private String svcRspSt;

    private String rmtArId;

    private String pystDtbsTm;

    private String cnstJrnlPcsst;

    private static final long serialVersionUID = 1L;

    public String getPystSvcNm() {
        return pystSvcNm;
    }

    public void setPystSvcNm(String pystSvcNm) {
        this.pystSvcNm = pystSvcNm == null ? null : pystSvcNm.trim();
    }

    public Date getSysRecvTime() {
        return sysRecvTime;
    }

    public void setSysRecvTime(Date sysRecvTime) {
        this.sysRecvTime = sysRecvTime;
    }

    public Date getSysRespTime() {
        return sysRespTime;
    }

    public void setSysRespTime(Date sysRespTime) {
        this.sysRespTime = sysRespTime;
    }

    public String getSvcRspSt() {
        return svcRspSt;
    }

    public void setSvcRspSt(String svcRspSt) {
        this.svcRspSt = svcRspSt == null ? null : svcRspSt.trim();
    }

    public String getRmtArId() {
        return rmtArId;
    }

    public void setRmtArId(String rmtArId) {
        this.rmtArId = rmtArId == null ? null : rmtArId.trim();
    }

    public String getPystDtbsTm() {
        return pystDtbsTm;
    }

    public void setPystDtbsTm(String pystDtbsTm) {
        this.pystDtbsTm = pystDtbsTm == null ? null : pystDtbsTm.trim();
    }

    public String getCnstJrnlPcsst() {
        return cnstJrnlPcsst;
    }

    public void setCnstJrnlPcsst(String cnstJrnlPcsst) {
        this.cnstJrnlPcsst = cnstJrnlPcsst == null ? null : cnstJrnlPcsst.trim();
    }
}