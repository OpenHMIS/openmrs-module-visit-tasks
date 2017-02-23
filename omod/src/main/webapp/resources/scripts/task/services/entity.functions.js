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
			confirmRemoveTaskDialog: confirmRemoveTaskDialog,
			searchVisitTask: searchVisitTask,
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

		function confirmRemoveTaskDialog($scope, task) {
			var dialog = emr.setupConfirmationDialog({
				selector: '#remove-task-confirm-dialog',
				actions: {
					confirm: function() {
						task.voided = true;
						$scope.entity = task;

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

		function searchVisitTask(predefinedVisitTask, visitTasks) {
			for(var i = 0; i < visitTasks.length; i++) {
				var visitTask = visitTasks[i];
				if(predefinedVisitTask.name === visitTask.name &&
					visitTask.status === 'OPEN') {
					return true;
				}
			}
			return false;
		}

		return service;
	}
})();
