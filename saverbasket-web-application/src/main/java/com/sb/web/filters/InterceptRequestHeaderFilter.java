package com.sb.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/api/v1/*")
public class InterceptRequestHeaderFilter implements Filter {
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, 
      FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
           
        httpServletResponse.setHeader("svb-major-version", "v1.5.0");
        httpServletResponse.setHeader("svb-minor-version", "");
        chain.doFilter(request, response);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ...
    }
 
    @Override
    public void destroy() {
        // ...
    }

}
