<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" type="text/css" href="Page.css">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ballonfahrten Tirol</title>
</head>

<script>
	function loadDaten() {
		var xhttp = new XMLHttpRequest();
		console.log("Button wurde gedrückt");
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				var arr = JSON.parse(this.responseText);
				drawTableFlug(arr);
				//drawTabelleBuchung();
				
			}
		};
		xhttp.open("GET", "FlugServlet", true);
		xhttp.send();
	}

	function drawTableFlug(arr) {
		var s = " ";
		s += "<table class=\"names\" >";
		s+= "<tr>"+"<th>Flug</th>"+"<th>Personen Anzahl</th>"+"<th>Ort</th>"+"<th>Pilot</th>"+"<th>Preis pro Flug</th>"+"<th>Verfügbare Plätze</th>"+"<th>Zeitpunkt des Fluges</th> </tr>";
		var i;
		for (i = 0; i < arr.length; i++) {
			var row = arr[i];
		
			s += "<tr>";
			s += "<td >";
			s += row.flugID;
			s += " ";
			s += "</td>";
			s += " ";
			s += "<td >";
			s += row.anzPers;
			s += " ";
			s += "</td>";
			s += "<td >";
			s += row.ort;
			s += " ";
			s += "</td>";
			s += " ";
			s += "<td >";
			s += row.pilotID;
			s += " ";
			s += "</td>";
			s += " ";
			s += "<td >";
			s += row.preis;
			s += " ";
			s += "</td>";
			s += " ";
			s += "<td>";
			s += row.platze;
			s += " ";
			s += "</td>";
			s += " ";
			s += "<td >";
			s += row.zeitpunkt;
			s += " ";
			s += "</td>";
			s += " ";
			s += "</tr>";
		}
		s += "</table>";
		
		console.log(s);
		console.dir(s);
		document.getElementById("flug").innerHTML = s;
	}
</script>

<body>

	<div id="hintergrund">

	<% //out response, request, session
	
	String name; 
	
	if( request.getAttribute("usr") == null){
		name = "Achtung nicht angemeldet";
		response.sendRedirect("LoginPage.jsp");
	}else{
		name = (String) request.getAttribute("usr");
	}
	
	%>
	
<h1> Willkommen <%= name %></h1>


		<h1>Bitte füllen Sie hier ihre Daten ein um Ihren Rundflug zu Buchen:</h1>

		<form align="center">

			Vorname: <input type="text" id="vorname" min="2" max="50"
				maxlength="50" /> Nachname: <input type="text" id="nachname"
				min="1" max="60" maxlength="60" /> Datum Flug: <input type="date"
				name="dateFlug"> E-Mail: <input type="email" id="email"
				min="7" max="70" maxlength="70" /> Orte: <select name="Orte">

				<option value="Reutte">Reutte</option>
				<option value="Tanheim">Tanheim</option>
				<option value="Lechtal" selected>Lechtal</option>
				<option value="Ehrwald">Ehrwald</option>
			</select> 
			<br>
			 <input type="radio" name="za" value="Barzahlung" align="center"
				checked> Barzahlung <br> 
			<input type="radio" name="za"
				value="Banküberweisung" align="center"> Banküberweisung <br>

			<button type="button" onclick="loadDaten()" align="center"> Buchen </button>

		</form>

<h1>Flüge: </h1>

		<p id="flug"></p>

<h1>Ihre Buchung: </h1>

		<p id="buchung"></p>

	</div>

</body>
</html>