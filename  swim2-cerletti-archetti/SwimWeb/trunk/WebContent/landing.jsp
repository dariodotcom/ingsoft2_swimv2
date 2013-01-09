<%@page
	import="it.polimi.swim.web.servlets.AuthenticationServlet.RegistrationFields"%>
<%@page import="it.polimi.swim.web.pagesupport.ErrorType"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String retry = (String) request.getAttribute("retry");
	Boolean showLanding = false, retryLogin = false, retryRegistration = false;

	if (retry != null) {
		retryLogin = retry.equals("login");
		retryRegistration = retry.equals("registration");
	} else {
		showLanding = true;
	}

	String bodyClass = (showLanding ? "welcome" : "retry");
	request.setAttribute("selectedTab", UnloggedMenu.HOME);

	String pageHeading = showLanding ? "Benvenuto su Swim."
			: retryLogin ? "Login." : "Registrazione";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="swim <%=bodyClass%>">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<%@include file="shared/messageNotifier.jsp"%>
				<h1 class="pageTitle"><%=pageHeading%></h1>
			</div>
			<div class="monoPageContent">
				<%
					if (showLanding) {
				%>
				<div id="welcomeImage" class="welcomeColumn"></div>
				<%
					}
				%>
				<div class="formContainer welcomeColumn">
					<%
						if (showLanding) {
					%>
					<h2>Registrati! &Egrave; gratis, e lo sarà sempre.</h2>
					<%
						}

						if (showLanding || retryRegistration) {
							String propertyClass = retryRegistration ? " reducedWidth" : "";
					%>
					<form id="regForm" action="<%=request.getContextPath()%>/register"
						method="post" class="welcomeForm">
						<div class="propertyList<%=propertyClass%>">
							<%
								for (RegistrationFields field : RegistrationFields.values()) {
										String name = field.getName(), label = field.getLabel(), type = field
												.getType();
							%>
							<div class="property">
								<label class="propertyName" for="<%=name%>"><%=label%></label>
								<div class="propertyValue">
									<input type="<%=type%>" id="<%=name%>" name="<%=name%>"
										class="inputtext" />
								</div>
							</div>
							<%
								}
							%>
							<div class="submitLine">
								<input type="submit" value="Registrati" class="inputsubmit"/>
							</div>
						</div>
					</form>
					<%
						} else if (retryLogin) {
					%>
					<form id="secLoginForm"
						action="<%=request.getContextPath() + "/login"%>" method="post"
						class="welcomeForm">
						<div class="propertyList reducedWidth">
							<div class="property">
								<label class="propertyName" for="secUsername">Username</label>
								<div class="propertyValue">
									<input type="text" id="secUsername" name="username"
										class="inputtext" />
								</div>
							</div>
							<div class="property">
								<label class="propertyName" for="secPassword">Password</label>
								<div class="propertyValue">
									<input type="password" id="secPassword" name="password"
										class="inputtext" />
								</div>
							</div>
							<div class="submitLine">
								<input type="submit" value="Login" class="inputsubmit" />
							</div>
						</div>
					</form>
					<%
						}
					%>
				</div>
			</div>
		</div>
		<%@include file="shared/footer.jsp"%>
	</div>
</body>
</html>