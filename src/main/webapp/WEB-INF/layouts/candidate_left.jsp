<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

	<h4>Search <a href='<c:url  value="/jobs/advanceSearch" />'> (Advance)</a></h4>
	<form action='<c:url value="/jobs/search"/>' class="s">
		<input name="search" type="text" value="${ not empty param.search  ?  param.search : ''}">
	</form>

	<nav>
		<h4>Navigation</h4>
		<ul>
			<li><a href="<c:url  value="/" />">Main</a></li>
			<li><a href="<c:url value="/candidate/candidate-profile"/>">View Profile</a></li>			
			<li><a  href="<c:url value="/candidate/edit-candiate-profile"/>">Edit Profile</a> </li>			
			<li><a id="search-job" href="<c:url value="/jobs/advanceSearch"/>">Search Job</a></li>
			<li><a id="jobCategory" href="<c:url value="/jobCategory"/>">View All Job</a></li>
			<li><a href="<c:url value="/logout" />">Logout</a></li>
		</ul>
	</nav>
 
	<h4>About Us</h4>
	<div id="c">
		<img
			src="<c:url value="/resources/images/jonas-jared-jacek_50x50.png"/>"
			alt="Jonas Jacek">
		<p>
			ds sd sdsd <a href="http://jonas.me/">sdds sdsd</a>.
		</p>
		<p>aslajdlaj adj dj aj askas;l asjs l;asjla sj.</p>
		<p>asl a daknkas andn dja sdalsjdkl jasdjakljd asldjkasljd laskjd
			asdjlajdklas djadj lasdjlasjdjasljd asdlasldjlasj</p>
		<b>Follow Us</b>
		<p>
			<a href="#" title="Follow @jabz">Facebook</a> | <a href="#"
				title="Follow @jabz">Twitter</a>
		</p>
	</div>

	<h4>Top 5 Job Search Tips</h4>
	<div class="adspace">
		<a href="http://jabz.net/" rel="me"><img
			src="<c:url value="/resources/images/jabz-logo.png"/>"
			alt="Jabz Internet Marketing GmbH"
			title="Jabz Internet Marketing GmbH" /></a>
	</div>
	<h4>Top 5 Interview Tips</h4>
	<ul>
		<li><a href="http://jabz.net/">Jabz Internet Marketing GmbH</a></li>
		<li><a href="http://jabz.net/contact/jonas-jacek">Jonas Jacek</a></li>
		<li><a href="http://www.w3.org/">World Wide Web Consortium</a></li>
		<li><a href="http://www.getfirefox.com/">Firefox Web Browser</a></li>
	</ul>
	<h4>Top 5 CV Tips</h4>
	<ul id="tagcloud">
		<li class="tagcloudsize-1"><a href="#">Lorem</a></li>
		<li class="tagcloudsize-2"><a href="#">Ipsum</a></li>
		<li class="tagcloudsize-3"><a href="#">Dolor</a></li>
		<li class="tagcloudsize-2"><a href="#">Sit Amet</a></li>
		<li class="tagcloudsize-1"><a href="#">Consectetur</a></li>
	</ul>
