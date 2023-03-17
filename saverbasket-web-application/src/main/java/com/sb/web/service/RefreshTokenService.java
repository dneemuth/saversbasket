package com.sb.web.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.DeviceProfile;
import com.sb.web.entities.RefreshToken;
import com.sb.web.exception.TokenRefreshException;
import com.sb.web.repositories.DeviceProfileRepository;
import com.sb.web.repositories.RefreshTokenRepository;


@Service
@Transactional
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	 @Autowired
	private DeviceProfileRepository deviceProfileRepository;

    @Value("${security.jwt.token.refresh-duration}")
    private Long refreshTokenDurationMs;

    /**
     * Find a refresh token based on the natural id i.e the token itself
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Persist the updated refreshToken instance to database
     */
    @Transactional(readOnly = false,noRollbackFor= Exception.class, propagation = Propagation.REQUIRED)	
    @Modifying   
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Creates and returns a new refresh token
     */
    public RefreshToken createRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(generateRandomUuid());
        refreshToken.setRefreshCount(0L);
        return refreshToken;
    }
    
    
    private static String generateRandomUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Expired token. Please issue a new request");
        }
    }

    /**
     * Delete the refresh token associated with the user device
     */
    @Transactional(readOnly = false,noRollbackFor= Exception.class, propagation = Propagation.REQUIRED)	
    @Modifying 
    public void deleteByIdToken(Long idToken) {
    	
    	RefreshToken refreshToken = refreshTokenRepository.getOne(idToken);
    	DeviceProfile deviceProfile = refreshToken.getDeviceProfile();
    	/* remove reference */
    	deviceProfile.setRefreshToken(null);
    	deviceProfileRepository.save(deviceProfile);
    	
    	//delete refresh token
        refreshTokenRepository.deleteByIdToken(idToken);
    }

    /**
     * Increase the count of the token usage in the database. Useful for
     * audit purposes
     */
    public void increaseCount(RefreshToken refreshToken) {
        refreshToken.incrementRefreshCount();
        save(refreshToken);
    }

}
