package com.sb.web.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sb.web.entities.audit.Auditable;

@Entity(name="userproductaction")
public class UserProductAction  extends Auditable<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUserProductAction")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_product_action_generator")
    @SequenceGenerator(name = "user_product_action_generator", sequenceName = "user_prod_action_seq", allocationSize = 1) 
    private Integer idUserProductAction;
    
    @Size(max = 100)
    @Column(name = "userProductComment")
    private String userActionComment;
    
    @ManyToOne
	@JoinColumn(name = "idProduct")   
	private Product product;

	public Integer getIdUserProductAction() {
		return idUserProductAction;
	}

	public void setIdUserProductAction(Integer idUserProductAction) {
		this.idUserProductAction = idUserProductAction;
	}

	public String getUserActionComment() {
		return userActionComment;
	}

	public void setUserActionComment(String userActionComment) {
		this.userActionComment = userActionComment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUserProductAction == null) ? 0 : idUserProductAction.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((userActionComment == null) ? 0 : userActionComment.hashCode());
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
		UserProductAction other = (UserProductAction) obj;
		if (idUserProductAction == null) {
			if (other.idUserProductAction != null)
				return false;
		} else if (!idUserProductAction.equals(other.idUserProductAction))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (userActionComment == null) {
			if (other.userActionComment != null)
				return false;
		} else if (!userActionComment.equals(other.userActionComment))
			return false;
		return true;
	}

}
