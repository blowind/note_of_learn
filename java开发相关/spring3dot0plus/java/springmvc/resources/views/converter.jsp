<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta content="text/html;charset=UTF-8" http-equiv="Content-Type">
	<title>HttpMessageConverter Demo</title>
</head>
<body>
<div id="resp"></div>
<input type="button" onclick="req();" value="请求"/>
<%--<script type="assets/js/jquery-3.3.1.min" type="text/javascript"></script>--%>
<script   src="https://code.jquery.com/jquery-3.3.1.min.js"   integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="   crossorigin="anonymous"></script>
<script>
	function req() {
		$.ajax({
			url: "convert",
			data: "1-songsiying",
			type: "POST",
			contentType: "application/zxf",
			success: function(data) {
				$("#resp").html(data);
			}
		})
	}
</script>
</body>
</html>