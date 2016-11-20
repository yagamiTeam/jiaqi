package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Py_Rte_Tbl implements Serializable {
    private String pyRteCd;

    private String exgWayCd;

    private String bsnOptSt;

    private Date rmtnwydt;

    private BigDecimal amtLwrlmt;

    private BigDecimal rmtAmtUplm;

    private String holInd;

    private BigDecimal holRstrcAmt;

    private static final long serialVersionUID = 1L;

    public String getPyRteCd() {
        return pyRteCd;
    }

    public void setPyRteCd(String pyRteCd) {
        this.pyRteCd = pyRteCd == null ? null : pyRteCd.trim();
    }

    public String getExgWayCd() {
        return exgWayCd;
    }

    public void setExgWayCd(String exgWayCd) {
        this.exgWayCd = exgWayCd == null ? null : exgWayCd.trim();
    }

    public String getBsnOptSt() {
        return bsnOptSt;
    }

    public void setBsnOptSt(String bsnOptSt) {
        this.bsnOptSt = bsnOptSt == null ? null : bsnOptSt.trim();
    }

    public Date getRmtnwydt() {
        return rmtnwydt;
    }

    public void setRmtnwydt(Date rmtnwydt) {
        this.rmtnwydt = rmtnwydt;
    }

    public BigDecimal getAmtLwrlmt() {
        return amtLwrlmt;
    }

    public void setAmtLwrlmt(BigDecimal amtLwrlmt) {
        this.amtLwrlmt = amtLwrlmt;
    }

    public BigDecimal getRmtAmtUplm() {
        return rmtAmtUplm;
    }

    public void setRmtAmtUplm(BigDecimal rmtAmtUplm) {
        this.rmtAmtUplm = rmtAmtUplm;
    }

    public String getHolInd() {
        return holInd;
    }

    public void setHolInd(String holInd) {
        this.holInd = holInd == null ? null : holInd.trim();
    }

    public BigDecimal getHolRstrcAmt() {
        return holRstrcAmt;
    }

    public void setHolRstrcAmt(BigDecimal holRstrcAmt) {
        this.holRstrcAmt = holRstrcAmt;
    }
}