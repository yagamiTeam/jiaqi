package com.ant.jiaqi.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;


public class HomeReqVo {
	private String pyPsnNm;
	
    private String pyPsnAccno;
    
    private BigDecimal rmtAmt;
    
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date wkdyPrd;
    
	public String getPyPsnAccno() {
		return pyPsnAccno;
	}

	public void setPyPsnAccno(String pyPsnAccno) {
		this.pyPsnAccno = pyPsnAccno;
	}

	public String getPyPsnNm() {
		return pyPsnNm;
	}

	public void setPyPsnNm(String pyPsnNm) {
		this.pyPsnNm = pyPsnNm;
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
    
}
