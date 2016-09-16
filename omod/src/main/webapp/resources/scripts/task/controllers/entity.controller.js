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
		'EntityRestFactory', 'VisitTaskRestfulService', 'VisitTaskModel', 'VisitTaskFunctions',
		'$window', 'PaginationService'];

	function VisitTasksController($stateParams, $injector, $scope, $filter, EntityRestFactory,
	                              VisitTaskRestfulService, VisitTaskModel, VisitTaskFunctions,
	                              $window, PaginationService) {
		var self = this;
		var entity_name_message_key = emr.message("visit_tasks.page");
		var REST_ENTITY_NAME = "task";

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

				$scope.selectedVisitTasks = [];
				$scope.addVisitTask = self.addVisitTask;

				$scope.changeVisitTaskOperation = self.changeVisitTaskOperation;
				$scope.searchPatientTasks = self.searchPatientTasks;
				$scope.searchPredefinedTasks = self.searchPredefinedTasks;
				$scope.addOrRemovePredefinedTask = false;
				$scope.changeVisitTask;

				//pagination variables
				$scope.patientTasksCurrentPage = $scope.patientTasksCurrentPage || 1;
				$scope.predefinedTasksCurrentPage = $scope.predefinedTasksCurrentPage || 1;

				$scope.patientTasksLimit = 5;
				$scope.predefinedTasksLimit = 5;

				$scope.patientTasksPagingFrom = PaginationService.pagingFrom;
				$scope.predefinedTasksPagingFrom = PaginationService.pagingFrom;
				$scope.patientTasksPagingTo = PaginationService.pagingTo;
				$scope.predefinedTasksPagingTo = PaginationService.pagingTo;

				$scope.includeClosedTasks = false;

				//load my visit tasks
				self.searchPatientTasks($scope.patientTasksCurrentPage);

				$scope.showDetailsSection = false;
				$scope.toggleDetailsSection = self.toggleDetailsSection;
				$scope.updateVisitTask = self.updateVisitTask;
			}

		/**
		 * All post-submit validations are done here.
		 * @return boolean
		 */
		// @Override
		self.validateBeforeSaveOrUpdate = self.validateBeforeSaveOrUpdate || function() {
				if($scope.changeVisitTask !== undefined) {
					$scope.entity = self.updateVisitTaskEntity($scope.changeVisitTask);
					return true;
				} else if($scope.entity.patient !== undefined && $scope.entity.patient.uuid !== undefined && $scope.entity.visit.uuid !== undefined) {
					$scope.entity = self.updateVisitTaskEntity($scope.entity);
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

					if(($scope.entity.name === undefined || $scope.entity.name === "") &&
						$scope.selectedVisitTasks.length === 0) {
						emr.errorAlert('Visit Task required');
						return false;
					}

					if($scope.selectedVisitTasks.length > 0) {
						var visitTaskSummaryList = [];

						if($scope.entity.name !== undefined && $scope.entity.name !== "") {
							$scope.entity = self.createVisitTaskEntity($scope.entity);
						}

						for(var i = 0; i < $scope.selectedVisitTasks.length; i++) {
							var visitTask = self.createVisitTaskEntity($scope.selectedVisitTasks[i]);
							if($scope.entity.name === undefined || $scope.entity.name === "") {
								$scope.entity = visitTask;
							} else {
								visitTaskSummaryList.push(visitTask);
							}
						}

						$scope.entity.visitTaskSummaryList = visitTaskSummaryList;
					} else {
						$scope.entity = self.createVisitTaskEntity($scope.entity);
					}
				}

				$scope.loading = true;
				return true;
			}

		self.toggleDetailsSection = self.toggleDetailsSection || function(entity) {
				if(entity.showDetailsSection) {
					entity.showDetailsSection = false;
				} else {
					entity.showDetailsSection = true;
				}
			}

		self.updateVisitTaskEntity = self.updateVisitTaskEntity || function(entity) {
				var visitTask = entity;
				visitTask.visit = entity.visit.uuid;
				visitTask.patient = entity.patient.uuid;
				visitTask.creator = entity.creator.uuid;

				delete visitTask['$$hashKey'];
				delete visitTask['showDetailsSection'];
				delete visitTask['checked'];
				delete visitTask['closedBy'];
				delete visitTask['closedOn'];
				return visitTask;
			}

		self.updateVisitTask = self.updateVisitTask || function(entity) {
				$scope.loading = true;
				$scope.entity = entity;
				$scope.saveOrUpdate();
			}

		self.createVisitTaskEntity = self.createVisitTaskEntity || function(entity) {
				var visitTask = {};
				visitTask.name = entity.name;
				visitTask.description = entity.description;
				visitTask.status = 'OPEN';
				visitTask.visit = $scope.visitUuid;
				visitTask.patient = $scope.patientUuid;
				return visitTask;
			}

		self.searchPatientTasks = self.searchPatientTasks || function(currentPage) {
				VisitTaskRestfulService.getPatientVisitTasks(currentPage,
					$scope.patientTasksLimit, $scope.visitUuid,
					$scope.patientUuid, $scope.includeClosedTasks,
					self.onLoadPatientVisitTasksSuccessful);
			}

		self.searchPredefinedTasks = self.searchPredefinedTasks || function(currentPage) {
				VisitTaskRestfulService.getPredefinedVisitTasks(
					currentPage,
					$scope.predefinedTasksLimit,
					self.onLoadPredefinedVisitTasksSuccessful);
			}

		self.addVisitTask = self.addVisitTask || function(predefinedVisitTask) {
				if(predefinedVisitTask.checked === true) {
					$scope.selectedVisitTasks.push(predefinedVisitTask);
				} else {
					self.removeVisitTask(predefinedVisitTask);
				}

				$scope.saveOrUpdate();
			}

		self.removeVisitTask = self.removeVisitTask || function(predefinedVisitTask) {
				var index = $scope.selectedVisitTasks.indexOf(predefinedVisitTask);
				if(index !== -1) {
					$scope.selectedVisitTasks.splice(index, 1);
				}
			}

		self.changeVisitTaskOperation = self.changeVisitTaskOperation || function(visitTask) {
				VisitTaskFunctions.confirmChangeVisitTaskDialog($scope, visitTask);
			}

		self.onLoadPatientVisitTasksSuccessful = self.onLoadPatientVisitTasksSuccessful || function(data) {
				$scope.patientTasks = data.results;
				for(var i = 0; i < $scope.patientTasks.length; i++){
					if($scope.patientTasks[i].status === 'CLOSED'){
						$scope.patientTasks[i].checked = true;
					} else{
						$scope.patientTasks[i].checked = false;
					}
				}

				$scope.patientTasksTotalResults = data.length;

				//load predefined visit tasks
				if($scope.predefinedVisitTasks === undefined) {
					self.searchPredefinedTasks(1);
				}
			}

		self.onLoadPredefinedVisitTasksSuccessful = self.onLoadPredefinedVisitTasksSuccessful || function(data) {
				$scope.predefinedVisitTasks = [];
				$scope.selectedTasks = {};
				$scope.predefinedTasksTotalResults = data.length;
				for(var i = 0; i < data.results.length; i++) {
					var predefinedVisitTask = data.results[i];
					// only display if doesn't appear under My Visit Tasks.
					if(!VisitTaskFunctions.searchVisitTask(
							predefinedVisitTask, $scope.patientTasks)) {
						$scope.selectedTasks[predefinedVisitTask.uuid] = predefinedVisitTask;
						$scope.selectedTasks[predefinedVisitTask.uuid].default = false;
						$scope.predefinedVisitTasks.push(predefinedVisitTask);
					}
				}
			}

		// @Override
		self.cancel = self.cancel || function() {
				$window.location = decodeURIComponent($scope.urlArgs['returnUrl']);
			}

		// @Override
		self.onChangeEntitySuccessful = self.onChangeEntitySuccessful || function() {
				$window.location.reload();
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
