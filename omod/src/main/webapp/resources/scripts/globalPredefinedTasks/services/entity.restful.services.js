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

(function () {
	'use strict'
	
	angular.module('app.restfulServices').service(
		'PredefinedTasksRestfulService', PredefinedTasksRestfulService);
	
	PredefinedTasksRestfulService.$inject = ['EntityRestFactory', 'PaginationService'];
	
	function PredefinedTasksRestfulService(EntityRestFactory, PaginationService) {
		var service;
		service = {
			getPrivilege: getPrivilege,
			getUserPredefinedTasks: getUserPredefinedTasks
		};
		
		return service;
		
		function getPrivilege(module_name,onLoadPrivilegeSuccessful) {
			var requestParams = [];
			requestParams['resource'] = MODULE_PRIVILEGE_SETTINGS_URL;
			requestParams['privileges'] = TASK_MANAGE_METADATA;
			EntityRestFactory.setCustomBaseUrl(ROOT_URL);
			EntityRestFactory.loadResults(requestParams,
				onLoadPrivilegeSuccessful, errorCallback);
			//reset base url..
			EntityRestFactory.setBaseUrl(module_name);
		}
		
		function getUserPredefinedTasks(currentPage, limit, q,includeRetired,onLoadUserPredefinedTasksSuccessful) {
			currentPage = currentPage || 1;
			var requestParams = PaginationService.paginateParams(currentPage, limit, includeRetired, q);
			requestParams['rest_entity_name'] = 'predefinedTask';
			requestParams['global'] = false;
			EntityRestFactory.loadEntities(requestParams,
				onLoadUserPredefinedTasksSuccessful, errorCallback);
		}
		
		function errorCallback(error) {
			emr.errorAlert(error);
		}
	}
	
})();
