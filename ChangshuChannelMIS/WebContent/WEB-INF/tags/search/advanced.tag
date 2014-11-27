<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="dataTableName" description="数据表名称" required="true" type="java.lang.String" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring"
	prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper"
	scope="request" />
<c:set target="${dataTableHelper}" property="dataTableName" value="${dataTableName}" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}"
	scope="request" />
<div class="advancedSearch content">
	<form name="newConditionForm" action="addAdvancedSearchCondition.action">
		<input type="hidden" name="dataTableName" value="${dataTableName}"/>
		<input type="hidden" name="searchName" value="advanced" />
		<select class="field" name="fieldName">
			<option value="">---请选择字段---</option>
			<c:forEach items="${dataTable.fields}" var="field">
				<c:if test="${field.visible}">
					<option value="${field.name}">${field.label}<c:if test="${not empty field.unit}">(${field.unit})</c:if></option>
				</c:if>
			</c:forEach>
		</select>
		<select class="operator" name="operator">
		</select>
		<div class="valueInput"></div>
		<button>添加</button>
	</form>
	<form action="advancedSearch.action">
		<input type="hidden" name="dataTableName" value="${dataTableName}" />
		<input type="hidden" name="searchName" value="advanced" />
		<button>查询</button>
	</form>
	<div class="conditionList" title="当前条件"></div>
</div>
