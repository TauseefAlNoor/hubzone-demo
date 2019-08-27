<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>
<!-- <script>
/* jQuery(document).ready(function($){
  $('.help-button').on('click', function(e){
    e.preventDefault();
    $(this).siblings('.info-box').show();
  });

  $('.close-button').on('click', function(e){
    e.preventDefault();
    $(this).parents('.info-box').hide();
  });
}); */
window.onload = function() {
	   //Get the DOM element that represents the <button> element.
	   //And set the onclick event
	   document.getElementById("help-button").onclick = function(){
	      //Set a variable to contain the DOM element of the overly
	      var overlay = document.getElementById("overlay");
	      //Set a variable to contain the DOM element of the popup
	     /*  var popup = document.getElementById("popup"); */

	      //Changing the display css style from none to block will make it visible
	      overlay.style.display = "block";
	      //Same goes for the popup
	      /* popup.style.display = "block"; */
	   };
	};
</script> -->

<script type="text/javascript">
$(function(){	
/* 	var alert = '<div id="close_button1" class="alert alert-info alert-dismissable">'+
				'<button type="button" class="close" id="close_button" data-dismiss="alert" aria-hidden="true">&times;</button>'+
				'A type of search allowing users to combine keywords with operators such as AND, '+
				'NOT and OR to further produce more relevant results. For example, a Boolean search '+
				'could be "java" AND "developer". This would limit the search results to only those '+
				'documents containing the two keywords.</div>'; */
/* 	$("#booleanSearch1").click(function() {
		
		$("#helpAlert1").append(alert);
	}); */
	
	$("#booleanSearch1111").click(function() {
		/* if(.open){
			$("#helpAlert1").hide(alert);
		}else{
 */			$("#boolean_alert_box").show();
		/* } */ 
		
	});
	
	/*$("#booleanSearch2").click(function() {
		$("#helpAlert2").append(alert);
	});
	
	$("#close_button").click(function() {
		$("#helpAlert11").hide(alert);
	});*/
});
</script>

<div class="mainbody">
	<div class="headersection12">
		<h1>
			<b>Quick Search</b>
		</h1>
	</div>
	<c:if test="${not empty message}">
