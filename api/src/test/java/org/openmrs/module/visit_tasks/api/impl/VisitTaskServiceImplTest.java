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
package org.openmrs.module.visit_tasks.api.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.visit_tasks.api.IVisitTaskDataService;
import org.openmrs.module.visit_tasks.api.IVisitTaskDataServiceTest;
import org.openmrs.module.visit_tasks.api.model.VisitTask;
import org.openmrs.module.visit_tasks.api.model.VisitTaskStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitTaskServiceImplTest extends IVisitTaskDataServiceTest {
	private IVisitTaskDataService visitTaskDataService;
	private IVisitTaskDataServiceTest visitTaskDataServiceTest;

	@Before
	public void before() throws Exception {
		super.before();

		visitTaskDataService = createService();
		visitTaskDataServiceTest = new IVisitTaskDataServiceTest();
	}

	@Override
	protected IVisitTaskDataService createService() {
		return Context.getService(IVisitTaskDataService.class);
	}

	@Test
	public void visitTask_shouldCreateAndOpenVisitTask() throws Exception {
		VisitTask visitTask = visitTaskDataServiceTest.createEntity(true);
		visitTask.setStatus(VisitTaskStatus.OPEN);
		visitTask.setCreator(Context.getAuthenticatedUser());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateCreatedStr = "06-09-2016";
		Date dateCreated = sdf.parse(dateCreatedStr);
		visitTask.setDateCreated(dateCreated);

		visitTaskDataService.save(visitTask);
		Context.flushSession();

		Assert.assertNotNull(visitTask);
		Assert.assertEquals(VisitTaskStatus.OPEN, visitTask.getStatus());
	}

	@Test
	public void visitTask_shouldCloseVisitTask() throws Exception {
		VisitTask visitTask = visitTaskDataService.getById(1);

		Assert.assertEquals("Test Visit Task 1", visitTask.getName());
		Assert.assertEquals(VisitTaskStatus.OPEN, visitTask.getStatus());

		visitTask.setStatus(VisitTaskStatus.CLOSED);
		visitTaskDataService.save(visitTask);
		Context.flushSession();

		Assert.assertEquals(VisitTaskStatus.CLOSED, visitTask.getStatus());
	}

	@Test
	public void visitTask_shouldVoidVisitTask() throws Exception {
		VisitTask visitTask = visitTaskDataService.getById(3);

		Assert.assertEquals("Test Visit Task 3", visitTask.getName());
		Assert.assertEquals(VisitTaskStatus.OPEN, visitTask.getStatus());
		visitTaskDataServiceTest.updateEntityFields(visitTask);

		Assert.assertEquals("Test Visit Task 3 updated", visitTask.getName());
		visitTask.setVoided(true);
		visitTask.setVoidedBy(Context.getAuthenticatedUser());
		visitTask.setDateVoided(new Date());
		visitTaskDataService.save(visitTask);
		Context.flushSession();

		Assert.assertEquals(true, visitTask.getVoided());
	}
}
