<%@page
	import="it.polimi.swim.web.servlets.PasswordResetServlet.PasswordResetSections"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	PasswordResetSections selectedSection = (PasswordResetSections) request
			.getAttribute(Misc.SELECTED_SECTION_ATTR);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<%@include file="shared/messageNotifier.jsp"%>
				<h1 class="pageTitle"><%=selectedSection.getTitle()%></h1>
			</div>
			<div class="monoPageContent">
				<%
					switch (selectedSection) {
					case LANDING:
				%>
				<p class="paragraph">Inserisci il tuo username per reimpostare
					la password:</p>
				<form action="<%=context%>/resetpassword/start" method="post">
					<div class="propertyList reducedWidth">
						<div class="property">
							<label class="propertyName" for="username">Username:</label>
							<div class="propertyValue">
								<input type="text" class="inputtext" name="username"
									id="username" />
							</div>
						</div>
						<div class="submitLine">
							<input type="submit" class="inputsubmit" value="Invia" />
						</div>
					</div>
				</form>
				<%
					break;
					case EMAIL_SENT:
				%>
				<p class="paragraph">Ti è stata inviata una all'indirizzo
					collegato al tuo profilo per confermare la tua identità. Segui le
					istruzioni in essa contenute per continuare.</p>
				<%
					break;
					case CONFIRM:
				%>
				<p class="paragraph">La password è stata resettata e ti è stata
					inviata per email. Una volta autenticato, ti consigliamo di
					cambiare la password.</p>
				<%
					break;
					}
				%>
			</div>
		</div>
		<%@include file="shared/footer.jsp"%>
	</div>
</body>
</html>