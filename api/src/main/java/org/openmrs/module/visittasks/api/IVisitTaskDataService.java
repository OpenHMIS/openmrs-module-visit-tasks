package org.openmrs.module.visittasks.api;

import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IEntityDataService;
import org.openmrs.module.visittasks.api.model.VisitTask;
import org.openmrs.module.visittasks.api.model.VisitTaskStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface that represents classes which perform data operations for {@VisitTask}
 */
@Transactional
public interface IVisitTaskDataService extends IEntityDataService<VisitTask> {

	@Transactional(readOnly = true)
	List<VisitTask> getVisitTasksByVisit(VisitTaskStatus visitTaskStatus, Visit visit, PagingInfo pagingInfo);

	@Transactional(readOnly = true)
	List<VisitTask> getVisitTasksByPatient(VisitTaskStatus visitTaskStatus, Patient patient, PagingInfo pagingInfo);

	@Transactional(readOnly = true)
	List<VisitTask> getVisitTasksByVisitAndPatient(VisitTaskStatus visitTaskStatus, Visit visit,
	        Patient patient, PagingInfo pagingInfo);

}
