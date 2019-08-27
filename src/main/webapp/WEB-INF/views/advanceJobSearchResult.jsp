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
</c:if>
<div class="table-style">
	<h3><b>Jobs List</b></h3>
	<table>
		<tbody>
			<c:forEach var="job" items="${joblist}" varStatus="status">
			<tr>
				<td>
					<a class="link-style" href="<c:url value="/jobs/details/${job.jobID}"/>">${job.jobTitle}</a><br>
					${job.employer.companyName}<br>
					<%-- ${job.days} days left<br> --%>
				</td>
				<td>
					<b>Location: </b>${job.jobCity}, ${job.jobState} ${job.jobZip}, USA<br/>
					<b>Salary: </b>${job.jobRate}<br/>
					<b>Job Status/Type: </b>${job.jobDuration}<br/>
					<b>Required Skills: </b>${job.jobKeyWord}<br/>
					<b>Job Posted Date:</b>${job.currentDate}<br/>
				</td>
				<td>
				<b><a href="<c:url value="/apply-for-job/${job.employer.employerID}/${job.jobID}"/> ">Apply Now</a></b><br/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div style="float: left; clear: both"></div>
</div>

