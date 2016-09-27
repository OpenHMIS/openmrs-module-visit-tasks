<script>
    jQuery.noConflict();
    jQuery.fn.modal.noConflict();
</script>

<script>
    function animate(id, className) {
        jQuery('#' + id)
                .removeClass()
                .addClass(className + ' animated')
                .one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend',
                        function() {
                            jQuery(this).removeClass();
                        });
    };
</script>

<br />
<span class="h1-substitue-left">
    ${ui.message('visittasks.page')}
</span>
<form name="taskForm" class="entity-form" ng-class="{'submitted': submitted}" style="font-size:inherit">

    <div class="row">
        <div class="col-xs-9">
            <div class="input-group">
                <input type="text"
                       autocomplete="off"
                       id="searchBox"
                       class="form-control autocomplete-search"
                       placeholder="${ui.message('visittasks.task.add')}"
                       ng-enter="saveOrUpdate()"
                       ng-model="taskName">
                <div class="input-group-btn">
                    <button type="button" class="btn btn-default dropdown-toggle btn-align"
                            data-toggle="dropdown">
                        <span class="caret" ></span>
                    </button>
                    <ul id="color-dropdown-menu" class="dropdown-menu dropdown-menu-right dropdown-width overflow overflow-height">
                        <li ng-repeat="task in predefinedVisitTasks" class="input-lg no-height">
                            <div class="detail-section-border-bottom">
                                <p class="wrap">
                                    <a ng-click="addVisitTask(task)">{{task.name}}</a>
                                </p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-2">
            <input type="button" class="confirm right btn gray-button"
                   value="${ui.message('visittasks.admin.create')}"
                   ng-click="saveOrUpdate()" />
        </div>
    </div>

    <br />

    <div class="detail-section-border-top">
        <br />
        <div class="visitlist">
            <span ng-hide="patientTasks.length > 0" class="no-tasks">
                ${ui.message("visittasks.task.noTasks")}
            </span>
            <table style="margin-bottom:5px;"
                   class="manage-entities-table"
                   ng-show="patientTasks.length > 0">

                <tbody>
                <tr class="clickable-tr" pagination-id="__patientTasks"
                    dir-paginate="entity in patientTasks | itemsPerPage: patientTasksLimit"
                    total-items="patientTasksTotalResults" current-page="patientTasksCurrentPage">
                    <td>
                        <input type="checkbox"
                               ng-model="entity.checked"
                               ng-checked="entity.checked"
                               ng-click="changeVisitTaskOperation(entity, \$index)"
                               ng-show="entity.status === 'OPEN'"
                               title="${ui.message('visittasks.task.markClosed')}"/>

                        <input type="checkbox"
                               ng-model="entity.checked"
                               ng-checked="entity.checked"
                               ng-click="changeVisitTaskOperation(entity, \$index)"
                               ng-show="entity.status === 'CLOSED'"
                               title="${ui.message('visittasks.task.markOpen')}"/>
                    </td>
                    <td ng-style="strikeThrough(entity.status === 'CLOSED')">

                        <span id="animation-{{\$index}}" style="display: block;">

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
                                <i class="icon-trash trash"
                                   ng-click="confirmRemoveTaskDialog(entity)"
                                   title="${ui.message('visittasks.task.voidTask')}"></i>
                            </span>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
            ${ui.includeFragment("openhmis.commons", "paginationFragment", [
                    hide                : "patientTasks.length == 0",
                    paginationId        : "__patientTasks",
                    onPageChange        : "searchPatientTasks(patientTasksCurrentPage)",
                    model               : "patientTasksLimit",
                    onChange            : "searchPatientTasks(patientTasksCurrentPage)",
                    pagingFrom          : "patientTasksPagingFrom(patientTasksCurrentPage, patientTasksLimit)",
                    pagingTo            : "patientTasksPagingTo(patientTasksCurrentPage, patientTasksLimit, patientTasksTotalResults)",
                    totalNumberOfResults: "patientTasksTotalResults",
                    showRetiredSection  : "true"
            ])}

            <div class="row">
                <div class="col-sm-1 col-sm-1-margin">
                    <input type="checkbox"
                           ng-checked="includeClosedTasks"
                           ng-model="includeClosedTasks"
                           ng-change="searchPatientTasks(patientTasksCurrentPage)" />
                </div>
                <div class="col-sm-3 col-include-closed-tasks-margin">
                    ${ui.message('visittasks.task.includeClosedTasks')}
                </div>
            </div>

            <br />
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
            <i class="icon-remove cancel show-cursor"  style="float:right;" ng-click="closeThisDialog()"></i>
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
</form>
