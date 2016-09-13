<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {
            label: "${ ui.message("Patient name")}",
            link: '${ui.pageLink("openhmis.inventory", "inventoryLanding")}'
        },
        {
            label: "${ ui.message("visit-tasks.admin.create")}",
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
    <label>Add Visit Task</label>

    <fieldset class="format">
        <ul class="table-layout">
            <li class="required">
                <span>{{messageLabels['general.name']}}</span>
            </li>
            <li>
                <input name="entityName" type="text" ng-model="entity.name" class="maximized" placeholder="{{messageLabels['general.name']}}" required />
                <p class="checkRequired" ng-hide="nameIsRequiredMsg == '' || nameIsRequiredMsg == undefined">{{nameIsRequiredMsg}}</p>
            </li>
        </ul>
        <ul class="table-layout">
            <li style="vertical-align: top" class="not-required">
                <span>{{messageLabels['general.description']}}</span>
            </li>
            <li>
                <textarea ng-model="entity.description" placeholder="{{messageLabels['general.description']}}" rows="3"
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

    <br/><br/>
    <label>Select from Predefined Visit Tasks</label>
    <div ng-repeat="predefinedVisitTask in predefinedVisitTasks track by predefinedVisitTask.uuid">
        <ul class="table-layout">
            <li>
                <input type="checkbox" name="response"
                       ng-model="selectedTasks[predefinedVisitTask.uuid].checked"
                       ng-change="addVisitTask(predefinedVisitTask)" />
            </li>
            <li>
                <span>{{predefinedVisitTask.name}}</span>
            </li>
            <li>
                <span>{{predefinedVisitTask.description}}</span>
            </li>
        </ul>
    </div>
    <br /><br />
    <label>My Visit Tasks</label>
    <div>
        <table style="margin-bottom:5px;" class="manage-entities-table">
            <thead>
            <tr>
                <th>${ui.message('Action')}</th>
                <th>${ui.message('general.name')}</th>
                <th>${ui.message('Date Created')}</th>
                <th>${ui.message('general.description')}</th>
            </tr>
            </thead>
            <tbody>
            <tr class="clickable-tr" dir-paginate="entity in myVisitTasks | itemsPerPage: limit"
                total-items="totalNumOfResults" current-page="currentPage">
                <td>
                    <i class="icon-remove" ng-click="closeVisitTaskOperation(entity)"></i>
                </td>
                <td ng-style="strikeThrough(entity.status === 'CLOSED')">{{entity.name}}</td>
                <td ng-style="strikeThrough(entity.status === 'CLOSED')">{{entity.dateCreated | date:'dd MMM yyyy HH:mm'}}</td>
                <td ng-style="strikeThrough(entity.status === 'CLOSED'n)">{{entity.description}}</td>
            </tr>
            </tbody>
        </table>
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
            </span>
            <br /><br />
            <div class="ngdialog-buttons detail-section-border-top">
                <br />
                <input type="button" class="cancel" value="{{messageLabels['general.cancel']}}" ng-click="closeThisDialog('Cancel')" />
                <input type="button" class="confirm right" value="Confirm" ng-click="confirm('OK')" />
            </div>
        </div>
    </div>

    <div id="close-task-confirm-dialog" class="dialog" style="display:none;">
        <div class="dialog-header">
            <span>
                <i class="icon-remove"></i>
                <h3>Close Task</h3>
            </span>
            <i class="icon-remove cancel show-cursor"  style="float:right;" ng-click="closeThisDialog()"></i>
        </div>
        <div class="dialog-content form">
            <span>
                Are you sure you want to close this task?
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
