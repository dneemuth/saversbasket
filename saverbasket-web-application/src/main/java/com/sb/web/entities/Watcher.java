package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.WatchNotificationStatus;

@Entity(name="watcher")
public class Watcher  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idWatcher")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "watcher_generator")
    @SequenceGenerator(name = "watcher_generator", sequenceName = "watcher_seq", allocationSize = 1) 
    private Integer idWatcher;
	
	@Enumerated(EnumType.ORDINAL)
	private WatchNotificationStatus watchNotificationStatus;	
	
	@ManyToOne
	@JoinColumn(name = "idProduct")   
	private Product product;
	
	public Integer getIdWatcher() {
		return idWatcher;
	}

	public void setIdWatcher(Integer idWatcher) {
		this.idWatcher = idWatcher;
	}

	public WatchNotificationStatus getWatchNotificationStatus() {
		return watchNotificationStatus;
	}

	public void setWatchNotificationStatus(WatchNotificationStatus watchNotificationStatus) {
		this.watchNotificationStatus = watchNotificationStatus;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getWatchDateCreated() {
		return watchDateCreated;
	}

	public void setWatchDateCreated(Date watchDateCreated) {
		this.watchDateCreated = watchDateCreated;
	}

	public Integer getWatchCreatedByUser() {
		return watchCreatedByUser;
	}

	public void setWatchCreatedByUser(Integer watchCreatedByUser) {
		this.watchCreatedByUser = watchCreatedByUser;
	}

	@Convert(converter=BooleanToStringConverter.class)
	private Boolean isActive;  	
	
	@Column(name = "watchDateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date watchDateCreated;
	    
    @Column(name = "watchCreatedByUser")
    private Integer watchCreatedByUser;

}
