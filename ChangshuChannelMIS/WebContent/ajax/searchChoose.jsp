<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/search" prefix="search" %>
<script type="text/javascript" src="js/advancedSearch.js"></script>
<script type="text/javascript">
	$(function(){
		 $("button").click(function() {
		    	$(".tabs").hide();
		    	$(".tab_content:first").show();
		    });
	});
</script>
<li class="catalog">
	<div class="title">常用查询</div>
	<search:normal dataTableName="${param.dataTableName}" />
</li>
<li class="catalog">
	<div class="title">高级查询</div>
	<search:advanced dataTableName="${param.dataTableName}" />
</li>