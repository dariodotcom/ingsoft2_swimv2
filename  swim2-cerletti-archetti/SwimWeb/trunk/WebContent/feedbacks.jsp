<%@page import="it.polimi.swim.web.servlets.CustomerWorkrequestServlet"%>
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

	Boolean showReceived = selectedSection
			.equals(CustomerFeedbackSection.RECEIVED_FEEDBACKS);

	String verb = showReceived ? "ricevuti" : "inviati";
	String feedbackClass = showReceived ? "other" : "self";
	String replyClass = showReceived ? "self" : "other";
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
				<div class="monoPageContent">
					<%
						if (feedbackList.size() == 0) {
					%>
					<p class="paragraph">
						Non sono stati ancora
						<%=verb%>
						feedback.
					</p>
					<%
						} else {
					%>
					<p class="paragraph">
						Sono stati
						<%=verb%>
						i seguenti feedbacks
					</p>
					<div class="feedbackList">
						<%
							for (Object o : feedbackList) {
									Feedback f = (Feedback) o;
									WorkRequest relatedWork = f.getLinkedRequest();
									Customer target = showReceived ? relatedWork.getSender()
											: relatedWork.getReceiver();
									String targetDesc = showReceived ? "Autore"
											: "Destinatario";

									String identity = target.getName() + " "
											+ target.getSurname();

									int feedbackMark = f.getMark();
									request.setAttribute(Misc.MARK_VALUE, feedbackMark);
									String reply = f.getReply();
									Boolean showReplyInsertion = (showReceived && reply == null);
									String link = String.format("%s/%s/view?%s=%s", context,
											CustomerWorkrequestServlet.CONTEXT_NAME,
											CustomerWorkrequestServlet.WORK_REQUEST_PARAM,
											relatedWork.getId());
						%>
						<div class="feedback clearfix">
							<p class=heading>
								<span class="bold"><%=targetDesc%>:&nbsp;</span><%=identity%>
								&emsp;<span class="bold">Voto:&nbsp;</span>
								<%@include file="shared/feedbackMarker.jsp"%>
								<a href="<%=link%>" class="button">Visualizza richiesta di
									lavoro</a>
							</p>
							<p class="review">
								<span class="bold">Recensione:</span>&nbsp;<%=f.getReview()%>
							</p>
							<%
								if (showReceived) {
							%>
							<p class="reply">
								<%
									if (reply != null) {
								%>
								<span class="bold">Risposta:</span>&nbsp;<%=reply%>
								<%
									} else if (showReplyInsertion) {
													String onclickFormat = "javascript:quickReply('%s', this, 'reply', {reqId: %s})";
													String action = request.getContextPath()
															+ "/feedbacks/reply";
													String onclick = String.format(onclickFormat,
															action, relatedWork.getId());
								%>
								<a class="button no" href="javascript:" onclick="<%=onclick%>">Rispondi</a>
								<%
									}
								%>
							</p>
							<%
								}
							%>
						</div>
						<%
							}
						%>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>