package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;
import java.util.Date;

public class Pltfrm_Jrnl_TblKey implements Serializable {
    private String ovrlSttnEvTrckNo;

    private Date wkdyPrd;

    private static final long serialVersionUID = 1L;

    public String getOvrlSttnEvTrckNo() {
        return ovrlSttnEvTrckNo;
    }

    public void setOvrlSttnEvTrckNo(String ovrlSttnEvTrckNo) {
        this.ovrlSttnEvTrckNo = ovrlSttnEvTrckNo == null ? null : ovrlSttnEvTrckNo.trim();
    }

    public Date getWkdyPrd() {
        return wkdyPrd;
    }

    public void setWkdyPrd(Date wkdyPrd) {
        this.wkdyPrd = wkdyPrd;
    }
}