${message}
</c:if>
	<c:if test="${ empty message}">
		<div style="width:100%;">
			<div id="helpAlert1" style="float:right;width:400px;font-size:11px;padding-right:10px;">
			</div>
			<c:url var="action" value="/employer/search-doc-resume" />
			<form:form method="POST" id="search-resume" action='${action}' style="float:left;width:500px;">
			
				<!--  <input name="start" type="hidden" value="0" />
				<input name="limit" type="hidden" value="3" />  -->
				<fieldset>
					<legend>Search Resume Database</legend>
					<!-- <label>Keyword: (e.g. developer, accountant, laborer)</label> --> 
					<div class="help">
					<!-- <div class="info-box" id="overlay">
	         			<a href="#" class="close-button">&times;</a>
	         			A type of search allowing users to combine keywords with operators such as AND, NOT and OR to further produce more relevant results. For example, a Boolean search could be "java" AND "developer". This would limit the search results to only those documents containing the two keywords.
	    			</div> -->
						<label>
							Boolean Search: 
							<a class='help-button' id="booleanSearch1" href="#" rel="popover"
   data-content="This is the body of Popover">(What is Boolean Search?)</a>
						</label> 
					</div>
					<input type="text" name="serachKeyword" id="serachKeyword" />
				</fieldset>
				<input type="submit" class="butn " value="Quick Search"
					style="margin-left: 350px;" align="left">
			</form:form>			
			<div style="clear:both;"></div>
		</div>
		<div class="headersection12">
			<h1>
				<b>Advanced Search</b>
			</h1>
		</div>
		<div style="width:100%;">
		<div id="helpAlert2" style="float:right;width:400px;font-size:11px;padding-right:10px;">
			</div>
			<c:url var="action" value="/employer/search-resume-result" />
			<form:form method="POST" id="search-resume" action='${action}' style="float:left;width:500px;">
				<!-- <input name="start" type="hidden" value="0" />
				<input name="limit" type="hidden" value="3" />
	 -->
				<fieldset>
					<legend>Search Resume Database</legend>
					<!-- <label>Keyword: (e.g. developer, accountant, laborer)</label>  -->
					<label>Boolean Search: <a href="#" id="booleanSearch2">(What is Boolean Search?)</a></label> 
					<input type="text"
						name="candidate.recentEmployer" id="recentEmployer" value=""/> 
						<!-- <label>Search Type: </label>  -->
						<!-- <select
						name="searchType" id="searchType">
						<option value="anyword">Match Any Words</option>
						<option value="allword">Match All Words</option>
					</select> -->
				</fieldset>
				
				<fieldset>
					<legend>Desired Position</legend>
					<label>Desired Position: (e.g. technician, cashier, analyst)</label> <input type="text"
						name="candidate.desiredPosition" id="desiredPosition" value="" />
				</fieldset>
				<fieldset>
					<legend>Resumes Posted: </legend>
					<select name="resumeUpdated" id="resumeUpdated"
						title="Resumes updated">
						<option value="1">Today</option>
						<option value="2">within 1 day</option>
						<option value="3">within 2 days</option>
						<option value="4">within 3 days</option>
						<option value="7">within 1 week</option>
						<option value="30">within 1 month</option>
						<option value="90">within 3 months</option>
						<option value="180">within 6 months</option>
						<option value="270">within 9 months</option>
						<option value="365">within 12 months</option>
						<option selected="selected" value="1095">All Resumes</option>
					</select>
				</fieldset>
				
				<fieldset>
					<legend>Location Options</legend>
					<label> Only show job seekers within </label> <select
						name="distance" id="distance">
						<option value=10>10 miles of</option>
						<option value=20>20 miles of</option>
						<option value=30>30 miles of</option>
						<option value=40>40 miles of</option>
						<option value=50>50 miles of</option>
						<option value=60>60 miles of</option>
						<option value=70>70 miles of</option>
						<option value=80>80 miles of</option>
						<option value=90>90 miles of</option>
						<option value=100>100 miles of</option>
					</select> <label> City/Zip Code: </label>
					<!-- <input type="text" name="job.jobState" id="jobState" /> -->
					<input type="text" name="location" id="location" placeholder="" />
				</fieldset>
				<fieldset>
					<legend>Resume Details</legend>
					<label>Enter keywords to search specific areas of job seeker
						resumes.</label> <label>Most Recent Skillsets: (e.g. Oracle, data entry, construction)</label> <input type="text"
						name="candidate.candidateSkill" id="candidateSkill" value=""/> <!-- <label>Most
						Recent Job Title: </label> <input type="text"
						name="candidate.recentJobTitle" id="recentJobTitle" /> <label>Most
						Recent Job Description: </label> <input type="text"
						name="candidate.recentJobDescription" id="recentJobDescription" />
					<label>Schools Attended: </label> <input type="text"
						name="candidate.lastSchoolAttended" id="lastSchoolAttended" />-->
				</fieldset> 
				<fieldset>
					<legend>Job Seeker Profile</legend>
					<label>Limit results to job seekers with the selected
						education level</label> <select name="candidate.highestEducationLevel"
						id="highestEducationLevel">
						<option value="" selected="selected"></option>
						<option value="No Degree">No Degree or Higher</option>
						<option value="High School/GED">High School/GED or Higher</option>
						<option value="Associate Degree">Associates Degree or Higher</option>
						<option value="Bachelors Degree">Bachelors Degree or Higher</option>
						<option value="Masters Degree">Masters Degree or
							Higher</option>
					</select>
				</fieldset>
	
	
				<!-- multiple="multiple" -->
				<fieldset>
					<legend>Salary Level</legend>
					<label>Job Seeker Salary Level</label> <select name="malsalaryLevel"
						id="salaryLevel" >
						<option value="" selected="selected"></option>
						<option value="Negotiable">Negotiable</option>
						<option value="0-30k">0-30k</option>
						<option value="30k-50k">30k-50k</option>
						<option value="50k-75k">50k-75k</option>
						<option value="75k-100k">75k-100k</option>
						<option value="100k-125k">100k-125k</option>
						<option value="125k-150k">125k-150k</option>
						<option value="150k+">150k+</option>
					</select>
				</fieldset>
	
				<fieldset>
					<legend>Job Preference </legend>
					<label>Only show job seekers interested in the selected job
						preference.</label> <input type="checkbox" name="candidate.jobStatus"
						value="Full time" />Full Time<br /> <input type="checkbox"
						name="candidate.jobStatus" value="Part time" />Part Time<br />
					<!-- <input type="checkbox" name="candidate.jobStatus" value="Contractual"/>Contractual<br/> -->
				</fieldset>
				<fieldset>
					<legend>Job Categories</legend>
					<label>Only show job seekers interested in specific job
						categories</label>
					<c:forEach var="category" items="${jobCategories}"
						varStatus="status">
						<input type="checkbox" name="candidate.jobCategory"
							value="${category.jobCategoryName}" />${category.jobCategoryName}<br />
					</c:forEach>
				</fieldset>
				<br />
				<input type="submit" class="butn " value="Advanced Search"
					style="margin-left: 350px;">
				<input type="reset" class="butn " value="Reset"
					style="margin-left: 350px;">
					
<!-- 		<div id="boolean_alert_box" class="alert alert-info alert-dismissable hidden">
		<button type="button" class="close" id="close_button" data-dismiss="alert" aria-hidden="true">&times;</button>
		A type of search allowing users to combine keywords with operators such as AND, 
		NOT and OR to further produce more relevant results. For example, a Boolean search 
		could be "java" AND "developer". This would limit the search results to only those 
		documents containing the two keywords.</div>
		</div> -->

			</form:form>
		
	</c:if>
	
	<div style="float: left; clear: both"></div>
</div>
