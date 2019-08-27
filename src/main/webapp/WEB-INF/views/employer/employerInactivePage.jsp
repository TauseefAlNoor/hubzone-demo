<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--   
<div class="mainbodytext">
<p>Thank you for registering for HUBZone Talent. We are close to being able to give you access to the nation&rsquo;s premier HUBZone Search and Job Posting site. In order to gain access, you will have to complete payment for the package you have selected. Somebody from our team should be contacting you shortly, but feel free to reach out to us at <a href="mailto:support@HUBZoneTalent.com">support@HUBZoneTalent.com</a>. Thank you and welcome to the site!</p>
</div>
-->


<div class="mainbodytext">
	<div class="" style="width: 50%; border:3px solid #E4F3F6;">
		<h2 style="font-size: 14px; font-weight: bold; background-color:#E4F3F6; padding: 10px; margin: 0; border: 1px solid #E4F3F6;">PAYMENT OPTIONS</h2>
<%-- 		<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
		<input type="hidden" name="cmd" value="_s-xclick">
		<input type="hidden" name="hosted_button_id" value="GXNPWYGH7AC8S">
		<div class="" style="margin-bottom: 20px;">
			<input type="hidden" name="on0" value="Payment Options">
			<select name="os0" style="height: 30px;">
	        	<option value="Option 1">Option 1 : $99.00 USD - monthly</option>
	            <option value="Option 2">Option 2 : $899.00 USD - yearly</option>
			</select>
		</div>
		<div class="">
			<input type="hidden" name="currency_code" value="USD">
			<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
			<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_subscribeCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
		</div>	
		</form> --%>
		
	<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
		<input type="hidden" name="cmd" value="_s-xclick">
		<input type="hidden" name="hosted_button_id" value="WJ6EY86WUB2MN">
			<table>
				<tr><td><input type="hidden" name="on0" value="Subscription Options">Subscription Options</td></tr><tr><td>
				<select name="os0">
					<option value="Option 1">Option 1 : $99.00 USD - monthly</option>
					<option value="Option 2">Option 2 : $899.00 USD - yearly</option>
				</select> </td></tr>
			</table>
		<input type="hidden" name="currency_code" value="USD">
		<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_subscribeCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
		<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
	</form>
		
	</div>		
</div>