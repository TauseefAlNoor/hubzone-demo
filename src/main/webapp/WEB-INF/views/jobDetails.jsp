<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="mainbodyhead">

				<div
					style="min-width: 500px; margin-top: 15px; float: left; -webkit-border-radius: 20px; -moz-border-radius: 20px; border-radius: 20px; border: 3px solid #96C0FF; background: rgba(255, 255, 255, 0.5); -webkit-box-shadow: #B3B3B3 4px 4px 4px; -moz-box-shadow: #B3B3B3 4px 4px 4px; box-shadow: #B3B3B3 4px 4px 4px;">

					<h3><b>Jobs Details</b></h3>
				
					<table width="100%">

						<tbody>
							<tr>
								<td><strong>Job Title</strong></td>
								<td>${job.jobTitle}</td>
							</tr>
							<tr>
								<td><strong>Company</strong></td>
								<td>${job.employer.companyName}</td>
							</tr>
							<tr>
								<td><strong>City</strong></td>
								<td>${job.jobCity}</td>
							</tr>
							<tr>
								<td><strong>Date</strong></td>
								<td><fmt:formatDate pattern="MM-dd-yyyy"  value="${job.lastDate}" /></td>
							</tr>
							<tr>
								<td><strong>Salary</strong></td>
								<td>${job.jobRate}</td>
							</tr>
							<tr>
								<td><strong>Skill</strong></td>
								<td>${job.jobKeyWord}</td>
							</tr>
							<tr>
								<td><strong>Category</strong></td>
								<td>${job.jobCategoryName}</td>
							</tr>
							<tr>
								<td><strong>Duration</strong></td>
								<td>${job.jobDuration}</td>
							</tr>
							<tr>
								<td><strong>Summary</strong></td>
								<td>${job.jobSummary}</td>
							</tr>
							<tr>
								<!-- <td><strong>Remaining Time</strong></td> -->
								<td><strong>Job Posted Date</strong></td>
								<td>${job.currentDate}</td>
							</tr>
							<tr>
							<td><strong>Apply</strong></td>
							<td><a href="<c:url value="/apply-for-job/${job.employer.employerID}/${job.jobID}"/> ">Apply Now</a></td>
							</tr>
							
						</tbody>
					</table>
				</div>
				<div style="float: left; clear: both"></div>
</div>
			
			