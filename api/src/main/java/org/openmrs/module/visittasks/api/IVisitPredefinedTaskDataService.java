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
	List<VisitPredefinedTask> getPredefinedTasks(User user, String name, String showGlobal, boolean includeRetired,
	        PagingInfo pagingInfo);
}
