package org.openmrs.module.visit_tasks.api.model;

import org.openmrs.module.openhmis.commons.api.entity.model.BaseSerializableOpenmrsMetadata;

/**
 * Model class that represents a Predefined Task.
 */
public class VisitPredefinedTask extends BaseSerializableOpenmrsMetadata {
	public static final long serialVersionUID = 0L;

	private Integer visitPredefinedTaskId;

	@Override public Integer getId() {
		return this.visitPredefinedTaskId;
	}

	@Override public void setId(Integer id) {
		this.visitPredefinedTaskId = id;
	}
}
