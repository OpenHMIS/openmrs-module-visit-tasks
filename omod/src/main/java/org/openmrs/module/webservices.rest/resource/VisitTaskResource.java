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
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.visittasks.api.IVisitTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitTask;
import org.openmrs.module.visittasks.api.model.VisitTaskStatus;
import org.openmrs.module.visittasks.web.ModuleRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

import java.util.Date;

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
		description.addProperty("name");
		description.addProperty("description");
		description.addProperty("status");
		description.addProperty("creator");
		description.addProperty("dateCreated");
		description.addProperty("voidedBy");
		description.addProperty("dateVoided");
		description.addProperty("closedBy");
		description.addProperty("closedOn");
		description.addProperty("voided");
		description.addProperty("patient", Representation.REF);
		description.addProperty("visit", Representation.REF);

		return description;
	}

	@Override
	public VisitTask newDelegate() {
		return new VisitTask();
	}

	@Override
	public Class<IEntityDataService<VisitTask>> getServiceClass() {
		return (Class<IEntityDataService<VisitTask>>)(Object)IVisitTaskDataService.class;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		return getRepresentationDescription(new DefaultRepresentation());
	}

	@Override
	public VisitTask save(VisitTask delegate) {
		if(delegate.getStatus() == VisitTaskStatus.CLOSED){
			delegate.setClosedBy(Context.getAuthenticatedUser());
			delegate.setClosedOn(new Date());
		} else if (delegate.getVoided()){
			delegate.setVoidedBy(Context.getAuthenticatedUser());
			delegate.setDateVoided(new Date());
		}

		return super.save(delegate);
	}
}
