<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.qinglingsoft.changshuchannel.model.DataTable" %>
<%@ attribute name="data" description="记录数据" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%@ taglib tagdir="/WEB-INF/tags/auth" prefix="auth" %>

<auth:loadAuthorizationHelper dataTable="${dataTable}" data="${data}" />
<div class="statusIcon"></div>
<table class="tableData">
	<c:forEach items="${dataTable.fields}" var="field">
		<%-- 渲染可见字段 --%>
		<c:if test="${field.visible}">
			<tr>
				<th>${field.label}<c:if test="${not empty field.unit}">(${field.unit})</c:if></th>
				<td>
					<c:choose>
						<c:when test="${field.editable and authorizationHelper.hasPermission}">
							<%-- 有访问权限且字段可编辑的渲染方式，按字段类型区分对待 --%>
							<c:choose>
								<c:when test="${field.type == 'FILE'}">
									<div class="preview">
										<data:fileTypeFieldPreview data="${data}" dataTable="${dataTable}" field="${field}"/>
									</div>
									<form class="fileTypeFieldForm" action="updateFileTypeField.action" method="post" enctype="multipart/form-data">
						                <input type="hidden" name="dataTableName" value="${dataTable.name}" />
						                <input type="hidden" name="fieldName" value="${field.name}" />
										<c:forEach items="${dataTable.primaryKeys}" var="pk">
											<input type="hidden" name="primaryKeys.${pk.name}" value="${data[pk.name]}" />
										</c:forEach>
										<input name="upload" type="file" />
										<button type="submit">√</button>
						            </form>
								</c:when>
								<c:when test="${field.type == 'NUMBER'}">
									<fmt:formatNumber var="fieldValue" value="${data[field.name]}" maxFractionDigits="${field.fractionDigits}" groupingUsed="false" />
									<input type="hidden" name="old.${field.name}" value="${fieldValue}"/>
									<input type="text" name="${field.name}" value="${fieldValue}"/>
								</c:when>
								<c:when test="${field.type == 'DATE'}">
									<fmt:formatDate var="fieldValue" value="${data[field.name]}" type="DATE"/>
									<input type="hidden" value="${fieldValue}"/>
									<input class="Wdate" type="text" name="${field.name}" value="${fieldValue}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
									<button>√</button>
								</c:when>
								<c:when test="${field.type == 'DATETIME'}">
									<fmt:formatDate var="fieldValue" value="${data[field.name]}" type="BOTH"/>
									<input type="hidden" value="${fieldValue}"/>
									<input class="Wdate" type="text" name="${field.name}" value="${fieldValue}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
									<button>√</button>
								</c:when>
								<c:when test="${field.type == 'CODE'}">
									<input type="hidden" name="old.${field.name}" value="${fn:trim(data[field.name])}"/>
									<select name="${field.name}">
										<c:if test="${field.nullable}">
											<option class="null" value="">--- 请选择 ---</option>
										</c:if>
										<c:forEach items="${field.codeTable.codes}" var="code">
											<c:set var="selected"><c:if test="${fn:trim(data[field.name]) == code.key}">selected="selected"</c:if></c:set>
											<option value="${code.key}" ${selected}>${code.key}. ${code.value.meaning}</option>
										</c:forEach>
									</select>
								</c:when>
								<c:when test="${field.type == 'TEXT'}">
									<c:set var="escapedValue"><c:out value="${data[field.name]}" escapeXml="true" /></c:set>
									<input type="hidden" name="old.${field.name}" value="${escapedValue}"/>
									<textarea name="${field.name}" cols="40" rows="${field.length / 100 > 8 ? 8 : field.length / 100}">${escapedValue}</textarea>
								</c:when>
								<c:otherwise>
									<c:set var="escapedValue"><c:out value="${data[field.name]}" escapeXml="true" /></c:set>
									<input type="hidden" name="old.${field.name}" value="${escapedValue}"/>
									<input type="text" name="${field.name}" value="${escapedValue}" maxlength="${field.length}" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<%-- 不可编辑字段渲染方式，按字段类型区分对待 --%>
							<c:choose>
								<c:when test="${field.type == 'FILE'}">
									<div class="preview">
										<data:fileTypeFieldPreview data="${data}" dataTable="${dataTable}" field="${field}"/>
									</div>
								</c:when>
								<c:when test="${field.type == 'NUMBER'}">
									<fmt:formatNumber value="${data[field.name]}" maxFractionDigits="${field.fractionDigits}"/>
								</c:when>
								<c:when test="${field.type == 'DATE'}">
									<fmt:formatDate value="${data[field.name]}" type="DATE"/>
								</c:when>
								<c:when test="${field.type == 'DATETIME'}">
									<fmt:formatDate value="${data[field.name]}" type="BOTH"/>
								</c:when>
								<c:when test="${field.type == 'CODE'}">
									<c:set var="code" value="${field.codeTable.codes[data[field.name]]}"/>
									<c:if test="${not empty code}">${code.value}. ${code.meaning}</c:if>
								</c:when>
								<c:otherwise>
									<c:set var="escapedValue"><c:out value="${data[field.name]}" escapeXml="true" /></c:set>
									${escapedValue}
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:if>
	</c:forEach>
</table>