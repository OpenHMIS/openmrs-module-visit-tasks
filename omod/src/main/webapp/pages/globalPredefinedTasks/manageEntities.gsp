<script type="text/javascript">
	var breadcrumbs = [
		{icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
		{
			label: "${ ui.message("coreapps.app.systemAdministration.label")}",
			link: '${ui.pageLink("coreapps", "systemadministration/systemAdministration")}'
		},
		{label: "${ ui.message("visittasks.predefinedTask.global.task.label")}",}
	];
	
	jQuery('#breadcrumbs').html(emr.generateBreadcrumbHtml(breadcrumbs));
</script>

<div id="entities-body">
	<br/>
	
	<div id="manage-entities-header">
		<span class="h1-substitue-left" style="float:left;">
			${ui.message('visittasks.predefinedTask.global.task.label')}
		</span>
		<span style="float:right;">
			<a class="button confirm" ui-sref="new">
				<i class="icon-plus"></i>
				${ui.message('visittasks.predefinedTask.new')}
			</a>
		</span>
	</div>
	<br/><br/><br/>
	
	<div>
		<div id="entities">
			${ui.includeFragment("openhmis.commons", "searchFragment", [
					model        : "globalPredefinedTasksName",
					onChangeEvent: "searchGlobalPredefinedVisitTasks()",
					class        : ["field-display ui-autocomplete-input form-control searchinput"],
					placeholder  : [ui.message("openhmis.commons.general.enterSearchPhrase")]
			])}
			
			<br/><br/>
			<table style="margin-bottom:5px;" class="manage-entities-table">
				<thead>
				<tr>
					<th style="width: 40%">${ui.message('visittasks.predefinedTask.global.task.label')}</th>
				</tr>
				</thead>
				<tbody>
				<tr class="clickable-tr" pagination-id="__GlobalPredefinedTasks" dir-paginate="entity in globalPredefinedVisitTasks | itemsPerPage: myPredefinedTasksLimit"
				    total-items="totalNumOfGlobalPredefinedVisitTasks" current-page="globalPredefinedTasksCurrentPage" ui-sref="edit({uuid: entity.uuid})">
					<td ng-style="strikeThrough(entity.retired)">{{entity.name}}</td>
				</tr>
				</tbody>
			</table>
			
			<div ng-show="globalPredefinedVisitTasks.length == 0">
				<br/>
				${ui.message('openhmis.commons.general.preSearchMessage')} - <b>{{searchField}}</b> - {{postSearchMessage}}
				<br/><br/>
				<span><input type="checkbox" ng-checked="includeRetired" ng-model="includeRetired"
				             ng-change="searchGlobalPredefinedVisitTasks(globalPredefinedTasksCurrentPage)"></span>
				<span>${ui.message('openhmis.commons.general.includeRetired')}</span>
			</div>
			${ui.includeFragment("openhmis.commons", "paginationFragment", [
					hide                : "globalPredefinedVisitTasks.length == 0",
					paginationId        : "__GlobalPredefinedTasks",
					onPageChange        : "searchGlobalPredefinedVisitTasks(globalPredefinedTasksCurrentPage)",
					model               : "globalPredefinedTasksLimit",
					onChange            : "searchGlobalPredefinedVisitTasks(globalPredefinedTasksCurrentPage)",
					pagingFrom          : "globalPredefinedTasksPagingFrom(globalPredefinedTasksCurrentPage, globalPredefinedTasksLimit)",
					pagingTo            : "globalPredefinedTasksPagingTo(globalPredefinedTasksCurrentPage, globalPredefinedTasksLimit, totalNumOfGlobalPredefinedVisitTasks)",
					totalNumberOfResults: "totalNumOfGlobalPredefinedVisitTasks"
			])}
		</div>
	</div>
</div>
