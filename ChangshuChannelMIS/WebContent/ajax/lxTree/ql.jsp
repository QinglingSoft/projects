<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataSource" beanName="dataSource" />
<sql:setDataSource dataSource="${dataSource}"/>
<sql:query var="result">
	select qlbm, qlbh, qlmc, centerzh from T_QL where lxbm=? and ldxlh=? order by centerzh
	<sql:param value="${param.lxbm}"/>
	<sql:param value="${param.ldxlh}"/>
</sql:query>
<c:forEach items="${result.rows}" var="row">
	<li class="link" qlbm="${row.qlbm}">[${row.qlbh}] ${row.qlmc}(${row.centerzh})</li>
</c:forEach>
