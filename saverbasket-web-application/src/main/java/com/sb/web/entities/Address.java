package com.sb.web.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.AddressType;

@Entity(name="address")
public class Address extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAddress")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    @SequenceGenerator(name = "address_generator", sequenceName = "address_seq", allocationSize = 1)
    private Integer idAddress;	
	
	@Enumerated(EnumType.ORDINAL)
	private AddressType addressType;
	
	@Size(max = 120)
    @Column(name = "address1")
    private String address1;
	
	@Size(max = 120)
    @Column(name = "address2")
    private String address2;
	
	@Size(max = 120)
    @Column(name = "address3")
    private String address3;	
	
	@Size(max = 8)
    @Column(name = "zip")
    private String zip;
	
	@Size(max = 30)
    @Column(name = "city")
    private String city;	
	
	@Size(max = 50)
    @Column(name = "district")
    private String district;
	
	@Size(max = 50)
    @Column(name = "state")
    private String state;
	
	@Size(max = 5)
    @Column(name = "postCode")
    private String postCode;
	
	@JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = true)
    private User user;
	
	@Convert(converter=BooleanToStringConverter.class)
    private Boolean activeAddress;
	
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idCountry", referencedColumnName = "idCountry", nullable = true)
	private Country country;

	public Integer getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Integer idAddress) {
		this.idAddress = idAddress;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getActiveAddress() {
		return activeAddress;
	}

	public void setActiveAddress(Boolean activeAddress) {
		this.activeAddress = activeAddress;
	}	

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
		
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activeAddress, address1, address2, address3, addressType, city, country, district,
				idAddress, postCode, state, user, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(activeAddress, other.activeAddress) && Objects.equals(address1, other.address1)
				&& Objects.equals(address2, other.address2) && Objects.equals(address3, other.address3)
				&& addressType == other.addressType && Objects.equals(city, other.city)
				&& Objects.equals(country, other.country) && Objects.equals(district, other.district)
				&& Objects.equals(idAddress, other.idAddress) && Objects.equals(postCode, other.postCode)
				&& Objects.equals(state, other.state) && Objects.equals(user, other.user)
				&& Objects.equals(zip, other.zip);
	}
	

}
