<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String pageSubTitle = (String) request.getAttribute("pagetitle");
	String pageTitle = "Swim"
			+ (pageSubTitle == null ? "" : " | " + pageSubTitle);
%>

<head>
<title><%=pageTitle%></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<link type="text/css" rel="stylesheet" href="zero.css" />
<link href='http://fonts.googleapis.com/css?family=Droid+Sans'
	rel='stylesheet' type='text/css' />
<link type="text/css" rel="stylesheet" href="swim.css" />

<link rel="icon" href="resources/favicon.png" type="image/png" />
</head>