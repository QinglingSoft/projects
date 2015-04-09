package com.qinglingsoft.changshuchannel.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class T_GC_HDGCWCQK {

	@Id
	@GeneratedValue
	private Long gcwcqkId;//工程完成情况ID
	private String qcxmmc;//工程项目名称
	private String gcwcrq;//工程完成情况时间
	private String qcxmddlb;//工程地点线别
	private String gcnrky;//工程内容概要
	private String byjhgzl;//本年计划工程量
	private String bysjwcl;//本月实际完成工程量
	private String zncljwcgzl;//自年初累计实际完成工程量
	private String bnjhgzlhy;//本年计划工作量（千元）
	private String bnsjwclhy;//本月实际完成工作量（千元）
	private String zncljwcgzlhy;//自年初累计实际完成工作量（千元）
	private String bz;//备注
	@Transient
	private String mc;//项目名称
	@Transient
	private String dw;//单位


	public Long getGcwcqkId() {
		return gcwcqkId;
	}

	public void setGcwcqkId(Long gcwcqkId) {
		this.gcwcqkId = gcwcqkId;
	}

	public String getQcxmmc() {
		return qcxmmc;
	}

	public void setQcxmmc(String qcxmmc) {
		this.qcxmmc = qcxmmc;
	}

	public String getGcwcrq() {
		return gcwcrq;
	}

	public void setGcwcrq(String gcwcrq) {
		this.gcwcrq = gcwcrq;
	}

	public String getQcxmddlb() {
		return qcxmddlb;
	}

	public void setQcxmddlb(String qcxmddlb) {
		this.qcxmddlb = qcxmddlb;
	}

	public String getGcnrky() {
		return gcnrky;
	}

	public void setGcnrky(String gcnrky) {
		this.gcnrky = gcnrky;
	}

	public String getByjhgzl() {
		return byjhgzl;
	}

	public void setByjhgzl(String byjhgzl) {
		this.byjhgzl = byjhgzl;
	}

	public String getBysjwcl() {
		return bysjwcl;
	}

	public void setBysjwcl(String bysjwcl) {
		this.bysjwcl = bysjwcl;
	}

	public String getZncljwcgzl() {
		return zncljwcgzl;
	}

	public void setZncljwcgzl(String zncljwcgzl) {
		this.zncljwcgzl = zncljwcgzl;
	}

	public String getBnjhgzlhy() {
		return bnjhgzlhy;
	}

	public void setBnjhgzlhy(String bnjhgzlhy) {
		this.bnjhgzlhy = bnjhgzlhy;
	}

	public String getBnsjwclhy() {
		return bnsjwclhy;
	}

	public void setBnsjwclhy(String bnsjwclhy) {
		this.bnsjwclhy = bnsjwclhy;
	}

	public String getZncljwcgzlhy() {
		return zncljwcgzlhy;
	}

	public void setZncljwcgzlhy(String zncljwcgzlhy) {
		this.zncljwcgzlhy = zncljwcgzlhy;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gcwcqkId == null) ? 0 : gcwcqkId.hashCode());
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
		T_GC_HDGCWCQK other = (T_GC_HDGCWCQK) obj;
		if (gcwcqkId == null) {
			return false;
		} else if (!gcwcqkId.equals(other.gcwcqkId))
			return false;
		return true;
	}
}
