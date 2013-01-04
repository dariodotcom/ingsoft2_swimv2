<%@page import="it.polimi.swim.web.pagesupport.Misc"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String pageSubTitle = (String) request
			.getAttribute(Misc.PAGE_TITLE_ATTR);
	String pageTitle = Misc.BASE_PAGE_TITLE
			+ (pageSubTitle == null ? "" : Misc.PAGE_TITLE_SEP
					+ pageSubTitle);
	String context = request.getContextPath();
%>

<head>
<title><%=pageTitle%></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<link type="text/css" rel="stylesheet" href="<%=context%>/zero.css" />
<link type="text/css" rel="stylesheet" href="<%=context%>/clearfix.css" />
<link href='http://fonts.googleapis.com/css?family=Droid+Sans'
	rel='stylesheet' type='text/css' />
<link type="text/css" rel="stylesheet" href="<%=context%>/swim.css" />

<link rel="icon" href="<%=context%>/resources/favicon.png"
	type="image/png" />

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script type="text/javascript" src="<%=context%>/swim.js"></script>
</head>