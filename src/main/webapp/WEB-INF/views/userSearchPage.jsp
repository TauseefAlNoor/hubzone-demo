<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>

<div class="mainbody">
<c:if test="${not empty message}">
${message} 
</c:if>
<c:if test="${ empty message}">
	<c:url var="action" value="/admin/user-search" />
	<form:form method="POST" action='${action}'>
		<div>
			<div style="float:left; margin-left:10px;">
				<fieldset>
					<label>Employer Name : </label>
					<input type="text" name="employerID"	id="employerID" />
				</fieldset>
			</div>
			<br/>
			<div style="float: left; clear: both"></div>
			<input type="submit" class="btn " value="Find Employer" style="margin-left: 200px;">
		</div>
	</form:form>
</c:if>

<c:if test="${not empty message}">
${message} 
</c:if>
<c:if test="${not empty searchError}">
<!-- <div class="highlight-1" title="Error Employer Search"> -->
Result not found. 
<!-- </div> -->
</c:if>
<c:if test="${ empty message}">
	<c:url var="action" value="/admin/user-search-email" />
	<form:form method="POST" action='${action}'>
		<div>
			<div style="float:left; margin-left:10px;">
				<fieldset>
					<label>Employer Email : </label>
					<input type="text" name="email"	id="email" />
				</fieldset>
			</div>
			<br/>
			<div style="float: left; clear: both"></div>
			<input type="submit" class="btn " value="Find Employer" style="margin-left: 200px;">
		</div>
	</form:form>
</c:if>


<div class="table-style">
	<h3>Employer: </h3>
	<table>
		<tbody>
			<c:forEach var="admin" items="${employerList}" varStatus="status">
			<tr>
				<td><b>Name: </b><a href="<c:url value="/admin/edit-job-count/${admin.employerID}"/>">${admin.employerID}</a></td>
				<td><b>Number of Job: </b><a href="<c:url value="/admin/edit-job-count/${admin.employerID}"/>">${admin.jobCountLimit}</a><br/></td>
			<%-- 	<td>
					<b>Name: </b>${admin.employerID}<br/>
					<b>Number of Job: </b>${admin.jobCountLimit}<br/>
				</td> --%>
				<td><a href="<c:url value="/admin/delete-employer?employerID=${admin.employerID}"/> "> 
							Delete</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="float: left; clear: both"></div>
</div>

