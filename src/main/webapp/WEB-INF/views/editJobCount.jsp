<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(function() {
	jQuery("#employer-regi").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
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
<h1><b>Edit Employer Profile</b></h1>
</div>

<div class="employerRegi">
<c:if test="${not empty editjobcount}">
<div class="highlight-3" title="Update Job Count">
	Edit Candidate Successful.
</div>
</c:if>
<c:url var="jobcounturl" value="/admin/update-job-count/${user.userName}" />

<form:form method="POST" id="employer-regi" action="${jobcounturl}">

	<!-- <label>Employer UserName :</label> -->
	<input type="hidden" name="userName" id="userName"  value="${user.userName}" class="validate[required,minSize[2],maxSize[30]]"/>
	<%-- <label>Employer Password :</label>--%>
	<input type="hidden" name="password" id="password"  value="${user.password}" class="validate[required,minSize[3]]"/>
	<label>Company Name :</label>
	<input type="text" name="companyName" id="CompanyName" value="${employer.companyName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]"/>
	<label>Company StreetAddress1 :</label>
	<input type="text" name="companyStreetAddress1" id="CompanyStreetAddress1"   value="${employer.companyStreetAddress1}" class="validate[required,minSize[3],maxSize[30]]" />
	<label>Company City :</label>
	<input type="text" name="companyCity" id="CompanyCity" value="${employer.companyCity}" class="validate[required,minSize[3],maxSize[30]]" />
	<label>Company Zip :</label>
	<input type="text" name="companyZip" id="CompanyZip" value="${employer.companyZip}"  class="validate[required,minSize[5],maxSize[5],custom[zipcode]]" />
	<label>Company State :</label>
	<select name="companyState" id="select-state">
		<c:forEach var="state" items="${states}" varStatus="status">
		<option value="${state.stateName}" ${state.stateName == employer.companyState ? 'selected' : ''}>${state.stateName}</option>
		</c:forEach>
	</select>
	<%--
	<form:select path="companyState">
        <form:options items="${states}"  itemValue="stateName" itemLabel="stateName" />
    </form:select>
    --%>
	<label>Company StreetAddress2 : (Optional)</label>
	<input type="text" name="companyStreetAddress2" id="CompanyStreetAddress2" value="${employer.companyStreetAddress2}"  class="validate[minSize[3],maxSize[30]]"/>
	<label>Company POCPrimaryPhone :</label>
	<input type="text" name="pocPrimaryPhone" id="POCPrimaryPhone" value="${employer.pocPrimaryPhone}"  class="validate[required, custom[phone]]"/>
	<label>Company POCSecondaryPhone :</label>
	<input type="text" name="pocSecondaryPhone" id="POCSecondaryPhone"  value="${employer.pocSecondaryPhone}" class="validate[custom[phone]]"/>
	<label>POC FirstName :</label>
	<input type="text" name="pocFirstName" id="POCFirstName"  value="${employer.pocFirstName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]"/>
	<label>POC LastName :</label>
	<input type="text" name="pocLastName" id="POCLastName" value="${employer.pocLastName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]"/>
	<label>POC Email :</label>
	<input type="text" name="pocEmail" id="POCEmail" value="${employer.pocEmail}" class="validate[required,custom[email]]"/>
	<%-- <label>Subscription Level :</label>
	<select id="subscriptionLevel"  name="subscriptionLevel">
		<option value="Daily" ${employer.subscriptionLevel == 'Daily' ? 'selected' : ''}>Daily</option>
		<option value="Weekly"  ${employer.subscriptionLevel == 'Weekly' ? 'selected' : ''}>Weekly</option>
		<option value="Monthly"  ${employer.subscriptionLevel == 'Monthly' ? 'selected' : ''}>Monthly</option>
	</select> --%>
	<!-- <label>Job Email :</label> -->
	<input type="hidden" name="email" id="email"  value="${user.email}" class="validate[required,custom[email]]"/><br/>
	<label>Company Status :</label>
	<select name="currentStatus" id="currentStatus">
		<option value="Active" ${employer.currentStatus == 'Active' ? 'selected' : ''}>Active</option>
		<option value="Inactive" ${employer.currentStatus == 'Inactive' ? 'selected' : ''}>Inactive</option>
	</select>
	<label>Job Limit :</label>
	<input type="text" name="jobCountLimit" id="jobCountLimit" value="${employer.jobCountLimit}" class="validate[required,custom[onlyNumberSp]]"/>

	<input type="submit" class='btn' value="Update" />
</form:form>
</div>
</div>

