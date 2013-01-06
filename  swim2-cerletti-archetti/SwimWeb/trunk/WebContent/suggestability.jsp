<%@page import="it.polimi.swim.business.entity.AbilityRequest"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	List<?> abilityReqList = (List<?>) request
			.getAttribute(Misc.ABILITY_LIST);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div class="pageHeading">
				<%@include file="shared/messageNotifier.jsp"%>
				<h1 class="pageTitle">Richiedi nuova professionalit&agrave;</h1>
			</div>
			<div class="monoPageContent">
				<p class="paragraph">Richiedi una nuova professionalit&agrave;.
					La tua richiesta sarà valutata da un amministratore del sistema.</p>

				<h2 class="partTitle">Richiedi professionalit&agrave;</h2>
				<div class="part">
					<form action="<%=context%>/abilityrequest/suggest" method="post">
						<div class="propertyList reducedWidth">
							<div class="property">
								<label class="propertyName" for="abilityName">Nome:</label>
								<div class="propertyValue">
									<input type="text" class="inputtext" name="name"
										id="abilityName" />
								</div>
							</div>
							<div class="property">
								<label class="propertyName" for="abilityDescription">Descrizione:</label>
								<div class="propertyValue">
									<input type="text" class="inputtext" name="description"
										id="abilityDescription" />
								</div>
							</div>
							<div class="submitLine">
								<input type="submit" class="inputsubmit" value="Invia" />
							</div>
						</div>
					</form>
				</div>
				<h2 class="partTitle">Richieste inviate</h2>
				<div class="part">
					<%
						if (abilityReqList.size() == 0) {
					%>
					<p class="paragraph">Non hai inviato alcuna richiesta.</p>
					<%
						} else {
					%>
					<div class="propertyList">
						<%
							for (Object o : abilityReqList) {
									AbilityRequest req = (AbilityRequest) o;
									String className, text;
									if (req.getApproved() == null) {
										text = "In attesa di revisione";
										className = "";
									} else {
										Boolean accepted = req.getApproved();
										className = accepted ? "accepted" : "rejected";
										text = String.format("Richiesta %s (%s)",
												accepted ? "accettata" : "respinta",
												req.getReview());
									}
						%>
						<div class="property">
							<div class="propertyName">
								<span class="bold">Nome:</span>&nbsp;<%=req.getAbilityName()%>
								&emsp; <span class="bold">Descrizione:</span>&nbsp;<%=req.getAbilityDescription()%>
							</div>
							<span class="propertyValue <%=className%>"> <%=text%></span>
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
		<%@include file="shared/footer.jsp"%>
	</div>
</body>
</html>