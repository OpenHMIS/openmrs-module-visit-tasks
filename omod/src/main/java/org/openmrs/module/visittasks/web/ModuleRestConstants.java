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
package org.openmrs.module.visittasks.web;

import org.openmrs.module.openhmis.commons.web.WebConstants;
import org.openmrs.module.webservices.rest.web.RestConstants;

/**
 * Constants class for REST urls.
 */
public class ModuleRestConstants extends WebConstants {
	public static final String MODULE_REST_ROOT = RestConstants.VERSION_2 + "/visittasks/";
	public static final String VISIT_PREDEFINED_TASK_RESOURCE = MODULE_REST_ROOT + "predefinedTask";
	public static final String VISIT_TASKS_RESOURCE = MODULE_REST_ROOT + "task";
}
