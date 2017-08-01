<script type="text/javascript">
	var breadcrumbs = [
		{icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
		{
			label: "${ ui.message("coreapps.app.systemAdministration.label")}",
			link: '${ui.pageLink("coreapps", "systemadministration/systemAdministration")}'
		},
		{
			label: "${ ui.message("visittasks.admin.global.predefinedTasks")}",
			link: '/' + OPENMRS_CONTEXT_PATH + '/visittasks/globalPredefinedTasks/entities.page##/'
		},
		{
			label: "${ui.message("visittasks.predefinedTask.name")}"
		}
	];
	
	jQuery('#breadcrumbs').html(emr.generateBreadcrumbHtml(breadcrumbs));
</script>

${ui.includeFragment("visittasks", "predefinedTasks/entity")}
