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

	VisitTaskRestfulService.$inject = ['EntityRestFactory', 'PaginationService', 'Restangular'];

	function VisitTaskRestfulService(EntityRestFactory, PaginationService, Restangular) {
		var service;

		service = {
			getVisitTasks: getVisitTasks,
			getPredefinedVisitTasks: getPredefinedVisitTasks,
			searchPredefinedVisitTasks: searchPredefinedVisitTasks,
		};

		return service;

		function getVisitTasks(currentPage, limit, visitUuid,
		                              patientUuid, status, onLoadVisitTasksSuccessful) {
			var params = [];
			if(currentPage !== '' && limit !== ''){
				params = PaginationService.paginateParams(currentPage, limit, false, '');
			}

			getWSVisitTasks(params, visitUuid, patientUuid, status, onLoadVisitTasksSuccessful);
		}

		function getPredefinedVisitTasks(onLoadPredefinedVisitTasksSuccessful) {
			var requestParams = [];
			requestParams['rest_entity_name'] = 'predefinedTask';
			EntityRestFactory.loadEntities(requestParams, onLoadPredefinedVisitTasksSuccessful, errorCallback);
		}

		function searchPredefinedVisitTasks(search, predefinedTasks) {
			var results = [];
			var requestParams = [];
			if(search !== undefined && search !== '') {
				requestParams['q'] = search;
				return Restangular.all('predefinedTask').customGET('', requestParams).then(function(data) {
					for(var i = 0; i < data.results.length; i++) {
						var predefinedTask = data.results[i];
						if(predefinedTasks.indexOf(predefinedTask.name) >= 0) {
							results.push(predefinedTask);
						}
					}
					return results;
				}, errorCallback);
			}
		}

		function getWSVisitTasks(requestParams, visitUuid, patientUuid, status, onLoadVisitTasksSuccessful) {
			requestParams['rest_entity_name'] = 'task';
			requestParams['visit_uuid'] = visitUuid;
			requestParams['patient_uuid'] = patientUuid;
			requestParams['status'] = status;

			EntityRestFactory.loadEntities(requestParams, onLoadVisitTasksSuccessful, errorCallback);
		}

		function errorCallback(error) {
			emr.errorAlert(error);
		}
	}
})();
