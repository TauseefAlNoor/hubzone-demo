<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %> --%>

<script>
$(function() {
	jQuery("#adminEmail").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
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
<h1><b>Candidate Email</b></h1>
</div>
<div class="adminEmail">

<c:if test="${not empty messageblock}">
<div class="highlight-3">
${messageblock} 
</div>
</c:if>

<c:if test="${not empty messageblock1}">
<div class="highlight-1">
${messageblock1} 
</div>
</c:if>

<c:url var="formActionUrl" value="send-mass-email" />

<form:form id="adminEmail" action="${formActionUrl}" method="POST" role="form">
	<div>
		<div style="float: left; margin-left: 50px;">
			<label>Subject: *</label>
			<input type="text" name="subject" id="subject" value="${csForm.subject}" class="validate[required]" />
			<label>Description: *</label>
			<textarea id="message" name="message" rows="24" cols="50" value="${csForm.message}" class="validate[required]"></textarea>
		</div>
	</div>
	<br/>
	<input type="submit" class='btn' value="Submit" style="margin-left: 300px;" />
</form:form>
</div>
</div>


