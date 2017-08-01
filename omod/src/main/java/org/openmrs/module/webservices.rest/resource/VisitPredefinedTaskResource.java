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
package org.openmrs.module.webservices.rest.resource;

import org.apache.commons.lang.StringUtils;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.visittasks.api.IVisitPredefinedTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitPredefinedTask;
import org.openmrs.module.visittasks.api.util.VisitTasksHibernateCriteriaConstants;
import org.openmrs.module.visittasks.web.ModuleRestConstants;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.EmptySearchResult;

import java.util.List;

/**
 * REST resource representing a {@link VisitPredefinedTask}.
 */
@Resource(name = ModuleRestConstants.VISIT_PREDEFINED_TASK_RESOURCE, supportedClass = VisitPredefinedTask.class,
		supportedOpenmrsVersions = { "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.x" })
@Handler(supports = { VisitPredefinedTask.class }, order = 0)
public class VisitPredefinedTaskResource extends BaseRestMetadataResource<VisitPredefinedTask> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty(VisitTasksHibernateCriteriaConstants.GLOBAL);
		return description;
	}

	@Override
	public VisitPredefinedTask newDelegate() {
		return new VisitPredefinedTask();
	}

	@Override
	public Class<? extends IMetadataDataService<VisitPredefinedTask>> getServiceClass() {
		return IVisitPredefinedTaskDataService.class;
	}

	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return doSearch(context);
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		String query = context.getParameter("q");
		Boolean global = null;
		if(StringUtils.isNotEmpty(context.getParameter(VisitTasksHibernateCriteriaConstants.GLOBAL))){
			global = new Boolean(context.getParameter(VisitTasksHibernateCriteriaConstants.GLOBAL));
		}

		IVisitPredefinedTaskDataService service = Context.getService(IVisitPredefinedTaskDataService.class);
		PagingInfo pagingInfo = PagingUtil.getPagingInfoFromContext(context);

		List<VisitPredefinedTask> visitPredefinedTasks =
				service.getPredefinedTasks(Context.getAuthenticatedUser(), query,global, context.getIncludeAll(), pagingInfo);

		if (visitPredefinedTasks.size() == 0) {
			return new EmptySearchResult();
		} else {
			return
					new AlreadyPagedWithLength<VisitPredefinedTask>(context, visitPredefinedTasks,
							pagingInfo.hasMoreResults(),
							pagingInfo.getTotalRecordCount());
		}
	}
}
