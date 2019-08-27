package com.hubzone.utility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

@SuppressWarnings("serial")
public class StateTag extends TagSupport {

	private WebApplicationContext _applicationContext;

	public StateTag() {
		_applicationContext = RequestContextUtils.getWebApplicationContext(
				pageContext.getRequest(), pageContext.getServletContext());
	}

	@Override
	@SuppressWarnings("unused")
	public int doStartTag() throws JspException {
		
		Object bean = _applicationContext.getBean("someName");
		return 0;

		
	}
}