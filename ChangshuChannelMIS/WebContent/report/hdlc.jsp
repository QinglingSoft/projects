<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" />	
<title>航道里程</title>
<style type="text/css">
	table {
		border-collapse: collapse;
		margin: 2em;
		border: solid black 1px;
	}
	table > caption {
		font-size: larger;
		font-weight: bolder;
	}
	td, th {
		border: solid black 1px;
		padding: .2em 1em;
	}

	td {
		text-align: right;
		vertical-align: middle;
		height: 1.5em;
	}
	th {
		white-space: nowrap;
	}
	tbody tr:hover {
		background-color: #e8f2fe;
	}
	#exportButton {
		position: absolute;
		top: .5em;
		left: .5em;
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
	<caption>航道里程</caption>
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
<button id="exportButton" onclick="window.location.href = 'hdlc2.jsp'">导出Excel</button>
</center>
</body>
</html>