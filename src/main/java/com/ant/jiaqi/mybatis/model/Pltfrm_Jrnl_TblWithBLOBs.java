package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;

public class Pltfrm_Jrnl_TblWithBLOBs extends Pltfrm_Jrnl_Tbl implements Serializable {
    private byte[] pystRqsMsgbdyCntnt;

    private byte[] pystRspMsgbdyCntnt;

    private static final long serialVersionUID = 1L;

    public byte[] getPystRqsMsgbdyCntnt() {
        return pystRqsMsgbdyCntnt;
    }

    public void setPystRqsMsgbdyCntnt(byte[] pystRqsMsgbdyCntnt) {
        this.pystRqsMsgbdyCntnt = pystRqsMsgbdyCntnt;
    }

    public byte[] getPystRspMsgbdyCntnt() {
        return pystRspMsgbdyCntnt;
    }

    public void setPystRspMsgbdyCntnt(byte[] pystRspMsgbdyCntnt) {
        this.pystRspMsgbdyCntnt = pystRspMsgbdyCntnt;
    }
}