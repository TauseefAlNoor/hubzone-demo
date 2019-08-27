<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>	
	<script type="text/javascript">
		function loadLinks() {
			var links = $("#links");
			links.load(
				'<c:url value="/links" />',
				function(responseText, textStatus, xhr) {
					if (xhr.status == 200) {
						$("#ajaxLogin").click(function() {
							loadLogin();
						});
					} else {
						links.html('<h4 style="color: #F00">Could not load URL "/links"</h4>');
						links.show();
					}
				});
		}
		function loadLogin() {
			$("#login").load(
				'<c:url value="/login" />',
				function(responseText, textStatus, xhr) {
					if (xhr.status == 200) {
						$("#login").dialog('open');
					} else {
						$("#login").html('<h4 style="color: #F00">Could not load URL "/login"</h4>');
					}
				});
		}
		function performLogin(form) {
			form.find(".message").hide();
			form.find(".error").hide();
			$.post(
				'<c:url value="/login" />',
				form.serialize(),
				function (data, textStatus) {
					if (data.status) {
						$("#login").dialog('close');
						loadLinks();
					} else {
						var err = form.find(".error");
						err.html("Login Failed [" + data.error + "]");
						err.show();
					}
				}, "json");
		}
		$(document).ready(function() {
			// Initialize dialog window
			$("#login").dialog({ 
				autoOpen: false,
				modal: true,
				position: 'center',
				resizable: false,
				width: 500,
				open: function() {
					$("#form form button").click(function() {
						performLogin($("#form form"));
					});
					$("#form form input, #form form button").keypress(function(e) {
						if (e.which == 13) {
							performLogin($("#form form"));
						}
					});
				}
			});
			// Load links
			loadLinks();
		});
	</script>
</head>
<body>
	<h1>Hello Security world!</h1>
	<h3>This page is not secure.</h3>
	
	<!-- Populated by jQuery & links.jsp -->
	<div id="links"></div>
	
	<!-- This is my login form, not displayed by default. Populated by jQuery & login.jsp -->
	<div id="login" class="login" title="Login Form"></div>
</body>
</html>
