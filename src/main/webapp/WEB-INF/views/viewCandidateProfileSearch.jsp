<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" 
           uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="mainbody">
<%-- <c:if test="${empty messageresume}">
<div class="highlight-3" title="Update Employer Profile">
	Delete Resume Failed.
</div>
</c:if> --%>
<c:if test="${not empty messageresume}">
<div class="highlight-3" title="Update Employer Profile">
	Delete Resume Successful.
</div>
</c:if>
	<div class="table-style">
		<h3><b>Job Seeker Profile
<%-- 		<c:if test="${not empty param.position}">  
		 (${param.position} of ${param.totalRecord} )
		 
		</c:if>  --%>
		 </b></h3>
	<%-- 	<c:if test="${empty message}">
			File deletion failed.
		</c:if> --%>
		<c:if test="${empty candidate}">
			File Deleted Successfully. 
		</c:if>
		<c:if test="${not empty candidate}">
		<table>
			<tbody>
				<tr>
				<td></td>
				<!-- <p>&nbsp;&nbsp;&nbsp;&nbsp;</p><td><button type="button" name="back" onclick="history.back()">Back to Search Result</button></td> -->
				<p>&nbsp;&nbsp;&nbsp;&nbsp;</p><td> <a href="<c:url value="/employer/search-resume-result"/>">Back to Search Result</a></td>
				</tr>
				<tr>
					<td><strong>Job Seeker FirstName</strong></td>
					<td><c:out value="${candidate.candidateFirstName}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker LastName</strong></td>
					<td><c:out value="${candidate.candidateLastName}"/></td>
				</tr>
				<%--
				<tr>
					<td><strong>Candidate Date of Birth</strong></td>
					<td><c:out value="${candidate.dob}"/></td>
				</tr>
				--%>
				<tr>
					<td><strong>Job Seeker Address</strong></td>
					<td><c:out value="${candidate.address1}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker Phone Number</strong></td>
					<td><c:out value="${candidate.candidatePrimaryPhone}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker Alternative Phone</strong></td>
					<td><c:out value="${candidate.candidateAltPhone}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker Email</strong></td>
					<td><c:out value="${user.email}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker City</strong></td>
					<td><c:out value="${candidate.candidateCity}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker State</strong></td>
					<td><c:out value="${candidate.candidateState}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker Zip</strong></td>
					<td><c:out value="${candidate.candidateZip}"/></td>
				</tr>
				<%--
				<tr>
					<td><strong>Candidate Email</strong></td>
					<td><c:out value="${candidate.candidateEmail}"/></td>
				</tr>
				--%>
				<tr>
					<td><strong>Highest Educational Level</strong></td>
					<td><c:out value="${candidate.highestEducationLevel}"/></td>
				</tr>
				<tr>
					<td><strong>Salary Level $</strong></td>
					<td><c:out value="${candidate.salaryLevel}"/></td>
				</tr>
				<tr>
					<td><strong>Job Category</strong></td>
					<td><c:out value="${candidate.jobCategory}"/></td>
				</tr>
				<tr>
					<td><strong>Resident Status</strong></td>
					<td><c:out value="${candidate.residentStatus}"/></td>
				</tr>
				<tr>
					<td><strong>Desired Position</strong></td>
					<td><c:out value="${candidate.desiredPosition}"/></td>
				</tr>
				<tr>
					<td><strong>Job Seeker Skill</strong></td>
					<td><c:out value="${candidate.candidateSkill}"/></td>
				</tr>
				<%-- <tr>
					<td><strong>Last School Attended</strong></td>
					<td><c:out value="${candidate.lastSchoolAttended}"/></td>
				</tr> --%>
				<tr>
					<td><strong>Job Seeker Description</strong></td>
					<td><c:out value="${candidate.candidateDescription}"/></td>
				</tr>
				<c:if test="${not empty candicateResume}">
   				<tr>
   					<td><strong>Job Seeker Resume</strong></td>
   					<td>
   						<%-- File Description: ${candicateResume.resumeFileDesc}  <br/>  --%>
         				<a  href='<c:url value="/upload/downloadCv"/>'>Download Resume</a>
   					</td>         			
         		</tr>
         		<tr>
   					<td><strong>Delete Resume</strong></td>
   					<td>
   						<%-- File Description: ${candicateResume.resumeFileDesc}  <br/>  --%>
         				<a  href='<c:url value="/upload/deleteCv"/>'>Delete Resume</a>
   					</td>         			
         		</tr>
         		</c:if>
			</tbody>
		</table>
		<table>
			<tr>
			<td>
			<c:set var="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />
			
			<iframe src="https://docs.google.com/gview?embedded=true&url=${baseURL}/<c:url value='upload/downloadCandidateCv?user=${candidate.candidateID}'/>" style="width:718px; height:700px;" frameborder="0"></iframe> 
			<%-- <embed src="http://docs.google.com/gview?url=${baseURL}/<c:url value='upload/downloadCandidateCv?user=${candidate.candidateID}'/>" width="500" height="375"> --%>
			</td>
			</tr>
		
		</table>
		</c:if>
	</div>
	<div style="float: left; clear: both"></div>
</div>
			