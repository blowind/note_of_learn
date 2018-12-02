<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>展示拦截器在视图渲染中的操作</title>
</head>
<body>
    <h1><%
        System.out.println("视图渲染--打印到后端控制台");
        out.print("输出到视图的内容--输出到前台页面\n");
        %></h1>
</body>
</html>