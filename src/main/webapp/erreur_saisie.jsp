<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="loginservlet.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Authentification</title>
	<!-- Inclure les fichiers CSS de Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<!-- Inclure les fichiers JavaScript de Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<style>
/* Ajouter du CSS personnalisé pour le formulaire */
.app-title {
	margin-top:40px;
	margin-bottom: 50px;
	color:  #ff5722;
	text-align:left;
	margin-right: 10%;
	font-family: Arial, sans-serif;
  	font-size: 36px;
  	
}

.logo {
	float: left;
	margin-top: 10px;
	margin-left: 10px;
	max-width: 100px;
}

.body {
	background-color: #FFE5B4;
	  display: flex;
  	align-items: center;
  	justify-content: center;
  	height: 100vh;
}
/* definir la hauteur qui sépare le formulaire du top de la page  */
.card {
	margin-top: 30px;
	margin-bottom:80px;
}
/* Personnaliser la boîte de formulaire */
.auth-box {
	border: 1px solid #ccc;
	background-color: #f8f8f8;
	padding: 20px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

/* Personnaliser les titres de formulaire */
.auth-box legend {
	font-weight: bold;
	color: #333;
}

/* Personnaliser les étiquettes de formulaire */
.auth-box label {
	color: #666;
}

/* Personnaliser les champs de formulaire */
.auth-box input[type="text"], .auth-box input[type="password"] {
	border: 1px solid #ccc;
	border-radius: 3px;
	padding: 5px 10px;
	background-color: #fff;
	color: #333;
}

/* Personnaliser les boutons de formulaire */
.auth-box button[type="submit"] {
	background-color: #ff5722;
	color: #fff;
	border: none;
	border-radius: 3px;
	padding: 10px 20px;
	margin-top: 10px;
	text-align: center;
}

/* Personnaliser les liens de formulaire */
.auth-box a {
	color: #ff5722;
}
</style>
</head>
<body>
	<div class="container">
		<img src="logoo.png" class="logo" alt="Logo">
		
		<h1 class="text-center  app-title"> Bienvenue</h1>
		
		<div class="row">
			<div class="col-md-6 mx-auto">
				<div class="card">
					<div class="card-header">
						Authentification
					</div>
					<div class="card-body">
			        	<p style="color:red;">Nom d'utilisateur ou mot de passe incorrect</p>

						<form class="auth-box" method="get" action="./LoginServlet">
							<div class="form-group">
								<label for="username">Nom d'utilisateur :</label>
								<input type="text" class="form-control" id="username" name="username" placeholder="Entrez votre nom d'utilisateur">
							</div>
							<div class="form-group">
								<label for="password">Mot de passe :</label>
								<input type="password" class="form-control" id="password" name="password" placeholder="Entrez votre mot de passe">
							</div>
							 <div style="text-align: center;">
							<button type="submit" class="btn btn-primary" name="action" value="Connexion">Se connecter</button>
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>



