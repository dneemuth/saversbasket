package com.sb.web.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;

@Entity(name="lotteryraffle")
//@Audited
public class LotteryRaffle  extends Auditable<Integer> implements Serializable{

    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idLotteryRaffle")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lottery_raffle_generator")
    @SequenceGenerator(name = "lottery_raffle_generator", sequenceName = "lottery_raffle_seq", allocationSize = 1)
    private Integer idLotteryRaffle;
	
	/** Points to the user of this account. */
	@OneToOne(mappedBy = "lotteryRaffle", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Reward reward;	
	
	/** Points to the user of this account. */
	@OneToOne(mappedBy = "lotteryRaffle", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private User user;
	
	@Column(name = "startLotteryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startLotteryDate; 
		
	@Column(name = "runLotteryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date runLotteryDate; 

	@Column(name = "endLotteryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endLotteryDate; 
		
	@Column(name = "rollOverLotteryDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rollOverLotteryDate;
	
	@Column(name = "frequency")
	private Integer frequency;

	public Integer getIdLotteryRaffle() {
		return idLotteryRaffle;
	}

	public void setIdLotteryRaffle(Integer idLotteryRaffle) {
		this.idLotteryRaffle = idLotteryRaffle;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartLotteryDate() {
		return startLotteryDate;
	}

	public void setStartLotteryDate(Date startLotteryDate) {
		this.startLotteryDate = startLotteryDate;
	}

	public Date getRunLotteryDate() {
		return runLotteryDate;
	}

	public void setRunLotteryDate(Date runLotteryDate) {
		this.runLotteryDate = runLotteryDate;
	}

	public Date getEndLotteryDate() {
		return endLotteryDate;
	}

	public void setEndLotteryDate(Date endLotteryDate) {
		this.endLotteryDate = endLotteryDate;
	}

	public Date getRollOverLotteryDate() {
		return rollOverLotteryDate;
	}

	public void setRollOverLotteryDate(Date rollOverLotteryDate) {
		this.rollOverLotteryDate = rollOverLotteryDate;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endLotteryDate, frequency, idLotteryRaffle, reward, rollOverLotteryDate, runLotteryDate,
				startLotteryDate, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LotteryRaffle other = (LotteryRaffle) obj;
		return Objects.equals(endLotteryDate, other.endLotteryDate) && Objects.equals(frequency, other.frequency)
				&& Objects.equals(idLotteryRaffle, other.idLotteryRaffle) && Objects.equals(reward, other.reward)
				&& Objects.equals(rollOverLotteryDate, other.rollOverLotteryDate)
				&& Objects.equals(runLotteryDate, other.runLotteryDate)
				&& Objects.equals(startLotteryDate, other.startLotteryDate) && Objects.equals(user, other.user);
	}


}
