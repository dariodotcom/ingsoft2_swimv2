<%@page import="it.polimi.swim.business.entity.Friendship"%>
<%@page import="it.polimi.swim.web.pagesupport.Misc.FriendshipStatus"%>
<%@page import="it.polimi.swim.web.servlets.GenericProfileServlet"%>
<%@page
	import="it.polimi.swim.web.servlets.GenericProfileServlet.GenericProfileSection"%>
<%@page import="it.polimi.swim.web.servlets.CustomerFeedbackServlet"%>
<%@page
	import="it.polimi.swim.web.servlets.CustomerFeedbackServlet.CustomerFeedbackSection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	Object ref = request.getAttribute(Misc.SELECTED_SECTION_ATTR);
	GenericProfileSection selectedSection = (ref != null ? (GenericProfileSection) ref
			: GenericProfileSection.PROFILE);

	request.setAttribute(Misc.PAGE_TITLE_ATTR,
			selectedSection.getSectionName());

	Customer target = (Customer) request
			.getAttribute(Misc.USER_TO_SHOW);

	FriendshipStatus status = (FriendshipStatus) request
			.getAttribute(Misc.FRIENDSHIP_STATUS);

	List<?> friendList = (List<?>) request
			.getAttribute(Misc.FRIENDLIST_ATTR);

	int friendCount = friendList.size();

	String ctx = request.getContextPath();
	String targetIdentity = String.format("%s %s", target.getName(),
			target.getSurname());
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="profileHeader">
				<div class="username headerElem">
					Profilo di <span class="bold"><%=targetIdentity%></span>
				</div>
				<div class="userControls headerElem">
					<form action="<%=ctx%>/user/sendfriendship" method="post">
						<input type="hidden" name="u" value="<%=target.getUsername()%>" />
						<input type="submit" value="<%=status.getButtonText()%>"
							class="inputsubmit <%=status.getButtonClass()%>" />
					</form>
				</div>
				<div class="userControls headerElem">
					<form>
						<input type="submit" value="Invia richiesta di lavoro"
							class="inputsubmit" />
					</form>
				</div>
			</div>
			<div id="leftColumn" class="column">
				<ul id="swimSecondaryMenu">
					<%
						for (GenericProfileSection s : GenericProfileSection.values()) {
							String link = ctx + "/" + GenericProfileServlet.CONTEXT_NAME
									+ "/" + s.getSectionIdentifier() + "?u="
									+ target.getUsername(), name = s.getSectionName();
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
						case PROFILE:
					%>
					<%@include file="shared/userProfileDetails.jsp"%>

					<%
						break;
						case FEEDBACKS:
					%>
					Quisque congue auctor magna sed egestas. Vivamus leo arcu, ornare
					elementum imperdiet a, commodo sit amet diam. Pellentesque
					consequat, quam sit amet commodo dictum, mauris lectus fringilla
					metus, ac tempor mi enim at tellus. Duis in felis erat, vel dictum
					leo. Maecenas semper justo eget dolor ultrices ac malesuada sapien
					commodo. Duis diam elit, placerat ut vestibulum vitae, sagittis id
					velit. In fringilla tincidunt urna vehicula porttitor. Nam et quam
					quis risus faucibus sollicitudin. Ut bibendum, ipsum et vulputate
					faucibus, odio diam blandit enim, at pellentesque eros eros ut
					turpis. Cras hendrerit, augue vitae ullamcorper aliquam, risus erat
					egestas leo, at congue ipsum ipsum in ligula. Fusce sapien mauris,
					consectetur at semper sit amet, convallis vel metus. Donec
					vestibulum justo vel quam laoreet imperdiet sed facilisis augue.
					Nunc ornare augue non sem rhoncus condimentum sit amet sit amet
					lacus. Aenean eget orci hendrerit quam fermentum elementum. Etiam
					nec orci purus, quis dictum nulla.
					<%
						break;
						case FRIENDS:

							if (friendList.size() == 0) {
					%>
					<p class="text"><%=targetIdentity%>
						non ha nessun amico. Non vuoi essere tu il primo?
					</p>
					<%
						} else {
					%>
					<p class="paragraph"><%=targetIdentity%>
						ha
						<%=friendCount%>
						amic<%=friendCount == 1 ? "o" : "i"%>:
					</p>
					<div class="list">
						<%
							for (Object o : friendList) {
										Friendship f = (Friendship) o;
										Customer sender = f.getSender(), receiver = f
												.getReceiver(), friend = (sender.getUsername()
												.equals(target.getUsername()) ? receiver
												: sender);

										String friendIdentity = String.format("%s %s",
												friend.getName(), friend.getSurname());
						%>
						<div class="listEntry friendship">
							<div class="smallImageFrame">
								<img src="<%=ctx%>/resources/user-img.png" alt="User image" />
							</div>
							<span class="username"><a
								href="<%=ctx%>/user/?u=<%=friend.getUsername()%>"><%=friendIdentity%></a></span>
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