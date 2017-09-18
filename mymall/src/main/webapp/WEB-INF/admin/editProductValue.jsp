<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<title>编辑产品属性值</title>

<script>
$(function() {
	$("input.pvValue").keyup(function(){
		var value = $(this).val();
		var page = "${pageContext.request.contextPath}/product/updatePropertyValue";
		var pvid = $(this).attr("pvid");
		var ptid=$(this).attr("ptid");
		var pid=$(this).attr("pid");
		var parentSpan = $(this).parent("span");
		parentSpan.css("border","1px solid yellow");
		$.post(
			    page,
			    {"value":value,"pvid":pvid,"ptid":ptid,"pid":pid},
			    function(result){
			    	if("success"==result)
						parentSpan.css("border","1px solid green");
			    	else
			    		parentSpan.css("border","1px solid red");
			    }
			);		
	});
});
</script>

<div class="workingArea">
	<ol class="breadcrumb">
	  <li><a href="${pageContext.request.contextPath}/category/list">所有分类</a></li>
	  <li><a href="${pageContext.request.contextPath}/product/list/${p.category.id}">${p.category.name}</a></li>
	  <li class="active">${p.name}</li>
	  <li class="active">编辑产品属性</li>
	</ol>
	
	<div class="editPVDiv">
		<c:forEach items="${pvs}" var="pv">
			<div class="eachPV">
				<span class="pvName" >${pv.property.name}</span>
				<span class="pvValue"><input class="pvValue" ptid="${pv.property.id}" pid="${p.id}" pvid="${pv.id}" type="text" value="${pv.value}"></span>
			</div>
		</c:forEach>
	<div style="clear:both"></div>	
	</div>
	
</div>

