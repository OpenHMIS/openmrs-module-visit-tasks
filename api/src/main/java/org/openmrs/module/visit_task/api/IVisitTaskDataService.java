package org.openmrs.module.visit_task.api;

import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.visit_task.api.model.VisitTask;
import org.openmrs.module.visit_task.api.model.VisitTaskStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IVisitTaskDataService extends IMetadataDataService<VisitTask> {

	@Transactional(readOnly = true)
	List<VisitTask> getListOfVisitTasks(VisitTaskStatus visitTaskStatus, PagingInfo pagingInfo);

}
