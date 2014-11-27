<%@ tag display-name="list" description="普通列表" body-content="scriptless" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="items" description="要列出的对象集" required="true" type="java.lang.Object" %>
<%@ attribute name="id" description="HTML标签中的id属性"%>
<%@ attribute name="label" description="列表中实体名称，用于总数描述和table的summary"%>
<%@ attribute name="functionUrl" description="功能键地址"%>
<%@ attribute name="functionLabel" description="功能名称"%>
<%@ attribute name="tableHead" fragment="true" %>
<%@ attribute name="tableBody" fragment="true" %>
<%@ variable name-given="entry" %>
<c:set var="idDefination"><c:if test="${not empty id}">id="${id}"</c:if></c:set>
<div ${idDefination} class="listTable">
	<c:if test="${not empty label}">
		<div class="total">${label} 共${fn:length(items)}条</div>
	</c:if>
	<c:if test="${not empty functionLabel}">
		<div class="function"><form action="${functionUrl}"><button type="submit">${functionLabel}</button></form></div>
	</c:if>
	<table class="list" summary="${label}列表">
		<thead>
			<tr>
				<jsp:invoke fragment="tableHead" />
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${items}" var="entry" varStatus="status">
				<c:set var="trclass">
					<c:if test="${status.count % 2 == 1}">odd</c:if>
					<c:if test="${status.count % 2 == 0}">even</c:if>
				</c:set>
				<tr class="${trclass}">
					<jsp:invoke fragment="tableBody" />
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
