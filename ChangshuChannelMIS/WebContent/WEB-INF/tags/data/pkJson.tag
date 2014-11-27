<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.chaos.roadbridge.model.DataTable" %>
<%@ attribute name="data" description="数据" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
({
<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
	<c:if test="${not status.first}">,</c:if>${pk.name}: '${data[pk.name]}'
</c:forEach>
})
