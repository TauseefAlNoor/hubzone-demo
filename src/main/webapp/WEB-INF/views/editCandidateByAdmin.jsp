<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>

<script>
$(function() {
	jQuery("#candidate-editing").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
	});
	
/* function isInputNumber(field, rules, i, options){
	// This will allow for decimals, signs, and 
	// even scientific notation on the end if you want. 
	var numberRegex = /^[+-]?\d+(\.\d+)?([eE][+-]?\d+)?$/;
	if(numberRegex.test(field.val())) {
		return options.allrules.numberInput.alertText;
	} */
function zipCodeValid(field, rules, i, options){
	// This will allow for decimals, signs, and 
	// even scientific notation on the end if you want. 
	var numberRegex = /^(\d{5})(-\d{4})?$/;
	if(numberRegex.test(field.val())) {
		return options.allRules.zipCodeValidate.alertText;
	}
}
</script>

<div class="mainbody">
<div class="headersection">
<h1><b>Update Job Seeker Profile</b></h1>
</div>

<div class="employerRegi">
<c:if test="${not empty message}">
	<div class="highlight-3" title="Update Candidate Profile">
		 ${message}
	</div>
	</c:if>
<c:url var="jobcounturl" value="/admin/update-candidate/${user.userName}" />

<form:form method="POST" id="candidate-editing" action="${jobcounturl}" enctype="multipart/form-data">

	<%-- <label>Job Limit :</label>
	<input type="text" name="jobCountLimit" id="jobCountLimit" value="${candidate.jobCountLimit}"  /> --%>
	<!-- <label>UserName :</label> -->
	<input type="hidden" name="userName" id="userName"  value="${user.userName}" class="validate[required,minSize[2],maxSize[30]]"/>
	<%-- <label>Password :</label>--%>
	<input type="hidden" name="password" id="password"  value="${user.password}" class="validate[required,minSize[3]]"/> 
	<label>First Name :</label>
	<input type="text" name="candidateFirstName" id="candidateFirstName" value="${candidate.candidateFirstName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]"/>
	<label>Last Name :</label>
	<input type="text" name="candidateLastName" id="candidateLastName" value="${candidate.candidateLastName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]" />
	<%--
	<label>Candidate Date of Birth</label>
	<input type="date" name="dob" id="dob" value="${candidate.dob}" />
	--%>
	<!-- <label>Email Address :</label> -->
	<input type="hidden" name="email" id="jobsEmail" value="${user.email}" class="validate[required,custom[email]]" />
	<label>Primary Phone :</label>
	<input type="text" name="candidatePrimaryPhone" id="candidatePrimaryPhone" value="${candidate.candidatePrimaryPhone}" class="validate[required, custom[phone]]"/>
	<label>Alternative Phone :</label>
	<input type="text" name="candidateAltPhone" id="candidateAltPhone" value="${candidate.candidateAltPhone}" class="validate[custom[phone]]"/>
	<label>Street Address :</label>
	<input type="text" name="address1" id="address1" value="${candidate.address1}" class="validate[required,minSize[3],maxSize[30]]"/>
	<label>Street Address : (Optional)</label>
	<input type="text" name="address2" id="address2" value="${candidate.address2}" class="validate[minSize[3],maxSize[30]]"/>
	<label>City :</label>
	<input type="text" name="candidateCity" id="candidateCity" value="${candidate.candidateCity}" class="validate[required,minSize[3],maxSize[30]]"/>
	<label>State :</label>
	<select name="candidateState" id="candidateState">
		<c:forEach var="state" items="${states}" varStatus="status">
		<option value="${state.stateName}" ${state.stateName == candidate.candidateState ? 'selected' : ''}>${state.stateName}</option>
		</c:forEach>
	</select>
	<label>Zip Code :</label>
	<input type="text" name="candidateZip" id="candidateZip" value="${candidate.candidateZip}" class="validate[required,minSize[5],maxSize[5],custom[zipcode]]"/>
	<label>Job Category :</label>
	<select name="jobCategory" id="select-job">
		<c:forEach var="category" items="${jobCategories}" varStatus="status">
		<option value="${category.jobCategoryName}" ${category.jobCategoryName == candidate.jobCategory ? 'selected' : ''}>${category.jobCategoryName}</option>
		</c:forEach>
	</select>
	<label>Highest Education Achieved :</label>
	<select name="highestEducationLevel" id="highestEducaitonLevel">
		<option value="No Degree" ${candidate.highestEducationLevel == 'No Degree' ? 'selected' : ''}></option>
		<option value="High School/GED" ${candidate.highestEducationLevel == 'High School/GED' ? 'selected' : ''}>High School/GED</option>
		<option value="Associate Degree" ${candidate.highestEducationLevel == 'Associate Degree' ? 'selected' : ''}>Associates Degree</option>
		<option value="Bachelors Degree" ${candidate.highestEducationLevel == 'Bachelors Degree' ? 'selected' : ''}>Bachelors Degree</option>
		<option value="Masters Degree or Higher" ${candidate.highestEducationLevel == 'Masters Degree or Higher' ? 'selected' : ''}>Masters Degree or Higher</option>
	</select>
	<label>Salary Level : </label>
	 <select name="salaryLevel" id="salaryLevel">
		<option value="Negotiable" ${candidate.salaryLevel == 'Negotiable' ? 'selected' : ''}>Negotiable</option>
		<option value="0-30k" ${candidate.salaryLevel == '0-30k' ? 'selected' : ''}>0-30k</option> 
		<option value="30k-50k" ${candidate.salaryLevel == '30k-50k' ? 'selected' : ''}>30k-50k</option>
		<option value="50k-75k" ${candidate.salaryLevel == '50k-75k' ? 'selected' : ''}>50k-75k</option>
		<option value="75k-100k" ${candidate.salaryLevel == '75k-100k' ? 'selected' : ''}>75k-100k</option>
		<option value="100k-125k" ${candidate.salaryLevel == '100k-125k' ? 'selected' : ''}>100k-125k</option>
		<option value="125k-150k" ${candidate.salaryLevel == '125k-150k' ? 'selected' : ''}>125k-150k</option>
		<option value="150k+" ${candidate.salaryLevel == '150k+' ? 'selected' : ''}>150k+</option> 
	 </select> 	
	<%-- <input type="text" name="salaryLevel" id="salaryLevel" value="${candidate.salaryLevel}" /> --%>
	<label>Resident Status : (Optional)</label>
	<select name="residentStatus" id="residentStatus">
		<option value="US Citizen" ${candidate.residentStatus == 'US Citizen' ? 'selected' : ''}>US Citizen</option>
		<option value="Green Card/EAD" ${candidate.residentStatus == 'Green Card/EAD' ? 'selected' : ''}>Green Card/EAD</option>
		<option value="H1B or Other Work Visa" ${candidate.residentStatus == 'H1B or Other Work Visa' ? 'selected' : ''}>H1B or Other Work Visa</option>
	</select>
	<label>Desired Position :</label>
	<input type="text" name="desiredPosition" id="desiredPosition" value="${candidate.desiredPosition}" class="validate[required,minSize[2],maxSize[100]]"/>
	<label>Job Seeker Skill :</label>
	<input type="text" name="candidateSkill" id="candidateSkill" value="${candidate.candidateSkill}" class="validate[required,minSize[2],maxSize[100]]"/>
	<%-- <label>Last School Attended :</label>
	<input type="text" name="lastSchoolAttended" id="lastSchoolAttended" value="${candidate.lastSchoolAttended}"/> --%>
	<label>Job Preference :</label>
	<select name="jobStatus" id="jobStatus">
		<option value="Fulltime" ${candidate.jobStatus == 'Fulltime' ? 'selected' : ''}>Full Time</option>
		<option value="Parttime" ${candidate.jobStatus == 'Parttime' ? 'selected' : ''}>Part Time</option>
		<%-- <option value="Contractual" ${candidate.jobStatus == 'Contractual' ? 'selected' : ''}>Contractual</option> --%>
	</select>
	<label> Recent Employer :</label>
	<input type="text" name="recentEmployer" id="recentEmployer" value="${candidate.recentEmployer}" class="validate[required,minSize[2],maxSize[100]]"/>
	<%-- <label>Recent Job Title :</label>
	<input type="text" name="recentJobTitle" id="recentJobTitle" value="${candidate.recentJobTitle}"/>
	<label>Recent Job Description :</label>
	<textarea id="recentJobDescription" name="recentJobDescription" rows="24" cols="50">${candidate.recentJobDescription}</textarea> --%>
	<label>Job Seeker Description :</label>
	<textarea name="candidateDescription" id="candidateDescription" rows="24" cols="50">${candidate.candidateDescription}</textarea>
	
	<label for="fileData" >Upload Resume (only .doc or .docx file accepted)</label>
	<input name="fileData"   id="fileData"  type="file"/>
	
	<input type="submit" class="btn" value="Update" style="margin-left:200px;"/>
	<!-- <input type="submit" class='btn' value="Update" /> -->
</form:form>
</div>
</div>


<script type="text/javascript">
function Checkfiles()
{
	
	
	
var fup = document.getElementById('fileData');
var fileName = fup.value;
var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
if(ext == "pdf" || ext == "PDF" || ext == "doc" || ext == "docx")
{
return true;
} 
else
{

document.getElementById('message').innerHTML = '<b>Upload pdf or doc file only</b>';
fup.focus();
return false;
}
}
</script>

