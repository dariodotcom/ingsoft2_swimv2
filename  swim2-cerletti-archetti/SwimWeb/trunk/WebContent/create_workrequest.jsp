<%@page import="it.polimi.swim.business.entity.Ability"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	Customer receiver = (Customer) request
			.getAttribute(Misc.USER_TO_SHOW);
	List<?> abilityList = (List<?>) request
			.getAttribute(Misc.ABILITY_LIST);
	String ctx = request.getContextPath();
%>
<%@ include file="shared/head.jsp"%>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">

			<div class="pageHeading">
				<%@include file="shared/messageNotifier.jsp"%>
				<h1 class="pageTitle">Modulo di richiesta di lavoro</h1>
			</div>

			<div class="monoPageContent">
				<div class="propertyList reducedWidth">
					<form action="<%=ctx%>/user/createWorkrequest" method="post">
						<input type="hidden" name="target"
							value="<%=receiver.getUsername()%>" />
						<div class="property">
							<span class="propertyName">A:</span> <span class="propertyValue">
								<%=receiver.getName()%>&nbsp; <%=receiver.getSurname()%>
							</span>
						</div>
						<div class="property">
							<label class="propertyName">Professionalit&agrave;
								richiesta*:</label>
							<div class="propertyValue">
								<select name="abilitySelection" class="inputselect">
									<%
										for (Object o : abilityList) {
											Ability a = (Ability) o;
											String option = a.getName();
									%>
									<option value="<%=option%>"><%=option%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
						<div class="property">
							<label for="startDate" class="propertyName">Data inizio*
								(gg,mm,aa):</label>
							<div class="propertyValue">
								<input type="text" name="startDate" id="startDate"
									class="inputtext" />
							</div>
						</div>
						<div class="property">
							<span class="propertyName">Ora inizio* (hh:mm):</span>
							<div class="propertyValue">
								<input type="text" id="startTime" name="startTime"
									class="inputtext" />
							</div>
						</div>
						<div class="property">
							<span class="propertyName">Data fine (gg,mm,aa):</span>
							<div class="propertyValue">
								<input type="text" id="endDate" name="endDate" class="inputtext" />
							</div>
						</div>
						<div class="property">
							<span class="propertyName">Ora fine (hh:mm):</span>
							<div class="propertyValue">
								<input type="text" id="endTime" name="endTime" class="inputtext" />
							</div>
						</div>
						<div class="property">
							<span class="propertyName">Luogo*:</span>
							<div class="propertyValue">
								<input type="text" id="location" name="location"
									class="inputtext" />
							</div>
						</div>
						<div class="property">
							<span class="propertyName">Descrizione*:</span>
							<div class="propertyValue">
								<input type="text" id="description" name="description"
									class="inputtext" />
							</div>
						</div>
						<div class="submitLine">
							<input type="submit" class="inputsubmit" value="Invia" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>