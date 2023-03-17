package com.sb.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Elastic Beanstalk health checks only happen over HTTP, so as a workaround
 * create a new URL (/aws-health) that forwards to the Actuator health check
 * That URL is set to respond over any channel (not just secure, aka https, ones) in {@link WebSecurityConfiguration}
 * 
 * @see https://candrews.integralblue.com/2017/03/spring-boot-https-required-and-elastic-beanstalk-health-checks/
 */
@Controller
public class InsecureHealthController {

  //@Autowired  
  //ManagementServerProperties management;
	 
  @RequestMapping(value="/health", method=RequestMethod.GET)
  public void health(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
	  /**
    final String healthUrl = UriComponentsBuilder.fromPath(management.getServlet().getContextPath()).path("/aws-health").toUriString();
    final HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request){
      @Override
        public boolean isSecure() {
          return true;
        }
      };
      request.getRequestDispatcher(healthUrl).forward(requestWrapper, response);
      */
    }
}
