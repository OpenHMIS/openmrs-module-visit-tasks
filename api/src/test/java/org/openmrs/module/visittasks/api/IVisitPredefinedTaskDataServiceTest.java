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

package org.openmrs.module.visittasks.api;

import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;
import org.openmrs.module.visittasks.api.model.VisitPredefinedTask;

public class IVisitPredefinedTaskDataServiceTest
        extends IMetadataDataServiceTest<IVisitPredefinedTaskDataService, VisitPredefinedTask> {
	public static final String VISIT_PREDEFINED_TASK_DATASET = TestConstants.BASE_DATASET_DIR
	        + "VisitPredefinedTaskTest.xml";

	@Override
	public void before() throws Exception {
		super.before();

		executeDataSet(VISIT_PREDEFINED_TASK_DATASET);
	}

	@Override
	protected int getTestEntityCount() {
		return 5;
	}

	@Override
	public VisitPredefinedTask createEntity(boolean valid) {
		VisitPredefinedTask visitPredefinedTask = new VisitPredefinedTask();

		if (valid) {
			visitPredefinedTask.setName("new Visit Predefined Task");
		}

		visitPredefinedTask.setDescription("new Visit PredefinedTask description");
		visitPredefinedTask.setGlobal(true);

		return visitPredefinedTask;
	}

	@Override
	protected void updateEntityFields(VisitPredefinedTask visitPredefinedTask) {
		visitPredefinedTask.setName(visitPredefinedTask.getName() + " updated");
		visitPredefinedTask.setDescription(visitPredefinedTask.getDescription() + " updated");
		visitPredefinedTask.setGlobal(visitPredefinedTask.getGlobal());
	}

	@Override
	protected void assertEntity(VisitPredefinedTask expected, VisitPredefinedTask actual) {
		super.assertEntity(expected, actual);
	}
}
