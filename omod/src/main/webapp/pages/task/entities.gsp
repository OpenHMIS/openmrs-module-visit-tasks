<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("visit_tasks.admin.create") ])

    /* load stylesheets */
    ui.includeCss("openhmis.commons", "bootstrap.css")
    ui.includeCss("openhmis.commons", "entities2x.css")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")
    ui.includeCss("visit-tasks", "entity.css")

    /* load angular libraries */
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("uicommons", "angular-ui/angular-ui-router.min.js")
    ui.includeJavascript("uicommons", "angular-ui/ui-bootstrap-tpls-0.11.2.min.js")
    ui.includeJavascript("uicommons", "angular-common.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")
    ui.includeJavascript("uicommons", "datetimepicker/bootstrap-datetimepicker.min.js")
    ui.includeCss("uicommons", "datetimepicker.css")

    /* load re-usables/common modules */
    ui.includeFragment("openhmis.commons", "load.reusable.modules")

    /* load stockroom modules */
    ui.includeJavascript("visittasks", "task/models/entity.model.js")
    ui.includeJavascript("visittasks", "task/services/entity.restful.services.js")
    ui.includeJavascript("visittasks", "task/controllers/entity.controller.js")
    ui.includeJavascript("visittasks", "task/services/entity.functions.js")
    ui.includeJavascript("visittasks", "constants.js")
%>

<script data-main="task/configs/entity.main" src="/${ ui.contextPath() }/moduleResources/uicommons/scripts/require/require.js"></script>

<div id="taskApp">
    <div ui-view></div>
</div>
