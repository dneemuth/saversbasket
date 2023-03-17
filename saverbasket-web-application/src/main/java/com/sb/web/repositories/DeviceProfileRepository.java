package com.sb.web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.DeviceProfile;
import com.sb.web.entities.RefreshToken;

public interface DeviceProfileRepository extends JpaRepository<DeviceProfile, Integer> {
	 
	    Optional<DeviceProfile> findByIdDeviceProfile(Integer idDeviceProfile);

	    Optional<DeviceProfile> findByRefreshToken(RefreshToken refreshToken);

	    @Query("SELECT dp FROM deviceprofile dp WHERE  dp.user.idUser = :userId")
	    Optional<DeviceProfile> findByUserId(@Param("userId") Integer userId);

}
