<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="mainbody">
<div class="headersection">
<h1><b>Edit Admin Profile</b></h1>
</div>
<div class="employerRegi">

<c:if test="${not empty editadmin}">
<div class="highlight-3" title="Update Admin Profile">
	Admin Update Successful.
</div>
</c:if>
<c:url var="adminregistrationurl" value="/admin/updateAdmin" />
<form:form method="POST" id="candidate-regi" action="${adminregistrationurl}">
	<!-- <label>UserName :</label> -->
	<input type="hidden" name="userName" id="userName"  value="${user.userName}"/>
	<!-- <label>Password :</label> -->
	<input type="hidden" name="password" id="password"  value="${user.password}"/>
	<label>First Name :</label>
	<input type="text" name="adminFirstName" id="adminFirstName" value="${admin.adminFirstName}" />
	<label>Last Name :</label>
	<input type="text" name="adminLastName" id="adminLastName" value="${admin.adminLastName}" />
	<!-- <label>Email Address :</label> -->
	<input type="hidden" name="email" id="email" value="${user.email}" />
	
	<input type="submit" class="btn" value="Update" style="margin-left:200px;"/>
</form:form>
</div>
</div>