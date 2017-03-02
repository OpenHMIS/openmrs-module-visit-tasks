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
package org.openmrs.module.visittasks.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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
import org.openmrs.module.visittasks.api.util.PrivilegeConstants;

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
					if (status.equals(VisitTaskStatus.CLOSED)) {
						criteria.addOrder(Order.desc("closedOn"));
					} else {
						criteria.addOrder(Order.desc("dateCreated"));
					}
				} else {
					criteria.addOrder(Order.desc("dateCreated"));
				}

				if (visit != null) {
					criteria.add(Restrictions.eq("visit", visit));
				}

				if (patient != null) {
					criteria.add(Restrictions.eq("patient", patient));
				}

				criteria.add(Restrictions.eq("voided", false));
			}
		});
	}

	@Override
	public String getVoidPrivilege() {
		return PrivilegeConstants.TASK_MANAGE_VISIT_TASK_METADATA;
	}

	@Override
	public String getSavePrivilege() {
		return PrivilegeConstants.TASK_MANAGE_VISIT_TASK_METADATA;
	}

	@Override
	public String getPurgePrivilege() {
		return PrivilegeConstants.TASK_MANAGE_VISIT_TASK_METADATA;
	}

	@Override
	public String getGetPrivilege() {
		return PrivilegeConstants.TASK_MANAGE_VISIT_TASK_METADATA;
	}
}
