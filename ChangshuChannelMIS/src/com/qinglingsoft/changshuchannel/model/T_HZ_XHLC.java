package com.qinglingsoft.changshuchannel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class T_HZ_XHLC {

	@Id
	@GeneratedValue
	private Long xhlcId;//巡航里程ID
	private String xhsj;//巡航时间
	private String sgcz;//巡航车船
	private int xhcs;//巡航次数
	private int sflfxh;//是否来回巡航
	private String hdmc;//
	private Double hdlc;//航道里程
	private Double jhxhlc;//计划巡航里程
	private Double sjwfxjlc;//实际往返巡航里程
	@Transient
	private String wflcStr;//往返里程
	@Transient
	private String chStr;//巡航车船名称
	@Transient
	private String hdStr;//航道名称

	

	public Long getXhlcId() {
		return xhlcId;
	}

	public void setXhlcId(Long xhlcId) {
		this.xhlcId = xhlcId;
	}

	public String getXhsj() {
		return xhsj;
	}

	public void setXhsj(String xhsj) {
		this.xhsj = xhsj;
	}

	public String getSgcz() {
		return sgcz;
	}

	public void setSgcz(String sgcz) {
		this.sgcz = sgcz;
	}

	public int getXhcs() {
		return xhcs;
	}

	public void setXhcs(int xhcs) {
		this.xhcs = xhcs;
	}

	public int getSflfxh() {
		return sflfxh;
	}

	public void setSflfxh(int sflfxh) {
		this.sflfxh = sflfxh;
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

	public Double getJhxhlc() {
		return jhxhlc;
	}

	public void setJhxhlc(Double jhxhlc) {
		this.jhxhlc = jhxhlc;
	}

	public Double getSjwfxjlc() {
		return sjwfxjlc;
	}

	public void setSjwfxjlc(Double sjwfxjlc) {
		this.sjwfxjlc = sjwfxjlc;
	}

	public String getWflcStr() {
		if(this.sflfxh!=0){
			wflcStr = ((this.xhcs-this.sflfxh*2)+this.sflfxh)+" &frasl; "+this.sflfxh;
		}else{
			wflcStr =this.xhcs+"";
		}
		
		return wflcStr;
	}

	public String getChStr() {
		return chStr;
	}

	public void setChStr(String chStr) {
		this.chStr = chStr;
	}

	public String getHdStr() {
		return hdStr;
	}

	public void setHdStr(String hdStr) {
		this.hdStr = hdStr;
	}

	public void setWflcStr(String wflcStr) {
		this.wflcStr = wflcStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xhlcId == null) ? 0 : xhlcId.hashCode());
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
		T_HZ_XHLC other = (T_HZ_XHLC) obj;
		if (xhlcId == null) {
			return false;
		} else if (!xhlcId.equals(other.xhlcId))
			return false;
		return true;
	}
}
