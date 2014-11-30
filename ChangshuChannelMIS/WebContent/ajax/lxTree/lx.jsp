<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataSource" beanName="dataSource" />
<sql:setDataSource dataSource="${dataSource}"/>
<sql:query var="result">
	select distinct lxbm, lxmc from T_LD where lxbm like ?
	<sql:param value="${param.lxbmPrefix}%"/>
</sql:query>
<c:forEach items="${result.rows}" var="row">
	<li class="catalog" lxbm="${row.lxbm}"><div class="title">${row.lxbm}[${row.lxmc}]</div></li>
</c:forEach>
