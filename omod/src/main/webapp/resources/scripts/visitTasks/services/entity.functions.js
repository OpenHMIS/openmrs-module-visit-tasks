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

	var app = angular.module('app.visitTaskFunctionsFactory', []);
	app.service('VisitTaskFunctions', VisitTaskFunctions);

	VisitTaskFunctions.$inject = ['EntityFunctions'];

	function VisitTaskFunctions(EntityFunctions) {
		var service;

		service = {
			extractUrlArgs: extractUrlArgs,
			confirmAddTaskDialog: confirmAddTaskDialog,
			searchVisitTask: searchVisitTask,
			confirmCloseTaskDialog: confirmCloseTaskDialog,
		};

		function extractUrlArgs(urlArgs) {
			var urlParams = [];
			urlArgs = urlArgs.replace('?', '');
			if(urlArgs.indexOf("&") > 0) {
				var params = urlArgs.split("&");
				for(var i = 0; i < params.length; i++) {
					var param = params[i];
					var paramArgs = param.split("=");
					urlParams[paramArgs[0]] = paramArgs[1];
				}
			}

			return urlParams;
		}

		function confirmAddTaskDialog($scope, predefinedVisitTask) {
			if(predefinedVisitTask.checked === true) {
				$scope.taskConfirmTitle = 'Add Visit Task';
				$scope.taskConfirmMessage = 'Are you sure you want to add Task: ' + predefinedVisitTask.name + '?';
			} else {
				$scope.taskConfirmTitle = 'Remove Visit Task';
				$scope.taskConfirmMessage = 'Are you sure you want to remove Task: ' + predefinedVisitTask.name + '?';
			}

			var dialog = emr.setupConfirmationDialog({
				selector: '#task-confirm-dialog',
				actions: {
					confirm: function() {
						$scope.addOrRemovePredefinedTask = true;
						if(predefinedVisitTask.checked === true) {
							predefinedVisitTask.default = true;
							predefinedVisitTask.voided = false;
						} else {
							predefinedVisitTask.default = false;
							predefinedVisitTask.voided = true;
						}

						$scope.saveOrUpdate();

						$scope.$apply();
						dialog.close();
					},
					cancel: function() {
						predefinedVisitTask.checked = predefinedVisitTask.default;
						$scope.$apply();
						dialog.close();
					}
				}
			});

			dialog.show();

			EntityFunctions.disableBackground();
		}

		function confirmCloseTaskDialog($scope, visitTask) {
			var dialog = emr.setupConfirmationDialog({
				selector: '#close-task-confirm-dialog',
				actions: {
					confirm: function() {
						$scope.closeVisitTask = visitTask;
						$scope.closeVisitTask.status = 'CLOSED';
						$scope.saveOrUpdate();
						$scope.$apply();
						dialog.close();
					},
					cancel: function() {
						dialog.close();
					}
				}
			});

			dialog.show();

			EntityFunctions.disableBackground();
		}

		function searchVisitTask(predefinedVisitTask, myVisitTasks) {
			for(var i = 0; i < myVisitTasks.length; i++) {
				var myVisitTask = myVisitTasks[i];
				if(predefinedVisitTask.name === myVisitTask.name &&
					predefinedVisitTask.description === myVisitTask.description) {
					return true;
				}
			}

			return false;
		}

		return service;

	}
})();
