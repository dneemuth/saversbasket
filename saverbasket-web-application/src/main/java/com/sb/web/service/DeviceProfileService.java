package com.sb.web.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.entities.DeviceProfile;
import com.sb.web.entities.RefreshToken;
import com.sb.web.exception.TokenRefreshException;
import com.sb.web.repositories.DeviceProfileRepository;

@Service
public class DeviceProfileService {	
	
	@Autowired
	private DeviceProfileRepository deviceProfileRepository;

      /**
     * Find the user device info by user id
     */
    public Optional<DeviceProfile> findByUserId(Integer userId) {
        return deviceProfileRepository.findByUserId(userId);
    }

    /**
     * Find the user device info by refresh token
     */
    public Optional<DeviceProfile> findByRefreshToken(RefreshToken refreshToken) {
        return deviceProfileRepository.findByRefreshToken(refreshToken);
    }
    
    @Transactional
    public void createAndPersistDeviceProfile(DeviceProfile deviceInfo) {
    	deviceProfileRepository.save(deviceInfo);
    }

    /**
     * Creates a new user device and set the user to the current device
     */
    public DeviceProfile createUserDevice(DeviceProfile deviceInfo) {
    	DeviceProfile deviceProfile = new DeviceProfile();
    	deviceProfile.setDeviceId(deviceInfo.getDeviceId());
    	deviceProfile.setDeviceType(deviceInfo.getDeviceType());
    	deviceProfile.setNotificationToken(deviceInfo.getNotificationToken());
    	deviceProfile.setRefreshActive(true);
    	
        return deviceProfile;
    }

    /**
     * Check whether the user device corresponding to the token has refresh enabled and
     * throw appropriate errors to the client
     */
    void verifyRefreshAvailability(RefreshToken refreshToken) {
    	DeviceProfile deviceProfile = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "No device found for the matching token. Please login again"));

        if (!deviceProfile.getRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }

}
