package org.openmrs.module.visittasks.api;

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
	 * Gets all the predefined tasks for the specified {@link org.openmrs.User}.
	 * @return All predefined tasks for the specified {@link org.openmrs.User}.
	 * @should throw IllegalArgumentException if the predefined tasks is null
	 * @should return an empty list if the User has no predefined tasks
	 * @should not return retired predefined tasks unless specified
	 * @should return all predefined tasks for the specified user
	 */
	@Transactional(readOnly = true)
	List<VisitPredefinedTask> getPredefinedTasksByUser(Integer userId, boolean includeRetired);

	/**
	 * Gets all the predefined tasks for the specified {@link org.openmrs.User}.
	 * @param includeRetired Whether retired predefined tasks should be included in the results.
	 * @param pagingInfo The paging information
	 * @return All predefined tasks for the specified {@link org.openmrs.User}.
	 * @throws org.openmrs.api.APIException
	 */
	@Transactional(readOnly = true)
	List<VisitPredefinedTask> getPredefinedTasksByUser(Integer userId, boolean includeRetired, PagingInfo pagingInfo);

	/**
	 * Gets all predefined tasks in the specified {@link org.openmrs.User} that start with the specified name.
	 * @param userId The user to search.
	 * @param name The predefined tasks name fragment.
	 * @param includeRetired Whether retired predefined tasks should be included in the results.
	 * @return All predefined tasks in the specified {@link org.openmrs.User} that start with the specified name.
	 * @should throw IllegalArgumentException if the user is null
	 * @should throw IllegalArgumentException if the name is null
	 * @should throw IllegalArgumentException if the name is empty
	 * @should throw IllegalArgumentException if the name is longer than 255 characters
	 * @should return an empty list if no predefined tasks are found
	 * @should not return retired predefined tasks unless specified
	 * @should return predefined tasks that start with the specified name
	 * @should return predefined tasks for only the specified user
	 */
	@Transactional(readOnly = true)
	List<VisitPredefinedTask> getPredefinedTasksByUserAndName(Integer userId, String name, boolean includeRetired);

	/**
	 * Gets all predefined tasks in the specified {@link org.openmrs.User} that start with the specified name.
	 * @param userId The user to search.
	 * @param name The predefined tasks name fragment.
	 * @param includeRetired Whether retired predefined tasks should be included in the results.
	 * @param pagingInfo The paging information.
	 * @return All predefined tasks in the specified {@link org.openmrs.User} that start with the specified name.
	 */
	@Transactional(readOnly = true)
	List<VisitPredefinedTask> getPredefinedTasksByUserAndName(Integer userId, String name, boolean includeRetired,
	        PagingInfo pagingInfo);
}
