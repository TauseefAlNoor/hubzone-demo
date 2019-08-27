<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <div class="pagewrapper"> -->
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

 <script type="text/javascript">

 $(document).ready(function() {
    $('#btn').click(function(event){
    
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
		<h1>Employer Registration Form</h1>
	</div>
		<div class="employerRegi">
			<c:if test="${not empty registrationerror}">
				<div class="highlight-1">Employer Registration Not Successful.
				</div>
			</c:if>
			<c:if test="${not empty duplicateuser}">
                <div class="highlight-1" title="Error Employer Profile">
                    Duplicate username.Please give new username.
                </div>
	        </c:if>
	
	        <c:if test="${not empty duplicateemail}">
                <div class="highlight-1" title="Error Employer Profile">
                    This email address has already been used, please login first to use this email or sign up with a different email address.
                </div>
	        </c:if>
	
			<c:if test="${not empty registration}">
				<div class="highlight-3" title="Employer Registration">
					Employer Registration Successful.</div>
			</c:if>
			<c:url var="ermployerregistrationurl" value="/employer/saveEmployer" />
			<form:form method="POST" id="employer-regi" action="${ermployerregistrationurl}">
				<div class="employerContent">
					<div class="employerContentSection1">
						<label>UserName: *</label> 
						<input type="text" name="userName" id="userName" value="${user.userName}" class="validate[required,minSize[2],maxSize[30]]" /> 
						<label>Password: *</label>
						 <input type="password" name="password" id="password" class="validate[required,minSize[3]]" /> 
						 <label>Confirm Password *</label>
						 <input type="password" name="confirmPassword" id="confirmPassword" class="validate[required,minSize[3]]" />
						 <label>Business Name: *</label> 
						 <input type="text" name="companyName" id="CompanyName" value="${employer.companyName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]" /> 
						 <label>StreetAddress1: *</label> 
						 <input type="text" name="companyStreetAddress1" id="CompanyStreetAddress1" value="${employer.companyStreetAddress1}" class="validate[required,minSize[3],maxSize[30]]" /> 
						 <label>StreetAddress2: </label> 
						 <input type="text" name="companyStreetAddress2" id="CompanyStreetAddress2" value="${employer.companyStreetAddress2}" class="validate[minSize[3],maxSize[30]]"/> 
						 <label>City: *</label> 
						 <input type="text" name="companyCity" id="CompanyCity" value="${employer.companyCity}" class="validate[required,minSize[3],maxSize[30]]" /> 
						 <label>Zip: *</label> 
						 <input type="text" name="companyZip" id="CompanyZip" value="${employer.companyZip}" class="validate[required,minSize[5],maxSize[5],custom[zipcode]]" /> 
						 <label>State: *</label> 
						 <select name="companyState" id="select-state" class="validate[required]" >
							<c:forEach var="state" items="${states}" varStatus="status">
								<option value="${state.stateName}">${state.stateName}</option>
							</c:forEach>
						</select>
                    </div>
					<div class="employerContentSection2">
						<label>Admin POC Primary Phone: *</label> 
						<input type="text" name="pocPrimaryPhone" id="POCPrimaryPhone" value="${employer.pocPrimaryPhone}" class="validate[required, custom[phone]]" /> 
						<label>Admin  POC Secondary Phone: </label> 
						<input type="text" name="pocSecondaryPhone" id="POCSecondaryPhone" value="${employer.pocSecondaryPhone}" class="validate[custom[phone]]"/> 
						<label>Admin POC First Name: *</label> 
						<input type="text" name="pocFirstName" id="POCFirstName" value="${employer.pocFirstName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]" /> 
						<label>Admin POC Last Name: *</label> 
						<input type="text" name="pocLastName" id="POCLastName" value="${employer.pocLastName}" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[30]]" />
						<label>Admin POC Email: *</label> 
						<input type="text" name="pocEmail" id="POCEmail" value="${employer.pocEmail}" class="validate[required,custom[email]]" /> 
						<!-- <label>Subscription Level: *</label> --> 
	<!-- 					<select  type="hidden" id="subscriptionLevel" name="subscriptionLevel">
							<option value="monthly">Monthly - $99/mo</option>
							<option value="quarterly">Quarterly- $249/qtr</option>
							<option value="Annual">Annual-$899/yr</option>
						</select>  -->
						<label>Email POC for Jobs: *</label> 
						<input type="text" name="email" id="email" value="${user.email}" class="validate[required,custom[email]]" />
					</div>
                    <div class="clear"></div>
				</div>
				<br />
				<!-- <input type="submit" class='btn' value="Sign Up"
					style="margin-left: 200px;" /> -->
				<input type="submit" class='btn' id="btn" value="Sign Up"/>
			</form:form>
		</div>
	</div>
<!-- </div> -->
