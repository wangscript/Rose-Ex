<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<title>文件上传</title>
</head>
<body>


<script>
	$(function(){
		$('#typeTab a:first').tab('show') 
	});
</script>
<div class="panel panel-default">
	<div class="panel-heading">
		<span aria-hidden="true" class="glyphicon glyphicon-th-large"></span>&nbsp;上传文件
	</div>
	<div class="panel-body">
	  	<form id="upload_form" action="/upload" method="post" enctype="multipart/form-data" >
		  	<div class="row">
			  <div class="col-xs-2">
			     <label class="control-label">上传文件：</label>
		      </div>
		      <div class="col-xs-2">
		         <input name="file" class="input-file" type="file" />
			  </div>
			</div>
			<div class="row">
			  <div class="col-xs-2">&nbsp;</div>
			  <div class="col-xs-2">
	          	<a onclick="createContract()" class="btn btn-success" href="javascript:void(0)" role="button">
	     		确认提交
	     	</a>
	     	<script>
	     		function createContract(){
	     			var cf = $("#upload_form");
	     			cf.submit();
	     		}
	     	</script>
	          </div>
	        </div>
		</form>
	</div>
</div>

<div class="panel panel-default">
	 <div class="panel-heading">
		<span aria-hidden="true" class="glyphicon glyphicon-th-list"></span>&nbsp;文件列表
	 </div>
	 <div class="panel-body">
		<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<th>上传时间</th>
					<th>文件名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="file" varStatus="status" items="${files}">
				<tr>
			        <th>${file.modifyTime }</th>
			        <td>${file.fileName }</td>
			        <td>下载</td>
		        </tr>
			</c:forEach>
		    </tbody>
		</table>
	 </div>
</div>
</body>
</html>