<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.chaos.roadbridge.model.DataTable" %>
<%@ attribute name="parentPrimaryKeys" description="父表主键数据" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/functions" prefix="wfn" %>
<p class="validateTips">下列字段必须填写</p>
<form action="addData.action">
	<input type="hidden" name="dataTableName" value="${dataTable.name}"/>
	<c:set var="parentTable" value="${dataTable.parent}" />
	<c:forEach items="${parentPrimaryKeys}" var="parentPkEntry">
		<input type="hidden" name="values.${parentPkEntry.key}" value="${parentPkEntry.value}" />
	</c:forEach>
	<ul>
		<c:forEach items="${dataTable.fields}" var="field">
			<c:if test="${not field.nullable and not wfn:contains(parentTable.primaryKeyNames, field.name) and not field.generatedByDatabase}">
				<li>
					<label for="values.${field.name}">${field.label}</label>
					<c:choose>
						<c:when test="${field.type == 'FILE'}">
							不支持文件类型内容
						</c:when>
						<c:when test="${field.type == 'NUMBER'}">
							<input name="values.${field.name}" value="${value}" ${readonly} />
							<c:if test="${not empty field.unit}">（${field.unit}）</c:if>
						</c:when>
						<c:when test="${field.type == 'DATE'}">
							<input class="Wdate" name="values.${field.name}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
						</c:when>
						<c:when test="${field.type == 'DATETIME'}">
							<input class="Wdate" name="values.${field.name}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
						</c:when>
						<c:when test="${field.type == 'CODE'}">
							<select name="values.${field.name}">
								<c:forEach items="${field.codeTable.codes}" var="code">
									<option value="${code.key}">${code.value.meaning}</option>
								</c:forEach>
							</select>
							<c:if test="${not empty field.unit}">（${field.unit}）</c:if>
						</c:when>
						<c:when test="${field.type == 'TEXT'}">
							<textarea name="values.${field.name}" rows="3"></textarea>
						</c:when>
						<c:otherwise>
							<input name="values.${field.name}" maxlength="${field.length}" value="${value}" ${readonly} />
							<c:if test="${not empty field.unit}">（${field.unit}）</c:if>
						</c:otherwise>
					</c:choose>
				</li>
			</c:if>
		</c:forEach>
	</ul>
</form>
