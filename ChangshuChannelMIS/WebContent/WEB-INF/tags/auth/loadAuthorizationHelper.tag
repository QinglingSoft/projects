<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.qinglingsoft.changshuchannel.model.DataTable" %>
<%@ attribute name="data" description="记录数据" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>

<spring:useBean id="authorizationHelper" beanName="authorizationHelper" scope="request" />
<c:set target="${authorizationHelper}" property="user" value="${sessionScope.loginUser}" />
<c:set target="${authorizationHelper}" property="dataTableName" value="${dataTable.name}" />
<c:set var="recordPkValues" value="${authorizationHelper.primaryKeyValues}" />
<c:forEach items="${dataTable.primaryKeyNames}" var="pkName">
	<c:set target="${recordPkValues}" property="${pkName}" value="${data[pkName]}" />
</c:forEach>

