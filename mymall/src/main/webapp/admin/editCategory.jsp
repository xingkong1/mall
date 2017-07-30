<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<title>编辑分类</title>


<script>
$(function(){
	
	$("#editForm").submit(function(){
		if(!checkEmpty("name","分类名称"))
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
	      $('.workingArea').append($('<span>图片预览</span>'));
	      $('.workingArea').append($('<img/>').attr('src', imgURL));
	      // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
	      // URL.revokeObjectURL(imgURL);
	    }
	  });
	});

</script>

<div class="workingArea">

	<ol class="breadcrumb">
	  <li><a href="${pageContext.request.contextPath}/category/list">所有分类</a></li>
	  <li class="active">编辑分类</li>
	</ol>

	<div class="panel panel-warning editDiv">
	  <div class="panel-heading">编辑分类</div>
	  <div class="panel-body">
	    	<form method="post" id="editForm" action="${pageContext.request.contextPath}/category/update"  enctype="multipart/form-data">
	    		<table class="editTable">
	    			<tr>
	    				<td>分类名称</td>
	    				<td><input  id="name" name="name" value="${c.name}" type="text" class="form-control"></td>
	    			</tr>
	    			
	    			<tr>
	    			<td>分类圖片</td>
	    				<td><img height="40px"  src="${pageContext.request.contextPath}/img/category/${c.id}.jpg"></td>
	    			</tr>
	    			<tr>
	    				
	    				<td>
	    					<input id="categoryPic" accept="image/*" type="file" name="filepath" />
	    				</td>
	    			</tr>	    			
	    			<tr class="submitTR">
	    				<td colspan="2" align="center">
	    					<input type="hidden" name="id" value="${c.id}">
	    					<button type="submit" class="btn btn-success">提 交</button>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
	  </div>
	</div>	
</div>