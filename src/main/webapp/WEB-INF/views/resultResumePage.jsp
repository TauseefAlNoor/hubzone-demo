<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>


<div class="mainbody" id="highlight-word">
	<div class="table-style">
		<h3>
			<b>Job Seeker List</b>
		</h3>
	<!-- <div id="pageNavPosition"></div> -->	
	<a href="<c:url value="/employer/search-resume"/>">Back to Search Page</a>
		<table id="resultsForPagination">

			<tbody>
				<tr>
					<!-- 
				<th>Candidate Name</th>  
				<th>Location</th>
				<th>Email</th>
				<th>Education</th>
				<th>Skill</th>
				 -->
				
				</tr>
				<c:forEach var="cand" items="${candList}" varStatus="status">
				
					<tr>
					<c:url value="candidate-profile-search" var="url">
							<c:param name="CandidateID" value="${cand.candidateID}" />
						</c:url> 
						
						<%-- <p>&nbsp;&nbsp;&nbsp;&nbsp;</p><td> <a href="<c:url value="/employer/search-resume"/>">Back to Search Page</a></td> --%>
						
						<td><strong> <a class="link-style"
								href="<c:url value="${url}"/>"> <c:out
										value="${cand.candidateFirstName}" /> <c:out
										value="${cand.candidateLastName}" />
							</a>
						</strong><br /> <b>City: </b>${cand.candidateCity}<br /> <b>State: </b>
							${cand.candidateState}<br /> <b>Phone Number:</b>${cand.candidatePrimaryPhone}<br />
							<b>Alternative Phone Number:</b>${cand.candidateAltPhone}<br />
							 <b>Address:</b>${cand.address1}<br />
							</td>

						<td><b>ZIP:</b>${cand.candidateZip}<br />
						<b>Job Category: </b>${cand.jobCategory}<br />
						<b>Resume Updated: </b>${cand.lastUpdateDate}<br /> <b>Desired
								Salary: </b>${cand.salaryLevel}<br /> <b>Desired Position: </b>${cand.desiredPosition}<br />
							<b>Most Recent Employer: </b>${cand.recentEmployer}<br /></td>
						<td><b>Skills: </b>${cand.candidateSkill}<br /> <%-- ${cand.candidateDescription}<br/> --%>
							<b>Highest Educational Level:</b>${cand.highestEducationLevel} <br />
							<b>Resident Status:</b>${cand.residentStatus}<br /> <b>Job
								Preference:</b>${cand.jobStatus}<br /></td>

						<td>
						<c:if test="${cand.resumeFileLocation !=null}">
						<br />
						<br /> <a
							href="<c:url value="/upload/downloadCandidateCv?user=${cand.candidateID}"/> ">
								Download Resume</a>
						</c:if>
						<c:if test="${cand.resumeFileLocation ==null}">
						<br />
						<br /> <a>No Resume Uploaded</a>
						</c:if>
						</td>

					</tr>
					
				</c:forEach>

 		</table>
<div id="pageNavPosition"></div><a><b>(Total:${totalResult})</b></a>
<script type="text/javascript">
var pager = new Pager('resultsForPagination', 10); 
pager.init(); 
pager.showPageNav('pager', 'pageNavPosition'); 
pager.showPage(1);
</script>
 		
	</div>
</div>