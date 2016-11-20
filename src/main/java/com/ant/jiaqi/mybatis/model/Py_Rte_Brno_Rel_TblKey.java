package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;

public class Py_Rte_Brno_Rel_TblKey implements Serializable {
    private String pcpBrno;

    private String pyRteCd;

    private static final long serialVersionUID = 1L;

    public String getPcpBrno() {
        return pcpBrno;
    }

    public void setPcpBrno(String pcpBrno) {
        this.pcpBrno = pcpBrno == null ? null : pcpBrno.trim();
    }

    public String getPyRteCd() {
        return pyRteCd;
    }

    public void setPyRteCd(String pyRteCd) {
        this.pyRteCd = pyRteCd == null ? null : pyRteCd.trim();
    }
}