<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/search/tree" prefix="searchTree" %>
<ul class="content">
	<li class="catalog">
		<div class="title">按跨径浏览</div>
		<searchTree:code dataTableName="T_QL" fieldName="AKJF" />
	</li>
	<li class="catalog">
		<div class="title">按评定等级浏览</div>
		<searchTree:childTableCode dataTableName="T_QL" childTableName="T_QLAQZKPD" fieldName="qlsygnpd" />
	</li>
	<li class="catalog">
		<div class="title">按病害类型浏览</div>
		<searchTree:childRecordExists dataTableName="T_QL" catalog="病害记录" />
	</li>
</ul>