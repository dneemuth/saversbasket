package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;

@Entity(name="profile")
public class Profile  extends Auditable<Integer> implements Serializable {

   private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProfile")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_generator")
    @SequenceGenerator(name = "user_profile_generator", sequenceName = "user_profile_seq", allocationSize = 1)
    private Integer idProfile;
	
	@Convert(converter=BooleanToStringConverter.class)
	private Boolean monitorKeyWords;
	
	/** Points to the user of this account. */
	@OneToOne(mappedBy = "profile", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;

	public Integer getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Integer idProfile) {
		this.idProfile = idProfile;
	}

	public Boolean getMonitorKeyWords() {
		return monitorKeyWords;
	}

	public void setMonitorKeyWords(Boolean monitorKeyWords) {
		this.monitorKeyWords = monitorKeyWords;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
	
				

}
