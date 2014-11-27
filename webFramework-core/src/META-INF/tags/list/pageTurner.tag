<%@ tag display-name="pageTurner" description="翻页器" body-content="empty" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="pageHelper" description="包含页信息的助手对象" required="true" type="com.qinglingsoft.webframework.helper.PageHelper" %>
<%@ attribute name="linkParams" description="要传递的页面参数" required="false" type="java.util.Map" %>
<%@ attribute name="url" description="目标链接"%>
<%@ attribute name="styleClass" description="HTML标签中的class属性"%>
<%@ attribute name="id" description="HTML标签中的id属性"%>
<%@ attribute name="prevPageImage" description="“上一页”链接图标"%>
<%@ attribute name="nextPageImage" description="“下一页”链接图标"%>
<c:set var="idDefination"><c:if test="${not empty id}">id="${id}"</c:if></c:set>
<c:set var="classDefination"><c:if test="${not empty styleClass}">class="${styleClass}"</c:if></c:set>
<c:if test="${empty prevPageImage}">
	<c:url var="prevPageImage" value="/images/prevPage.gif" />
</c:if>
<c:if test="${empty nextPageImage}">
	<c:url var="nextPageImage" value="/images/nextPage.gif" />
</c:if>
<div ${idDefination} ${classDefination}>
<table>
<tr>
	<td class="prevPage">
		<c:if test="${not pageHelper.firstPage}">
			<c:url var="prevPageUrl" value="${url}">
				<c:forEach items="${linkParams}" var="linkParam">
					<c:param name="${linkParam.key}" value="${linkParam.value}"/>
				</c:forEach>
				<c:param name="currentPage" value="${pageHelper.currentPage - 1}" />
			</c:url>
			<a href="${prevPageUrl}"><img src="${prevPageImage}" alt="上一页"/></a>
		</c:if>
	</td>
	<td class="pageNumber">
		<c:if test="${pageHelper.morePages}">
			<form name="userListPageForm" action="${url}">
				<c:forEach items="${linkParams}" var="linkParam">
					<input type="hidden" name="${linkParam.key}" value="${linkParam.value}"/>
				</c:forEach>
				<select
					name="currentPage" class="pageNumberSelect" onchange="this.form.submit()">
					<c:forEach begin="1" end="${pageHelper.totalPages}" var="pageNumber">
						<c:set var="selected"><c:if test="${pageNumber - 1 == pageHelper.currentPage}">selected="selected"</c:if></c:set>
						<option value="${pageNumber - 1}" ${selected}>${pageNumber}</option>
					</c:forEach>
				</select>
				/${pageHelper.totalPages}页
			</form>
		</c:if>
	</td>
	<td class="nextPage">
		<c:if test="${not pageHelper.lastPage}">
			<c:url var="nextPageUrl" value="${url}">
				<c:forEach items="${linkParams}" var="linkParam">
					<c:param name="${linkParam.key}" value="${linkParam.value}"/>
				</c:forEach>
				<c:param name="currentPage" value="${pageHelper.currentPage + 1}" />
			</c:url>
			<a href="${nextPageUrl}"><img src="${nextPageImage}" alt="下一页"/></a>
		</c:if>
	</td>
</tr>
</table>
</div>
