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
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="profileHeader">
				<div class="username headerElem">
					Profilo di <span class="bold"><%=target.getName()%> <%=target.getSurname()%></span>
				</div>
				<div class="userControls headerElem">
					<form>
						<input type="submit" value="Invia richiesta di amicizia"
							class="inputsubmit" />
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
							String link = request.getContextPath() + "/"
									+ GenericProfileServlet.CONTEXT_NAME + "/"
									+ s.getSectionIdentifier() + "?u="
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
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>