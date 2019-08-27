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
	<c:url var="action" value="/admin/candidate-search" />
	<form:form method="POST" action='${action}'>
		<div>
			<div style="float:left; margin-left:10px;">
				<fieldset>
					<label>Job Seeker Name : </label>
					<input type="text" name="candidateID"	id="candidateID" />
				</fieldset>
			</div>
			<br/>
			<div style="float: left; clear: both"></div>
			<input type="submit" class="btn " value="Find Job Seeker" style="margin-left: 200px;">
		</div>
	</form:form>
</c:if>

<!-- new -->
<c:if test="${not empty message}">
${message} 
</c:if>
<c:if test="${not empty searchError}">
<!-- <div class="highlight-1" title="Error Candidate Search"> -->
Result not found. 
<!-- </div> -->
</c:if>
<c:if test="${ empty message}">
	<c:url var="action" value="/admin/candidate-search-email" />
	<form:form method="POST" action='${action}'>
		<div>
			<div style="float:left; margin-left:10px;">
				<fieldset>
					<label>Job Seeker Email : </label>
					<input type="text" name="email"	id="email" />
				</fieldset>
			</div>
			<br/>
			<div style="float: left; clear: both"></div>
			<input type="submit" class="btn " value="Find Job Seeker" style="margin-left: 200px;">
		</div>
	</form:form>
</c:if>

<!-- end -->


<div class="table-style">
	<h3>Job Seeker: (Total:${usersize})</h3>
	<table>
		<tbody>
			<c:forEach var="admin" items="${candidateList}" varStatus="status">
			
			<tr>
				
				<td><b>User Name: </b><a href="<c:url value="/admin/edit-candidate/${admin.candidateID}"/>">${admin.candidateID}</a></td>
				<td><b>First Name: </b><a href="<c:url value="/admin/edit-candidate/${admin.candidateID}"/>">${admin.candidateFirstName}</a></td>
				<td><b>Last Name: </b><a href="<c:url value="/admin/edit-candidate/${admin.candidateID}"/>">${admin.candidateLastName}</a></td>
				<td><b>City: </b><a href="<c:url value="/admin/edit-candidate/${admin.candidateID}"/>">${admin.candidateCity}</a></td>
				<td><b>State: </b><a href="<c:url value="/admin/edit-candidate/${admin.candidateID}"/>">${admin.candidateState}</a></td>
				<td><b>Zip: </b><a href="<c:url value="/admin/edit-candidate/${admin.candidateID}"/>">${admin.candidateZip}</a></td>
				<%-- <td><b>Number of Job: </b><a href="<c:url value="/admin/edit-job-count/${admin.employerID}"/>">${admin.jobCountLimit}</a><br/></td> --%>
			<%-- 	<td>
					<b>Name: </b>${admin.employerID}<br/>
					<b>Number of Job: </b>${admin.jobCountLimit}<br/>
				</td> --%>
				<td><a href="<c:url value="/admin/delete-candidate?candidateID=${admin.candidateID}"/> "> 
							Delete</a></td> 
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="float: left; clear: both"></div>
</div>

