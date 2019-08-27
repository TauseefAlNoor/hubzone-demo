<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(function() {
	jQuery("#employer-edit").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
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
<h1><b>Edit Company Profile</b></h1>
</div>

<div class="employerRegi">
<c:if test="${not empty editemployer}">
<div class="highlight-3" title="Update Employer Profile">
	Company Update Successful.
</div>
</c:if>
<c:url var="ermployerregistrationurl" value="/employer/updateEmployer" />

<form:form method="POST" id="employer-edit" action="${ermployerregistrationurl}">
	<!-- <label>Employer UserName :</label> -->
	<input type="hidden" name="userName" id="userName"  value="${user.userName}"/>
	<!-- <<label>Employer Password :</label> -->
	<input type="hidden" name="password" id="password"  value="${user.password}"/>
	<label>Business Name :</label>
	<input type="text" name="companyName" id="CompanyName" value="${employer.companyName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]" />
	<label>Company StreetAddress1 :</label>
	<input type="text" name="companyStreetAddress1" id="CompanyStreetAddress1"   value="${employer.companyStreetAddress1}"  class="validate[required]"/>
	<label>Company City :</label>
	<input type="text" name="companyCity" id="CompanyCity" value="${employer.companyCity}" class="validate[required,minSize[3],maxSize[30]]" />
	<label>Company Zip :</label>
	<input type="text" name="companyZip" id="CompanyZip" value="${employer.companyZip}" class="validate[required,minSize[5],maxSize[5],custom[zipcode]]" />
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
	<input type="text" name="companyStreetAddress2" id="CompanyStreetAddress2" value="${employer.companyStreetAddress2}"  />
	<label>Company POCPrimaryPhone :</label>
	<input type="text" name="pocPrimaryPhone" id="POCPrimaryPhone" value="${employer.pocPrimaryPhone}" class="validate[required, custom[phone]]"  />
	<label>Company POCSecondaryPhone :</label>
	<input type="text" name="pocSecondaryPhone" id="POCSecondaryPhone"  value="${employer.pocSecondaryPhone}" class="validate[custom[phone]]"/>
	<label>POC FirstName :</label>
	<input type="text" name="pocFirstName" id="POCFirstName"  value="${employer.pocFirstName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]"/>
	<label>POC LastName :</label>
	<input type="text" name="pocLastName" id="POCLastName" value="${employer.pocLastName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]"/>
	<label>POC Email :</label>
	<input type="text" name="pocEmail" id="POCEmail" value="${employer.pocEmail}" class="validate[required,custom[email]]" />
	<%-- <label>Subscription Level :</label>
	<select id="subscriptionLevel"  name="subscriptionLevel">
		<option value="Daily" ${employer.subscriptionLevel == 'Daily' ? 'selected' : ''}>Daily</option>
		<option value="Weekly"  ${employer.subscriptionLevel == 'Weekly' ? 'selected' : ''}>Weekly</option>
		<option value="Monthly"  ${employer.subscriptionLevel == 'Monthly' ? 'selected' : ''}>Monthly</option>
	</select> --%>
	<!-- <label>Job Email :</label> -->
	<input type="hidden" name="email" id="email"  value="${user.email}" class="validate[required,custom[email]]"/><br/>
	<input type="hidden" id="currentStatus" name="currentStatus" value="${employer.currentStatus}">
	<input type="hidden" id="jobCountLimit" name="jobCountLimit" value="${employer.jobCountLimit}">
	<input type="submit" class='btn' value="Update" />
</form:form>
</div>
</div>

