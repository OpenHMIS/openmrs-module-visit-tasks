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

package org.openmrs.module.webservices.rest.search;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Visit;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.VisitService;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.visittasks.api.IVisitTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitTask;
import org.openmrs.module.visittasks.api.model.VisitTaskStatus;
import org.openmrs.module.visittasks.web.ModuleRestConstants;
import org.openmrs.module.webservices.rest.resource.AlreadyPagedWithLength;
import org.openmrs.module.webservices.rest.resource.PagingUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.SearchConfig;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;
import org.openmrs.module.webservices.rest.web.resource.api.SearchQuery;
import org.openmrs.module.webservices.rest.web.resource.impl.EmptySearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Search handler for {@link VisitTask}s.
 */
@Component
public class VisitTaskSearchHandler implements SearchHandler {
	private static final Log LOG = LogFactory.getLog(VisitTaskSearchHandler.class);

	private IVisitTaskDataService visitTaskService;
	private VisitService visitService;
	private PatientService patientService;

	@Autowired
	public VisitTaskSearchHandler(IVisitTaskDataService visitTaskService, VisitService visitService,
			PatientService patientService){
		this.visitTaskService = visitTaskService;
		this.visitService = visitService;
		this.patientService = patientService;
	}

	private final SearchConfig searchConfig =
			new SearchConfig("default", ModuleRestConstants.VISIT_TASKS_RESOURCE,
					Arrays.asList("*"),
					Arrays.asList(
							new SearchQuery.Builder("Find a visitTask by status, visit and patient")
									.withOptionalParameters("status", "visit_uuid", "patient_uuid")
									.build()
					)
			);

	@Override
	public SearchConfig getSearchConfig() {
		return searchConfig;
	}

	@Override
	public PageableResult search(RequestContext context) {
		String statusText = context.getParameter("status");
		String visitText = context.getParameter("visit_uuid");
		String patientText = context.getParameter("patient_uuid");

		VisitTaskStatus status = null;
		if (!StringUtils.isEmpty(statusText) && StringUtils.equalsIgnoreCase(statusText, "*")) {
			status = VisitTaskStatus.valueOf(statusText);
			if (status == null) {
				LOG.warn("Could not parse Visit Task Status '" + statusText + "'");
				return new EmptySearchResult();
			}
		}

		Visit visit = null;
		if( !StringUtils.isEmpty(visitText)){
			visit = visitService.getVisitByUuid(visitText);
			if(visit == null){
				LOG.warn("Could not find Visit '" + visitText + "'");
				return new EmptySearchResult();
			}
		}

		Patient patient = null;
		if( !StringUtils.isEmpty(patientText)){
			patient = patientService.getPatientByUuid(patientText);
			if(patient == null){
				LOG.warn("Could not find Patient '" + patientText + "'");
				return new EmptySearchResult();
			}
		}

		PagingInfo pagingInfo = PagingUtil.getPagingInfoFromContext(context);
		List<VisitTask> visitTasks = visitTaskService.getVisitTasksByVisitAndPatient(status, visit, patient, pagingInfo);

		if (visitTasks.size() == 0) {
			return new EmptySearchResult();
		} else{
			return new AlreadyPagedWithLength<VisitTask>(context, visitTasks, pagingInfo.hasMoreResults(),
					pagingInfo.getTotalRecordCount());
		}
	}
}
