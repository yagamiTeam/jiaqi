package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Py_Pdar_Tbl implements Serializable {
    private String rmtArId;

    private String pyPsnNm;

    private String pyPsnAccno;

    private BigDecimal rmtAmt;

    private Date wkdyPrd;

    private String exgWayCd;

    private static final long serialVersionUID = 1L;

    public String getRmtArId() {
        return rmtArId;
    }

    public void setRmtArId(String rmtArId) {
        this.rmtArId = rmtArId == null ? null : rmtArId.trim();
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

    public BigDecimal getRmtAmt() {
        return rmtAmt;
    }

    public void setRmtAmt(BigDecimal rmtAmt) {
        this.rmtAmt = rmtAmt;
    }

    public Date getWkdyPrd() {
        return wkdyPrd;
    }

    public void setWkdyPrd(Date wkdyPrd) {
        this.wkdyPrd = wkdyPrd;
    }

    public String getExgWayCd() {
        return exgWayCd;
    }

    public void setExgWayCd(String exgWayCd) {
        this.exgWayCd = exgWayCd == null ? null : exgWayCd.trim();
    }
}