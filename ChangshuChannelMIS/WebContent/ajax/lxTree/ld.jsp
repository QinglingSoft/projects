<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataSource" beanName="dataSource" />
<sql:setDataSource dataSource="${dataSource}"/>
<sql:query var="result">
	select lxbm, ldxlh, qdmc, zdmc, qdzh, zdzh from T_LD where lxbm=? order by qdzh
	<sql:param value="${param.lxbm}"/>
</sql:query>
<c:forEach items="${result.rows}" var="row">
	<sql:query var="countResult">
		select count(*) as count from T_QL where lxbm=? and ldxlh=?
		<sql:param value="${row.lxbm}"/>
		<sql:param value="${row.ldxlh}"/>
	</sql:query>
	<c:set var="emptySign"></c:set>
	<c:forEach items="${countResult.rows}" var="countRow">
		<c:if test="${countRow.count == 0}"><c:set var="emptySign">empty</c:set></c:if>
	</c:forEach>
	<li class="catalog ${emptySign}" lxbm="${row.lxbm}" ldxlh="${row.ldxlh}"><div class="title">[${row.ldxlh}] ${row.qdmc}—${row.zdmc}(${row.qdzh}—${row.zdzh})</div></li>
</c:forEach>
