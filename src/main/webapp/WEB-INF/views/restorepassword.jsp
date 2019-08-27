<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="mainbody">
<%-- <c:if test="${ not empty error   }">
${error}  
</c:if> --%>
<%-- <c:if test="${not empty message}">
${message} <br> <a href='<c:url value="/login?param=${param}" />' >Login </a>
</c:if>
 --%>
 <c:if test="${not empty successblock}">
	<div class="highlight-3" title="restore password">
		${successblock}<br />
	</div> <%-- Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].error} --%>
</c:if>
<c:if test="${not empty errorblock}">
	<div class="highlight-1" title="restore password">
		${errorblock}<br />
	</div> <%-- Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].error} --%>
</c:if>
<c:if test="${not empty warningblock}">
	<div class="highlight-1" title="restore password">
		${warningblock}<br />
	</div> <%-- Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].error} --%>
</c:if>


<c:if test="${ empty successblock}">

	<c:url var="action" value="/restore-password" />
	<form:form method="POST" id="forgotpass" action='${action}'>

		<input type="hidden" value="${session}" name="session" />
		<input type="hidden" value="${email}" name="email" />
		<div id="forgot-password">
			<label for="passord">Enter New Password</label> <input type="password"
				name="password" id="passord" /> <label>Confirm Password</label> <input
				type="password" name="confirmPassword" id="confirmPassword" /> <input
				type="submit" id="forgot-password-submit" value="submit">
		</div>
	</form:form>
</c:if>
</div>