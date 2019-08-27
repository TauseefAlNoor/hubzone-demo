<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="f" uri="/WEB-INF/functions.tld" %>
<div class="mainbody">
<div class="headersection">
<h1><b>Job Category</b></h1>
</div>
					
				<dl class="vevent" >
				 <div style1="min-width:500px;margin-top:15px;float: left;-webkit-border-radius: 20px;-moz-border-radius: 20px;border-radius: 20px;border:3px solid #96C0FF;background:rgba(255,255,255,0.5);-webkit-box-shadow: #B3B3B3 4px 4px 4px;-moz-box-shadow: #B3B3B3 4px 4px 4px; box-shadow: #B3B3B3 4px 4px 4px;">
				   <div style="float: left;margin-top: 20px;" id="jobCategoryLeft">				
						 
							<ul>
								
								<c:forEach var="category" items="${jobCategories}" begin="0" end="${catSep}" step="1" varStatus ="status">
									<%--  <c:url var="url" value="/jobs/category/${f:encodeUri(category.jobCategoryName)}"/> --%> 
										
									<c:url value="/jobs/category" var="url">
										 <c:param name="cat" value="${category.jobCategoryName}"/>	 									   
										</c:url> 									
									<li><a href="<c:out value="${url}" />">${category.jobCategoryName}(${category.numJobs})</a></li>
								</c:forEach>
								
							</ul>
					
						
					</div>
					<div style="float: left;margin: 20px 0px 0px 15px;border-left: 2px solid #0098CC;">
						<ul>
							<c:forEach var="category" items="${jobCategories}" begin="${catSep+1}" end="${catEnd}" step="1" varStatus ="status">
										<%-- <c:url var="url" value="/jobs/category?cat=${category.jobCategoryName}"/> --%>
										<c:url value="/jobs/category" var="url">
										   <c:param name="cat" value="${category.jobCategoryName}"/>										   
										</c:url> 								
									<li><a href="<c:out value="${url}" />">${category.jobCategoryName}(${category.numJobs})</a></li>
							</c:forEach>
						</ul>
						
						
					 </div>	
					 </div>
				</dl>
				<div style="float:left;clear:both"></div>
</div>