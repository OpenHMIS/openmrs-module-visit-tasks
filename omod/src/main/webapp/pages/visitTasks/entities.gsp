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
    ui.includeJavascript("visit-tasks", "visitTasks/models/entity.model.js")
    ui.includeJavascript("visit-tasks", "visitTasks/services/entity.restful.services.js")
    ui.includeJavascript("visit-tasks", "visitTasks/controllers/entity.controller.js")
    ui.includeJavascript("visit-tasks", "visitTasks/services/entity.functions.js")
    ui.includeJavascript("visit-tasks", "constants.js")
%>

<script data-main="visitTasks/configs/entity.main" src="/${ ui.contextPath() }/moduleResources/uicommons/scripts/require/require.js"></script>

<div id="visitTasksApp">
    <div ui-view></div>
</div>
