<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="mainbody">

<%-- <c:if test="${not empty candicateResume}">
   <div>
         <p>  File Description: ${candicateResume.resumeFileDesc}  <br/> 
         <a  href='<c:url value="/upload/downloadCv"/>'>Download Resume</a></p>
         
   </div>
</c:if> --%>

<%-- <div class="highlight-3"  id="message"></div>
<c:if test="${not empty message}">
	<div class="highlight-3"  title="Employer Registration">
		${message} 
	</div>
</c:if>
 --%>
<form:form modelAttribute="uploadItem" onsubmit="return Checkfiles()"  name="uploadForm"  id="uploadForm" method="post" enctype="multipart/form-data">
	<fieldset>
		<legend>Upload Fields</legend>
		<form:label for="fileDescription" path="fileDescription">Description</form:label><br/>
		<form:input path="fileDescription"/>
		<form:label for="fileData" path="fileData">File</form:label><br/>
		<form:input path="fileData" id="fileData"  type="file"/>
		<input type="submit"  value="Upload" onclick="Checkfiles()" />
	</fieldset>
</form:form>
</div>
<script type="text/javascript">
function Checkfiles()
{
	
	
	
var fup = document.getElementById('fileData');
var fileName = fup.value;
var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
if(ext == "pdf" || ext == "PDF" || ext == "doc" || ext == "docx")
{
return true;
} 
else
{

document.getElementById('message').innerHTML = '<b>Upload pdf or doc file only</b>';
fup.focus();
return false;
}
}
</script>

