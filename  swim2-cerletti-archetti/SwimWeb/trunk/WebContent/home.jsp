<%@page import="it.polimi.swim.web.pagesupport.ErrorType"%>
<%@page import="it.polimi.swim.web.pagesupport.NotificationMessages"%>
<%@page import="it.polimi.swim.web.servlets.PersonalPageServlet"%>
<%@page
	import="it.polimi.swim.web.servlets.PersonalPageServlet.PersonalPageSection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	Object ref = request.getAttribute(Misc.SELECTED_SECTION_ATTR);
	PersonalPageSection selectedSection = (ref != null ? (PersonalPageSection) ref
			: PersonalPageSection.HOME);

	request.setAttribute(Misc.PAGE_TITLE_ATTR,
			selectedSection.getSectionName());

	Customer customer = (Customer) request
			.getAttribute(Misc.USER_TO_SHOW);

	NotificationMessages notification = (NotificationMessages) request
			.getAttribute(Misc.NOTIFICATION_ATTR);

	ErrorType error = (ErrorType) request.getAttribute(Misc.ERROR_ATTR);

	String ctx = request.getContextPath();
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="leftColumn" class="column">
				<ul id="swimSecondaryMenu">
					<%
						for (PersonalPageSection s : PersonalPageSection.values()) {
							String link = ctx + "/" + PersonalPageServlet.CONTEXT_NAME
									+ "/" + s.getSectionIdentifier(), name = s
									.getSectionName();
							String selClass = selectedSection.equals(s) ? " selected" : "";
					%>
					<li class="entry<%=selClass%>"><a class="label"
						href="<%=link%>"><%=name%></a></li>
					<%
						}
					%>
				</ul>
			</div>
			<div id="rightColumn" class="column">
				<div class="profilePage">
					<div class="pageHeading">
						<%
							if (notification != null) {
						%>
						<p class="notification">
							<%=notification.getDescription()%>
						</p>
						<%
							}

							if (error != null) {
						%>
						<p class="error">
							<%=error.getErrorDescription()%>
						</p>
						<%
							}
						%>
						<h1 class="pageTitle"><%=selectedSection.getSectionName()%></h1>
					</div>
					<div class="monoPageContent">
						<%
							switch (selectedSection) {
							case HOME:
						%>
						<%@include file="shared/userProfileDetails.jsp"%>
						<%
							break;
							case EDIT_PROFILE:
						%>
						<h2 class="partTitle">Modifica dati personali</h2>
						<div class="part">
							<form action="<%=ctx%>/home/editProfile" method="post">
								<div class="detail">
									<div class="detailName">
										<label for="editName">Nome*</label>
									</div>
									<div class="detailValue">
										<input type="text" name="name" value="<%=customer.getName()%>"
											id="editName" class="inputtext" />
									</div>
								</div>

								<div class="detail">
									<div class="detailName">
										<label for="editSurname">Cognome*</label>
									</div>
									<div class="detailValue">
										<input type="text" name="surname"
											value="<%=customer.getSurname()%>" id="editSurname"
											class="inputtext" />
									</div>
								</div>

								<div class="detail">
									<div class="detailName">
										<label for="editBirthdate">Data di nascita
											(gg/mm/aaaa)</label>
									</div>
									<div class="detailValue">
										<input type="text" name="birthdate"
											value="<%=Misc.parseDate(customer.getBirthDate())%>"
											id="editBirthdate" class="inputtext" />
									</div>
								</div>

								<div class="detail">
									<div class="detailName">
										<label for="editLocation">Luogo di residenza</label>
									</div>
									<div class="detailValue">
										<input type="text" name="location"
											value="<%=Misc.nullfix(customer.getLocation())%>"
											id="editName" class="inputtext" />
									</div>
								</div>
								<div class="submitLine">
									<input type="submit" class="inputsubmit" value="Salva" />
								</div>
							</form>
						</div>
						<h2 class="partTitle">Gestisci professionalit&agrave;</h2>
						<div class="part"></div>
						<h2 class="partTitle">Aggiungi professionalit&agrave;</h2>
						<div class="part">
							<form action="<%=ctx%>/home/addAbility">
								<input type="text" id="addAbilityInput" class="inputtext" />
							</form>
						</div>

						<%
							break;
							case EDIT_ACCOUNT:
						%>
						<h2 class="partTitle">Modifica email</h2>
						<div class="part">
							<form action="<%=ctx%>/home/changePassword" method="post">

								<div class="detail">
									<div class="detailName">
										<label for="currentPassword">Password corrente*</label>
									</div>
									<div class="detailValue">
										<input type="password" name="currentpassword"
											id="currentPassword" class="inputtext" />
									</div>
								</div>

								<div class="detail">
									<div class="detailName">
										<label for="editPassword">Nuova password*</label>
									</div>
									<div class="detailValue">
										<input type="password" name="password" id="editPassword"
											class="inputtext" />
									</div>
								</div>

								<div class="detail">
									<div class="detailName">
										<label for="editPasswordRepeat">Ripeti password*</label>
									</div>
									<div class="detailValue">
										<input type="password" name="passwordrepeat"
											id="editPasswordRepeat" class="inputtext" />
									</div>
								</div>

								<div class="submitLine">
									<input type="submit" class="inputsubmit"
										value="Modifica password" />
								</div>
							</form>
						</div>

						<h2 class="partTitle">Modifica email</h2>
						<div class="part">
							<form action="<%=ctx%>/home/changeEmail" method="post">

								<div class="detail">
									<div class="detailName">
										<label for="currentPassword2">Password corrente*</label>
									</div>
									<div class="detailValue">
										<input type="password" name="currentpassword"
											id="currentPassword2" class="inputtext" />
									</div>
								</div>

								<div class="detail">
									<div class="detailName">
										<label for="editEmail">Email*</label>
									</div>
									<div class="detailValue">
										<input type="text" name="newemail" id="editEmail"
											class="inputtext" value="<%=customer.getEmail()%>" />
									</div>
								</div>
								<div class="submitLine">
									<input type="submit" class="inputsubmit" value="Modifica email" />
								</div>
							</form>
						</div>
						<%
							break;
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>