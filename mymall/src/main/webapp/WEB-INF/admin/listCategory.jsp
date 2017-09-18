<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
$(function(){
	
	$("#addForm").submit(function(){
		if(!checkEmpty("name","分类名称"))
			return false;
		if(!checkEmpty("categoryPic","分类图片"))
			return false;
		
		
		
		return true;
	});
});

$(function() {
	  $('#categoryPic').change(function(event) {
	    // 根据这个 <input> 获取文件的 HTML5 js 对象
	    var files = event.target.files, file;        
	    if (files && files.length > 0) {
	      // 获取目前上传的文件
	      file = files[0];
	      // 来在控制台看看到底这个对象是什么
	      console.log(file);
	      // 那么我们可以做一下诸如文件大小校验的动作
	      if(file.size > 1024 * 1024 * 2) {
	        alert('图片大小不能超过 2MB!');
	        return false;
	      }
	      // !!!!!!
	      // 下面是关键的关键，通过这个 file 对象生成一个可用的图像 URL
	      // 获取 window 的 URL 工具
	      var URL = window.URL || window.webkitURL;
	      // 通过 file 生成目标 url
	      var imgURL = URL.createObjectURL(file);
	      // 用这个 URL 产生一个 <img> 将其显示出来
	      $('.panel-body').append($('<span>图片预览</span>'));
	      $('.panel-body').append($('<img/>').attr('src', imgURL));
	      // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
	      // URL.revokeObjectURL(imgURL);
	    }
	  });
	});

</script>

<title>分类管理</title>


<div class="workingArea">
	<h1 class="label label-info" >分类管理</h1>
	<br>
	<br>
	
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid"> 
    <div class="navbar-header">
        <a class="navbar-brand" href="#">分类查找</a>
    </div>
    <div>
        <form class="navbar-form navbar-left" action="${pageContext.request.contextPath}/category/search" role="search">
            <div class="form-group">
                <input type="text" name="key" class="form-control" placeholder="Search">
            </div>
            <button type="submit" class="btn btn-default">提交</button>
        </form>
    </div>
    </div>
</nav>
	
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>图片</th>
					<th>分类名称</th>
					<th>属性管理</th>
					<th>产品管理</th>
					<th>编辑</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="c">
				
				<tr>
					<td>${c.id}</td>
					<td><img height="40px" src="${pageContext.request.contextPath}/img/category/${c.id}.jpg"></td>
					<td>${c.name}</td>
					 	
					<td><a href="${pageContext.request.contextPath}/property/list/${c.id}"><span class="glyphicon glyphicon-th-list"></span></a></td>					
					<td><a href="${pageContext.request.contextPath}/product/list/${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>					
					<td><a href="${pageContext.request.contextPath}/category/update/${c.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
					<td><a deleteLink="true" href="${pageContext.request.contextPath}/category/delete/${c.id}"><span class=" 	glyphicon glyphicon-trash"></span></a></td>
	
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<div class="panel panel-warning addDiv">
	  <div class="panel-heading">新增分类</div>
	  <div class="panel-body">
	    	<form method="post" id="addForm" action="${pageContext.request.contextPath}/category/add" enctype="multipart/form-data">
	    		<table class="addTable">
	    			<tr>
	    				<td>分类名称</td>
	    				<td><input  id="name" name="name" type="text" class="form-control"></td>
	    			</tr>
	    			<tr>
	    				<td>分类圖片</td>
	    				<td>
	    					<input id="categoryPic" accept="image/*" type="file" name="filepath" />
	    				</td>
	    			</tr>
	    			<tr class="submitTR">
	    				<td colspan="2" align="center">
	    					<button type="submit" class="btn btn-success">提 交</button>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
	  </div>
	</div>
	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>