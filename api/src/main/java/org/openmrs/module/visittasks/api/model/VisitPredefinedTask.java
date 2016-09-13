package org.openmrs.module.visittasks.api.model;

import org.openmrs.BaseOpenmrsMetadata;

/**
 * Model class that represents a Visit Predefined Task.
 */
public class VisitPredefinedTask extends BaseOpenmrsMetadata {
	public static final long serialVersionUID = 0L;

	private Integer visitPredefinedTaskId;

	@Override
	public Integer getId() {
		return this.visitPredefinedTaskId;
	}

	@Override
	public void setId(Integer id) {
		this.visitPredefinedTaskId = id;
	}
}
