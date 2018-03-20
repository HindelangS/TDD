<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" type="text/css" href="Page.css">
<head>
	<title>Login Ballonfahrten</title>
	<meta charset="UTF-8">
<head>

<body>
	<div id="hintergrund">
		<form method="POST" action="/UebungBallon/LoginServlet">
			<p>Name: </p>
			<input type='text' name='usr' align='center' /> </br>
			<p>Passwort: </p>
			<input type='password' name='pwd' align='center'/> </br> 
			<br>
			<input type='submit' value='Anmelden'>
		</form>
	</div>
</body>
</html>