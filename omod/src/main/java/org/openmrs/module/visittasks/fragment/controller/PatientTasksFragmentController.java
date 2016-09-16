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

package org.openmrs.module.visittasks.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.visittasks.api.IVisitTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitTask;
import org.openmrs.module.visittasks.api.model.VisitTaskStatus;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.List;

/**
 * Spring OpenMRS 2.x Controller for Patient Visit Tasks page
 */
public class PatientTasksFragmentController {

	public void controller(FragmentModel model,
			@FragmentParam("patientId") Patient patient,
			@SpringBean("visitTaskDataService")IVisitTaskDataService visitTaskDataService){
		List<VisitTask> patientTasks = visitTaskDataService.getVisitTasksByVisitAndPatient(
				VisitTaskStatus.OPEN, null, patient, new PagingInfo(1, 10));

		model.addAttribute("patientTasks", patientTasks);
	}
}

