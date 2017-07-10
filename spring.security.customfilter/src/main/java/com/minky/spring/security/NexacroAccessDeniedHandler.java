package com.minky.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class NexacroAccessDeniedHandler implements AccessDeniedHandler {

	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {

		if (!response.isCommitted()) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "nexacro access denied. " + accessDeniedException.getMessage());
		}
	}

}
