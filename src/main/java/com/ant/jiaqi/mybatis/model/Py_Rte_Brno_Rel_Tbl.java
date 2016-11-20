package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;

public class Py_Rte_Brno_Rel_Tbl extends Py_Rte_Brno_Rel_TblKey implements Serializable {
    private String pcpFullnm;

    private String blngDrcPcprBrno;

    private static final long serialVersionUID = 1L;

    public String getPcpFullnm() {
        return pcpFullnm;
    }

    public void setPcpFullnm(String pcpFullnm) {
        this.pcpFullnm = pcpFullnm == null ? null : pcpFullnm.trim();
    }

    public String getBlngDrcPcprBrno() {
        return blngDrcPcprBrno;
    }

    public void setBlngDrcPcprBrno(String blngDrcPcprBrno) {
        this.blngDrcPcprBrno = blngDrcPcprBrno == null ? null : blngDrcPcprBrno.trim();
    }
}