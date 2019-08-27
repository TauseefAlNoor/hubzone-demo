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
	<c:url var="action" value="/jobs/advanceSearch" />
			<div style="width:100%;">			
			<div id="helpAlert2" style="float:right;width:400px;font-size:11px;padding-right:10px;">
			</div>
	<form:form method="POST" action='${action}' style="float:left;width:500px;">
		<div>
			<!-- <div style="float:left; margin-left:10px;"> -->
				<fieldset>
					<legend>Search Jobs</legend>
					<label>Keyword : (e.g. J2EE, Spring, JPA2)</label>
					<input type="text" name="search" id="search" />
					<label>Search Type : </label>
					<select name="searchType" id="searchType">
						<option value="anyword">Match Any Words</option>
						<option value="allword">Match All Words</option>
					</select>
					<label> Job Title : </label>
					<input type="text" name="job.jobTitle"	id="jobTitle" />
				</fieldset>
				<fieldset>
			<legend>Job Preference </legend>
			<label>Only show jobs interested in the selected job preference.</label>
			<input type="checkbox" name="job.jobDuration" value="Full Time"/>Full Time<br/>
			<input type="checkbox" name="job.jobDuration" value="Part Time"/>Part Time<br/> 
			<!-- <input type="checkbox" name="candidate.jobStatus" value="Contractual"/>Contractual<br/> -->
				</fieldset>
				<fieldset>
			<legend>Location Options</legend>			
			<label> Only show job within </label>
			<select name="distance" id="distance">
				<option value=10>10 miles of</option>
				<option value=20>20 miles of</option>
				<option value=30>30 miles of</option>
				<option value=40>40 miles of</option>
				<option value=50>50 miles of</option>
				<option value=60>60 miles of</option>
				<option value=70>70 miles of</option>
				<option value=80>80 miles of</option>
				<option value=90>90 miles of</option>
				<option value=100>100 miles of</option>
			</select>
			<label> City/Zip Code: </label>
			<!-- <input type="text" name="job.jobState" id="jobState" /> -->
			<input type="text" name="location" id="location" placeholder="" />
		</fieldset> 
			<!-- </div> -->
<!-- 			<div style="float:left; margin-left:10px;clear: right;" >				
 -->				<%--  
				<label> Job Category</label>
				<select id="job-category" name="job.jobCategoryName">
					<option value="">Select</option>
					<c:forEach var="category" items="${jobCategories}">
					<option value="${category.jobCategoryName}">${category.jobCategoryName}</option>
					</c:forEach>
				</select>
				--%>
<!-- 			</div>
 -->			<br/>
			<div style="float: left; clear: both"></div>
			<input type="submit" class="btn " value="Find Jobs" style="margin-left: 350px;">
		</div>
	</form:form>
	</div>
</c:if>

<%-- <div class="table-style">
	<h3>Jobs List</h3>
	<table>
		<tbody>
			<!-- 
			<tr>
				<th>Job Title</th>
				<th>Company</th>
				<th>Location</th>
				<th>Date</th>
			</tr>
			 -->
			<c:forEach var="job" items="${joblist}" varStatus="status">
			<tr>
				<td>
					<a class="link-style" href="<c:url value="/jobs/details/${job.jobID}"/>">${job.jobTitle}</a><br>
					${job.employer.companyName}<br>
					${job.days} days left<br>
				</td>
				<td>
					<b>Location: </b>b${job.jobCity} | ${job.jobState}<br/>
					<b>Salary: </b>${job.jobRate}<br/>
					<b>Job Status/Type: </b>${job.jobDuration}<br/>
					<b>Required Skills: </b>${job.jobKeyWord}<br/>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div> --%>
<div style="float: left; clear: both"></div>
</div>

