<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>	<tiles:getAsString name="title" /></title>
<meta charset="UTF-8">
<meta name="description" content="HubZone Largest Job Site In USA">
<meta name="robots" content="index,follow,noarchive">

 <%-- <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>"> 
 <link rel="stylesheet" href="<c:url value="/resources/bootstrap/css/bootstrap-theme.min.css"/>">  --%>

<%-- <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.8.3.js" />"></script> --%>



	<%--  <script type="text/javascript" src="<c:url value="/resources/pagination/paging.js"/>"></script>  --%>
	<%-- <script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script> --%>
	
	<link rel="stylesheet" href="<c:url value="/resources/css/index.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/stylesheet.css"/>">

<%-- <link rel="stylesheet" href="<c:url value="/resources/css/template.css"/>"> --%>


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
<%-- <script type="text/javascript" src="<c:url value="/resources/js/jquery.highlight-4.js" />"></script> --%>

<script type="text/javascript" src="<c:url value="/resources/js/paging.js" />"></script>



<style type="text/css">
#boxscroll {
	padding:5px;
	height: 220px;		
	overflow: auto;
	margin-bottom:20px;
	margin-right: auto;
	margin-left: auto;
}

</style>
<!-- <script>
  $(document).ready(function() {
//    $("html").niceScroll();  // The document page (html)
    
    $("#boxscroll").niceScroll({touchbehavior:false,cursorcolor:"#00F",cursoropacitymax:0.7,cursorwidth:11,cursorborder:"1px solid #2848BE",cursorborderradius:"8px",background:"#ccc",autohidemode:"scroll"}).cursor.css({"background-image":"url(img/mac6scroll.png)"}); // MAC like scrollbar

  });
</script> -->
</head>
<body> 
	<div id="a">
		<tiles:insertAttribute name="header" />
		<div id="b">
			<article>
				<div class="pagewrapper">
				<tiles:insertAttribute name="body" />
				</div>
			</article>
			<%-- 	
			<aside>
				<tiles:insertAttribute name="left" />
			</aside>
			--%>
		</div>
		<footer>
			<tiles:insertAttribute name="footer" />
		</footer>
	</div>


</body>
</html>
