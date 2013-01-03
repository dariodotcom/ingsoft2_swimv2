<%@page import="it.polimi.swim.business.entity.WorkRequest"%>
<%@page import="it.polimi.swim.business.entity.Feedback"%>
<%@page import="java.util.List"%>
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
	CustomerFeedbackSection selectedSection = (ref != null ? (CustomerFeedbackSection) ref
			: CustomerFeedbackSection.RECEIVED_FEEDBACKS);

	request.setAttribute(Misc.PAGE_TITLE_ATTR,
			selectedSection.getSectionName());

	List<?> feedbackList = (List<?>) request
			.getAttribute(Misc.FEEDBACK_LIST);
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="leftColumn" class="column">
				<ul id="swimSecondaryMenu">
					<%
						for (CustomerFeedbackSection s : CustomerFeedbackSection.values()) {
							String link = request.getContextPath() + "/"
									+ CustomerFeedbackServlet.CONTEXT_NAME + "/"
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
				<%
					switch (selectedSection) {
					case RECEIVED_FEEDBACKS:
				%>
				<div class="list">
					<%
						if (feedbackList.size() == 0) {
					%>
					<p class="text">Non sono stati ancora ricevuti feedback.</p>
					<%
						} else {
								for (Object o : feedbackList) {
									Feedback f = (Feedback) o;
									WorkRequest relatedWork = f.getLinkedRequest();
									int mark = f.getMark();
								}
							}
					%>
				</div>
				<%
					break;
					case SENT_FEEDBACKS:
				%>
				<div class="list">
					<%
						if (feedbackList.size() == 0) {
					%>
					<p class="text">Non sono stati ancora inviati feedback.</p>
					<%
						} else {
								for (Object o : feedbackList) {
									Feedback f = (Feedback) o;
									WorkRequest relatedWork = f.getLinkedRequest();
									int mark = f.getMark();
								}
							}
					%>
				</div>
				<%
					break;
					}
				%>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>