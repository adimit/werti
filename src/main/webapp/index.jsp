<?xml version="1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<link type="text/css" href="werti.css" rel="stylesheet" />
		<title>Welcome to WERTi</title>
	</head>
	<body>
		<div id="header">
			I'm a header
		</div>
		<div id="navbar">
			<ul>
				<li><a href="/WERTi/index.jsp?content=home">Home</a></li>
				<li><a href="/WERTi/index.jsp?content=start">Start</a></li>
				<li><a href="/WERTi/index.jsp?content=about">About</a></li>
				<li><a href="/WERTi/index.jsp?content=help">Help</a></li>
				<li><a href="/WERTi/index.jsp?content=links">Links</a></li>
			</ul>
		</div>
		<div id="main">
			<% 
			String content = request.getParameter("content");
			if (content == null) {
				content = "tmpl/home.jsp";
			} else {
				content = "tmpl/" + content + ".jsp";
			}
			%>
			<jsp:include page="<%= content %>" />
		</div>
	</body>
</html>
