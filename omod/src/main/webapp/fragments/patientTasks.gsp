<style type="text/css">

.dateCreated {
    color: #7b7b7b;
}
</style>

<div class="info-section">
    <div class="info-header">
        <i class="icon-list-ul"></i>
        <h3>${ ui.message("Visit Tasks").toUpperCase() }</h3>
        <i class="icon-plus edit-action right" title='${ ui.message("Add Visit Task") }'
           onclick="location.href='/${ui.contextPath()}/visittasks/task/entities.page?patientUuid=${param.patientId[0]}'"></i>
    </div>
    <div class="info-body">
        <% if (patientTasks.size == 0) { %>
        ${ ui.message("No Patient Visits found") }
        <% } else { %>
        <ul>
            <% patientTasks.each { patientTask -> %>
            <li>
                <span class="patientTaskId">
                    <b><a>${ patientTask.name}</a></b>
                    <i class="icon-double-angle-right"></i>
                </span>
                <span class="billStatus">${patientTask.getStatus()} <i class="icon-double-angle-right"></i> </span>
                <span class="billDate">${patientTask.getFormatDateCreated()}</span>
            </li>
            <% } %>
        </ul>
        <% } %>
    </div>
</div>
