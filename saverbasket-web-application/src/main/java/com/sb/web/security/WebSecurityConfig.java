package com.sb.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sb.web.security.oauth.CustomOAuth2UserService;
import com.sb.web.security.oauth.OAuthLoginSuccessHandler;
import com.sb.web.utils.SaversBasketConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {	
	
  @Value("${server.useSecuredConnection}")
  public String useSecuredConnection;
	
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  
  @Autowired
  MyUserDetails myUserDetails;
  
  @Autowired
  private CustomOAuth2UserService oauth2UserService;
	
  @Autowired
  private OAuthLoginSuccessHandler oauthLoginSuccessHandler;
	
  @Autowired
  private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;
    
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }
  
  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(myUserDetails)
              .passwordEncoder(passwordEncoder());
  }
  
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Disable CSRF (cross site request forgery)
    http.csrf().disable();   
    
    // Create Http Session as needed
    http.sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    
    // Force secured connection
    if (useSecuredConnection.equalsIgnoreCase("Y")) {
    	http.requiresChannel()    	
    	.anyRequest().requiresSecure();
    }

    // Entry points
    http.authorizeRequests()// 
    
    /** To comment on Prod */
	.antMatchers("/","/index").permitAll()// 
	.antMatchers("/newProduct?barcode={barcode}").permitAll()//
	.antMatchers("/scanProduct").permitAll()//
	.antMatchers("/newBusiness").permitAll()//
	.antMatchers("/contactus").permitAll()//
	.antMatchers("/viewProduct?pid={pid}").permitAll()//
	.antMatchers("/api/v1/basket/getAllProductTypes").permitAll()//
	.antMatchers("/api/v1/basket/getAllValidBusinesses").permitAll()//
	.antMatchers("/api/v1/basket/retrieveProductInformationFromAPI").permitAll()// 
	.antMatchers("/api/v1/basket/getSuggestedSimilarProducts").permitAll()// 
	.antMatchers("/api/v1/basket/getSuggestedSimilarProducts").permitAll()// 
	.antMatchers("/api/v1/basket/retrieveProductToPopulateForm").permitAll()// 
	.antMatchers("/api/v1/basket/addProduct").permitAll()// 
	.antMatchers("/api/v1/basket/findProducts").permitAll()// 
	.antMatchers("/api/v1/basket/getAllProductTypes").permitAll()//
	.antMatchers("/api/v1/basket/addBusiness").permitAll()//
	.antMatchers("/api/v1/basket/contactTeamByEmail").permitAll()//
	.antMatchers("/api/v1/basket/getScannedProductInfo").permitAll()//
	.antMatchers("/api/v1/basket/contributePrice").permitAll()//
	.antMatchers("/api/v1/basket/addProductToUserBasket").permitAll()//
	.antMatchers("/api/v1/basket/deleteItemFromUserBasket").permitAll()//
	.antMatchers("/api/v1/basket/updateQuantityForBasketItems").permitAll()//
	 /** To comment on Prod */
	
	.antMatchers("/login").permitAll()     
	.antMatchers("/logout").permitAll()// 	
	.antMatchers("/error").permitAll()//
	.antMatchers("/signuplink").permitAll()//  
	.antMatchers("/aboutus").permitAll()//   
	.antMatchers("/refresh").permitAll()//
    .antMatchers("/signin").permitAll()//     
    .antMatchers("/signup").permitAll()//  
    .antMatchers("/privacy").permitAll()// 
    .antMatchers("/listings").permitAll()// 
    .antMatchers("/sitemaintenance").permitAll()// 
    .antMatchers("/registrationConfirm**").permitAll()// 
    .antMatchers("/oauth/**").permitAll()// 
       
     // permit access to all resources
    .antMatchers("/resources/**").permitAll()
    // permit access to all resources
    .antMatchers("/vendor/**").permitAll()
    //health check
    .antMatchers("/health").permitAll()    
    .antMatchers("/h2-console/**/**").permitAll()
    /** For authenticated calls */
    .anyRequest()
    .authenticated()
    .and()
    //Normal Login
    .formLogin()
    .loginPage("/login") 
    .usernameParameter("email")
    .successHandler(databaseLoginSuccessHandler)
    .defaultSuccessUrl("/index")
    .failureUrl("/login")
    .permitAll()    
    .and()
    //Oauth2 features
    .oauth2Login()
	.loginPage("/login")
	.userInfoEndpoint()
	.userService(oauth2UserService)
	.and()
	.successHandler(oauthLoginSuccessHandler)
	.and()
    .logout()
    .addLogoutHandler(new HeaderWriterLogoutHandler(
            new ClearSiteDataHeaderWriter(
              ClearSiteDataHeaderWriter.Directive.CACHE,
              ClearSiteDataHeaderWriter.Directive.COOKIES,
              ClearSiteDataHeaderWriter.Directive.STORAGE)))
    .logoutSuccessUrl("/login")
    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
     .logoutSuccessHandler(new com.sb.web.security.CustomLogoutSuccessHandler())
    .permitAll()        
    .deleteCookies("JSESSIONID",SaversBasketConstants.JWT_TOKEN_COOKIE)     
    .invalidateHttpSession(false)
     //clear all browser sessions & localstorage
     .addLogoutHandler(new CustomLogoutHandler());
    

    // If a user try to access a resource without having enough permissions
    http.exceptionHandling().accessDeniedPage("/error");

    // Apply JWT
    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    // Optional, if you want to test the API from a browser
    http.httpBasic();
  }
  
  
  @Bean
  public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
      StrictHttpFirewall firewall = new StrictHttpFirewall();
      firewall.setAllowUrlEncodedSlash(true); 
      firewall.setAllowSemicolon(true);
      return firewall;
  }
 
  @Override
  public void configure(WebSecurity web) throws Exception {
	  
    super.configure(web);
    web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//    	
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/oauth/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public")
        .antMatchers("/resources/**", "/vendor/**", "/static/**", "/fonts/**", "/scss/**", "/css/**", "/js/**", "/images/**")
        
        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");        
  }
  

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }  
  
  @Bean
  public AuthenticationTrustResolver getAuthenticationTrustResolver() {
      return new AuthenticationTrustResolverImpl();
  }

}
