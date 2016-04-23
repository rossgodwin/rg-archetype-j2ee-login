<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up</title>
</head>
<body>

	<form action="signup" method="post">
		<table cellpadding="3pt">
			<tr>
				<td>Username :</td>
				<td><input type="text" name="username" size="30" /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><input type="password" name="password" size="30" /></td>
			</tr>
			<tr>
				<td>Email :</td>
				<td><input type="text" name="email" size="30" /></td>
			</tr>
		</table>
		<br> <input type="submit" value="Sign Up" />
	</form>

</body>
</html>