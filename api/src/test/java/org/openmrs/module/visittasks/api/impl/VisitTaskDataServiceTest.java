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
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.visittasks.api.IVisitTaskDataService;
import org.openmrs.module.visittasks.api.TestConstants;
import org.openmrs.module.visittasks.api.model.VisitTask;
import org.openmrs.module.visittasks.api.model.VisitTaskStatus;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;
import org.openmrs.test.Verifies;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VisitTaskDataServiceTest extends BaseModuleContextSensitiveTest {

	private static IVisitTaskDataService service;

	@Before
	public void runBeforeAllTests() throws Exception {
		service = Context.getService(IVisitTaskDataService.class);
		initializeInMemoryDatabase();
		executeDataSet(TestConstants.VISIT_TASK_DATASET);
	}

	@Test
	@Verifies(value = "should return closed tasks for a given visit",
	        method = "getVisitTasks(VisitTaskStatus, Visit, PagingInfo)")
	public void shouldGetClosedVisitTasksByVisit() throws Exception {
		PagingInfo pagingInfo = new PagingInfo();
		Visit visit = new Visit();
		visit.setId(1);

		List<VisitTask> results = service.getVisitTasks(VisitTaskStatus.CLOSED, visit, pagingInfo);

		assertNotNull("Should not return null", results);
		assertEquals("Should return one visit task", 1, results.size());
	}

	@Test
	@Verifies(value = "should return open tasks for a given visit",
	        method = "getVisitTasks(VisitTaskStatus, Visit, PagingInfo)")
	public void shouldGetOpenVisitTasksByVisit() throws Exception {
		PagingInfo pagingInfo = new PagingInfo();
		Visit visit = new Visit();
		visit.setId(1);

		List<VisitTask> results = service.getVisitTasks(VisitTaskStatus.OPEN, visit, pagingInfo);

		assertNotNull("Should not return null", results);
		assertEquals("Should return two visit task", 2, results.size());
	}

}
