<%@page import="it.polimi.swim.business.entity.Friendship"%>
<%@page import="java.util.List"%>
<%@page import="it.polimi.swim.web.servlets.CustomerFriendshipServlet"%>
<%@page
	import="it.polimi.swim.web.servlets.CustomerFriendshipServlet.CustomerFriendshipSection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	Object ref = request.getAttribute(Misc.SELECTED_SECTION_ATTR);
	CustomerFriendshipSection selectedSection = (ref != null ? (CustomerFriendshipSection) ref
			: CustomerFriendshipSection.FRIENDS);

	request.setAttribute(Misc.PAGE_TITLE_ATTR,
			selectedSection.getSectionName());

	List<?> friendList = (List<?>) request
			.getAttribute(Misc.FRIENDLIST_ATTR);

	String ctx = request.getContextPath();
	String selfUsername = (String) session
			.getAttribute(AuthenticationServlet.LOGGED_USERNAME);
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="leftColumn" class="column">
				<ul id="swimSecondaryMenu">
					<%
						for (CustomerFriendshipSection s : CustomerFriendshipSection
								.values()) {
							String link = ctx + "/"
									+ CustomerFriendshipServlet.CONTEXT_NAME + "/"
									+ s.getSectionIdentifier(), name = s.getSectionName();
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
				<div class="pageHeading">
					<h1 class="pageTitle"><%=selectedSection.getSectionName()%></h1>
				</div>
				<div class="monoPageContent">
					<%
						switch (selectedSection) {
						case FRIENDS:
							if (friendList.size() == 0) {
					%>
					<p class="text">Non hai amici. Inizia ad esplorare il mondo di
						Swim e stringi nuove amicizie.</p>
					<%
						} else {
					%>
					<p class="paragraph">I seguenti utenti sono tuoi amici:</p>
					<div class="propertyList reducedWidth withImage">
						<%
							for (Object o : friendList) {
										Friendship f = (Friendship) o;
										Customer sender = f.getSender(), receiver = f
												.getReceiver(), friend = (sender.getUsername()
												.equals(selfUsername) ? receiver : sender);

										String friendIdentity = String.format("%s %s",
												friend.getName(), friend.getSurname());
										String thumbnailUrl = ctx
												+ Misc.getThumbnailPhotoUrl(friend);
						%>
						<div class="property">
							<div class="propertyName">
								<span class="smallImageFrame"> <img
									src="<%=thumbnailUrl%>" alt="User image" />
								</span> <span class="afterImage"> <a
									href="<%=ctx%>/user/?u=<%=friend.getUsername()%>"><%=friendIdentity%></a></span>
							</div>
							<form action="<%=ctx%>/friends/remove" method="post"
								class="propertyValue">
								<input type="hidden" name="f" value="<%=f.getId()%>" /> <input
									type="submit" class="inputsubmit" value="Rimuovi" />
							</form>
						</div>
						<%
							}
						%>
					</div>
					<%
						}
					%>

					<%
						break;
						case FRIENDSHIP_REQUESTS:
							if (friendList.size() == 0) {
					%>
					<p class="text">Non hai richieste di amicizia.</p>
					<%
						} else {
					%>
					<p class="paragraph">Hai ricevuto le seguenti richieste di
						amicizia:</p>
					<div class="propertyList withImage reducedWidth">
						<%
							for (Object o : friendList) {
										Friendship f = (Friendship) o;
										Customer sender = f.getSender();
										String senderIdentity = sender.getName() + " "
												+ sender.getSurname();
										String thumbnailUrl = ctx
												+ Misc.getThumbnailPhotoUrl(sender);
						%>
						<div class="property">
							<div class="propertyName">
								<span class="smallImageFrame"> <img
									src="<%=thumbnailUrl%>" alt="User image" />
								</span> <span class="afterImage"><a
									href="<%=ctx%>/user/?u=<%=sender.getUsername()%>"><%=senderIdentity%></a></span>
							</div>
							<form action="<%=ctx%>/friends/accept" method="post"
								class="propertyValue">
								<input type="hidden" name="f" value="<%=f.getId()%>" /> <input
									type="submit" class="inputsubmit" value="Accetta" />
							</form>
							<form action="<%=ctx%>/friends/decline" method="post"
								class="propertyValue">
								<input type="hidden" name="f" value="<%=f.getId()%>" /> <input
									type="submit" class="inputsubmit" value="Rifiuta" />
							</form>
						</div>
						<%
							}
						%>
					</div>
					<%
						}
					%>
					<%
						break;
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>