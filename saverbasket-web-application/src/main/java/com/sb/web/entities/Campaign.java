package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;
import com.sb.web.entities.converters.BooleanToStringConverter;
import com.sb.web.enumerations.CampaignType;

@Entity(name="campaign")
public class Campaign  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id    
    @Column(name = "idCampaign")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_generator")
    @SequenceGenerator(name = "campaign_generator", sequenceName = "campaign_seq", allocationSize = 1)
    private Integer idCampaign;
    
    @Size(max = 150)
    @Column(name = "campaignUid")
    private String campaignUid;
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean active; 
    
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean shutdown;    
    
    @Column(name = "dateCampaignModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCampaignModified;
    
    @Size(max = 250)
    @Column(name = "description")
    private String description;    
    
    @Enumerated(EnumType.ORDINAL)
    private CampaignType campaignType;
    
    @Size(max = 250)
    @Column(name = "valueProposition")
    private String valueProposition;
    
    @Size(max = 150)
    @Column(name = "goalDescription")
    private String goalDescription;
    
    @Column(name = "campaignStartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date campaignStartDate;
    
    @Column(name = "campaignEndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date campaignEndDate;
    
    @Column(name = "campaignDuration")
    private Long campaignDuration;
    
    @Column(name = "sponsoredProduct")
    private Integer sponsoredProduct;
    
    @Column(name = "idPurchase")
    private Integer idPurchase;
    
    @ManyToOne
    @JoinColumn(name = "idAccount")   
    private Account account;

	public Integer getIdCampaign() {
		return idCampaign;
	}

	public void setIdCampaign(Integer idCampaign) {
		this.idCampaign = idCampaign;
	}

	public String getCampaignUid() {
		return campaignUid;
	}

	public void setCampaignUid(String campaignUid) {
		this.campaignUid = campaignUid;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getShutdown() {
		return shutdown;
	}

	public void setShutdown(Boolean shutdown) {
		this.shutdown = shutdown;
	}

	public Date getDateCampaignModified() {
		return dateCampaignModified;
	}

	public void setDateCampaignModified(Date dateCampaignModified) {
		this.dateCampaignModified = dateCampaignModified;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValueProposition() {
		return valueProposition;
	}

	public void setValueProposition(String valueProposition) {
		this.valueProposition = valueProposition;
	}

	public String getGoalDescription() {
		return goalDescription;
	}

	public void setGoalDescription(String goalDescription) {
		this.goalDescription = goalDescription;
	}

	public Date getCampaignStartDate() {
		return campaignStartDate;
	}

	public void setCampaignStartDate(Date campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}

	public Date getCampaignEndDate() {
		return campaignEndDate;
	}

	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}

	public Integer getSponsoredProduct() {
		return sponsoredProduct;
	}

	public void setSponsoredProduct(Integer sponsoredProduct) {
		this.sponsoredProduct = sponsoredProduct;
	}

	public Integer getIdPurchase() {
		return idPurchase;
	}

	public void setIdPurchase(Integer idPurchase) {
		this.idPurchase = idPurchase;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CampaignType getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(CampaignType campaignType) {
		this.campaignType = campaignType;
	}

	public Long getCampaignDuration() {
		return campaignDuration;
	}

	public void setCampaignDuration(Long campaignDuration) {
		this.campaignDuration = campaignDuration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((campaignDuration == null) ? 0 : campaignDuration.hashCode());
		result = prime * result + ((campaignEndDate == null) ? 0 : campaignEndDate.hashCode());
		result = prime * result + ((campaignStartDate == null) ? 0 : campaignStartDate.hashCode());
		result = prime * result + ((campaignType == null) ? 0 : campaignType.hashCode());
		result = prime * result + ((campaignUid == null) ? 0 : campaignUid.hashCode());
		result = prime * result + ((dateCampaignModified == null) ? 0 : dateCampaignModified.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((goalDescription == null) ? 0 : goalDescription.hashCode());
		result = prime * result + ((idCampaign == null) ? 0 : idCampaign.hashCode());
		result = prime * result + ((idPurchase == null) ? 0 : idPurchase.hashCode());
		result = prime * result + ((shutdown == null) ? 0 : shutdown.hashCode());
		result = prime * result + ((sponsoredProduct == null) ? 0 : sponsoredProduct.hashCode());
		result = prime * result + ((valueProposition == null) ? 0 : valueProposition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campaign other = (Campaign) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (campaignDuration == null) {
			if (other.campaignDuration != null)
				return false;
		} else if (!campaignDuration.equals(other.campaignDuration))
			return false;
		if (campaignEndDate == null) {
			if (other.campaignEndDate != null)
				return false;
		} else if (!campaignEndDate.equals(other.campaignEndDate))
			return false;
		if (campaignStartDate == null) {
			if (other.campaignStartDate != null)
				return false;
		} else if (!campaignStartDate.equals(other.campaignStartDate))
			return false;
		if (campaignType != other.campaignType)
			return false;
		if (campaignUid == null) {
			if (other.campaignUid != null)
				return false;
		} else if (!campaignUid.equals(other.campaignUid))
			return false;
		if (dateCampaignModified == null) {
			if (other.dateCampaignModified != null)
				return false;
		} else if (!dateCampaignModified.equals(other.dateCampaignModified))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (goalDescription == null) {
			if (other.goalDescription != null)
				return false;
		} else if (!goalDescription.equals(other.goalDescription))
			return false;
		if (idCampaign == null) {
			if (other.idCampaign != null)
				return false;
		} else if (!idCampaign.equals(other.idCampaign))
			return false;
		if (idPurchase == null) {
			if (other.idPurchase != null)
				return false;
		} else if (!idPurchase.equals(other.idPurchase))
			return false;
		if (shutdown == null) {
			if (other.shutdown != null)
				return false;
		} else if (!shutdown.equals(other.shutdown))
			return false;
		if (sponsoredProduct == null) {
			if (other.sponsoredProduct != null)
				return false;
		} else if (!sponsoredProduct.equals(other.sponsoredProduct))
			return false;
		if (valueProposition == null) {
			if (other.valueProposition != null)
				return false;
		} else if (!valueProposition.equals(other.valueProposition))
			return false;
		return true;
	}

}
