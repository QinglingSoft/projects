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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="x-ua-compatible" content="ie=8" /> 
	<title>桥梁管理及安全监控系统</title>
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
	<script type="text/javascript" src="js/roadBridge-mapLayerSelect.js"></script>
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
		});
		
		//显示服务器时间
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
			$("iframe[name=workspace]").attr("src", "mapFrame.jsp");
		}

	</script>
	<script type="text/javascript" src="js/advancedSearch.js"></script>
	<script type="text/javascript" src="js/roadBridge-lxTree.js"></script>
</head>
<body>
	<div id="header" class="ui-layout-north ui-widget-header">
		<div class="title">桥梁管理及安全监控系统</div>
		<div class="subtitle">Bridge Management and Safety Monitoring System</div>
		<div id="clock"></div>
		<div id="topLevelFunctions">
			<ul>
				<li><a href="mapFrame.jsp">地图浏览</a></li>
				<li><a href="articleFrame.jsp">新闻通知</a></li>
				<li><a href="changePassword.jsp">修改密码</a></li>
				<li><a href="logout.action" target="_top">退出登录</a></li>
				<li><a id="copyrightLink" href="javascript:void(0)">关于
					<span id="copyright">
						桥梁管理及安全监控系统 v1.0<br/>
						版权所有 &copy; 2010
					</span>
				</a></li>
			</ul>
			<span id="welcome"><span id="userName"></span>${sessionScope.loginUser.name} 您好！</span>
		</div>
	</div>
	<div class="ui-layout-west">
		<div id="menu">
		    <h3><a href="#">地图管理</a></h3>
		    <div>
		    	<ul>
		    		<li class="link"><a href="/YZGL/mapload.jsp">地图浏览</a></li>
		    		<li class="catalog expanded">
		    			<div class="title">地图信息</div>
		    			<map:layerSelector />
		    		</li>
		    		<%-- 
		    		<li class="catalog collapse">
		    			<div class="title">背景信息</div>
		    			<ul>
		    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_LD">路段</a></li>
		    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_SD">隧道</a></li>
		    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_DK">渡口</a></li>
		    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_XZ">乡镇</a></li>
		    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_JZC">建制村</a></li>
		    				<li class="link"><a href="oneLevelFrame.jsp?dataTableName=T_CXX">村小学</a></li>
		    			</ul>
		    		</li>
		    		--%>
				</ul>
			</div>
			<h3><a href="#">桥梁监控管理</a></h3>
			<div>
				<lxTree:root />
			</div>
			<%--
			<h3><a href="#">桥梁基本信息</a></h3>
		    <div>
				<h4><a href="twoLevelFrame.jsp?dataTableName=T_QL&catalog=桥梁信息&clearCondition=true">浏览</a></h4>
				<jsp:include page="search.jsp" >
					<jsp:param name="dataTableName" value="T_QL"/>
					<jsp:param name="catalog" value="桥梁信息"/>
					<jsp:param name="page" value="twoLevelFrame.jsp"/>
				</jsp:include>
				<jsp:include page="advancedSearch.jsp" >
					<jsp:param name="searchName" value="桥梁信息"/>
					<jsp:param name="dataTableName" value="T_QL"/>
					<jsp:param name="catalog" value="桥梁信息"/>
					<jsp:param name="page" value="twoLevelFrame.jsp"/>
				</jsp:include>
		    </div>
			<h3><a href="#">桥梁评定信息</a></h3>
		    <div>
				<ul>
					<li class="link"><a href="treeFrame.jsp?dataTableName=T_QL&catalog=桥梁检查">浏览</a></li>
					<li class="link"><a href="treeSearchFrame.jsp?dataTableName=T_QL&catalog=桥梁检查">查询</a></li>
				</ul>
		    </div>
		     --%>
		    <h3><a href="#">统计分析</a></h3>
		    <div>
		   		<ul>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_QLJBZLMXB" />
		   				<c:param name="label" value="桥梁基本资料明细表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="桥梁基本资料明细表">桥梁基本资料明细表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_GLQLAQZKFLPJMXB" />
		   				<c:param name="label" value="公路桥梁安全状况分类评价明细表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="公路桥梁安全状况分类评价明细表">公路桥梁安全状况分类评价明细表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_YYLGJHNTBLTLQBHFLTJB" />
		   				<c:param name="label" value="预应力钢筋混凝土板梁T梁桥病害分类统计表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="预应力钢筋混凝土板梁T梁桥病害分类统计表">预应力钢筋混凝土板梁T梁桥病害分类统计表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_GJHNTSQGQBHFLTJB" />
		   				<c:param name="label" value="钢筋混凝土双曲拱桥病害分类统计表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="钢筋混凝土双曲拱桥病害分类统计表">钢筋混凝土双曲拱桥病害分类统计表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_GJHNTSFQBHFLTJB" />
		   				<c:param name="label" value="钢筋混凝土实腹桥病害分类统计表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="钢筋混凝土实腹桥病害分类统计表">钢筋混凝土实腹桥病害分类统计表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_GJHNTHJGQBHFLTJB" />
		   				<c:param name="label" value="钢筋混凝土桁架拱桥病害分类统计表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="钢筋混凝土桁架拱桥病害分类统计表">钢筋混凝土桁架拱桥病害分类统计表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_QTQXBHFLTJB" />
		   				<c:param name="label" value="其他桥型病害分类统计表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="其他桥型病害分类统计表">其他桥型病害分类统计表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_GLQLMXB" />
		   				<c:param name="label" value="公路桥梁明细表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="公路桥梁明细表">公路桥梁明细表</a></li>
		   			<c:url var="statUrl" value="statistic.jsp">
		   				<c:param name="procedureName" value="SP_GLQLHZB" />
		   				<c:param name="label" value="公路桥梁汇总表" />
		   			</c:url>
					<li class="link"><a href="${statUrl}" title="公路桥梁汇总表">公路桥梁汇总表</a></li>
				</ul>
			</div>
			<h3><a href="#">查询</a></h3>
		    <div>
		    	<ul>
		    		<li class="catalog">
		    			<div class="title">浏览</div>
		    			<search:tree />
		    		</li>
		    		<li class="catalog">
		    			<div class="title">常用查询</div>
		    			<search:normal dataTableName="T_QL" />
					</li>
					<li class="catalog">
						<div class="title">高级查询</div>
						<search:advanced dataTableName="T_QL" />
					</li>
				</ul>
		    </div>
		    <c:if test="${sessionScope.loginUser.role == 'ADMIN'}">
			<h3><a href="#">系统管理</a></h3>
			<div>
				<ul>
					<li class="link"><a href="admin/userList.jsp">用户管理</a></li>
					<li class="link"><a href="article/noticeList.jsp">通知管理</a></li>
					<li class="link"><a href="article/newsList.jsp">新闻管理</a></li>
					<li class="link"><a href="admin/qlMediaImport.jsp">导入桥梁图片</a></li>
					<li class="link"><a href="admin/backup.action" onclick="return confirm('数据备份将使系统暂时不能为其他用户提供服务，确定要备份吗？')">数据备份</a></li>
				</ul>
			</div>
			</c:if>
		</div>
	</div>
	<iframe class="ui-layout-center" name="workspace" src=""></iframe>
</body>
</html>