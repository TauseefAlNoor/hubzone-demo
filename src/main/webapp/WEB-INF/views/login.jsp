<!-- This JSP page displays the login form -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
.login-header {
	font-size: 20px;
	font-style: bold;
	text-align: center;
    text-transform: uppercase;
    padding-top: 30px;
    color: #0000ff;
    border-bottom: 1px solid #000000;
}
</style>

<script>
$(function() {
	jQuery("#login").validationEngine('attach', {promptPosition : "centerRight", scroll: false});
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
	<div id="form">
		<!-- <p> <strong>Please enter your login credentials </strong>  </p> -->
		<div id="employer-login" class="login-header">${loginHeader}</div>
		    <c:if test="${not empty error}">
            <div class="errorblock">
                Invalid Username or Password.<br /> <%-- Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].error} --%>
            </div>
		</c:if>
		<c:if test="${not empty messageSent}">
            <div class="highlight-3" title="Update Employer Profile">
                ${messageSent}<br />
            </div> <%-- Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].error} --%>
		</c:if>
		<c:if test="${not empty messageFail}">
            <div class="highlight-1" title="Update Employer Profile">
                ${messageFail}<br />
            </div> <%-- Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].error} --%>
		</c:if>
		<h1 class="h1Header">Please Enter Your Login Credentials</h1>
        <div class="loginContent">
            <form id="login" method="POST" action="<c:url value="j_spring_security_check"/>">
                <div class="message">&nbsp;</div>
                <div class="error">&nbsp;</div>
                <div class="loginContentSection">
                    <label class="loginContentSection1Label" for="j_username">Username:</label>
                    <input class="loginContentSection1Input"
                                type='text' id="j_username" name='j_username' class="validate[required]" />
                </div>
                <div class="clear"></div>
                <div class="loginContentSection">
                    <label class="loginContentSection2Label"
                                for="j_password">Password:</label>
                    <input class="loginContentSection2Input"
                                type="password" id="j_password" name="j_password"  class="validate[required]" />
                </div>
                <a class="loginContentSection" id="forgot-password" href="<c:url value="/forgot-password"/>">Forgot Password</a>
                <div class="clear"></div>
                <div class="loginContentSection" id="rememberMe">
                    Remember me <input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" />
                </div>
                <div class="clear"></div>
                <input id="loginContentSection" type="submit" class='btn' value="Login"/>
            </form>
        </div>
		<c:if test="${not empty loginFooter1}">
		<div class="loginContentSection">Not a Member? <a href="<c:url value="/user-registration"/>">Please click here to sign-up</a></div>
		</c:if>
		
		<c:if test="${not empty loginFooter2}">
		<div class="loginContentSection">Not a Member? <a href="<c:url value="/candidate-registration"/>">Please click here to sign-up</a></div>
		</c:if>
		
	<%-- 	<c:if test="${not empty L}">
		<div>Not a Member? <a href="<c:url value="/login?param=emp"/>">Please click here to sign-up</a></div>
		</c:if> --%>
	</div>
</div>

