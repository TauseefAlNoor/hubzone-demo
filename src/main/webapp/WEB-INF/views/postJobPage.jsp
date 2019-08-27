<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- <script type="text/javascript">
$(function() {
	$("#postJobForm").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
	
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

function isInputNumber(field, rules, i, options){
	// This will allow for decimals, signs, and 
	// even scientific notation on the end if you want. 
	var numberRegex = /^[+-]?\d+(\.\d+)?([eE][+-]?\d+)?$/;
	if(numberRegex.test(field.val())) {
		return options.allrules.numberInput.alertText;
	}
} 
</script> 
 -->
 
 <script>
$(function() {
	jQuery("#postJobForm").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
	});
	
function isInputNumber(field, rules, i, options){
	// This will allow for decimals, signs, and 
	// even scientific notation on the end if you want. 
	var numberRegex = /^[+-]?\d+(\.\d+)?([eE][+-]?\d+)?$/;
	if(numberRegex.test(field.val())) {
		return options.allrules.numberInput.alertText;
	}
}
</script>

<div class="mainbody">
	<div class="headersection">
		<h1><b>Post Job</b></h1>
	</div>
	<div class="postJob">
		<c:if test="${not empty postnewjob}">
		<div class="highlight-3" title="Post Job">
			<!-- Post job successful. -->
			${message}
		</div>
		</c:if>
		<c:if test="${not empty postnewjobwarn}">
		<div class="highlight-2" title="Post Job">
			${message}
		</div>
		</c:if>		
		<c:if test="${not empty postnewjoberror}">
		<div class="highlight-1" title="Post Job">
			<!-- Post job successful. -->
			${message}
		</div>
		</c:if>

		<c:url var="postJobUrl" value="/employer/postJob" />
		<form:form method="POST" id="postJobForm" action="${postJobUrl}">
			<label>Job Category: *</label>
			<select id="job-category" name="jobCategoryName">
			<c:forEach var="category" items="${jobCategories}">
				<option value="${category.jobCategoryName}">${category.jobCategoryName}</option>
			</c:forEach>
			</select>
			<label>Job Title: *</label>
			<input type="text" name="jobTitle" id="jobTitle" class="validate[required, custom[onlyLetterSp],minSize[2],maxSize[30]]" />
			<label>Job Skills: *</label>
			<input type="text" name="jobKeyWord" id="jobKeyWord" class="validate[required, custom[onlyLetterSp],minSize[2],maxSize[30]]" />
			<!-- <label>Job Location :</label> -->
			<label>Job City: *</label>
			<input type="text" name="jobCity" id="jobCity" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]" />
			<label>Job State: *</label>
			<select name="jobState" id="select-state">
			<c:forEach var="state" items="${states}" varStatus="status">
				<option value="${state.stateName}">${state.stateName}</option>
			</c:forEach>
			</select>
			<label>Job Zip Code: *</label>
			<input type="text" name="jobZip" id="jobZip" class="validate[required,minSize[5],maxSize[5],custom[zipcode]]" />
			<label>Salary Level: *</label>
			<select name="jobRate" id="jobRate">
				<option value="Negotiable" selected="selected">Negotiable</option>
				<option value="0-30k">0-30k</option> 
				<option value="30k-50k">30k-50k</option>
				<option value="50k-75k">50k-75k</option>
				<option value="75k-100k">75k-100k</option>
				<option value="100k-125k">100k-125k</option>
				<option value="125k-150k">125k-150k</option>
				<option value="150k+">150k+</option>
			</select>
			<!-- <input type="text" name="jobRate" id="jobRate" /> -->		
			<label>Job Preference: *</label>
			<select name="jobDuration" id="jobDuration">
				<option value="Full Time" selected="selected">Full Time</option>
				<option value="Part Time">Part Time</option>
				<!-- <option>Contractual</option> -->
			</select>
			<label>Job Description: *</label>
			<textarea id="jobSummary" name="jobSummary" rows="24" cols="50" class="validate[required]" ></textarea>
			<!-- <label>Expiration Date: *</label>
			<input type="text" name="lastDate1" id="lastDate"class="validate[required]" />	 -->		
			<br><br>
			<input type="submit" value="Post Job" />
		</form:form>
	</div>
</div>