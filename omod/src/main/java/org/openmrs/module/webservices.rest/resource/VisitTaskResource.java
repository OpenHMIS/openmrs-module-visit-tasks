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

import org.openmrs.annotation.Handler;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.visit_tasks.api.IVisitTaskService;
import org.openmrs.module.visit_tasks.api.model.VisitTask;
import org.openmrs.module.visit_tasks.web.ModuleRestConstants;
import org.openmrs.module.webservices.rest.resource.BaseRestDataResource;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

/**
 * REST resource representing a {@link VisitTask}
 */
@Resource(name = ModuleRestConstants.VISIT_TASKS_RESOURCE, supportedClass = VisitTask.class,
		supportedOpenmrsVersions = { "1.9.*", "1.10.*", "1.11.*", "1.12.*" })
@Handler(supports = { VisitTask.class }, order = 0)
public class VisitTaskResource extends BaseRestDataResource<VisitTask> {

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("name", Representation.DEFAULT);
		description.addProperty("description", Representation.DEFAULT);
		description.addProperty("status", Representation.REF);
		description.addProperty("createdBy", Representation.DEFAULT);
		description.addProperty("createdOn", Representation.DEFAULT);
		description.addProperty("voidedBy", Representation.DEFAULT);
		description.addProperty("voidedOn", Representation.DEFAULT);
		description.addProperty("closedBy", Representation.DEFAULT);
		description.addProperty("closedOn", Representation.DEFAULT);

		return description;
	}

	@Override
	public VisitTask newDelegate() {
		return new VisitTask();
	}

	@Override
	public Class<IEntityDataService<VisitTask>> getServiceClass() {
		return (Class<IEntityDataService<VisitTask>>)(Object)IVisitTaskService.class;
	}
}
