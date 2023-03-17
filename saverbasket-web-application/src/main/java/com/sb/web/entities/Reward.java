package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.converters.BooleanToStringConverter;

@Entity(name="reward")
@Inheritance
public abstract class Reward implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idReward")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_generator")
    @SequenceGenerator(name = "reward_generator", sequenceName = "reward_seq", allocationSize = 1)
    private Integer idReward;		
	
	@Column(name = "rewardName")
    private String rewardName;
	
	@Column(name = "startRewardDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startRewardDate; 

	@Column(name = "endRewardDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endRewardDate; 
	
	@Column(name = "rewardRedeemed")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean rewardRedeemed;
	
	@Column(name = "dateRewardRedeemed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVoucherRedeemed; 
    
    @Column(name = "rewardRedeemedByUser")
    private String rewardRedeemedByUser;
    
    // Lottery raffle
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "idLotteryRaffle", referencedColumnName = "idLotteryRaffle", nullable = true)
    private LotteryRaffle lotteryRaffle;

	public Integer getIdReward() {
		return idReward;
	}

	public void setIdReward(Integer idReward) {
		this.idReward = idReward;
	}

	public String getRewardName() {
		return rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public Date getStartRewardDate() {
		return startRewardDate;
	}

	public void setStartRewardDate(Date startRewardDate) {
		this.startRewardDate = startRewardDate;
	}

	public Date getEndRewardDate() {
		return endRewardDate;
	}

	public void setEndRewardDate(Date endRewardDate) {
		this.endRewardDate = endRewardDate;
	}

	public Boolean getRewardRedeemed() {
		return rewardRedeemed;
	}

	public void setRewardRedeemed(Boolean rewardRedeemed) {
		this.rewardRedeemed = rewardRedeemed;
	}

	public Date getDateVoucherRedeemed() {
		return dateVoucherRedeemed;
	}

	public void setDateVoucherRedeemed(Date dateVoucherRedeemed) {
		this.dateVoucherRedeemed = dateVoucherRedeemed;
	}

	public String getRewardRedeemedByUser() {
		return rewardRedeemedByUser;
	}

	public void setRewardRedeemedByUser(String rewardRedeemedByUser) {
		this.rewardRedeemedByUser = rewardRedeemedByUser;
	}

	public LotteryRaffle getLotteryRaffle() {
		return lotteryRaffle;
	}

	public void setLotteryRaffle(LotteryRaffle lotteryRaffle) {
		this.lotteryRaffle = lotteryRaffle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateVoucherRedeemed, endRewardDate, idReward, lotteryRaffle, rewardName, rewardRedeemed,
				rewardRedeemedByUser, startRewardDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reward other = (Reward) obj;
		return Objects.equals(dateVoucherRedeemed, other.dateVoucherRedeemed)
				&& Objects.equals(endRewardDate, other.endRewardDate) && Objects.equals(idReward, other.idReward)
				&& Objects.equals(lotteryRaffle, other.lotteryRaffle) && Objects.equals(rewardName, other.rewardName)
				&& Objects.equals(rewardRedeemed, other.rewardRedeemed)
				&& Objects.equals(rewardRedeemedByUser, other.rewardRedeemedByUser)
				&& Objects.equals(startRewardDate, other.startRewardDate);
	}

}
