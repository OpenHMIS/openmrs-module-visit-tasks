/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.visittasks.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseEntityDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IEntityAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.visittasks.api.IVisitTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitTask;
import org.openmrs.module.visittasks.api.model.VisitTaskStatus;
import java.util.List;

/**
 * provides {@VisitTask} service implementations.
 */
public class VisitTaskDataServiceImpl extends BaseEntityDataServiceImpl<VisitTask> implements
        IEntityAuthorizationPrivileges, IVisitTaskDataService {
	protected final Log LOG = LogFactory.getLog(this.getClass());

	@Override
	protected IEntityAuthorizationPrivileges getPrivileges() {
		return this;
	}

	@Override
	protected void validate(VisitTask object) {
		return;
	}

	@Override
	public List<VisitTask> getVisitTasksByVisit(VisitTaskStatus status, final Visit visit, PagingInfo pagingInfo) {
		return getVisitTasksByVisitAndPatient(status, visit, null, pagingInfo);
	}

	@Override
	public List<VisitTask> getVisitTasksByPatient(VisitTaskStatus status, Patient patient, PagingInfo pagingInfo) {
		return getVisitTasksByVisitAndPatient(status, null, patient, pagingInfo);
	}

	@Override
	public List<VisitTask> getVisitTasksByVisitAndPatient(final VisitTaskStatus status, final Visit visit,
	        final Patient patient, PagingInfo pagingInfo) {
		return executeCriteria(VisitTask.class, pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				if (status != null) {
					criteria.add(Restrictions.eq("status", status));
				}

				if (visit != null) {
					criteria.add(Restrictions.eq("visit", visit));
				}

				if (patient != null) {
					criteria.add(Restrictions.eq("patient", patient));
				}
			}
		}, getDefaultSort());
	}

	@Override
	public String getVoidPrivilege() {
		return "";
	}

	@Override
	public String getSavePrivilege() {
		return "";
	}

	@Override
	public String getPurgePrivilege() {
		return "";
	}

	@Override
	public String getGetPrivilege() {
		return "";
	}
}
