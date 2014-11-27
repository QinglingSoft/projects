<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="codeTableHelper" beanName="codeTableHelper" scope="request"/>
<ul class="content">
	<li class="catalog">
		<div class="title">按跨径</div>
		<ul>
			<%-- 代码表：公路桥梁按跨径分类代码 --%>
			<c:set target="${codeTableHelper}" property="codeTableName" value="C_AKJF" />
			<c:forEach items="${codeTableHelper.codeTable.codes}" var="codeEntry">
				<c:set var="code" value="${codeEntry.value}" />
				<c:url var="searchUrl" value="search.action">
					<%-- 字段：按跨径分 --%>
					<c:param name="condition.AKJF" value="${code.value}" />
				</c:url>
				<li class="link"><a href="${searchUrl}">${code.meaning}</a></li>
			</c:forEach>
		</ul>
	</li>
	<li class="catalog">
		<div class="title">按评定等级</div>
		<ul>
			<%-- 代码表：桥梁技术状况评定代码 --%>
			<c:set target="${codeTableHelper}" property="codeTableName" value="C_JSZKPD" />
			<c:forEach items="${codeTableHelper.codeTable.codes}" var="codeEntry">
				<c:set var="code" value="${codeEntry.value}" />
				<c:url var="searchUrl" value="search.action">
					<%-- 字段：技术检查评定等级 --%>
					<c:param name="condition.JSPDDJ" value="${code.value}" />
				</c:url>
				<li class="link"><a href="${searchUrl}">${code.meaning}</a></li>
			</c:forEach>
		</ul>
	</li>
	<li class="link"><a href="javascript:void(0)">病害</a></li>
</ul>