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
		'$window', 'PaginationService', 'CookiesService'];

	function VisitTasksController($stateParams, $injector, $scope, $filter, EntityRestFactory,
	                              VisitTaskRestfulService, VisitTaskModel, VisitTaskFunctions,
	                              $window, PaginationService, CookiesService) {
		var self = this;
		var entity_name_message_key = emr.message("visit_tasks.page");
		var REST_ENTITY_NAME = "task";

		// @Override
		self.setRequiredInitParameters = self.setRequiredInitParameters || function() {
				self.bindBaseParameters(VISIT_TASKS_MODULE_NAME, REST_ENTITY_NAME,
					entity_name_message_key, '');
				self.checkPrivileges(TASK_MANAGE_VISIT_TASK_METADATA);
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
				$scope.visitActive = $scope.urlArgs['active'];

				$scope.addVisitTask = self.addVisitTask;

				$scope.changeVisitTaskOperation = self.changeVisitTaskOperation;
				$scope.searchOpenPatientTasks = self.searchOpenPatientTasks;
				$scope.searchClosedPatientTasks = self.searchClosedPatientTasks;
				$scope.searchPredefinedTasks = self.searchPredefinedTasks;
				$scope.getPredefinedTasks = self.getPredefinedTasks;
				$scope.changeVisitTask;

				//pagination variables
				$scope.openPatientTasksCurrentPage = 1;
				$scope.closedPatientTasksCurrentPage = 1;

				$scope.openPatientTasksLimit = CookiesService.get('openPatientTasksLimit') || 5;
				$scope.closedPatientTasksLimit = CookiesService.get('closedPatientTasksLimit') || 5;

				$scope.openPatientTasksPagingFrom = PaginationService.pagingFrom;
				$scope.closedPatientTasksPagingFrom = PaginationService.pagingFrom;
				$scope.openPatientTasksPagingTo = PaginationService.pagingTo;
				$scope.closedPatientTasksPagingTo = PaginationService.pagingTo;

				//load patient visit tasks
				self.searchOpenPatientTasks($scope.openPatientTasksCurrentPage);
				self.searchClosedPatientTasks($scope.closedPatientTasksCurrentPage);
				self.getPredefinedTasks();

				$scope.showDetailsSection = false;
				$scope.toggleDetailsSection = self.toggleDetailsSection;
				$scope.taskName;
				$scope.predefinedTaskName;

				$scope.confirmRemoveTaskDialog = self.confirmRemoveTaskDialog;
				$scope.expandTasks = self.expandTasks;
				$scope.expandedTasks = false;
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

		self.searchOpenPatientTasks = self.searchOpenPatientTasks || function(currentPage) {
				CookiesService.set("openPatientTasksCurrentPage", currentPage);
				CookiesService.set("openPatientTasksLimit", $scope.openPatientTasksLimit);

				VisitTaskRestfulService.getPatientVisitTasks(
					CookiesService.get("openPatientTasksCurrentPage"),
					CookiesService.get("openPatientTasksLimit"),
					$scope.visitUuid, $scope.patientUuid, "OPEN",
					self.onLoadOpenPatientVisitTasksSuccessful);
			}

		self.searchClosedPatientTasks = self.searchClosedPatientTasks || function(currentPage) {
				CookiesService.set("closedPatientTasksCurrentPage", currentPage);
				CookiesService.set("closedPatientTasksLimit", $scope.closedPatientTasksLimit);

				VisitTaskRestfulService.getPatientVisitTasks(
					CookiesService.get("closedPatientTasksCurrentPage"),
					CookiesService.get("closedPatientTasksLimit"),
					$scope.visitUuid, $scope.patientUuid, "CLOSED",
					self.onLoadClosedPatientVisitTasksSuccessful);
			}

		// search a predefined task
		self.searchPredefinedTasks = self.searchPredefinedTasks || function(search) {
				return VisitTaskRestfulService.searchPredefinedVisitTasks(search, $scope.predefinedVisitTaskNames);
			}

		// retrieves all predefined tasks.
		self.getPredefinedTasks = self.getPredefinedTasks || function() {
				return VisitTaskRestfulService.getPredefinedVisitTasks(self.onLoadPredefinedVisitTasksSuccessful);
			}

		self.addVisitTask = self.addVisitTask || function(task) {
				$scope.predefinedTaskName = task.name;
				$scope.taskName = undefined;
				$scope.saveOrUpdate();
			}

		self.changeVisitTaskOperation = self.changeVisitTaskOperation || function(visitTask) {
				$scope.changeVisitTask = visitTask;
				$scope.changeVisitTask.animate = true;
				if($scope.changeVisitTask.status === 'OPEN') {
					$scope.changeVisitTask.status = 'CLOSED';
				} else {
					$scope.changeVisitTask.status = 'OPEN';
				}

				$scope.saveOrUpdate();
			}

		self.expandTasks = self.expandTasks || function(expandAction) {
				$scope.expandedTasks = expandAction;
				for(var i = 0; i < $scope.openPatientTasks.length; i++) {
					$scope.openPatientTasks[i].showDetailsSection = expandAction;
				}

				for(var i = 0; i < $scope.closedPatientTasks.length; i++) {
					$scope.closedPatientTasks[i].showDetailsSection = expandAction;
				}
			}

		self.onLoadOpenPatientVisitTasksSuccessful = self.onLoadOpenPatientVisitTasksSuccessful || function(data) {
				$scope.openPatientTasks = data.results;
				$scope.openPatientTasksTotalResults = data.length;
				if($scope.animate) {
					$scope.openPatientTasks[0].animate = true;
				}

				if($scope.expandedTasks === true) {
					self.expandTasks(true);
				}

				$scope.animate = false;
			}

		self.onLoadClosedPatientVisitTasksSuccessful = self.onLoadClosedPatientVisitTasksSuccessful || function(data) {
				$scope.closedPatientTasks = data.results;
				for(var i = 0; i < $scope.closedPatientTasks.length; i++) {
					$scope.closedPatientTasks[i].checked = true;
				}

				$scope.closedPatientTasksTotalResults = data.length;

				if($scope.expandedTasks === true) {
					self.expandTasks(true);
				}
			}

		self.onLoadPredefinedVisitTasksSuccessful = self.onLoadPredefinedVisitTasksSuccessful || function(data) {
				$scope.predefinedVisitTasks = [];
				$scope.predefinedVisitTaskNames = [];
				if(data.results.length > 0) {
					VisitTaskRestfulService.getAllPatientVisitTasks(
						$scope.visitUuid, $scope.patientUuid, "",
						function(patientTasks) {
							if(patientTasks.results.length > 0) {
								for(var i = 0; i < data.results.length; i++) {
									var predefinedVisitTask = data.results[i];
									// only show a predefined task that is not being used.
									if(!VisitTaskFunctions.searchVisitTask(
											predefinedVisitTask, patientTasks.results)) {
										$scope.predefinedVisitTasks.push(predefinedVisitTask);
										$scope.predefinedVisitTaskNames.push(predefinedVisitTask.name);
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
				if($scope.entity.voided !== true) {
					if($scope.changeVisitTask === undefined) {
						$scope.animate = true;
					}
				}

				$scope.entity = {};
				$scope.predefinedTaskName = "";
				$scope.taskName = "";
				$scope.changeVisitTask = undefined;
				self.searchOpenPatientTasks(1);
				self.searchClosedPatientTasks(1);
				self.getPredefinedTasks();
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
