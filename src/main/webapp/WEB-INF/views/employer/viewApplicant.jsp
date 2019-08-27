<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>

<div class="mainbody" id="highlight-word">

<div class="table-style">
		<h3>
			<b>Job Seeker List</b>
		</h3>
		
	<table>
		<tbody>
				<tr>
				<th><p>&nbsp;&nbsp;&nbsp;&nbsp;</p><td><button type="button" name="back" onclick="history.back()">Back to Search Result</button></th>
				</tr>
				<tr>	
				<th>Job Seeker Name</th>
				<th>Apply Date</th>		
				</tr>
				<c:forEach var="cand" items="${candList}" varStatus="status">
				
					<tr>
					
<%-- 					<th><c:url value="candidate-profile" var="url">
							<c:param name="CandidateID" value="${cand.candidate.candidateID}" />
						</c:url></th> --%>
					<th><a href="<c:url value="/candidate/candidate-profile/${cand.candidate.candidateID}"/>">${cand.candidate.candidateID}</a></th>
					<th>${cand.applyDate}</th>
					</tr>
				</c:forEach>
		</tbody>
	</table>


</div>
</div>