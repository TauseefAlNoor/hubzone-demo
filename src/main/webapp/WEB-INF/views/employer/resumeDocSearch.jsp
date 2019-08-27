<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security" %>
<%@ page session="false" %>

<div class="mainbody">
<h1><b>Search Resume</b></h1>
<c:if test="${not empty message}">
${message}
</c:if>
<c:if test="${ empty message}">
	<c:url var="action" value="/employer/search-doc-resume" />
	<form:form method="POST" id="search-resume" action='${action}'>
		<fieldset>
			<legend>Search By Any Keyword</legend>
			<label>Keyword: (e.g. J2EE, Spring, JPA2)</label> 
			<input type="text" name="serachKeyword" id="serachKeyword" />
			
		</fieldset>
		
		<input type="submit" class="btn " value="Quick Search" style="margin-left:350px;">
	</form:form>
</c:if>

<div style="float: left; clear: both"></div>
</div>
