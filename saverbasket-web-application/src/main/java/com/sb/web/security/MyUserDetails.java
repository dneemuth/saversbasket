package com.sb.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sb.web.entities.Role;
import com.sb.web.entities.User;
import com.sb.web.repositories.UserRepository;



@Service
public class MyUserDetails implements UserDetailsService {
	
  private User user;

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	  
    user = userRepository.getUserAndRoleByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User " + email + "' not found");
    }
        
    List<Role> roleList = userRepository.getUserAndRole(user.getIdUser()).getRoles();
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roleList.size());
    if (roleList != null && roleList.size() > 0) {
    	for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
      	}
    }

    return org.springframework.security.core.userdetails.User//    		 
        .withUsername(email)//
        .password(user.getPassword())//
        .authorities(authorities)//
        .accountExpired(false)//
        .accountLocked(false)//       
        .credentialsExpired(false)//
        .disabled(!user.getEnabled())//
        .build();
  }
	
   public String getUsername() {
		return user.getUsername();
   }
}
