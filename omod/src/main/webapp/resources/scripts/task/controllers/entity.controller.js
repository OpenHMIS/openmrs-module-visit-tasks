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
		'$window', 'PaginationService', 'CookiesService', '$timeout'];

	function VisitTasksController($stateParams, $injector, $scope, $filter, EntityRestFactory,
	                              VisitTaskRestfulService, VisitTaskModel, VisitTaskFunctions,
	                              $window, PaginationService, CookiesService, $timeout) {
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
				$scope.urlArgs = VisitTaskFunctions.extractUrlArgs(window.location.search);
				$scope.visitUuid = $scope.urlArgs['visitUuid'];
				$scope.patientUuid = $scope.urlArgs['patientUuid'];

				$scope.addVisitTask = self.addVisitTask;

				$scope.changeVisitTaskOperation = self.changeVisitTaskOperation;
				$scope.searchPatientTasks = self.searchPatientTasks;
				$scope.searchPredefinedTasks = self.searchPredefinedTasks;
				$scope.changeVisitTask;

				//pagination variables
				$scope.patientTasksCurrentPage = 1;

				$scope.patientTasksLimit = CookiesService.get('patientTasksLimit') || 5;

				$scope.patientTasksPagingFrom = PaginationService.pagingFrom;
				$scope.patientTasksPagingTo = PaginationService.pagingTo;

				$scope.includeClosedTasks = CookiesService.get('includeClosedTasks') === 'true';

				//load patient visit tasks
				self.searchPatientTasks($scope.patientTasksCurrentPage);
				self.searchPredefinedTasks();

				$scope.showDetailsSection = false;
				$scope.toggleDetailsSection = self.toggleDetailsSection;
				$scope.taskName;
				$scope.predefinedTaskName;

				$scope.confirmRemoveTaskDialog = self.confirmRemoveTaskDialog;
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
						emr.errorAlert('visittasks.task.visit.required');
						return false;
					}

					if($scope.patientUuid === undefined) {
						emr.errorAlert('visittasks.task.patient.required');
						return false;
					}

					if(($scope.taskName === undefined || $scope.taskName === "") &&
						($scope.predefinedTaskName === undefined || $scope.predefinedTaskName === "")) {
						emr.errorAlert('visittasks.task.required');
						return false;
					} else {
						$scope.entity.name = $scope.taskName || $scope.predefinedTaskName;
					}

					$scope.entity = self.createVisitTaskEntity($scope.entity);
				}

				return true;
			}

		self.confirmRemoveTaskDialog = self.confirmRemoveTaskDialog || function(task) {
				VisitTaskFunctions.confirmRemoveTaskDialog($scope, task);
			}

		self.toggleDetailsSection = self.toggleDetailsSection || function(entity) {
				if(entity.showDetailsSection) {
					entity.showDetailsSection = false;
				} else {
					entity.showDetailsSection = true;
				}
			}

		self.updateVisitTaskEntity = self.updateVisitTaskEntity || function(entity) {
				var visitTask = {};
				visitTask.uuid = entity.uuid;
				visitTask.name = entity.name;
				visitTask.status = entity.status;
				visitTask.visit = entity.visit.uuid;
				visitTask.patient = entity.patient.uuid;
				visitTask.creator = entity.creator.uuid;

				if(entity.voided === true) {
					visitTask.voided = true;
				}

				return visitTask;
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
				CookiesService.set("patientTasksCurrentPage", currentPage);
				CookiesService.set("patientTasksLimit", $scope.patientTasksLimit);
				CookiesService.set("includeClosedTasks", $scope.includeClosedTasks);

				VisitTaskRestfulService.getPatientVisitTasks(
					CookiesService.get("patientTasksCurrentPage"),
					CookiesService.get("patientTasksLimit"),
					$scope.visitUuid, $scope.patientUuid,
					CookiesService.get("includeClosedTasks"),
					self.onLoadPatientVisitTasksSuccessful);
			}

		self.searchPredefinedTasks = self.searchPredefinedTasks || function() {
				VisitTaskRestfulService.getPredefinedVisitTasks(self.onLoadPredefinedVisitTasksSuccessful);
			}

		self.addVisitTask = self.addVisitTask || function(task) {
				$scope.predefinedTaskName = task.name;
				$scope.taskName = undefined;
				$scope.saveOrUpdate();
			}

		self.changeVisitTaskOperation = self.changeVisitTaskOperation || function(visitTask, id) {
				$scope.changeVisitTask = visitTask;
				if(visitTask.status === 'OPEN') {
					$timeout(function() {
						animate('animation-' + id, 'zoomOut');
						visitTask.status = 'CLOSED';
						$scope.saveOrUpdate();
					}, 500);
				} else {
					$timeout(function() {
						animate('animation-' + id, 'zoomIn');
						visitTask.status = 'OPEN';
						$scope.saveOrUpdate();
					}, 500);
				}
			}

		self.onLoadPatientVisitTasksSuccessful = self.onLoadPatientVisitTasksSuccessful || function(data) {
				$scope.patientTasks = data.results;
				for(var i = 0; i < $scope.patientTasks.length; i++) {
					if($scope.patientTasks[i].status === 'CLOSED') {
						$scope.patientTasks[i].checked = true;
					} else {
						$scope.patientTasks[i].checked = false;
					}
				}

				$scope.patientTasksTotalResults = data.length;
			}

		self.onLoadPredefinedVisitTasksSuccessful = self.onLoadPredefinedVisitTasksSuccessful || function(data) {
				$scope.predefinedVisitTasks = [];
				if(data.results.length > 0) {
					VisitTaskRestfulService.getAllPatientVisitTasks(
						$scope.visitUuid, $scope.patientUuid, $scope.includeClosedTasks,
						function(patientTasks) {
							if(patientTasks.results.length > 0) {
								for(var i = 0; i < data.results.length; i++) {
									var predefinedVisitTask = data.results[i];
									// only display if doesn't appear under Patient Visit Tasks.
									if(!VisitTaskFunctions.searchVisitTask(
											predefinedVisitTask, patientTasks.results)) {
										$scope.predefinedVisitTasks.push(predefinedVisitTask);
									}
								}
							} else {
								$scope.predefinedVisitTasks = data.results;
							}
						}
					);
				}
			}

		// @Override
		self.cancel = self.cancel || function() {
				$window.location = decodeURIComponent($scope.urlArgs['returnUrl']);
			}

		// @Override
		self.onChangeEntitySuccessful = self.onChangeEntitySuccessful || function() {
				if($scope.entity.voided !== true){
					if($scope.changeVisitTask === undefined) {
						$timeout(function() {
							animate('animation-0', 'swing');
						}, 300);
					}
				}

				$scope.entity = {};
				$scope.predefinedTaskName = "";
				$scope.taskName = "";
				$scope.changeVisitTask = undefined;
				self.searchPatientTasks(1);
				self.searchPredefinedTasks();
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
