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
	Boolean requestCompleted = workReq.isCompleted(), recCompl = Misc
			.boolValueOf(workReq.getSenderCompleted()), sendCompl = Misc
			.boolValueOf(workReq.getSenderCompleted());

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
						<span class="propertyName">Per abilità:</span> <span
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

					<p>Richiesta in corso di svolgimento.</p>
					<%
						} else if (interlocutorDeclined) {
					%>
					<p>
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
					<p>
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
					<p class="paragraph">La richiesta di lavoro è completata.</p>
					<%
						} else if (showCompletionControl) {
					%>
					<form action="<%=ctx%>/works/respond" method="post"
						class="inlineForm">
						<input type="hidden" name="w" value="<%=workReq.getId()%>" /><input
							type="submit" class="inputsubmit" value="La richiesta è completa" />
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
		</div>
	</div>
</body>
</html>