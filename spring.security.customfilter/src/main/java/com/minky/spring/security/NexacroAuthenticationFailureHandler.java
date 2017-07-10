package com.minky.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class NexacroAuthenticationFailureHandler implements
		AuthenticationFailureHandler {

	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		// 401은 runtime에서 처리를 왜 못하지.. 트랜잭션이 완료가 되지 않네.. 
		String errorMsg = exception.getMessage();
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "authentication failer. e=" + errorMsg);
	}

}
