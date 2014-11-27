<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="mapLayerHelper" beanName="mapLayerHelper" scope="application" />
<form class="content" id="mapLayerSelectForm">
	<ul>
		<c:forEach items="${mapLayerHelper.all}" var="mapLayer">
			<li><input type="checkbox" name="layers" value="${mapLayer.id}" />${mapLayer.name}</li>
		</c:forEach>
	</ul>
</form>
