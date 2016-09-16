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

(function() {
	'use strict';

	angular.module('app.restfulServices').service('VisitTaskRestfulService', VisitTaskRestfulService);

	VisitTaskRestfulService.$inject = ['EntityRestFactory', 'PaginationService'];

	function VisitTaskRestfulService(EntityRestFactory, PaginationService) {
		var service;

		service = {
			getPatientVisitTasks: getPatientVisitTasks,
			getPredefinedVisitTasks: getPredefinedVisitTasks,
		};

		return service;

		function getPatientVisitTasks(currentPage, limit, visitUuid,
		                         patientUuid, includeClosedTasks, onLoadMyVisitTasksSuccessful) {
			var requestParams = PaginationService.paginateParams(currentPage, limit, false, '');
			requestParams['rest_entity_name'] = 'task';
			requestParams['visit_uuid'] = visitUuid;
			requestParams['patient_uuid'] = patientUuid;
			if(includeClosedTasks !== true) {
				requestParams['status'] = 'OPEN';
			}

			EntityRestFactory.loadEntities(requestParams,
				onLoadMyVisitTasksSuccessful, errorCallback);
		}

		function getPredefinedVisitTasks(currentPage, limit, onLoadPredefinedVisitTasksSuccessful) {
			var requestParams = PaginationService.paginateParams(currentPage, limit, false, '');
			requestParams['rest_entity_name'] = 'predefinedTasks';

			EntityRestFactory.loadEntities(requestParams,
				onLoadPredefinedVisitTasksSuccessful, errorCallback);
		}

		function errorCallback(error) {
			emr.errorAlert(error);
		}
	}
})();
