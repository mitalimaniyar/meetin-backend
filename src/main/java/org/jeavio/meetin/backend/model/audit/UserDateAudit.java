package org.jeavio.meetin.backend.model.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * For Auditing purpose
 * 
 *   
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class UserDateAudit extends DateAudit {

    /**
	 * 
	 */
	private static final long serialVersionUID = -424152558269769973L;

	@CreatedBy
	@Column(name = "created_by")
    private Integer createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Integer updatedBy;

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

    
}
