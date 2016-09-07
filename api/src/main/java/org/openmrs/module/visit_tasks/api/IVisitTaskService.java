package org.openmrs.module.visit_tasks.api;

import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.visit_tasks.api.model.VisitTask;
import org.openmrs.module.visit_tasks.api.model.VisitTaskStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface that represents classes which perform data operations for {@VisitTask}
 */
@Transactional
public interface IVisitTaskService extends IEntityDataService<VisitTask> {

	@Transactional(readOnly = true)
	List<VisitTask> getVisitTasks(VisitTaskStatus visitTaskStatus, PagingInfo pagingInfo);

}
