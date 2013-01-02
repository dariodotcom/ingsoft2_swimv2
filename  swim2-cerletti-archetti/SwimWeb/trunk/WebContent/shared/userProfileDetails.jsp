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
	Class<Customer> custClass = Customer.class;

	List<UserDetail> details = new ArrayList<UserDetail>();

	details.add(new UserDetail("Nome", false, "getName"));
	details.add(new UserDetail("Cognome", false, "getSurname"));
	details.add(new UserDetail("Username", true, "getUsername"));
	details.add(new UserDetail("Email", true, "getEmail"));
	details.add(new UserDetail("Data di nascita", false, "getBirthDate"));
	details.add(new UserDetail("Luogo di residenza", false,
	"getLocation"));

	Boolean showOwnProfile;

	if (SwimServlet.isCustomerLoggedIn(session)) {
		String loggedUsername = (String) session
		.getAttribute(AuthenticationServlet.LOGGED_USERNAME);
		showOwnProfile = loggedUsername.equals(cust.getUsername());
	} else {
		showOwnProfile = false;
	}
%>

<div id="userProfileDetails" class="profilePage">
	<div class="column" id="photoColumn">
		<div class="imageFrame">
			<img src="<%=request.getContextPath()%>/resources/big-user-img.png"
				alt="user image" />
		</div>
	</div>
	<div class="column propertyList" id="detailColumn">
		<%
			for (UserDetail d : details) {
				Method getter = custClass.getMethod(d.getGetterName(),
						(Class<?>[]) null);
				Object retVal = getter.invoke(cust, (Object[]) null);
				String detailValue = ( retVal != null ? 
						retVal instanceof Date ? Misc.DATE_FORMAT.format(retVal) :
							retVal.toString()
						: "Non impostato");
				String additionalClass = retVal == null ? " unset" : "";

				if (showOwnProfile || !d.isDetailPrivate()) {
		%>
		<div class="property">
			<div class="propertyName"><%=d.getDetailName()%></div>
			<div class="propertyValue<%=additionalClass%>"><%=detailValue%></div>
		</div>
		<%
			}
			}
		%>
	</div>
</div>