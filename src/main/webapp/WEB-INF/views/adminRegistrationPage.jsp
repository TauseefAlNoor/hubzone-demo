<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="mainbody">
<div class="headersection">
<h1><b>Job Seeker Registration Form</b></h1>
</div>
<div class="candidateRegi">

<c:if test="${not empty registrationerror}">
	<div class="highlight-1">
		Admin Register Not Successful.
	</div>
</c:if>

<c:if test="${not empty registration}">
	<div class="highlight-3">
		Admin Registered Successfully.
	</div>
</c:if>
<c:url var="adminregistrationurl" value="admin/saveAdmin" />

<form:form method="POST" id="admin-regi" action="${adminregistrationurl}">
	<div>
		<div style="float: left; margin-left: 10px;">
			<label>First Name :</label>
			<input type="text" name="adminFirstName" id="adminFirstName" />
			<label>Last Name :</label>
			<input type="text" name="adminLastName" id="adminLastName" />
			<label>UserName:</label>
			<input type="text" name="userName" id="userName" />
			<label>Password:</label>
			<input type="password" name="password" id="password" />
				</div>
	</div>
	<br/>
	<input type="submit" class='btn' value="Sign Up" style="margin-left: 300px;" />
</form:form>
</div>
</div>
		