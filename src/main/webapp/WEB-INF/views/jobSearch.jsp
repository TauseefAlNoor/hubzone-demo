<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>
<div class="mainbody">


				<div
					style="min-width: 500px; margin-top: 15px; float: left; -webkit-border-radius: 20px; -moz-border-radius: 20px; border-radius: 20px; border: 3px solid #96C0FF; background: rgba(255, 255, 255, 0.5); -webkit-box-shadow: #B3B3B3 4px 4px 4px; -moz-box-shadow: #B3B3B3 4px 4px 4px; box-shadow: #B3B3B3 4px 4px 4px;">

					<h3><b>Jobs List</b></h3>
				
					<table>

						<tbody>
							<tr>
								<th>Job Title</th>
								<th>Company</th>
								<th>Location</th>
								<th>Date</th>
								<th>Refresh All Jobs</th>
							</tr>
							<c:forEach var="job" items="${joblist}" varStatus="status">
								<tr>
									<td><a href="<c:url value="/jobs/details/${job.jobID}"/>">${job.jobTitle}</a></td>
									<td>${job.employer.companyName}</td>
									<td>${job.jobCity}</td>
									<td>${job.lastDate}</td>
									<td>${job.days}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div style="float: left; clear: both"></div>
			
</div>			