package org.openmrs.module.visit_tasks.api.impl;

import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.visit_tasks.api.IVisitPredefinedTaskDataService;
import org.openmrs.module.visit_tasks.api.model.VisitPredefinedTask;
import org.openmrs.module.visit_tasks.api.security.BasicMetadataAuthorizationPrivileges;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data service implementation class for {@link org.openmrs.module.visit_tasks.api.model.VisitPredefinedTask}s.
 */
@Transactional
public class VisitPredefinedTaskDataServiceImpl  extends BaseMetadataDataServiceImpl<VisitPredefinedTask> implements
		IVisitPredefinedTaskDataService{
	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return new BasicMetadataAuthorizationPrivileges();
	}

	@Override
	protected void validate(VisitPredefinedTask entity) {
		return;
	}
}
