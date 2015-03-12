<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="dataTableName" description="数据表名称" required="true" type="java.lang.String" %>
<%@ attribute name="catalog" description="栏目名" required="true" type="java.lang.String" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring"
	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper"
	scope="request" />
<c:set target="${dataTableHelper}" property="dataTableName" value="" />
<c:set target="${dataTableHelper}" property="catalog" value="${catalog}" />
<c:set var="dataTableList" value="${dataTableHelper.dataTableList}"  scope="request" />
<div class="dataTableSearch content">
	<select class="dataT" name=dataTableName>
		<c:forEach items="${dataTableList}" var="dataTable">
			<c:choose>
				<c:when test="${dataTable.name==dataTableName}">
					<option value="${dataTable.name}" selected="selected">${dataTable.label}</option>
				</c:when>
				<c:otherwise>
					<option value="${dataTable.name}" >${dataTable.label}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<div class="normalSearch content"></div>
</div>
