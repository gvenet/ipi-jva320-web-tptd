package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityExistsException;

@Controller
public class ListController {
    @Autowired
    SalarieAideADomicileService salarieService;

    private void salarieModel(
            int page,
            int size,
            String sortDirection,
            String sortProperty,
            String name,
            ModelMap model) {

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<SalarieAideADomicile> salariesPage;

        if (name == null || name.isEmpty()) {
            salariesPage = salarieService.getSalaries(pageable);
        } else {
            salariesPage = salarieService.getSalaries(name, pageable);
        }

        model.put("salaries", salariesPage.getContent());
        model.put("currentPage", salariesPage.getNumber());
        model.put("totalPages", salariesPage.getTotalPages());
        model.put("totalElements", salariesPage.getTotalElements());
        model.put("countEmployees", this.salarieService.countSalaries());
        model.put("size", size);
        model.put("name", name);
        model.put("page", page);
        model.put("sortDirection", sortDirection);
        model.put("sortProperty", sortProperty);

    }

    @PostMapping("/salaries/{id}")
    public String updateSalarie(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "nom") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            final ModelMap model,
            SalarieAideADomicile salarie)
            throws EntityExistsException, SalarieException {

        this.salarieService.updateSalarieAideADomicile(salarie);
        salarieModel(page, size, sortDirection, sortProperty, null, model);

        return "list";
    }

    @PostMapping("/salaries")
    public String createSalarie(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "nom") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,

            final ModelMap model,
            SalarieAideADomicile salarie)
            throws EntityExistsException, SalarieException {

        this.salarieService.creerSalarieAideADomicile(salarie);
        salarieModel(page, size, sortDirection, sortProperty, null, model);

        return "list";
    }

    @GetMapping("/salaries/{id}/delete")
    public String deleteSalarie(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "nom") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            final ModelMap model, @PathVariable(value = "id") Long id)
            throws EntityExistsException, SalarieException {

        this.salarieService.deleteSalarieAideADomicile(id);
        salarieModel(page, size, sortDirection, sortProperty, null, model);

        return "list";
    }

    @GetMapping("/salaries")
    public String listSalaries(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "nom") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "sortIdDirection", defaultValue = "ASC") String sortIdDirection,
            @RequestParam(value = "sortNameDirection", defaultValue = "ASC") String sortNameDirection,
            @RequestParam(value = "name", required = false) String name,
            ModelMap model) {

        if (sortProperty.equals("nom")) {
            sortDirection = sortNameDirection;
        } else {
            sortDirection = sortIdDirection;
        }

        salarieModel(page, size, sortDirection, sortProperty, name, model);
        model.put("sortIdDirection", sortIdDirection);
        model.put("sortNameDirection", sortNameDirection);

        return "list";
    }

    @GetMapping("/search")
    public String searchSalarie(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "nom") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam("name") String name, ModelMap model)
            throws EntityExistsException {

        salarieModel(page, size, sortDirection, sortProperty, name, model);

        return "list";
    }

}
