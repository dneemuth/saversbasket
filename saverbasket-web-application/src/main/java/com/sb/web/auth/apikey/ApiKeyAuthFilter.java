package com.sb.web.auth.apikey;

import javax.servlet.http.HttpServletRequest;


import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter responsible for getting the api key off of incoming requests that need to be authorized.
 */
public class ApiKeyAuthFilter  extends AbstractPreAuthenticatedProcessingFilter {

	private static final Logger LOG = LoggerFactory.getLogger(ApiKeyAuthFilter.class);

    private final String headerName;

    public ApiKeyAuthFilter(final String headerName) {
        this.headerName = headerName;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        // No creds when using API key
        return null;
    }
}
