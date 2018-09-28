<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Walmart</title>
</head>
<body>
    <h1>Seja Bem-vindo!</h1>
	<form action="Compra" method="post">
	    Escolha o produto: <input type="text" id="tipo" name="tipo" size="5" ><br>
	    Quantidade: <input type="text" id="qtdCompra" name="qtdCompra" size="5" ><br>
	    <input type="submit" id="comprar" value="Comprar" />
	</form>
</body>
</html>