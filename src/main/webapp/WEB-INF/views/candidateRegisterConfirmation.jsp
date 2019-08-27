<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:if test="${not empty error}">
	${error}          
</c:if>

<div class="mainbodytext">

<c:if test="${not empty message}">
<p>Congratulations on being a part of HUBZone Talent.  To login into your account please click to <a href="<c:url value="/login?param=cand"/>">Job Seeker Login</a>.</p>
</c:if>
</div>