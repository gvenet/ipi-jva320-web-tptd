package com.ipi.jva320.controller;

import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityExistsException;

@Controller
public class DetailController {
    @Autowired
    SalarieAideADomicileService salarieService;

    // on envoie un boolean "newEmployee" qui permettra d'adapter la page détail (create ou update)
    @GetMapping("/salaries/{id}")
    public String getSalarie(final ModelMap model, @PathVariable(value = "id") String id) {
        model.put("salarie", this.salarieService.getSalarie(Long.parseLong(id)));
        model.put("countEmployees", this.salarieService.countSalaries());
        model.put("newEmployee", false);
        return "detail_Salarie";
    }

    @GetMapping("/salaries/aide/new")
    public String setSalarie(final ModelMap model) throws EntityExistsException {
        model.put("salarie", new SalarieAideADomicile());
        model.put("countEmployees", this.salarieService.countSalaries());
        model.put("newEmployee", true);
        return "detail_Salarie";
    }
}
