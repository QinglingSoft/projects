<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="mapObject" description="数据" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
({
<c:forEach items="${mapObject}" var="entry" varStatus="status">
	<c:if test="${not status.first}">,</c:if>${entry.key}: '${entry.value}'
</c:forEach>
})
