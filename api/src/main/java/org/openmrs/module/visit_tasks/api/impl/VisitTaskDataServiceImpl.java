/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.visit_tasks.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.visit_tasks.api.IVisitTaskDataService;
import org.openmrs.module.visit_tasks.api.model.VisitTask;
import org.openmrs.module.visit_tasks.api.model.VisitTaskStatus;

import java.util.List;

/**
 * provides {@VisitTask} service implementations.
 */
public class VisitTaskDataServiceImpl extends BaseMetadataDataServiceImpl<VisitTask> implements IVisitTaskDataService {
	protected final Log LOG = LogFactory.getLog(this.getClass());

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(VisitTask object) { return; }

	@Override public List<VisitTask> getListOfVisitTasks(final VisitTaskStatus visitTaskStatus, PagingInfo pagingInfo) {
		return executeCriteria(VisitTask.class, pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.eq("visitTaskStatus", visitTaskStatus));
			}
		}, getDefaultSort());
	}
}
