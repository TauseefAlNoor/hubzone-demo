<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>

$(function() {
	$("#lastDate").datepicker({
		changeMonth: true,
        changeYear: true,
        yearRange: "c:c+3",
        defaultDate: new Date(),
        minDate: new Date(),
        maxDate: '+3y',
        dateFormat: "mm-dd-yy"
	});   
});

</script>

<div class="mainbody">
	<h1><b>Edit Posted Job</b></h1>
	<div class="postJob">
		<c:if test="${not empty postnewjob}">
		<div class="highlight-3" title="Post Job">
			<!-- Post job successful. -->
			${message}
		</div>
		</c:if>
		<c:if test="${not empty postnewjobwarn}">
		<div class="highlight-2" title="Post Job">
			<!-- Post job successful. -->
			${message}
		</div>
		</c:if>
		<c:if test="${not empty postnewjoberror}">
		<div class="highlight-1" title="Post Job">
			<!-- Post job successful. -->
			${message}
		</div>
		</c:if>
		<c:url var="postedJobEditUrl" value="/jobs/updateJob" />
		<form:form method="POST" id="update-job" action="${postedJobEditUrl}">
		<input type="hidden" name="jobID" value="${promptJob.jobID }" />
		<label>Job Category: </label>
		<select id="job-category" name="jobCategoryName">
			<c:forEach var="category" items="${jobCategories}">
			<option value="${category.jobCategoryName}" ${category.jobCategoryName == promptJob.jobCategoryName ? 'selected' : ''}>${category.jobCategoryName}</option>
			</c:forEach>
		</select>
		<label>Job Title :</label>
		<input type="text" name="jobTitle" id="jobTitle" value="${promptJob.jobTitle }" />
		<label>Job Skills :</label>
		<input type="text" name="jobKeyWord" id="jobKeyWord" value="${promptJob.jobKeyWord }" />
		<!-- <label>Job Location :</label> -->
		<label>Job City :</label>
		<input type="text" name="jobCity" id="jobCity" value="${promptJob.jobCity }" />
		<label>Job State :</label>
		<select name="jobState" id="select-state">
			<c:forEach var="state" items="${states}" varStatus="status">
			<option value="${state.stateName}" ${state.stateName == promptJob.jobState ? 'selected' : ''}>${state.stateName}</option>
			</c:forEach>
		</select>
		<label>Job Zip Code :</label>
		<input type="text" name="jobZip" id="jobZip" value="${promptJob.jobZip }"/>
		<label>Salary Level :</label>
		<%-- <input type="text" name="jobRate" id="jobRate" value="${promptJob.jobRate}"/> --%>
		<select name="jobRate" id="jobRate">
			<option value="Negotiable" ${promptJob.jobRate == 'Negotiable' ? 'selected' : ''}>Negotiable</option>
			<option value="0-30k" ${promptJob.jobRate == '0-30k' ? 'selected' : ''}>0-30k</option> 
			<option value="30k-50k" ${promptJob.jobRate == '30k-50k' ? 'selected' : ''}>30k-50k</option>
			<option value="50k-75k" ${promptJob.jobRate == '50k-75k' ? 'selected' : ''}>50k-75k</option>
			<option value="75k-100k" ${promptJob.jobRate == '75k-100k' ? 'selected' : ''}>75k-100k</option>
			<option value="100k-125k" ${promptJob.jobRate == '100k-125k' ? 'selected' : ''}>100k-125k</option>
			<option value="125k-150k" ${promptJob.jobRate == '125k-150k' ? 'selected' : ''}>125k-150k</option>
			<option value="150k+" ${promptJob.jobRate == '150k+' ? 'selected' : ''}>150k+</option> 
		 </select> 	
		
		<label>Job Preference :</label>
		<select name="jobDuration" id="jobDuration">
			<option value="Full Time" ${promptJob.jobDuration == 'Full Time' ? 'selected' : ''}>Full Time</option>
			<option value="Part Time" ${promptJob.jobDuration == 'Part Time' ? 'selected' : ''}>Part Time</option>
			<!-- <option>Contractual</option> -->
		</select>
		<label>Job Description :</label>
		<textarea id="jobSummary" name="jobSummary" rows="24" cols="50">${promptJob.jobSummary }</textarea>
		<%-- <label>Expiration Date :</label>
		<input type="text" name="lastDate1" id="lastDate"  value="<fmt:formatDate pattern="MM-dd-yyyy" value="${promptJob.lastDate }" />" /> --%>
		<!-- <label>Salary Level :</label>
			<select name="salaryLevel" id="salaryLevel">
				<option value="Negotiable">Negotiable</option>
				<option value="0-30k">0-200k</option>
				
				<option value="30k-50k">30k-50k</option>
				<option value="50k-75k">50k-75k</option>
				<option value="75k-100k">75k-100k</option>
				<option value="100k-125k">100k-125k</option>
				<option value="125k-150k">125k-150k</option>
				<option value="150k+">150k+</option>
				
		</select> -->
		<br>
		<br>
		<input type="submit" value="Edit Job" />

	</form:form>
</div>
</div>