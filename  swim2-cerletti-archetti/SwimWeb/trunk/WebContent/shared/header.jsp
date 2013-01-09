<%@page
	import="it.polimi.swim.business.bean.remote.UserProfileControllerRemote"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="it.polimi.swim.web.pagesupport.Misc"%>
<%@page import="it.polimi.swim.web.servlets.SwimServlet"%>
<%@page import="it.polimi.swim.web.servlets.PersonalPageServlet"%>
<%@page import="java.util.Hashtable"%>
<%@page import="javax.naming.Context"%>
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
	String reqContext = request.getContextPath();

	//Retrieve user informations
	Boolean userLoggedIn = SwimServlet.isUserLoggedIn(session);
	UserType hUserType = null;
	String hUserIdentity = null, hUsername, hThumb = null;

	if (userLoggedIn) {
		hUserType = (UserType) session
				.getAttribute(Misc.LOGGED_USERTYPE);
		hUsername = (String) session.getAttribute(Misc.LOGGED_USERNAME);

		switch (hUserType) {
		case ADMINISTRATOR:
			menuElements = AdminMenu.values();
			hUserIdentity = "Administrator (" + hUsername + ")";
			hThumb = request.getContextPath()
					+ Misc.DEFAULT_PHOTO_THUMB_URL;

			break;
		case CUSTOMER:
			menuElements = CustomerMenu.values();
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.jnp.interfaces.NamingContextFactory");
			env.put(Context.PROVIDER_URL, "localhost:1099");

			UserProfileControllerRemote profileCtrl;
			InitialContext jndiContext = new InitialContext(env);
			Object hRef = jndiContext.lookup(Misc.BeanNames.PROFILE);
			profileCtrl = (UserProfileControllerRemote) hRef;

			Customer c = profileCtrl.getByUsername(hUsername);
			hUserIdentity = c.getName() + " " + c.getSurname();
			hThumb = request.getContextPath()
					+ Misc.getThumbnailPhotoUrl(c);
			break;
		}
	} else {
		menuElements = UnloggedMenu.values();
	}

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
				<img src="<%=hThumb%>" alt="user image" />
			</div>
			<span id="userControl"> <span id="userName"><%=hUserIdentity%></span><br />
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
			<a class="loginLink" href="<%=reqContext%>/resetpassword/">Password
				dimenticata?</a>
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



