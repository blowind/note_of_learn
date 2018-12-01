<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>文件上传</title>
</head>
<body>
<%--注意enctype，一定要指定为multipart/form-data，否则不能上传文件--%>
<form method="post" action="./upload/request" enctype="multipart/form-data">
    <input type="file" name="file" value="请选择上传的文件"/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>