<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="baseContext" value="/"/>

<html>
<head>
	<script type="text/javascript" src="http://ifrype.com/applications/external/draugiem.js"></script>
	<script type="text/javascript">
			// MAKE SURE THIS IS CHANGED! OR IMPLEMENT MORE SOPHISTICATED SOLUTION!
	        var draugiem_callback_url='http://your-domain.com/callback.html';
	        var draugiem_container = 'container';
	</script>	
</head>
<body>
<div id="container">
	<h2>Draugiem API Examples</h2>
	<h3>Profile</h3>
	<table>
	<tr>
		<td><img src="${user.image}" /></td>
		<td>
			<p>${user.name} ${user.surname} / ${user.age}</p>
			<p>(${user.nick})</p>
			
			<p><strong>${user.place}</strong></p>
		</td>
	</tr>
	</table>
	
	<h3>Notifications</h3>
	<p>Create notification in your profile:</p>
		
	<form action="${baseContext}NotificationServlet" method="post">
		<input type="text" name="text" />
		<input type="submit" /> 
	</form>
	
	<h3>Activity</h3>
	<p>Create activity in your stream:</p>
	
	<form action="${baseContext}ActivityServlet" method="post">
		<input type="text" name="text" />
		<input type="submit" /> 
	</form>
	
	<h3>Application Status</h3>
	<table>
	<tr>
		<td>Users</td>
		<td>${status.users}</td>
	</tr>
	<tr>
		<td>UsersIn24h</td>
		<td>${status.usersIn24h}</td>
	</tr>
	<tr>
		<td>UsersOnline</td>
		<td>${status.usersOnline}</td>
	</tr>
	<tr>
		<td>AppRequests</td>
		<td>${status.appRequests}</td>
	</tr>
	<tr>
		<td>Activities</td>
		<td>${status.activities}</td>
	</tr>
	<tr>
		<td>Notifications</td>
		<td>${status.notifications}</td>
	</tr>
	</table>	
	
	<h3>Application Users</h3>
	<table>
	<tr>
		<td>Image</td>
		<td>UID</td>
		<td>Name</td>
		<td>Surname</td>
		<td>Nick</td>
		<td>Place</td>
		<td>Age</td>
	</tr>
	<c:forEach var="user" items="${users}">
	<tr>
		<td><img src="${user.icon}" /></td>
		<td>${user.id}</td>
		<td>${user.name}</td>
		<td>${user.surname}</td>
		<td>${user.nick}</td>
		<td>${user.place}</td>
		<td>${user.age}</td>
	</tr>
	</c:forEach>
	</table>
</div>

</body>
</html>
