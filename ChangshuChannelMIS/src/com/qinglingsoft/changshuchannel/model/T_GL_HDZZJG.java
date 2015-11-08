package com.qinglingsoft.changshuchannel.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class T_GL_HDZZJG {

	@Id
	@GeneratedValue
	private Long gljg_ksid;//管理机构科室ID
	private String gljg_ksdm;//管理机构科室类别
	private String gljg_qhdm;//管理机构科室名称
	private Integer glrysl;//科室人员数量
	private Long hdgljgid;//航道管理机构ID
	private String sfbj_hdgcgh;//是否允许编辑航道工程规划计划
	private String sfbj_hdyhgh;//是否允许编辑航道养护规划计划
	private String sfbj_xhjh;//是否允许编辑巡航计划
	private String sfxg_hdjcxx;//是否有修改航道基础信息权限
	private String bz;//备注
	public Long getGljg_ksid() {
		return gljg_ksid;
	}
	public void setGljg_ksid(Long gljg_ksid) {
		this.gljg_ksid = gljg_ksid;
	}
	public String getGljg_ksdm() {
		return gljg_ksdm;
	}
	public void setGljg_ksdm(String gljg_ksdm) {
		this.gljg_ksdm = gljg_ksdm;
	}
	public String getGljg_qhdm() {
		return gljg_qhdm;
	}
	public void setGljg_qhdm(String gljg_qhdm) {
		this.gljg_qhdm = gljg_qhdm;
	}
	public Integer getGlrysl() {
		return glrysl;
	}
	public void setGlrysl(Integer glrysl) {
		this.glrysl = glrysl;
	}
	public Long getHdgljgid() {
		return hdgljgid;
	}
	public void setHdgljgid(Long hdgljgid) {
		this.hdgljgid = hdgljgid;
	}
	public String getSfbj_hdgcgh() {
		return sfbj_hdgcgh;
	}
	public void setSfbj_hdgcgh(String sfbj_hdgcgh) {
		this.sfbj_hdgcgh = sfbj_hdgcgh;
	}
	public String getSfbj_hdyhgh() {
		return sfbj_hdyhgh;
	}
	public void setSfbj_hdyhgh(String sfbj_hdyhgh) {
		this.sfbj_hdyhgh = sfbj_hdyhgh;
	}
	public String getSfbj_xhjh() {
		return sfbj_xhjh;
	}
	public void setSfbj_xhjh(String sfbj_xhjh) {
		this.sfbj_xhjh = sfbj_xhjh;
	}
	public String getSfxg_hdjcxx() {
		return sfxg_hdjcxx;
	}
	public void setSfxg_hdjcxx(String sfxg_hdjcxx) {
		this.sfxg_hdjcxx = sfxg_hdjcxx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

	
}
