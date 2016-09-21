package org.openmrs.module.visittasks.api;

import org.openmrs.User;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.visittasks.api.model.VisitPredefinedTask;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface that represents classes which perform data operations for {@link VisitPredefinedTask}s.
 */
@Transactional
public interface IVisitPredefinedTaskDataService extends IMetadataDataService<VisitPredefinedTask> {

	/**
	 * Gets all predefined tasks in the specified {@link org.openmrs.User} that start with the specified name.
	 * @param user The user to search.
	 * @param name The predefined tasks name fragment.
	 * @param includeRetired Whether retired predefined tasks should be included in the results.
	 * @param pagingInfo The paging information.
	 * @return All predefined tasks in the specified {@link org.openmrs.User} that start with the specified name.
	 */
	@Transactional(readOnly = true)
	List<VisitPredefinedTask> getPredefinedTasks(User user, String name, boolean includeRetired,
	        PagingInfo pagingInfo);
}
