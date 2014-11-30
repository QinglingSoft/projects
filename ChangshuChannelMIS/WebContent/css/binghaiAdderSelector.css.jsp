@CHARSET "UTF-8";
<%@ page language="java" contentType="text/css; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataSource" beanName="dataSource" />
<sql:setDataSource dataSource="${dataSource}"/>
<c:if test="${not empty param.SBJGXS}">
<sql:query var="result">
	select binghaiTable from SBJGXS_binghaiTable where SBJGXS=?
	<sql:param value="${param.SBJGXS}"/>
</sql:query>
<c:forEach items="${result.rows}" var="row">
	<c:set var="matchBinghaiTable" value="${row.binghaiTable}" />
</c:forEach>
</c:if>
<c:forEach begin="1" end="9" var="binghaiTableNumber">
<c:set var="binghaiTable" value="T_QLBH${binghaiTableNumber}" />
<c:choose>
<c:when test="${binghaiTable == matchBinghaiTable}"></c:when>
<c:otherwise>
#rightClickMenu li.adder[dataTableName='${binghaiTable}'] {
	display: none;
}
</c:otherwise>
</c:choose>
</c:forEach>
