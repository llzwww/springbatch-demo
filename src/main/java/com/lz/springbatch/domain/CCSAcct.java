package com.lz.springbatch.domain;

public class CCSAcct {
	private String org;
	private Long accNbr;
	private Long custId;
	private Long custLmtId;

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public Long getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(Long accNbr) {
		this.accNbr = accNbr;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getCustLmtId() {
		return custLmtId;
	}

	public void setCustLmtId(Long custLmtId) {
		this.custLmtId = custLmtId;
	}

}
