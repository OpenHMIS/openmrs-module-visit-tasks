<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("visittasks.admin.predefinedTasks") ])

    /* load stylesheets */
    ui.includeCss("openhmis.commons", "bootstrap.css")
    ui.includeCss("openhmis.commons", "entities2x.css")
    ui.includeCss("uicommons", "ngDialog/ngDialog.min.css")

    /* load angular libraries */
    ui.includeJavascript("uicommons", "angular.min.js")
    ui.includeJavascript("uicommons", "angular-ui/angular-ui-router.min.js")
    ui.includeJavascript("uicommons", "angular-ui/ui-bootstrap-tpls-0.11.2.min.js")
    ui.includeJavascript("uicommons", "angular-common.js")
    ui.includeJavascript("uicommons", "ngDialog/ngDialog.js")

    /* load re-usables/common modules */
    ui.includeFragment("openhmis.commons", "load.reusable.modules")

    /* load predefinedTasks modules */
    ui.includeJavascript("visittasks", "predefinedTasks/models/entity.model.js")
    ui.includeJavascript("visittasks", "predefinedTasks/services/entity.restful.services.js")
    ui.includeJavascript("visittasks", "myPredefinedTasks/controllers/manage-entity.controller.js")
    ui.includeJavascript("visittasks", "constants.js")
%>

<script data-main="predefinedTasks/configs/entity.main" src="/${ ui.contextPath() }/moduleResources/uicommons/scripts/require/require.js"></script>

<div id="entitiesApp">
    <div ui-view></div>
</div>
