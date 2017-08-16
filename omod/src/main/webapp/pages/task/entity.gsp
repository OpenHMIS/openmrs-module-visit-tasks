<script>
    jQuery.noConflict();
    jQuery.fn.modal.noConflict();
    jQuery(".tabs").tabs();
</script>

<span class="h1-substitue-left">
    ${ui.message('visittasks.page')}
</span>
<form name="taskForm" class="entity-form" ng-class="{'submitted': submitted}" class="taskForm">
    <div class="row" ng-show="visitActive==='true'">
        <div class="col-xs-9">
            <div class="input-group">
                <input type="text"
                       autocomplete="off"
                       id="searchBox"
                       class="form-control autocomplete-search"
                       placeholder="${ui.message('visittasks.task.add')}"
                       ng-enter="saveOrUpdate()"
                       ng-model="taskName"
                       typeahead="task.name for task in searchPredefinedTasks(\$viewValue)"
                       typeahead-focus-first="false"
                       typeahead-editable="true"
                       typeahead-min-length="3" />
                <div class="input-group-btn">
                    <button type="button" class="btn btn-default dropdown-toggle btn-align"
                            data-toggle="dropdown">
                        <span class="caret" ></span>
                    </button>
                    <ul id="color-dropdown-menu" ng-show="predefinedVisitTasks.length > 0"
                        ng-class="{'overflow-height': predefinedVisitTasks.length > 5}"
                        class="dropdown-menu dropdown-menu-right dropdown-width dropdown-margin overflow ">
                        <li ng-repeat="task in predefinedVisitTasks | orderBy: 'name'" class="input-lg no-height">
                            <div class="detail-section-border-bottom">
                                <p class="wrap">
                                    <a ng-click="addVisitTask(task)" class="show-cursor">{{task.name}}
                                        <span class="recent-lozenge" ng-show="task.global===true">
                                            ${ui.message('visittasks.task.global')}
                                        </span>
                                    </a>
                                </p>
                            </div>
                        </li>
                    </ul>
                    <ul id="color-dropdown-menu2" ng-show="predefinedVisitTasks.length == 0"
                        class="dropdown-menu dropdown-menu-right dropdown-width dropdown-margin">
                        <li class="input-lg no-height">
                            <center>${ui.message('visittasks.task.noAvailablePredefinedTasks')}</center>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-2">
            <input type="button" class="confirm right btn gray-button"
                   value="${ui.message('visittasks.admin.create')}"
                   ng-click="saveOrUpdate()" ng-disabled="taskName === undefined || taskName === ''"/>
        </div>
    </div>

    <div class="detail-section-border">
        <br />
        <div class="visitlist">
            <div class="tabs">
                <ul>
                    <li>
                        <a href="#openTasksTab" class="tabs-height">
                            ${ui.message('visittasks.task.openTasks')}
                        </a>
                    </li>
                    <li>
                        <a href="#closedTasksTab" class="tabs-height">
                            ${ui.message('visittasks.task.closedTasks')}
                        </a>
                    </li>

                    <div class="row align-right" ng-show="openPatientTasks.length > 0">
                        <div class="col-sm-1 show-cursor">
                            <i class="toggle-icon icon-arrow-down small caret-color"
                               ng-click="expandTasks(true)"
                               title="${ui.message('visittasks.task.expandAll')}"></i>

                        </div>
                        <div class="col-sm-1 show-cursor">
                            <i class="toggle-icon icon-arrow-up small caret-color"
                               ng-click="expandTasks(false)"
                               title="${ui.message('visittasks.task.collapseAll')}"></i>
                        </div>
                    </div>
                </ul>

                <div id="openTasksTab">
                    <span ng-hide="openPatientTasks.length > 0">
                        <br /><br />
                        <span  class="no-tasks">
                            ${ui.message("visittasks.task.noTasks")}
                            <br />
                        </span>
                    </span>
                    <table class="manage-entities-table"
                           ng-show="openPatientTasks.length > 0">
                        <tbody>
                        <tr class="clickable-tr" pagination-id="__openPatientTasks"
                            dir-paginate="entity in openPatientTasks | itemsPerPage: openPatientTasksLimit"
                            total-items="openPatientTasksTotalResults" current-page="openPatientTasksCurrentPage">
                            <td>
                                <input type="checkbox"
                                       ng-model="entity.checked"
                                       ng-checked="entity.checked"
                                       ng-click="changeVisitTaskOperation(entity)"
                                       ng-show="entity.status === 'OPEN' && visitActive==='true'"
                                       title="${ui.message('visittasks.task.markClosed')}"/>
                            </td>
                            <td ng-style="strikeThrough(entity.status === 'CLOSED')">
                                <span id="animation-{{\$index}}" class="show-animation"
                                      ng-class="{'fadeIn animated': entity.animate===true}">
                                    <i class="toggle-icon icon-caret-right small caret-color"
                                       ng-click="toggleDetailsSection(entity)"
                                       ng-hide="entity.showDetailsSection"></i>
                                    <i class="toggle-icon icon-caret-down small caret-color"
                                       ng-click="toggleDetailsSection(entity)"
                                       ng-show="entity.showDetailsSection"></i>
                                    {{entity.name}}
                                </span>

                                <div class="row" ng-show="entity.showDetailsSection">
                                    <span class="col-sm-2">
                                        <em><b>${ui.message('visittasks.task.dateCreated')}</b></em>
                                    </span>
                                    <span class="col-sm-2 col-sm-2-margin">
                                        {{entity.dateCreated | date:'dd MMM yyyy HH:mm'}}
                                    </span>
                                    <span class="col-sm-2 col-dd-margin">
                                        <em><b>${ui.message('visittasks.task.createdBy')}</b></em>
                                    </span>
                                    <span class="col-sm-3 col-creator-margin">
                                        {{entity.creator.person.display}}
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-2 col-closed-margin">
                                        <em><b>${ui.message('visittasks.task.dateClosed')}</b></em>
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-2 col-sm-2-margin">
                                        {{entity.closedOn | date:'dd MMM yyyy HH:mm'}}
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-2 col-dd-margin">
                                        <em><b>${ui.message('visittasks.task.closedBy')}</b></em>
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-3 col-creator-margin">
                                        {{entity.closedBy.person.display}}
                                    </span>
                                    <span class="col-sm-1 col-removed-margin">
                                        <i class="icon-trash trash trash-icon-margin"
                                           ng-click="confirmRemoveTaskDialog(entity)"
                                           ng-show="visitActive==='true'"
                                           title="${ui.message('visittasks.task.voidTask')}"></i>
                                    </span>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    ${ui.includeFragment("openhmis.commons", "paginationFragment", [
                            hide                : "openPatientTasks.length == 0",
                            paginationId        : "__openPatientTasks",
                            onPageChange        : "searchOpenPatientTasks(openPatientTasksCurrentPage)",
                            model               : "openPatientTasksLimit",
                            onChange            : "searchOpenPatientTasks(openPatientTasksCurrentPage)",
                            pagingFrom          : "openPatientTasksPagingFrom(openPatientTasksCurrentPage, openPatientTasksLimit)",
                            pagingTo            : "openPatientTasksPagingTo(openPatientTasksCurrentPage, openPatientTasksLimit, openPatientTasksTotalResults)",
                            totalNumberOfResults: "openPatientTasksTotalResults",
                            showRetiredSection  : "true"
                    ])}

                    <span ng-show="openPatientTasks.length < 3">
                        <br /><br />
                    </span>
                </div>

                <div id="closedTasksTab">
                    <table class="manage-entities-table" ng-show="closedPatientTasks.length > 0">
                        <tbody>
                        <tr class="clickable-tr" pagination-id="__closedPatientTasks"
                            dir-paginate="entity in closedPatientTasks | itemsPerPage: closedPatientTasksLimit"
                            total-items="closedPatientTasksTotalResults" current-page="closedPatientTasksCurrentPage">
                            <td>
                                <input type="checkbox"
                                       ng-model="entity.checked"
                                       ng-checked="entity.checked"
                                       ng-click="changeVisitTaskOperation(entity, \$index)"
                                       ng-show="entity.status === 'CLOSED' && visitActive==='true'"
                                       title="${ui.message('visittasks.task.markOpen')}"/>
                            </td>
                            <td ng-style="strikeThrough(entity.status === 'CLOSED')"
                                ng-click="toggleDetailsSection(entity)">
                                <span id="animation-closed-{{\$index}}" class="show-animation"
                                      ng-class="{'fadeIn animated': entity.animate===true}">
                                    <i class="toggle-icon icon-caret-right small caret-color"
                                       ng-click="toggleDetailsSection(entity)"
                                       ng-hide="entity.showDetailsSection"></i>
                                    <i class="toggle-icon icon-caret-down small caret-color"
                                       ng-click="toggleDetailsSection(entity)"
                                       ng-show="entity.showDetailsSection"></i>
                                    {{entity.name}}
                                </span>

                                <div class="row" ng-show="entity.showDetailsSection">
                                    <span class="col-sm-2">
                                        <em><b>${ui.message('visittasks.task.dateCreated')}</b></em>
                                    </span>
                                    <span class="col-sm-2 col-sm-2-margin">
                                        {{entity.dateCreated | date:'dd MMM yyyy HH:mm'}}
                                    </span>
                                    <span class="col-sm-2 col-dd-margin">
                                        <em><b>${ui.message('visittasks.task.createdBy')}</b></em>
                                    </span>
                                    <span class="col-sm-3 col-creator-margin">
                                        {{entity.creator.person.display}}
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-2 col-closed-margin">
                                        <em><b>${ui.message('visittasks.task.dateClosed')}</b></em>
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-2 col-sm-2-margin">
                                        {{entity.closedOn | date:'dd MMM yyyy HH:mm'}}
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-2 col-dd-margin">
                                        <em><b>${ui.message('visittasks.task.closedBy')}</b></em>
                                    </span>
                                    <span ng-show="entity.status === 'CLOSED'" class="col-sm-3 col-creator-margin">
                                        {{entity.closedBy.person.display}}
                                    </span>
                                    <span class="col-sm-1 col-removed-margin">
                                        <i class="icon-trash trash trash-icon-closed-margin"
                                           ng-click="confirmRemoveTaskDialog(entity)"
                                           ng-show="visitActive==='true'"
                                           title="${ui.message('visittasks.task.voidTask')}"></i>
                                    </span>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                    ${ui.includeFragment("openhmis.commons", "paginationFragment", [
                            hide                : "closedPatientTasks.length == 0",
                            paginationId        : "__closedPatientTasks",
                            onPageChange        : "searchClosedPatientTasks(closedPatientTasksCurrentPage)",
                            model               : "closedPatientTasksLimit",
                            onChange            : "searchClosedPatientTasks(closedPatientTasksCurrentPage)",
                            pagingFrom          : "closedPatientTasksPagingFrom(closedPatientTasksCurrentPage, closedPatientTasksLimit)",
                            pagingTo            : "closedPatientTasksPagingTo(closedPatientTasksCurrentPage, closedPatientTasksLimit, closedPatientTasksTotalResults)",
                            totalNumberOfResults: "closedPatientTasksTotalResults",
                            showRetiredSection  : "true"
                    ])}
                </div>
            </div>

            <br /><br />
            <div class="detail-section-border-top">
                <br />
                <input type="button" class="cancel" value="${ui.message('general.back')}" ng-click="cancel()" />
            </div>
        </div>
    </div>

    <div id="remove-task-confirm-dialog" class="dialog" style="display:none;">
        <div class="dialog-header">
            <span>
                <i class="icon-trash trash"></i>
                <h3>${ui.message('visittasks.task.voidTask')}</h3>
            </span>
            <i class="icon-remove cancel show-cursor"  class="align-right"
               ng-click="closeThisDialog()"></i>
        </div>
        <div class="dialog-content form">
            <span>
                ${ui.message('visittasks.task.voidConfirm')}
            </span>
            <br /><br />
            <div class="ngdialog-buttons detail-section-border-top">
                <br />
                <input type="button" class="cancel" value="${ui.message('general.cancel')}"
                       ng-click="closeThisDialog('Cancel')" />
                <input type="button" class="confirm right" value="Confirm" ng-click="confirm('OK')" />
            </div>
        </div>
    </div>
    <br />
</form>
