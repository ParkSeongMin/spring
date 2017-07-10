package com.minky.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.nexacro.xapi.data.PlatformData;
import com.nexacro.xapi.tx.HttpPlatformRequest;
import com.nexacro.xapi.tx.PlatformException;

public class NexacroAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements InitializingBean {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	
	public NexacroAuthenticationFilter() {
		super(new AntPathRequestMatcher("/nexacro/login", "POST"));
	}
	
	
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		//setAuthenticationFailureHandler(new NexacroAuthenticationFailureHandler());
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		
		HttpPlatformRequest platformRequest = new HttpPlatformRequest(request);
		try {
			platformRequest.receiveData();
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		PlatformData data = platformRequest.getData();
		
		String username = data.getVariable("id").getString();
		String password = data.getVariable("pw").getString();
		
		
		
		
//		String username = obtainUsername(request);
//		String password = obtainPassword(request);
//
//		if (username == null) {
//			username = "";
//		}
//
//		if (password == null) {
//			password = "";
//		}
//
//		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private void setDetails(HttpServletRequest request,
			UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		
	}

	private String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	private String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

}
