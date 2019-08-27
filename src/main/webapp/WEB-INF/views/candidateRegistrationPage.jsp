<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/validation.error.css" />" type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery.validation.zipcode.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.3.2.js" />"></script>

<script type="text/javascript">
	$(function() {
		$('#candidateZip').validator({
			format: 'zipUS',
			invalidEmpty: true,
			correct: function() {
				$('#validation_result').text('VALID');
			},
			error: function() {
				$('#validation_result').text('INVALID');
			}
		});
		
		$('#button_validate').click(function(e) {
			$('#candidateZip').validator('validate');
		});
	});
	</script>

<script>
$(function() {
	jQuery("#candidate-regi").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
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
<h1><b>Job Seeker Registration Form</b></h1>
</div>
<div class="candidateRegi">

<c:if test="${not empty message}">
${messageAlert} 
</c:if>

<c:if test="${not empty duplicateuser}">
	<div class="highlight-3" title="Error Candidate Profile">
		 Duplicate username.Please give new username.
	</div>
	</c:if>
<c:if test="${not empty duplicateemail}">
	<div class="highlight-3" title="Error Candidate Profile">
		 Duplicate email.Please give new email.
	</div>
	</c:if>

<c:if test="${not empty registrationerror}">
	<div class="highlight-1">
		Job Seeker Register Not Successful.
	</div>
</c:if>

<c:if test="${not empty registration}">
	<div class="highlight-3">
		Job Seeker Registered Successfully.
	</div>
</c:if>
<c:url var="candidateregistrationurl" value="candidate/saveCandidate" />

<form:form method="POST" id="candidate-regi" action="${candidateregistrationurl}" enctype="multipart/form-data">
	<div>
		<div style="float: left; margin-left: 50px;">
			<label>First Name: *</label>
			<input type="text" name="candidateFirstName" id="candidateFirstName" class="validate[required, custom[onlyLetterSp],maxSize[10]]" />
			<label>Last Name: *</label>
			<input type="text" name="candidateLastName" id="candidateLastName" class="validate[required, custom[onlyLetterSp]]" />
			<label>UserName: *</label>
			<input type="text" name="userName" id="userName" class="validate[required]" />
			<label>Password: *</label>
			<input type="password" name="password" id="password" class="validate[required]" />
			<!-- <label>Candidate Date of Birth</label>
 			<input type="date" name="dob" id="dob"/>  -->
			<label>Email Address: *</label>
			<input type="text" name="email" id="email" class="validate[required,custom[email]]" />
			<label>Primary Phone: *</label>
			<input type="text" name="candidatePrimaryPhone" id="candidatePrimaryPhone" class="validate[required, custom[phone]]" />
			<label>Alternative Phone: </label>
			<input type="text" name="candidateAlthone" id="candidateAltPhone" />
			<label>Street Address: *</label>
			<input type="text" name="address1" id="address1" class="validate[required]"/>
		</div>
		<div style="float: left; margin-left: 50px; clear: right;">
			<label>Street Address (Optional) :</label>
			<input type="text" name="address2" id="address2" />
			<label>City: *</label>
			<input type="text" name="candidateCity" id="candidateCity" class="validate[required]" />
			<label>State: *</label>
			<select name="candidateState" id="select-state">
			<c:forEach var="state" items="${states}" varStatus="status">
				<option value="${state.stateName}">${state.stateName}</option>
			</c:forEach>
			</select>
			<label>Zip Code: *</label>
			<input type="text" name="candidateZip" id="candidateZip" class="validate[required ,funcCall[zipCodeValid]]"/>
			<label>Industry: *</label>
			<select name="jobCategory" id="jobCategory">
				<c:forEach var="category" items="${jobCategories}" varStatus="status">
					<option value="${category.jobCategoryName}">${category.jobCategoryName}</option>
				</c:forEach>
			</select>
			<label>Highest Education Achieved: *</label>
			<select name="highestEducationLevel" id="highestEducaitonLevel" class="validate[required]">
				<option value="No Degree"></option>
				<option value="High School/GED">High School/GED</option>
				<option value="Associate Degree">Associates Degree</option>
				<option value="Bachelors Degree">Bachelors Degree</option>
				<option value="Masters Degree or Higher">Masters Degree or Higher</option>
			</select>
			<label>Salary Level: *</label>
			 <select name="salaryLevel" id="salaryLevel">
				<option value="Negotiable">Negotiable</option>
				<option value="0-30k">0-30k</option> 
				<option value="30k-50k">30k-50k</option>
				<option value="50k-75k">50k-75k</option>
				<option value="75k-100k">75k-100k</option>
				<option value="100k-125k">100k-125k</option>
				<option value="125k-150k">125k-150k</option>
				<option value="150k+">150k+</option>
			 </select> 
			<!-- <input type="text" name="salaryLevel" id="salaryLevel" /> -->
			<label>Resident Status (Optional) :</label>
			<select name="residentStatus" id="residentStatus">
				<option value="US Citizen">US Citizen</option>
				<option value="Green Card/EAD">Green Card/EAD</option>
				<option value="H1B or Other Work Visa">H1B or Other Work Visa</option>
			</select>
			<label>Job Preference: *</label>
			<select name="jobStatus" id="jobStatus">
				<option value="Full Time">Full Time</option>
				<option value="Part Time">Part Time</option>
				<!-- <option value="Contractual">Contractual</option> -->
			</select>
			<label>Desired Position: *(e.g. technician, cashier, analyst)</label>
			<input type="text" name="desiredPosition" id="desiredPosition" class="validate[required]" />
			<label>Job Seeker Skill(s): *(e.g. Oracle, data entry, construction)</label>
			<input type="text" name="candidateSkill" id="candidateSkill" class="validate[required]" />
			<!-- <label>Last School Attended :</label>
			<input type="text" name="lastSchoolAttended" id="lastSchoolAttended" /> -->
			<label> Recent Employer: *</label>
			<input type="text" name="recentEmployer" id="recentEmployer" class="validate[required]" />
			<!-- <label>Recent Job Title :</label>
			<input type="text" name="recentJobTitle" id="recentJobTitle" />
			<label>Recent Job Description :</label>
			<textarea id="recentJobDescription" name="recentJobDescription" rows="24" cols="50"></textarea> -->
			<label for="fileData" >Upload Resume</label>
			<input name="fileData"   id="fileData"  type="file"/>
			<label>Or</label>
			<label>Enter/Paste Resume: </label>
			<textarea id="candidateDescription" name="candidateDescription" rows="24" cols="50" ></textarea>
		</div>
	</div>
	<br/>
	<input type="submit" class='btn' value="Sign Up" style="margin-left: 300px;" />
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


 --%>
 
 
 
 
 
 
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>

<link rel="stylesheet" href="<c:url value="/resources/css/validation.error.css" />" type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery.validation.zipcode.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.3.2.js" />"></script>


<!--
<script type="text/javascript">
	$(function() {
		$('#candidateZip').validator({
			format: 'zipUS',
			invalidEmpty: true,
			correct: function() {
				$('#validation_result').text('VALID');
			},
			error: function() {
				$('#validation_result').text('INVALID');
			}
		});
		
		$('#button_validate').click(function(e) {
			$('#candidateZip').validator('validate');
		});
	});
	</script>
	
 -->

<script>
$(function() {
	jQuery("#candidate-regi").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
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

 <script type="text/javascript">

 $(document).ready(function() {
    $('#sign-up-pass').click(function(event){
    
        data = $('.password').val();
/*         var len = data.length;
        
        if(len < 1) {
            alert("Password cannot be blank");
            // Prevent form submission
            event.preventDefault();
        } */
         
        if($('.password').val() != $('.ConfirmPassword').val()) {
            alert("Password and Confirm Password don't match");
            // Prevent form submission
            event.preventDefault();
        }
         
    });
});

</script>

<div class="mainbody">
    <div class="headersection" id="headerSec">
        <h1><b>Job Seeker Registration Form</b></h1>
    </div>
    <div class="candidateRegi" id="candidateRegi">
        <c:if test="${not empty duplicateuser}">
            <div class="highlight-1" title="Error Candidate Profile">
                 Duplicate username.Please give new username.
            </div>
            </c:if>
        <c:if test="${not empty duplicateemail}">
            <div class="highlight-1" title="Error Candidate Profile">
                 This email address has already been used, please login first to use this email or sign up with a different email address.
            </div>
            </c:if>

        <c:if test="${not empty registrationerror}">
            <div class="highlight-1">
                Job Seeker Register Not Successful.
            </div>
        </c:if>

        <c:if test="${not empty messageAlert}">
            <div class="highlight-1">
                Please upload in doc format.
            </div>
        </c:if>

        <c:if test="${not empty registration}">
            <div class="highlight-3">
                Job Seeker Registered Successfully.
            </div>
        </c:if>
        <c:url var="candidateregistrationurl" value="/candidate/saveCandidate" />

    <form:form method="POST" id="candidate-regi" action="${candidateregistrationurl}" enctype="multipart/form-data">
        <div class="candidateContent">
            <div class="candidateContentSection1">
                <a><b><i>Your personal information will not be sold to any companies</i></b></a>
                <label>First Name: *</label>
                <input type="text" name="candidateFirstName" id="candidateFirstName" value="${candidate.candidateFirstName}" class="validate[required, custom[onlyLetterSp],minSize[2],maxSize[30]]" />
                <label>Last Name: *</label>
                <input type="text" name="candidateLastName" id="candidateLastName" value="${candidate.candidateLastName}" class="validate[required,minSize[2],maxSize[30],custom[onlyLetterSp]]" />
                <label>UserName: *</label>
                <input type="text" name="userName" id="userName" value="${user.userName}" class="validate[required,custom[onlyLetterSp],minSize[2],maxSize[30]]" />
                <label>Password: *</label>
                <input type="password" name="password" id="password" class="validate[required,minSize[3]]" />
                <label>Confirm Password: *</label>
                <input type="password" name="confirmPassword" id="confirmPassword" class="validate[required,minSize[3]]" />
                <!-- <label>Candidate Date of Birth</label>
                <input type="date" name="dob" id="dob"/>  -->
                <label>Email Address: *</label>
                <input type="text" name="email" id="email" value="${user.email}" class="validate[required,custom[email]]" />
                <label>Primary Phone: *</label>
                <input type="text" name="candidatePrimaryPhone" id="candidatePrimaryPhone" value="${candidate.candidatePrimaryPhone}" class="validate[required, custom[phone]]" />
                <label>Alternative Phone: </label>
                <input type="text" name="candidateAlthone" id="candidateAltPhone" value="${candidate.candidateAltPhone}" />
                <label>Street Address: *</label>
                <input type="text" name="address1" id="address1" value="${candidate.address1}" class="validate[required,minSize[3],maxSize[30]]"/>
                <label>Street Address (Optional) :</label>
                <input type="text" name="address2" id="address2" value="${candidate.address2}" class="validate[minSize[3],maxSize[30]]" />
                <label>City: *</label>
                <input type="text" name="candidateCity" id="candidateCity" value="${candidate.candidateCity}" class="validate[required,minSize[3],maxSize[30]]" />
                <label>State: *</label>
                <select name="candidateState" id="select-state">
                    <c:forEach var="state" items="${states}" varStatus="status">
                        <option value="${state.stateName}">${state.stateName}</option>
                    </c:forEach>
                </select>
                <label>Zip Code: *</label>
                <input type="text" name="candidateZip" id="candidateZip" value="${candidate.candidateZip}" class="validate[required,minSize[5],maxSize[5],custom[zipcode]]"/>
            </div>
            <div class="candidateContentSection2">
                <label>Industry: *</label>
                <select name="jobCategory" id="jobCategory">
                    <c:forEach var="category" items="${jobCategories}" varStatus="status">
                        <option value="${category.jobCategoryName}">${category.jobCategoryName}</option>
                    </c:forEach>
                </select>
                <label>Highest Education Achieved: *</label>
                <select name="highestEducationLevel" id="highestEducaitonLevel" class="validate[required]">
                    <option value="No Degree"></option>
                    <option value="High School/GED">High School/GED</option>
                    <option value="Associate Degree">Associates Degree</option>
                    <option value="Bachelors Degree">Bachelors Degree</option>
                    <option value="Masters Degree or Higher">Masters Degree or Higher</option>
                </select>
                <label>Salary Level: *</label>
                 <select name="salaryLevel" id="salaryLevel">
                    <option value="Negotiable">Negotiable</option>
                    <option value="0-30k">0-30k</option>
                    <option value="30k-50k">30k-50k</option>
                    <option value="50k-75k">50k-75k</option>
                    <option value="75k-100k">75k-100k</option>
                    <option value="100k-125k">100k-125k</option>
                    <option value="125k-150k">125k-150k</option>
                    <option value="150k+">150k+</option>
                 </select>
                <!-- <input type="text" name="salaryLevel" id="salaryLevel" /> -->
                <label>Resident Status (Optional) :</label>
                <select name="residentStatus" id="residentStatus">
                    <option value="US Citizen">US Citizen</option>
                    <option value="Green Card/EAD">Green Card/EAD</option>
                    <option value="H1B or Other Work Visa">H1B or Other Work Visa</option>
                </select>
                <label>Job Preference: *</label>
                <select name="jobStatus" id="jobStatus">
                    <option value="Full Time">Full Time</option>
                    <option value="Part Time">Part Time</option>
                    <!-- <option value="Contractual">Contractual</option> -->
                </select>
                <label>Desired Position: (e.g. technician, cashier, analyst)</label>
                <input type="text" name="desiredPosition" id="desiredPosition" value="${candidate.desiredPosition}" class="validate[minSize[2],maxSize[100]]" />
                <label>Job Seeker Skill(s): *(e.g. Oracle, data entry, construction)</label>
                <input type="text" name="candidateSkill" id="candidateSkill" value="${candidate.candidateSkill}" class="validate[required,minSize[1],maxSize[100]]" />
                <!-- <label>Last School Attended :</label>
                <input type="text" name="lastSchoolAttended" id="lastSchoolAttended" /> -->
                <label> Recent Employer: *</label>
                <input type="text" name="recentEmployer" id="recentEmployer" value="${candidate.recentEmployer}" class="validate[required,maxSize[100]]" />
                <!-- <label>Recent Job Title :</label>
                <input type="text" name="recentJobTitle" id="recentJobTitle" />
                <label>Recent Job Description :</label>
                <textarea id="recentJobDescription" name="recentJobDescription" rows="24" cols="50"></textarea> -->
                <label for="fileData" >Upload Resume (only .doc or .docx file accepted)</label>
                <input name="fileData"   id="fileData"  type="file"/>
                <label>Or</label>
                <label>Copy/Paste Resume or add a summary of your work history: </label>
                <textarea id="candidateDescription" name="candidateDescription" value="${candidate.candidateDescription}" rows="24" cols="50" ></textarea>
            </div>
            <div class="clear"></div>
        </div>
        <br/>
        <input type="submit" class='btn' value="Sign Up" id="sign-up-pass" />
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

				<!-- <script type="text/javascript">
				$(document).ready(function() {
				    $('#candidate-regi').bootstrapValidator({
				        message: 'This value is not valid',
				        feedbackIcons: {
				            valid: 'glyphicon glyphicon-ok',
				            invalid: 'glyphicon glyphicon-remove',
				            validating: 'glyphicon glyphicon-refresh'
				        },
				        live: 'enabled',
				        fields: {
				             password: {
				            	validators: {
				            		/*notEmpty: {
				                        message: 'The password is required and cannot be empty'
				                    },
				                    stringLength: {
				                        min: 6,
				                        max: 30,
				                        message: 'The password must be more than 6 and less than 30 characters long'
				                    },*/
				                    identical: {
				                        field: 'confirmPassword',
				                        message: 'The password and its confirm are not the same'
				                    }
				            	} 
				            },
				            confirmPassword: {
				                validators: {
				                    identical: {
				                        field: 'password',
				                        message: 'The password and its confirm are not the same'
				                    }
				                }
				            }
				        }
				    });
				});
				</script>


 --> 
