<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="dataTableName" description="数据表名称" required="true" type="java.lang.String" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<c:set target="${dataTableHelper}" property="dataTableName" value="${dataTableName}" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" scope="request" />
<div class="normalSearch content">
<form action="searchGeneral.action">
<input type="hidden" name="dataTableName" value="${dataTableName}" />
<ul>
	<c:forEach items="${dataTable.searchConditionFields}" var="field">
		<li>
		<label>${field.label}<c:if test="${not empty field.unit}">(${field.unit})</c:if></label>
		<c:choose>
			<c:when test="${field.type == 'NUMBER'}">
				<input type="text" name="conditions.${field.name}_GE" title="${field.label}下限"/>
				<input type="text" name="conditions.${field.name}_LE" title="${field.label}上限"/>
			</c:when>
			<c:when test="${field.type == 'DATE' || field.type == 'DATETIME'}">
				<input class="Wdate" type="text" name="conditions.${field.name}_GE" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"  title="${field.label}下限" />
				<input class="Wdate" type="text" name="conditions.${field.name}_LE" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"  title="${field.label}上限" />
			</c:when>
			<c:when test="${field.type == 'CODE'}">
				<div class="codeList" title="${field.label}范围">
					<ul>
						<c:forEach items="${field.codeTable.codes}" var="code">
							<li><input type="checkbox" name="conditions.${field.name}_IN" value="${code.key}"/>${code.key}. ${code.value.meaning}</li>
						</c:forEach>
					</ul>
				</div>
			</c:when>
			<c:when test="${field.type == 'TEXT' || field.type == 'STRING'}">
				<input type="text" name="conditions.${field.name}_LIKE"  title="${field.label}包含"/>
			</c:when>
		</c:choose>
		</li>
	</c:forEach>
	</ul>
	<button>查询</button>
</form>
</div>
<div class="clear"></div>
