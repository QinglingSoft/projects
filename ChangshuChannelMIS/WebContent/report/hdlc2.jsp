<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@page import="java.util.Calendar"%>  
<%  
    response.setCharacterEncoding("UTF-8");  
    response.setContentType("application/vnd.ms-excel");  
    response.setHeader("Content-Disposition", "attachment;filename=\""  
            + new String( "航道里程表".getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");     
%> 
<spring:useBean id="report" beanName="reportHelper" />
<jsp:setProperty name="report" property="*" />
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name></x:Name><x:WorksheetOptions><x:Selected/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
<style type="text/css">
	td{
		mso-number-format:'\@'; 
	}
	table.briefList {
		margin-bottom:1em;
	}
	table.briefList th {
		text-align: center;
	}
	table.headerInfo, table.footerInfo {
		min-width: 100%;
		border-collapse: collapse;
		border: none;
		margin-top: 1em;
		margin-left:1em;
	}
	table.headerInfo th,table.footerInfo th {
		text-align: left;
		padding:2px;
	}
	table.headerInfo th.center {
		text-align: center;
	}
	table.footerInfo {
		margin-bottom: 4em;
	}
</style>
</head>
<body>
<spring:useBean id="dataSource" beanName="dataSource" />
<sql:setDataSource dataSource="${dataSource}"/>
<sql:query var="result">
	select 项目,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航,id from (
select id,项目,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航 from (
select '01' as id,'等级航道合计' as 项目,sum(hdlc) as "里程(公里)",sum(case when hdxzdj='1' then hdlc end) as 一级,sum(case when hdxzdj='3' then hdlc end) as 二级,sum(case when hdxzdj='3' then hdlc end) as 三级,sum(case when hdxzdj='4' then hdlc end) as 四级,sum(case when hdxzdj='5' then hdlc end) as 五级,sum(case when hdxzdj='6' then hdlc end) as 六级,sum(case when hdxzdj='7' then hdlc end) as 七级,sum(case when hdxzdj='8' or hdxzdj is null and hdxzdj='' then hdlc end) as 等外 from t_hdhd  where hdxzdj is not null and hdxzdj<>'' and hdxzdj<>'8') as t1 left join (select '01' as id1,count(SFAH) as 共计,sum(case when SFAH='1' then 1 end) as 其中碍航 from t_ghql as t left join t_hdhd on t.hdhdid=t_hdhd.hdhdid where hdxzdj is not null and hdxzdj<>'' and hdxzdj<>'8'  ) as t2 on t1.id=t2.id1  
union
select '02' as id,hdmc as 项目,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航 from (
select hddm,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航 from ( 
select hddm,'等级航道合计' as 项目,sum(hdlc) as "里程(公里)",sum(case when hdxzdj='1' then hdlc end) as 一级,sum(case when hdxzdj='3' then hdlc end) as 二级,sum(case when hdxzdj='3' then hdlc end) as 三级,sum(case when hdxzdj='4' then hdlc end) as 四级,sum(case when hdxzdj='5' then hdlc end) as 五级,sum(case when hdxzdj='6' then hdlc end) as 六级,sum(case when hdxzdj='7' then hdlc end) as 七级,sum(case when hdxzdj='8' or hdxzdj is null and hdxzdj='' then hdlc end) as 等外 from t_hdhd  where hdxzdj is not null and hdxzdj<>'' and hdxzdj<>'8' group by hddm) as t1 left join (select hddm1,count(sfah) as 共计,sum(case when SFAH='1' then 1 end) as 其中碍航 from (
select  hddm as hddm1,SFAH from t_ghql as t  left join t_hdhd on t.hdhdid=t_hdhd.hdhdid where hdxzdj is not null and hdxzdj<>'' and hdxzdj<>'8') as tt group by hddm1 ) as t2 on t1.hddm=t2.hddm1) as t1 left join (select hddm,hdmc from t_hd) as t2 on t1.hddm=t2.hddm
union

select id,项目,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航 from (
select '11' as id,'等外航道合计' as 项目,sum(hdlc) as "里程(公里)",sum(case when hdxzdj='1' then hdlc end) as 一级,sum(case when hdxzdj='3' then hdlc end) as 二级,sum(case when hdxzdj='3' then hdlc end) as 三级,sum(case when hdxzdj='4' then hdlc end) as 四级,sum(case when hdxzdj='5' then hdlc end) as 五级,sum(case when hdxzdj='6' then hdlc end) as 六级,sum(case when hdxzdj='7' then hdlc end) as 七级,sum(case when hdxzdj='8' or hdxzdj is null and hdxzdj='' then hdlc end) as 等外 from t_hdhd  where hdxzdj is null or hdxzdj='' or hdxzdj='8') as t1 left join (select '11' as id1,count(SFAH) as 共计,sum(case when SFAH='1' then 1 end) as 其中碍航 from t_ghql as t left join t_hdhd on t.hdhdid=t_hdhd.hdhdid where hdxzdj   is null or hdxzdj='' or hdxzdj='8' ) as t2 on t1.id=t2.id1  
union
select '12' as id,hdmc as 项目,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航 from (
select hddm,"里程(公里)",一级,二级,三级,四级,五级,六级,七级,等外,共计,其中碍航 from ( 
select hddm,'等级航道合计' as 项目,sum(hdlc) as "里程(公里)",sum(case when hdxzdj='1' then hdlc end) as 一级,sum(case when hdxzdj='3' then hdlc end) as 二级,sum(case when hdxzdj='3' then hdlc end) as 三级,sum(case when hdxzdj='4' then hdlc end) as 四级,sum(case when hdxzdj='5' then hdlc end) as 五级,sum(case when hdxzdj='6' then hdlc end) as 六级,sum(case when hdxzdj='7' then hdlc end) as 七级,sum(case when hdxzdj='8' or hdxzdj is null and hdxzdj='' then hdlc end) as 等外 from t_hdhd  where hdxzdj is null or hdxzdj='' or hdxzdj='8' group by hddm) as t1 left join (select hddm1,count(sfah) as 共计,sum(case when SFAH='1' then 1 end) as 其中碍航 from (
select  hddm as hddm1,SFAH from t_ghql as t  left join t_hdhd on t.hdhdid=t_hdhd.hdhdid where hdxzdj   is null or hdxzdj='' or hdxzdj='8') as tt group by hddm1 ) as t2 on t1.hddm=t2.hddm1) as t1 left join (select hddm,hdmc from t_hd) as t2 on t1.hddm=t2.hddm)as ttt order by id
</sql:query>
<center>
<table>
	<thead class="ui-widget-header">
		<tr>
			<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
				<c:if test="${not status.last}">
					<c:choose>
						<c:when test="${status.index < 2}">
							<th rowspan="2">${columnName}</th>
						</c:when>
						<c:otherwise>
							<c:if test="${status.index == 2}"><th colspan="8">达 到 等 级 航 道 里 程 (公 里)</th></c:if>
							<c:if test="${status.index == 3}"><th colspan="2">跨河桥梁(座)</th></c:if>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			<th rowspan="2">备注</th>
		</tr>
		<tr>
			<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
				<c:if test="${not status.last}">
					<c:choose>
						<c:when test="${status.index > 1}">
							<th>${columnName}</th>
						</c:when>
					</c:choose>
				</c:if>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${result.rows}" var="row" varStatus="status">
			<tr>
				<c:forEach items="${result.columnNames}" var="columnName" varStatus="status">
					<c:if test="${not status.last}">
						<c:choose>
							<c:when test="${status.index < 2}">
								<td>${row[columnName]}</td>
							</c:when>
							<c:otherwise>
								<th>${row[columnName]}</th>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</tr>
		</c:forEach>
	</tbody>
</table>
</center>
</body>
</html>