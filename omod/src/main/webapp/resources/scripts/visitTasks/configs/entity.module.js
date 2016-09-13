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

/*
 * The module determines which page should be loaded depending on the url/route.
 * The manage entities page loads all entities. The entity.page
 * page either creates a new operation if NO uuid is given, else loads an
 * existing entity for editing.
 */
(function() {
	define([], loadpage);

	function loadpage() {
		'use strict';
		var app = angular.module('visitTasksApp', ['ui.bootstrap', 'ngDialog', 'ui.router',
			'angularUtils.directives.dirPagination', 'app.visitTaskFunctionsFactory', 'app.css',
			'app.filters', 'app.pagination', 'app.cookies', 'app.genericMetadataModel', 'app.restfulServices',
			'app.genericEntityController', 'app.genericManageController']);
		app.config(function($stateProvider, $urlRouterProvider, $provide) {
			/*
			 * Configure routes and urls. The default route is '/' which loads
			 * manage entities.page. 'edit' route calls entities.page -- it
			 * appends a 'uuid' to the url to edit an existing entity. 'new'
			 * route is called to create a new entity.
			 */
			$urlRouterProvider.otherwise('/');
			$stateProvider.state('view', {
				url: '/:args',
				views: {
					'': {
						templateUrl: 'entity.page',
						controller: 'VisitTasksController'
					}
				}
			});

			$provide.factory('$excleptionHandler', function($injector) {
				return ohmis.handleException;
			});
		});
		return app;
	}
})();
