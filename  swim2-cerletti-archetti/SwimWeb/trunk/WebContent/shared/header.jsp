<%@page import="it.polimi.swim.web.servlets.SwimServlet"%>
<%@page import="it.polimi.swim.web.servlets.PersonalPageServlet"%>
<%@page
	import="it.polimi.swim.business.bean.remote.AuthenticationControllerRemote"%>
<%@page import="it.polimi.swim.business.entity.Customer"%>
<%@page import="it.polimi.swim.business.bean.UserType"%>
<%@page import="it.polimi.swim.web.servlets.AuthenticationServlet"%>
<%@page import="it.polimi.swim.web.pagesupport.MenuDescriptor"%>
<%@page import="it.polimi.swim.web.pagesupport.UnloggedMenu"%>
<%@page import="it.polimi.swim.web.pagesupport.AdminMenu"%>
<%@page import="it.polimi.swim.web.pagesupport.CustomerMenu"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	MenuDescriptor[] menuElements = null;
	Boolean userLoggedIn = SwimServlet.isUserLoggedIn(session);

	UserType type = (UserType) session
			.getAttribute(AuthenticationServlet.LOGGED_USERTYPE);

	String userIdentity = null;

	if (type == null) {
		menuElements = UnloggedMenu.values();
	} else {
		switch (type) {
		case ADMINISTRATOR:
			menuElements = AdminMenu.values();
			userIdentity = "Administrator";
			break;
		case CUSTOMER:
			menuElements = CustomerMenu.values();
			Customer c = (Customer) request
					.getAttribute(PersonalPageServlet.USER_ATTR);
			if (c != null) {
				userIdentity = c.getName() + " " + c.getSurname();
			} else {
				userIdentity = "Logged user not set TODO";
			}
			break;
		}
	}

	String reqContext = request.getContextPath();
	
	MenuDescriptor selectedTab = (MenuDescriptor) request
			.getAttribute("selectedTab");
%>
<div id="swimHeaderContainer">
	<div id="swimHeader" class="headerContent topWidthElement">
		<div id="swimLogoContainer">
			<a href="<%=request.getContextPath()%>/" id="swimLogo"><img
				src="<%=request.getContextPath()%>/resources/icon-blank.png"
				alt="Swim Network" /></a>
		</div>
		<%
			if (userLoggedIn) {
				//Display user photo, name and surname
		%>
		<div id="swimUserAwareness">
			<div id="userImageFrame">
				<img src="<%=request.getContextPath()%>/resources/user-img.png"
					alt="user image" />
			</div>
			<span id="userControl"> <span id="userName"><%=userIdentity%></span><br />
				<a id="userLogout" href="<%=request.getContextPath()%>/logout">Esci</a>
			</span>
		</div>
		<%
			} else {
				//Display form to login
		%>
		<form name="login" id="loginForm"
			action="<%=request.getContextPath() + "/login"%>" method="post">
			<div class="loginInputContainer">
				<label for="logUsername" class="label">Username</label><br /> <input
					type="text" id="logUsername" class="textinput" name="username"
					tabindex="1" />
			</div>
			<div class="loginInputContainer">
				<label for="logPassword" class="label">Password</label><br /> <input
					type="password" id="logPassword" class="textinput" name="password"
					tabindex="2" />
			</div>
			<div class="loginInputContainer">
				<input type="submit" class="inputsubmit" value="Login" tabindex="3" />
			</div>
			<a class="loginLink" href="<%=reqContext%>/resetpassword/">Password dimenticata?</a>
		</form>


		<%
			}
		%>
	</div>
	<div id="swimMenuContainer" class="headerContent topWidthElement">
		<ul id="swimMenu">
			<%
				if (menuElements != null) {
					for (MenuDescriptor m : menuElements) {
						String selClass = (m.equals(selectedTab) ? " selected" : "");
			%>
			<li class="menuEntry<%=selClass%>" id="<%=m.getElementId()%>"><a
				class="tabLink"
				href="<%=request.getContextPath() + m.getTabLink()%>"> <img
					class="tabIcon"
					src="<%=request.getContextPath()%>/resources/icon-blank.png"
					alt="icon" /> <span class="tabTitle"><%=m.getTabName()%></span>
			</a></li>
			<%
				}
				}
			%>
		</ul>
	</div>
</div>



