<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="mainbody">
<div class="headersection">
<h1><b>Job Applied Confirmation</b></h1>
</div>
<c:if test="${not empty message}">
<div class="highlight-3">
${message} 
</div>
</c:if>
<c:if test="${ empty message}">
<div class="highlight-1">
Job Applied Failed
</div>
</c:if>
</div>