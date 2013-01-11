<%@page import="it.polimi.swim.business.entity.Ability"%>
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

	String ctx = request.getContextPath();

	List<?> abilityList = (List<?>) request
			.getAttribute(Misc.ABILITY_LIST);
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
						<%@include file="shared/messageNotifier.jsp"%>
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
								<div class="propertyList reducedWidth">
									<div class="property">
										<label for="editName" class="propertyName">Nome*</label>
										<div class="propertyValue">
											<input type="text" name="name"
												value="<%=customer.getName()%>" id="editName"
												class="inputtext" />
										</div>
									</div>
									<div class="property">
										<label for="editSurname" class="propertyName">Cognome*</label>
										<div class="propertyValue">
											<input type="text" name="surname"
												value="<%=customer.getSurname()%>" id="editSurname"
												class="inputtext" />
										</div>
									</div>
									<div class="property">
										<label for="editBirthdate" class="propertyName">Data
											di nascita (gg/mm/aaaa)</label>
										<div class="propertyValue">
											<input type="text" name="birthdate"
												value="<%=Misc.parseDate(customer.getBirthDate())%>"
												id="editBirthdate" class="inputtext" />
										</div>
									</div>
									<div class="property">
										<label for="editLocation" class="propertyName">Luogo
											di residenza</label>
										<div class="propertyValue">
											<input type="text" name="location"
												value="<%=Misc.replaceEmpty(customer.getLocation(), "")%>"
												id="editName" class="inputtext" />
										</div>
									</div>
								</div>

								<div class="submitLine">
									<input type="submit" class="inputsubmit" value="Salva" />
								</div>
							</form>
						</div>

						<h2 class="partTitle">Modifica la foto del profilo</h2>
						<div class="part">
							<form method="post" enctype="multipart/form-data"
								action="<%=context%>/home/changePhoto">
								<div class="propertyList">
									<div class="property">
										<label class="propertyName" for="fakeImageInput">Immagine
											del profilo (100px x 100px)</label>
										<div class="propertyValue">
											<input type=text id="fakeImageInput" class="inputtext" readonly/>
											<a href="javascript:" id="fakeImageSubmit" class="button">Cerca</a>
											
											<input type="file" name="customerphoto"
												id="realImageInput"/>
										</div>
									</div>
									<div class="submitLine">
										<input type="submit" class="inputsubmit" value="Salva" />
									</div>
								</div>
							</form>

						</div>

						<h2 class="partTitle">Aggiungi professionalit&agrave;</h2>
						<div class="part">
							<form action="<%=ctx%>/home/addAbility" method="post">
								<div class="propertyList reducedWidth">
									<div class="property">
										<label for="addAbilityInput" class="propertyName">Ricerca
											abilità</label>

										<div class="propertyValue">
											<input type="text" id="addAbilityInput" name="abilityName"
												class="inputtext abilityInput" />
										</div>
									</div>
									<div class="submitLine">
										<input type="submit" class="inputsubmit" value="Aggiungi" />
									</div>
								</div>
							</form>
							<p class="paragraph spaceTop">
								Non hai trovato la professionalit&agrave; che cercavi? <a
									href="<%=context%>/abilityrequest/">Richiedila ora!</a>
							</p>
						</div>

						<h2 class="partTitle">Gestisci professionalit&agrave;</h2>
						<div class="part">
							<%
								if (abilityList.size() == 0) {
							%>
							<p class="paragraph">Non hai professionalit&agrave;
								dichiarate.</p>
							<%
								} else {
							%>
							<p class="paragraph">Hai dichiarato le seguenti
								professionalit&agrave;. Se ne rimuovi una non potrai più
								ricevere richieste di lavoro inerenti ad essa.</p>
							<div class="propertyList abilityList reducedWidth">
								<%
									for (Object o : abilityList) {
												Ability a = (Ability) o;
								%>
								<div class="property">
									<span class="propertyName"><%=a.getName()%></span>
									<form class="propertyValue"
										action="<%=context%>/home/removeAbility" method="post">
										<input type="hidden" name="abilityName"
											value="<%=a.getName()%>" /> <input type="submit"
											class="inputsubmit" value="Rimuovi" />
									</form>
								</div>
								<%
									}
								%>
							</div>
							<%
								}
							%>
						</div>

						<%
							break;
							case EDIT_ACCOUNT:
						%>
						<h2 class="partTitle">Modifica password</h2>
						<div class="part">
							<form action="<%=ctx%>/home/changePassword" method="post">
								<div class="propertyList reducedWidth">
									<div class="property">
										<label for="currentPassword" class="propertyName">Password
											corrente*</label>
										<div class="propertyValue">
											<input type="password" name="currentpassword"
												id="currentPassword" class="inputtext" />
										</div>
									</div>

									<div class="property">
										<label for="editPassword" class="propertyName">Nuova
											password*</label>
										<div class="propertyValue">
											<input type="password" name="password" id="editPassword"
												class="inputtext" />
										</div>
									</div>

									<div class="property">
										<label for="editPasswordRepeat" class="propertyName">Ripeti
											password*</label>
										<div class="propertyValue">
											<input type="password" name="passwordrepeat"
												id="editPasswordRepeat" class="inputtext" />
										</div>
									</div>

									<div class="submitLine">
										<input type="submit" class="inputsubmit"
											value="Modifica password" />
									</div>
								</div>
							</form>
						</div>

						<h2 class="partTitle">Modifica email</h2>
						<div class="part">
							<form action="<%=ctx%>/home/changeEmail" method="post">
								<div class="propertyList reducedWidth">
									<div class="property">
										<label for="currentPassword2" class="propertyName">Password
											corrente*</label>
										<div class="propertyValue">
											<input type="password" name="currentpassword"
												id="currentPassword2" class="inputtext" />
										</div>
									</div>

									<div class="property">
										<label for="editEmail" class="propertyName">Email*</label>
										<div class="propertyValue">
											<input type="text" name="newemail" id="editEmail"
												class="inputtext" value="<%=customer.getEmail()%>" />
										</div>
									</div>
									<div class="submitLine">
										<input type="submit" class="inputsubmit"
											value="Modifica email" />
									</div>
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