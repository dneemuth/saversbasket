package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.sb.web.entities.audit.Auditable;

@Entity(name="circle")
public class Circle  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "circle_generator")
    @SequenceGenerator(name = "circle_generator", sequenceName = "circle_seq", allocationSize = 1)
    private Integer idCircle;

	@Column(name = "friend1")
	private Integer friend1;
	
	@Column(name = "friend2")
	private Integer friend2;
	
	@Column(name = "status")
	private Integer status;	

	public Integer getIdCircle() {
		return idCircle;
	}

	public void setIdCircle(Integer idCircle) {
		this.idCircle = idCircle;
	}

	public Integer getFriend1() {
		return friend1;
	}

	public void setFriend1(Integer friend1) {
		this.friend1 = friend1;
	}

	public Integer getFriend2() {
		return friend2;
	}

	public void setFriend2(Integer friend2) {
		this.friend2 = friend2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((friend1 == null) ? 0 : friend1.hashCode());
		result = prime * result + ((friend2 == null) ? 0 : friend2.hashCode());
		result = prime * result + ((idCircle == null) ? 0 : idCircle.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Circle other = (Circle) obj;
		if (friend1 == null) {
			if (other.friend1 != null)
				return false;
		} else if (!friend1.equals(other.friend1))
			return false;
		if (friend2 == null) {
			if (other.friend2 != null)
				return false;
		} else if (!friend2.equals(other.friend2))
			return false;
		if (idCircle == null) {
			if (other.idCircle != null)
				return false;
		} else if (!idCircle.equals(other.idCircle))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
}
