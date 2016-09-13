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
	VisitTasksController.$inject = ['$stateParams', '$injector', '$scope', '$filter',
		'EntityRestFactory', 'VisitTaskRestfulService', 'VisitTaskModel', 'VisitTaskFunctions', '$location'];

	function VisitTasksController($stateParams, $injector, $scope, $filter, EntityRestFactory,
	                              VisitTaskRestfulService, VisitTaskModel, VisitTaskFunctions, $location) {
		var self = this;
		var entity_name_message_key = emr.message("visit_tasks.page");
		var REST_ENTITY_NAME = "visitTask";

		// @Override
		self.setRequiredInitParameters = self.setRequiredInitParameters || function() {
				self.bindBaseParameters(VISIT_TASKS_MODULE_NAME, REST_ENTITY_NAME,
					entity_name_message_key, '');
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
				$scope.urlArgs = VisitTaskFunctions.extractUrlArgs(window.location.search);
				$scope.visitUuid = $scope.urlArgs['visitUuid'];
				$scope.patientUuid = $scope.urlArgs['patientUuid'];

				console.log($scope.visitUuid)
				console.log($scope.patientUuid)

				$scope.addVisitTask = self.addVisitTask;
				$scope.closeVisitTaskOperation = self.closeVisitTaskOperation;
				$scope.addOrRemovePredefinedTask = false;
				$scope.closeVisitTask = false;

				//load my visit tasks
				VisitTaskRestfulService.getMyVisitTasks(self.onLoadMyVisitTasksSuccessful);


			}

		/**
		 * All post-submit validations are done here.
		 * @return boolean
		 */
		// @Override
		self.validateBeforeSaveOrUpdate = self.validateBeforeSaveOrUpdate || function() {
				if($scope.closeVisitTask !== undefined) {
					$scope.entity = $scope.closeVisitTask;
					$scope.entity.visit = $scope.entity.visit.uuid;
					$scope.entity.patient = $scope.entity.patient.uuid;
					$scope.entity.creator = $scope.entity.creator.uuid;
					delete $scope.entity['$$hashKey'];
					return true;
				} else {
					if($scope.visitUuid === undefined) {
						emr.errorAlert('Visit uuid is required');
						return false;
					}

					if($scope.patientUuid === undefined) {
						emr.errorAlert('Patient uuid is required');
						return false;
					}

					if($scope.addOrRemovePredefinedTask === true) {
						$scope.entity.name = $scope.predefinedVisitTask.name;
						$scope.entity.description = $scope.predefinedVisitTask.description;
						$scope.entity.voided = $scope.predefinedVisitTask.voided;
					}

					var requestPayload = {};
					requestPayload.name = $scope.entity.name;
					requestPayload.description = $scope.entity.description;
					requestPayload.voided = $scope.entity.voided;
					requestPayload.status = 'OPEN';
					requestPayload.visit = $scope.visitUuid;
					requestPayload.patient = $scope.patientUuid;
					$scope.entity = requestPayload;
				}

				console.log($scope.entity);
				return true;
			}

		self.addVisitTask = self.addVisitTask || function(predefinedVisitTask) {
				$scope.addOrRemovePredefinedTask = false;
				$scope.predefinedVisitTask = predefinedVisitTask;
				VisitTaskFunctions.confirmAddTaskDialog($scope, predefinedVisitTask);
			}

		self.closeVisitTaskOperation = self.closeVisitTaskOperation || function(closeVisitTask) {
			VisitTaskFunctions.confirmCloseTaskDialog($scope, closeVisitTask);
		}

		self.onLoadMyVisitTasksSuccessful = self.onLoadMyVisitTasksSuccessful || function(data) {
				$scope.myVisitTasks = data.results;

				//load predefined visit tasks
				VisitTaskRestfulService.getPredefinedVisitTasks(self.onLoadPredefinedVisitTasksSuccessful);
			}

		self.onLoadPredefinedVisitTasksSuccessful = self.onLoadPredefinedVisitTasksSuccessful || function(data) {
				$scope.predefinedVisitTasks = $scope.predefinedVisitTasks || [];
				$scope.selectedTasks = {};
				for(var i = 0; i < data.results.length; i++) {
					var predefinedVisitTask = data.results[i];
					// only display if doesn't appear under My Visit Tasks.
					if(!VisitTaskFunctions.searchVisitTask(
							predefinedVisitTask, $scope.myVisitTasks)) {
						$scope.selectedTasks[predefinedVisitTask.uuid] = predefinedVisitTask;
						$scope.selectedTasks[predefinedVisitTask.uuid].default = false;
						$scope.predefinedVisitTasks.push(predefinedVisitTask);
					}
				}
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
