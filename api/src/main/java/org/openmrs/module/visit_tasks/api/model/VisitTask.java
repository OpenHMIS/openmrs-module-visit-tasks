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
package org.openmrs.module.visit_tasks.api.model;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.User;

import java.util.Date;

public class VisitTask extends BaseOpenmrsMetadata {
	private Integer visitTaskId;
	private User createdBy;
	private Date createdOn;
	private String name;
	private VisitTaskStatus status;
	private User closedBy;
	private Date closedOn;
	private User voidedBy;
	private Date voidedOn;

	@Override
	public Integer getId() {
		return this.visitTaskId;
	}

	@Override
	public void setId(Integer id) {
		this.visitTaskId = id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String taskText) {
		this.name = name;
	}

	public VisitTaskStatus getStatus() {
		return status;
	}

	public void setStatus(VisitTaskStatus status) {
		this.status = status;
	}

	public User getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(User closedBy) {
		this.closedBy = closedBy;
	}

	public Date getClosedOn() {
		return closedOn;
	}

	public void setClosedOn(Date closedOn) {
		this.closedOn = closedOn;
	}

	public User getVoidedBy() {
		return voidedBy;
	}

	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}

	public Date getVoidedOn() {
		return voidedOn;
	}

	public void setVoidedOn(Date voidedOn) {
		this.voidedOn = voidedOn;
	}
}

