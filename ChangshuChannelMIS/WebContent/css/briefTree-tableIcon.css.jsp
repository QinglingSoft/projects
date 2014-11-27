@CHARSET "UTF-8";
<%@ page language="java" contentType="text/css; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" />
<c:forEach items="${dataTableHelper.all}" var="dataTable">
ul.briefTree li[dataTableName='${dataTable.name}'] {
	background-image: url('../images/tableIcon/${dataTable.name}.png');
}
</c:forEach>