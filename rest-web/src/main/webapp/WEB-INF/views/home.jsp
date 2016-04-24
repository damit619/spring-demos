<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<script src="<c:url value="/resources/jquery/jquery-1.11.1.js"/>"></script>
	<title>Home</title>
</head>
<body>
<h1>
</h1>

<script type="text/javascript">
var serverUrl = 'http://localhost:8080/rest';
var id;
	function populateUsers() {
		$.ajax({
			url : serverUrl + '/user',
			type : 'GET',
			dataType : 'json',
			contentType : 'application/json',
			success : function (data) {
				//alert('success + data = ' + data);
				var content = '';
				for(var i = 0; i < data.length; i++) {
					content+='<tr>';
					content+='<td>' + data[i].id + '</td>';
					content+='<td>' + data[i].firstName + '</td>';
					content+='<td>' + data[i].lastName + '</td>';
					content+='<td>' + data[i].address + '</td>';
					content+='<td><a href="#" onClick="javascript:deleteUser(' + data[i].id + ')">Delete</a></td>';
					content+='<td><a href="#" onClick="javascript:updateUser(' + data[i].id + ')">Update</a></td>';
					content+='</tr>';
					$('#dataTable tbody').html(content);
				}
			}
		});
	}
	window.onload = function () {
		populateUsers();	
	};
	
	function deleteUser (id) {
		$.ajax({
			url : serverUrl + '/user/' + id,
			type : 'DELETE',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				var successMsg = 'User with id ' + data + ' successfully deleted';
				$('#msg').val(successMsg);
				populateUsers();
			}
		});
	}

	function addUser() {
		var firstName = $('#firstName').val();
		var lastName = $('#lastName').val();
		var country = $('#country').val();
		var uid = $('#uid').val();
		var jsonData = {'id' : uid ,'firstName' : firstName, 'lastName' : lastName, 'address' : country};
		$('#uid').val(null);
		var addUrl = serverUrl + '/user';
		$.ajax({
			url : addUrl,
			type : 'PUT',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(jsonData),
			success : function (user) {
				$('#msg').val('Employee added successfully' + user.firsName);
				populateUsers();
			},
		});
		return false;
	}
	
	function updateUser(id) {
				
		var updateUrl = serverUrl + '/user/' + id;
		$.ajax({
			url : updateUrl,
			type : 'GET',
			dataType : 'json',
			contentType : 'application/json',
			success : function (user) {
				$('#uid').val(user.id)
				$('#firstName').val(user.firstName);
				$('#lastName').val(user.lastName);
				$('#country').val(user.address);
			}
		});
	}
	
</script>

<form action="add" id="form1">

	<input type="hidden" id="uid" name="uid"/>
	<label>FirstName</label>
	<input type="text" id="firstName" name="firstName"/>
	<label>LastName</label>
	<input type="text" id="lastName" name="lastName"/>
	<label>Country</label>
	<input type="text" id="country" name="country"/>
	
	<input type="button" id="send" value="Go" onClick="javascript:addUser();" />

</form>

<p id="msg"> </p>
<table id="dataTable">

	<thead>
		<tr>
			<td>Id</td>
			<td>FirstName</td>
			<td>LastName</td>
			<td>Country</td>
			<td>Delete</td>
			<td>Update</td>
		</tr>
	</thead>
	<tbody>
	
	
	</tbody>
</table>
</body>
</html>
