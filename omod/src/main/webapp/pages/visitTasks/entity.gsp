<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {
            label: "${ ui.message("Patient name")}",
            link: '${ui.pageLink("openhmis.inventory", "inventoryLanding")}'
        },
        {
            label: "${ ui.message("visit_tasks.admin.create")}",
        },
    ];

    jQuery('#breadcrumbs').html(emr.generateBreadcrumbHtml(breadcrumbs));

    jQuery(".tabs").tabs();

</script>

<!--<div ng-show="loading" class="loading-msg">
    <span>${ui.message("openhmis.commons.general.processingPage")}</span>
    <br />
    <span class="loading-img">
        <img src="${ ui.resourceLink("uicommons", "images/spinner.gif") }"/>
    </span>
</div> -->

<form name="itemForm" class="entity-form" ng-class="{'submitted': submitted}" style="font-size:inherit">
</form>
