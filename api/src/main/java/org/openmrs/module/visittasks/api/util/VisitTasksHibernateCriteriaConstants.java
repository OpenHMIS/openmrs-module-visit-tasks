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
package org.openmrs.module.visittasks.api.util;

/**
 * Constants class for Hibernate criteria fields.
 */
public class VisitTasksHibernateCriteriaConstants {
	public static final String USER = "creator";
	public static final String NAME = "name";
	public static final String RETIRED = "retired";
	public static final String GLOBAL = "global";
	public static final String DATE_CREATED = "dateCreated";
	public static final String STATUS = "status";
	public static final String CLOSED_ON = "closedOn";
	public static final String VISIT = "visit";
	public static final String VOIDED = "voided";

	// should not be instantiated.
	protected VisitTasksHibernateCriteriaConstants() {}
}
