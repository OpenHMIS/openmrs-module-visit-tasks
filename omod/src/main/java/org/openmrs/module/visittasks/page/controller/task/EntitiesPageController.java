package org.openmrs.module.visittasks.page.controller.task;

import org.openmrs.Patient;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.api.PatientService;
import org.openmrs.module.appframework.domain.Extension;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.visittasks.web.ModuleWebConstants;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.InjectBeans;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.Redirect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the visit task page.
 */
@Controller
@OpenmrsProfile(modules = { "uiframework:*.*" })
public class EntitiesPageController {

	/**
	 * @param model
	 * @param patientUuid
	 * @throws IOException
	 */
	public Object get(
			@RequestParam(value = "patientUuid", required = false) String patientUuid,
			@RequestParam(value = "returnUrl", required = false) String returnUrl,
			PageModel model, @SpringBean("patientService") PatientService patientService) throws IOException {
		Patient patient = patientService.getPatientByUuid(patientUuid);
		if(patient != null){
			model.addAttribute("patient", patient.getPersonName().getFullName());
		} else{
			return new Redirect("referenceapplication", "home", "");
		}

		model.addAttribute("returnUrl", returnUrl);

		return null;
	}
}
