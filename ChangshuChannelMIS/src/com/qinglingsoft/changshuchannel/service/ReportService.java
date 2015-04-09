package com.qinglingsoft.changshuchannel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qinglingsoft.changshuchannel.model.T_GC_HDGCWCQK;
import com.qinglingsoft.changshuchannel.model.T_HZ_THBZQK;
import com.qinglingsoft.changshuchannel.model.T_HZ_XHFY;
import com.qinglingsoft.changshuchannel.model.T_HZ_XHJH;
import com.qinglingsoft.changshuchannel.model.T_HZ_XHLC;

@Repository
@Transactional
public class ReportService {

	@Resource
	private SessionFactory sessionFactory;


	/**
	 * 航道工程完成
	 * @param reportDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T_GC_HDGCWCQK> getProjectProcessReports(String reportDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				T_GC_HDGCWCQK.class);
		crit.add(Restrictions.eq("gcwcrq", reportDate));
		crit.addOrder(Order.asc("qcxmmc"));
		return crit.list();
	}
	
	/**
	 * 巡航计划
	 * @param reportDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T_HZ_XHJH> getCruisePlanReports(Date reportDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				T_HZ_XHJH.class);
		crit.add(Restrictions.ge("xhrq", getFirstday(reportDate)));
		crit.add(Restrictions.le("xhrq", getLastday(reportDate)));
		crit.addOrder(Order.asc("xhsj"));
		return crit.list();
	}
	
	/**
	 * 航政巡航里程
	 * @param reportDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T_HZ_XHLC> getCruiseMileageReports(Date reportDate) {
		
		String sql = " select sgcz,sum(xhcs) as xhcs,sum(sflfxh) as sflfxh,hdmc,sum(hdlc) as hdlc,sum(jhxhlc) as jhxhlc,sum(sjwfxjlc) as sjwfxjlc from T_HZ_XHLC where xhsj >= '"+getDateToString(getYearFirst(reportDate))
		+"' and xhsj <= '"+getDateToString(getYearLast(reportDate))+"' group by sgcz,hdmc";
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("sgcz",StandardBasicTypes.STRING)
				.addScalar("xhcs",StandardBasicTypes.INTEGER)
				.addScalar("sflfxh",StandardBasicTypes.INTEGER)
				.addScalar("hdmc",StandardBasicTypes.STRING)
				.addScalar("hdlc",StandardBasicTypes.DOUBLE)
				.addScalar("jhxhlc",StandardBasicTypes.DOUBLE)
				.addScalar("sjwfxjlc",StandardBasicTypes.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(T_HZ_XHLC.class));
		return query.list();
	}
	
	/**
	 * 航政巡航费用
	 * @param reportDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T_HZ_XHFY> getCruiseCostReports(String reportDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				T_HZ_XHFY.class);
		crit.add(Restrictions.eq("xhsj", reportDate));
		crit.addOrder(Order.asc("sgcz"));
		return crit.list();
	}
	
	/**
	 * 航道通航保证情况
	 * @param reportDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T_HZ_THBZQK> getChannelStateReports(String reportDate) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				T_HZ_THBZQK.class);
		crit.add(Restrictions.eq("tjsj", reportDate));
		crit.addOrder(Order.asc("hdmc"));
		return crit.list();
	}
	
	/**
	 * 需要特殊赋值的表
	 */
	public void setTableValueByParam(String dataTableName, String fieldName, String fieldValue, Map<String, String> primaryKeys){
		try{
			if(dataTableName.equals("T_HZ_XHFY")){
				if((fieldName.equals("XHSJ")||fieldName.equals("SGCZ")||fieldName.equals("HS"))){
					setXHFYValueByParam(fieldName, fieldValue, primaryKeys);
				}
			}else if(dataTableName.equals("T_HZ_XHJH")){
				if(fieldName.equals("XHRQ")){
					setXHJHValueByParam(fieldName, fieldValue, primaryKeys);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setXHJHValueByParam(String fieldName, String fieldValue, Map<String, String> primaryKeys){
		long id = 0;
		for (String value : primaryKeys.values()) {
			id = Long.valueOf(value);
		}
		T_HZ_XHJH xhjh = (T_HZ_XHJH) sessionFactory.getCurrentSession().get(T_HZ_XHJH.class,id);
		xhjh.setXhsj(Integer.valueOf(fieldValue.substring(fieldValue.lastIndexOf("-")+1)));
		sessionFactory.getCurrentSession().update(xhjh);
	}
	/**
	 * 设置巡航费用值
	 */
	private void setXHFYValueByParam(String fieldName, String fieldValue, Map<String, String> primaryKeys){
		long id = 0;
		for (String value : primaryKeys.values()) {
			id = Long.valueOf(value);
		}
		T_HZ_XHFY xhfy = (T_HZ_XHFY) sessionFactory.getCurrentSession().get(T_HZ_XHFY.class,id);
		
		if(fieldName.equals("XHSJ")){//巡航时间
			if(xhfy.getSgcz()!=null&&xhfy.getSgcz()!=""){
				SimpleDateFormat df=new SimpleDateFormat("yyyy");
				Date date = null;
				try {
					date = df.parse(fieldValue);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				getCruiseMileageByParam(xhfy,date,xhfy.getSgcz());
				
			}
		}else if(fieldName.equals("SGCZ")){//巡航车船
			if(xhfy.getXhsj()!=null&&xhfy.getXhsj()!=""){
				SimpleDateFormat df=new SimpleDateFormat("yyyy");
				Date date = null;
				try {
					date = df.parse(xhfy.getXhsj());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				getCruiseMileageByParam(xhfy,date, fieldValue);
			}
		}else if(fieldName.equals("HS")){//航速
			if(xhfy.getSjshlc()!=0){
				xhfy.setSjxhsj(xhfy.getSjshlc()/xhfy.getHs());
			}
		}
		sessionFactory.getCurrentSession().update(xhfy);
	}
	
	private void getCruiseMileageByParam(T_HZ_XHFY xhfy,Date date,String sgcz) {
		
		String sql = " select sum(xhcs) as xhcs,sum(sflfxh) as sflfxh,sum(hdlc) as hdlc,sum(jhxhlc) as jhxhlc,sum(sjwfxjlc) as sjwfxjlc from T_HZ_XHLC where xhsj >= '"+getDateToString(getYearFirst(date))
		+"' and xhsj <= '"+getDateToString(getYearLast(date))+"' and sgcz = '"+sgcz+"'";
		Query query=sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("xhcs",StandardBasicTypes.INTEGER)
				.addScalar("sflfxh",StandardBasicTypes.INTEGER)
				.addScalar("hdlc",StandardBasicTypes.DOUBLE)
				.addScalar("jhxhlc",StandardBasicTypes.DOUBLE)
				.addScalar("sjwfxjlc",StandardBasicTypes.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(T_HZ_XHLC.class));
		T_HZ_XHLC xhlc = (T_HZ_XHLC) query.uniqueResult();
		if(xhlc.getSflfxh()>0){
			xhfy.setSjxhsx(2.0);//实际巡航系数
		}else{
			xhfy.setSjxhsx(1.0);//实际巡航系数
		}
		xhfy.setJhxhlc(xhlc.getJhxhlc());
		xhfy.setSjshlc(xhfy.getJhxhlc()*xhfy.getSjxhsx());
		if(xhfy.getHs()!=0){
			xhfy.setSjxhsj(xhfy.getSjshlc()/xhfy.getHs());
		}
	}
	
	
	private  Date getFirstday(Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,   1);

        return calendar.getTime();
    }
	private  Date getLastday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,   1);
        calendar.add(Calendar.MONTH,   1);
        calendar.add(Calendar.DAY_OF_MONTH,   -1);
        return calendar.getTime();
    }
	
	/**
     * 获取某年第一天日期
     * @return Date
     */
	private Date getYearFirst(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }
     
    /**
     * 获取某年最后一天日期
     * @return Date
     */
	private Date getYearLast(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
         
        return currYearLast;
    }
    
	private String getDateToString(Date date){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

}
