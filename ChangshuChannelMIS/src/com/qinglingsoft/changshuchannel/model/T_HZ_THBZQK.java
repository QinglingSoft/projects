package com.qinglingsoft.changshuchannel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class T_HZ_THBZQK {

	@Id
	@GeneratedValue
	private Long thbzqkid;//通航保证情况ID
	private String tbdw;//填报单位
	private String tjsj;//统计时间
	private String hdmc;//航道名称
	@Transient
	private String hdmcStr;//航道名称
	
	private String dhsj;//断航时间
	private String dhyy;//断航原因
	private String thbzl;//通航保证率
	private String tbr;//填报人
	@Temporal(TemporalType.TIMESTAMP)
	private Date tbrq;//填报日期
	private String bz;//备注

	

	public Long getThbzqkid() {
		return thbzqkid;
	}

	public void setThbzqkid(Long thbzqkid) {
		this.thbzqkid = thbzqkid;
	}

	public String getTbdw() {
		return tbdw;
	}

	public void setTbdw(String tbdw) {
		this.tbdw = tbdw;
	}

	public String getTjsj() {
		return tjsj;
	}

	public void setTjsj(String tjsj) {
		this.tjsj = tjsj;
	}

	public String getHdmc() {
		return hdmc;
	}

	public void setHdmc(String hdmc) {
		this.hdmc = hdmc;
	}

	public String getDhsj() {
		return dhsj;
	}

	public void setDhsj(String dhsj) {
		this.dhsj = dhsj;
	}

	public String getDhyy() {
		return dhyy;
	}

	public void setDhyy(String dhyy) {
		this.dhyy = dhyy;
	}

	public String getThbzl() {
		return thbzl;
	}

	public void setThbzl(String thbzl) {
		this.thbzl = thbzl;
	}

	public String getTbr() {
		return tbr;
	}

	public void setTbr(String tbr) {
		this.tbr = tbr;
	}

	public Date getTbrq() {
		return tbrq;
	}

	public void setTbrq(Date tbrq) {
		this.tbrq = tbrq;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getHdmcStr() {
		return hdmcStr;
	}

	public void setHdmcStr(String hdmcStr) {
		this.hdmcStr = hdmcStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((thbzqkid == null) ? 0 : thbzqkid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		T_HZ_THBZQK other = (T_HZ_THBZQK) obj;
		if (thbzqkid == null) {
			return false;
		} else if (!thbzqkid.equals(other.thbzqkid))
			return false;
		return true;
	}
}
