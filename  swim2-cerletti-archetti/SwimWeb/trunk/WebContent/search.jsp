<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="shared/head.jsp"%>
</head>
<body class="swim">
	<%@include file="shared/header.jsp"%>
	<div id="swimContentContainer">
		<div id="swimContent" class="topWidthElement">
			<div id="formContainer">
				<form id="searchForm"
					action="<%=request.getContextPath() + "/perform"%>" method="post">
					<div class="inputLine">
						<label class="inputLabel" for="name">Nome</label> <input
							type="text" id="name" name="name" class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="surname">Cognome</label> <input
							type="text" id="surname" name="surname" class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="location">Luogo</label> <input
							type="text" id="location" name="location" class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="ability">Abilit&agrave;
							richiesta</label> <input type="text" id="ability" name="ability"
							class="inputtext" />
					</div>
					<div class="inputLine">
						<label class="inputLabel" for="filter">Filtra la ricerca
							tra gli amici</label> <input type="checkbox" name="filter" value="filter" />
					</div>
					<div class="inputLine">
						<input type="submit" value="Cerca" class="inputsubmit" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="shared/footer.jsp"%>
</body>
</html>