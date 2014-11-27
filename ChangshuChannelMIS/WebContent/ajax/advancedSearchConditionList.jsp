<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
	<c:set var="conditionListAttrName" value="search.${searchName}"/>
	<c:forEach items="${sessionScope[conditionListAttrName]}" var="condition" varStatus="status">
		<c:url var="deleteUrl" value="deleteAdvancedSearchCondition">
			<c:param name="searchName" value="${searchName}"/>
			<c:param name="conditionIndex" value="${status.index}"/>
		</c:url>
		<li>${condition} <a href="${deleteUrl}">【删除】</a></li>
	</c:forEach>
</ul>