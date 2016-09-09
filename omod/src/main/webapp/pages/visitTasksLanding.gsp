<%
	ui.decorateWith("appui", "standardEmrPage")
	
	def htmlSafeId = {extensions ->
		"${extensions.id.replace(".", "-")}-${extensions.id.replace(".", "-")}-extension"
	}
%>

<script type="text/javascript">
	var breadcrumbs = [
		{icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
		{label: "${ ui.message("visit_tasks.page") }"}
	];
</script>

<div id="home-container">
	
	<h1>${ui.message("visit_tasks.task.page")}</h1>
	

</div>
