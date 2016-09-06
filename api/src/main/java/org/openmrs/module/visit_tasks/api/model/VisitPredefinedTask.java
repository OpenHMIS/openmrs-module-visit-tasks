package org.openmrs.module.visit_tasks.api.model;

import org.openmrs.BaseOpenmrsMetadata;

public class VisitPredefinedTask extends BaseOpenmrsMetadata {
	public static final long serialVersionUID = 1L;

	private Integer visitPredefinedTaskId;

	@Override public Integer getId() {
		return this.visitPredefinedTaskId;
	}

	@Override public void setId(Integer id) {
		this.visitPredefinedTaskId = id;
	}

	public String toString() {
		return getName();
	}
}
