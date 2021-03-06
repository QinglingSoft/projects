<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/lxTree" prefix="lxTree" %>
<%@ taglib tagdir="/WEB-INF/tags/search" prefix="search" %>
<%@ taglib tagdir="/WEB-INF/tags/map" prefix="map" %>
<jsp:useBean id="now" class="java.util.Date"/>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<c:set var="dataTableList" value="${dataTableHelper.all}"  scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>常熟市航道管理信息系统</title>
	<link href="css/default.css" rel="stylesheet" type="text/css" />
	<link href="css/mainFrame.css" rel="stylesheet" type="text/css" />
	<link href="css/normalSearch.css" rel="stylesheet" type="text/css" />
	<link href="css/advancedSearch.css" rel="stylesheet" type="text/css" />
	<link href="css/cupertino/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />	
	<link href="css/layout-default-latest.css" rel="stylesheet" type="text/css" />
	<link href="css/lxTree.css" rel="stylesheet" type="text/css" />
	<base target="workspace" />
 	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="js/jquery.layout.min.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/roadBridge-base.js"></script>
	<script type="text/javascript" src="js/searchChoose.js"></script>
	<style type="text/css">
	ul.tabs {
		margin: 0;
		padding: 0;
		float: left;
		list-style: none;
		height: 32px;
		border-bottom: 1px solid #999;
		border-left: 1px solid #999;
		width: 100%;
	}
	ul.tabs li {
		float: left;
		margin: 0;
		padding: 0;
		height: 31px;
		line-height: 31px;
		border: 1px solid #999;
		border-left: none;
		margin-bottom: -1px;
		background: #e0e0e0;
		overflow: hidden;
		position: relative;
	}
	ul.tabs li a {
		text-decoration: none;
		color: #000;
		display: block;
		font-size: 1.2em;
		padding: 0 20px;
		border: 1px solid #fff;
		outline: none;
	}
	ul.tabs li a:hover {
		background: #ccc;
	}	
	html ul.tabs li.active, html ul.tabs li.active a:hover  {
		background: #fff;
		border-bottom: 1px solid #fff;
	}
	.tab_content {
		border-top: none;
		clear: both;
		float: left; 
		width: 100%;
		height:100%; 
	}
	</style>
    <script type="text/javascript">
		$(function(){
			//主框架
			var $mainLayout = $("body").layout({
				north__resizeable: false,
				north__closable: false,
				north__slidable: false,
				east__resizeWhileDragging: true,
				east__onresize: function() { $("#menu").accordion("resize"); }
			});
			//菜单
			$("#menu").accordion({fillSpace: true});
			$("#menu li.catalog>.title").live("click", function(){
				$(this).parent().toggleClass("expanded");
			});
			
			//菜单图标
		    $("#menu ul > li.link").each(function(i){
		    	if(i>=25){
		    		$(this).css("background-image","url(images/menu/"+(i%25)+".png)");
		    	}else{
		    		$(this).css("background-image","url(images/menu/"+(i+1)+".png)");
		    	}
		           
			});
			
		});
		
		//显示服务器时间
		Date.prototype.toLocaleString = function() {
		  return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + "点" + this.getMinutes() + "分" + this.getSeconds() + "秒";
		 };
		roadBridge.clockShower = {
			timeAdjust: +"${now.time}" - new Date().getTime(),
			clockElement: null,
			timmer: null,
			run: function(clockElement) {
				this.clockElement = clockElement;
				this.timmer = window.setInterval("roadBridge.clockShower.showClock()", 1000);
			},
			showClock: function() {
				var serverTime = new Date((new Date()).getTime() + this.timeAdjust);
				this.clockElement.text(serverTime.toLocaleString());
			}
		};
		$(function() {
			roadBridge.clockShower.run($("#clock"));
		});

		//保持session
		function keepSession() {
			$.get("ajax/keepSession.jsp");
		}
		
		//延时加载地图，以使主框架大小可以调整完成
		$(function (){
			window.setTimeout("displayMap()", 2000);
		});

		function displayMap() {
			$("iframe[name=workspace]").attr("src", "testFrame.jsp");
		}
		
	</script>
	<script type="text/javascript" src="js/roadBridge-lxTree.js"></script>
