<script type="text/javascript">
	var breadcrumbs = [
		{icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
		{label: "${ ui.message("visittasks.predefinedTask.user.task.label")}"}
	];
	
	jQuery('#breadcrumbs').html(emr.generateBreadcrumbHtml(breadcrumbs));
</script>

<div id="entities-body">
	<br/>
	
	<div id="manage-entities-header">
		<span class="h1-substitue-left" style="float:left;">
			${ui.message('visittasks.predefinedTask.user.task.label')}
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
					model        : "myPredefinedTasksName",
					onChangeEvent: "searchMyPredefinedVisitTasks()",
					class        : ["field-display ui-autocomplete-input form-control searchinput"],
					placeholder  : [ui.message("openhmis.commons.general.enterSearchPhrase")]
			])}
			
			<br/><br/>
			<table style="margin-bottom:5px;" class="manage-entities-table">
				<thead>
				<tr>
					<th style="width: 40%">${ui.message('visittasks.predefinedTask.user.task.label')}</th>
				</tr>
				</thead>
				<tbody>
				<tr class="clickable-tr" pagination-id="__myPredefinedTasks"
					dir-paginate="entity in myPredefinedVisitTasks | itemsPerPage: myPredefinedTasksLimit"
				    total-items="totalNumOfMyPredefinedVisitTasks" current-page="myPredefinedTasksCurrentPage"
					ui-sref="edit({uuid: entity.uuid})">
					<td ng-style="strikeThrough(entity.retired)">{{entity.name}}</td>
				</tr>
				</tbody>
			</table>
			
			<div ng-show="myPredefinedVisitTasks.length == 0">
				<br/>
				${ui.message('openhmis.commons.general.preSearchMessage')} - <b>{{searchField}}</b> - {{postSearchMessage}}
				<br/><br/>
				<span><input type="checkbox" ng-checked="includeRetired" ng-model="includeRetired"
				             ng-change="searchMyPredefinedVisitTasks(myPredefinedTasksCurrentPage)"></span>
				<span>${ui.message('openhmis.commons.general.includeRetired')}</span>
			</div>
			${ui.includeFragment("openhmis.commons", "paginationFragment", [
					hide                : "myPredefinedVisitTasks.length == 0",
					paginationId        : "__myPredefinedTasks",
					onPageChange        : "searchMyPredefinedVisitTasks(myPredefinedTasksCurrentPage)",
					model               : "myPredefinedTasksLimit",
					onChange            : "searchMyPredefinedVisitTasks(myPredefinedTasksCurrentPage)",
					pagingFrom          : "myPredefinedTasksPagingFrom(myPredefinedTasksCurrentPage, myPredefinedTasksLimit)",
					pagingTo            : "myPredefinedTasksPagingTo(myPredefinedTasksCurrentPage, myPredefinedTasksLimit, totalNumOfMyPredefinedVisitTasks)",
					totalNumberOfResults: "totalNumOfMyPredefinedVisitTasks"
			])}
		</div>
	</div>
</div>
