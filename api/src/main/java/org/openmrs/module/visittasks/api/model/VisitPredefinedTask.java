/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * Copyright (C) OpenHMIS.  All Rights Reserved.
 */
package org.openmrs.module.visittasks.api.model;

import org.openmrs.BaseOpenmrsMetadata;

/**
 * Model class that represents a Visit Predefined Task.
 */
public class VisitPredefinedTask extends BaseOpenmrsMetadata {
	public static final long serialVersionUID = 0L;

	private Integer visitPredefinedTaskId;
	private Boolean global;

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	@Override
	public Integer getId() {
		return this.visitPredefinedTaskId;
	}

	@Override
	public void setId(Integer id) {
		this.visitPredefinedTaskId = id;
	}
}
