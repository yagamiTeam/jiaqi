package com.ant.jiaqi.mybatis.model;

import java.io.Serializable;

public class Sequence implements Serializable {
    private String seqKey;

    private Long seqValue;

    private static final long serialVersionUID = 1L;

    public String getSeqKey() {
        return seqKey;
    }

    public void setSeqKey(String seqKey) {
        this.seqKey = seqKey == null ? null : seqKey.trim();
    }

    public Long getSeqValue() {
        return seqValue;
    }

    public void setSeqValue(Long seqValue) {
        this.seqValue = seqValue;
    }
}