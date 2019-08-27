<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page session="false"%>

<div class="mainbody">
	<div class="table-style">
		<h3><b>Admin Profile</b></h3>
		<c:if test="${empty admin}">
			No Information Found. testing
		</c:if>
		<c:if test="${not empty admin}">
		<table>
			<tbody>
				<tr>
					<td><strong>Admin FirstName</strong></td>
					<td><c:out value="${admin.adminFirstName}"/></td>
				</tr>
				<tr>
					<td><strong>Admin LastName</strong></td>
					<td><c:out value="${admin.adminLastName}"/></td>
				</tr>
						</tbody>
		</table>
		</c:if>
	</div>
	<div style="float: left; clear: both"></div>
</div>