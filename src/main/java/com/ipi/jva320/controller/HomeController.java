package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    SalarieAideADomicileService salarieService;

    @GetMapping(value = "/")
    public String home(final ModelMap model) {
        model.put("countEmployees", this.salarieService.countSalaries());
        return "home";
    }

    @GetMapping("/salaries/{id}")
    public String getSalarie(final ModelMap model, @PathVariable(value = "id") String id) {
        model.put("salarie", this.salarieService.getSalarie(Long.parseLong(id)));
        model.put("countEmployees", this.salarieService.countSalaries());
        model.put("newEmployee", false);
        return "detail_Salarie";
    }

    @PostMapping("/salaries/{id}")
    public String updateSalarie(final ModelMap model, SalarieAideADomicile salarie)
            throws EntityExistsException, SalarieException {
        this.salarieService.updateSalarieAideADomicile(salarie);
        List<SalarieAideADomicile> listSalaries = this.salarieService.getSalaries();
        model.put("salaries", listSalaries);
        model.put("countEmployees", this.salarieService.countSalaries());
        return "list";
    }

    @PostMapping("/salaries/")
    public String createSalarie(final ModelMap model, SalarieAideADomicile salarie)
            throws EntityExistsException, SalarieException {
        this.salarieService.creerSalarieAideADomicile(salarie);
        List<SalarieAideADomicile> listSalaries = this.salarieService.getSalaries();
        model.put("salaries", listSalaries);
        model.put("countEmployees", this.salarieService.countSalaries());
        return "list";
    }

    @GetMapping("/salaries/aide/new")
    public String setSalarie(final ModelMap model) throws EntityExistsException, SalarieException {
        model.put("salarie", new SalarieAideADomicile());
        model.put("countEmployees", this.salarieService.countSalaries());
        model.put("newEmployee", true);
        return "detail_Salarie";
    }

    @GetMapping("/salaries")
    public String listeSalaries(final ModelMap model) throws EntityExistsException {
        model.put("salaries", this.salarieService.getSalaries());
        model.put("countEmployees", this.salarieService.countSalaries());
        return "list";
    }

    @GetMapping("/salaries/{id}/delete")
    public String deleteSalarie(final ModelMap model, @PathVariable(value = "id") Long id)
            throws EntityExistsException, SalarieException {
        this.salarieService.deleteSalarieAideADomicile(id);
        List<SalarieAideADomicile> listSalaries = this.salarieService.getSalaries();
        model.put("salaries", listSalaries);
        model.put("countEmployees", this.salarieService.countSalaries());
        return "list";
    }

    @GetMapping("/salaries/search/{name}")
    public String searchSalarie(final ModelMap model, @PathVariable(value = "name") String name)
            throws EntityExistsException, SalarieException {
        List<SalarieAideADomicile> listSalaries = this.salarieService.getSalaries(name);
        model.put("salaries", listSalaries);
        model.put("countEmployees", this.salarieService.countSalaries());
        return "list";
    }
}
