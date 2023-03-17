package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import com.sb.web.entities.converters.BooleanToStringConverter;

@Embeddable
@AttributeOverrides({
	  @AttributeOverride( name = "longitude", column = @Column(name = "longitude")),
	  @AttributeOverride( name = "latitude", column = @Column(name = "latitude")),
	  @AttributeOverride( name = "geoNotes", column = @Column(name = "geoNotes"))
	})
public class GeoLocationTag  implements Serializable {
	
	private static final long serialVersionUID = 1L;

    @Size(max = 20)
    @Column(name = "longitude")
    private String longitude;
    
    @Size(max = 20)
    @Column(name = "latitude")
    private String latitude;
    
    @Size(max = 100)
    @Column(name = "geoNotes")
    private String geoNotes;
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean locationUpdatable;

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getGeoNotes() {
		return geoNotes;
	}

	public void setGeoNotes(String geoNotes) {
		this.geoNotes = geoNotes;
	}

	public Boolean getLocationUpdatable() {
		return locationUpdatable;
	}

	public void setLocationUpdatable(Boolean locationUpdatable) {
		this.locationUpdatable = locationUpdatable;
	}

}
