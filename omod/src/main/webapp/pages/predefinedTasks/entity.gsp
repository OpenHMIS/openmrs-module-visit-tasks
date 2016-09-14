<script type="text/javascript">
	var breadcrumbs = [
		{icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
		{
			label: "${ ui.message("visittasks.page")}",
			link: '${ui.pageLink("visittasks", "visitTasksLanding")}'
		},
		{
			label: "${ ui.message("visittasks.admin.predefinedTasks")}",
			link: '/' + OPENMRS_CONTEXT_PATH + '/visittasks/predefinedTasks/entities.page##/'
		},
		{label: "${ui.message("visittasks.predefinedTask.name")}"}
	];
	
	jQuery('#breadcrumbs').html(emr.generateBreadcrumbHtml(breadcrumbs));
</script>

<form name="entityForm" class="entity-form" ng-class="{'submitted': submitted}" style="font-size:inherit">
	${ui.includeFragment("openhmis.commons", "editEntityHeaderFragment")}
	
	<input type="hidden" ng-model="entity.uuid"/>
	
	<fieldset class="format">
		<ul class="table-layout">
			<li class="required">
				<span>${ui.message('general.name')}</span>
			</li>
			<li>
				<input name="entityName" class="form-control" type="text" ng-model="entity.name" class="maximized"
				       placeholder="${ui.message('general.name')}" required/>
				
				<p class="checkRequired"
				   ng-hide="nameIsRequiredMsg == '' || nameIsRequiredMsg == undefined">{{nameIsRequiredMsg}}</p>
			</li>
		</ul>
		<ul class="table-layout">
			<li style="vertical-align: top" class="not-required">
				<span>${ui.message('general.description')}</span>
			</li>
			<li>
				<textarea class="form-control" ng-model="entity.description"
				          placeholder="${ui.message('general.description')}" rows="3"
				          cols="40">
				</textarea>
			</li>
		</ul>
		<ul class="table-layout">
			<li style="vertical-align: top" class="not-required">
				<span>${ui.message('visittasks.predefined.task.type.roleLabel')}</span>
			</li>
			<li>
				<select class="form-control" ng-model="entity.role"
				        ng-options='role.display for role in roles track by role.uuid'>
				</select>
			</li>
		</ul>
	</fieldset>
	<fieldset class="format">
		<span>
			<input type="button" class="cancel" value="${ui.message('general.cancel')}" ng-click="cancel()"/>
			<input type="button" class="confirm right" value="${ui.message('general.save')}" ng-click="saveOrUpdate()"/>
		</span>
	</fieldset>
</form>
${ui.includeFragment("openhmis.commons", "retireUnretireDeleteFragment")}
