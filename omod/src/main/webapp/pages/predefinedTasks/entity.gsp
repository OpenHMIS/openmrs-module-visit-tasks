<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("visittasks.page")}",
            link: '${ui.pageLink("visittasks", "visitTasksLanding")}'},
        { label: "${ ui.message("visittasks.admin.predefinedTasks")}",
            link: '/' + OPENMRS_CONTEXT_PATH + '/visittasks/predefinedTasks/entities.page##/'},
        { label: "${ui.message("visittasks.predefinedTask.name")}"}
    ];

    jQuery('#breadcrumbs').html(emr.generateBreadcrumbHtml(breadcrumbs));
</script>

<form name="entityForm" class="entity-form" ng-class="{'submitted': submitted}" style="font-size:inherit">
    ${ ui.includeFragment("openhmis.commons", "editEntityHeaderFragment")}

    <input type="hidden" ng-model="entity.uuid" />

    <fieldset class="format">
        <ul class="table-layout">
            <li class="required">
                <span>{{messageLabels['general.name']}}</span>
            </li>
            <li>
                <input name="entityName" class="form-control" type="text" ng-model="entity.name" class="maximized" placeholder="{{messageLabels['general.name']}}" required />
                <p class="checkRequired" ng-hide="nameIsRequiredMsg == '' || nameIsRequiredMsg == undefined">{{nameIsRequiredMsg}}</p>
            </li>
        </ul>
        <ul class="table-layout">
            <li style="vertical-align: top" class="not-required">
                <span>{{messageLabels['general.description']}}</span>
            </li>
            <li>
                <textarea class="form-control" ng-model="entity.description" placeholder="{{messageLabels['general.description']}}" rows="3"
                          cols="40">
                </textarea>
            </li>
        </ul>
    </fieldset>
    <fieldset class="format">
        <span>
            <input type="button" class="cancel" value="{{messageLabels['general.cancel']}}" ng-click="cancel()" />
            <input type="button" class="confirm right" value="{{messageLabels['general.save']}}" ng-click="saveOrUpdate()" />
        </span>
    </fieldset>
</form>
${ ui.includeFragment("openhmis.commons", "retireUnretireDeleteFragment") }
