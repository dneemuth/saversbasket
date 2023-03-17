package com.sb.web.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sb.web.entities.Role;
import com.sb.web.exception.CustomException;
import com.sb.web.utils.CookieUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  @Value("${security.jwt.token.secret-key}")
  private String secretKey = "secretKey";

  @Value("${security.jwt.token.expire-length}")
  private long jwtExpirationInMs = 3600000; // 1h

  @Autowired
  private MyUserDetails myUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }
  
   
   public long getJwtExpirationInMs() {
		return jwtExpirationInMs;
   }

/**
   * Generates a token from a principal object. Embed the refresh token in the jwt
   * so that a new jwt can be created
   */
  public String generateToken(UserDetails userDetails) {
      Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
      return Jwts.builder()
              .setSubject(userDetails.getUsername())
              .setIssuedAt(Date.from(Instant.now()))
              .setExpiration(Date.from(expiryDate))
              .signWith(SignatureAlgorithm.HS512, secretKey)
              .compact();
  }
  
  /**
   * Generates a token from a principal object. Embed the refresh token in the jwt
   * so that a new jwt can be created
   */
  public String generateTokenFromUserEmail(String email) {
      Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
      return Jwts.builder()
              .setSubject(email)
              .setIssuedAt(Date.from(Instant.now()))
              .setExpiration(Date.from(expiryDate))
              .signWith(SignatureAlgorithm.HS512, secretKey)
              .compact();
  }
  
  
  public String createToken(String username, List<Role> roles) {

	    Claims claims = Jwts.claims().setSubject(username);
	    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getRole())).filter(Objects::nonNull).collect(Collectors.toList()));
	    
	    
	    //Date now = new Date();
	    //Date validity = new Date(now.getTime() + validityInMilliseconds);
	    
	    Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
	    Instant expiration = issuedAt.plus(jwtExpirationInMs, ChronoUnit.MILLIS);

	    return Jwts.builder()//
	        .setClaims(claims)//
	        .setIssuedAt(Date.from(issuedAt))//
	        .setExpiration(Date.from(expiration))//
	        .signWith(SignatureAlgorithm.HS256, secretKey)//
	        .compact();
	  }

  public String createToken(String username, String refreshToken, List<Role> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getRole())).filter(Objects::nonNull).collect(Collectors.toList()));
    claims.put("refresh", refreshToken);
    
    //Date now = new Date();
    //Date validity = new Date(now.getTime() + validityInMilliseconds);
    
    Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    Instant expiration = issuedAt.plus(jwtExpirationInMs, ChronoUnit.MILLIS);

    return Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(Date.from(issuedAt))//
        .setExpiration(Date.from(expiration))//
        .signWith(SignatureAlgorithm.HS256, secretKey)//
        .compact();
  }
  
  
  public Authentication getAuthenticationByUsername(String username) {
	    UserDetails userDetails = myUserDetails.loadUserByUsername(username);
	    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {    
      
      throw new CustomException(
	          "Expired or invalid JWT token",
	          "Expired or invalid JWT token",
	          "",
	          "Clear all browser cookies.",
	          "Reach out to http://saversbasket.com/contact for more help");
    }
  }
  
  
  public String getTokenFromBrowser(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
      String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
      if(token == null) return null;
      return token;
  }
  
  public String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
      String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
      if(token == null) return null;
      return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
  }
  
  public Claims getClaimsFromHttpRequest(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
      String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
      if(token == null) return null;
      return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
  }

}
