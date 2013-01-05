<%@page import="it.polimi.swim.business.entity.Feedback"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="it.polimi.swim.business.entity.Message"%>
<%@page import="it.polimi.swim.business.entity.Ability"%>
<%@page import="it.polimi.swim.business.entity.WorkRequest"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	WorkRequest workReq = (WorkRequest) request
			.getAttribute(Misc.TARGET_WORKREQUEST);
	Customer sender = workReq.getSender(), receiver = workReq
			.getReceiver();
	Ability requiredAbility = workReq.getRequiredAbility();

	String selfUsername = (String) session
			.getAttribute(Misc.LOGGED_USERNAME);

	String ctx = request.getContextPath();

	//Find out the state of the request
	Boolean isSender = selfUsername.equals(sender.getUsername());
	Boolean requestConfirmed = workReq.isConfirmed();
	Boolean receiverAcceptance = workReq.isConfirmedByReceiver();
	Boolean senderAcceptance = workReq.isConfirmedBySender();

	Boolean interlocutorDeclined, selfDeclined, interlocutorResponseAwaited, selfResponseAwaited;

	if (isSender) {
		interlocutorDeclined = (receiverAcceptance != null && receiverAcceptance == false);
		interlocutorResponseAwaited = (receiverAcceptance == null);
		selfDeclined = (senderAcceptance != null && senderAcceptance == false);
		selfResponseAwaited = (receiverAcceptance != null
				&& receiverAcceptance == true && senderAcceptance == null);
	} else {
		interlocutorDeclined = (senderAcceptance != null && senderAcceptance == false);
		interlocutorResponseAwaited = (receiverAcceptance != null && senderAcceptance == null);
		selfDeclined = (receiverAcceptance != null && receiverAcceptance == false);
		selfResponseAwaited = (receiverAcceptance == null);
	}

	//Find out if request has been marked as completed
	Boolean requestCompleted = workReq.isCompleted(), recCompl = workReq
			.getReceiverCompleted(), sendCompl = workReq
			.getSenderCompleted();

	Boolean showCompletionControl, otherCompletionAwaited;

	showCompletionControl = requestConfirmed && !requestCompleted
			&& (isSender ? !sendCompl : !recCompl);
	otherCompletionAwaited = requestConfirmed && !requestCompleted
			&& (isSender ? !recCompl : !sendCompl);

	String other = (isSender ? "destinatario" : "mittente");

	String responseAwaitedMessage = (isSender ? "Il destinatario ha accettato la richiesta. Ora tocca a te confermarla prima di cominiciare a lavorare."
			: "Non hai ancora risposto alla richiesta: fallo ora!");

	String greetingMessage = "Hai "
			+ (isSender ? "inviato" : "ricevuto")
			+ " la seguente richiesta di lavoro:";

	String endDate;
	if (workReq.getEndDate() == null) {
		endDate = "Non impostato";
	} else {
		endDate = Misc.DATE_TIME_FORMAT.format(workReq.getEndDate());
	}

	//Feedback
	Feedback feedback = (Feedback) request.getAttribute(Misc.FEEDBACK);
	Boolean hasFeedback = (feedback != null);

	String reply = hasFeedback ? feedback.getReply() : null;
	Boolean hasReply = (reply != null);

	String feedbackMessage = null;

	Boolean showFeedbackInsert = requestCompleted && isSender
			&& !hasFeedback;
	Boolean showFeedbackReply = !isSender && requestCompleted
			&& hasFeedback && !hasReply;

	if (!hasFeedback) {
		feedbackMessage = isSender ? "Una volta completata la richiesta potrai esprimere un parere sull'utente che ha effettuato il lavoro."
				: "Una volta completata la richiesta il mittente potr&agrave; esprimere un parere sul tuo operato.";
	} else if (hasFeedback && !hasReply) {
		feedbackMessage = isSender ? "Hai inviato il seguente feedback. Ora l'altro utente può rispondere."
				: "Hai ricevuto il seguente feedback. Ora puoi rispondere se lo ritieni necesario.";
	} else if (hasFeedback && hasReply) {
		feedbackMessage = "NON LO SO!!!";
	}

	//Put mark in request to include feedbackMarker.jsp
	if (hasFeedback) {
		request.setAttribute(Misc.MARK_VALUE, feedback.getMark());
	}

	//Retrieve message list
	Boolean canSendMessage = !selfDeclined && !interlocutorDeclined
			&& !requestCompleted;
	List<?> messageList = (List<?>) request
			.getAttribute(Misc.MESSAGE_LIST);
	//request.getAttribute(Misc.MESSAGE_LIST);
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<h1 class="pageTitle">Richiesta di lavoro</h1>
			</div>

			<div class="monoPageContent">
				<h2 class="partTitle">Descrizione richiesta</h2>
				<div class="part">
					<p class="paragraph"><%=greetingMessage%></p>
					<div class="propertyList reducedWidth spaceUnder">
						<div class="property">
							<span class="propertyName">Da:</span> <span class="propertyValue">
								<%=sender.getName()%> <%=sender.getSurname()%>
							</span>
						</div>
						<div class="property">
							<span class="propertyName">A:</span> <span class="propertyValue">
								<%=receiver.getName()%> <%=receiver.getSurname()%>
							</span>
						</div>
						<div class="property">
							<span class="propertyName">Per professionalit&agrave;:</span> <span
								class="propertyValue"> <%=requiredAbility.getName()%>
							</span>
						</div>
						<div class="property">
							<span class="propertyName">Inizio:</span> <span
								class="propertyValue"> <%=Misc.DATE_TIME_FORMAT.format(workReq.getStartDate())%>
							</span>
						</div>
						<div class="property">
							<span class="propertyName">Fine:</span> <span
								class="propertyValue"> <%=endDate%>
							</span>
						</div>
						<div class="property">
							<span class="propertyName">Luogo:</span> <span
								class="propertyValue"> <%=workReq.getLocation()%>
							</span>
						</div>
						<div class="property">
							<span class="propertyName">Descrizione:</span> <span
								class="propertyValue"> <%=workReq.getDescription()%>
							</span>
						</div>
					</div>

					<div class="centerText">
						<%
							//Request status informations
							if (requestConfirmed && !requestCompleted) {
						%>
						<p class="paragraph">Richiesta in corso di svolgimento.</p>
						<%
							} else if (interlocutorDeclined) {
						%>
						<p class="paragraph">
							Il
							<%=other%>
							ha rifiutato la richiesta.
						</p>
						<%
							} else if (selfDeclined) {
						%>
						<p>Hai rifiutato la richiesta.</p>
						<%
							} else if (interlocutorResponseAwaited) {
						%>
						<p class="paragraph">
							Il
							<%=other%>
							non ha ancora risposto alla richiesta.
						</p>
						<%
							} else if (selfResponseAwaited) {
						%><p class="paragraph"><%=responseAwaitedMessage%></p>
						<form action="<%=ctx%>/works/respond" method="post"
							class="inlineForm">
							<input type="hidden" name="w" value="<%=workReq.getId()%>" /> <input
								type="hidden" name="a" value="true" /> <input type="submit"
								class="inputsubmit" value="Accetta" />
						</form>
						<form action="<%=ctx%>/works/respond" method="post"
							class="inlineForm">
							<input type="hidden" name="w" value="<%=workReq.getId()%>" /> <input
								type="hidden" name="a" value="false" /> <input type="submit"
								class="inputsubmit" value="Rifiuta" />
						</form>
						<%
							}

							//Request complete
							if (requestCompleted) {
						%>
						<p class="paragraph">La richiesta di lavoro &egrave;
							completata.</p>
						<%
							} else if (showCompletionControl) {
						%>
						<form action="<%=ctx%>/works/complete" method="post"
							class="inlineForm">
							<input type="hidden" name="w" value="<%=workReq.getId()%>" /><input
								type="submit" class="inputsubmit" value="Segna come completata" />
						</form>
						<%
							} else if (otherCompletionAwaited) {
						%>
						<p class="paragraph">
							Hai segnato la richiesta come completata. Ora anche il
							<%=other%>
							deve fare lo stesso.
						</p>
						<%
							}
						%>

					</div>
				</div>

				<!-- Feedback -->
				<h2 class="partTitle">Feedback</h2>
				<div class="part">
					<p class="paragraph"><%=feedbackMessage%></p>
					<%
						if (hasFeedback) {
							//Show feedback
							String authorClass = (isSender ? "self" : "other");
					%>
					<div class="messageContainer clearfix <%=authorClass%>">
						<div class="arrow">&nbsp;</div>
						<div class="message feedbackMessage">
							<%@include file="shared/feedbackMarker.jsp"%>
							<p class="messageText"><%=feedback.getReview()%></p>
						</div>
					</div>
					<%
						}

						if (showFeedbackInsert) {
							//Show form to insert feedback
					%>
					<form action="<%=ctx%>/works/insertfeedback" method="post">
						<p class="paragraph">
							<label for="feedbackReview">Esprimi un parere
								sull'operato dell'utente.</label>
						</p>
						<div class="messageContainer clearfix self">
							<div class="arrow">&nbsp;</div>
							<div class="message feedbackMessage">
								<input type="hidden" name="w" value="<%=workReq.getId()%>" /> <input
									type="hidden" name="mark" value="" id="feedbackReview" />
								<div id="marker">
									<a href="javascript:" class="markExpression">&nbsp;</a> <a
										href="javascript:" class="markExpression">&nbsp;</a> <a
										href="javascript:" class="markExpression">&nbsp;</a> <a
										href="javascript:" class="markExpression">&nbsp;</a> <a
										href="javascript:" class="markExpression">&nbsp;</a>
								</div>
								<textarea name="review" id="feedbackReview" class="messageInput"></textarea>
								<div class="submitLine">
									<input type="submit" class="inputsubmit" value="Invia feedback"></input>
								</div>
							</div>
						</div>
					</form>
					<%
						}

						if (showFeedbackReply) {
					%>
					<div class="messageContainer clearfix self">
						<div class="arrow">&nbsp;</div>
						<div class="message feedbackReply">
							<form action="<%=context%>/works/replyfeedback" method="post">
								<input type="hidden" name="w" value="<%=workReq.getId()%>" />
								<textarea name="reply" class="messageInput"></textarea>
								<div class="submitLine">
									<input type="submit" class="inputsubmit" value="Rispondi"></input>
								</div>
							</form>
						</div>
					</div>
					<%
						}

						if (hasReply) {
					%>
					<div class="messageContainer clearfix self">
						<div class="arrow">&nbsp;</div>
						<div class="message feedbackReply">
							<p class="messageText"><%=reply%></p>
						</div>
					</div>
					<%
						}
					%>
				</div>

				<!-- Conversation -->
				<h2 class="partTitle">Conversazione</h2>
				<div class="part">
					<div class="messageList">
						<%
							if (messageList.size() == 0) {
						%>
						<p class="paragraph">Nessun messaggio scambiato</p>
						<%
							} else {

								for (Object o : messageList) {
									Message message = (Message) o;
									Boolean selfIsAuthor = message.getSender().getUsername()
											.equals(selfUsername);
									String messageClassName = (selfIsAuthor ? "self" : "other");
						%>
						<div class="messageContainer clearfix <%=messageClassName%>">
							<div class="arrow">&nbsp;</div>
							<div class="message">
								<p class="messageDate"><%=Misc.DATE_TIME_FORMAT.format(message
							.getSentDate())%></p>
								<p class="messageText"><%=message.getMessage()%></p>
							</div>
						</div>
						<%
							}
							}

							//Put form to send message
							if (canSendMessage) {
						%>
						<div class="messageContainer clearfix self">
							<div class="arrow">&nbsp;</div>
							<div class="message">
								<form action="<%=ctx%>/works/sendmsg" method="post">
									<input type="hidden" name="w" value="<%=workReq.getId()%>" />
									<p>
										<label class="sendText" for="messageText">Invia un
											messaggio:</label>
									</p>
									<textarea name="messageText" id="messageText"></textarea>
									<div class="submitLine">
										<input type="submit" class="inputsubmit"
											value="Invia messaggio"></input>
									</div>
								</form>
							</div>
						</div>
						<%
							} else {
						%>
						<p class="paragraph">Non &egrave; più possibile inviare
							messaggi.</p>
						<%
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>