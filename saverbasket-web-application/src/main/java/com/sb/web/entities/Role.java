package com.sb.web.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity(name="role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id   
    @Column(name = "idRole")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @SequenceGenerator(name = "role_generator", sequenceName = "role_seq", allocationSize = 1) 
    private int idRole;
	
    @Column(name = "role")
    private String role;    
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "roleprivilege", joinColumns = @JoinColumn(name = "idRole", referencedColumnName = "idRole"), inverseJoinColumns = @JoinColumn(name = "idPrivilege", referencedColumnName = "idPrivilege"))
    private List<Privilege> privileges;
    
    public Role() {
     super();
    }
    
    public Role(final String role) {
    	super();
    	this.role = role;
    }

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
    
	

}
