<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="lxbmPrefix" description="路线编码前缀" required="true" type="java.lang.String" %>
<%@ attribute name="itemLabel" description="列表项文字标签" required="true" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataSource" beanName="dataSource"></spring:useBean>
<sql:setDataSource dataSource="${dataSource}"/>
<sql:query var="result">
	select count(*) as count from T_LD where lxbm like ?
	<sql:param value="${lxbmPrefix}%"/>
</sql:query>
<c:forEach items="${result.rows}" var="row">
	<c:if test="${row.count == 0}"><c:set var="emptySign">empty</c:set></c:if>
</c:forEach>
<li class="catalog ${emptySign}" lxbmPrefix="${lxbmPrefix}"><div class="title">${itemLabel}</div></li>
