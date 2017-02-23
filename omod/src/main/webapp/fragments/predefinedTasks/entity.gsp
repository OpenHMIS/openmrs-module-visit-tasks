<div ng-show="loading" class="loading-msg">
	<span>${ui.message("openhmis.commons.general.processingPage")}</span>
	<br />
	<span class="loading-img">
		<img src="${ ui.resourceLink("uicommons", "images/spinner.gif") }"/>
	</span>
</div>

<form ng-hide="loading" name="entityForm" class="entity-form" ng-class="{'submitted': submitted}" style="font-size:inherit">
	${ ui.includeFragment("openhmis.commons", "editEntityHeaderFragment")}
	
	<input type="hidden" ng-model="entity.uuid" />
	<fieldset class="format">
		<div class="row">
			<div class="col-md-2">
				<span style="color: red">*</span><span>&nbsp;${ui.message('general.name')}</span>
			</div>
			<div class="col-md-10">
				<input name="entityName" class="form-control" type="text" ng-model="entity.name" class="maximized"
				       placeholder="${ui.message('general.name')}" required/>
				
				<p class="checkRequired"
				   ng-hide="nameIsRequiredMsg == '' || nameIsRequiredMsg == undefined">{{nameIsRequiredMsg}}</p>
			</div>
		</div>
		<br/>
		<div class="detail-section-border-top">
			<br/>
			<span>
				<input type="button" class="cancel" value="${ui.message('general.cancel')}" ng-click="cancel()"/>
				<input type="button" class="confirm right" value="${ui.message('general.save')}" ng-click="saveOrUpdate()"/>
			</span>
		</div>
		
	</fieldset>
</form>
${ ui.includeFragment("openhmis.commons", "retireUnretireDeleteFragment") }
