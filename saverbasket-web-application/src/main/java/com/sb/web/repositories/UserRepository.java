package com.sb.web.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.User;
import com.sb.web.enumerations.AuthenticationType;

public interface UserRepository extends JpaRepository<User, Integer> {
	
  @Query("SELECT u FROM user u join fetch u.roles rol WHERE u.idUser=:idUser")
  public User getUserAndRole(@Param("idUser") Integer idUser);
  
  
  @Query("SELECT u FROM user u join fetch u.roles rol WHERE u.username=:username")
  public User getUserAndRoleByUsername(@Param("username") String username);
  
  @Query("SELECT u FROM user u WHERE u.enabled='Y'")
  public List<User> getAllActiveUsers();
  
  @Transactional
  @Query("SELECT u FROM user u join fetch u.roles rol WHERE u.email=:email")
  public User getUserAndRoleByEmail(@Param("email") String email);

  @Transactional
  boolean existsByUsername(String username);
  
  
  @Transactional
  boolean existsByEmail(String email);
 
  @Transactional
  User findByUsername(String username);
  
  @Transactional
  User findByEmail(String email);
  
  @Transactional
  void deleteByUsername(String username);
  
  @Transactional
  @Modifying
  @Query("UPDATE user u SET u.lastTimeLogged = CURRENT_DATE WHERE u.idUser = :idUser")
  int updateLastTimeLoggedForUser(@Param("idUser") Integer idUser);
  
  @Modifying
  @Query("UPDATE user u SET u.authType = ?2 WHERE u.email = ?1")
  public void updateAuthenticationType(String username, AuthenticationType authType);

}
