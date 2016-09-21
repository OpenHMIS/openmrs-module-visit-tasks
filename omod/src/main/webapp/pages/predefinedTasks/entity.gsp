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
	<span class="h1-substitue-left">
		${ui.message('visittasks.predefinedTask.new')}
	</span>
	
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
		<ul class="table-layout" ng-show="showMakeGlobal == true">
			<li style="vertical-align: top" class="not-required">
				<span>${ui.message('visittasks.predefined.task.makeGlobal.label')}</span>
			</li>
			<li>
				<input type="checkbox" ng-model="entity.global" name="global" ng-checked="entity.global"/>
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
