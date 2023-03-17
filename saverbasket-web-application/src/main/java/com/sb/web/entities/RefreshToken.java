package com.sb.web.entities;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

@Entity(name="refreshtoken")
public class RefreshToken {
	
	
	@Id
    @Column(name = "idToken")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_generator")
    @SequenceGenerator(name = "refresh_token_generator", sequenceName = "refresh_token_seq", allocationSize = 1)
    private Long idToken;

    @Column(name = "token", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String token;

    @OneToOne(mappedBy = "refreshToken", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private DeviceProfile deviceProfile;

    @Column(name = "refreshCount")
    private Long refreshCount;

    @Column(name = "expiryDate", nullable = false)
    private Instant expiryDate;

    public RefreshToken() {
    }

    public RefreshToken(Long idToken, String token, DeviceProfile deviceProfile, Long refreshCount, Instant expiryDate) {
        this.idToken = idToken;
        this.token = token;
        this.deviceProfile = deviceProfile;
        this.refreshCount = refreshCount;
        this.expiryDate = expiryDate;
    }

    public void incrementRefreshCount() {
        refreshCount = refreshCount + 1;
    }

    public Long getIdToken() {
		return idToken;
	}

	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public DeviceProfile getDeviceProfile() {
		return deviceProfile;
	}

	public void setDeviceProfile(DeviceProfile deviceProfile) {
		this.deviceProfile = deviceProfile;
	}

	public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(Long refreshCount) {
        this.refreshCount = refreshCount;
    }

}