</head>
<body>
	<div id="header" class="ui-layout-north ui-widget-header">
		<div class="title">常熟市航道管理信息系统</div>
		<div class="subtitle">Changshu Channel Management Information System</div>
		<div id="clock"></div>
		<div id="topLevelFunctions">
			<ul>
				<li><a href="testFrame.jsp">地图浏览</a></li>
				<li><a href="articleFrame.jsp">新闻通知</a></li>
				<li><a href="changePassword.jsp">修改密码</a></li>
				<li><a href="logout.action" target="_top">退出登录</a></li>
				<li><a id="copyrightLink" href="javascript:void(0)">关于
					<span id="copyright">
						常熟市航道管理信息系统 v1.0<br/>
						版权所有 &copy; 2014
					</span>
				</a></li>
			</ul>
			<span id="welcome"><span id="userName"></span>${sessionScope.loginUser.name} 您好！</span>
		</div>
	</div>
	<div class="ui-layout-west">
		<div id="menu">
		    <h3><a href="#">地图</a></h3>
		    <div>
		    	<ul>
		    		<li class="link"><a href="testFrame.jsp">地图浏览</a></li>
		    		<div>
				    	<ul>
				    		<li class="link"><a href="javascript:AddHDMap()">导入航道轨迹</a></li>
				    		<li class="link"><a href="testFrame.jsp">地图查询</a></li>
				    		<li class="link"><a href="javascript:ClearMapSelect()">清除选择</a></li>
						</ul>
					</div>
				</ul>
			</div>

			<h3><a href="#">航道基础信息管理</a></h3>
		    <div>
				<ul>
					<c:url var="url" value="searchTreeFrame.jsp">
						<c:param name="dataTableName" value="T_HD" />
						<c:param name="catalog" value="航道基础信息管理" />
					</c:url>
					<li class="link"><a id="showMap" href="${url}">航道基础信息一览</a></li>
				</ul>
		    </div>
		   <h3><a href="#">查询</a></h3>
		    <div>
		    	<ul>
		    		<li><select class="field" name="dataTableName" id="dataTableName">
						<option value="">---请选择内容---</option>
						<c:forEach items="${dataTableList}" var="dataTable">
							<c:if test="${dataTable.name != 'T_REPORT' && dataTable.name != 'T_MEDIA'}">
								<option value="${dataTable.name}">${dataTable.label}</option>
							</c:if>
						</c:forEach>
						</select>
					</li>
				</ul>
		    	<ul></ul>
		    </div>
			<h3><a href="#">航道工程管理</a></h3>
		    <div>
				<ul>
   					<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_GC_HDGHML">航道工程规划目录</a></li>
					<c:url var="url" value="searchTreeFrame.jsp">
						<c:param name="dataTableName" value="T_GC_HDGHML" />
						<c:param name="catalog" value="航道工程管理" />
					</c:url>
					<li class="link"><a href="${url}">规划、计划及统计、执行一览</a></li>
   					<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_GC_QZSCJH">维护措施目录</a></li>
   					<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_GC_HDGCWCQK">航道工程完成情况维护</a></li>
    				<li class="link"><a href="report/hdwchqk.jsp">航道工程完成情况</a></li>
				</ul>
		    </div>
		   
		    <h3><a href="#">航道养护管理</a></h3>
		    <div>
				<ul>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_YH_WHCSML">维护措施目录</a></li>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_YH_ZZJZWML">建筑物维护目录</a></li>
		    	</ul>
			</div>
		   
		    <h3><a href="#">航政业务管理</a></h3>
		    <div>
				<ul>
     				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_HZ_XHJH">航政巡航计划维护</a></li>
    				<li class="link"><a href="report/xhjh.jsp">巡航计划表</a></li>
    				<li class="link"><a href="report/xhlcjs.jsp">航政巡航里程</a></li>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_HZ_XHFY">航政巡航费用维护</a></li>
    				<li class="link"><a href="report/xhfyjs.jsp">航政巡航费用</a></li>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_HZ_THBZQK">航道通航保证情况</a></li>
    				<li class="link"><a href="report/thbz.jsp">航道通航保证情况报表</a></li>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_HZ_XKDJ">航道证许可事项登记</a></li>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_REPORT">航道管理与养护年报</a></li>
		    	</ul>
			</div>
			
			<h3><a href="#">航道统计管理</a></h3>
		    <div>
				<ul>
     				<li class="link"><a href="javascript:void(0)">航道统计汇总</a></li>
    				<li class="link"><a href="report/hdwchqk.jsp">航道工程完成情况汇总</a></li>
    				<li class="link"><a href="report/xhjh.jsp">航道巡航计划汇总</a></li>
    				<li class="link"><a href="report/xhfyjs.jsp">航道巡航费用汇总</a></li>
    				<li class="link"><a href="report/thbz.jsp">航道通航保证情况汇总</a></li>
    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_REPORT">航道管理与养护年报</a></li>
    				<li class="link"><a href="report/hdmx.jsp">航道明细</a></li>
    				<li class="link"><a href="report/hdlc.jsp">航道里程</a></li>
		    	</ul>
			</div>
			
			<h3><a href="#">航道管理部门</a></h3>
		    <div>
				<ul>
   					<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_GL_HDGLJG">航道管理机构列表</a></li>
					<c:url var="url" value="searchTreeFrame.jsp">
						<c:param name="dataTableName" value="T_GL_HDGLJG" />
						<c:param name="catalog" value="航道管理部门" />
					</c:url>
   					<li class="link"><a href="${url}">航道管理机构一览</a></li>
				</ul>
		    </div>
			
		    <c:if test="${sessionScope.loginUser.role == 'ADMIN'}">
			<h3><a href="#">系统管理</a></h3>
			<div>
				<ul>
					<li class="link"><a href="admin/userList.jsp">用户管理</a></li>
					<li class="link"><a href="admin/backup.action" onclick="return confirm('数据备份将使系统暂时不能为其他用户提供服务，确定要备份吗？')">数据备份</a></li>
				</ul>
			</div>
			</c:if>
		</div>
	</div>
	<iframe class="ui-layout-center" name="workspace" src=""></iframe>  
</body>
</html>