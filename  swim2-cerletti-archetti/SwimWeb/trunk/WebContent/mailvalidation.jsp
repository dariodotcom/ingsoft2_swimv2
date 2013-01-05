<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String email = (String) request.getAttribute(Misc.CUSTOMER_EMAIL);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<%@include file="shared/messageNotifier.jsp"%>
				<h1 class="pageTitle">Convalida l'indirizzo email</h1>
			</div>
			<div class="monoPageContent">
				<p class="paragraph">
					Controlla la casella email <span class="bold"><%=(email != null ? email + " " : "")%></span>che
					hai inserito, ti &egrave; stato inviato un email. Contiene un
					collegamento che, una volta aperto, convalider&agrave; l'indirizzo
					email e ti permetterà di iniziare ad usare la piattaforma SWIM!
				</p>
			</div>
		</div>
		<%@include file="shared/footer.jsp"%>
	</div>
</body>
</html>