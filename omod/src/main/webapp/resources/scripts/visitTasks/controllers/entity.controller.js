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

	var base = angular.module('app.genericEntityController');
	base.controller("VisitTasksController", VisitTasksController);
	VisitTasksController.$inject = ['$stateParams', '$injector', '$scope', '$filter', 'EntityRestFactory',
		'VisitTaskRestfulService', 'PaginationService', 'VisitTaskFunctions',
		'CookiesService', 'VisitTaskModel', 'CommonsRestfulFunctions', '$timeout', 'EntityFunctions'];

	function VisitTasksController($stateParams, $injector, $scope, $filter, EntityRestFactory,
	                                   VisitTaskRestfulService, PaginationService, VisitTaskFunctions,
	                                   CookiesService, VisitTaskModel, CommonsRestfulFunctions, $timeout, EntityFunctions) {
		var self = this;
		var entity_name_message_key = emr.message("visit_tasks.page");
		var REST_ENTITY_NAME = "visitTask";

		// @Override
		self.setRequiredInitParameters = self.setRequiredInitParameters || function() {
				self.bindBaseParameters(VISIT_TASKS_MODULE_NAME, REST_ENTITY_NAME, entity_name_message_key, INVENTORY_TASK_DASHBOARD_PAGE_URL);
				//self.checkPrivileges(TASK_ACCESS_CREATE_OPERATION_PAGE);
			}
		/**
		 * Initializes and binds any required variable and/or function specific to entity.page
		 * @type {Function}
		 */
		// @Override
		self.bindExtraVariablesToScope = self.bindExtraVariablesToScope
			|| function() {
				$scope.loading = false;

				//load my visit tasks
				VisitTaskRestfulService.getMyVisitTasks(self.onLoadMyVisitTasksSuccessful);

				//load predefined visit tasks
				VisitTaskRestfulService.getPredefinedVisitTasks(onLoadPredefinedVisitTasks);
			}

		/**
		 * All post-submit validations are done here.
		 * @return boolean
		 */
		// @Override
		self.validateBeforeSaveOrUpdate = self.validateBeforeSaveOrUpdate || function() {
				return true;
			}

		self.onLoadMyVisitTasksSuccessful = self.onLoadMyVisitTasksSuccessful || function(data) {
			console.log(data);
				$scope.myVisitTasks = data.results;
			}

		self.onLoadPredefinedVisitTasks = self.onLoadPredefinedVisitTasks || function(data) {
			console.log(data);
				$scope.predefinedVisitTasks = data.results;
			}

		/* ENTRY POINT: Instantiate the base controller which loads the page */
		$injector.invoke(base.GenericEntityController, self, {
			$scope: $scope,
			$filter: $filter,
			$stateParams: $stateParams,
			EntityRestFactory: EntityRestFactory,
			GenericMetadataModel: VisitTaskModel
		});
	}
})();
