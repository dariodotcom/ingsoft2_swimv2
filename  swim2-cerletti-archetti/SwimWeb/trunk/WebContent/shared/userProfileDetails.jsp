<%@page import="it.polimi.swim.business.entity.Ability"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Date"%>
<%@page import="it.polimi.swim.web.servlets.AuthenticationServlet"%>
<%@page import="it.polimi.swim.web.servlets.SwimServlet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.polimi.swim.web.pagesupport.UserDetail"%>
<%@page import="it.polimi.swim.web.pagesupport.Misc"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="it.polimi.swim.business.entity.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	Customer cust = (Customer) request.getAttribute(Misc.USER_TO_SHOW);
	List<UserDetail> details = new ArrayList<UserDetail>();
	String birthDate = Misc.parseDate(cust.getBirthDate());
	List<?> usrAbilityList = (List<?>) request
			.getAttribute(Misc.ABILITY_LIST);

	StringBuilder usrAbilities = new StringBuilder();
	Iterator<?> abIterator = usrAbilityList.iterator();

	while (abIterator.hasNext()) {
		Ability a = (Ability) abIterator.next();
		usrAbilities.append(a.getName());
		if (abIterator.hasNext()) {
			usrAbilities.append(", ");
		}
	}

	details.add(new UserDetail("Nome", false, cust.getName()));
	details.add(new UserDetail("Cognome", false, cust.getSurname()));
	details.add(new UserDetail("Username", true, cust.getUsername()));
	details.add(new UserDetail("Email", true, cust.getEmail()));
	details.add(new UserDetail("Data di nascita", false, birthDate));
	details.add(new UserDetail("Luogo di residenza", false, cust
			.getLocation()));
	details.add(new UserDetail("Professionalit&agrave dichiarate",
			false, usrAbilities.toString()));

	Boolean showOwnProfile;

	String photoUrl = request.getContextPath() + Misc.getPhotoUrl(cust);

	if (SwimServlet.isCustomerLoggedIn(session)) {
		String loggedUsername = (String) session
				.getAttribute(AuthenticationServlet.LOGGED_USERNAME);
		showOwnProfile = loggedUsername.equals(cust.getUsername());
	} else {
		showOwnProfile = false;
	}

	Integer feedbackAvg = (Integer) request
			.getAttribute(Misc.MARK_VALUE);
%>

<div id="userProfileDetails" class="profilePage">
	<div class="column" id="photoColumn">
		<div class="imageFrame">
			<img src="<%=photoUrl%>" alt="user image" />
		</div>
	</div>
	<div class="column propertyList" id="detailColumn">
		<%
			for (UserDetail d : details) {
				String value = d.getValue();
				String additionalClass = (Misc.isStringEmpty(value) ? " unset"
						: "");

				if (showOwnProfile || !d.isDetailPrivate()) {
		%>
		<div class="property">
			<div class="propertyName"><%=d.getDetailName()%></div>
			<div class="propertyValue<%=additionalClass%>"><%=Misc.replaceEmpty(value, "-")%></div>
		</div>
		<%
			}
			}
		%>
		<div class="property">
			<div class="propertyName">Media feedback</div>
			<%
				if (feedbackAvg == null) {
			%>
			<div class="propertyValue unset">L'utente non ha feedback.</div>
			<%
				} else {
			%>
			<div class="propertyValue">
				<%@include file="feedbackMarker.jsp"%>
			</div>
			<%
				}
			%>

		</div>
	</div>
</div>