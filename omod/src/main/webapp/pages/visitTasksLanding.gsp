<%
	ui.decorateWith("appui", "standardEmrPage")
	
	def htmlSafeId = {extensions ->
		"${extensions.id.replace(".", "-")}-${extensions.id.replace(".", "-")}-extension"
	}
%>

<script type="text/javascript">
	var breadcrumbs = [
		{icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
		{label: "${ ui.message("visit-tasks.page") }"}
	];
</script>

<div id="home-container">
	
	<h1>${ui.message("visit-tasks.task.page")}</h1>

	<div id="apps">dd
		<% extensions.each { ext -> %>
		<a id="${ htmlSafeId(ext) }" href="/${ contextPath }/${ ext.url }" class="button app big">
			<% if (ext.icon) { %>
			<i class="${ ext.icon }"></i>
			<% } %>
			${ ui.message(ext.label) }
		</a>
		<% } %>
	</div>
	

</div>
