package com.qinglingsoft.changshuchannel.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class T_HZ_XHFY {

	@Id
	@GeneratedValue
	private Long xhfyId;//巡航费用ID
	private String xhsj;//巡航时间
	private String sgcz;//巡航车船
	@Transient
	private String chmc;//巡航车船名称
	
	private Double zjgl;//主机功率（KW）
	private String yhl;//油耗率(Kg/h.MW)
	private Double yh;//油耗(Kg/h)
	private Double jhxhlc;//计划巡航里程(公里)
	private Double sjxhsx;//实际巡航系数
	private Double sjshlc;//实际巡航里程(公里)
	private Double hs;//航速(Km/h)
	private Double sjxhsj;//实际巡航时间(小时)
	private Double hycysl;//耗用柴油数量Kg
	private Double cyh;//柴油费
	private Double zjwbfysx;//主机维保费用(元/h)
	private Double zjwbfy;//主机维保费用(元)
	private Double fdzjwbfy;//发电机组维保费用
	private Double qyhxctzhy;//全年巡航船艇总费用(元)


	public Long getXhfyId() {
		return xhfyId;
	}

	public void setXhfyId(Long xhfyId) {
		this.xhfyId = xhfyId;
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

	public Double getZjgl() {
		return zjgl;
	}

	public void setZjgl(Double zjgl) {
		this.zjgl = zjgl;
	}

	public String getYhl() {
		return yhl;
	}

	public void setYhl(String yhl) {
		this.yhl = yhl;
	}

	public Double getYh() {
		return yh;
	}

	public void setYh(Double yh) {
		this.yh = yh;
	}

	public Double getJhxhlc() {
		return jhxhlc;
	}

	public void setJhxhlc(Double jhxhlc) {
		this.jhxhlc = jhxhlc;
	}

	public Double getSjxhsx() {
		return sjxhsx;
	}

	public void setSjxhsx(Double sjxhsx) {
		this.sjxhsx = sjxhsx;
	}

	public Double getSjshlc() {
		return sjshlc;
	}

	public void setSjshlc(Double sjshlc) {
		this.sjshlc = sjshlc;
	}

	public Double getHs() {
		return hs;
	}

	public void setHs(Double hs) {
		this.hs = hs;
	}

	public Double getSjxhsj() {
		return sjxhsj;
	}

	public void setSjxhsj(Double sjxhsj) {
		this.sjxhsj = sjxhsj;
	}

	public Double getHycysl() {
		return hycysl;
	}

	public void setHycysl(Double hycysl) {
		this.hycysl = hycysl;
	}

	public Double getCyh() {
		return cyh;
	}

	public void setCyh(Double cyh) {
		this.cyh = cyh;
	}

	public Double getZjwbfysx() {
		return zjwbfysx;
	}

	public void setZjwbfysx(Double zjwbfysx) {
		this.zjwbfysx = zjwbfysx;
	}

	public Double getZjwbfy() {
		return zjwbfy;
	}

	public void setZjwbfy(Double zjwbfy) {
		this.zjwbfy = zjwbfy;
	}

	public Double getFdzjwbfy() {
		return fdzjwbfy;
	}

	public void setFdzjwbfy(Double fdzjwbfy) {
		this.fdzjwbfy = fdzjwbfy;
	}

	public Double getQyhxctzhy() {
		return qyhxctzhy;
	}

	public void setQyhxctzhy(Double qyhxctzhy) {
		this.qyhxctzhy = qyhxctzhy;
	}

	public String getChmc() {
		return chmc;
	}

	public void setChmc(String chmc) {
		this.chmc = chmc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xhfyId == null) ? 0 : xhfyId.hashCode());
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
		T_HZ_XHFY other = (T_HZ_XHFY) obj;
		if (xhfyId == null) {
			return false;
		} else if (!xhfyId.equals(other.xhfyId))
			return false;
		return true;
	}
}
