<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="controler.*"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<title>Display Text File</title>
</head>
<body>
	<form method="get" enctype="multipart/form-data"
		action="./LoginServlet">
		<div class="form-group">
			<label for="file">Import File:</label> 
			<input type="file"
				class="form-control-file" name="file" id="file"> 
				<label
				for="separator">Select a separator:</label> 
				<select name="separator"
				id="separator">
				<option>Select a separator</option>
				<option value="\t">Tabulation (\t)</option>
				<option value=";">Semicolon (;)</option>
			</select>
		</div>
		<button type="submit" class="btn btn-primary" name="action"
			value="Import">Orowan</button>
	</form>

	<% String mainPath = Orowan_Controler.getInstance().getMainPath(); %>
	<div>
		<h1><%= mainPath %></h1>
		<img alt="Image" src="plot_1939351_F3.txt.png" width="700px" height="600px" />
	
	</div>
</body>
</html>
