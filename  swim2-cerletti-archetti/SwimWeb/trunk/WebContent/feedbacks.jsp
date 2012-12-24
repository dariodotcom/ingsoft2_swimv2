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
				<%
					switch (selectedSection) {
					case RECEIVED_FEEDBACKS:
				%>
				<div class="text">Quisque congue auctor magna sed egestas.
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec
					facilisis, felis vel iaculis commodo, tellus tellus euismod mi,
					eget volutpat quam massa id nunc. Sed eget urna a metus luctus
					molestie quis dictum enim. Nulla facilisis tortor vel velit
					volutpat malesuada. Pellentesque pellentesque ligula ac neque
					tincidunt lobortis. Sed malesuada leo et enim scelerisque sed
					congue orci imperdiet. Curabitur id arcu neque. Sed lacinia
					dignissim eros in molestie. Maecenas vehicula iaculis adipiscing.
					Nam mattis, massa nec accumsan imperdiet, nibh tortor feugiat
					neque, vel tincidunt nisl mauris et libero. Vestibulum eget
					imperdiet quam. Nulla facilisi. Donec ut libero tellus. Praesent
					ullamcorper elit diam, sed euismod orci. Integer vel sem lacus.</div>

				<%
					break;
					case SENT_FEEDBACKS:
				%>
				<div class="text">Quisque congue auctor magna sed egestas.
					Vivamus leo arcu, ornare elementum imperdiet a, commodo sit amet
					diam. Pellentesque consequat, quam sit amet commodo dictum, mauris
					lectus fringilla metus, ac tempor mi enim at tellus. Duis in felis
					erat, vel dictum leo. Maecenas semper justo eget dolor ultrices ac
					malesuada sapien commodo. Duis diam elit, placerat ut vestibulum
					vitae, sagittis id velit. In fringilla tincidunt urna vehicula
					porttitor. Nam et quam quis risus faucibus sollicitudin. Ut
					bibendum, ipsum et vulputate faucibus, odio diam blandit enim, at
					pellentesque eros eros ut turpis. Cras hendrerit, augue vitae
					ullamcorper aliquam, risus erat egestas leo, at congue ipsum ipsum
					in ligula. Fusce sapien mauris, consectetur at semper sit amet,
					convallis vel metus. Donec vestibulum justo vel quam laoreet
					imperdiet sed facilisis augue. Nunc ornare augue non sem rhoncus
					condimentum sit amet sit amet lacus. Aenean eget orci hendrerit
					quam fermentum elementum. Etiam nec orci purus, quis dictum nulla.
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