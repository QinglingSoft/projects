package com.qinglingsoft.changshuchannel.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.model.Code;
import com.qinglingsoft.changshuchannel.model.CodeTable;
import com.qinglingsoft.changshuchannel.model.T_GC_HDGCWCQK;
import com.qinglingsoft.changshuchannel.model.T_HZ_THBZQK;
import com.qinglingsoft.changshuchannel.model.T_HZ_XHFY;
import com.qinglingsoft.changshuchannel.model.T_HZ_XHJH;
import com.qinglingsoft.changshuchannel.model.T_HZ_XHLC;
import com.qinglingsoft.changshuchannel.service.CodeTableService;
import com.qinglingsoft.changshuchannel.service.ReportService;

@Component
@Scope("prototype")
public class ReportHelper {
	@Resource
	private ReportService reportService;
	@Resource
	private CodeTableService codeTableService;
	private Date reportDate;
	private String dateStr;
	private int hangDaoNum;
	
	private Date otherDate;
	private String personName;
	private String unitName;
	
	
	/**
	 * 航道工程完成
	 * @return
	 */
	public List<T_GC_HDGCWCQK> getProjectProcessReports(){
		if(dateStr!=null&&dateStr!=""){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			try {
				reportDate = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(reportDate==null){
			reportDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			dateStr = sdf.format(reportDate);
		}
		List<T_GC_HDGCWCQK> reportList = new ArrayList<T_GC_HDGCWCQK>();
		List<T_GC_HDGCWCQK> list = reportService.getProjectProcessReports(dateStr);
		CodeTable codeTable = codeTableService.findByName("TC_HDGCXM");
		Map<String, Code> codeMap = codeTable.getCodes();
		for (Code c : codeMap.values()) {
			boolean flag = true;//判断查询结果中是否有该航道，true时没有，增加航道确保显示所有航道
			int s = c.getValue().length()-1;
		    String pre = "";
		    for (int i = 0; i < s; i++) {
			   pre+="&nbsp;";
		    }
			for (T_GC_HDGCWCQK t_GC_HDGCWCQK : list) {
			   if(t_GC_HDGCWCQK.getQcxmmc().equals(c.getValue())){
				   if(c.getMeaning().indexOf("(")>0){
					   t_GC_HDGCWCQK.setMc(pre+c.getMeaning().substring(0,c.getMeaning().indexOf("(")));
					   t_GC_HDGCWCQK.setDw(c.getMeaning().substring(c.getMeaning().indexOf("(")+1,c.getMeaning().indexOf(")")));
				   }else{
					   t_GC_HDGCWCQK.setMc(pre+c.getMeaning());
					}
				   flag = false;
				   reportList.add(t_GC_HDGCWCQK);
			   }
			}
			if(flag){
				T_GC_HDGCWCQK tk = new T_GC_HDGCWCQK();
				if(c.getMeaning().indexOf("(")>0){
					tk.setMc(pre+c.getMeaning().substring(0,c.getMeaning().indexOf("(")));
					tk.setDw(c.getMeaning().substring(c.getMeaning().indexOf("(")+1,c.getMeaning().indexOf(")")));
				}else{
					tk.setMc(pre+c.getMeaning());
				}
				reportList.add(tk);
			}
		}
		list = null;
		return reportList;
	}
	
	/**
	 * 巡航计划
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<List> getCruisePlanReports(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if(reportDate==null){
			try {
				reportDate = sdf.parse(sdf.format(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(dateStr!=null&&dateStr!=""){
			
			try {
				reportDate = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//页面展示数据集合 第一个list为巡航船只xhccList，之后list为巡航航道
		List<List> dataList = new ArrayList<List>();
		
		//巡航航道
		CodeTable ct = codeTableService.findByName("TC_HDDM");
		
		//获取巡航船只
		CodeTable codeTable = codeTableService.findByName("TC_XHCC");
		Map<String, Code> codeMap = codeTable.getCodes();
		List<Code> xhccList = new ArrayList<Code>();
		for (Code c : codeMap.values()) {
			xhccList.add(c);
		}
		dataList.add(xhccList);
		hangDaoNum = xhccList.size();
		//获取当月最大天数
		int dateNum = getMaxDateNum(reportDate);
		List<String[]> xhhdList = new ArrayList<String[]>();
		
		List<T_HZ_XHJH> list = reportService.getCruisePlanReports(reportDate);
		for (int i = 1; i <= dateNum; i++) {
			String[] datas = new String[codeMap.size()+1];
			datas[0]=""+i;
			for (T_HZ_XHJH t : list) {
				//日期
				if(t.getXhsj()==i){
					for (int j = 1; j <= xhccList.size(); j++) {
						//巡航车船
						if(xhccList.get(j-1).getValue().equals(t.getSgcz())){
							for (Code c : ct.getCodes().values()) {
								if(c.getValue().equals(t.getHdmc())){
									datas[j]=c.getMeaning();
								}
							}
							
						}
					}
				}
			}
			xhhdList.add(datas);
			datas =null;
		}
		dataList.add(xhhdList);
		xhccList =null;
		xhhdList =null;
		return dataList;
	}
	
	/**
	 * 巡航里程
	 * @return
	 */
	public List<T_HZ_XHLC> getCruiseMileageReports() {
		if(reportDate==null){
			reportDate = new Date();
		}
		if(dateStr!=null&&dateStr!=""){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			try {
				reportDate = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//巡航航道
		CodeTable ct = codeTableService.findByName("TC_HDDM");
				
		//获取巡航船只
		CodeTable codeTable = codeTableService.findByName("TC_XHCC");
		
		T_HZ_XHLC yt = new T_HZ_XHLC();//巡航总里程
		yt.setHdStr("巡航总里程");
		
		T_HZ_XHLC mt = new T_HZ_XHLC();//巡航平均里程
		mt.setHdStr("每月巡航里程");
		
		List<T_HZ_XHLC> list = reportService.getCruiseMileageReports(reportDate);
		for (T_HZ_XHLC t_HZ_XHLC : list) {
			for (Code c : codeTable.getCodes().values()) {
				if(t_HZ_XHLC.getSgcz().equals(c.getValue())){
					t_HZ_XHLC.setChStr(c.getMeaning());
				}
			}
			for (Code c : ct.getCodes().values()) {
				if(t_HZ_XHLC.getHdmc().equals(c.getValue())){
					t_HZ_XHLC.setHdStr(c.getMeaning());
				}
			}
			
			yt.setJhxhlc((yt.getJhxhlc()==null?0:yt.getJhxhlc())+(t_HZ_XHLC.getJhxhlc()==null?0:t_HZ_XHLC.getJhxhlc()));
			yt.setSjwfxjlc((yt.getSjwfxjlc()==null?0:yt.getSjwfxjlc())+(t_HZ_XHLC.getSjwfxjlc()==null?0:t_HZ_XHLC.getSjwfxjlc()));
			
		}
		mt.setJhxhlc(Double.valueOf(new BigDecimal((yt.getJhxhlc()==null?0:yt.getJhxhlc())/12).setScale(2, BigDecimal.ROUND_HALF_UP)+""));
		mt.setSjwfxjlc(Double.valueOf(new BigDecimal((yt.getSjwfxjlc()==null?0:yt.getSjwfxjlc())/12).setScale(2, BigDecimal.ROUND_HALF_UP)+""));
		
		list.add(mt);
		list.add(yt);
		return list;
	}
	
	/**
	 * 巡航费用
	 * @return
	 */
	public List<T_HZ_XHFY> getCruiseCostReports() {
		if(dateStr!=null&&dateStr!=""){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			try {
				reportDate = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(reportDate==null){
			reportDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			dateStr = sdf.format(reportDate);
		}
		List<T_HZ_XHFY> reportList= new ArrayList<T_HZ_XHFY>();
		T_HZ_XHFY tt = new T_HZ_XHFY();//总和
		tt.setChmc("总计");
		
		List<T_HZ_XHFY> list = reportService.getCruiseCostReports(dateStr);
		//获取巡航船只
		CodeTable codeTable = codeTableService.findByName("TC_XHCC");
		Map<String, Code> codeMap = codeTable.getCodes();
		for (Code c : codeMap.values()) {
			boolean flag = true;//判断查询结果中是否有该船只，true时没有，增加船只
			for (T_HZ_XHFY t_HZ_XHFY : list) {
			   if(t_HZ_XHFY.getSgcz().equals(c.getValue())){
				   flag = false;
				   t_HZ_XHFY.setChmc(c.getMeaning());
				   reportList.add(t_HZ_XHFY);
				   //
				   tt.setSjshlc((tt.getSjshlc()==null?0:tt.getSjshlc())+(t_HZ_XHFY.getSjshlc()==null?0:t_HZ_XHFY.getSjshlc()));
				   tt.setHycysl((tt.getHycysl()==null?0:tt.getHycysl())+(t_HZ_XHFY.getHycysl()==null?0:t_HZ_XHFY.getHycysl()));
				   tt.setCyh((tt.getCyh()==null?0:tt.getCyh())+(t_HZ_XHFY.getCyh()==null?0:t_HZ_XHFY.getCyh()));
				   tt.setZjwbfy((tt.getZjwbfy()==null?0:tt.getZjwbfy())+(t_HZ_XHFY.getZjwbfy()==null?0:t_HZ_XHFY.getZjwbfy()));
				   tt.setFdzjwbfy((tt.getFdzjwbfy()==null?0:tt.getFdzjwbfy())+(t_HZ_XHFY.getFdzjwbfy()==null?0:t_HZ_XHFY.getFdzjwbfy()));
				   tt.setQyhxctzhy((tt.getQyhxctzhy()==null?0:tt.getQyhxctzhy())+(t_HZ_XHFY.getQyhxctzhy()==null?0:t_HZ_XHFY.getQyhxctzhy()));
			   }
			}
			if(flag){
				T_HZ_XHFY tk = new T_HZ_XHFY();
				tk.setChmc(c.getMeaning());
				reportList.add(tk);
			}
		}
		reportList.add(tt);
		list = null;
		
		return reportList;
	}
	
	
	/**
	 * 航道通航保证情况
	 * @return
	 */
	public List<T_HZ_THBZQK> getChannelStateReports() {
		
		//巡航航道
		CodeTable ct = codeTableService.findByName("TC_HDDM");
		List<T_HZ_THBZQK> list = reportService.getChannelStateReports(dateStr);
		for (T_HZ_THBZQK t_HZ_THBZQK : list) {
			for (Code c : ct.getCodes().values()) {
				if(t_HZ_THBZQK.getHdmc().equals(c.getValue())){
					t_HZ_THBZQK.setHdmcStr(c.getMeaning());
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				otherDate = sdf.parse(t_HZ_THBZQK.getTbrq());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			personName = t_HZ_THBZQK.getTbr();
			unitName = t_HZ_THBZQK.getTbdw();
		}
		return list;
	}
	
	private int getMaxDateNum(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,   1);

        return calendar.getActualMaximum(Calendar.DATE);
    }
	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public int getHangDaoNum() {
		return hangDaoNum;
	}

	public void setHangDaoNum(int hangDaoNum) {
		this.hangDaoNum = hangDaoNum;
	}

	public Date getOtherDate() {
		return otherDate;
	}

	public void setOtherDate(Date otherDate) {
		this.otherDate = otherDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
}
