<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- <link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/stylesheet.css"/>">

<link rel="stylesheet" href="<c:url value="/resources/css/template.css"/>">


<!-- jQuery -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.8.3.js" />"></script>
<script  src="<c:url value="/resources/js/jquery.nicescroll.js"/>"></script>
<!-- jQueryUI -->
<link rel="stylesheet" href="<c:url value="/resources/css/south-street/jquery-ui-1.9.2.custom.min.css" />" type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-1.9.2.custom.min.js" />"></script>
<!-- Validation Engine -->
<link rel="stylesheet" href="<c:url value="/resources/css/validationEngine.jquery.css" />" type="text/css" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery.validationEngine.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.validationEngine-en.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.highlight-4.js" />"></script> --%>
<!-- Pagination -->
<%-- <link rel="stylesheet" href="<c:url value="/resources/pagination/css/style.css"/>">
<script src="<c:url value="/resources/pagination/js/jquery.pages.js"/>"></script> --%>

<div class="topHeader">
    <p>&nbsp;</p>
</div>
<div class="">
    <img src="/hubzone/resources/images/top-logo.jpg" width="980" height="179"/>
</div>

<div class="top-nav" id="menu">
	<li>
		<a href="<c:url  value="/" />">Home</a>
	</li>
	<security:authorize access="isAnonymous()">
 	 <li>
		<a id="user-registration" href="<c:url value="/user-registration"/>">Employer Sign Up</a>
	</li>  
<%--  	<li>
		<a id="user-registration" href="<c:url value="/employer/employer-temp-page"/>">Employer Sign Up</a>
	</li>  --%>
	<li>
		<a id="candidate-registration" href="<c:url value="/candidate-registration"/>">Job Seeker Sign Up</a>
	</li>
  	 <li>
		<a id="company-profile" href="<c:url value="/login?param=emp"/>">Employer Login</a>
	</li> 
<%-- 	  <li>
		<a id="company-profile" href="<c:url value="/employer/employer-temp-page"/>">Employer Login</a>
	</li>   --%>
	<li>
		<a id="candidate-profile" href="<c:url value="/login?param=cand"/>">Job Seeker Login</a>
	</li>
	<%-- <li>
		<a id="admin-profile" href="<c:url value="/login?param=adm"/>">Administrator Login</a>
	</li> --%>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_EMP')">
	<li>
		<a id="search-resume" href="<c:url value="/employer/search-resume"/>">Search Resumes</a>
	</li>
	<li>
		<a id="post-job" href="<c:url value="/employer/post-job"/>">Post Job</a>
	</li>
	<li>
		<a id="view-company-profile" href="<c:url value="/employer/company-profile"/>">View Profile</a>
	</li>
	<li>
		<a id="edit-company-profile" href="<c:url value="/employer/edit-company-profile"/>">Edit Profile</a>
	</li>
	<li>
		<a id="view-company-profile" href="<c:url value="/employer/job-list-by-employer"/>">View Posted Jobs</a>
	</li>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_CAN')">
	<li>
		<a href="<c:url value="/candidate/candidate-profile"/>">View Profile</a>
	</li>
	<li>
		<a href="<c:url value="/candidate/edit-candiate-profile"/>">Edit Profile</a>
	</li>	
<%-- 	<li>
		<a href="<c:url value="/upload/uploadCv"/>">Upload Resume</a>
	</li> --%>		
	<li>
		<a id="search-job" href="<c:url value="/jobs/advanceSearch"/>">Search Jobs</a>
	</li> 
<%-- 	<li>
		<a id="search-job" href="<c:url value="/jobs/job-temp-page"/>">Search Jobs</a>
	</li> --%>
	 <li>
		<a id="jobCategory" href="<c:url value="/jobCategory"/>">View All Jobs</a>
	</li> 
<%-- 	<li>
		<a id="jobCategory" href="<c:url value="/jobs/job-temp-page"/>">View All Jobs</a>
	</li> --%>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_CAN', 'ROLE_EMP')">
	<li>
		<a href="<c:url value="/logout" />">Logout</a>
	</li>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_ADM')">
	<li>
		<a href="<c:url value="/admin/admin-profile"/>">View Profile</a>
	</li>
	<li>
		<a href="<c:url value="/admin/edit-admin-profile"/>">Edit Profile</a>
	</li>			
	<%-- <li>
		<a href="<c:url value="/admin/edit-job-employer"/>">Edit Job</a>
	</li> --%>
	<li>
		<a href="<c:url value="/admin/user-search" />">Employer Search</a>
	</li>
	<li>
		<a href="<c:url value="/admin/candidate-search" />">Job Seeker Search</a>
	</li>
	<li>
		<a href="<c:url value="/admin/admin-email" />">Email</a>
	</li>
	<li>
		<a href="<c:url value="/logout" />">Logout</a>
	</li>
	</security:authorize>
	<security:authorize access="hasAnyRole('ROLE_CAN', 'ROLE_EMP' , 'ROLE_ADM')">
	</security:authorize>
</div>
