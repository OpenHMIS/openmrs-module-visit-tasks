/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.visittasks.api.impl;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.visittasks.api.IVisitPredefinedTaskDataService;
import org.openmrs.module.visittasks.api.TestConstants;
import org.openmrs.module.visittasks.api.model.VisitPredefinedTask;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VisitPredefinedTaskDataServiceTest extends BaseModuleContextSensitiveTest {

	private static IVisitPredefinedTaskDataService service;

	@Before
	public void runBeforeAllTests() throws Exception {
		service = Context.getService(IVisitPredefinedTaskDataService.class);
		initializeInMemoryDatabase();
		executeDataSet(TestConstants.VISIT_PREDEFINED_TASK_DATASET);
	}

	@Test
	@Verifies(value = "should get predefined tasks",
	        method = "getPredefinedTasks(User, String, String, boolean, PagingInfo)")
	public void shouldGetPredefinedTasks() throws Exception {
		// retrieve just my predefined tasks.
		User user = new User();
		user.setId(1);

		List<VisitPredefinedTask> results = service.getPredefinedTasks(user, "", false, false, new PagingInfo());

		assertNotNull("Should not return null", results);
		assertEquals("Should return two predefined visit tasks", 2, results.size());

		// retrieve only global predefined tasks
		results = service.getPredefinedTasks(user, "", true, false, new PagingInfo());

		assertNotNull("Should not return null", results);
		assertEquals("Should return three global predefined visit tasks", 3, results.size());

		// retrieve all (including global) predefined tasks
		results = service.getPredefinedTasks(user, "", null, false, new PagingInfo());

		assertNotNull("Should not return null", results);
		assertEquals("Should return all predefined visit tasks", 5, results.size());

		try {
			// retrieve tasks by passing null values
			service.getPredefinedTasks(null, null, null, false, null);
		} catch (IllegalArgumentException ex) {
			assertNotNull("Should throw an IllegalArgument Exception", ex);
		}
	}
}
