<script>
    jQuery(".tabs").tabs();
</script>
<div ng-show="loading" class="loading-msg">
    <span>${ui.message("openhmis.commons.general.processingPage")}</span>
    <br />
    <span class="loading-img">
        <img src="${ ui.resourceLink("uicommons", "images/spinner.gif") }"/>
    </span>
</div>

<form ng-hide="loading" name="taskForm" class="entity-form" ng-class="{'submitted': submitted}" style="font-size:inherit">
    <div>
        <input type="text" id="searchBox"
               ng-model="entity.name"
               class="form-control autocomplete-search"
               placeholder="Add a Visit Task" ng-enter="saveOrUpdate()"/>

        <span class="plus-on"><i class="icon-plus-sign" ng-click="saveOrUpdate()"></i></span>

        <span ng-show="predefinedVisitTasks.length > 0"><br /></span>
    </div>

    <div class="tabs" ng-show="predefinedVisitTasks.length > 0">
        <ul>
            <li>
                <a href="#predefinedTaskTab" style="height:inherit">${ui.message('Choose Predefined Visit Tasks')}</a>
            </li>
        </ul>

        <div id="predefinedTaskTab" >
            <div class="visitlist">
                <table style="margin-bottom:5px;" class="manage-entities-table predefined-table">
                    <thead>
                    <tr>
                        <th></th>
                        <th>${ui.message('general.name')}</th>
                        <th>${ui.message('general.description')}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="clickable-tr" pagination-id="__predefinedTasks"
                        dir-paginate="entity in predefinedVisitTasks | itemsPerPage: predefinedTasksLimit"
                        total-items="predefinedTasksTotalResults" current-page="predefinedTasksCurrentPage">
                        <td>
                            <input type="checkbox" name="response"
                                   ng-model="selectedTasks[entity.uuid].checked"
                                   ng-change="addVisitTask(entity)"
                                   title="Add Visit Task"/>
                        </td>
                        <td>{{entity.name}}</td>
                        <td>{{entity.description}}</td>
                    </tr>
                    </tbody>
                </table>
                ${ui.includeFragment("openhmis.commons", "paginationFragment", [
                        hide                : "predefinedVisitTasks.length == 0",
                        paginationId        : "__predefinedTasks",
                        onPageChange        : "searchPredefinedTasks(predefinedTasksCurrentPage)",
                        model               : "predefinedTasksLimit",
                        onChange            : "searchPredefinedTasks(predefinedTasksCurrentPage)",
                        pagingFrom          : "predefinedTasksPagingFrom(predefinedTasksCurrentPage, predefinedTasksLimit)",
                        pagingTo            : "predefinedTasksPagingTo(predefinedTasksCurrentPage, predefinedTasksLimit, predefinedTasksTotalResults)",
                        totalNumberOfResults: "predefinedTasksTotalResults",
                        showRetiredSection  : "false"
                ])}
            </div>
        </div>
    </div>

    <br />
    <div class="detail-section-border-top">
        <br/>
        <div class="tabs">
            <ul>
                <li>
                    <a href="#patientTasksTab" style="height:inherit">${ui.message('Patient Visit Tasks')}</a>
                </li>
            </ul>

            <div id="patientTasksTab" class="visitlist">
                <span ng-hide="patientTasks.length > 0" class="no-tasks">
                    ${ui.message("You don't have Tasks for this patient.")}
                </span>
                <table style="margin-bottom:5px;"
                       class="manage-entities-table"
                       ng-show="patientTasks.length > 0">
                    <thead>
                    <tr>
                        <th></th>
                        <th>${ui.message('general.name')}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="clickable-tr" pagination-id="__patientTasks"
                        dir-paginate="entity in patientTasks | itemsPerPage: patientTasksLimit"
                        total-items="patientTasksTotalResults" current-page="patientTasksCurrentPage">
                        <td>
                            <input type="checkbox"
                                   ng-model="entity.checked"
                                   ng-checked="entity.checked"
                                   ng-click="changeVisitTaskOperation(entity)"
                                   ng-show="entity.status === 'OPEN'"
                                   title="Mark as CLOSED"/>

                            <input type="checkbox"
                                   ng-model="entity.checked"
                                   ng-checked="entity.checked"
                                   ng-click="changeVisitTaskOperation(entity)"
                                   ng-show="entity.status === 'CLOSED'"
                                   title="Mark as OPEN"/>
                        </td>
                        <td ng-style="strikeThrough(entity.status === 'CLOSED')">
                            {{entity.name}}
                            <a href="">
                                <i class="toggle-icon icon-caret-down small"
                                   ng-click="toggleDetailsSection(entity)"
                                   ng-hide="entity.showDetailsSection"></i>
                                <i class="toggle-icon icon-caret-up small"
                                   ng-click="toggleDetailsSection(entity)"
                                   ng-show="entity.showDetailsSection"></i>
                            </a>
                            <div class="row" ng-show="entity.showDetailsSection">
                                <span class="col-sm-2">
                                    <em><b>Created:</b></em>
                                </span>
                                <span class="col-sm-2 col-sm-2-margin">
                                    {{entity.dateCreated | date:'dd MMM yyyy HH:mm'}}
                                </span>

                                <span class="col-sm-2 col-dd-margin">
                                    <em><b>By:</b></em>
                                </span>
                                <span class="col-sm-3 col-creator-margin">
                                    {{entity.creator.person.display}}
                                </span>

                                <span class="col-sm-2 col-note-margin">
                                    <i class="icon-pencil"></i>
                                    <em><b>Add Note:</b></em>
                                </span>
                                <span class="col-sm-3 col-note-desc-margin">
                                    <input type="text" class="form-control input-sm"
                                           style="width:350px" ng-model="entity.description"
                                           ng-enter="updateVisitTask(entity)" />
                                </span>
                                <span class="col-sm-2 col-save-margin">
                                    <input type="button" class="confirm right btn gray-button"
                                           value="${ui.message('general.save')}"
                                           ng-click="updateVisitTask(entity)" />
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
                        ${ui.message('Include Closed Tasks')}
                    </div>
                </div>

                <br />
                <div class="detail-section-border-top">
                    <br />
                    <input type="button" class="cancel" value="{{messageLabels['general.cancel']}}" ng-click="cancel()" />
                </div>
            </div>
        </div>
    </div>

    <div id="task-confirm-dialog" class="dialog" style="display:none;">
        <div class="dialog-header">
            <span>
                <i class="icon-plus"></i>
                <h3>{{taskConfirmTitle}}</h3>
            </span>
            <i class="icon-remove cancel show-cursor"  style="float:right;" ng-click="closeThisDialog()"></i>
        </div>
        <div class="dialog-content form">
            <span>
                {{taskConfirmMessage}}
            </span><q></q>
            <br /><br />
            <div class="ngdialog-buttons detail-section-border-top">
                <br />
                <input type="button" class="cancel" value="{{messageLabels['general.cancel']}}" ng-click="closeThisDialog('Cancel')" />
                <input type="button" class="confirm right" value="Confirm" ng-click="confirm('OK')" />
            </div>
        </div>
    </div>

    <div id="change-task-confirm-dialog" class="dialog" style="display:none;">
        <div class="dialog-header">
            <span>
                <i class="icon-remove"></i>
                <h3>{{changeVisitTaskTitle}}</h3>
            </span>
            <i class="icon-remove cancel show-cursor"  style="float:right;" ng-click="closeThisDialog()"></i>
        </div>
        <div class="dialog-content form">
            <span>
                {{changeVisitTaskBody}}
            </span>
            <br /><br />
            <div class="ngdialog-buttons detail-section-border-top">
                <br />
                <input type="button" class="cancel" value="{{messageLabels['general.cancel']}}" ng-click="closeThisDialog('Cancel')" />
                <input type="button" class="confirm right" value="Confirm" ng-click="confirm('OK')" />
            </div>
        </div>
    </div>

</form>
