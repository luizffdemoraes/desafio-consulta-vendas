package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<ReportDTO> getReport(@RequestParam(name = "minDate", defaultValue = "") String minDate,
                                               @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
                                               @RequestParam(name = "name", defaultValue = "") String name) {

        return ResponseEntity.ok(service.searchReport(minDate, maxDate, name));
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<List<SummaryDTO>> getSummary(@RequestParam(name = "minDate", defaultValue = "") String minDate,
                                                       @RequestParam(name = "maxDate", defaultValue = "") String maxDate) {

        return ResponseEntity.ok(service.summaryReport(minDate, maxDate));
    }
}
