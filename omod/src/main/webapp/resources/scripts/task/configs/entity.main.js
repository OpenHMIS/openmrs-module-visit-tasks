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
 *
 */

/* initialize and bootstrap application */
requirejs(['task/configs/entity.module'], function() {
	angular.bootstrap(document, ['taskApp']);
});

/* load UI messages */
emr.loadMessages([
	"visittasks.page",
	"general.name", "general.description", "general.cancel",
	"general.save",
	"visittasks.task.visit.required",
	"visittasks.task.patient.required",
	"visittasks.task.required",
]);
