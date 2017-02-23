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

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Visit;
import java.util.Date;

/**
 * Model class that represents a task to be performed during a Patient's visit.
 */
public class VisitTask extends BaseOpenmrsData {
	private Integer visitTaskId;
	private String name;
	private String description;
	private VisitTaskStatus status;
	private User closedBy;
	private Date closedOn;
	private Visit visit;
	private Patient patient;

	@Override
	public Integer getId() {
		return this.visitTaskId;
	}

	@Override
	public void setId(Integer id) {
		this.visitTaskId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
