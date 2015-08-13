package com.qinglingsoft.changshuchannel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class T_HZ_XHJH {

	@Id
	@GeneratedValue
	private Long xhjhId;//巡航计划ID
	private String xhrq;//巡航计划年月
	private String sgcz;//巡航车船
	private Integer xhsj;//巡航日期
	private String hdmc;//航道名称
	private Double hdlc;//航道里程
	private String sflfxh;//是否来回巡航
	private String bz;//备注

	public Long getXhjhId() {
		return xhjhId;
	}

	public void setXhjhId(Long xhjhId) {
		this.xhjhId = xhjhId;
	}

	public String getXhrq() {
		return xhrq;
	}

	public void setXhrq(String xhrq) {
		this.xhrq = xhrq;
	}

	public String getSgcz() {
		return sgcz;
	}

	public void setSgcz(String sgcz) {
		this.sgcz = sgcz;
	}

	public Integer getXhsj() {
		return xhsj;
	}

	public void setXhsj(Integer xhsj) {
		this.xhsj = xhsj;
	}

	public String getHdmc() {
		return hdmc;
	}

	public void setHdmc(String hdmc) {
		this.hdmc = hdmc;
	}

	public Double getHdlc() {
		return hdlc;
	}

	public void setHdlc(Double hdlc) {
		this.hdlc = hdlc;
	}

	public String getSflfxh() {
		return sflfxh;
	}

	public void setSflfxh(String sflfxh) {
		this.sflfxh = sflfxh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xhjhId == null) ? 0 : xhjhId.hashCode());
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
		T_HZ_XHJH other = (T_HZ_XHJH) obj;
		if (xhjhId == null) {
			return false;
		} else if (!xhjhId.equals(other.xhjhId))
			return false;
		return true;
	}
}
